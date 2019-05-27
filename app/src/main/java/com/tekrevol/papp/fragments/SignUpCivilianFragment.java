package com.tekrevol.papp.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.papp.R;
import com.tekrevol.papp.activities.HomeActivity;
import com.tekrevol.papp.adapters.recyleradapters.AddDependentsAdapter;
import com.tekrevol.papp.callbacks.OnItemAdd;
import com.tekrevol.papp.callbacks.OnItemClickListener;
import com.tekrevol.papp.constatnts.AppConstants;
import com.tekrevol.papp.constatnts.WebServiceConstants;
import com.tekrevol.papp.enums.BaseURLTypes;
import com.tekrevol.papp.enums.FileType;
import com.tekrevol.papp.fragments.abstracts.BaseFragment;
import com.tekrevol.papp.helperclasses.ui.helper.UIHelper;
import com.tekrevol.papp.helperclasses.validator.PasswordValidation;
import com.tekrevol.papp.libraries.PasswordStrength;
import com.tekrevol.papp.managers.retrofit.WebServices;
import com.tekrevol.papp.managers.retrofit.entities.MultiFileModel;
import com.tekrevol.papp.models.sending_model.DependantSendingModel;
import com.tekrevol.papp.models.sending_model.ParentSendingModel;
import com.tekrevol.papp.models.wrappers.UserModelWrapper;
import com.tekrevol.papp.models.wrappers.WebResponse;
import com.tekrevol.papp.widget.AnyEditTextView;
import com.tekrevol.papp.widget.AnyTextView;
import com.tekrevol.papp.widget.TitleBar;
import com.jcminarro.roundkornerlayout.RoundKornerRelativeLayout;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.tekrevol.papp.activities.HomeActivity;
import com.tekrevol.papp.constatnts.AppConstants;
import com.tekrevol.papp.constatnts.WebServiceConstants;
import com.tekrevol.papp.enums.BaseURLTypes;
import com.tekrevol.papp.enums.FileType;
import com.tekrevol.papp.fragments.abstracts.BaseFragment;
import com.tekrevol.papp.helperclasses.ui.helper.UIHelper;
import com.tekrevol.papp.helperclasses.validator.PasswordValidation;
import com.tekrevol.papp.libraries.PasswordStrength;
import com.tekrevol.papp.models.sending_model.DependantSendingModel;
import com.tekrevol.papp.models.sending_model.ParentSendingModel;
import com.tekrevol.papp.models.wrappers.UserModelWrapper;
import com.tekrevol.papp.models.wrappers.WebResponse;
import com.tekrevol.papp.widget.AnyEditTextView;
import com.tekrevol.papp.widget.TitleBar;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;
import static com.tekrevol.papp.constatnts.AppConstants.PARENT_ROLE;

/**
 * Created by hamza.ahmed on 7/19/2018.
 */

public class SignUpCivilianFragment extends BaseFragment implements OnItemClickListener, OnItemAdd {


