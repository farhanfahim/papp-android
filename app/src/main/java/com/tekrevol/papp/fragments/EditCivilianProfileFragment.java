package com.tekrevol.papp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.jcminarro.roundkornerlayout.RoundKornerRelativeLayout;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.tekrevol.papp.R;
import com.tekrevol.papp.activities.HomeActivity;
import com.tekrevol.papp.constatnts.AppConstants;
import com.tekrevol.papp.constatnts.WebServiceConstants;
import com.tekrevol.papp.enums.BaseURLTypes;
import com.tekrevol.papp.enums.FileType;
import com.tekrevol.papp.fragments.abstracts.BaseFragment;
import com.tekrevol.papp.helperclasses.ui.helper.UIHelper;
import com.tekrevol.papp.libraries.imageloader.ImageLoaderHelper;
import com.tekrevol.papp.managers.retrofit.WebServices;
import com.tekrevol.papp.managers.retrofit.entities.MultiFileModel;
import com.tekrevol.papp.models.receiving_model.UserDetails;
import com.tekrevol.papp.models.receiving_model.UserModel;
import com.tekrevol.papp.models.sending_model.ParentEditProfileModel;
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

/**
 * Created by hamza.ahmed on 7/19/2018.
 */

public class EditCivilianProfileFragment extends BaseFragment {


    Unbinder unbinder;

    @BindView(R.id.contBack)
    LinearLayout contBack;
    @BindView(R.id.imgProfile)
    CircleImageView imgProfile;
    @BindView(R.id.btnCamera)
    ImageButton btnCamera;
    @BindView(R.id.contProfile)
    RoundKornerRelativeLayout contProfile;
    @BindView(R.id.edtFirstName)
    AnyEditTextView edtFirstName;
    @BindView(R.id.edtLastName)
    AnyEditTextView edtLastName;
    @BindView(R.id.edtEmailAddress)
    AnyTextView edtEmailAddress;
    @BindView(R.id.contAddDependents)
    LinearLayout contAddDependents;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.contBtnUpdate)
    LinearLayout contBtnUpdate;
    @BindView(R.id.contLogin)
    LinearLayout contLogin;


    private File fileTemporaryProfilePicture;

    public static EditCivilianProfileFragment newInstance() {

        Bundle args = new Bundle();

        EditCivilianProfileFragment fragment = new EditCivilianProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public int getDrawerLockMode() {
        return 0;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_edit_civilian_profile;
    }

    @Override
    public void setTitlebar(TitleBar titleBar) {

        titleBar.resetViews();
        titleBar.setVisibility(View.GONE);
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

        ImageLoaderHelper.loadImageWithAnimationsByPath(imgProfile, getCurrentUser().getUserDetails().getImage(), true);
        edtFirstName.setText(getCurrentUser().getUserDetails().getFirstName());
        edtLastName.setText(getCurrentUser().getUserDetails().getLastName());
        edtEmailAddress.setText(getCurrentUser().getEmail());

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


    @OnClick({R.id.contBtnUpdate, R.id.contBack, R.id.contProfile})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.contBtnUpdate:
                editProfileCall();
                break;
            case R.id.contBack:
                getBaseActivity().onBackPressed();
                break;
            case R.id.contProfile:
                UIHelper.cropImagePicker(getContext(), this);
                break;
        }
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
                        UserDetails userDetails = getGson().fromJson(getGson().toJson(webResponse.result), UserDetails.class);
                        UserModel currentUser = sharedPreferenceManager.getCurrentUser();
                        currentUser.setUserDetails(userDetails);
                        sharedPreferenceManager.putObject(AppConstants.KEY_CURRENT_USER_MODEL, currentUser);
                        getBaseActivity().finish();
                        getBaseActivity().openActivity(HomeActivity.class);
                    }

                    @Override
                    public void onError(Object object) {

                    }
                });
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
