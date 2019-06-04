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

import com.tekrevol.papp.R;
import com.google.gson.reflect.TypeToken;
import com.tekrevol.papp.adapters.recyleradapters.OnGoingTaskAdapter;
import com.tekrevol.papp.adapters.recyleradapters.TaskAdapter;
import com.tekrevol.papp.callbacks.OnItemClickListener;
import com.tekrevol.papp.constatnts.AppConstants;
import com.tekrevol.papp.constatnts.WebServiceConstants;
import com.tekrevol.papp.fragments.abstracts.BaseFragment;
import com.tekrevol.papp.managers.retrofit.GsonFactory;
import com.tekrevol.papp.managers.retrofit.WebServices;
import com.tekrevol.papp.models.receiving_model.TaskReceivingModel;
import com.tekrevol.papp.models.wrappers.WebResponse;
import com.tekrevol.papp.widget.AnyTextView;
import com.tekrevol.papp.widget.TitleBar;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.tekrevol.papp.constatnts.AppConstants.TASK_STATUS_AVAILABLE;

public class TasksFragment extends BaseFragment implements OnItemClickListener {


    Unbinder unbinder;


    OnGoingTaskAdapter adpOnGoingTask;
    ArrayList<TaskReceivingModel> arrOnGoingTask;


    TaskAdapter adapterCompletedTasks;
    ArrayList<TaskReceivingModel> arrCompletedTasks;


    TaskAdapter adapterUpcomingTasks;
    ArrayList<TaskReceivingModel> arrUpcomingTasks;

    TaskAdapter adapterPendingApproval;
    ArrayList<TaskReceivingModel> arrPendingApproval;


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
    @BindView(R.id.txtViewAllPendingApproval)
    AnyTextView txtViewAllPendingApproval;
    @BindView(R.id.rvPendingApproval)
    RecyclerView rvPendingApproval;


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
        adpOnGoingTask = new OnGoingTaskAdapter(getContext(), arrOnGoingTask, AppConstants.TASK_STATUS_ONGOING, this);


        arrCompletedTasks = new ArrayList<>();
        adapterCompletedTasks = new TaskAdapter(getContext(), arrCompletedTasks, AppConstants.TASK_STATUS_COMPLETED, this);


        arrUpcomingTasks = new ArrayList<>();
        adapterUpcomingTasks = new TaskAdapter(getContext(), arrUpcomingTasks, TASK_STATUS_AVAILABLE, this);


        arrPendingApproval = new ArrayList<>();
        adapterPendingApproval = new TaskAdapter(getContext(), arrPendingApproval, AppConstants.TASK_STATUS_PENDING_ADMIN_APPROVAL, this);


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

        getTasks(AppConstants.TASK_STATUS_ONGOING, false);
        getTasks(AppConstants.TASK_STATUS_COMPLETED, false);
        getTasks(AppConstants.TASK_STATUS_AVAILABLE, true);
        getTasks(AppConstants.TASK_STATUS_PENDING_ADMIN_APPROVAL, false);

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
        rvUpcomingTasks.setAdapter(adapterUpcomingTasks);


    }


    @Override
    public void onItemClick(int position, Object object, View view, Object type) {

        TaskReceivingModel model = (TaskReceivingModel) object;

        if (type instanceof Integer) {
            int status = (int) type;

            if (status == TASK_STATUS_AVAILABLE) {
                getBaseActivity().addDockableFragment(TaskSummaryFragment.newInstance(model, status), true);
            } else if (status == AppConstants.TASK_STATUS_COMPLETED) {
                showNextBuildToast();
            } else if (status == AppConstants.TASK_STATUS_PENDING_ADMIN_APPROVAL) {
                showNextBuildToast();
            } else if (status == AppConstants.TASK_STATUS_ONGOING) {
                getBaseActivity().addDockableFragment(TaskDetailsFragment.newInstance(model, status), true);
            }

        }


    }

    @OnClick(R.id.txtViewAllCompletedTasks)
    public void onViewClicked() {
        showNextBuildToast();
    }


    public void getTasks(int status, boolean isAvailable) {
        Map<String, Object> queryMap = new HashMap<>();
        if (isMentor()) {
            queryMap.put(WebServiceConstants.Q_PARAM_TYPE, AppConstants.TASK_TYPE_MENTOR);
        } else {
            queryMap.put(WebServiceConstants.Q_PARAM_TYPE, AppConstants.TASK_TYPE_USER);
        }

        if (isAvailable) {
            queryMap.put(WebServiceConstants.Q_PARAM_AVAILABLE, "true");
        } else {
            queryMap.put(WebServiceConstants.Q_PARAM_STATUS, status);
        }


        queryMap.put(WebServiceConstants.Q_PARAM_LIMIT, 3);
        queryMap.put(WebServiceConstants.Q_PARAM_OFFSET, 0);


        getBaseWebService().getAPIAnyObject(WebServiceConstants.PATH_TASKS, queryMap, new WebServices.IRequestWebResponseAnyObjectCallBack() {
            @Override
            public void requestDataResponse(WebResponse<Object> webResponse) {
                Type type = new TypeToken<ArrayList<TaskReceivingModel>>() {
                }.getType();
                ArrayList<TaskReceivingModel> arrayList = GsonFactory.getSimpleGson()
                        .fromJson(GsonFactory.getSimpleGson().toJson(webResponse.result)
                                , type);


                if (status == TASK_STATUS_AVAILABLE) {
                    arrUpcomingTasks.clear();
                    arrUpcomingTasks.addAll(arrayList);
                    adapterUpcomingTasks.notifyDataSetChanged();

                } else if (status == AppConstants.TASK_STATUS_COMPLETED) {

                    arrCompletedTasks.clear();
                    arrCompletedTasks.addAll(arrayList);
                    adapterCompletedTasks.notifyDataSetChanged();

                } else if (status == AppConstants.TASK_STATUS_PENDING_ADMIN_APPROVAL) {
                    arrPendingApproval.clear();
                    arrPendingApproval.addAll(arrayList);
                    adapterPendingApproval.notifyDataSetChanged();
                } else if (status == AppConstants.TASK_STATUS_ONGOING) {
                    arrOnGoingTask.clear();
                    arrOnGoingTask.addAll(arrayList);
                    adpOnGoingTask.notifyDataSetChanged();
                }


            }

            @Override
            public void onError(Object object) {

            }
        });
    }

}
