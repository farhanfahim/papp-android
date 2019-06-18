package com.tekrevol.papp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.tekrevol.papp.R;
import com.tekrevol.papp.adapters.LoginPagerAdapter;
import com.tekrevol.papp.adapters.SignupPagerAdapter;
import com.tekrevol.papp.fragments.abstracts.BaseFragment;
import com.tekrevol.papp.widget.CustomViewPager;
import com.tekrevol.papp.widget.TitleBar;
import com.tekrevol.papp.fragments.abstracts.BaseFragment;
import com.tekrevol.papp.widget.CustomViewPager;
import com.tekrevol.papp.widget.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by hamza.ahmed on 7/19/2018.
 */

public class SignUpFragment extends BaseFragment {


    Unbinder unbinder;
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.viewpager)
    CustomViewPager viewpager;
    @BindView(R.id.contLogin)
    LinearLayout contLogin;

    private SignupPagerAdapter adapter;

    public static SignUpFragment newInstance() {

        Bundle args = new Bundle();

        SignUpFragment fragment = new SignUpFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public int getDrawerLockMode() {
        return 0;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_signup;
    }

    @Override
    public void setTitlebar(TitleBar titleBar) {
        titleBar.setVisibility(View.GONE);

    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setViewPagerAdapter();
    }


    private void setViewPagerAdapter() {
        adapter = new SignupPagerAdapter(getChildFragmentManager());
        viewpager.setAdapter(adapter);
        viewpager.setPagingEnabled(true);
        tabs.setupWithViewPager(viewpager);
//        if (isFromMentor) {
//            viewpager.setCurrentItem(1);
//        }

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
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.contBack)
    public void onViewClicked() {
        getBaseActivity().onBackPressed();
    }
}
