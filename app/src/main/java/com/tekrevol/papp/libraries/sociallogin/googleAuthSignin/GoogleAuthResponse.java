package com.tekrevol.papp.libraries.sociallogin.googleAuthSignin;

/**
 * Created by Hamza and Muzz on 6/21/2016.
 */

public interface GoogleAuthResponse {

    void onGoogleAuthSignIn(GoogleAuthUser user);

    void onGoogleAuthSignInFailed();

    void onGoogleAuthSignOut(boolean isSuccess);
}
