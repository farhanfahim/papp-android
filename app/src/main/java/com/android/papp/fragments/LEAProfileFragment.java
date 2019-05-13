package com.android.papp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.papp.R;
import com.android.papp.activities.ChatActivity;
import com.android.papp.adapters.recyleradapters.MedalAdapter;
import com.android.papp.adapters.recyleradapters.SpecialityAdapter;
import com.android.papp.callbacks.OnItemClickListener;
import com.android.papp.constatnts.Constants;
import com.android.papp.fragments.abstracts.BaseFragment;
import com.android.papp.libraries.residemenu.ResideMenu;
import com.android.papp.models.SpinnerModel;
import com.android.papp.widget.AnyTextView;
import com.android.papp.widget.TitleBar;
import com.jcminarro.roundkornerlayout.RoundKornerLinearLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class LEAProfileFragment extends BaseFragment implements OnItemClickListener {


    Unbinder unbinder;


    @BindView(R.id.btnLeft1)
    TextView btnLeft1;
    @BindView(R.id.txtTitle)
    AnyTextView txtTitle;
    @BindView(R.id.btnRight1)
    ImageView btnRight1;
    @BindView(R.id.containerTitlebar1)
    LinearLayout containerTitlebar1;
    @BindView(R.id.txtName)
    AnyTextView txtName;
    @BindView(R.id.txtDesignation)
    AnyTextView txtDesignation;
    @BindView(R.id.txtLocation)
    AnyTextView txtLocation;
    @BindView(R.id.rvMilestones)
    RecyclerView rvMilestones;
    @BindView(R.id.contMilestones)
    RoundKornerLinearLayout contMilestones;
    @BindView(R.id.ratingbarDeliverySpeed)
    AppCompatRatingBar ratingbarDeliverySpeed;
    @BindView(R.id.txtReviews)
    AnyTextView txtReviews;
    @BindView(R.id.contReviews)
    LinearLayout contReviews;
    @BindView(R.id.rvInterest)
    RecyclerView rvInterest;


    SpecialityAdapter specialityAdapter;
    ArrayList<SpinnerModel> arrInterest;

    MedalAdapter medalAdapter;
    ArrayList<SpinnerModel> arrMedals;
    @BindView(R.id.txtEdit)
    AnyTextView txtEdit;
    @BindView(R.id.txtScheduleMeeting)
    AnyTextView txtScheduleMeeting;
    @BindView(R.id.imgChat)
    ImageView imgChat;
    @BindView(R.id.contChat)
    LinearLayout contChat;


    public static LEAProfileFragment newInstance() {

        Bundle args = new Bundle();

        LEAProfileFragment fragment = new LEAProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        arrInterest = new ArrayList<>();
        specialityAdapter = new SpecialityAdapter(getContext(), arrInterest, this, false);


        arrMedals = new ArrayList<>();
        medalAdapter = new MedalAdapter(getContext(), arrMedals, this);

    }

    @Override
    public int getDrawerLockMode() {
        return DrawerLayout.LOCK_MODE_LOCKED_CLOSED;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_lea_profile;
    }

    @Override
    public void setTitlebar(TitleBar titleBar) {
        titleBar.resetViews();
        titleBar.setVisibility(View.GONE);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        bindRecyclerView();

        arrInterest.clear();
        arrInterest.addAll(Constants.getSpeciality());

        arrMedals.clear();
        arrMedals.addAll(Constants.getMedalURL());


        if (isLEA()) {
            txtEdit.setVisibility(View.VISIBLE);
            btnRight1.setVisibility(View.GONE);
            txtScheduleMeeting.setVisibility(View.GONE);
            contChat.setVisibility(View.GONE);
            txtTitle.setText("My Profile");


        } else {
            txtScheduleMeeting.setVisibility(View.VISIBLE);
            btnRight1.setVisibility(View.VISIBLE);
            contChat.setVisibility(View.VISIBLE);
            txtEdit.setVisibility(View.GONE);
            txtTitle.setText("LEA Profile");
        }

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
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rvInterest.setLayoutManager(mLayoutManager);
        ((DefaultItemAnimator) rvInterest.getItemAnimator()).setSupportsChangeAnimations(false);
        rvInterest.setAdapter(specialityAdapter);


        RecyclerView.LayoutManager mLayoutManager2 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rvMilestones.setLayoutManager(mLayoutManager2);
        ((DefaultItemAnimator) rvMilestones.getItemAnimator()).setSupportsChangeAnimations(false);
        rvMilestones.setAdapter(medalAdapter);

    }


    @Override
    public void onItemClick(int position, Object object, View view, Object type) {

    }

    @OnClick({R.id.btnLeft1, R.id.txtScheduleMeeting, R.id.contChat, R.id.btnRight1, R.id.contMilestones, R.id.contReviews, R.id.txtEdit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.txtScheduleMeeting:
                getBaseActivity().addDockableFragment(ScheduleAMeetingFragment.newInstance(), true);
                break;
            case R.id.btnLeft1:
                getBaseActivity().onBackPressed();
                break;
            case R.id.btnRight1:
                getResideMenu().openMenu(ResideMenu.DIRECTION_RIGHT);
                break;
            case R.id.contMilestones:
                break;
            case R.id.contChat:
                getBaseActivity().openActivity(ChatActivity.class);
                break;
            case R.id.contReviews:
                getBaseActivity().addDockableFragment(ReviewsFragment.newInstance(), true);
                break;
            case R.id.txtEdit:
                getBaseActivity().addDockableFragment(EditLeaProfileFragment.newInstance(), false);
                break;
        }
    }
}
