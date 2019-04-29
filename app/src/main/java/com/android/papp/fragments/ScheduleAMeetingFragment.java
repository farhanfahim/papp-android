package com.android.papp.fragments;

import android.content.DialogInterface;
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

import com.android.papp.R;
import com.android.papp.adapters.recyleradapters.DependentsAdapter;
import com.android.papp.callbacks.OnCalendarUpdate;
import com.android.papp.callbacks.OnItemClickListener;
import com.android.papp.constatnts.Constants;
import com.android.papp.fragments.abstracts.BaseFragment;
import com.android.papp.helperclasses.GooglePlaceHelper;
import com.android.papp.helperclasses.ui.helper.UIHelper;
import com.android.papp.managers.DateManager;
import com.android.papp.models.SpinnerModel;
import com.android.papp.widget.AnyEditTextView;
import com.android.papp.widget.AnyTextView;
import com.android.papp.widget.TitleBar;
import com.jcminarro.roundkornerlayout.RoundKornerLinearLayout;

import java.util.ArrayList;
import java.util.Calendar;

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
    ArrayList<SpinnerModel> arrDependents;
    GooglePlaceHelper googlePlaceHelper;



    public static ScheduleAMeetingFragment newInstance() {

        Bundle args = new Bundle();

        ScheduleAMeetingFragment fragment = new ScheduleAMeetingFragment();
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
        titleBar.setTitle("Connect with Roger");
        titleBar.showResideMenu(getHomeActivity());
        titleBar.showBackButton(getBaseActivity());
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        bindRecyclerView();


        arrDependents.clear();
        arrDependents.addAll(Constants.getAddDependentsArray2());
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

        arrDependents.get(position).setSelected(!arrDependents.get(position).isSelected());
        dependentsAdapter.notifyDataSetChanged();


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (googlePlaceHelper != null) {
            googlePlaceHelper.onActivityResult(requestCode, resultCode, data);
        }

    }

    @OnClick({R.id.txtSetDateTime, R.id.contLocation, R.id.contSendRequest})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.txtSetDateTime:
                DateManager.showDateTimePicker(getContext(), null, new OnCalendarUpdate() {
                    @Override
                    public void onCalendarUpdate(Calendar calendar) {

                    }
                }, true);
                break;
            case R.id.contLocation:
                googlePlaceHelper = new GooglePlaceHelper(getBaseActivity(), GooglePlaceHelper.PLACE_PICKER, new GooglePlaceHelper.GooglePlaceDataInterface() {
                    @Override
                    public void onPlaceActivityResult(double longitude, double latitude, String locationName) {

                        txtLocation.setText(locationName);
                    }

                    @Override
                    public void onError(String error) {

                    }
                }, ScheduleAMeetingFragment.this);

                googlePlaceHelper.openAutocompleteActivity();
                break;
            case R.id.contSendRequest:
                UIHelper.showAlertDialogWithCallback("Your session has been created.", "Session",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                getBaseActivity().popStackTill(1);
                            }
                        }, getContext());
                break;
        }
    }
}
