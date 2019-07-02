package com.tekrevol.papp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.jcminarro.roundkornerlayout.RoundKornerLinearLayout;
import com.tekrevol.papp.R;
import com.tekrevol.papp.activities.MapsActivity;
import com.tekrevol.papp.adapters.recyleradapters.DependentsAdapter;
import com.tekrevol.papp.callbacks.OnItemClickListener;
import com.tekrevol.papp.constatnts.AppConstants;
import com.tekrevol.papp.constatnts.Constants;
import com.tekrevol.papp.constatnts.WebServiceConstants;
import com.tekrevol.papp.fragments.abstracts.BaseFragment;
import com.tekrevol.papp.helperclasses.GooglePlaceHelper;
import com.tekrevol.papp.helperclasses.ui.helper.UIHelper;
import com.tekrevol.papp.managers.DateManager;
import com.tekrevol.papp.managers.retrofit.WebServices;
import com.tekrevol.papp.models.general.IntWrapper;
import com.tekrevol.papp.models.general.LocationModel;
import com.tekrevol.papp.models.receiving_model.UserModel;
import com.tekrevol.papp.models.sending_model.SessionSendingModel;
import com.tekrevol.papp.models.wrappers.WebResponse;
import com.tekrevol.papp.widget.AnyEditTextView;
import com.tekrevol.papp.widget.AnyTextView;
import com.tekrevol.papp.widget.TitleBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ScheduleAMeetingFragment extends BaseFragment implements OnItemClickListener {

    @BindView(R.id.txtSetDateTime)
    AnyTextView txtSetDateTime;
    @BindView(R.id.txtLocation)
    AnyTextView txtLocation;
    @BindView(R.id.contLocation)
    RoundKornerLinearLayout contLocation;
    @BindView(R.id.txtDependentsCount)
    AnyTextView txtDependentsCount;
    @BindView(R.id.contDependents)
    RoundKornerLinearLayout contDependents;
    @BindView(R.id.rvDependents)
    RecyclerView rvDependents;
    @BindView(R.id.edtMessage)
    AnyEditTextView edtMessage;
    @BindView(R.id.cbOnline)
    CheckBox cbOnline;
    @BindView(R.id.contSendRequest)
    AnyTextView contSendRequest;
    Unbinder unbinder;


    DependentsAdapter dependentsAdapter;
    ArrayList<UserModel> arrDependents;
    GooglePlaceHelper googlePlaceHelper;
    @BindView(R.id.contDTTM)
    RoundKornerLinearLayout contDTTM;
    @BindView(R.id.rbAudioCall)
    RadioButton rbAudioCall;
    @BindView(R.id.rbVideoCall)
    RadioButton rbVideoCall;
    @BindView(R.id.rgCallType)
    RadioGroup rgCallType;
    @BindView(R.id.txtDuration)
    AnyTextView txtDuration;
    @BindView(R.id.contDuration)
    RoundKornerLinearLayout contDuration;
    private UserModel mentorModel;

    UserModel selectedDependent;
    LocationModel locationModel;
    IntWrapper durationPosition = new IntWrapper(0);


    public static ScheduleAMeetingFragment newInstance(UserModel mentorModel) {

        Bundle args = new Bundle();

        ScheduleAMeetingFragment fragment = new ScheduleAMeetingFragment();
        fragment.mentorModel = mentorModel;
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        arrDependents = new ArrayList<>();
        dependentsAdapter = new DependentsAdapter(getContext(), arrDependents, this);

    }

    @Override
    public int getDrawerLockMode() {
        return DrawerLayout.LOCK_MODE_LOCKED_CLOSED;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_schedule_a_meeting;
    }

    @Override
    public void setTitlebar(TitleBar titleBar) {
        titleBar.setVisibility(View.VISIBLE);
        titleBar.setTitle("Connect with " + mentorModel.getUserDetails().getFullName());
        titleBar.showResideMenu(getHomeActivity());
        titleBar.showBackButton(getBaseActivity());
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        bindRecyclerView();


        arrDependents.clear();
        arrDependents.addAll(getCurrentUser().getDependants());
        dependentsAdapter.notifyDataSetChanged();
    }

    @Override
    public void setListeners() {

        cbOnline.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                UIHelper.showShortToastInCenter(getBaseActivity(), "Please select online session type.");
                rgCallType.setVisibility(View.VISIBLE);
            } else {
                rgCallType.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    private void bindRecyclerView() {
        GridLayoutManager mLayoutManager2 = new GridLayoutManager(getContext(), 4);
        rvDependents.setLayoutManager(mLayoutManager2);
        ((DefaultItemAnimator) rvDependents.getItemAnimator()).setSupportsChangeAnimations(false);
        rvDependents.setAdapter(dependentsAdapter);


    }


    @Override
    public void onItemClick(int position, Object object, View view, Object type) {

        for (int i = 0; i < arrDependents.size(); i++) {
            arrDependents.get(i).setSelected(false);
        }

        arrDependents.get(position).setSelected(true);
        selectedDependent = arrDependents.get(position);
        dependentsAdapter.notifyDataSetChanged();


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (googlePlaceHelper != null) {
            googlePlaceHelper.onActivityResult(requestCode, resultCode, data);
        }

    }

    @OnClick({R.id.contDTTM, R.id.contLocation, R.id.contSendRequest, R.id.contDuration})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.contDTTM:
                DateManager.showDateTimePicker(getContext(), txtSetDateTime, null, true);
                break;
            case R.id.contLocation:
                googlePlaceHelper = new GooglePlaceHelper(getBaseActivity(), GooglePlaceHelper.PLACE_PICKER, new GooglePlaceHelper.GooglePlaceDataInterface() {
                    @Override
                    public void onPlaceActivityResult(double longitude, double latitude, String locationName) {
                        locationModel = new LocationModel(latitude, longitude, locationName);
                        txtLocation.setText(locationName);
                    }

                    @Override
                    public void onError(String error) {

                    }
                }, ScheduleAMeetingFragment.this, onCreated);


                googlePlaceHelper.openMapsActivity();



                break;
            case R.id.contSendRequest:
                createSession();
                break;
            case R.id.contDuration:
                showDurationSpinner();
                break;
        }
    }


    private void showDurationSpinner() {
        UIHelper.showSpinnerDialog(this, Constants.getDuration(), "Select Duration", txtDuration, null,
                data -> {
                }, durationPosition);
    }


    public void createSession() {

        // Validations

        if (txtSetDateTime.getStringTrimmed().isEmpty()) {
            UIHelper.showAlertDialog(getContext(), "Please select valid Date and Time");
            return;
        }

        if (cbOnline.isChecked()) {
            if (!rbAudioCall.isChecked() && !rbVideoCall.isChecked()) {
                UIHelper.showAlertDialog(getContext(), "Please select online session type");
                return;
            }
        } else {
            if (txtLocation.getStringTrimmed().isEmpty()) {
                UIHelper.showAlertDialog(getContext(), "Please select location");
                return;
            }
        }


        if (txtDuration.getStringTrimmed().isEmpty()) {
            UIHelper.showAlertDialog(getContext(), "Please select duration");
            return;
        }

        if (selectedDependent == null) {
            UIHelper.showAlertDialog(getContext(), "Please select a dependent");
            return;
        }


        // Setting model

        SessionSendingModel sessionSendingModel = new SessionSendingModel();
        sessionSendingModel.setScheduleDate(txtSetDateTime.getStringTrimmed());

        if (!txtLocation.getStringTrimmed().isEmpty()) {
            sessionSendingModel.setAddress(txtLocation.getStringTrimmed());
            sessionSendingModel.setLat(String.valueOf(locationModel.getLat()));
            sessionSendingModel.setLng(String.valueOf(locationModel.getLng()));
        }

        ArrayList<SessionSendingModel.SessionUser> sessionUserArrayList = new ArrayList<>();
        sessionUserArrayList.add(new SessionSendingModel.SessionUser(selectedDependent.getId()));

        sessionSendingModel.setSessionUser(sessionUserArrayList);
        sessionSendingModel.setMessage(edtMessage.getStringTrimmed());

        if (cbOnline.isChecked()) {
            sessionSendingModel.setIsOnline(1);
            if (rbAudioCall.isChecked()) {
                sessionSendingModel.setSessionType(AppConstants.SESSION_TYPE_AUDIO);
            } else if (rbVideoCall.isChecked()) {
                sessionSendingModel.setSessionType(AppConstants.SESSION_TYPE_VIDEO);
            }
        } else {
            sessionSendingModel.setIsOnline(0);
            sessionSendingModel.setSessionType(AppConstants.SESSION_TYPE_ONE_ON_ONE);
        }

        sessionSendingModel.setDuration(Constants.getDuration().get(durationPosition.value).getId());
        sessionSendingModel.setMentorId(mentorModel.getId());

        getBaseWebService().postAPIAnyObject(WebServiceConstants.PATH_SESSIONS, sessionSendingModel.toString(), new WebServices.IRequestWebResponseAnyObjectCallBack() {
            @Override
            public void requestDataResponse(WebResponse<Object> webResponse) {
                UIHelper.showAlertDialogWithCallback(webResponse.message, "Session",
                        (dialogInterface, i) -> getBaseActivity().popStackTill(1), getContext());
            }

            @Override
            public void onError(Object object) {

            }
        });
    }
}
