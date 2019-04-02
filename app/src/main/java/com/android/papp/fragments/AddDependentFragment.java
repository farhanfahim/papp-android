package com.android.papp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.android.papp.R;
import com.android.papp.callbacks.OnItemAdd;
import com.android.papp.callbacks.OnSpinnerOKPressedListener;
import com.android.papp.constatnts.Constants;
import com.android.papp.fragments.abstracts.BaseFragment;
import com.android.papp.helperclasses.ui.helper.UIHelper;
import com.android.papp.managers.FileManager;
import com.android.papp.models.IntWrapper;
import com.android.papp.models.SpinnerModel;
import com.android.papp.widget.AnyEditTextView;
import com.android.papp.widget.AnyTextView;
import com.android.papp.widget.TitleBar;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.io.IOException;
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

public class AddDependentFragment extends BaseFragment {


    Unbinder unbinder;
    @BindView(R.id.contBack)
    LinearLayout contBack;
    @BindView(R.id.edtFirstName)
    AnyEditTextView edtFirstName;
    @BindView(R.id.edtLastName)
    AnyEditTextView edtLastName;
    @BindView(R.id.txtAge)
    AnyTextView txtAge;
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


    IntWrapper agePosition = new IntWrapper(0);
    IntWrapper genderPosition = new IntWrapper(0);
    private File fileTemporaryProfilePicture;
    private ArrayList<SpinnerModel> arrData;

    public static AddDependentFragment newInstance(ArrayList<SpinnerModel> arrData) {

        Bundle args = new Bundle();

        AddDependentFragment fragment = new AddDependentFragment();
        fragment.arrData = arrData;
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

        setSpinnerData();
    }


    private void setSpinnerData() {

    }

    private void showGenderSpinner() {
        UIHelper.showSpinnerDialog(this, Constants.getGenderArray(), "Select Gender", txtGender, null,
                new OnSpinnerOKPressedListener() {
                    @Override
                    public void onItemSelect(Object data) {

                    }
                }, genderPosition);
    }

    private void showAgeSpinner() {
        UIHelper.showSpinnerDialog(this, Constants.getAgeNumbersArray(), "Select Age", txtAge, null,
                new OnSpinnerOKPressedListener() {
                    @Override
                    public void onItemSelect(Object data) {

                    }
                }, agePosition);
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



    @OnClick({R.id.contBack, R.id.txtUploadPhoto, R.id.contAddMore, R.id.contBtnSave,R.id.txtAge, R.id.txtGender })
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
            case R.id.txtAge:
                showAgeSpinner();
                break;
            case R.id.contBtnSave:

                if (arrData != null) {
                    arrData.add(new SpinnerModel("John Doe"));
                    getBaseActivity().popBackStack();
                }
                break;
        }
    }
}