    Unbinder unbinder;
    @BindView(R.id.edtFirstName)
    AnyEditTextView edtFirstName;
    @BindView(R.id.edtLastName)
    AnyEditTextView edtLastName;
    @BindView(R.id.edtEmailAddress)
    AnyEditTextView edtEmailAddress;
    @BindView(R.id.edtPassword)
    AnyEditTextView edtPassword;
    @BindView(R.id.contAddDependents)
    LinearLayout contAddDependents;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.contBtnSignUp)
    LinearLayout contBtnSignUp;
    @BindView(R.id.txtOrLoginWith)
    AnyTextView txtOrLoginWith;
    @BindView(R.id.contFacebookLogin)
    LinearLayout contFacebookLogin;
    @BindView(R.id.contTwitterLogin)
    LinearLayout contTwitterLogin;
    @BindView(R.id.contSocialLogin)
    LinearLayout contSocialLogin;

    AddDependentsAdapter adapter;
    ArrayList<DependantSendingModel> arrDependents;

    @BindView(R.id.imgProfile)
    CircleImageView imgProfile;
    @BindView(R.id.btnCamera)
    ImageButton btnCamera;
    @BindView(R.id.contProfile)
    RoundKornerRelativeLayout contProfile;
    @BindView(R.id.txtPasswordStrength)
    AnyTextView txtPasswordStrength;
    @BindView(R.id.imgPasswordStrength)
    ImageView imgPasswordStrength;
    private File fileTemporaryProfilePicture;


    public static SignUpCivilianFragment newInstance() {

        Bundle args = new Bundle();

        SignUpCivilianFragment fragment = new SignUpCivilianFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public int getDrawerLockMode() {
        return 0;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_signup_civilian;
    }

    @Override
    public void setTitlebar(TitleBar titleBar) {

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        arrDependents = new ArrayList<>();
        adapter = new AddDependentsAdapter(getContext(), arrDependents, this);
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


        bindRecyclerView();
        edtPassword.addValidator(new PasswordValidation());

        if (fileTemporaryProfilePicture != null) {
            setImageAfterResult(Uri.fromFile(fileTemporaryProfilePicture).toString());
        }

        if (onCreated) {
            adapter.notifyDataSetChanged();
            return;
        }


    }

    @Override
    public void setListeners() {
        edtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                updatePasswordStrengthView(charSequence.toString());

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    private void updatePasswordStrengthView(String password) {

        if (password.isEmpty()) {
            txtPasswordStrength.setText("");
            imgPasswordStrength.setColorFilter(getContext().getResources().getColor(R.color.transparent));

            return;
        }

        PasswordStrength str = PasswordStrength.calculateStrength(password);
        txtPasswordStrength.setText(str.getText());
        txtPasswordStrength.setTextColor(str.getColor());
        imgPasswordStrength.setColorFilter(str.getColor());


    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }


    private void bindRecyclerView() {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        ((DefaultItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        int resId = R.anim.layout_animation_fall_bottom;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getContext(), resId);
//        recylerView.setLayoutAnimation(animation);
        recyclerView.setAdapter(adapter);
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


    @OnClick({R.id.contAddDependents, R.id.contBtnSignUp, R.id.contFacebookLogin, R.id.contTwitterLogin, R.id.contProfile})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.contAddDependents:
                getBaseActivity().addDockableFragment(AddDependentFragment.newInstance(true, arrDependents), false);
                break;
            case R.id.contBtnSignUp:
                signUpWebCall();
                break;
            case R.id.contFacebookLogin:
                showNextBuildToast();
                break;
            case R.id.contTwitterLogin:
                showNextBuildToast();
                break;
            case R.id.contProfile:
                UIHelper.cropImagePicker(getContext(), this);
                break;
        }
    }

    public void signUpWebCall() {
        // Validations

        if (!edtFirstName.testValidity()) {
            UIHelper.showAlertDialog(getContext(), "Please enter valid First Name");
            return;
        }

        if (!edtLastName.testValidity()) {
            UIHelper.showAlertDialog(getContext(), "Please enter valid Last Name");
            return;
        }

        if (!edtEmailAddress.testValidity()) {
            UIHelper.showAlertDialog(getContext(), "Email Address not valid");
            return;
        }


        if (!edtPassword.testValidity()) {
            UIHelper.showAlertDialog(getContext(), "Password not valid");
            return;
        }


        if (arrDependents.isEmpty()) {
            UIHelper.showAlertDialog(getContext(), "Kindly add Dependents");
            return;
        }


        // Initialize Models

        ParentSendingModel parentSendingModel = new ParentSendingModel();
        ArrayList<MultiFileModel> arrMultiFileModel = new ArrayList<>();


        // Adding Images
        if (fileTemporaryProfilePicture != null) {
            arrMultiFileModel.add(new MultiFileModel(fileTemporaryProfilePicture, FileType.IMAGE, "image"));
        }


        // Setting data

        parentSendingModel.setFirstName(edtFirstName.getStringTrimmed());
        parentSendingModel.setLastName(edtLastName.getStringTrimmed());
        parentSendingModel.setEmail(edtEmailAddress.getStringTrimmed());
        parentSendingModel.setPassword(edtPassword.getStringTrimmed());
        parentSendingModel.setPasswordConfirmation(edtPassword.getStringTrimmed());
        parentSendingModel.setDeviceType(AppConstants.DEVICE_OS_ANDROID);
        parentSendingModel.setRole(AppConstants.PARENT_ROLE);
        parentSendingModel.setDependant(arrDependents);

        new WebServices(getContext(), "", BaseURLTypes.BASE_URL, true)
                .postMultipartAPI(WebServiceConstants.PATH_REGISTER, arrMultiFileModel, parentSendingModel.toString(), new WebServices.IRequestWebResponseAnyObjectCallBack() {
                    @Override
                    public void requestDataResponse(WebResponse<Object> webResponse) {
                        UserModelWrapper userModelWrapper = getGson().fromJson(getGson().toJson(webResponse.result), UserModelWrapper.class);
                        sharedPreferenceManager.putValue(AppConstants.KEY_IS_MENTOR, false);
                        sharedPreferenceManager.putObject(AppConstants.KEY_CURRENT_USER_MODEL, userModelWrapper.getUser());
                        getBaseActivity().finish();
                        getBaseActivity().openActivity(HomeActivity.class);
                    }

                    @Override
                    public void onError(Object object) {

                    }
                });
    }

    @Override
    public void onItemClick(int position, Object object, View view, Object type) {

        DependantSendingModel model = (DependantSendingModel) object;

        UIHelper.showAlertDialog("Are you sure you want to remove " + model.getFirstName() + " " + model.getLastName() + "?",
                "Alert", (dialogInterface, i) -> {
                    arrDependents.remove(position);
                    adapter.notifyDataSetChanged();
                }, getContext());
    }

    @Override
    public void onItemAdd(Object object) {
//        arrCategories.add(new SpinnerModel("John Doe"));
//        adapter.notifyDataSetChanged();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                fileTemporaryProfilePicture = new File(result.getUri().getPath());
//                uploadImageFile(fileTemporaryProfilePicture.getPath(), result.getUri().toString());
                setImageAfterResult(result.getUri().toString());


            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                error.printStackTrace();
            }
        }
    }

    private void setImageAfterResult(final String uploadFilePath) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    ImageLoader.getInstance().displayImage(uploadFilePath, imgProfile);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


}
