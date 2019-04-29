package com.android.papp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.android.papp.R;
import com.android.papp.fragments.abstracts.BaseFragment;
import com.android.papp.widget.AnyTextView;
import com.android.papp.widget.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by hamza.ahmed on 7/19/2018.
 */

public class AudioCallFragment extends BaseFragment {


    Unbinder unbinder;
    @BindView(R.id.imgProfile)
    CircleImageView imgProfile;
    @BindView(R.id.txtCallerName)
    AnyTextView txtCallerName;
    @BindView(R.id.imgMute)
    ImageView imgMute;
    @BindView(R.id.imgCancelCall)
    ImageView imgCancelCall;
    @BindView(R.id.txtTime)
    AnyTextView txtTime;


    public static AudioCallFragment newInstance() {

        Bundle args = new Bundle();

        AudioCallFragment fragment = new AudioCallFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public int getDrawerLockMode() {
        return 0;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_audio_call;
    }

    @Override
    public void setTitlebar(TitleBar titleBar) {

        titleBar.resetViews();
        titleBar.setVisibility(View.GONE);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick(R.id.imgCancelCall)
    public void onViewClicked() {
        getBaseActivity().onBackPressed();
     }
}
