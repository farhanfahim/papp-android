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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.github.clans.fab.FloatingActionButton;
import com.tekrevol.papp.R;
import com.tekrevol.papp.adapters.recyleradapters.TaskAdapter;
import com.tekrevol.papp.callbacks.OnItemAdd;
import com.tekrevol.papp.callbacks.OnItemClickListener;
import com.tekrevol.papp.constatnts.AppConstants;
import com.tekrevol.papp.fragments.abstracts.BaseFragment;
import com.tekrevol.papp.helperclasses.ui.helper.UIHelper;
import com.tekrevol.papp.models.receiving_model.TaskReceivingModel;
import com.tekrevol.papp.widget.AnyEditTextView;
import com.tekrevol.papp.widget.AnyTextView;
import com.tekrevol.papp.widget.TitleBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.tekrevol.papp.constatnts.AppConstants.TASK_STATUS_AVAILABLE;

/**
 * Created by hamza.ahmed on 7/19/2018.
 */

public class ViewAllTasksFragment extends BaseFragment implements OnItemClickListener, OnItemAdd {


    Unbinder unbinder;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.empty_view)
    AnyTextView emptyView;
    @BindView(R.id.emptyview_container)
    LinearLayout emptyviewContainer;
    @BindView(R.id.edtSearchBar)
    AnyEditTextView edtSearchBar;
    @BindView(R.id.imgSearch)
    ImageView imgSearch;
    @BindView(R.id.contSearchBar)
    LinearLayout contSearchBar;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.contParent)
    RelativeLayout contParent;


    TaskAdapter adapter;
    ArrayList<TaskReceivingModel> arrData;
    private int taskType;


    public static ViewAllTasksFragment newInstance(ArrayList<TaskReceivingModel> arrTasks, int taskType) {

        Bundle args = new Bundle();

        ViewAllTasksFragment fragment = new ViewAllTasksFragment();
        fragment.arrData = arrTasks;
        fragment.taskType = taskType;
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public int getDrawerLockMode() {
        return 0;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_generic_recycler_view;
    }

    @Override
    public void setTitlebar(TitleBar titleBar) {

        titleBar.resetViews();
        titleBar.setVisibility(View.VISIBLE);
        if (taskType == AppConstants.TASK_STATUS_AVAILABLE) {
            titleBar.setTitle("Available Tasks");
        } else if (taskType == AppConstants.TASK_STATUS_COMPLETED) {
            titleBar.setTitle("Completed Tasks");
        } else if (taskType == AppConstants.TASK_STATUS_PENDING_ADMIN_APPROVAL) {
            titleBar.setTitle("Pending Tasks");
        }

        titleBar.showBackButton(getBaseActivity());
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

        adapter = new TaskAdapter(getContext(), arrData, taskType, this);

        fab.setVisibility(View.GONE);
        bindRecyclerView();

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
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        ((DefaultItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        int resId = R.anim.layout_animation_fall_bottom;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getContext(), resId);
//        recylerView.setLayoutAnimation(animation);
        recyclerView.setAdapter(adapter);
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

        TaskReceivingModel model = (TaskReceivingModel) object;

        if (type instanceof Integer) {
            int status = (int) type;

            if (status == TASK_STATUS_AVAILABLE) {
                getBaseActivity().addDockableFragment(TaskSummaryFragment.newInstance(model, status), true);
            } else if (status == AppConstants.TASK_STATUS_COMPLETED) {
                if (model.getTaskUsers() == null) {
                    UIHelper.showShortToastInCenter(getBaseActivity(), "Started date should not be empty");
                } else {
                    getBaseActivity().addDockableFragment(TaskDetailsFragment.newInstance(model, status), true);
                }
            } else if (status == AppConstants.TASK_STATUS_PENDING_ADMIN_APPROVAL) {
                if (model.getTaskUsers() == null) {
                    UIHelper.showShortToastInCenter(getBaseActivity(), "Started date should not be empty");
                } else {
                    getBaseActivity().addDockableFragment(TaskDetailsFragment.newInstance(model, status), true);
                }
            }
        }
    }

    @Override
    public void onItemAdd(Object object) {
//        arrCategories.add(new SpinnerModel("John Doe"));
//        adapter.notifyDataSetChanged();
    }

}
