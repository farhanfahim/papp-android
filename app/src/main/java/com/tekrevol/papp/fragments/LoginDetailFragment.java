package com.tekrevol.papp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.android.papp.R;
import com.tekrevol.papp.activities.HomeActivity;
import com.tekrevol.papp.constatnts.AppConstants;
import com.tekrevol.papp.constatnts.WebServiceConstants;
import com.tekrevol.papp.enums.BaseURLTypes;
import com.tekrevol.papp.fragments.abstracts.BaseFragment;
import com.tekrevol.papp.helperclasses.validator.PasswordValidation;
import com.tekrevol.papp.managers.retrofit.WebServices;
import com.tekrevol.papp.models.sending_model.LoginSendingModel;
import com.tekrevol.papp.models.wrappers.UserModelWrapper;
import com.tekrevol.papp.models.wrappers.WebResponse;
import com.tekrevol.papp.widget.AnyEditTextView;
import com.tekrevol.papp.widget.AnyTextView;
import com.tekrevol.papp.widget.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by hamza.ahmed on 7/19/2018.
 */

public class LoginDetailFragment extends BaseFragment {


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
    private boolean isMentor;

    public static LoginDetailFragment newInstance(boolean isLEA) {

        Bundle args = new Bundle();

        LoginDetailFragment fragment = new LoginDetailFragment();
        fragment.isMentor = isLEA;
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


        if (isMentor) {
            contSocialLogin.setVisibility(View.GONE);
            txtOrLoginWith.setVisibility(View.GONE);
        } else {
            contSocialLogin.setVisibility(View.VISIBLE);
            txtOrLoginWith.setVisibility(View.VISIBLE);
        }

        edtPassword.addValidator(new PasswordValidation());

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
                showNextBuildToast();
                break;
            case R.id.contTwitterLogin:
                showNextBuildToast();
                break;
            case R.id.contSignup:
                getBaseActivity().addDockableFragment(SignUpFragment.newInstance(isMentor), true);
                break;
        }
    }

    public void webCallLogin(LoginSendingModel loginSendingModel) {
        new WebServices(getContext(), "", BaseURLTypes.BASE_URL, true)
                .postAPIAnyObject(WebServiceConstants.PATH_LOGIN, loginSendingModel.toString(),
                        new WebServices.IRequestWebResponseAnyObjectCallBack() {
                            @Override
                            public void requestDataResponse(WebResponse<Object> webResponse) {

                                UserModelWrapper userModelWrapper = getGson().fromJson(getGson().toJson(webResponse.result), UserModelWrapper.class);
                                if (isMentor) {
                                    sharedPreferenceManager.putValue(AppConstants.KEY_IS_MENTOR, true);
                                } else {
                                    sharedPreferenceManager.putValue(AppConstants.KEY_IS_MENTOR, false);
                                }
                                sharedPreferenceManager.putObject(AppConstants.KEY_CURRENT_USER_MODEL, userModelWrapper.getUser());
                                sharedPreferenceManager.putValue(AppConstants.KEY_TOKEN, userModelWrapper.getUser().getAccessToken());
                                getBaseActivity().finish();
                                getBaseActivity().openActivity(HomeActivity.class);

                            }

                            @Override
                            public void onError(Object object) {

                            }
                        });
    }


}
