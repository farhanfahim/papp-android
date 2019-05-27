package com.tekrevol.papp.fragments;

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
import android.widget.LinearLayout;

import com.android.papp.R;
import com.tekrevol.papp.adapters.recyleradapters.AddDependentsAdapter;
import com.tekrevol.papp.callbacks.OnItemAdd;
import com.tekrevol.papp.callbacks.OnItemClickListener;
import com.tekrevol.papp.fragments.abstracts.BaseFragment;
import com.tekrevol.papp.helperclasses.ui.helper.UIHelper;
import com.tekrevol.papp.models.SpinnerModel;
import com.tekrevol.papp.models.sending_model.DependantSendingModel;
import com.tekrevol.papp.widget.AnyEditTextView;
import com.tekrevol.papp.widget.TitleBar;
import com.jcminarro.roundkornerlayout.RoundKornerRelativeLayout;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.tekrevol.papp.fragments.abstracts.BaseFragment;
import com.tekrevol.papp.helperclasses.ui.helper.UIHelper;
import com.tekrevol.papp.models.SpinnerModel;
import com.tekrevol.papp.models.sending_model.DependantSendingModel;
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

/**
 * Created by hamza.ahmed on 7/19/2018.
 */

public class EditCivilianProfileFragment extends BaseFragment implements OnItemClickListener, OnItemAdd {


    Unbinder unbinder;

    AddDependentsAdapter adapter;
    ArrayList<DependantSendingModel> arrData;

    @BindView(R.id.contBack)
    LinearLayout contBack;
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
    @BindView(R.id.contBtnUpdate)
    LinearLayout contBtnUpdate;
    @BindView(R.id.contLogin)
    LinearLayout contLogin;
    @BindView(R.id.imgProfile)
    CircleImageView imgProfile;
    @BindView(R.id.btnCamera)
    ImageButton btnCamera;
    @BindView(R.id.contProfile)
    RoundKornerRelativeLayout contProfile;
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

        arrData = new ArrayList<>();
        adapter = new AddDependentsAdapter(getContext(), arrData, this);
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
        DependantSendingModel dependant = new DependantSendingModel();
        dependant.setFirstName("Test");
        dependant.setLastName("last");
        dependant.setGender(1);


        arrData.add(dependant);
        arrData.add(dependant);
        arrData.add(dependant);
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


    @OnClick({R.id.contAddDependents, R.id.contBtnUpdate, R.id.contBack, R.id.contProfile})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.contAddDependents:
                getBaseActivity().addDockableFragment(AddDependentFragment.newInstance(false, arrData), false);
                break;
            case R.id.contBtnUpdate:
                getBaseActivity().onBackPressed();
                break;
            case R.id.contBack:
                getBaseActivity().onBackPressed();
                break;
            case R.id.contProfile:
                UIHelper.cropImagePicker(getContext(), this);
                break;
        }
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
