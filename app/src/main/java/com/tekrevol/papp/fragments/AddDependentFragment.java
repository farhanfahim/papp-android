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
import com.tekrevol.papp.managers.DateManager;
import com.tekrevol.papp.managers.retrofit.WebServices;
import com.tekrevol.papp.managers.retrofit.entities.MultiFileModel;
import com.tekrevol.papp.models.general.IntWrapper;
import com.tekrevol.papp.models.sending_model.DependantSendingModel;
import com.tekrevol.papp.models.sending_model.ParentSendingModel;
import com.tekrevol.papp.models.wrappers.UserModelWrapper;
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

public class AddDependentFragment extends BaseFragment {


    Unbinder unbinder;
    @BindView(R.id.contBack)
    LinearLayout contBack;
    @BindView(R.id.edtFirstName)
    AnyEditTextView edtFirstName;
    @BindView(R.id.edtLastName)
    AnyEditTextView edtLastName;
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

    IntWrapper genderPosition = new IntWrapper(0);
    @BindView(R.id.txtSetPassword)
    AnyTextView txtSetPassword;
    @BindView(R.id.edtEmailAddress)
    AnyEditTextView edtEmailAddress;
    @BindView(R.id.contEmailPassword)
    LinearLayout contEmailPassword;
    private File fileTemporaryProfilePicture;
    private ArrayList<DependantSendingModel> arrData;
    private boolean isRegistrationProcess = false;

    public static AddDependentFragment newInstance(boolean isRegistrationProcess, ArrayList<DependantSendingModel> arrData) {

        Bundle args = new Bundle();

        AddDependentFragment fragment = new AddDependentFragment();
        fragment.arrData = arrData;
        fragment.isRegistrationProcess = isRegistrationProcess;
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
        if (isRegistrationProcess) {
            titleBar.setVisibility(View.GONE);
        } else {
            titleBar.setVisibility(View.VISIBLE);
            titleBar.setTitle("Add Dependent");
            titleBar.showBackButton(getBaseActivity());
        }

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

        if (isRegistrationProcess) {
            txtUploadPhoto.setVisibility(View.GONE);

        } else {
            contBack.setVisibility(View.GONE);
        }


        contEmailPassword.setVisibility(View.GONE);
        txtSetPassword.setVisibility(View.GONE);
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


    @OnClick({R.id.contBack, R.id.txtUploadPhoto, R.id.contAddMore, R.id.contBtnSave, R.id.txtDOB, R.id.txtGender})
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
            case R.id.txtDOB:


                if (txtDOB.getStringTrimmed().isEmpty()) {
                    DateManager.showDatePicker(getContext(), txtDOB, AppConstants.DOB_FORMAT, null, true, false, null);
                } else {
                    DateManager.showDatePicker(getContext(), txtDOB, AppConstants.DOB_FORMAT, null, true, false, DateManager.getDate(sdfDOB, txtDOB.getStringTrimmed()));
                }


                break;
            case R.id.contBtnSave:

                // Validations

                if (!edtFirstName.testValidity()) {
                    UIHelper.showAlertDialog(getContext(), "Please enter valid First Name");
                    return;
                }

                if (!edtLastName.testValidity()) {
                    UIHelper.showAlertDialog(getContext(), "Please enter valid Last Name");
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


                if (isRegistrationProcess && arrData != null) {
                    arrData.add(dependant);
                    getBaseActivity().popBackStack();
                    return;
                }

                addDependentWebCall(dependant);


                break;
        }
    }


    public void addDependentWebCall(DependantSendingModel dependantSendingModel) {


        // Initialize Models

        ArrayList<MultiFileModel> arrMultiFileModel = new ArrayList<>();


        // Adding Images
        if (fileTemporaryProfilePicture != null) {
            arrMultiFileModel.add(new MultiFileModel(fileTemporaryProfilePicture, FileType.IMAGE, "image"));
        }


        getBaseWebService()
                .postMultipartAPI(WebServiceConstants.PATH_ADD_DEPENDENT, arrMultiFileModel, dependantSendingModel.toString(), new WebServices.IRequestWebResponseAnyObjectCallBack() {
                    @Override
                    public void requestDataResponse(WebResponse<Object> webResponse) {
                        updateUser(new WebServices.IRequestWebResponseAnyObjectCallBack() {
                            @Override
                            public void requestDataResponse(WebResponse<Object> webResponse) {
//                                getBaseActivity().isReloadFragmentOnBack = true;
                                getBaseActivity().popBackStack();
                            }

                            @Override
                            public void onError(Object object) {

                            }
                        });
                    }

                    @Override
                    public void onError(Object object) {

                    }
                });
    }
}
