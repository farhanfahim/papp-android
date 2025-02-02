/**
 * Copyright 2016 Google Inc. All Rights Reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tekrevol.papp.firebase;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.tekrevol.papp.R;
import com.tekrevol.papp.activities.CallActivity;
import com.tekrevol.papp.activities.MainActivity;
import com.tekrevol.papp.constatnts.AppConstants;
import com.tekrevol.papp.managers.SharedPreferenceManager;
import com.tekrevol.papp.managers.retrofit.GsonFactory;
import com.tekrevol.papp.models.receiving_model.GeneralPushReceivingModel;
import com.tekrevol.papp.models.receiving_model.OpenTokSessionRecModel;

import java.util.List;

import co.chatsdk.core.session.ChatSDK;

import static com.tekrevol.papp.constatnts.AppConstants.KEY_FIREBASE_TOKEN;
import static com.tekrevol.papp.constatnts.AppConstants.KEY_FIREBASE_TOKEN_UPDATED;


public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private final String TAG = "Fcm";
    private final String ACTION_TYPE_OPEN_TOK = "opentok_session";
    private final String ACTION_TYPE_OFFLINE_CHAT = "offline_message";


    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);

        SharedPreferenceManager.getInstance(this).putValue(KEY_FIREBASE_TOKEN, s);
        SharedPreferenceManager.getInstance(this).putValue(KEY_FIREBASE_TOKEN_UPDATED, true);

        Log.d(TAG, "onNewToken: " + s);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.e(TAG, "From: " + remoteMessage.getFrom());

        if (SharedPreferenceManager.getInstance(this).getCurrentUser() == null) {
            return;
        }


        // Check if message contains a notification payload.
        if (remoteMessage.getData() != null && remoteMessage.getData().get("extra_payload") != null) {

            GeneralPushReceivingModel generalPushReceivingModel = GsonFactory.getSimpleGson().fromJson(remoteMessage.getData().get("extra_payload"), GeneralPushReceivingModel.class);

            if (generalPushReceivingModel != null && generalPushReceivingModel.getActionType().equalsIgnoreCase(ACTION_TYPE_OPEN_TOK)) {
                OpenTokSessionRecModel openTokSessionRecModel = GsonFactory.getSimpleGson().fromJson(remoteMessage.getData().get("extra_payload"), OpenTokSessionRecModel.class);

                openTokSessionRecModel.setCaller(false);
                openActivity(CallActivity.class, openTokSessionRecModel.toString());
            } else {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                handleNotification("PAPP", generalPushReceivingModel.getMessage() + "", intent);
            }

        } else if (remoteMessage.getData() != null && remoteMessage.getData().get("action_type") != null) {


            if (remoteMessage.getData().get("action_type").equals(ACTION_TYPE_OFFLINE_CHAT)) {
                String threadId = remoteMessage.getData().get("thread_id");
                String msgId = remoteMessage.getData().get("msg_id");
                String text = remoteMessage.getData().get("text");
                String username = remoteMessage.getData().get("username");

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                intent.putExtra("thread_id", threadId);
//                intent.putExtra("msg_id", msgId);

                handleNotification(username, text, intent);

            }

        } else {

            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            handleNotification("PAPP", "ACTION TYPE UNDEFINED", intent);
        }


        // Check if message contains a data payload.
//        if (remoteMessage.getData().size() > 0) {
//            Log.e(TAG, "UserData Payload: " + remoteMessage.getData().toString());
//
//            try {
//                JSONObject json = new JSONObject(remoteMessage.getData().toString());
//                handleDataMessage(json);
//            } catch (Exception e) {
//                Log.e(TAG, "Exception: " + e.getMessage());
//            }
//        }
    }

    public void broadcastIntent() {
        Intent intent = new Intent();
        intent.setAction(getString(R.string.pushNotification));
        getApplicationContext().sendBroadcast(intent);
        Log.d("ServiceScoring ", "Broadcast");
    }

    public void broadcastIntent2() {
        Intent intent = new Intent();
        intent.setAction(getString(R.string.pushNotificationMsg));
        getApplicationContext().sendBroadcast(intent);
        Log.d("ServiceScoring ", "Broadcast");
    }

    private void handleNotification(String title, String message, Intent intentToOpen) {
        broadcastIntent();

        //if (remoteMessage.getData().size() > 0) {
        // GlobalHelperNormal.getInstance().vibrateAlert(this, 1000);
        playNotificationSound();
        handleDataMessage(title, message, intentToOpen);
        // }
    }

    private void playNotificationSound() {
        try {
            MediaPlayer player = MediaPlayer.create(this, Settings.System.DEFAULT_NOTIFICATION_URI);
            player.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleDataMessage(String title, String message, Intent intentToOpen) {
        Bundle mBundle = new Bundle();
        //  mBundle.putString(getString(R.string.custom), remoteMessage.getData().values().toArray()[0].toString());
     /*   mBundle.putString(getString(R.string.gcm_notification_badge),
                remoteMessage.toIntent().getStringExtra(getString(R.string.gcm_notification_badge)));*/

        intentToOpen.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intentToOpen.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        showNotification(getApplicationContext(),
                title, message,
                intentToOpen);
    }


    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param messageBody FCM message body received.
     */

    private int NOTIFICATION_ID = 0;

    private void sendNotification(String title, String messageBody) {

      /*  Intent intent = new Intent(getApplicationContext(), RedeemHistoryActivity.class);
        Bundle b = new Bundle();
        b.putInt(getApplicationContext().getString(R.string.bottom_menu_pos), 2);
        intent.putExtras(b);*/
       /* intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);*/

        Intent intent = new Intent();
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_ONE_SHOT);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.notification_logo)
                .setContentTitle(title)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);


        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (NOTIFICATION_ID > 0) {
            notificationBuilder.setNumber(-1);
        }
        notificationManager.notify(++NOTIFICATION_ID, notificationBuilder.build());

    }
    //notification close


    private void sendPostDetailNotification(String title, String messageBody, int postId) {
/*

        Intent intent = new Intent(getApplicationContext(), FeedDetailActivity.class);
        Bundle b = new Bundle();
        b.putBoolean(getResources().getString(R.string.key_notification_screen), false);
        b.putBoolean(getResources().getString(R.string.key_add_friend_fcm), true);
        b.putString(getResources().getString(R.string.key_fcm_user_id), "" + postId);
        b.putInt(getResources().getString(R.string.key_user_request), -1);
        intent.putExtras(b);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 */
        /* Request code *//*
, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_ONE_SHOT);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.notification_logo)
                .setContentTitle(title)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);


        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (NOTIFICATION_ID > 0) {
            notificationBuilder.setNumber(-1);
        }
        notificationManager.notify(++NOTIFICATION_ID, notificationBuilder.build());
*/

    }


    //setting badge start
    public void setBadge(Context context, int count) {
        String launcherClassName = getLauncherClassName(context);
        if (launcherClassName == null) {
            return;
        }
        Intent intent = new Intent("android.intent.action.BADGE_COUNT_UPDATE");
        intent.putExtra("badge_count", count);
        intent.putExtra("badge_count_package_name", context.getPackageName());
        intent.putExtra("badge_count_class_name", launcherClassName);
        context.sendBroadcast(intent);
    }
    //setting badge close

    //launcher class start
    public String getLauncherClassName(Context context) {

        PackageManager pm = context.getPackageManager();

        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);

        List<ResolveInfo> resolveInfos = pm.queryIntentActivities(intent, 0);
        for (ResolveInfo resolveInfo : resolveInfos) {
            String pkgName = resolveInfo.activityInfo.applicationInfo.packageName;
            if (pkgName.equalsIgnoreCase(context.getPackageName())) {
                String className = resolveInfo.activityInfo.name;
                return className;
            }
        }
        return null;
    }
    //launcher class close


    public void showNotification(Context context, String title, String message, Intent intent) {
        SharedPreferenceManager sharedPreferenceManager = SharedPreferenceManager.getInstance(context);

        PendingIntent pendingIntent = PendingIntent.getActivity(context,
                sharedPreferenceManager.getInt(context.getString(R.string.notification_req_code)),
                intent, PendingIntent.FLAG_ONE_SHOT);
        String CHANNEL_ID = "papp_channel";// The id of the channel.
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.mipmap.notification_logo)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = context.getString(R.string.app_name);// The user-visible name of the channel.
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            notificationManager.createNotificationChannel(mChannel);
        }
        notificationManager.notify(sharedPreferenceManager.getInt(context.getString(R.string.notification_req_code)), notificationBuilder.build());
        if (sharedPreferenceManager.getInt(context.getString(R.string.notification_req_code)) <= 1000) {
            sharedPreferenceManager.putValue(context.getString(R.string.notification_req_code),
                    sharedPreferenceManager.getInt(context.getString(R.string.notification_req_code)) + 1);
        } else {
            sharedPreferenceManager.putValue(context.getString(R.string.notification_req_code), 0);
        }
        Log.d("showNotification", "showNotification: " + sharedPreferenceManager.getInt(context.getString(R.string.notification_req_code)));
    }


    public void openActivity(Class<?> tClass, String object) {
        Intent i = new Intent(this, tClass);
        i.putExtra(AppConstants.JSON_STRING_KEY, object);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

}
