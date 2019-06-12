package com.tekrevol.papp.fragments;

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

import com.tekrevol.papp.R;
import com.jcminarro.roundkornerlayout.RoundKornerLinearLayout;
import com.tekrevol.papp.activities.ChatActivity;
import com.tekrevol.papp.adapters.recyleradapters.MedalAdapter;
import com.tekrevol.papp.adapters.recyleradapters.SpecialityAdapter;
import com.tekrevol.papp.callbacks.OnItemClickListener;
import com.tekrevol.papp.constatnts.Constants;
import com.tekrevol.papp.fragments.abstracts.BaseFragment;
import com.tekrevol.papp.helperclasses.GooglePlaceHelper;
import com.tekrevol.papp.helperclasses.StringHelper;
import com.tekrevol.papp.libraries.imageloader.ImageLoaderHelper;
import com.tekrevol.papp.libraries.residemenu.ResideMenu;
import com.tekrevol.papp.models.general.SpinnerModel;
import com.tekrevol.papp.models.receiving_model.UserModel;
import com.tekrevol.papp.widget.AnyTextView;
import com.tekrevol.papp.widget.TitleBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

public class LEAProfileFragment extends BaseFragment implements OnItemClickListener {


    Unbinder unbinder;


    SpecialityAdapter specialityAdapter;
    ArrayList<SpinnerModel> arrSpecialization;

    MedalAdapter medalAdapter;
    ArrayList<SpinnerModel> arrMedals;
    @BindView(R.id.btnLeft1)
    TextView btnLeft1;
    @BindView(R.id.txtTitle)
    AnyTextView txtTitle;
    @BindView(R.id.btnRight1)
    ImageView btnRight1;
    @BindView(R.id.containerTitlebar1)
    LinearLayout containerTitlebar1;
    @BindView(R.id.imgProfile)
    CircleImageView imgProfile;
    @BindView(R.id.txtEdit)
    AnyTextView txtEdit;
    @BindView(R.id.txtName)
    AnyTextView txtName;
    @BindView(R.id.txtDesignation)
    AnyTextView txtDesignation;
    @BindView(R.id.txtLocation)
    AnyTextView txtLocation;
    @BindView(R.id.imgChat)
    ImageView imgChat;
    @BindView(R.id.contChat)
    LinearLayout contChat;
    @BindView(R.id.rvMilestones)
    RecyclerView rvMilestones;
    @BindView(R.id.contMilestones)
    RoundKornerLinearLayout contMilestones;
    @BindView(R.id.txtAgency)
    AnyTextView txtAgency;
    @BindView(R.id.txtDepartment)
    AnyTextView txtDepartment;
    @BindView(R.id.txtPoints)
    AnyTextView txtPoints;
    @BindView(R.id.contPointsEarned)
    LinearLayout contPointsEarned;
    @BindView(R.id.ratingbar)
    AppCompatRatingBar ratingbar;
    @BindView(R.id.txtReviews)
    AnyTextView txtReviews;
    @BindView(R.id.contReviews)
    LinearLayout contReviews;
    @BindView(R.id.rvSpecialization)
    RecyclerView rvSpecialization;
    @BindView(R.id.txtPersonalInfo)
    AnyTextView txtPersonalInfo;
    @BindView(R.id.txtScheduleMeeting)
    AnyTextView txtScheduleMeeting;

    private UserModel mentorModel;


    public static LEAProfileFragment newInstance(UserModel mentorModel) {

        Bundle args = new Bundle();

        LEAProfileFragment fragment = new LEAProfileFragment();
        fragment.mentorModel = mentorModel;
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        arrSpecialization = new ArrayList<>();
        specialityAdapter = new SpecialityAdapter(getContext(), arrSpecialization, this, false);


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


        if (mentorModel == null) {
            mentorModel = getCurrentUser();
        }


        bindRecyclerView();


        if (isMentor()) {
            txtEdit.setVisibility(View.VISIBLE);
            btnRight1.setVisibility(View.GONE);
            txtScheduleMeeting.setVisibility(View.GONE);
            contChat.setVisibility(View.GONE);
            txtTitle.setText("My Profile");
            contPointsEarned.setVisibility(View.VISIBLE);
        } else {
            txtScheduleMeeting.setVisibility(View.VISIBLE);
            btnRight1.setVisibility(View.VISIBLE);
            contChat.setVisibility(View.VISIBLE);
            txtEdit.setVisibility(View.GONE);
            txtTitle.setText("Mentor Profile");
            contPointsEarned.setVisibility(View.GONE);
        }


        txtName.setText(mentorModel.getUserDetails().getFullName());
        txtDesignation.setText(mentorModel.getUserDetails().getDesignation());
        txtLocation.setText(mentorModel.getUserDetails().getAddress());
        ImageLoaderHelper.loadImageWithAnimationsByPath(imgProfile, mentorModel.getUserDetails().getImage(), true);
        txtAgency.setText(mentorModel.getUserDetails().getAgency());
        txtDepartment.setText(getHomeActivity().sparseArrayDepartments.get(mentorModel.getUserDetails().getDepartmentId(), ""));

        txtPoints.setText(mentorModel.getUserDetails().getTotalPoints() + " pts");
//        ratingbar.setRating(mentorModel.getUserDetails().get);
//        txtReviews.setText();     --> Reviews Count
        txtPersonalInfo.setText(mentorModel.getUserDetails().getAbout());
        arrSpecialization.clear();

        if (mentorModel.getSpecializations() != null && !mentorModel.getSpecializations().isEmpty()) {
            arrSpecialization.addAll(mentorModel.getSpecializations());
        }

        arrMedals.clear();
        arrMedals.addAll(Constants.getMedalURL());


        specialityAdapter.notifyDataSetChanged();


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
        rvSpecialization.setLayoutManager(mLayoutManager);
        ((DefaultItemAnimator) rvSpecialization.getItemAnimator()).setSupportsChangeAnimations(false);
        rvSpecialization.setAdapter(specialityAdapter);


        RecyclerView.LayoutManager mLayoutManager2 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rvMilestones.setLayoutManager(mLayoutManager2);
        ((DefaultItemAnimator) rvMilestones.getItemAnimator()).setSupportsChangeAnimations(false);
        rvMilestones.setAdapter(medalAdapter);

    }


    @Override
    public void onItemClick(int position, Object object, View view, Object type) {

    }

    @OnClick({R.id.btnLeft1, R.id.txtScheduleMeeting, R.id.contChat, R.id.btnRight1, R.id.contMilestones, R.id.contReviews, R.id.txtEdit, R.id.txtLocation})
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
                getBaseActivity().addDockableFragment(ReviewsFragment.newInstance(mentorModel), true);
                break;
            case R.id.txtEdit:
                getBaseActivity().addDockableFragment(EditLeaProfileFragment.newInstance(), false);
                break;
            case R.id.txtLocation:
                if (!StringHelper.isNullOrEmpty(getCurrentUser().getUserDetails().getAddress())) {
                    GooglePlaceHelper.intentOpenMap(getBaseActivity(), getCurrentUser().getUserDetails().getLat(), getCurrentUser().getUserDetails().getLng(), getCurrentUser().getUserDetails().getAddress());
                }
                break;
        }
    }
}
