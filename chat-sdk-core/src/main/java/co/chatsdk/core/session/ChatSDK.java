package co.chatsdk.core.session;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import co.chatsdk.core.base.LocationProvider;
import co.chatsdk.core.base.BaseNetworkAdapter;
import co.chatsdk.core.dao.DaoCore;
import co.chatsdk.core.dao.Message;
import co.chatsdk.core.dao.Thread;
import co.chatsdk.core.dao.User;
import co.chatsdk.core.error.ChatSDKException;
import co.chatsdk.core.events.EventType;
import co.chatsdk.core.events.NetworkEvent;
import co.chatsdk.core.handlers.AudioMessageHandler;
import co.chatsdk.core.handlers.AuthenticationHandler;
import co.chatsdk.core.handlers.BlockingHandler;
import co.chatsdk.core.handlers.ContactHandler;
import co.chatsdk.core.handlers.CoreHandler;
import co.chatsdk.core.handlers.EncryptionHandler;
import co.chatsdk.core.handlers.EventHandler;
import co.chatsdk.core.handlers.FileMessageHandler;
import co.chatsdk.core.handlers.HookHandler;
import co.chatsdk.core.handlers.ImageMessageHandler;
import co.chatsdk.core.handlers.LastOnlineHandler;
import co.chatsdk.core.handlers.LocationMessageHandler;
import co.chatsdk.core.handlers.ProfilePicturesHandler;
import co.chatsdk.core.handlers.PublicThreadHandler;
import co.chatsdk.core.handlers.PushHandler;
import co.chatsdk.core.handlers.ReadReceiptHandler;
import co.chatsdk.core.handlers.SearchHandler;
import co.chatsdk.core.handlers.SocialLoginHandler;
import co.chatsdk.core.handlers.StickerMessageHandler;
import co.chatsdk.core.handlers.ThreadHandler;
import co.chatsdk.core.handlers.TypingIndicatorHandler;
import co.chatsdk.core.handlers.UploadHandler;
import co.chatsdk.core.handlers.VideoMessageHandler;
import co.chatsdk.core.interfaces.InterfaceAdapter;
import co.chatsdk.core.interfaces.LocalNotificationHandler;
import co.chatsdk.core.interfaces.ThreadType;
import co.chatsdk.core.types.MessageType;
import co.chatsdk.core.types.ReadStatus;
import co.chatsdk.core.utils.AppBackgroundMonitor;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

/**
 * Created by ben on 9/5/17.
 */

public class ChatSDK {


    private static final ChatSDK instance = new ChatSDK();
    protected WeakReference<Context> context;
    public Configuration config;
    public Disposable localNotificationDisposable;

    protected InterfaceAdapter interfaceAdapter;
    protected StorageManager storageManager;
    protected BaseNetworkAdapter networkAdapter;

    protected LocationProvider locationProvider;

    public final static String PREFS_NAME = "papp_pref";

    protected ChatSDK () {
    }

    private void setContext (Context context) {
        this.context = new WeakReference<>(context);
    }


    public static ChatSDK initialize (Configuration config) throws ChatSDKException {
        return initialize(config, null, null);
    }

    public static ChatSDK initialize (Configuration config, BaseNetworkAdapter networkAdapter, InterfaceAdapter interfaceAdapter) throws ChatSDKException {
        shared().setContext(config.context.get());
        shared().config = config;

        DaoCore.init(shared().context());

        shared().storageManager = new StorageManager();

        if(interfaceAdapter != null) {
            shared().interfaceAdapter = interfaceAdapter;
        }
//        else {
//            shared().activateModule("UserInterfaceModule", "activate", new MethodArgument(Context.class, shared().context()));
//        }

        if (networkAdapter != null) {
            shared().networkAdapter = networkAdapter;
        }
//        else {
//            shared().activateModule("FirebaseModule", "activate");
//        }

        shared().locationProvider = new LocationProvider();

        shared().handleLocalNotifications();
        // Monitor the app so if it goes into the background we know
        AppBackgroundMonitor.shared().setEnabled(true);

        if (config().debug) {
            Timber.plant(new Timber.DebugTree());
        }
      
        return shared();
    }

    public void activateModule (String moduleName, String methodName, MethodArgument... arguments) throws ChatSDKException {
        try {
            ArrayList<Class<?>> classes = new ArrayList<>();
            ArrayList<Object> values = new ArrayList<>();

            for(MethodArgument a : arguments) {
                classes.add(a.type);
                values.add(a.value);
            }

            Class<?> interfaceModule = Class.forName(moduleName);
            Method method = interfaceModule.getMethod(methodName, classes.toArray(new Class<?>[0]));
            method.invoke(null, values.toArray(new Object[0]));
        }
        catch (ClassNotFoundException e) {
            throw new ChatSDKException("Module: " + moduleName + "Not found");
        }
        catch (NoSuchMethodException e) {
            throw new ChatSDKException("Activate method not found for module");
        }
        catch (IllegalAccessException e) {
            throw new ChatSDKException("Activate method not found for module");
        }
        catch (InvocationTargetException e) {
            throw new ChatSDKException("Activate method not found for module");
        }
    }

