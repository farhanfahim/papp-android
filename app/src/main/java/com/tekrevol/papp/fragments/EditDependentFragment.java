package com.tekrevol.papp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.tekrevol.papp.R;
import com.tekrevol.papp.activities.HomeActivity;
import com.tekrevol.papp.constatnts.AppConstants;
import com.tekrevol.papp.constatnts.Constants;
import com.tekrevol.papp.constatnts.WebServiceConstants;
import com.tekrevol.papp.enums.BaseURLTypes;
import com.tekrevol.papp.enums.FileType;
import com.tekrevol.papp.fragments.abstracts.BaseFragment;
import com.tekrevol.papp.helperclasses.ui.helper.UIHelper;
import com.tekrevol.papp.helperclasses.validator.PasswordValidation;
import com.tekrevol.papp.libraries.imageloader.ImageLoaderHelper;
import com.tekrevol.papp.managers.DateManager;
import com.tekrevol.papp.managers.retrofit.WebServices;
import com.tekrevol.papp.managers.retrofit.entities.MultiFileModel;
import com.tekrevol.papp.models.general.IntWrapper;
import com.tekrevol.papp.models.receiving_model.UserDetails;
import com.tekrevol.papp.models.receiving_model.UserModel;
import com.tekrevol.papp.models.sending_model.DependantSendingModel;
import com.tekrevol.papp.models.sending_model.ParentEditProfileModel;
import com.tekrevol.papp.models.wrappers.WebResponse;
import com.tekrevol.papp.widget.AnyEditTextView;
import com.tekrevol.papp.widget.AnyTextView;
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
import static com.tekrevol.papp.managers.DateManager.sdfDOB;

/**
 * Created by hamza.ahmed on 7/19/2018.
 */

public class EditDependentFragment extends BaseFragment {


    Unbinder unbinder;


    IntWrapper genderPosition = new IntWrapper(0);
    @BindView(R.id.contBack)
    LinearLayout contBack;
    @BindView(R.id.txtSetPassword)
    AnyTextView txtSetPassword;
    @BindView(R.id.edtFirstName)
    AnyEditTextView edtFirstName;
    @BindView(R.id.edtLastName)
    AnyEditTextView edtLastName;
    @BindView(R.id.edtEmailAddress)
    AnyEditTextView edtEmailAddress;
    @BindView(R.id.contEmailPassword)
    LinearLayout contEmailPassword;
    @BindView(R.id.txtDOB)
    AnyTextView txtDOB;
    @BindView(R.id.txtGender)
    AnyTextView txtGender;
    @BindView(R.id.imgDependentProfile)
    CircleImageView imgDependentProfile;
    @BindView(R.id.txtUploadPhoto)
    AnyTextView txtUploadPhoto;
    @BindView(R.id.contAddMore)
    LinearLayout contAddMore;
    @BindView(R.id.contBtnSave)
    LinearLayout contBtnSave;

    private File fileTemporaryProfilePicture;
    private UserModel userModel;

