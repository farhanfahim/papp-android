package com.tekrevol.papp.fragments;

import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.tekrevol.papp.R;
import com.tekrevol.papp.adapters.LoginPagerAdapter;
import com.tekrevol.papp.fragments.abstracts.BaseFragment;
import com.tekrevol.papp.widget.AnyTextView;
import com.tekrevol.papp.widget.CustomViewPager;
import com.tekrevol.papp.widget.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by hamza.ahmed on 7/19/2018.
 */

public class LoginFragment extends BaseFragment {


    Unbinder unbinder;
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.viewpager)
    CustomViewPager viewpager;
    @BindView(R.id.contLogin)
    LinearLayout contLogin;
    @BindView(R.id.txtForgotPassword)
    AnyTextView txtForgotPassword;
    private LoginPagerAdapter adapter;

    public static LoginFragment newInstance() {

        Bundle args = new Bundle();

        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public int getDrawerLockMode() {
        return 0;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_login;
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
        adapter = new LoginPagerAdapter(getChildFragmentManager());
        viewpager.setAdapter(adapter);
        viewpager.setPagingEnabled(true);
        tabs.setupWithViewPager(viewpager);

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

    @OnClick(R.id.txtForgotPassword)
    public void onViewClicked() {
        getBaseActivity().addDockableFragment(ForgotPasswordFragment.newInstance(), true);

    }
}
