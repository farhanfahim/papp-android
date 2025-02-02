package co.chatsdk.firebase;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import co.chatsdk.core.base.AbstractThreadHandler;
import co.chatsdk.core.dao.DaoCore;
import co.chatsdk.core.dao.Keys;
import co.chatsdk.core.dao.Message;
import co.chatsdk.core.dao.Thread;
import co.chatsdk.core.dao.User;
import co.chatsdk.core.hook.HookEvent;
import co.chatsdk.core.interfaces.ThreadType;
import co.chatsdk.core.session.ChatSDK;
import co.chatsdk.core.session.StorageManager;
import co.chatsdk.core.types.MessageSendProgress;
import co.chatsdk.core.utils.CrashReportingCompletableObserver;
import co.chatsdk.firebase.wrappers.MessageWrapper;
import co.chatsdk.firebase.wrappers.ThreadWrapper;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Single;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.SingleSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by benjaminsmiley-andrews on 25/05/2017.
 */

public class FirebaseThreadHandler extends AbstractThreadHandler {

    public static int UserThreadLinkTypeAddUser = 1;
    public static int UserThreadLinkTypeRemoveUser = 2;
    public static int UserThreadLinkTypeGrantAudioAccess = 3;

    public Single<List<Message>> loadMoreMessagesForThread(final Message fromMessage, final Thread thread) {
        return super.loadMoreMessagesForThread(fromMessage, thread).flatMap(messages -> {
            if (messages.isEmpty()) {
                return new ThreadWrapper(thread).loadMoreMessages(fromMessage, ChatSDK.config().messagesToLoadPerBatch);
            }
            return Single.just(messages);
        });
    }

    /**
     * Add given users list to the given thread.
     * The RepetitiveCompletionListenerWithError will notify by his "onItem" method for each user that was successfully added.
     * In the "onItemFailed" you can get all users that the system could not add to the server.
     * When all users are added the system will call the "onDone" method.
     **/
    public Completable addUsersToThread(final Thread thread, final List<User> users) {
        return setUserThreadLinkValue(thread, users, UserThreadLinkTypeAddUser,0);
    }

    /**
     * This function is a convenience function to add or remove batches of users
     * from threads. If th12e value is defined, it will populate the thread/users
     * path with the user IDs. And add the thread ID to the user/threads path for
     * private threads. If value is null, the users will be removed from the thread/users
     * path and the thread will be removed from the user/threads path
     *
     * @param thread
     * @param users
     * @param userThreadLinkType - 1 => Add, 2 => Remove
     * @return
     */
    protected Completable setUserThreadLinkValue(final Thread thread, final List<User> users, final int userThreadLinkType, int extras) {
        return Completable.create(e -> {

            DatabaseReference ref = FirebasePaths.firebaseRawRef();
            final HashMap<String, Object> data = new HashMap<>();

            for (final User u : users) {
                DatabaseReference threadUsersRef = FirebasePaths.threadUsersRef(thread.getEntityID()).child(u.getEntityID()).child(Keys.Status);
                DatabaseReference threadUserAudioRecordAccess = FirebasePaths.threadUsersRef(thread.getEntityID()).child(u.getEntityID()).child(Keys.AudioAccess);
                DatabaseReference userThreadsRef = FirebasePaths.userThreadsRef(u.getEntityID()).child(thread.getEntityID()).child(Keys.InvitedBy);

                String threadUsersPath = threadUsersRef.toString().replace(threadUsersRef.getRoot().toString(), "");
                String userThreadsPath = userThreadsRef.toString().replace(userThreadsRef.getRoot().toString(), "");
                String threadUserAudioRecordAccessPath = threadUserAudioRecordAccess.toString().replace(userThreadsRef.getRoot().toString(), "");

                //
                if (userThreadLinkType == UserThreadLinkTypeAddUser) {
                    data.put(threadUsersPath, u.getEntityID().equals(thread.getCreatorEntityId()) ? Keys.Owner : Keys.Member);
                    data.put(userThreadsPath, ChatSDK.currentUserID());

                    if (thread.typeIs(ThreadType.Public)) {
                        threadUsersRef.onDisconnect().removeValue();
                    }
                } else if (userThreadLinkType == UserThreadLinkTypeRemoveUser) {
                    data.put(threadUsersPath, null);
                    data.put(userThreadsPath, null);
                } else if(userThreadLinkType == UserThreadLinkTypeGrantAudioAccess) {
                    data.put(threadUserAudioRecordAccessPath, extras);
                }
            }

            ref.updateChildren(data, (databaseError, databaseReference) -> {
                if (databaseError == null) {
                    FirebaseEntity.pushThreadUsersUpdated(thread.getEntityID()).subscribe(new CrashReportingCompletableObserver());
                    for (User u : users) {
                        FirebaseEntity.pushUserThreadsUpdated(u.getEntityID()).subscribe(new CrashReportingCompletableObserver());
                    }
                    e.onComplete();
                } else {
                    e.onError(databaseError.toException());
                }
            });
        }).subscribeOn(Schedulers.single());
    }

