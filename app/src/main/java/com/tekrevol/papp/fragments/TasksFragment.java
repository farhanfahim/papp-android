package com.tekrevol.papp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.android.papp.R;
import com.tekrevol.papp.adapters.recyleradapters.OnGoingTaskAdapter;
import com.tekrevol.papp.adapters.recyleradapters.TaskAdapter;
import com.tekrevol.papp.callbacks.OnItemClickListener;
import com.tekrevol.papp.constatnts.Constants;
import com.tekrevol.papp.fragments.abstracts.BaseFragment;
import com.tekrevol.papp.models.general.SpinnerModel;
import com.tekrevol.papp.widget.AnyTextView;
import com.tekrevol.papp.widget.TitleBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class TasksFragment extends BaseFragment implements OnItemClickListener {


    Unbinder unbinder;


    OnGoingTaskAdapter adpOnGoingTask;
    ArrayList<SpinnerModel> arrOnGoingTask;


    TaskAdapter adapterCompletedTasks;
    ArrayList<SpinnerModel> arrCompletedTasks;


    TaskAdapter adapterUpcomingSession;
    ArrayList<SpinnerModel> arrUpcomingTasks;
    @BindView(R.id.rvOnGoingTasks)
    RecyclerView rvOnGoingTasks;
    @BindView(R.id.txtViewAllCompletedTasks)
    AnyTextView txtViewAllCompletedTasks;
    @BindView(R.id.rvCompletedTasks)
    RecyclerView rvCompletedTasks;
    @BindView(R.id.txtViewAllUpcomingTasks)
    AnyTextView txtViewAllUpcomingTasks;
    @BindView(R.id.rvUpcomingTasks)
    RecyclerView rvUpcomingTasks;


    public static TasksFragment newInstance() {

        Bundle args = new Bundle();

        TasksFragment fragment = new TasksFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        arrOnGoingTask = new ArrayList<>();
        adpOnGoingTask = new OnGoingTaskAdapter(getContext(), arrOnGoingTask, this);


        arrCompletedTasks = new ArrayList<>();
        adapterCompletedTasks = new TaskAdapter(getContext(), arrCompletedTasks, this, false);


        arrUpcomingTasks = new ArrayList<>();
        adapterUpcomingSession = new TaskAdapter(getContext(), arrUpcomingTasks, this, true);


    }

    @Override
    public int getDrawerLockMode() {
        return DrawerLayout.LOCK_MODE_LOCKED_CLOSED;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_tasks;
    }

    @Override
    public void setTitlebar(TitleBar titleBar) {
        titleBar.resetViews();
        titleBar.setVisibility(View.VISIBLE);
        titleBar.setTitle("Tasks");
        titleBar.showBackButton(getBaseActivity());
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        bindRecyclerView();

        arrOnGoingTask.clear();
        arrOnGoingTask.add(new SpinnerModel(""));

        arrCompletedTasks.clear();
        arrCompletedTasks.addAll(Constants.getAddDependentsArray());


        arrUpcomingTasks.clear();
        arrUpcomingTasks.addAll(Constants.getAddDependentsArray2());
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
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rvOnGoingTasks.setLayoutManager(mLayoutManager);
        ((DefaultItemAnimator) rvOnGoingTasks.getItemAnimator()).setSupportsChangeAnimations(false);
        rvOnGoingTasks.setAdapter(adpOnGoingTask);


        RecyclerView.LayoutManager mLayoutManager2 = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rvCompletedTasks.setLayoutManager(mLayoutManager2);
        ((DefaultItemAnimator) rvCompletedTasks.getItemAnimator()).setSupportsChangeAnimations(false);
        rvCompletedTasks.setAdapter(adapterCompletedTasks);


        RecyclerView.LayoutManager mLayoutManager3 = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rvUpcomingTasks.setLayoutManager(mLayoutManager3);
        ((DefaultItemAnimator) rvUpcomingTasks.getItemAnimator()).setSupportsChangeAnimations(false);
        rvUpcomingTasks.setAdapter(adapterUpcomingSession);


    }


    @Override
    public void onItemClick(int position, Object object, View view, Object type) {
        if (type instanceof String) {
            if (((String) type).equalsIgnoreCase(OnGoingTaskAdapter.class.getSimpleName())) {
                getBaseActivity().addDockableFragment(TaskDetailsFragment.newInstance(), true);
            } else if (((String) type).equalsIgnoreCase(TaskAdapter.class.getSimpleName())) {
                getBaseActivity().addDockableFragment(TaskSummaryFragment.newInstance(), true);
            } else if (((String) type).equalsIgnoreCase(TaskAdapter.class.getSimpleName() + "completed")) {
                showNextBuildToast();
            }


        }


    }

    @OnClick(R.id.txtViewAllCompletedTasks)
    public void onViewClicked() {
        showNextBuildToast();
    }
}
