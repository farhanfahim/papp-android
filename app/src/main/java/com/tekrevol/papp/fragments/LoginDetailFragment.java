package com.tekrevol.papp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.gson.JsonElement;
import com.modules.facebooklogin.FacebookHelper;
import com.modules.facebooklogin.FacebookResponse;
import com.modules.facebooklogin.FacebookUser;
import com.tekrevol.papp.R;
import com.tekrevol.papp.activities.HomeActivity;
import com.tekrevol.papp.constatnts.AppConstants;
import com.tekrevol.papp.constatnts.WebServiceConstants;
import com.tekrevol.papp.enums.BaseURLTypes;
import com.tekrevol.papp.firebase.chat.FirebaseIntegration;
import com.tekrevol.papp.fragments.abstracts.BaseFragment;
import com.tekrevol.papp.fragments.abstracts.GenericDialogFragment;
import com.tekrevol.papp.helperclasses.ui.helper.UIHelper;
import com.tekrevol.papp.helperclasses.validator.PasswordValidation;
import com.tekrevol.papp.managers.retrofit.WebServices;
import com.tekrevol.papp.models.sending_model.LoginSendingModel;
import com.tekrevol.papp.models.sending_model.SocialLoginSendingModel;
import com.tekrevol.papp.models.wrappers.UserModelWrapper;
import com.tekrevol.papp.models.wrappers.WebResponse;
import com.tekrevol.papp.widget.AnyEditTextView;
import com.tekrevol.papp.widget.AnyTextView;
import com.tekrevol.papp.widget.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.CompletableObserver;
import io.reactivex.disposables.Disposable;

import static com.tekrevol.papp.constatnts.AppConstants.SOCIAL_MEDIA_PLATFORM_FACEBOOK;
import static com.tekrevol.papp.constatnts.WebServiceConstants.PATH_SOCIAL_LOGIN;

/**
 * Created by hamza.ahmed on 7/19/2018.
 */

public class LoginDetailFragment extends BaseFragment implements FacebookResponse {


    Unbinder unbinder;
    @BindView(R.id.edtEmailAddress)
    AnyEditTextView edtEmailAddress;
    @BindView(R.id.edtPassword)
    AnyEditTextView edtPassword;
    @BindView(R.id.contBtnLogin)
    LinearLayout contBtnLogin;
    @BindView(R.id.contFacebookLogin)
    LinearLayout contFacebookLogin;
    @BindView(R.id.contTwitterLogin)
    LinearLayout contTwitterLogin;
    @BindView(R.id.contSignup)
    LinearLayout contSignup;
    @BindView(R.id.txtOrLoginWith)
    AnyTextView txtOrLoginWith;
    @BindView(R.id.contSocialLogin)
    LinearLayout contSocialLogin;

    private FacebookHelper mFbHelper;

    public static LoginDetailFragment newInstance() {

        Bundle args = new Bundle();

        LoginDetailFragment fragment = new LoginDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public int getDrawerLockMode() {
        return 0;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_login_detail;
    }

    @Override
    public void setTitlebar(TitleBar titleBar) {

    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //fb api initialization
        mFbHelper = new FacebookHelper(this,
                "id,name,email,gender,birthday,picture",
                getBaseActivity());

        contSocialLogin.setVisibility(View.VISIBLE);
        txtOrLoginWith.setVisibility(View.VISIBLE);

        edtPassword.addValidator(new PasswordValidation());



        edtEmailAddress.setText("a@a.a");
        edtPassword.setText("123456");

    }


    @Override
    public void setListeners() {

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }


    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        mFbHelper.onActivityResult(requestCode, resultCode, data);

    }

    @OnClick({R.id.contBtnLogin, R.id.contFacebookLogin, R.id.contTwitterLogin, R.id.contSignup})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.contBtnLogin:

                LoginSendingModel loginSendingModel = new LoginSendingModel();
                loginSendingModel.setDeviceType(AppConstants.DEVICE_OS_ANDROID);
                loginSendingModel.setEmail(edtEmailAddress.getStringTrimmed());
                loginSendingModel.setPassword(edtPassword.getStringTrimmed());

                if (edtEmailAddress.testValidity() && edtPassword.testValidity()) {
                    webCallLogin(loginSendingModel);
                }


