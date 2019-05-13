package com.android.papp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.android.papp.activities.HomeActivity;
import com.android.papp.adapters.recyleradapters.SpecialityAdapter;
import com.android.papp.callbacks.OnItemClickListener;
import com.android.papp.constatnts.AppConstants;
import com.android.papp.fragments.abstracts.BaseFragment;
import com.android.papp.helperclasses.ui.helper.KeyboardHelper;
import com.android.papp.helperclasses.ui.helper.UIHelper;
import com.android.papp.models.SpinnerModel;
import com.android.papp.widget.AnyEditTextView;
import com.android.papp.widget.AnyTextView;
import com.android.papp.widget.TitleBar;
import com.jcminarro.roundkornerlayout.RoundKornerRelativeLayout;
import com.nostra13.universalimageloader.core.ImageLoader;
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
    @BindView(R.id.edtDepartment)
    AnyEditTextView edtDepartment;
    @BindView(R.id.edtDesignation)
    AnyEditTextView edtDesignation;
    @BindView(R.id.edtSpecialization)
    AnyEditTextView edtSpecialization;
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


    SpecialityAdapter adapter;
    ArrayList<SpinnerModel> arrData;
    @BindView(R.id.imgProfile)
    CircleImageView imgProfile;
    @BindView(R.id.btnCamera)
    ImageButton btnCamera;
    @BindView(R.id.contProfile)
    RoundKornerRelativeLayout contProfile;

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

        arrData = new ArrayList<>();
        adapter = new SpecialityAdapter(getContext(), arrData, this, true);
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

        arrData.clear();
        adapter.notifyDataSetChanged();
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
                    arrData.remove(position);
                    adapter.notifyDataSetChanged();
                }, getContext());
    }


    @OnClick({R.id.imgAddSpecialization, R.id.contBtnSignUp, R.id.contFacebookLogin, R.id.contTwitterLogin, R.id.contProfile})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgAddSpecialization:
                if (edtSpecialization.getStringTrimmed().isEmpty()) {
                    UIHelper.showShortToastInCenter(getContext(), "Please write something");
                    return;
                }
                arrData.add(new SpinnerModel(edtSpecialization.getStringTrimmed()));
                edtSpecialization.setText("");
                KeyboardHelper.hideSoftKeyboardForced(getContext(), edtSpecialization);
                adapter.notifyDataSetChanged();
                KeyboardHelper.hideSoftKeyboard(getContext(), view);
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
        }
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
