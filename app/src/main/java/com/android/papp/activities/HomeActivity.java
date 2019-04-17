package com.android.papp.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.android.papp.R;
import com.android.papp.constatnts.AppConstants;
import com.android.papp.fragments.DashboardCivilianFragment;
import com.android.papp.fragments.DashboardLEAFragment;
import com.android.papp.fragments.RightSideMenuFragment;
import com.android.papp.fragments.abstracts.BaseFragment;
import com.android.papp.libraries.residemenu.ResideMenu;
import com.android.papp.managers.SharedPreferenceManager;
import com.android.papp.utils.utility.Blur;
import com.android.papp.utils.utility.Utils;

import java.util.List;


public class HomeActivity extends BaseActivity {


    NavigationView navigationView;
    FrameLayout contMain;
    RelativeLayout contParentActivityLayout;



    private RightSideMenuFragment rightSideMenuFragment;
    private ResideMenu resideMenu;

    //For Blurred Background
    private Bitmap mDownScaled;
    private String mBackgroundFilename;
    private Bitmap background;



    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
//        String intentData = getIntent().getStringExtra(AppConstants.JSON_STRING_KEY);


        navigationView = findViewById(R.id.nav_view);
        navigationView.getBackground().setColorFilter(0x80000000, PorterDuff.Mode.MULTIPLY);
        contMain = findViewById(R.id.contMain);
        contParentActivityLayout = findViewById(R.id.contParentActivityLayout);

        setSideMenu(ResideMenu.DIRECTION_RIGHT);

        initFragments("");


    }


    public ResideMenu getResideMenu() {
        return resideMenu;
    }



    public void setSideMenu(int direction) {

        resideMenu = new ResideMenu(this);
        resideMenu.setBackground(R.drawable.img_background_sidebar);
        resideMenu.attachToActivity(HomeActivity.this);
        resideMenu.setScaleValue(0.56f);
        resideMenu.setShadowVisible(false);
        setMenuItemDirection(direction);
    }


    public void setMenuItemDirection(int direction) {

        if (direction == ResideMenu.DIRECTION_RIGHT) {
            rightSideMenuFragment = RightSideMenuFragment.newInstance();
            resideMenu.addMenuItem(rightSideMenuFragment, "RightSideMenuFragment", direction);
        }
    }

    public RightSideMenuFragment getRightSideMenuFragment() {
        return rightSideMenuFragment;
    }


    @Override
    protected int getViewId() {
        return R.layout.activity_home;
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

    private void initFragments(String intentData) {
        if (SharedPreferenceManager.getInstance(this).getBoolean(AppConstants.KEY_IS_LEA)) {
            addDockableFragment(DashboardLEAFragment.newInstance(), false);
        } else {
            addDockableFragment(DashboardCivilianFragment.newInstance(), false);

        }
    }

    public FrameLayout getContMain() {
        return contMain;
    }

    public RelativeLayout getContParentActivityLayout() {
        return contParentActivityLayout;
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {

            if (drawerLayout.isDrawerOpen(Gravity.START)) {
                drawerLayout.closeDrawer(Gravity.START);
            } else if (resideMenu.isOpened()) {
                resideMenu.closeMenu();
            } else {
                super.onBackPressed();
                List<Fragment> fragments = getSupportFragmentManager().getFragments();
                BaseFragment fragment = (BaseFragment) fragments.get(fragments.size() - 1);
                fragment.setTitlebar(titleBar);
            }
        } else {
            closeApp();
        }
    }



//    public ImageView getBlurImage() {
//        return imageBlur;
//    }

    public void setBlurBackground() {

        // For Future use

////        if (mBackgroundFilename == null) {
//
//        this.mDownScaled = Utils.drawViewToBitmap(this.getMainContentFrame(), Color.parseColor("#fff5f5f5"));
//
//        mBackgroundFilename = getBlurredBackgroundFilename();
//        if (!TextUtils.isEmpty(mBackgroundFilename)) {
//            //context.getMainContentFrame().setVisibility(View.VISIBLE);
//            background = Utils.loadBitmapFromFile(mBackgroundFilename);
////                if (background != null) {
//            getBlurImage().setVisibility(View.VISIBLE);
//            getBlurImage().setImageBitmap(background);
//            getBlurImage().animate().alpha(1);
////                }
//        }
////        } else {
////            getBlurImage().setVisibility(View.VISIBLE);
////            getBlurImage().setImageBitmap(background);
////            getBlurImage().animate().alpha(1);
////        }
    }

    public String getBlurredBackgroundFilename() {
        Bitmap localBitmap = Blur.fastblur(this, this.mDownScaled, 20);
        String str = Utils.saveBitmapToFile(this, localBitmap);
        this.mDownScaled.recycle();
        localBitmap.recycle();
        return str;
    }
//
//    public void removeBlurImage() {
//        getBlurImage().setVisibility(View.GONE);
//    }



}