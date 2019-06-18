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

import com.tekrevol.papp.R;
import com.google.gson.reflect.TypeToken;
import com.jcminarro.roundkornerlayout.RoundKornerRelativeLayout;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.tekrevol.papp.activities.HomeActivity;
import com.tekrevol.papp.adapters.recyleradapters.SpecialityAdapter;
import com.tekrevol.papp.callbacks.OnItemClickListener;
import com.tekrevol.papp.callbacks.OnSpinnerOKPressedListener;
import com.tekrevol.papp.constatnts.AppConstants;
import com.tekrevol.papp.constatnts.WebServiceConstants;
import com.tekrevol.papp.enums.BaseURLTypes;
import com.tekrevol.papp.enums.FileType;
import com.tekrevol.papp.fragments.abstracts.BaseFragment;
import com.tekrevol.papp.helperclasses.GooglePlaceHelper;
import com.tekrevol.papp.helperclasses.ui.helper.KeyboardHelper;
import com.tekrevol.papp.helperclasses.ui.helper.UIHelper;
import com.tekrevol.papp.helperclasses.validator.PasswordValidation;
import com.tekrevol.papp.libraries.PasswordStrength;
import com.tekrevol.papp.libraries.kmpautotextview.KMPAutoComplTextView;
import com.tekrevol.papp.managers.retrofit.GsonFactory;
import com.tekrevol.papp.managers.retrofit.WebServices;
import com.tekrevol.papp.managers.retrofit.entities.MultiFileModel;
import com.tekrevol.papp.models.general.IntWrapper;
import com.tekrevol.papp.models.general.LocationModel;
import com.tekrevol.papp.models.general.SpinnerModel;
import com.tekrevol.papp.models.sending_model.MentorSendingModel;
import com.tekrevol.papp.models.wrappers.UserModelWrapper;
import com.tekrevol.papp.models.wrappers.WebResponse;
import com.tekrevol.papp.widget.AnyEditTextView;
import com.tekrevol.papp.widget.AnyTextView;
import com.tekrevol.papp.widget.TitleBar;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

/**
 * Created by hamza.ahmed on 7/19/2018.
 */

public class SignUpMentorFragment extends BaseFragment implements OnItemClickListener {


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
    ArrayList<SpinnerModel> arrSpecializationSpinner;
    ArrayList<SpinnerModel> arrDepartmentsSpinner;
    IntWrapper departmentPosition = new IntWrapper(0);
    SpinnerModel selectedDepartment;
    @BindView(R.id.txtPasswordStrength)
    AnyTextView txtPasswordStrength;
    @BindView(R.id.imgPasswordStrength)
    ImageView imgPasswordStrength;
    @BindView(R.id.txtLocation)
    AnyTextView txtLocation;

    @BindView(R.id.contLocation)
    LinearLayout contLocation;


    private File fileTemporaryProfilePicture;
    LocationModel locationModel;
    GooglePlaceHelper googlePlaceHelper;


