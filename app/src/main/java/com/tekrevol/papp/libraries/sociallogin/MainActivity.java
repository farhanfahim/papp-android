//package com.tekrevol.papp.libraries.sociallogin;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.v7.app.AppCompatActivity;
//import android.util.Log;
//import android.view.View;
//import android.widget.Toast;
//
//import com.google.android.gms.plus.model.people.Person;
//import com.tekrevol.papp.R;
//import com.tekrevol.papp.libraries.sociallogin.facebookSignIn.FacebookHelper;
//import com.tekrevol.papp.libraries.sociallogin.facebookSignIn.FacebookResponse;
//import com.tekrevol.papp.libraries.sociallogin.facebookSignIn.FacebookUser;
//import com.tekrevol.papp.libraries.sociallogin.googleAuthSignin.GoogleAuthResponse;
//import com.tekrevol.papp.libraries.sociallogin.googleAuthSignin.GoogleAuthUser;
//import com.tekrevol.papp.libraries.sociallogin.googleAuthSignin.GoogleSignInHelper;
//import com.tekrevol.papp.libraries.sociallogin.googleSignIn.GooglePlusSignInHelper;
//import com.tekrevol.papp.libraries.sociallogin.googleSignIn.GoogleResponseListener;
//import com.tekrevol.papp.libraries.sociallogin.instagramSignIn.InstagramHelper;
//import com.tekrevol.papp.libraries.sociallogin.instagramSignIn.InstagramResponse;
//import com.tekrevol.papp.libraries.sociallogin.instagramSignIn.InstagramUser;
//import com.tekrevol.papp.libraries.sociallogin.linkedInSiginIn.LinkedInHelper;
//import com.tekrevol.papp.libraries.sociallogin.linkedInSiginIn.LinkedInResponse;
//import com.tekrevol.papp.libraries.sociallogin.linkedInSiginIn.LinkedInUser;
//import com.tekrevol.papp.libraries.sociallogin.twitterSignIn.TwitterHelper;
//import com.tekrevol.papp.libraries.sociallogin.twitterSignIn.TwitterResponse;
//import com.tekrevol.papp.libraries.sociallogin.twitterSignIn.TwitterUser;
//
//public class MainActivity extends AppCompatActivity implements View.OnClickListener,
//        GoogleResponseListener, FacebookResponse, TwitterResponse, LinkedInResponse, GoogleAuthResponse, InstagramResponse {
//
//    private FacebookHelper mFbHelper;
//    private GooglePlusSignInHelper mGHelper;
//    private GoogleSignInHelper mGAuthHelper;
//    private TwitterHelper mTwitterHelper;
//    private LinkedInHelper mLinkedInHelper;
//    private InstagramHelper mInstagramHelper;
//
//    @SuppressWarnings("ConstantConditions")
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        setContentView(R.layout.activity_main);
//
//        //Google api initialization
//        mGHelper = new GooglePlusSignInHelper(this, this);
//
//        //google auth initialization
//        mGAuthHelper = new GoogleSignInHelper(this, null, this);
//
//        //fb api initialization
//        mFbHelper = new FacebookHelper(this,
//                "id,name,email,gender,birthday,picture,cover",
//                this);
//
//        //twitter initialization
//        mTwitterHelper = new TwitterHelper(R.string.twitter_api_key,
//                R.string.twitter_secrate_key,
//                this,
//                this);
//
//        //linkedIn initializer
//        mLinkedInHelper = new LinkedInHelper(this, this);
//
//        //instagram initializer
//        mInstagramHelper = new InstagramHelper(
//                getResources().getString(R.string.instagram_client_id),
//                getResources().getString(R.string.instagram_client_secret),
//                getResources().getString(R.string.instagram_callback_url), this, this);
//
//        //set sign in button
//        findViewById(R.id.g_login_btn).setOnClickListener(this);
//        findViewById(R.id.g_logout_btn).setOnClickListener(this);
//        findViewById(R.id.g_plus_login_btn).setOnClickListener(this);
//        findViewById(R.id.g_plus_logout_btn).setOnClickListener(this);
//        findViewById(R.id.twitter_login_button).setOnClickListener(this);
//        findViewById(R.id.bt_act_login_fb).setOnClickListener(this);
//        findViewById(R.id.bt_act_logout_fb).setOnClickListener(this);
//        findViewById(R.id.linkedin_login_button).setOnClickListener(this);
//        findViewById(R.id.linkedin_logout_button).setOnClickListener(this);
//        findViewById(R.id.instagram_login_button).setOnClickListener(this);
//    }
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.g_login_btn:
//                mGAuthHelper.performSignIn(this);
//                break;
//            case R.id.g_logout_btn:
//                mGAuthHelper.performSignOut();
//                break;
//            case R.id.g_plus_login_btn:
//                mGHelper.performSignIn();
//                break;
//            case R.id.g_plus_logout_btn:
//                mGHelper.signOut();
//                break;
//            case R.id.bt_act_login_fb:
//                mFbHelper.performSignIn(this);
//                break;
//            case R.id.bt_act_logout_fb:
//                mFbHelper.performSignOut();
//                break;
//            case R.id.twitter_login_button:
//                mTwitterHelper.performSignIn();
//                break;
//            case R.id.linkedin_login_button:
//                mLinkedInHelper.performSignIn();
//                break;
//            case R.id.linkedin_logout_button:
//                mLinkedInHelper.logout();
//                break;
//            case R.id.instagram_login_button:
//                mInstagramHelper.performSignIn();
//                break;
//        }
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        mGHelper.disconnectApiClient();
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
//        //handle permissions
//        mGHelper.onPermissionResult(requestCode, grantResults);
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        //handle results
//        mFbHelper.onActivityResult(requestCode, resultCode, data);
//        mGHelper.onActivityResult(requestCode, resultCode, data);
//        mGAuthHelper.onActivityResult(requestCode, resultCode, data);
//        mTwitterHelper.onActivityResult(requestCode, resultCode, data);
//        mLinkedInHelper.onActivityResult(requestCode, resultCode, data);
//    }
//
//    @Override
//    public void onFbSignInFail() {
//        Toast.makeText(this, "Facebook sign in failed.", Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public void onFbSignInSuccess() {
//        Toast.makeText(this, "Facebook sign in success", Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public void onFbProfileReceived(FacebookUser facebookUser) {
//        Toast.makeText(this, "Facebook user data: name= " + facebookUser.name + " email= " + facebookUser.email, Toast.LENGTH_SHORT).show();
//
//        Log.d("Person name: ", facebookUser.name + "");
//        Log.d("Person gender: ", facebookUser.gender + "");
//        Log.d("Person email: ", facebookUser.email + "");
//        Log.d("Person image: ", facebookUser.facebookID + "");
//    }
//
//    @Override
//    public void onFBSignOut() {
//        Toast.makeText(this, "Facebook sign out success", Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public void onGSignInFail() {
//        Toast.makeText(this, "Google sign in failed.", Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public void onGSignInSuccess(Person person) {
//        Toast.makeText(this, "Google sign in success", Toast.LENGTH_SHORT).show();
//        Log.d("Person display name: ", person.getDisplayName() + "");
//        Log.d("Person birth date: ", person.getBirthday() + "");
//        Log.d("Person gender: ", person.getGender() + "");
//        Log.d("Person name: ", person.getName() + "");
//        Log.d("Person id: ", person.getImage() + "");
//    }
//
//    @Override
//    public void onTwitterError() {
//        Toast.makeText(this, "Twitter sign in failed.", Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public void onTwitterSignIn(@NonNull String userId, @NonNull String userName) {
//        Toast.makeText(this, " User id: " + userId + "\n user name" + userName, Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public void onTwitterProfileReceived(TwitterUser user) {
//        Toast.makeText(this, "Twitter user data: name= " + user.name + " email= " + user.email, Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public void onLinkedInSignInFail() {
//        Toast.makeText(this, "LinkedIn sign in failed.", Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public void onLinkedInSignInSuccess(String accessToken) {
//        Toast.makeText(this, "Linked in signin successful.\n Getting user profile...", Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public void onLinkedInProfileReceived(LinkedInUser user) {
//        Toast.makeText(this, "LinkedIn user data: name= " + user.name + " email= " + user.email, Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public void onGoogleAuthSignIn(GoogleAuthUser user) {
//        Toast.makeText(this, "Google user data: name= " + user.name + " email= " + user.email, Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public void onGoogleAuthSignInFailed() {
//        Toast.makeText(this, "Google sign in failed.", Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public void onGoogleAuthSignOut(boolean isSuccess) {
//        Toast.makeText(this, isSuccess ? "Sign out success" : "Sign out failed", Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public void onInstagramSignInSuccess(InstagramUser user) {
//        Toast.makeText(this, "Instagram user data: full name name=" + user.getFull_name() + " user name=" + user.getUsername(), Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public void onInstagramSignInFail(String error) {
//        Toast.makeText(this, "Instagram sign in failed", Toast.LENGTH_SHORT).show();
//    }
//}
