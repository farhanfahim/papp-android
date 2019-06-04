package com.tekrevol.papp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ScrollView;

import com.tekrevol.papp.R;
import com.tekrevol.papp.fragments.abstracts.BaseFragment;
import com.tekrevol.papp.models.receiving_model.TaskReceivingModel;
import com.tekrevol.papp.widget.AnyTextView;
import com.tekrevol.papp.widget.TitleBar;
import com.jcminarro.roundkornerlayout.RoundKornerLinearLayout;
import com.tekrevol.papp.fragments.abstracts.BaseFragment;
import com.tekrevol.papp.widget.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by hamza.ahmed on 7/19/2018.
 */

public class TaskDetailsFragment extends BaseFragment {


    Unbinder unbinder;
    @BindView(R.id.txtTitle)
    AnyTextView txtTitle;
    @BindView(R.id.imgTask)
    CircleImageView imgTask;
    @BindView(R.id.txtDate)
    AnyTextView txtDate;
    @BindView(R.id.txtDuration)
    AnyTextView txtDuration;
    @BindView(R.id.txtPoints)
    AnyTextView txtPoints;
    @BindView(R.id.txtDesc)
    AnyTextView txtDesc;
    @BindView(R.id.imgAttachment)
    RoundKornerLinearLayout imgAttachment;
    @BindView(R.id.txtSubmit)
    AnyTextView txtSubmit;
    @BindView(R.id.contParentLayout)
    ScrollView contParentLayout;
    private TaskReceivingModel taskReceivingModel;
    private int status;

    public static TaskDetailsFragment newInstance(TaskReceivingModel taskReceivingModel, int status) {

        Bundle args = new Bundle();

        TaskDetailsFragment fragment = new TaskDetailsFragment();
        fragment.taskReceivingModel = taskReceivingModel;
        fragment.status = status;
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public int getDrawerLockMode() {
        return 0;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_task_detail;
    }

    @Override
    public void setTitlebar(TitleBar titleBar) {

        titleBar.resetViews();
        titleBar.setVisibility(View.VISIBLE);
        titleBar.setTitle("Task UserDetails");
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

    @OnClick({R.id.imgAttachment, R.id.txtSubmit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgAttachment:
                showNextBuildToast();
                break;
            case R.id.txtSubmit:
                getBaseActivity().onBackPressed();
                break;
        }
    }
}