                break;
            case R.id.contFacebookLogin:
                mFbHelper.performSignIn(LoginDetailFragment.this);
                break;
            case R.id.contTwitterLogin:
                showNextBuildToast();
                break;
            case R.id.contSignup:
                getBaseActivity().addDockableFragment(SignUpFragment.newInstance(), true);
                break;
        }
    }

    public void webCallLogin(LoginSendingModel loginSendingModel) {
        new WebServices(getBaseActivity(), "", BaseURLTypes.BASE_URL, true)
                .postAPIAnyObject(WebServiceConstants.PATH_LOGIN, loginSendingModel.toString(),
                        new WebServices.IRequestWebResponseAnyObjectCallBack() {
                            @Override
                            public void requestDataResponse(WebResponse<Object> webResponse) {

                                UserModelWrapper userModelWrapper = getGson().fromJson(getGson().toJson(webResponse.result), UserModelWrapper.class);


                                FirebaseIntegration.getInstance().saveUserDetail(getContext(), userModelWrapper.getUser()).subscribe(new CompletableObserver() {
                                    @Override
                                    public void onSubscribe(Disposable d) {

                                    }

                                    @Override
                                    public void onComplete() {
                                        sharedPreferenceManager.putObject(AppConstants.KEY_CURRENT_USER_MODEL, userModelWrapper.getUser());
                                        sharedPreferenceManager.putValue(AppConstants.KEY_TOKEN, userModelWrapper.getUser().getAccessToken());

                                        getBaseActivity().finish();
                                        getBaseActivity().openActivity(HomeActivity.class);
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        UIHelper.showAlertDialog(getContext(), e.getMessage());

                                    }
                                });



                            }

                            @Override
                            public void onError(Object object) {

                            }
                        });
    }


    @Override
    public void onFbSignInFail() {
        Toast.makeText(getContext(), "Unable to sign in with Facebook", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFbSignInSuccess() {
//        Toast.makeText(getBaseActivity(), "Facebook sign in success", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFbProfileReceived(FacebookUser facebookUser) {
//        Toast.makeText(getContext(), "Facebook user data: name= " + facebookUser.name + " email= " + facebookUser.email, Toast.LENGTH_SHORT).show();

        SocialLoginSendingModel socialLoginSendingModel = new SocialLoginSendingModel();
        socialLoginSendingModel.setClientId(facebookUser.facebookID);
        socialLoginSendingModel.setDeviceType(AppConstants.DEVICE_OS_ANDROID);
        socialLoginSendingModel.setEmail(facebookUser.email);
        socialLoginSendingModel.setImage(facebookUser.profilePic);
        socialLoginSendingModel.setPlatform(SOCIAL_MEDIA_PLATFORM_FACEBOOK);
        socialLoginSendingModel.setUsername(facebookUser.name);
        socialLoginSendingModel.setToken("abc123");


        getBaseWebService().postAPIAnyObject(PATH_SOCIAL_LOGIN, socialLoginSendingModel.toString(), new WebServices.IRequestWebResponseAnyObjectCallBack() {
            @Override
            public void requestDataResponse(WebResponse<Object> webResponse) {

                JsonElement is_exists = getGson().toJsonTree(webResponse.result).getAsJsonObject().get("is_exists");
                if (is_exists == null) {
                    // User exists, do login

                    UserModelWrapper userModelWrapper = getGson().fromJson(getGson().toJson(webResponse.result), UserModelWrapper.class);
                    sharedPreferenceManager.putObject(AppConstants.KEY_CURRENT_USER_MODEL, userModelWrapper.getUser());
                    sharedPreferenceManager.putValue(AppConstants.KEY_TOKEN, userModelWrapper.getUser().getAccessToken());

                    FirebaseIntegration.getInstance().saveUserDetail(getContext(), userModelWrapper.getUser());

                    getBaseActivity().finish();
                    getBaseActivity().openActivity(HomeActivity.class);

                } else {
                    if (!is_exists.getAsBoolean()) {
                        // User doesn't exist, go to register screen

                        GenericDialogFragment genericDialogFragment = GenericDialogFragment.newInstance();
                        UIHelper.genericPopUp(getBaseActivity(), genericDialogFragment, "Select Role", "Kindly select the role you want to register as:",
                                "Civilian", "Mentor", () -> {
                                    genericDialogFragment.dismiss();
                                    getBaseActivity().addDockableFragment(SocialSignUpCivilianFragment.newInstance(facebookUser), true);
                                }, () -> {
                                    genericDialogFragment.dismiss();
                                    getBaseActivity().addDockableFragment(SocialSignUpMentorFragment.newInstance(facebookUser), true);
                                }, true, true);
                    }
                }


                mFbHelper.performSignOut();
            }

            @Override
            public void onError(Object object) {

            }
        });


    }

    @Override
    public void onFBSignOut() {

    }
}
