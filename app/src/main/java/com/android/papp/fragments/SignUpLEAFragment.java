package com.android.papp.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.papp.R;
import com.android.papp.activities.HomeActivity;
import com.android.papp.adapters.recyleradapters.SpecialityAdapter;
import com.android.papp.callbacks.OnItemClickListener;
import com.android.papp.callbacks.OnSpinnerOKPressedListener;
import com.android.papp.constatnts.AppConstants;
import com.android.papp.constatnts.WebServiceConstants;
import com.android.papp.fragments.abstracts.BaseFragment;
import com.android.papp.helperclasses.ui.helper.KeyboardHelper;
import com.android.papp.helperclasses.ui.helper.UIHelper;
import com.android.papp.libraries.kmpautotextview.KMPAutoComplTextView;
import com.android.papp.managers.retrofit.GsonFactory;
import com.android.papp.managers.retrofit.WebServices;
import com.android.papp.models.IntWrapper;
import com.android.papp.models.SpinnerModel;
import com.android.papp.models.wrappers.WebResponse;
import com.android.papp.widget.AnyEditTextView;
import com.android.papp.widget.AnyTextView;
import com.android.papp.widget.TitleBar;
import com.google.gson.reflect.TypeToken;
import com.jcminarro.roundkornerlayout.RoundKornerRelativeLayout;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

/**
 * Created by hamza.ahmed on 7/19/2018.
 */

public class SignUpLEAFragment extends BaseFragment implements OnItemClickListener {


    Unbinder unbinder;


    @BindView(R.id.edtFirstName)
    AnyEditTextView edtFirstName;
    @BindView(R.id.edtLastName)
    AnyEditTextView edtLastName;
    @BindView(R.id.edtEmailAddress)
    AnyEditTextView edtEmailAddress;
    @BindView(R.id.edtPassword)
    AnyEditTextView edtPassword;
    @BindView(R.id.edtAgency)
    AnyEditTextView edtAgency;
    @BindView(R.id.txtDepartment)
    AnyTextView txtDepartment;
    @BindView(R.id.edtDesignation)
    AnyEditTextView edtDesignation;
    @BindView(R.id.edtSpecialization)
    KMPAutoComplTextView edtSpecialization;
    @BindView(R.id.imgAddSpecialization)
    ImageView imgAddSpecialization;
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


    @BindView(R.id.imgProfile)
    CircleImageView imgProfile;
    @BindView(R.id.btnCamera)
    ImageButton btnCamera;
    @BindView(R.id.contProfile)
    RoundKornerRelativeLayout contProfile;

    SpecialityAdapter adapter;
    ArrayList<SpinnerModel> arrSelectedSpecialization;
    ArrayList<SpinnerModel> arrDepartmentsSpinner;
    ArrayList<SpinnerModel> arrSpecializationSpinner;
    IntWrapper departmentPosition = new IntWrapper(0);
    IntWrapper specializationPosition = new IntWrapper(0);
    SpinnerModel selectedDepartment;


    private File fileTemporaryProfilePicture;


