package edu.aku.ehs.activities;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.LinearLayout;

import com.google.gson.JsonObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.aku.ehs.R;
import edu.aku.ehs.constatnts.AppConstants;
import edu.aku.ehs.helperclasses.ui.helper.AnimationHelper;
import edu.aku.ehs.managers.SharedPreferenceManager;
import edu.aku.ehs.models.wrappers.WebResponse;
import retrofit2.Call;

import static android.view.View.VISIBLE;

public class SplashActivity extends AppCompatActivity {

    private static final String TAG = "SPLASH SCREEN";
    @BindView(R.id.contParentLayout)
    LinearLayout contParentLayout;
    private final int SPLASH_TIME_OUT = 2000;
    private final int ANIMATIONS_DELAY = 200;
    private final int ANIMATIONS_TIME_OUT = 250;
    private final int FADING_TIME = 500;
    private boolean hasAnimationStarted = false;
    private boolean isUpdateCallBackRecieved = false;

    Call<WebResponse<JsonObject>> isUpdateAvailableCall = null;
    Call<WebResponse<JsonObject>> getOneTimeTokenService = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        contParentLayout.setVisibility(View.INVISIBLE);
    }

    private void animateSplashLayout(final boolean showLoginScreen) {


        // Move Layout to center

        //->
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        ObjectAnimator translationY = ObjectAnimator.ofFloat(contParentLayout, "y", metrics.heightPixels / 2 - contParentLayout.getHeight() / 2); // metrics.heightPixels or root.getHeight()
        translationY.setDuration(1);
        translationY.start();
        //<-

        // Fading in Layout

        AnimationHelper.fade(contParentLayout, 0, VISIBLE, VISIBLE, 0.7f, FADING_TIME, new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {

                // If directly go Home or Login Screen.
                if (showLoginScreen) {
                    translateAnimation();
                } else {
//                    updateAppOrChangeActivity(HomeActivity.class);
                }
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });


    }

    private void translateAnimation() {


        contParentLayout.animate().

                setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {
                        contParentLayout.setVisibility(VISIBLE);
                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        contParentLayout.setTranslationY(0);
                        contParentLayout.setAlpha(1);
//                        updateAppOrChangeActivity(MainActivity.class);
                        changeActivity(MainActivity.class);
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                })
                .alpha(1)
                .translationY(0)
                .setDuration(SPLASH_TIME_OUT)
                .start();
    }


//    private void updateAppOrChangeActivity(final Class activityClass) {
//
//        AppVersionModel appVersionModel = new AppVersionModel();
//        PackageManager packageManager = getApplicationContext().getPackageManager();
//        String packageName = getApplicationContext().getPackageName();
//
//        String myVersionName = ""; // initialize String
//        int versionCode = 0; // initialize Integer
//
//        try {
//            myVersionName = packageManager.getPackageInfo(packageName, 0).versionName;
//            versionCode = packageManager.getPackageInfo(packageName, 0).versionCode;
//        } catch (PackageManager.NameNotFoundException e) {
//            Log.d(TAG, "updateAppOrChangeActivity: " + e.getLocalizedMessage());
//        }
//        appVersionModel.setAndappversioncode(versionCode);
//        appVersionModel.setAndappversionname(myVersionName);
//        isUpdateAvailable(appVersionModel, activityClass, "");
//
//        try {
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    if (!isUpdateCallBackRecieved) {
//                        pinVerification(activityClass);
//                    }
//                }
//            }, 10000);
//        } catch (Exception e) {
//            Log.d(TAG, "updateAppOrChangeActivity: " + e.getLocalizedMessage());
//        }
//    }

//    private void pinVerification(final Class activityClass) {
//
//        if (activityClass == MainActivity.class) {
//            changeActivity(activityClass);
//        } else {
//            boolean isPinEnabled = SharedPreferenceManager.getInstance(getApplicationContext()).getBoolean(AppConstants.KEY_IS_PIN_ENABLE);
//            if (isPinEnabled) {
//                try {
//                    showPinVerificationDialog(activityClass);
//                } catch (IllegalStateException e) {
//                    changeActivity(activityClass);
//                }
//            } else {
//                changeActivity(activityClass);
//            }
//        }
//
//
//    }

    private void changeActivity(Class activityClass) {
        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {

                Intent i;
                // This method will be executed once the timer is over
                // Start your app main activity
                i = new Intent(SplashActivity.this, activityClass);

                startActivity(i);
                overridePendingTransition(android.R.anim.fade_in, R.anim.fade_out);
                // close this activity
                finish();
            }
        }, ANIMATIONS_TIME_OUT);
    }

//    private void showPinVerificationDialog(final Class activityClass) {
//        final PinEntryDialogFragment pinEntryDialogFragment = PinEntryDialogFragment.newInstance(view -> {
//            //Success
//            changeActivity(activityClass);
//        }, view -> {
//            //Logout
//            SharedPreferenceManager.getInstance(this).clearDB();
//            changeActivity(MainActivity.class);
//        });
//        pinEntryDialogFragment.setTitle("Enter PIN");
//        pinEntryDialogFragment.setCancelable(false);
//        pinEntryDialogFragment.show(getSupportFragmentManager(), null);
//    }

