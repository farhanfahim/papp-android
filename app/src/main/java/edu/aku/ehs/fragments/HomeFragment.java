package edu.aku.ehs.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.crashlytics.android.Crashlytics;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import edu.aku.ehs.R;
import edu.aku.ehs.fragments.abstracts.BaseFragment;
import edu.aku.ehs.libraries.imageloader.ImageLoaderHelper;
import edu.aku.ehs.models.receiving_model.UserModel;
import edu.aku.ehs.widget.AnyTextView;
import edu.aku.ehs.widget.TitleBar;

/**
 * Created by hamza.ahmed on 7/19/2018.
 */

public class HomeFragment extends BaseFragment {


    Unbinder unbinder;
    @BindView(R.id.txtNurseName)
    AnyTextView txtNurseName;
    @BindView(R.id.txtNurseDesignation)
    AnyTextView txtNurseDesignation;
    @BindView(R.id.contSession)
    LinearLayout contSession;
    @BindView(R.id.contStats)
    LinearLayout contStats;

    UserModel userModel;
    @BindView(R.id.imgUser)
    CircleImageView imgUser;

    public static HomeFragment newInstance() {

        Bundle args = new Bundle();

        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public int getDrawerLockMode() {
        return 0;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_home;
    }

    @Override
    public void setTitlebar(TitleBar titleBar) {
        titleBar.resetViews();
        titleBar.setVisibility(View.VISIBLE);
        titleBar.setTitle("Home");
        titleBar.setRightButton2(getBaseActivity(), R.drawable.logout_icon, "", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoutClick(HomeFragment.this);
            }
        });
        titleBar.showBackButtonInvisible();
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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        userModel = sharedPreferenceManager.getCurrentUser();

        txtNurseName.setText(userModel.getName());
        txtNurseDesignation.setText(userModel.getRole());
        if (userModel.getUserPictureExists()) {
            ImageLoaderHelper.loadBase64Image(getContext(), imgUser, userModel.getUserPicture());
        } else {
            imgUser.setImageResource(R.drawable.female_icon_filled);
        }

//        logUser();

    }

    private void logUser() {
        // TODO: Use the current user's information
        // You can call any combination of these three methods
        Crashlytics.setUserIdentifier(userModel.getUserID());
//        Crashlytics.setUserEmail(userModel.);
        Crashlytics.setUserName(userModel.getName());
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }




    @OnClick({R.id.contSession, R.id.contStats})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.contSession:
                getBaseActivity().addDockableFragment(SessionListFragment.newInstance(), true);
                break;
            case R.id.contStats:
                getBaseActivity().addDockableFragment(StatsSessionFragment.newInstance(), true);
                 break;
        }
    }
}
