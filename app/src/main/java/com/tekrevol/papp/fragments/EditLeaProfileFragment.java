package com.tekrevol.papp.fragments;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.jcminarro.roundkornerlayout.RoundKornerRelativeLayout;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.tekrevol.papp.R;
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
import com.tekrevol.papp.libraries.imageloader.ImageLoaderHelper;
import com.tekrevol.papp.libraries.kmpautotextview.KMPAutoComplTextView;
import com.tekrevol.papp.managers.retrofit.GsonFactory;
import com.tekrevol.papp.managers.retrofit.WebServices;
import com.tekrevol.papp.managers.retrofit.entities.MultiFileModel;
import com.tekrevol.papp.models.general.IntWrapper;
import com.tekrevol.papp.models.general.LocationModel;
import com.tekrevol.papp.models.general.SpinnerModel;
import com.tekrevol.papp.models.receiving_model.UserDetails;
import com.tekrevol.papp.models.receiving_model.UserModel;
import com.tekrevol.papp.models.sending_model.MentorEditProfileModel;
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

public class EditLeaProfileFragment extends BaseFragment implements OnItemClickListener {


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
    @BindView(R.id.edtAgency)
    AnyEditTextView edtAgency;
    @BindView(R.id.txtDepartment)
    AnyTextView txtDepartment;
    @BindView(R.id.txtLocation)
    AnyTextView txtLocation;
    @BindView(R.id.contLocation)
    LinearLayout contLocation;
    @BindView(R.id.edtDesignation)
    AnyEditTextView edtDesignation;
    @BindView(R.id.edtSpecialization)
    KMPAutoComplTextView edtSpecialization;
    @BindView(R.id.imgAddSpecialization)
    ImageView imgAddSpecialization;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.edtPersonalInfo)
    AnyEditTextView edtPersonalInfo;
    @BindView(R.id.contBtnUpdate)
    LinearLayout contBtnUpdate;
    @BindView(R.id.contLogin)
    LinearLayout contLogin;


    private File fileTemporaryProfilePicture;

    LocationModel locationModel;
    GooglePlaceHelper googlePlaceHelper;
    IntWrapper departmentPosition = new IntWrapper(0);
    SpinnerModel selectedDepartment;
    SpecialityAdapter adapter;
    ArrayList<SpinnerModel> arrSelectedSpecialization;
    ArrayList<SpinnerModel> arrSpecializationSpinner;
    ArrayList<SpinnerModel> arrDepartmentsSpinner;

    public static EditLeaProfileFragment newInstance() {

        Bundle args = new Bundle();

        EditLeaProfileFragment fragment = new EditLeaProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public int getDrawerLockMode() {
        return 0;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_edit_lea_profile;
    }

    @Override
    public void setTitlebar(TitleBar titleBar) {

        titleBar.resetViews();
        titleBar.setVisibility(View.GONE);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        arrSelectedSpecialization = new ArrayList<>();
        arrSpecializationSpinner = new ArrayList<>();
        arrDepartmentsSpinner = new ArrayList<>();
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

        getSpecializations();

        bindData();
    }

    public void bindData() {
        ImageLoaderHelper.loadImageWithAnimationsByPath(imgProfile, getCurrentUser().getUserDetails().getImage(), true);
        edtFirstName.setText(getCurrentUser().getUserDetails().getFirstName());
        edtLastName.setText(getCurrentUser().getUserDetails().getLastName());
        edtEmailAddress.setText(getCurrentUser().getEmail());
        edtAgency.setText(getCurrentUser().getUserDetails().getAgency());
        txtDepartment.setText(getHomeActivity().sparseArrayDepartments.get(getCurrentUser().getUserDetails().getDepartmentId(), ""));
        txtLocation.setText(getCurrentUser().getUserDetails().getAddress());
        edtDesignation.setText(getCurrentUser().getUserDetails().getDesignation());
        arrSelectedSpecialization.clear();
        arrSelectedSpecialization.addAll(getCurrentUser().getSpecializations());
        edtPersonalInfo.setText(getCurrentUser().getUserDetails().getAbout());


        selectedDepartment = new SpinnerModel(txtDepartment.getStringTrimmed(), getCurrentUser().getUserDetails().getDepartmentId());
        locationModel = new LocationModel(getCurrentUser().getUserDetails().getLat(), getCurrentUser().getUserDetails().getLng(), getCurrentUser().getUserDetails().getAddress());


        arrDepartmentsSpinner.clear();
        for (int i = 1; i < getHomeActivity().sparseArrayDepartments.size(); i++) {
            arrDepartmentsSpinner.add(new SpinnerModel(getHomeActivity().sparseArrayDepartments.get(i), i));
        }

        adapter.notifyDataSetChanged();
    }


    @Override
    public void setListeners() {
        edtSpecialization.setOnPopupItemClickListener(this::addSpecialization);
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


    @OnClick({R.id.imgAddSpecialization, R.id.contBtnUpdate, R.id.contBack, R.id.contProfile, R.id.txtLocation, R.id.txtDepartment})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgAddSpecialization:
                if (edtSpecialization.getText().toString().isEmpty()) {
                    UIHelper.showShortToastInCenter(getContext(), "Please write something");
                    return;
                }
                addSpecialization(null);
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
                }, EditLeaProfileFragment.this, onCreated);

                googlePlaceHelper.openAutocompleteActivity();

                break;

            case R.id.contBtnUpdate:
                editProfileCall();
                break;
            case R.id.contBack:
                getBaseActivity().onBackPressed();
                break;
            case R.id.contProfile:
                UIHelper.cropImagePicker(getContext(), this);
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

    @Override
    public void onItemClick(int position, Object object, View view, Object type) {

        SpinnerModel model = (SpinnerModel) object;

        UIHelper.showAlertDialog("Are you sure you want to remove " + model.getText() + "?",
                "Alert", (dialogInterface, i) -> {
                    arrSelectedSpecialization.remove(position);
                    adapter.notifyDataSetChanged();
                }, getContext());
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

    public void addSpecialization(SpinnerModel spinnerModel) {
        if (spinnerModel == null) {
            spinnerModel = new SpinnerModel(edtSpecialization.getText().toString());
        }

        for (SpinnerModel model : arrSelectedSpecialization) {
            if (model.getText().equalsIgnoreCase(spinnerModel.getText())) {
                UIHelper.showLongToastInCenter(getContext(), model.getText() + " is already present.");
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

        MentorEditProfileModel mentorEditProfileModel = new MentorEditProfileModel();
        ArrayList<MultiFileModel> arrMultiFileModel = new ArrayList<>();


        // Adding Images
        if (fileTemporaryProfilePicture != null) {
            arrMultiFileModel.add(new MultiFileModel(fileTemporaryProfilePicture, FileType.IMAGE, "image"));
        }


        // Setting data

        mentorEditProfileModel.setFirstName(edtFirstName.getStringTrimmed());
        mentorEditProfileModel.setLastName(edtLastName.getStringTrimmed());
        mentorEditProfileModel.setEmail(edtEmailAddress.getStringTrimmed());
        mentorEditProfileModel.setAgency(edtAgency.getStringTrimmed());
        mentorEditProfileModel.setDepartmentId(selectedDepartment.getId());
        mentorEditProfileModel.setAddress(locationModel.getAddress());
        mentorEditProfileModel.setLat(locationModel.getLat());
        mentorEditProfileModel.setLng(locationModel.getLng());
        mentorEditProfileModel.setDesignation(edtDesignation.getStringTrimmed());
        mentorEditProfileModel.setSpecialization(arrSelectedSpecialization);
        mentorEditProfileModel.setAbout(edtPersonalInfo.getStringTrimmed());

        new WebServices(getBaseActivity(), getToken(), BaseURLTypes.BASE_URL, true)
                .postMultipartAPI(WebServiceConstants.PATH_PROFILE, arrMultiFileModel, mentorEditProfileModel.toString(), new WebServices.IRequestWebResponseAnyObjectCallBack() {
                    @Override
                    public void requestDataResponse(WebResponse<Object> webResponse) {
                        UserModel currentUser = sharedPreferenceManager.getCurrentUser();

                        UserDetails userDetails = getGson().fromJson(getGson().toJson(webResponse.result), UserDetails.class);

                        JsonArray specialization = getGson().toJsonTree(webResponse.result).getAsJsonObject().getAsJsonArray("specialization");
                        if (specialization != null) {
                            ArrayList<SpinnerModel> arrayList = new ArrayList<>();
                            for (JsonElement jsonElement : specialization) {
                                arrayList.add(getGson().fromJson(jsonElement, SpinnerModel.class));
                            }
                            currentUser.setSpecializations(arrayList);
                        }


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

}
