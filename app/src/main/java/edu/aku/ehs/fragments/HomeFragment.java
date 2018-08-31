package edu.aku.ehs.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import edu.aku.ehs.R;
import edu.aku.ehs.enums.BaseURLTypes;
import edu.aku.ehs.fragments.abstracts.BaseFragment;
import edu.aku.ehs.managers.retrofit.WebServices;
import edu.aku.ehs.widget.TitleBar;

/**
 * Created by hamza.ahmed on 7/19/2018.
 */

public class HomeFragment extends BaseFragment {


    Unbinder unbinder;

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
//                getBaseActivity().addDockableFragment(StatsFragment.newInstance(), true);


                new WebServices(getContext(),
                        "",
                        BaseURLTypes.GET_EMP_DEPT_URL, false)
                        .webServiceGetEmployeeDepartDetail();
                break;
        }
    }
}