    public Completable removeUsersFromThread(final Thread thread, List<User> users) {
        return setUserThreadLinkValue(thread, users, UserThreadLinkTypeRemoveUser,0);
    }

    @Override
    public Completable grantAudioRecordAccess(Thread thread, User users,  boolean grant) {
        return grantAudioAccess(thread,users, grant);
    }
    public Completable grantAudioAccess(final Thread thread, User user, boolean grant) {
        List<User> userList = new ArrayList<>();
        userList.add(user);
        return setUserThreadLinkValue(thread, userList, UserThreadLinkTypeGrantAudioAccess, grant ? 1 : 0);
    }

    public Completable pushThread(Thread thread) {
        return new ThreadWrapper(thread).push();
    }

    public Completable pushThreadMeta(Thread thread) {
        return new ThreadWrapper(thread).pushMeta();
    }

    public Completable deleteThreadPermanently(Thread thread) {
        return new ThreadWrapper(thread).deleteThreadPermanently();
    }

    @Override
    public Completable makeAdminToEarliestPerson(Thread thread, String userId) {
        return new ThreadWrapper(thread).makeAdminToEarliestPerson(userId);
    }

    /**
     * Send a message,
     * The message need to have a owner thread attached to it or it cant be added.
     * If the destination thread is public the system will add the user to the message thread if needed.
     * The uploading to the server part can bee seen her {@see FirebaseCoreAdapter#PushMessageWithComplition}.
     */
    public Observable<MessageSendProgress> sendMessage(final Message message) {
        return Observable.create(e -> {
            new MessageWrapper(message).send()
                .subscribeOn(Schedulers.single())
                .subscribe(() -> {
                    pushForMessage(message);
                    e.onNext(new MessageSendProgress(message));
                    e.onComplete();
                }, throwable -> e.onError(throwable))
        ;});
    }

    /**
     * Create thread for given users.
     * When the thread is added to the server the "onMainFinished" will be invoked,
     * If an error occurred the error object would not be null.
     * For each user that was successfully added the "onItem" method will be called,
     * For any item adding failure the "onItemFailed will be called.
     * If the main task will fail the error object in the "onMainFinished" method will be called."
     **/
    public Single<Thread> createThread(final List<User> users) {
        return createThread(null, users);
    }

    public Single<Thread> createThread(final String name, final List<User> users) {
        return createThread(name, users, -1);
    }

    public Single<Thread> createThread(final String name, final List<User> users, final int type) {
        return createThread(name, users, type, null);
    }

    public Single<Thread> createThread(String name, List<User> users, int type, String entityID) {
        return createThread(name, users, type, entityID, ChatSDK.config().reuseDeleted1to1Threads);
    }