    public void handleLocalNotifications () {

        if (localNotificationDisposable != null) {
            localNotificationDisposable.dispose();
        }

        // TODO: Check this
        localNotificationDisposable = ChatSDK.events().sourceOnMain()
                .filter(NetworkEvent.filterType(EventType.MessageAdded))
                .subscribe(networkEvent -> {
                    Message message = networkEvent.message;
                    Thread thread = networkEvent.thread;
                    if(message != null) {
                        if (thread.typeIs(ThreadType.Private) || (thread.typeIs(ThreadType.Public))) {
                            if(ChatSDK.config().pushNotificationsForPublicChatRoomsEnabled) {
                                if(!message.getSender().isMe()) {
                                    ReadStatus status = message.readStatusForUser(ChatSDK.currentUser());
                                    if (!message.isRead() && !status.is(ReadStatus.delivered())) {
                                        // Only show the alert if we'recyclerView not on the private threads tab
                                        ChatSDK.ui().notificationDisplayHandler().createMessageNotification(message);
                                    }
                                }

                                if(!message.getSender().isMe() && message.getType() == MessageType.Audio) {
                                    if(message.getMetaValues().size() > 0) {
                                        String path = message.getMetaValues().get(0).getValue();
                                        String fileName = new File(path).getName();
                                        if(fileName.contains("?")) {
                                            downloadFile(path, fileName.substring(0,  fileName.indexOf("?")));
                                        } else {
                                            downloadFile(path, fileName);
                                        }
                                    }
                                }
                            }

                        }
                    }
                });
    }

    private void downloadFile(String path, String name) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl(path);

       File rootPath = new File(Environment.getExternalStorageDirectory(), "Chat_SDK");
       File audioFolder = new File(rootPath, "Audio");

        if(!audioFolder.exists()) {
            audioFolder.mkdirs();
        }

        final File localFile = new File(audioFolder,name);

        storageRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                Log.e("firebase ",";local tem file created  created " +localFile.toString());
                //  updateDb(timestamp,localFile.toString(),position);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.e("firebase ",";local tem file not created  created " +exception.toString());
            }
        });
    }

    public void setLocalNotificationHandler (LocalNotificationHandler handler) {

    }

    public static ChatSDK shared () {
        return instance;
    }

    public SharedPreferences getPreferences () {
        return context.get().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public Context context () {
        return context.get();
    }

    public static Configuration config () {
        return shared().config;
    }

    public static void logError (Throwable t) {
        logError(new Exception(t));
    }

    public static void logError (Exception e) {
        if (config().debug) {
            e.printStackTrace();
        }
        if (config().crashHandler != null) {
            config().crashHandler.log(e);
        }
    }

    /**
     * Shortcut to return the interface adapter
     * @return InterfaceAdapter
     */
    public static InterfaceAdapter ui () {
        return shared().interfaceAdapter;
    }

    public void setInterfaceAdapter (InterfaceAdapter interfaceAdapter) {
        shared().interfaceAdapter = interfaceAdapter;
    }

    public void setNetworkAdapter (BaseNetworkAdapter networkAdapter) {
        shared().networkAdapter = networkAdapter;
    }

    public static CoreHandler core () {
        return a().core;
    }

    public static AuthenticationHandler auth () {
        return a().auth;
    }

    public static ThreadHandler thread () {
        return a().thread;
    }

    public static PublicThreadHandler publicThread () {
        return a().publicThread;
    }

    public static PushHandler push () {
        return a().push;
    }

    public static UploadHandler upload () {
        return a().upload;
    }

    public static EventHandler events () {
        return a().events;
    }

    public static User currentUser () {
        return ChatSDK.core().currentBaseUserModel();
    }

    public static String currentUserID() {
        return ChatSDK.core().currentBaseUserModel().getEntityID();
    }

    public static SearchHandler search () {
        return a().search;
    }

    public static ContactHandler contact () {
        return a().contact;
    }

    public static BlockingHandler blocking () {
        return a().blocking;
    }

    public static EncryptionHandler encryption () { return a().encryption; }

    public static LastOnlineHandler lastOnline () {
        return a().lastOnline;
    }

    public static AudioMessageHandler audioMessage () {
        return a().audioMessage;
    }

    public static VideoMessageHandler videoMessage () {
        return a().videoMessage;
    }

    public static HookHandler hook () {
        return a().hook;
    }

    public static SocialLoginHandler socialLogin () {
        return a().socialLogin;
    }

    public static StickerMessageHandler stickerMessage () {
        return a().stickerMessage;
    }

    public static FileMessageHandler fileMessage () {
        return a().fileMessage;
    }

    public static ImageMessageHandler imageMessage () {
        return a().imageMessage;
    }

    public static LocationMessageHandler locationMessage () {
        return a().locationMessage;
    }

    public static ReadReceiptHandler readReceipts () {
        return a().readReceipts;
    }

    public static TypingIndicatorHandler typingIndicator () {
        return a().typingIndicator;
    }

    public static ProfilePicturesHandler profilePictures () {
        return a().profilePictures;
    }

    public static LocationProvider locationProvider () {
        return shared().locationProvider;
    }

    public static BaseNetworkAdapter a() {
        return shared().networkAdapter;
    }

    public static StorageManager db () {
        return shared().storageManager;
    }

}
