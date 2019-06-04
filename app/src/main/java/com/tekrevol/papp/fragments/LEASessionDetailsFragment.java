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

import com.tekrevol.papp.R;
import com.tekrevol.papp.adapters.recyleradapters.DependentsAdapter;
import com.tekrevol.papp.callbacks.OnItemClickListener;
import com.tekrevol.papp.fragments.abstracts.BaseFragment;
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

public class LEASessionDetailsFragment extends BaseFragment implements OnItemClickListener {


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


    public static LEASessionDetailsFragment newInstance() {

        Bundle args = new Bundle();

        LEASessionDetailsFragment fragment = new LEASessionDetailsFragment();
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
        titleBar.setTitle("Session UserDetails");
        titleBar.showResideMenu(getHomeActivity());
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


        if (onCreated) {
            adapter.notifyDataSetChanged();
            return;
        }


        arrData.clear();
        arrData.addAll(getCurrentUser().getDependants());
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


    @OnClick({R.id.imgOneOnOne, R.id.imgCall, R.id.imgVdoCall, R.id.imgStop})
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
        }
    }
}
