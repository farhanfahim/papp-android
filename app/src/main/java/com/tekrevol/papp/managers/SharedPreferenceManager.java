package com.tekrevol.papp.managers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.tekrevol.papp.activities.BaseActivity;
import com.tekrevol.papp.activities.MainActivity;
import com.tekrevol.papp.constatnts.AppConstants;
import com.tekrevol.papp.models.receiving_model.UserModel;

import co.chatsdk.core.session.ChatSDK;
import co.chatsdk.core.utils.DisposableList;
import io.reactivex.android.schedulers.AndroidSchedulers;

import static com.tekrevol.papp.constatnts.AppConstants.DEPENDENT_ROLE;
import static com.tekrevol.papp.constatnts.AppConstants.PARENT_ROLE;

/**
 * Class that can be extended to make available simple preference
 * setter/getters.
 * <p>
 * Should be extended to provide Facades.
 */
public class SharedPreferenceManager {
    private static SharedPreferences pref;
    private static SharedPreferenceManager factory;
    public final static String PREFS_NAME = "papp_pref";
    private Context context;

    public static SharedPreferenceManager getInstance(Context context) {
        if (pref == null)
            pref = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        if (factory == null)
            factory = new SharedPreferenceManager();


        factory.context = context;

        return factory;
    }

    public void clearDB() {
        // Save Registered Device Data

        String firebaseToken = getString(AppConstants.KEY_FIREBASE_TOKEN);

        DisposableList disposableList = new DisposableList();

//        disposableList.add();


        ChatSDK.auth().logout()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                            Log.d("CLEAR DB: ", "clearDB: ");
                            logoutProcess(firebaseToken, disposableList);
                        }
                        ,
                        throwable -> {
                            ChatSDK.logError(throwable);
                            logoutProcess(firebaseToken, disposableList);
                        }
                );


    }

    private void logoutProcess(String firebaseToken, DisposableList disposableList) {
        pref.edit().clear().commit();
        ChatSDK.shared().getPreferences().edit().clear().commit();
        ChatSDK.db().deleteAll();
        putValue(AppConstants.KEY_FIREBASE_TOKEN, firebaseToken);
        clearAllActivitiesExceptThis(MainActivity.class);
    }


    public void clearAllActivitiesExceptThis(Class<?> cls) {
        Intent intents = new Intent(context, cls);
        intents.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intents);
        if (context instanceof BaseActivity) {
            ((BaseActivity) context).finish();
        }
    }


    public void clearKey(String key) {
        pref.edit().remove(key).commit();
    }


    public void putValue(String key, Object value) {
        if (value instanceof Boolean)
            pref.edit().putBoolean(key, (Boolean) value).commit();
        else if (value instanceof String)
            pref.edit().putString(key, (String) value).commit();
        else if (value instanceof Integer)
            pref.edit().putInt(key, (int) value).commit();
        else if (value instanceof Long)
            pref.edit().putLong(key, (long) value).commit();

//        else
//            pref.edit().putString(key, (String) value).apply();
    }

    public int getInt(String key) {
        return pref.getInt(key, -1);
    }

    public long getLong(String key) {
        return pref.getLong(key, -1);
    }

    public String getString(String key) {
        return pref.getString(key, "");
    }

    public boolean getBoolean(String key) {
        return pref.getBoolean(key, false);
    }

    public void putObject(String key, Object object) {
        if (object == null || object.equals("")) {
            pref.edit().putString(key, (String) object).commit();
            return;
        }

        pref.edit().putString(key, new Gson().toJson(object)).commit();
    }

    public void removeObject(String key) {
        pref.edit().remove(key).commit();
    }

    public <T> T getObject(String key, Class<T> a) {
        String json = pref.getString(key, null);
        if (json == null) {
            return null;
        } else {
            try {
                return new Gson().fromJson(json, a);
            } catch (Exception e) {
                throw new IllegalArgumentException("Object stored with key "
                        + key + " is instance of other class");
            }
        }
    }

    public boolean hasValue(String key) {
        return pref.contains(key);
    }

    public UserModel getCurrentUser() {
        return getObject(AppConstants.KEY_CURRENT_USER_MODEL, UserModel.class);
    }

    public boolean isMentor() {
        if (getCurrentUser() == null || getCurrentUser().getRoles_csv() == 0) {
            return false;
        }
        return getCurrentUser().getRoles_csv() == AppConstants.MENTOR_ROLE;
    }

    public boolean isDependent() {
        if (getCurrentUser() == null || getCurrentUser().getRoles_csv() == 0) {
            return false;
        }
        return getCurrentUser().getRoles_csv() == DEPENDENT_ROLE;
    }

    public boolean isParent() {
        if (getCurrentUser() == null || getCurrentUser().getRoles_csv() == 0) {
            return false;
        }
        return getCurrentUser().getRoles_csv() == PARENT_ROLE;
    }

    public boolean isForcedRestart() {
        return getBoolean(AppConstants.FORCED_RESTART);
    }

    public void setForcedRestart(boolean isForcedRestart) {
        putValue(AppConstants.FORCED_RESTART, isForcedRestart);
    }

    public void removePreference(Context context,
                                 String key) {

        SharedPreferences preferences = context.getSharedPreferences(
                PREFS_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.remove(key);
        editor.commit();
    }

}
