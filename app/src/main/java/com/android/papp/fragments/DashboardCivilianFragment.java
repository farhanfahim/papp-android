package com.android.papp.fragments;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.android.papp.R;
import com.android.papp.fragments.abstracts.BaseFragment;
import com.android.papp.widget.AnyEditTextView;
import com.android.papp.widget.AnyTextView;
import com.android.papp.widget.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class DashboardCivilianFragment extends BaseFragment {


    @BindView(R.id.edtSearch)
    AnyEditTextView edtSearch;
    @BindView(R.id.imgFilter)
    ImageView imgFilter;
    @BindView(R.id.rvCategories)
    RecyclerView rvCategories;
    @BindView(R.id.txtViewAllTopLEA)
    AnyTextView txtViewAllTopLEA;
    @BindView(R.id.rvTopLEA)
    RecyclerView rvTopLEA;
    @BindView(R.id.txtViewAllMyLEA)
    AnyTextView txtViewAllMyLEA;
    @BindView(R.id.rvMyLEA)
    RecyclerView rvMyLEA;
    @BindView(R.id.txtViewAllDependents)
    AnyTextView txtViewAllDependents;
    @BindView(R.id.rvDependents)
    RecyclerView rvDependents;
    @BindView(R.id.contChat)
    LinearLayout contChat;
    @BindView(R.id.contSessions)
    LinearLayout contSessions;
    @BindView(R.id.imgHome)
    ImageView imgHome;
    @BindView(R.id.contBottomBar)
    RelativeLayout contBottomBar;
    Unbinder unbinder;

    public static DashboardCivilianFragment newInstance() {

        Bundle args = new Bundle();

        DashboardCivilianFragment fragment = new DashboardCivilianFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public int getDrawerLockMode() {
        return DrawerLayout.LOCK_MODE_LOCKED_CLOSED;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_dashboard_civilian;
    }

    @Override
    public void setTitlebar(TitleBar titleBar) {
        titleBar.setVisibility(View.VISIBLE);
        titleBar.setTitle("Dashboard");
        titleBar.showResideMenu(getHomeActivity());
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

    @OnClick({R.id.imgFilter, R.id.txtViewAllTopLEA, R.id.txtViewAllMyLEA, R.id.txtViewAllDependents, R.id.contChat, R.id.contSessions, R.id.imgHome})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgFilter:
                break;
            case R.id.txtViewAllTopLEA:
                break;
            case R.id.txtViewAllMyLEA:
                break;
            case R.id.txtViewAllDependents:
                break;
            case R.id.contChat:
                break;
            case R.id.contSessions:
                break;
            case R.id.imgHome:
                break;
        }
    }
}
