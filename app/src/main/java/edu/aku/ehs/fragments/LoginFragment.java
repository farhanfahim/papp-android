package edu.aku.ehs.fragments;

import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import edu.aku.ehs.R;
import edu.aku.ehs.activities.HomeActivity;
import edu.aku.ehs.constatnts.AppConstants;
import edu.aku.ehs.enums.BaseURLTypes;
import edu.aku.ehs.fragments.abstracts.BaseFragment;
import edu.aku.ehs.helperclasses.ui.helper.KeyboardHelper;
import edu.aku.ehs.helperclasses.ui.helper.UIHelper;
import edu.aku.ehs.managers.retrofit.GsonFactory;
import edu.aku.ehs.managers.retrofit.WebServices;
import edu.aku.ehs.models.receiving_model.UserModel;
import edu.aku.ehs.widget.AnyEditTextView;
import edu.aku.ehs.widget.AnyTextView;
import edu.aku.ehs.widget.TitleBar;

import static edu.aku.ehs.constatnts.WebServiceConstants._token;

/**
 * Created by hamza.ahmed on 7/19/2018.
 */

public class LoginFragment extends BaseFragment {


    Unbinder unbinder;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.edtUserName)
    AnyEditTextView edtUserName;
    @BindView(R.id.edtPassword)
    AnyEditTextView edtPassword;
    @BindView(R.id.cbShowPassword)
    CheckBox cbShowPassword;
    @BindView(R.id.btnLogin)
    AnyTextView btnLogin;
    @BindView(R.id.contContent)
    LinearLayout contContent;

    public static LoginFragment newInstance() {

        Bundle args = new Bundle();

        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public int getDrawerLockMode() {
        return 0;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_login_v2;
    }

    @Override
    public void setTitlebar(TitleBar titleBar) {

    }

    @Override
    public void setListeners() {
        cbShowPassword.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                edtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            } else {
                edtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }
            edtPassword.setSelection(edtPassword.getText().length());
        });
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

        KeyboardHelper.showSoftKeyboard(getContext(), edtUserName);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btnLogin)
    public void onViewClicked() {
        if (edtUserName.testValidity() && edtPassword.testValidity())
            authenticateUserCall();
    }


    private void authenticateUserCall() {
        new WebServices(getContext(),
                _token,
                BaseURLTypes.AUTHENTICATE_USER_URL, true)
                .webServiceAuthenticateUser(edtUserName.getStringTrimmed(), edtPassword.getStringTrimmed(), new WebServices.IRequestWebResponseJustObjectCallBack() {
                    @Override
                    public void requestDataResponse(Object webResponse) {
                        if (webResponse instanceof String) {
                            boolean b = ((String) webResponse).equalsIgnoreCase("true");
                            if (b) {
                                getAuthenticatedUserDetails();
                            } else {
                                UIHelper.showShortToastInCenter(getContext(), "Username or Password is incorrect.");
                            }
                        }
                    }

                    @Override
                    public void onError(Object object) {
                        UIHelper.showShortToastInCenter(getContext(), "Something went wrong, API error");

                    }
                });
    }

    private void getAuthenticatedUserDetails() {
        new WebServices(getContext(),
                _token,
                BaseURLTypes.AUTHENTICATE_USER_URL, true)
                .webServiceGetAuthenticatedUserDetail(edtUserName.getStringTrimmed(), new WebServices.IRequestWebResponseJustObjectCallBack() {
                    @Override
                    public void requestDataResponse(Object webResponse) {
                        UserModel userModel = GsonFactory.getSimpleGson()
                                .fromJson(GsonFactory.getSimpleGson().toJson(webResponse), UserModel.class);

                        if (userModel.getName() == null || userModel.getName().isEmpty()) {
                            UIHelper.showShortToastInCenter(getContext(), "No Record Exist");
                        } else {
                            sharedPreferenceManager.putObject(AppConstants.KEY_CURRENT_USER_MODEL, userModel);
                            getBaseActivity().finish();
                            getBaseActivity().openActivity(HomeActivity.class);
                        }
                    }

                    @Override
                    public void onError(Object object) {
                        UIHelper.showShortToastInCenter(getContext(), "Something went wrong, API error");
                    }
                });
    }

}
