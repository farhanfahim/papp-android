package com.tekrevol.papp.firebase.chat;

import android.content.Context;

import com.tekrevol.papp.libraries.imageloader.ImageLoaderHelper;
import com.tekrevol.papp.managers.SharedPreferenceManager;
import com.tekrevol.papp.models.receiving_model.UserModel;

import java.util.HashMap;

import co.chatsdk.core.dao.User;
import co.chatsdk.core.hook.HookEvent;
import co.chatsdk.core.session.ChatSDK;
import co.chatsdk.core.utils.CrashReportingCompletableObserver;
import co.chatsdk.firebase.wrappers.UserWrapper;
import io.reactivex.Completable;
import io.reactivex.schedulers.Schedulers;

import static co.chatsdk.core.base.AbstractCoreHandler.login_user_id;

public class FirebaseIntegration {

    private static FirebaseIntegration firebaseIntegration;

    public static FirebaseIntegration getInstance() {

        if(firebaseIntegration == null) {
            firebaseIntegration = new FirebaseIntegration();
        }

        return firebaseIntegration;
    }

    public Completable saveUserDetail(Context context, UserModel userModel) {
        return Completable.create(
                e -> {
                    final UserWrapper userWrapper = UserWrapper.initWithEntityId(userModel.getId() + "");
                    User user = userWrapper.getModel();
                    user.setEntityID(userModel.getId() + "");
                    user.setEmail(userModel.getEmail());
                    user.setName(userModel.getUserDetails().getFullName());
                    user.setAvatarURL(ImageLoaderHelper.getImageURLFromPath(userModel.getUserDetails().getImage()));
                    user.setIsOnline(true);
                    user.setId((long) userModel.getId());

                    SharedPreferenceManager.getInstance(context).putValue(login_user_id, userModel.getId() + "");

                    userWrapper.once().subscribe(()-> {
                        userWrapper.getModel().setAvatarURL(ImageLoaderHelper.getImageURLFromPath(userModel.getUserDetails().getImage()));
                        userWrapper.getModel().setName(userModel.getUserDetails().getFullName());

                        userWrapper.getModel().update();
                        ChatSDK.core().setUserOnline().subscribe(new CrashReportingCompletableObserver());

                        ChatSDK.events().impl_currentUserOn(userWrapper.getModel().getEntityID());
//                        if (ChatSDK.push() != null) {
//                            ChatSDK.push().subscribeToPushChannel(userWrapper.getModel().getPushChannel());
//                        }

                        if (ChatSDK.hook() != null) {
                            HashMap<String, Object> data = new HashMap<>();
                            data.put(HookEvent.User, userWrapper.getModel());
                            ChatSDK.hook().executeHook(HookEvent.DidAuthenticate, data).subscribe(new CrashReportingCompletableObserver());
                        }


                        userWrapper.push().subscribe(e::onComplete, e::onError);
                    }, e::onError);
                }).subscribeOn(Schedulers.single());
    }
}