    public static EditDependentFragment newInstance(UserModel userModel) {

        Bundle args = new Bundle();

        EditDependentFragment fragment = new EditDependentFragment();
        fragment.userModel = userModel;
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public int getDrawerLockMode() {
        return 0;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_add_dependent;
    }

    @Override
    public void setTitlebar(TitleBar titleBar) {
        titleBar.setVisibility(View.VISIBLE);
        titleBar.setTitle("Edit Dependent");
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

        contBack.setVisibility(View.GONE);
        contEmailPassword.setVisibility(View.VISIBLE);
        txtSetPassword.setVisibility(View.VISIBLE);


        edtFirstName.setText(userModel.getUserDetails().getFirstName());
        edtLastName.setText(userModel.getUserDetails().getLastName());
        edtEmailAddress.setText(userModel.getEmail());
        txtDOB.setText(userModel.getUserDetails().getDob());
        txtGender.setText(AppConstants.getGenderString(userModel.getUserDetails().getGender()));
        if (userModel.getUserDetails().getImage() != null && !userModel.getUserDetails().getImage().isEmpty()) {
            imgDependentProfile.setVisibility(View.VISIBLE);
            ImageLoaderHelper.loadImageWithAnimationsByPath(imgDependentProfile, userModel.getUserDetails().getImage(), true);
        }

    }


    private void showGenderSpinner() {
        UIHelper.showSpinnerDialog(this, Constants.getGenderArray(), "Select Gender", txtGender, null,
                data -> {
                }, genderPosition);
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
                    imgDependentProfile.setVisibility(View.VISIBLE);
                    ImageLoader.getInstance().displayImage(uploadFilePath, imgDependentProfile);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    @OnClick({R.id.contBack, R.id.txtUploadPhoto, R.id.contAddMore, R.id.contBtnSave, R.id.txtDOB, R.id.txtGender, R.id.txtSetPassword})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.contBack:
                getBaseActivity().onBackPressed();
                break;
            case R.id.txtUploadPhoto:
                UIHelper.cropImagePicker(getContext(), this);
                break;
            case R.id.contAddMore:
                break;
            case R.id.txtGender:
                showGenderSpinner();
                break;

            case R.id.txtSetPassword:
                getBaseActivity().popBackStack();
                getBaseActivity().addDockableFragment(ChangePasswordFragment.newInstance(userModel, AppConstants.DEPENDENT_ROLE), false);
                break;

            case R.id.txtDOB:

                if (txtDOB.getStringTrimmed().isEmpty()) {
                    DateManager.showDatePicker(getContext(), txtDOB, AppConstants.DOB_FORMAT, null, true, false, null);
                } else {
                    DateManager.showDatePicker(getContext(), txtDOB, AppConstants.DOB_FORMAT, null, true, false, DateManager.getDate(sdfDOB, txtDOB.getStringTrimmed()));
                }

                break;
            case R.id.contBtnSave:
                editDependent();

                break;
        }
    }

    public void editDependent() {
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
            UIHelper.showAlertDialog(getContext(), "Please enter valid Email Address");
            return;
        }


        if (txtGender.getStringTrimmed().isEmpty()) {
            UIHelper.showAlertDialog(getContext(), "Please select Gender");
            return;
        }


        if (txtDOB.getStringTrimmed().isEmpty()) {
            UIHelper.showAlertDialog(getContext(), "Please select Date of Birth");
            return;
        }


        DependantSendingModel dependant = new DependantSendingModel();
        dependant.setFirstName(edtFirstName.getStringTrimmed());
        dependant.setLastName(edtLastName.getStringTrimmed());
        dependant.setGender(AppConstants.getGenderInt(txtGender.getStringTrimmed()));
        dependant.setDob(txtDOB.getStringTrimmed());


        showAPIRemainingToast();
        getBaseActivity().popBackStack();
    }


    public void editProfileCall() {
        // Validations

        if (!edtFirstName.testValidity()) {
            UIHelper.showAlertDialog(getContext(), "Please enter valid First Name");
            return;
        }

        if (!edtLastName.testValidity()) {
            UIHelper.showAlertDialog(getContext(), "Please enter valid Last Name");
            return;
        }


        // Initialize Models

        ParentEditProfileModel parentEditProfileModel = new ParentEditProfileModel();
        ArrayList<MultiFileModel> arrMultiFileModel = new ArrayList<>();


        // Adding Images
        if (fileTemporaryProfilePicture != null) {
            arrMultiFileModel.add(new MultiFileModel(fileTemporaryProfilePicture, FileType.IMAGE, "image"));
        }


        // Setting data

        parentEditProfileModel.setFirstName(edtFirstName.getStringTrimmed());
        parentEditProfileModel.setLastName(edtLastName.getStringTrimmed());

        new WebServices(getBaseActivity(), getToken(), BaseURLTypes.BASE_URL, true)
                .postMultipartAPI(WebServiceConstants.PATH_PROFILE, arrMultiFileModel, parentEditProfileModel.toString(), new WebServices.IRequestWebResponseAnyObjectCallBack() {
                    @Override
                    public void requestDataResponse(WebResponse<Object> webResponse) {

                        UIHelper.showAlertDialog(getContext(), webResponse.result.toString());
//                        UserDetails userDetails = getGson().fromJson(getGson().toJson(webResponse.result), UserDetails.class);
//                        UserModel currentUser = sharedPreferenceManager.getCurrentUser();
//                        currentUser.setUserDetails(userDetails);
//                        sharedPreferenceManager.putObject(AppConstants.KEY_CURRENT_USER_MODEL, currentUser);
//                        getBaseActivity().finish();
//                        getBaseActivity().openActivity(HomeActivity.class);
                    }

                    @Override
                    public void onError(Object object) {

                    }
                });
    }

}
