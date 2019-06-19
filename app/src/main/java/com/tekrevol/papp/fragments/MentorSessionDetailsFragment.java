package com.tekrevol.papp.fragments;

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
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jcminarro.roundkornerlayout.RoundKornerLinearLayout;
import com.tekrevol.papp.R;
import com.tekrevol.papp.adapters.recyleradapters.DependentsAdapter;
import com.tekrevol.papp.callbacks.OnItemClickListener;
import com.tekrevol.papp.constatnts.AppConstants;
import com.tekrevol.papp.fragments.abstracts.BaseFragment;
import com.tekrevol.papp.helperclasses.GooglePlaceHelper;
import com.tekrevol.papp.managers.DateManager;
import com.tekrevol.papp.models.receiving_model.SessionRecievingModel;
import com.tekrevol.papp.models.receiving_model.SessionUsers;
import com.tekrevol.papp.models.receiving_model.UserModel;
import com.tekrevol.papp.widget.AnyTextView;
import com.tekrevol.papp.widget.TitleBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by hamza.ahmed on 7/19/2018.
 */

public class MentorSessionDetailsFragment extends BaseFragment implements OnItemClickListener {


    Unbinder unbinder;


    DependentsAdapter adapter;
    ArrayList<UserModel> arrData;

    @BindView(R.id.txtDesc)
    AnyTextView txtDesc;
    @BindView(R.id.rvDependents)
    RecyclerView rvDependents;
    @BindView(R.id.txtLocation)
    AnyTextView txtLocation;
    @BindView(R.id.txtDate)
    AnyTextView txtDate;
    @BindView(R.id.txtTime)
    AnyTextView txtTime;
    @BindView(R.id.txtMessage)
    AnyTextView txtMessage;
    @BindView(R.id.txtSessionType)
    AnyTextView txtSessionType;
    @BindView(R.id.imgOneOnOne)
    ImageView imgOneOnOne;
    @BindView(R.id.imgCall)
    ImageView imgCall;
    @BindView(R.id.imgVdoCall)
    ImageView imgVdoCall;
    @BindView(R.id.imgStop)
    ImageView imgStop;
    @BindView(R.id.txtTimer)
    AnyTextView txtTimer;
    @BindView(R.id.contAccept)
    LinearLayout contAccept;
    @BindView(R.id.contReject)
    LinearLayout contReject;
    @BindView(R.id.contRequest)
    LinearLayout contRequest;
    @BindView(R.id.contSessionStartOptions)
    RoundKornerLinearLayout contSessionStartOptions;
    @BindView(R.id.contPlace)
    LinearLayout contPlace;
    @BindView(R.id.txtSessionTypeDesc)
    AnyTextView txtSessionTypeDesc;
    @BindView(R.id.contSessionType)
    LinearLayout contSessionType;
    private SessionRecievingModel sessionRecievingModel;


