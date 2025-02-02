package com.tekrevol.papp.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.gson.Gson;
import com.tekrevol.papp.BaseApplication;
import com.tekrevol.papp.R;
import com.tekrevol.papp.callbacks.GenericClickableInterface;
import com.tekrevol.papp.constatnts.AppConstants;
import com.tekrevol.papp.fragments.RightSideMenuFragment;
import com.tekrevol.papp.fragments.abstracts.BaseFragment;
import com.tekrevol.papp.fragments.abstracts.GenericDialogFragment;
import com.tekrevol.papp.managers.SharedPreferenceManager;
import com.tekrevol.papp.managers.retrofit.GsonFactory;
import com.tekrevol.papp.widget.TitleBar;


public abstract class BaseActivity extends AppCompatActivity {

    protected DrawerLayout drawerLayout;
    protected TitleBar titleBar;
    private RightSideMenuFragment rightSideMenuFragment;
    public BaseFragment baseFragment;
    public GenericClickableInterface genericClickableInterface;
    private Gson gson;
    public boolean isReloadFragmentOnBack = false;
    public SharedPreferenceManager sharedPreferenceManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getViewId());
        setAndBindTitleBar();
        sharedPreferenceManager = SharedPreferenceManager.getInstance(this);
        drawerLayout = (DrawerLayout) findViewById(getDrawerLayoutId());
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        gson = GsonFactory.getSimpleGson();
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
//            getWindow().setStatusBarColor(Color.WHITE);
//        }


//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            Window window = getWindow();
//            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(getResources().getColor(android.R.color.transparent));
//        }


        addDrawerFragment();
    }

    /**
     * Give Resource id of the view you want to inflate
     *
     * @return
     */
    protected abstract int getViewId();


    protected abstract int getTitlebarLayoutId();

    protected abstract int getDrawerLayoutId();

    protected abstract int getDockableFragmentId();

    protected abstract int getDrawerFragmentId();

    public DrawerLayout getDrawerLayout() {
        return drawerLayout;
    }


    public void addDrawerFragment() {
        rightSideMenuFragment = RightSideMenuFragment.newInstance();
        getSupportFragmentManager().beginTransaction().replace(getDrawerFragmentId(), rightSideMenuFragment).commit();
    }


    private void setAndBindTitleBar() {
        titleBar = (TitleBar) findViewById(getTitlebarLayoutId());
        titleBar.setVisibility(View.GONE);
        titleBar.resetViews();
    }

    public void closeApp() {
        final GenericDialogFragment genericDialogFragment = GenericDialogFragment.newInstance();

        genericDialogFragment.setTitle("Quit");
        genericDialogFragment.setMessage("Do you want to close this application?");
        genericDialogFragment.setButton1("Yes", new GenericClickableInterface() {
            @Override
            public void click() {
                genericDialogFragment.getDialog().dismiss();
                finish();
            }
        });

        genericDialogFragment.setButton2("No", new GenericClickableInterface() {
            @Override
            public void click() {
                genericDialogFragment.getDialog().dismiss();
            }
        });
        genericDialogFragment.show(getSupportFragmentManager(), null);


//        new iOSDialogBuilder(this)
//                .setTitle("Quit")
//                .setSubtitle("Do you want to close this application?")
//                .setBoldPositiveLabel(false)
//                .setCancelable(false)
//                .setPositiveListener("Yes", dialog -> {
//                    dialog.dismiss();
//                    finish();
//                })
//                .setNegativeListener("No", dialog -> dialog.dismiss())
//                .build().show();
    }

    public void addDockableFragment(Fragment fragment, boolean isTransition) {
        baseFragment = (BaseFragment) fragment;
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (isTransition) {
            fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
        }
        fragmentTransaction.replace(getDockableFragmentId(), fragment).addToBackStack(fragment.getClass().getSimpleName())
                .commit();
    }

    public TitleBar getTitleBar() {
        return titleBar;
    }


    public void openMapsActivityForResult(Class<?> tClass, int requestCode) {
        Intent i = new Intent(this, tClass);
        startActivityForResult(i, requestCode);
    }

    public void openMapsActivityForResult(Class<?> tClass, Fragment fragment, int requestCode) {
        Intent i = new Intent(this, tClass);
        fragment.startActivityForResult(i, requestCode);
    }

    public void openActivity(Class<?> tClass) {
        Intent i = new Intent(this, tClass);
        startActivity(i);
    }


    public void openImagePreviewActivity(String url, String title) {
        Intent i = new Intent(this, ImagePreviewActivity.class);
        i.putExtra(AppConstants.IMAGE_PREVIEW_TITLE, title);
        i.putExtra(AppConstants.IMAGE_PREVIEW_URL, url);
        startActivity(i);
    }

    public void openActivity(Class<?> tClass, String object) {
        Intent i = new Intent(this, tClass);
        i.putExtra(AppConstants.JSON_STRING_KEY, object);
        startActivity(i);
    }


    public RightSideMenuFragment getRightSideMenuFragment() {
        return rightSideMenuFragment;
    }

    public void reload() {
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
    }

    public void clearAllActivitiesExceptThis(Class<?> cls) {
        Intent intents = new Intent(this, cls);
        intents.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intents);
        finish();
    }


    public void emptyBackStack() {
        FragmentManager fm = getSupportFragmentManager();
        if (fm == null) return;
        for (int i = 0; i < fm.getBackStackEntryCount(); i++) {
            fm.popBackStack();
        }
    }

    public void popBackStack() {
        if (getSupportFragmentManager() == null) {
            return;
        } else {
            getSupportFragmentManager().popBackStack();
        }
    }

    public void popStackTill(int stackNumber) {
        FragmentManager fm = getSupportFragmentManager();
        if (fm == null) return;
        for (int i = stackNumber; i < fm.getBackStackEntryCount(); i++) {
            fm.popBackStack();
        }
    }


    public void popStackTill(String tag) {
        FragmentManager fm = getSupportFragmentManager();
        if (fm == null) {
            return;
        }

        int backStackEntryCount = fm.getBackStackEntryCount();
        for (int i = backStackEntryCount - 1; i > 0; i--) {
            if (fm.getBackStackEntryAt(i).getName().equalsIgnoreCase(tag)) {
                return;
            } else {
                fm.popBackStack();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        if (DeviceUtils.isRooted(getApplicationContext())) {
//            showAlertDialogAndExitApp("This device is rooted. You can't use this app.");
//        }
    }

    public void notifyToAll(int event, Object data) {
        BaseApplication.getPublishSubject().onNext(new Pair<>(event, data));
    }

    public void refreshFragment(BaseFragment fragment) {
        popBackStack();
        addDockableFragment(fragment, false);
    }

    public void setGenericClickableInterface(GenericClickableInterface genericClickableInterface) {
        this.genericClickableInterface = genericClickableInterface;
    }


    public void showAlertDialogAndExitApp(String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage(message);
        alertDialog.setCancelable(false);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                });

        alertDialog.show();
    }


    public Gson getGson() {
        return gson;
    }
}