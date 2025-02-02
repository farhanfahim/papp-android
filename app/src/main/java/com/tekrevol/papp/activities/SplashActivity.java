package com.tekrevol.papp.activities;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tekrevol.papp.R;
import com.tekrevol.papp.helperclasses.ui.helper.AnimationHelper;
import com.tekrevol.papp.managers.SharedPreferenceManager;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.VISIBLE;

public class SplashActivity extends AppCompatActivity {

    private static final String TAG = "SPLASH SCREEN";
    @BindView(R.id.contParentLayout)
    LinearLayout contParentLayout;
    private final int SPLASH_TIME_OUT = 2000;
    private final int ANIMATIONS_DELAY = 2000;
    private final int ANIMATIONS_TIME_OUT = 250;
    private final int FADING_TIME = 500;
    @BindView(R.id.imgBackground)
    ImageView imgBackground;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.txtVersionNumber)
    TextView txtVersionNumber;
    private boolean hasAnimationStarted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        contParentLayout.setVisibility(View.INVISIBLE);

        if (SharedPreferenceManager.getInstance(SplashActivity.this).isMentor()) {
            imgBackground.setImageResource(R.drawable.img_splash_mentor);
            imageView.setImageResource(R.mipmap.ic_launcher_round);
            contParentLayout.setVisibility(VISIBLE);
        }
        if(SharedPreferenceManager.getInstance(SplashActivity.this).isParent()){
            imgBackground.setImageResource(R.drawable.img_splash_bg);
            imageView.setImageResource(R.drawable.img_logo);
            contParentLayout.setVisibility(VISIBLE);
        }


        if (Build.VERSION.SDK_INT == 26) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);



        try {
            PackageManager manager = getPackageManager();
            PackageInfo info = manager.getPackageInfo(getPackageName(), PackageManager.GET_ACTIVITIES);
            txtVersionNumber.setText("Build Version: " + info.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            txtVersionNumber.setText("");
            e.printStackTrace();
        }

    }

    private void animateSplashLayout(final boolean showLoginScreen) {


        /*
        Move Layout to center
        ->
        */

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
                    translateAnimation(MainActivity.class);
                } else {
//                    updateAppOrChangeActivity(HomeActivity.class);
                    translateAnimation(HomeActivity.class);

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

    private void translateAnimation(Class activityClass) {


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
                        changeActivity(activityClass);
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


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        if (hasFocus) {
            if (SharedPreferenceManager.getInstance(getApplicationContext()).getCurrentUser() == null) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        animateSplashLayout(true);
                    }
                }, ANIMATIONS_DELAY);
            } else {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        changeActivity(HomeActivity.class);
                    }
                }, ANIMATIONS_DELAY);
            }
        }
    }


    @Override
    protected void onDestroy() {

        super.onDestroy();

    }
}
