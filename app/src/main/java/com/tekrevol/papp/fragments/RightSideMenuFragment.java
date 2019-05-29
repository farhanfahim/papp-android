package com.tekrevol.papp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.android.papp.R;
import com.tekrevol.papp.callbacks.OnNewPacketReceivedListener;
import com.tekrevol.papp.constatnts.AppConstants;
import com.tekrevol.papp.fragments.abstracts.BaseFragment;
import com.tekrevol.papp.fragments.abstracts.GenericContentFragment;
import com.tekrevol.papp.libraries.imageloader.ImageLoaderHelper;
import com.tekrevol.papp.models.receiving_model.UserModel;
import com.tekrevol.papp.widget.AnyTextView;
import com.tekrevol.papp.widget.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by khanhamza on 09-May-17.
 */

public class RightSideMenuFragment extends BaseFragment implements OnNewPacketReceivedListener {

    Unbinder unbinder;
    @BindView(R.id.imgBackground)
    ImageView imgBackground;
    @BindView(R.id.imgBack)
    ImageView imgBack;
    @BindView(R.id.txtUserName)
    AnyTextView txtUserName;
    @BindView(R.id.txtEmail)
    AnyTextView txtEmail;
    @BindView(R.id.contNofitications)
    LinearLayout contNofitications;
    @BindView(R.id.contEditProfile)
    LinearLayout contEditProfile;
    @BindView(R.id.contMyProfile)
    LinearLayout contMyProfile;
    @BindView(R.id.conSessionHistory)
    LinearLayout conSessionHistory;
    @BindView(R.id.contSessionPayoutHistory)
    LinearLayout contSessionPayoutHistory;
    @BindView(R.id.contMyGifts)
    LinearLayout contMyGifts;
    @BindView(R.id.contGiftsAndRewards)
    LinearLayout contGiftsAndRewards;
    @BindView(R.id.contMilestones)
    LinearLayout contMilestones;
    @BindView(R.id.contTasks)
    LinearLayout contTasks;
    @BindView(R.id.contAboutApp)
    LinearLayout contAboutApp;
    @BindView(R.id.contTermsAndConditions)
    LinearLayout contTermsAndConditions;
    @BindView(R.id.contLogout)
    LinearLayout contLogout;
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    @BindView(R.id.imgProfile)
    CircleImageView imgProfile;
    @BindView(R.id.contSponsors)
    LinearLayout contSponsors;

    public static RightSideMenuFragment newInstance() {

        Bundle args = new Bundle();

        RightSideMenuFragment fragment = new RightSideMenuFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_sidebar;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (sharedPreferenceManager.getBoolean(AppConstants.KEY_IS_MENTOR)) {
            contEditProfile.setVisibility(View.GONE);
            contMyProfile.setVisibility(View.VISIBLE);
        } else {
            contEditProfile.setVisibility(View.VISIBLE);
            contMyProfile.setVisibility(View.GONE);
        }
        ////        scrollToTop();


        if (getCurrentUser() != null) {
            ImageLoaderHelper.loadImageWithAnimationsByPath(imgProfile, getCurrentUser().getUserDetails().getImage());
            txtUserName.setText(getCurrentUser().getUserDetails().getFullName());
            txtEmail.setText(getCurrentUser().getEmail());
        }
    }

    @Override
    public void setTitlebar(TitleBar titleBar) {

    }

    @Override
    public void setListeners() {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public int getDrawerLockMode() {
        return DrawerLayout.LOCK_MODE_UNDEFINED;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }

//    public void scrollToTop() {
//        scrollView.post(new Runnable() {
//            public void run() {
//                scrollView.scrollTo(0, 0);
////                scrollView.fullScroll(View.FOCUS_UP);
//            }
//        });
//    }


    @OnClick({R.id.imgBack, R.id.contNofitications, R.id.contEditProfile, R.id.contMyProfile, R.id.conSessionHistory, R.id.contSessionPayoutHistory, R.id.contMyGifts, R.id.contGiftsAndRewards, R.id.contMilestones, R.id.contTasks, R.id.contSponsors, R.id.contAboutApp, R.id.contTermsAndConditions, R.id.contLogout})
    public void onViewClicked(View view) {

        closeMenu();

        switch (view.getId()) {
            case R.id.imgBack:
                break;
            case R.id.contNofitications:
                getBaseActivity().addDockableFragment(NotificationsFragment.newInstance(), false);
                break;
            case R.id.contEditProfile:
                getBaseActivity().addDockableFragment(EditCivilianProfileFragment.newInstance(), false);
                break;
            case R.id.contMyProfile:
                getBaseActivity().addDockableFragment(LEAProfileFragment.newInstance(null), false);
                break;
            case R.id.conSessionHistory:
                getBaseActivity().addDockableFragment(SessionHistoryFragment.newInstance(), false);
                break;
            case R.id.contSessionPayoutHistory:
                getBaseActivity().addDockableFragment(SessionPayoutHistoryFragment.newInstance(), false);
                break;
            case R.id.contMyGifts:
                getBaseActivity().addDockableFragment(MyGiftsFragment.newInstance(), false);
                break;
            case R.id.contGiftsAndRewards:
                getBaseActivity().addDockableFragment(GiftAndRewardsFragment.newInstance(), false);
                break;
            case R.id.contMilestones:
                getBaseActivity().addDockableFragment(MilestoneFragment.newInstance(), false);
                break;

            case R.id.contTasks:
                getBaseActivity().addDockableFragment(TasksFragment.newInstance(), false);
                break;
            case R.id.contSponsors:
                getBaseActivity().addDockableFragment(SponsorFragment.newInstance(), false);
                break;
            case R.id.contAboutApp:
                getBaseActivity().addDockableFragment(GenericContentFragment.newInstance("About", AppConstants.AboutUs), false);
                break;
            case R.id.contTermsAndConditions:
                break;
            case R.id.contLogout:
                logoutClick(this);
                break;
        }
    }
}