//    public void updateApp(final Class activityClass, AppVersionModel appVersionModel) {
//        int button2Visiblity;
//        String responseMessage;
//        String button1Text;
//
//        if (appVersionModel.getAndallowoldversion().equals("Y")) {
//            button2Visiblity = VISIBLE;
//            responseMessage = "An update of Family Hifazat App (v" + appVersionModel.getAndappversionname() + ") is available to download.\n" + "Do you want to update it now?";
//            button1Text = "Yes";
//        } else {
//            responseMessage = "An update of Family Hifazat App (v" + appVersionModel.getAndappversionname() + ") is available to download.";
//            button2Visiblity = GONE;
//            button1Text = "Update";
//        }
//
//        final GenericDialogFragment genericDialogFragment = GenericDialogFragment.newInstance();
//
//        genericDialogFragment.setTitle("Update App");
//        genericDialogFragment.setMessage(responseMessage);
//        genericDialogFragment.setButton1(button1Text, () -> {
//            genericDialogFragment.getDialog().dismiss();
//            Helper.openPlayStore(SplashActivity.this);
//        });
//
//        genericDialogFragment.setButton2("Not Now", () -> {
//            genericDialogFragment.getDialog().dismiss();
//            pinVerification(activityClass);
//        });
//
//        genericDialogFragment.setButton2Visibility(button2Visiblity);
//        genericDialogFragment.setCancelable(false);
//        genericDialogFragment.show(getSupportFragmentManager(), null);
//
//
//    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && !hasAnimationStarted) {
            hasAnimationStarted = true;


            animateSplashLayout(true);


//            if (SharedPreferenceManager.getInstance(getApplicationContext()).getString(AppConstants.KEY_CARD_NUMBER) == null
//                    || SharedPreferenceManager.getInstance(getApplicationContext()).getString(AppConstants.KEY_CARD_NUMBER).isEmpty()) {
//
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        animateSplashLayout(true);
//                    }
//                }, ANIMATIONS_DELAY);
//
//            } else {
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        animateSplashLayout(false);
//                    }
//                }, ANIMATIONS_DELAY);
//            }
        }
    }


//    private void getOneTimeTokenService(final AppVersionModel appVersionModel, final Class activityClass) {
//        getOneTimeTokenService = new WebServices(this, "", BaseURLTypes.EHS_BASE_URL, false)
//                .webServiceRequestAPIForJsonObject(WebServiceConstants.METHOD_GET_ONE_TIME_TOKEN,
//                        "",
//                        new WebServices.IRequestJsonDataCallBack() {
//                            @Override
//                            public void requestDataResponse(WebResponse<JsonObject> webResponse) {
//                                AddUpdateModel addUpdateModel = GsonFactory.getSimpleGson().fromJson(webResponse.result, AddUpdateModel.class);
//                                if (addUpdateModel.getStatus()) {
//                                    SharedPreferenceManager.getInstance(getContext()).putValue(AppConstants.KEY_ONE_TIME_TOKEN, addUpdateModel.getRecordid());
//                                    isUpdateAvailable(appVersionModel, activityClass, "");
//                                }
//                            }
//
//                            @Override
//                            public void onError() {
//                                isUpdateAvailable(appVersionModel, activityClass, "");
//                            }
//                        });
//    }


//    private void isUpdateAvailable(final AppVersionModel appVersionModel, final Class activityClass, String token) {
//        isUpdateAvailableCall = new WebServices(this,
//                token,
//                BaseURLTypes.EHS_BASE_URL, false)
//                .webServiceRequestAPIForJsonObject(WebServiceConstants.METHOD_USER_GET_APPLICATION_PARAMETER,
//                        appVersionModel.toString(),
//                        new WebServices.IRequestJsonDataCallBack() {
//                            @Override
//                            public void requestDataResponse(WebResponse<JsonObject> webResponse) {
//                                isUpdateCallBackRecieved = true;
//                                AppVersionModel appVersionModelReceiving = GsonFactory.getSimpleGson().fromJson(webResponse.result, AppVersionModel.class);
//
//                                if (appVersionModel.getAndappversioncode() < appVersionModelReceiving.getAndappversioncode()) {
//                                    updateApp(activityClass, appVersionModelReceiving);
//                                } else {
//                                    pinVerification(activityClass);
//                                }
//                            }
//
//                            @Override
//                            public void onError() {
//                                 isUpdateCallBackRecieved = true;
//                                pinVerification(activityClass);
//                            }
//                        });
//    }

    @Override
    protected void onDestroy() {
        if (getOneTimeTokenService != null && !getOneTimeTokenService.isCanceled()) {
            getOneTimeTokenService.cancel();
        }

        if (isUpdateAvailableCall != null && !isUpdateAvailableCall.isCanceled()) {
            isUpdateAvailableCall.cancel();
        }

        super.onDestroy();

    }
}
