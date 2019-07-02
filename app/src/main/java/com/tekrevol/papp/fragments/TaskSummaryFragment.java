package com.tekrevol.papp.fragments;

import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ScrollView;

import com.tekrevol.papp.R;
import com.tekrevol.papp.constatnts.WebServiceConstants;
import com.tekrevol.papp.fragments.abstracts.BaseFragment;
import com.tekrevol.papp.helperclasses.ui.helper.UIHelper;
import com.tekrevol.papp.libraries.imageloader.ImageLoaderHelper;
import com.tekrevol.papp.managers.DateManager;
import com.tekrevol.papp.managers.retrofit.WebServices;
import com.tekrevol.papp.models.receiving_model.TaskReceivingModel;
import com.tekrevol.papp.models.sending_model.TaskAcceptSendingModel;
import com.tekrevol.papp.models.wrappers.WebResponse;
import com.tekrevol.papp.widget.AnyTextView;
import com.tekrevol.papp.widget.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by hamza.ahmed on 7/19/2018.
 */

public class TaskSummaryFragment extends BaseFragment {


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
    @BindView(R.id.txtAcceptChallenge)
    AnyTextView txtAcceptChallenge;
    @BindView(R.id.contParentLayout)
    ScrollView contParentLayout;
    private int status;
    private TaskReceivingModel taskReceivingModel;

    public static TaskSummaryFragment newInstance(TaskReceivingModel taskReceivingModel, int status) {

        Bundle args = new Bundle();

        TaskSummaryFragment fragment = new TaskSummaryFragment();
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
        return R.layout.fragment_task_summary;
    }

    @Override
    public void setTitlebar(TitleBar titleBar) {

        titleBar.resetViews();
        titleBar.setVisibility(View.VISIBLE);
        titleBar.setTitle("Task Summary");
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


        txtTitle.setText(taskReceivingModel.getTitle());
        ImageLoaderHelper.loadImageWithAnimationsByPath(imgTask, taskReceivingModel.getIcon(), false);
        txtDate.setText(DateManager.convertToUserTimeZone(taskReceivingModel.getCreatedAt()));
        txtDuration.setText(taskReceivingModel.getDuration() + " days");
        txtPoints.setText(taskReceivingModel.getRewardPoints() + " points");
        txtDesc.setText(taskReceivingModel.getDescription());

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

    @OnClick(R.id.txtAcceptChallenge)
    public void onViewClicked() {
        TaskAcceptSendingModel taskAcceptSendingModel = new TaskAcceptSendingModel();
        taskAcceptSendingModel.setTaskId(taskReceivingModel.getId());

        getBaseWebService().postAPIAnyObject(WebServiceConstants.PATH_ACCEPT_TASK, taskAcceptSendingModel.toString(), new WebServices.IRequestWebResponseAnyObjectCallBack() {
            @Override
            public void requestDataResponse(WebResponse<Object> webResponse) {
                UIHelper.showShortToastInCenter(getContext(), webResponse.message);
                getBaseActivity().onBackPressed();
            }

            @Override
            public void onError(Object object) {

            }
        });


    }
}
