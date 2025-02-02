package com.tekrevol.papp;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.ComponentCallbacks2;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;

import androidx.multidex.MultiDexApplication;

import com.crashlytics.android.Crashlytics;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.tekrevol.papp.activities.MainActivity;
import com.tekrevol.papp.libraries.imageloader.CustomImageDownaloder;

import co.chatsdk.core.error.ChatSDKException;
import co.chatsdk.core.session.ChatSDK;
import co.chatsdk.core.session.Configuration;
import co.chatsdk.firebase.FirebaseNetworkAdapter;
import co.chatsdk.firebase.file_storage.FirebaseFileStorageModule;
import co.chatsdk.firebase.push.FirebasePushModule;
import co.chatsdk.profile.pictures.ProfilePicturesModule;
import co.chatsdk.ui.manager.BaseInterfaceAdapter;
import io.fabric.sdk.android.Fabric;
import io.reactivex.subjects.PublishSubject;
import io.realm.Realm;

/**
 * Created by khanhamza on 09-Mar-17.
 */

public class BaseApplication extends MultiDexApplication implements Application.ActivityLifecycleCallbacks {
    public static final boolean LOG_FLAG = true;
    private static PublishSubject<Pair> publishSubject = PublishSubject.create();
    private static boolean isInBackground = true;
    private static String applicationName;
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        configImageLoader(this);

        mContext = this;
        applicationName = getApplicationName(this);
        // TODO: 12/20/2017 Enable it to use Realm
//        initRealm();
        // TODO: 12/20/2017 Enable it to use Calligraphy font library
//        configureCalligraphyLibrary();

//        final Fabric fabric = new Fabric.Builder(this)
//                .kits(new Crashlytics())
//                .debuggable(true)
//                .build();

        // TODO: Enable Crash Lytics and Never Crash feature before releasing the app
        Fabric.with(this, new Crashlytics());
//        neverCrash();


        chatSDKInit();
    }



    public static void chatSDKInit(){

        try {
            Configuration.Builder config = new Configuration.Builder(mContext);

            config.firebaseRootPath("19_04");
            config.googleMaps(mContext.getString(R.string.google_maps_key));
            config.publicRoomCreationEnabled(true);
            config.pushNotificationSound("default");
            config.pushNotificationsForPublicChatRoomsEnabled(true);

//            config.twitterLogin("Kqprq5b6bVeEfcMAGoHzUmB3I", "hPd9HCt3PLnifQFrGHJWi6pSZ5jF7kcHKXuoqB8GJpSDAlVcLq");
//            config.googleLogin("1088435112418-e3t77t8jl2ucs8efeqs72o696in8soui.apps.googleusercontent.com");

            // For the demo version of the client exire rooms after 24 hours
            config.publicChatRoomLifetimeMinutes(60 * 24);



            ChatSDK.initialize(config.build(), new FirebaseNetworkAdapter(mContext), new BaseInterfaceAdapter(mContext));

            FirebaseFileStorageModule.activate();
            FirebasePushModule.activate();
            ProfilePicturesModule.activate();

            // Uncomment this to enable Firebase UI
            // FirebaseUIModule.activate(EmailAuthProvider.PROVIDER_ID, PhoneAuthProvider.PROVIDER_ID);

        }
        catch (ChatSDKException e) {
            e.printStackTrace();
        }
    }

    public static Context getContext() {
        return mContext;
    }

    private void initRealm() {
        Realm.init(getApplicationContext());
    }

    private String getApplicationName(Context context) {
        ApplicationInfo applicationInfo = context.getApplicationInfo();
        int stringId = applicationInfo.labelRes;
        return stringId == 0 ? applicationInfo.nonLocalizedLabel.toString() : context.getString(stringId);
    }

//    private void configureCalligraphyLibrary() {
//        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
//                .setDefaultFontPath("fonts/SanFranciscoRegular.ttf")
//                .setFontAttrId(R.attr.fontPath)
//                .build()
//        );
//
//    }

    private void configImageLoader(Context context) {
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder().cacheOnDisk(true).cacheInMemory(true).imageScaleType(ImageScaleType.EXACTLY).displayer(new FadeInBitmapDisplayer(300)).build();
        // Create global configuration and initialize ImageLoaderHelper with this config
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .imageDownloader(new CustomImageDownaloder(context))
                .defaultDisplayImageOptions(defaultOptions)
                .memoryCache(new WeakMemoryCache())
                .memoryCacheSize(2 * 1024 * 1024)
                .build();
        ImageLoader.getInstance().init(config);
    }

    /**
     * A method to perform a restart if crash appear, won't show crash to user and send the report to the Fabric
     */
    private void neverCrash() {
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            public void uncaughtException(Thread paramThread, final Throwable paramThrowable) {
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
//                        SharedPreferenceManager.getInstance().setForcedRestart(true);
                        Crashlytics.logException(paramThrowable);
                    }
                });
                t.start();
                try {
                    t.join();
                } catch (InterruptedException e) {
                    Log.e("CRASH", "uncaughtException: " + e.getMessage());
                }

//                Log.d("Crash BaseApplication", "uncaughtException: " + SharedPreferenceManager.getInstance().isForcedRestart());
                Log.e("Error" + Thread.currentThread().getStackTrace()[2], paramThrowable.getLocalizedMessage());

                PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 293, new Intent(getApplicationContext(), MainActivity.class), 0);
                AlarmManager mgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 30, pendingIntent);
                System.exit(1);
            }
        });
    }


    public static PublishSubject<Pair> getPublishSubject() {
        return publishSubject;
    }

    public static boolean isInBackground() {
        return isInBackground;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
    }

    @Override
    public void onActivityStarted(Activity activity) {
        Log.e("abc", "onActivityStarted " + activity.toString() + "");
        if (isInBackground) {
            isInBackground = false;
        }

    }

    @Override
    public void onActivityResumed(Activity activity) {
        if (isInBackground) {
            isInBackground = false;
        }
    }

    @Override
    public void onActivityPaused(Activity activity) {
        Log.e("abc", "onActivityPaused " + activity.toString());
    }

    @Override
    public void onActivityStopped(Activity activity) {
        Log.e("abc", "onActivityStopped " + activity.toString());

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        Log.e("abc", "onActivityDestroyed " + activity.toString());

    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        Log.e("abc", "onTrimMemory :- " + level);
        if (level == ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN) {
            isInBackground = true;

        }
    }

    public static String getApplicationName() {
        return applicationName;
    }


}

