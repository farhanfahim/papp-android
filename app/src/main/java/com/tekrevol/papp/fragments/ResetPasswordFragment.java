package com.tekrevol.papp.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.tekrevol.papp.R;
import com.tekrevol.papp.constatnts.AppConstants;
import com.tekrevol.papp.fragments.abstracts.BaseFragment;
import com.tekrevol.papp.helperclasses.ui.helper.UIHelper;
import com.tekrevol.papp.helperclasses.validator.PasswordValidation;
import com.tekrevol.papp.managers.retrofit.WebServices;
import com.tekrevol.papp.models.receiving_model.UserModel;
import com.tekrevol.papp.models.sending_model.DependentChangePasswordSendingModel;
import com.tekrevol.papp.models.sending_model.ResetPasswordSendingModel;
import com.tekrevol.papp.models.wrappers.WebResponse;
import com.tekrevol.papp.widget.AnyEditTextView;
import com.tekrevol.papp.widget.AnyTextView;
import com.tekrevol.papp.widget.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.tekrevol.papp.constatnts.WebServiceConstants.PATH_CHANGE_DEPENDENT_PASSWORD;
import static com.tekrevol.papp.constatnts.WebServiceConstants.PATH_RESET_PASSWORD;

/**
 * Created by hamza.ahmed on 7/19/2018.
 */

public class ResetPasswordFragment extends BaseFragment {


    Unbinder unbinder;
    @BindView(R.id.txtSetPassword)
    AnyTextView txtSetPassword;
    @BindView(R.id.txtEmailAddress)
    AnyTextView txtEmailAddress;
    @BindView(R.id.contEmailPassword)
    LinearLayout contEmailPassword;
    @BindView(R.id.edtNewPassword)
    AnyEditTextView edtNewPassword;
    @BindView(R.id.contCurrentPassword)
    LinearLayout contCurrentPassword;
    @BindView(R.id.edtConfirmPassword)
    AnyEditTextView edtConfirmPassword;
    @BindView(R.id.contNewPassword)
    LinearLayout contNewPassword;
    @BindView(R.id.contBtnSave)
    LinearLayout contBtnSave;

    private ResetPasswordSendingModel resetPasswordSendingModel;

    public static ResetPasswordFragment newInstance(ResetPasswordSendingModel resetPasswordSendingModel) {

        Bundle args = new Bundle();

        ResetPasswordFragment fragment = new ResetPasswordFragment();
        fragment.resetPasswordSendingModel = resetPasswordSendingModel;
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public int getDrawerLockMode() {
        return 0;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_reset_password;
    }

    @Override
    public void setTitlebar(TitleBar titleBar) {
        titleBar.setVisibility(View.VISIBLE);
        titleBar.setTitle("Reset Password");
        titleBar.showBackButton(getBaseActivity());
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        txtEmailAddress.setText(resetPasswordSendingModel.getEmail());

        edtConfirmPassword.addValidator(new PasswordValidation());
        edtNewPassword.addValidator(new PasswordValidation(edtConfirmPassword));

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
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick({R.id.contBtnSave})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.contBtnSave:
                resetPassword();
                break;
        }
    }

    public void resetPassword() {
        // Validations


        if (!edtNewPassword.testValidity()) {
            UIHelper.showAlertDialog(getContext(), "Please enter valid new password");
            return;
        }
        if (!edtConfirmPassword.testValidity()) {
            UIHelper.showAlertDialog(getContext(), "Please enter valid confirm password");
            return;
        }

        resetPasswordSendingModel.setPassword(edtNewPassword.getStringTrimmed());
        resetPasswordSendingModel.setPassword(edtConfirmPassword.getStringTrimmed());

        getBaseWebService().postAPIAnyObject(PATH_RESET_PASSWORD, resetPasswordSendingModel.toString(), new WebServices.IRequestWebResponseAnyObjectCallBack() {
            @Override
            public void requestDataResponse(WebResponse<Object> webResponse) {
                UIHelper.showAlertDialogWithCallback(webResponse.message, "Reset Password", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getBaseActivity().popStackTill(1);
                    }
                }, getContext());

            }

            @Override
            public void onError(Object object) {

            }
        });
    }
}