    public static SignUpLEAFragment newInstance() {

        Bundle args = new Bundle();

        SignUpLEAFragment fragment = new SignUpLEAFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public int getDrawerLockMode() {
        return 0;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_signup_lea;
    }

    @Override
    public void setTitlebar(TitleBar titleBar) {

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        arrSelectedSpecialization = new ArrayList<>();
        arrDepartmentsSpinner = new ArrayList<>();
        arrSpecializationSpinner = new ArrayList<>();
        adapter = new SpecialityAdapter(getContext(), arrSelectedSpecialization, this, true);
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


        if (onCreated) {
            adapter.notifyDataSetChanged();
            return;
        }


        getDepartmentSpinnerList();
        getSpecializations();

        arrSelectedSpecialization.clear();
        adapter.notifyDataSetChanged();
    }


    public void getDepartmentSpinnerList() {
        getBaseWebService().getAPIAnyObject(WebServiceConstants.PATH_GET_DEPARTMENTS, 0, 0, new WebServices.IRequestWebResponseAnyObjectCallBack() {
            @Override
            public void requestDataResponse(WebResponse<Object> webResponse) {
                Type type = new TypeToken<ArrayList<SpinnerModel>>() {
                }.getType();
                ArrayList<SpinnerModel> arrayList = GsonFactory.getSimpleGson()
                        .fromJson(GsonFactory.getSimpleGson().toJson(webResponse.result)
                                , type);


                arrDepartmentsSpinner.clear();
                arrDepartmentsSpinner.addAll(arrayList);

            }

            @Override
            public void onError(Object object) {

            }
        });
    }


    public void getSpecializations() {
        getBaseWebService().getAPIAnyObject(WebServiceConstants.PATH_GET_SPECIALIZATIONS, 0, 0, new WebServices.IRequestWebResponseAnyObjectCallBack() {
            @Override
            public void requestDataResponse(WebResponse<Object> webResponse) {
                Type type = new TypeToken<ArrayList<SpinnerModel>>() {
                }.getType();
                ArrayList<SpinnerModel> arrayList = GsonFactory.getSimpleGson()
                        .fromJson(GsonFactory.getSimpleGson().toJson(webResponse.result)
                                , type);


                arrSpecializationSpinner.clear();
                arrSpecializationSpinner.addAll(arrayList);

                edtSpecialization.setDatas(arrSpecializationSpinner);

            }

            @Override
            public void onError(Object object) {

            }
        });
    }


    @Override
    public void setListeners() {

        edtSpecialization.setOnPopupItemClickListener(new KMPAutoComplTextView.OnPopupItemClickListener() {
            @Override
            public void onPopupItemClick(SpinnerModel spinnerModel) {
                addSpecialization(spinnerModel);
            }
        });
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }


    private void bindRecyclerView() {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
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


    @Override
    public void onItemClick(int position, Object object, View view, Object type) {

        SpinnerModel model = (SpinnerModel) object;

        UIHelper.showAlertDialog("Are you sure you want to remove " + model.getText() + "?",
                "Alert", (dialogInterface, i) -> {
                    arrSelectedSpecialization.remove(position);
                    adapter.notifyDataSetChanged();
                }, getContext());
    }


    @OnClick({R.id.imgAddSpecialization, R.id.contBtnSignUp, R.id.contFacebookLogin, R.id.contTwitterLogin, R.id.contProfile, R.id.txtDepartment})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgAddSpecialization:
                if (edtSpecialization.getText().toString().isEmpty()) {
                    UIHelper.showShortToastInCenter(getContext(), "Please write something");
                    return;
                }
                addSpecialization(null);
                break;
            case R.id.contBtnSignUp:
                sharedPreferenceManager.putValue(AppConstants.KEY_IS_LEA, true);
                getBaseActivity().finish();
                getBaseActivity().openActivity(HomeActivity.class);
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
            case R.id.txtDepartment:
                UIHelper.showSpinnerDialog(this, arrDepartmentsSpinner, "Select Department", txtDepartment, null, new OnSpinnerOKPressedListener() {
                    @Override
                    public void onItemSelect(Object data) {
                        selectedDepartment = (SpinnerModel) data;
                    }
                }, departmentPosition);
                break;
        }
    }

    public void addSpecialization(SpinnerModel spinnerModel) {
        if (spinnerModel == null) {
            spinnerModel = new SpinnerModel(edtSpecialization.getText().toString());
        }

        for (SpinnerModel model : arrSelectedSpecialization) {
            if (model.getText().equalsIgnoreCase(spinnerModel.getText())) {
                UIHelper.showToast(getContext(), model.getText() + " is already present.");
                return;
            }
        }


        arrSelectedSpecialization.add(spinnerModel);

        edtSpecialization.setText("");
        KeyboardHelper.hideSoftKeyboardForced(getContext(), edtSpecialization);
        KeyboardHelper.hideSoftKeyboard(getContext(), edtSpecialization);

        KeyboardHelper.hideSoftKeyboardFromWindow(getContext(), edtSpecialization);

        adapter.notifyDataSetChanged();
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
