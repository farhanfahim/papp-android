package com.tekrevol.papp.activities;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.annotation.Nullable;

import com.google.android.material.navigation.NavigationView;
import com.tekrevol.papp.R;
import com.tekrevol.papp.constatnts.AppConstants;
import com.tekrevol.papp.fragments.VideoCallFragment;
import com.tekrevol.papp.helperclasses.RunTimePermissions;
import com.tekrevol.papp.helperclasses.StringHelper;
import com.tekrevol.papp.helperclasses.ui.helper.UIHelper;
import com.tekrevol.papp.models.receiving_model.OpenTokSessionRecModel;


public class CallActivity extends BaseActivity {

    private NavigationView navigationView;
    //    public String API_KEY = WebServiceConstants.API_KEY;
//    public String SESSION_ID = "2_MX40NjM1NDMxMn5-MTU2MTU0NTg2MjYxNn5WYlFjRkkydXNQSWwyM0wrUXVHSWJkUEp-fg";
//    public String TOKEN = "T1==cGFydG5lcl9pZD00NjM1NDMxMiZzaWc9NDMxMGIwOGI2YTRhMmQ1ZTUzZmMwYmU3MjNiOWZlODVlZTQxMzUwMDpzZXNzaW9uX2lkPTJfTVg0ME5qTTFORE14TW41LU1UVTJNVFUwTlRnMk1qWXhObjVXWWxGalJra3lkWE5RU1d3eU0wd3JVWFZIU1dKa1VFcC1mZyZjcmVhdGVfdGltZT0xNTYxNTQ1ODkxJm5vbmNlPTAuMDgwMTQ2MzMxNDA2NjgzOTImcm9sZT1wdWJsaXNoZXImZXhwaXJlX3RpbWU9MTU2MjE1MDY5MCZpbml0aWFsX2xheW91dF9jbGFzc19saXN0PQ==";
    public final String LOG_TAG = MainActivity.class.getSimpleName();
    public final int RC_SETTINGS_SCREEN_PERM = 123;
    public final int RC_VIDEO_APP_PERM = 124;

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        RunTimePermissions.verifyStoragePermissions(this);
        String stringExtra = getIntent().getStringExtra(AppConstants.JSON_STRING_KEY);

        if (StringHelper.isNullOrEmpty(stringExtra)) {
            return;
        }

        OpenTokSessionRecModel openTokSessionRecModel = getGson().fromJson(stringExtra, OpenTokSessionRecModel.class);


        initFragments(openTokSessionRecModel);

        navigationView = findViewById(R.id.nav_view);
        navigationView.getBackground().setColorFilter(0x80000000, PorterDuff.Mode.MULTIPLY);
    }

    @Override
    protected int getViewId() {
        return R.layout.activity_chat;
    }

    @Override
    protected int getTitlebarLayoutId() {
        return R.id.titlebar;
    }


    @Override
    protected int getDrawerLayoutId() {
        return R.id.drawer_layout;
    }

    @Override
    protected int getDockableFragmentId() {
        return R.id.contMain;
    }

    @Override
    protected int getDrawerFragmentId() {
        return R.id.contDrawer;
    }

    private void initFragments(OpenTokSessionRecModel openTokSessionRecModel) {
        if (openTokSessionRecModel.getSessionType().equals(String.valueOf(AppConstants.SESSION_TYPE_VIDEO))) {
            addDockableFragment(VideoCallFragment.newInstance(openTokSessionRecModel), false);
        } else {
            addDockableFragment(VideoCallFragment.newInstance(openTokSessionRecModel), false);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onBackPressed() {
        /**
         * Show Close app popup if no or single fragment is in stack. otherwise check if drawer is open. Close it..
         */

//        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
//
//            if (drawerLayout.isDrawerOpen(Gravity.START)) {
//                drawerLayout.closeDrawer(Gravity.START);
//            } else {
//                super.onBackPressed();
//                List<Fragment> fragments = getSupportFragmentManager().getFragments();
//                BaseFragment fragment = (BaseFragment) fragments.get(fragments.size() - 1);
//                fragment.setTitlebar(titleBar);
//            }
//        } else {
//            finish();
//        }
    }


}