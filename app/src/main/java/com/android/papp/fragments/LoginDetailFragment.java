package com.android.papp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.android.papp.R;
import com.android.papp.constatnts.AppConstants;
import com.android.papp.enums.BaseURLTypes;
import com.android.papp.fragments.abstracts.BaseFragment;
import com.android.papp.managers.retrofit.WebServices;
import com.android.papp.models.receiving_model.UserModel;
import com.android.papp.models.sending_model.LoginSendingModel;
import com.android.papp.models.wrappers.WebResponse;
import com.android.papp.widget.AnyEditTextView;
import com.android.papp.widget.AnyTextView;
import com.android.papp.widget.TitleBar;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

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
    private boolean isLEA;

    public static LoginDetailFragment newInstance(boolean isLEA) {

        Bundle args = new Bundle();

        LoginDetailFragment fragment = new LoginDetailFragment();
        fragment.isLEA = isLEA;
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


        if (isLEA) {
            contSocialLogin.setVisibility(View.GONE);
            txtOrLoginWith.setVisibility(View.GONE);
        } else {
            contSocialLogin.setVisibility(View.VISIBLE);
            txtOrLoginWith.setVisibility(View.VISIBLE);
        }

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


                new WebServices(getContext(), getToken(), BaseURLTypes.BASE_URL, true)
                        .postAPIAnyObject("login", loginSendingModel.toString(),
                                new WebServices.IRequestWebResponseAnyObjectCallBack() {
                                    @Override
                                    public void requestDataResponse(WebResponse<Object> webResponse) {

                                    }

                                    @Override
                                    public void onError(Object object) {

                                    }
                                });

                if (edtEmailAddress.testValidity() && edtPassword.testValidity()) {
                    webCallLogin(loginSendingModel);
                }


//                if (isLEA) {
//                    sharedPreferenceManager.putValue(AppConstants.KEY_IS_LEA, true);
//                } else {
//                    sharedPreferenceManager.putValue(AppConstants.KEY_IS_LEA, false);
//                }
//
//
//                getBaseActivity().finish();
//                getBaseActivity().openActivity(HomeActivity.class);

                break;
            case R.id.contFacebookLogin:
                showNextBuildToast();
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
        new WebServices(getContext(), "", BaseURLTypes.BASE_URL, true)
                .postAPIAnyObject("login", loginSendingModel.toString(),
                        new WebServices.IRequestWebResponseAnyObjectCallBack() {
                            @Override
                            public void requestDataResponse(WebResponse<Object> webResponse) {

                                Type type = new TypeToken<ArrayList<UserModel>>() {
                                }.getType();

                                ArrayList<UserModel> userModel = getGson().fromJson(webResponse.result.toString(), type);

                            }

                            @Override
                            public void onError(Object object) {

                            }
                        });
    }


}