    public static SignUpMentorFragment newInstance() {

        Bundle args = new Bundle();

        SignUpMentorFragment fragment = new SignUpMentorFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public int getDrawerLockMode() {
        return 0;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_signup_mentor;
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

        edtPassword.addValidator(new PasswordValidation());

        if (fileTemporaryProfilePicture != null) {
            setImageAfterResult(Uri.fromFile(fileTemporaryProfilePicture).toString());
        }


        if (onCreated) {
            adapter.notifyDataSetChanged();
            return;
        }


        getDepartmentSpinnerList();
        getSpecializations();

        adapter.notifyDataSetChanged();
    }


    public void getDepartmentSpinnerList() {
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put(WebServiceConstants.Q_PARAM_LIMIT, 0);
        queryMap.put(WebServiceConstants.Q_PARAM_OFFSET, 0);


        getBaseWebService().getAPIAnyObject(WebServiceConstants.PATH_GET_DEPARTMENTS, queryMap, new WebServices.IRequestWebResponseAnyObjectCallBack() {
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
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put(WebServiceConstants.Q_PARAM_LIMIT, 0);
        queryMap.put(WebServiceConstants.Q_PARAM_OFFSET, 0);

        getBaseWebService().getAPIAnyObject(WebServiceConstants.PATH_GET_SPECIALIZATIONS, queryMap, new WebServices.IRequestWebResponseAnyObjectCallBack() {
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


    @OnClick({R.id.imgAddSpecialization, R.id.contBtnSignUp, R.id.contFacebookLogin, R.id.contTwitterLogin, R.id.contProfile, R.id.txtDepartment, R.id.txtLocation})
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

            case R.id.txtLocation:
                googlePlaceHelper = new GooglePlaceHelper(getBaseActivity(), GooglePlaceHelper.PLACE_AUTOCOMPLETE, new GooglePlaceHelper.GooglePlaceDataInterface() {
                    @Override
                    public void onPlaceActivityResult(double longitude, double latitude, String locationName) {
                        txtLocation.setText(locationName);
                        locationModel = new LocationModel(latitude, longitude, locationName);
                    }

                    @Override
                    public void onError(String error) {

                    }
                }, SignUpMentorFragment.this);

                googlePlaceHelper.openAutocompleteActivity();

                break;
            case R.id.txtDepartment:
                UIHelper.showSpinnerDialog(this, arrDepartmentsSpinner, "Select Category", txtDepartment, null, new OnSpinnerOKPressedListener() {
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
        } else if (googlePlaceHelper != null) {
            googlePlaceHelper.onActivityResult(requestCode, resultCode, data);
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


        if (!edtAgency.testValidity()) {
            UIHelper.showAlertDialog(getContext(), "Agency not valid");
            return;
        }

        if (!edtDesignation.testValidity()) {
            UIHelper.showAlertDialog(getContext(), "Designation not valid");
            return;
        }

        if (txtDepartment.getStringTrimmed().isEmpty() && selectedDepartment == null) {
            UIHelper.showAlertDialog(getContext(), "Please select department");
            return;
        }


        if (txtLocation.getStringTrimmed().isEmpty() && locationModel == null) {
            UIHelper.showAlertDialog(getContext(), "Please select location");
            return;
        }


        if (arrSelectedSpecialization.isEmpty()) {
            UIHelper.showAlertDialog(getContext(), "Kindly add Specialization");
            return;
        }


        // Initialize Models

        MentorSendingModel mentorSendingModel = new MentorSendingModel();
        ArrayList<MultiFileModel> arrMultiFileModel = new ArrayList<>();


        // Adding Images
        if (fileTemporaryProfilePicture != null) {
            arrMultiFileModel.add(new MultiFileModel(fileTemporaryProfilePicture, FileType.IMAGE, "image"));
        }


        // Setting data

        mentorSendingModel.setFirstName(edtFirstName.getStringTrimmed());
        mentorSendingModel.setLastName(edtLastName.getStringTrimmed());
        mentorSendingModel.setEmail(edtEmailAddress.getStringTrimmed());
        mentorSendingModel.setPassword(edtPassword.getStringTrimmed());
        mentorSendingModel.setPasswordConfirmation(edtPassword.getStringTrimmed());
        mentorSendingModel.setDeviceType(AppConstants.DEVICE_OS_ANDROID);
        mentorSendingModel.setRole(AppConstants.MENTOR_ROLE);
        mentorSendingModel.setSpecialization(arrSelectedSpecialization);
        mentorSendingModel.setDepartmentId(selectedDepartment.getId());
        mentorSendingModel.setAgency(edtAgency.getStringTrimmed());
        mentorSendingModel.setDesignation(edtDesignation.getStringTrimmed());
        mentorSendingModel.setAddress(locationModel.getAddress());
        mentorSendingModel.setLat(locationModel.getLat());
        mentorSendingModel.setLng(locationModel.getLng());

        new WebServices(getBaseActivity(), "", BaseURLTypes.BASE_URL, true)
                .postMultipartAPI(WebServiceConstants.PATH_REGISTER, arrMultiFileModel, mentorSendingModel.toString(), new WebServices.IRequestWebResponseAnyObjectCallBack() {
                    @Override
                    public void requestDataResponse(WebResponse<Object> webResponse) {
                        UserModelWrapper userModelWrapper = getGson().fromJson(getGson().toJson(webResponse.result), UserModelWrapper.class);
                        sharedPreferenceManager.putObject(AppConstants.KEY_CURRENT_USER_MODEL, userModelWrapper.getUser());
                        sharedPreferenceManager.putValue(AppConstants.KEY_TOKEN, userModelWrapper.getUser().getAccessToken());
                        getBaseActivity().finish();
                        getBaseActivity().openActivity(HomeActivity.class);
                    }

                    @Override
                    public void onError(Object object) {

                    }
                });
    }


}