    public Single<Thread> createThread(String name, List<User> users, int type, String entityID, boolean reuseDeletedThreads) {
        return Single.create((SingleOnSubscribe<Thread>) e -> {

            // If the entity ID is set, see if the thread exists and return it if it does
            // TODO: Check this - what if for some reason the user isn't a member of this thread?
            if (entityID != null) {
                Thread t = ChatSDK.db().fetchThreadWithEntityID(entityID);
                if (t != null) {
                    e.onSuccess(t);
                    return;
                }
            }

            User currentUser = ChatSDK.currentUser();

            if (!users.contains(currentUser)) {
                users.add(currentUser);
            }


            if (users.size() == 2 && (type == ThreadType.None || ThreadType.is(type, ThreadType.Private1to1))) {

                User otherUser = null;
                Thread jointThread = null;

                for (User user : users) {
                    if (!user.equals(currentUser)) {
                        otherUser = user;
                        break;
                    }
                }

                // Check to see if a thread already exists with these
                // two users
                for(Thread thread : getThreads(ThreadType.Private1to1, reuseDeletedThreads, true)) {
                    if(thread.getUsers().size() == 2 &&
                            thread.containsUser(currentUser) &&
                            thread.containsUser(otherUser))
                    {
                        jointThread = thread;
                        break;
                    }
                }

                if(jointThread != null) {
                    jointThread.setDeleted(false);
                    DaoCore.updateEntity(jointThread);
                    e.onSuccess(jointThread);
                    return;
                }
            }

            final Thread thread = DaoCore.getEntityForClass(Thread.class);
            DaoCore.createEntity(thread);
            thread.setEntityID(entityID);
            thread.setCreator(currentUser);
            thread.setCreatorEntityId(currentUser.getEntityID());
            thread.setCreationDate(new Date());
            thread.setName(name);

            if (type != -1) {
                thread.setType(type);
            } else {
                thread.setType(users.size() == 2 ? ThreadType.Private1to1 : ThreadType.PrivateGroup);
            }

            // Save the thread to the database.
            e.onSuccess(thread);

        }).flatMap((Function<Thread, SingleSource<? extends Thread>>) thread -> Single.create(e -> {
            if (thread.getEntityID() == null) {
                ThreadWrapper wrapper = new ThreadWrapper(thread);

                wrapper.push().concatWith(addUsersToThread(thread, users)).subscribe(() -> e.onSuccess(thread), e::onError);

            } else {
                e.onSuccess(thread);
            }
        })).doOnSuccess(thread -> thread.addUsers(users)).subscribeOn(Schedulers.single());
    }

    public Completable deleteThread(Thread thread) {
        return deleteThreadWithEntityID(thread.getEntityID());
    }

    public Completable deleteThreadWithEntityID(final String entityID) {
        return Single.create((SingleOnSubscribe<Thread>) e -> {
            final Thread thread = DaoCore.fetchEntityWithEntityID(Thread.class, entityID);
            e.onSuccess(thread);
        }).flatMapCompletable(thread -> new ThreadWrapper(thread).deleteThread()).subscribeOn(Schedulers.single());
    }

    protected void pushForMessage(final Message message) {
        if (ChatSDK.push() != null && message.getThread().typeIs(ThreadType.Private)) {
            HashMap<String, Object> data = ChatSDK.push().pushDataForMessage(message);
            ChatSDK.push().sendPushNotification(data);
        }
    }

    public Completable deleteMessage(Message message) {
        return new MessageWrapper(message).delete();
    }

    public Completable leaveThread(Thread thread) {
        return null;
    }

    public Completable joinThread(Thread thread) {
        return null;
    }

    @Override
    public Observable<Boolean> isGroupAdmin(Thread thread) {
        return new ThreadWrapper(thread).isGroupAdmin();
    }


    @Override
    public Observable<Boolean> hasAudioAccess(Thread thread) {
        return new ThreadWrapper(thread).hasShareAudioAccess();
    }
}