    public static MentorSessionDetailsFragment newInstance(SessionRecievingModel sessionRecievingModel) {

        Bundle args = new Bundle();

        MentorSessionDetailsFragment fragment = new MentorSessionDetailsFragment();
        fragment.sessionRecievingModel = sessionRecievingModel;
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public int getDrawerLockMode() {
        return 0;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_session_detail_lea;
    }

    @Override
    public void setTitlebar(TitleBar titleBar) {

        titleBar.resetViews();
        titleBar.setVisibility(View.VISIBLE);
        titleBar.setTitle("Session Details");
//        titleBar.showResideMenu(getHomeActivity());
        titleBar.showBackButton(getBaseActivity());
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        arrData = new ArrayList<>();
        adapter = new DependentsAdapter(getContext(), arrData, this);
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


        bindData();


    }

    public void bindData() {

        if (sessionRecievingModel.getStatus() == AppConstants.SESSION_STATUS_PENDING) {
            contRequest.setVisibility(View.VISIBLE);
            contSessionStartOptions.setVisibility(View.GONE);
        } else if (sessionRecievingModel.getStatus() == AppConstants.SESSION_ACCEPTED_BY_MENTOR) {
            contRequest.setVisibility(View.GONE);
            contSessionStartOptions.setVisibility(View.VISIBLE);

            imgOneOnOne.setVisibility(View.GONE);
            imgCall.setVisibility(View.GONE);
            imgVdoCall.setVisibility(View.GONE);

            if (sessionRecievingModel.getSessionType() == AppConstants.SESSION_TYPE_AUDIO) {
                imgCall.setVisibility(View.VISIBLE);
            } else if (sessionRecievingModel.getSessionType() == AppConstants.SESSION_TYPE_VIDEO) {
                imgVdoCall.setVisibility(View.VISIBLE);
            } else if (sessionRecievingModel.getSessionType() == AppConstants.SESSION_TYPE_ONE_ON_ONE) {
                imgOneOnOne.setVisibility(View.VISIBLE);
            }

        } else {
            contRequest.setVisibility(View.GONE);
            contSessionStartOptions.setVisibility(View.GONE);
        }

        if (sessionRecievingModel.getSessionType() == AppConstants.SESSION_TYPE_ONE_ON_ONE) {
            contPlace.setVisibility(View.VISIBLE);
        } else {
            contPlace.setVisibility(View.GONE);
        }


        txtDesc.setText("Session with Dependent of " + sessionRecievingModel.getUser().getUserDetails().getFullName());
        txtLocation.setText(sessionRecievingModel.getAddress());
        txtDate.setText(DateManager.getDate(sessionRecievingModel.getScheduleDate(), AppConstants.DISPLAY_DATE_ONLY_FORMAT) + " - ");
        txtTime.setText(DateManager.getDate(sessionRecievingModel.getScheduleDate(), AppConstants.DISPLAY_TIME_ONLY_FORMAT));
        txtMessage.setText(sessionRecievingModel.getMessage());
        txtSessionTypeDesc.setText(sessionRecievingModel.getTypeText());


        arrData.clear();

        for (SessionUsers sessionUser : sessionRecievingModel.getSessionUsers()) {
            for (UserModel dependant : sessionRecievingModel.getUser().getDependants()) {
                if (dependant.getId() == sessionUser.getDependantId()) {
                    arrData.add(dependant);
                }
            }

        }

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
        rvDependents.setLayoutManager(mLayoutManager);
        ((DefaultItemAnimator) rvDependents.getItemAnimator()).setSupportsChangeAnimations(false);
        int resId = R.anim.layout_animation_fall_bottom;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getContext(), resId);
//        recylerView.setLayoutAnimation(animation);
        rvDependents.setAdapter(adapter);
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

    }


    @OnClick({R.id.imgOneOnOne, R.id.imgCall, R.id.imgVdoCall, R.id.txtLocation , R.id.imgStop, R.id.contAccept, R.id.contReject})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgOneOnOne:
                txtSessionType.setText("Stop Session");
                imgCall.setVisibility(View.GONE);
                imgOneOnOne.setVisibility(View.GONE);
                imgVdoCall.setVisibility(View.GONE);
                txtTimer.setVisibility(View.VISIBLE);
                imgStop.setVisibility(View.VISIBLE);
                break;
            case R.id.imgCall:
                getBaseActivity().addDockableFragment(AudioCallFragment.newInstance(), true);
                break;
            case R.id.imgVdoCall:
                getBaseActivity().addDockableFragment(VideoCallFragment.newInstance(), true);
                break;
            case R.id.imgStop:
                txtSessionType.setText("Start Session");
                imgCall.setVisibility(View.VISIBLE);
                imgOneOnOne.setVisibility(View.VISIBLE);
                imgVdoCall.setVisibility(View.VISIBLE);
                txtTimer.setVisibility(View.GONE);
                imgStop.setVisibility(View.GONE);
                break;

            case R.id.txtLocation:
                GooglePlaceHelper.intentOpenMap(getBaseActivity(), Double.valueOf(sessionRecievingModel.getLat()), Double.valueOf(sessionRecievingModel.getLng()), sessionRecievingModel.getAddress());
                break;

            case R.id.contAccept:

                break;

            case R.id.contReject:

                break;
        }
    }
}
