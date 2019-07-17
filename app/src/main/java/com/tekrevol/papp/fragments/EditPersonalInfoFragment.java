package com.tekrevol.papp.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import androidx.annotation.Nullable;

import com.chinalwb.are.AREditor;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.tekrevol.papp.R;
import com.tekrevol.papp.activities.HomeActivity;
import com.tekrevol.papp.constatnts.AppConstants;
import com.tekrevol.papp.constatnts.WebServiceConstants;
import com.tekrevol.papp.enums.BaseURLTypes;
import com.tekrevol.papp.fragments.abstracts.BaseFragment;
import com.tekrevol.papp.helperclasses.StringHelper;
import com.tekrevol.papp.helperclasses.ui.helper.KeyboardHelper;
import com.tekrevol.papp.managers.retrofit.WebServices;
import com.tekrevol.papp.models.general.SpinnerModel;
import com.tekrevol.papp.models.receiving_model.UserDetails;
import com.tekrevol.papp.models.receiving_model.UserModel;
import com.tekrevol.papp.models.sending_model.MentorEditInfoModel;
import com.tekrevol.papp.models.wrappers.WebResponse;
import com.tekrevol.papp.widget.TitleBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class EditPersonalInfoFragment extends BaseFragment {


    AREditor areditor;
    FloatingActionButton fabDone;

    public static EditPersonalInfoFragment newInstance() {

        Bundle args = new Bundle();

        EditPersonalInfoFragment fragment = new EditPersonalInfoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getDrawerLockMode() {
        return 0;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_edit_personal_info;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        areditor = view.findViewById(R.id.areditor);
        fabDone = view.findViewById(R.id.fabDone);

        KeyboardHelper.showSoftKeyboardForcefully(getContext(), areditor.getARE());

        if (StringHelper.checkNotNullAndNotEmpty(getCurrentUser().getUserDetails().getAbout())) {
            areditor.fromHtml(getCurrentUser().getUserDetails().getAbout());
        }

    }

    @Override
    public void setTitlebar(TitleBar titleBar) {
        titleBar.resetViews();
        titleBar.setVisibility(View.VISIBLE);
        titleBar.setTitle("Edit Personal Info");
        titleBar.showBackButton(getBaseActivity());
    }

    @Override
    public void setListeners() {
        fabDone.setOnClickListener(v -> {
            editPersonalInfo();
        });
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }


    private void editPersonalInfo() {
        MentorEditInfoModel editInfoModel = new MentorEditInfoModel();
        editInfoModel.setAbout(areditor.getHtml());

        getBaseWebService()
                .postMultipartAPI(WebServiceConstants.PATH_PROFILE, null, editInfoModel.toString(), new WebServices.IRequestWebResponseAnyObjectCallBack() {
                    @Override
                    public void requestDataResponse(WebResponse<Object> webResponse) {
                        UserModel currentUser = getCurrentUser();
                        currentUser.getUserDetails().setAbout(areditor.getHtml());
                        sharedPreferenceManager.putObject(AppConstants.KEY_CURRENT_USER_MODEL, currentUser);
                        getBaseActivity().popBackStack();
                    }

                    @Override
                    public void onError(Object object) {

                    }
                });
    }
}
