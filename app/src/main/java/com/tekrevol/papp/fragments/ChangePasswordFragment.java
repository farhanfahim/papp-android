package com.tekrevol.papp.fragments;

import android.os.Bundle;
import androidx.annotation.Nullable;
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
import com.tekrevol.papp.models.sending_model.ChangeUserPasswordSendingModel;
import com.tekrevol.papp.models.sending_model.DependentChangePasswordSendingModel;
import com.tekrevol.papp.models.wrappers.WebResponse;
import com.tekrevol.papp.widget.AnyEditTextView;
import com.tekrevol.papp.widget.AnyTextView;
import com.tekrevol.papp.widget.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.tekrevol.papp.constatnts.WebServiceConstants.PATH_CHANGE_DEPENDENT_PASSWORD;
import static com.tekrevol.papp.constatnts.WebServiceConstants.PATH_CHANGE_PASSWORD;

/**
 * Created by hamza.ahmed on 7/19/2018.
 */

public class ChangePasswordFragment extends BaseFragment {


    Unbinder unbinder;
    @BindView(R.id.txtSetPassword)
    AnyTextView txtSetPassword;
    @BindView(R.id.txtEmailAddress)
    AnyTextView txtEmailAddress;
    @BindView(R.id.contEmailPassword)
    LinearLayout contEmailPassword;
    @BindView(R.id.edtCurrentPassword)
    AnyEditTextView edtCurrentPassword;
    @BindView(R.id.contCurrentPassword)
    LinearLayout contCurrentPassword;
    @BindView(R.id.edtNewPassword)
    AnyEditTextView edtNewPassword;
    @BindView(R.id.contNewPassword)
    LinearLayout contNewPassword;
    @BindView(R.id.contBtnSave)
    LinearLayout contBtnSave;
    @BindView(R.id.edtConfirmPassword)
    AnyEditTextView edtConfirmPassword;
    @BindView(R.id.contConfirmNewPassword)
    LinearLayout contConfirmNewPassword;

    private UserModel userModel;
    private int role;

    public static ChangePasswordFragment newInstance(UserModel userModel, int role) {

        Bundle args = new Bundle();

        ChangePasswordFragment fragment = new ChangePasswordFragment();
        fragment.userModel = userModel;
        fragment.role = role;
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public int getDrawerLockMode() {
        return 0;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_change_password;
    }

    @Override
    public void setTitlebar(TitleBar titleBar) {
        titleBar.setVisibility(View.VISIBLE);
        if (role == AppConstants.DEPENDENT_ROLE) {
            titleBar.setTitle("Set Password");
        } else {
            titleBar.setTitle("Change Password");
        }
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

        txtEmailAddress.setText(userModel.getEmail());

        if (role == AppConstants.DEPENDENT_ROLE) {
            contCurrentPassword.setVisibility(View.GONE);
            contConfirmNewPassword.setVisibility(View.GONE);
        }

        edtCurrentPassword.addValidator(new PasswordValidation());
        edtNewPassword.addValidator(new PasswordValidation());
        edtConfirmPassword.addValidator(new PasswordValidation(edtNewPassword));

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

                if (role == AppConstants.DEPENDENT_ROLE) {
                    changeDependentPassword();
                } else {
                    changeUserPassword();
                }

                break;
        }
    }

    public void changeDependentPassword() {
        // Validations


        if (!edtNewPassword.testValidity()) {
            UIHelper.showAlertDialog(getContext(), "Please enter valid New Password");
            return;
        }

        DependentChangePasswordSendingModel dependentChangePasswordSendingModel = new DependentChangePasswordSendingModel();
        dependentChangePasswordSendingModel.setEmail(txtEmailAddress.getStringTrimmed());
        dependentChangePasswordSendingModel.setPassword(edtNewPassword.getStringTrimmed());

        getBaseWebService().postAPIAnyObject(PATH_CHANGE_DEPENDENT_PASSWORD, dependentChangePasswordSendingModel.toString(), new WebServices.IRequestWebResponseAnyObjectCallBack() {
            @Override
            public void requestDataResponse(WebResponse<Object> webResponse) {
                UIHelper.showToast(getContext(), webResponse.message);
                getBaseActivity().popBackStack();
            }

            @Override
            public void onError(Object object) {

            }
        });
    }


    public void changeUserPassword() {
        // Validations

        if (!edtCurrentPassword.testValidity()) {
            UIHelper.showAlertDialog(getContext(), "Please enter valid Current Password");
            return;
        }


        if (!edtNewPassword.testValidity()) {
            UIHelper.showAlertDialog(getContext(), "Please enter valid New Password");
            return;
        }


        if (!edtConfirmPassword.testValidity()) {
            UIHelper.showAlertDialog(getContext(), "Please enter valid Confirm Password");
            return;
        }

        ChangeUserPasswordSendingModel changeUserPasswordSendingModel = new ChangeUserPasswordSendingModel();
        changeUserPasswordSendingModel.setCurrentPassword(edtCurrentPassword.getStringTrimmed());
        changeUserPasswordSendingModel.setPassword(edtNewPassword.getStringTrimmed());
        changeUserPasswordSendingModel.setPasswordConfirmation(edtConfirmPassword.getStringTrimmed());

        getBaseWebService().postAPIAnyObject(PATH_CHANGE_PASSWORD, changeUserPasswordSendingModel.toString(), new WebServices.IRequestWebResponseAnyObjectCallBack() {
            @Override
            public void requestDataResponse(WebResponse<Object> webResponse) {
                UIHelper.showAlertDialogWithCallback(webResponse.message, "Change Password", (dialog, which) -> getBaseActivity().popStackTill(1), getContext());
            }

            @Override
            public void onError(Object object) {

            }
        });
    }
}
