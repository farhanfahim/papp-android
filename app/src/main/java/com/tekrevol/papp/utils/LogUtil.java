package com.tekrevol.papp.utils;

import android.util.Log;

import com.tekrevol.papp.BaseApplication;


public class LogUtil {
    public static void i(String tag, String msg) {
        if (BaseApplication.LOG_FLAG) {
            Log.i(tag, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (BaseApplication.LOG_FLAG) {
            Log.d(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (BaseApplication.LOG_FLAG) {
            Log.e(tag, msg);
        }
    }
}
