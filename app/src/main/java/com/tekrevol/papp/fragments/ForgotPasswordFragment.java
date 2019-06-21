package com.tekrevol.papp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.tekrevol.papp.R;
import com.tekrevol.papp.callbacks.OnSpinnerOKPressedListener;
import com.tekrevol.papp.constatnts.WebServiceConstants;
import com.tekrevol.papp.fragments.abstracts.BaseFragment;
import com.tekrevol.papp.fragments.dialogs.EnterNewPinDialogFragment;
import com.tekrevol.papp.helperclasses.ui.helper.UIHelper;
import com.tekrevol.papp.managers.retrofit.WebServices;
import com.tekrevol.papp.models.sending_model.ResetPasswordSendingModel;
import com.tekrevol.papp.models.wrappers.WebResponse;
import com.tekrevol.papp.widget.AnyEditTextView;
import com.tekrevol.papp.widget.AnyTextView;
import com.tekrevol.papp.widget.TitleBar;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.tekrevol.papp.constatnts.WebServiceConstants.PATH_FORGET_PASSWORD;
import static com.tekrevol.papp.constatnts.WebServiceConstants.PATH_VERIFY_RESET_CODE;

/**
 * Created by hamza.ahmed on 7/19/2018.
 */

public class ForgotPasswordFragment extends BaseFragment {


    Unbinder unbinder;
    @BindView(R.id.edtEmailAddress)
    AnyEditTextView edtEmailAddress;
    @BindView(R.id.contSendPassword)
    LinearLayout contSendPassword;
    @BindView(R.id.txtAlreadyHaveCode)
    AnyTextView txtAlreadyHaveCode;

    public static ForgotPasswordFragment newInstance() {

        Bundle args = new Bundle();

        ForgotPasswordFragment fragment = new ForgotPasswordFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public int getDrawerLockMode() {
        return 0;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_forgot_password;
    }

    @Override
    public void setTitlebar(TitleBar titleBar) {

        titleBar.resetViews();
        titleBar.setVisibility(View.VISIBLE);
        titleBar.showBackButton(getBaseActivity());
        titleBar.setTitle("Forgot Password?");
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


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


    @OnClick({R.id.contSendPassword, R.id.txtAlreadyHaveCode})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.contSendPassword:
                forgetPasswordAPI();
                break;
            case R.id.txtAlreadyHaveCode:

                if (!edtEmailAddress.testValidity()) {
                    UIHelper.showAlertDialog(getContext(), "Please enter valid email address");
                    return;
                }

                showVerificationCode();
                break;
        }
    }


    public void forgetPasswordAPI() {
        if (!edtEmailAddress.testValidity()) {
            UIHelper.showAlertDialog(getContext(), "Please enter valid email address");
            return;
        }

        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put(WebServiceConstants.Q_PARAM_EMAIL, edtEmailAddress.getStringTrimmed());

        getBaseWebService().getAPIAnyObject(PATH_FORGET_PASSWORD, queryMap, new WebServices.IRequestWebResponseAnyObjectCallBack() {
            @Override
            public void requestDataResponse(WebResponse<Object> webResponse) {
                UIHelper.showAlertDialogWithCallback(webResponse.message, "Forget Password", (dialog, which) -> {
                    showVerificationCode();
                }, getContext());
            }

            @Override
            public void onError(Object object) {

            }
        });
    }

    public void showVerificationCode() {
        EnterNewPinDialogFragment enterNewPinDialogFragment = EnterNewPinDialogFragment.newInstance(v -> {

        }, data -> {
            String code = (String) data;
            verifyCode(code);
        });

        enterNewPinDialogFragment.setTitle("Enter Verification Code");
        enterNewPinDialogFragment.show(getBaseActivity().getSupportFragmentManager(), EnterNewPinDialogFragment.class.getSimpleName());
    }

    public void verifyCode(String code) {

        ResetPasswordSendingModel resetPasswordSendingModel = new ResetPasswordSendingModel();

        resetPasswordSendingModel.setVerificationCode(code);


        getBaseWebService().postAPIAnyObject(PATH_VERIFY_RESET_CODE, resetPasswordSendingModel.toString(), new WebServices.IRequestWebResponseAnyObjectCallBack() {
            @Override
            public void requestDataResponse(WebResponse<Object> webResponse) {
                resetPasswordSendingModel.setEmail(edtEmailAddress.getStringTrimmed());
                getBaseActivity().addDockableFragment(ResetPasswordFragment.newInstance(resetPasswordSendingModel), true);
            }

            @Override
            public void onError(Object object) {

            }
        });
    }


}
