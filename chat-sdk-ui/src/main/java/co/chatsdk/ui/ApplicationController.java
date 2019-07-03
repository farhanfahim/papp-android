package co.chatsdk.ui;
import android.app.Application;
import android.view.View;

import androidx.multidex.MultiDexApplication;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class ApplicationController extends MultiDexApplication {
    @Override
    public void onCreate() {
        super.onCreate();

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Roboto-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());

    }

}
