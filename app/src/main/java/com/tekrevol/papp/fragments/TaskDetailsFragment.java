package com.tekrevol.papp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.jcminarro.roundkornerlayout.RoundKornerLinearLayout;
import com.tekrevol.papp.R;
import com.tekrevol.papp.adapters.recyleradapters.AttachmentAdapter;
import com.tekrevol.papp.callbacks.OnItemClickListener;
import com.tekrevol.papp.constatnts.AppConstants;
import com.tekrevol.papp.constatnts.WebServiceConstants;
import com.tekrevol.papp.enums.FileType;
import com.tekrevol.papp.fragments.abstracts.BaseFragment;
import com.tekrevol.papp.fragments.abstracts.GenericDialogFragment;
import com.tekrevol.papp.helperclasses.DateHelper;
import com.tekrevol.papp.helperclasses.ui.helper.UIHelper;
import com.tekrevol.papp.libraries.imageloader.ImageLoaderHelper;
import com.tekrevol.papp.managers.DateManager;
import com.tekrevol.papp.managers.retrofit.WebServices;
import com.tekrevol.papp.managers.retrofit.entities.MultiFileModel;
import com.tekrevol.papp.models.receiving_model.TaskReceivingModel;
import com.tekrevol.papp.models.sending_model.TaskAcceptSendingModel;
import com.tekrevol.papp.models.wrappers.WebResponse;
import com.tekrevol.papp.widget.AnyTextView;
import com.tekrevol.papp.widget.TitleBar;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;
import static com.tekrevol.papp.constatnts.AppConstants.INPUT_DATE_FORMAT;
import static com.tekrevol.papp.constatnts.WebServiceConstants.WSC_KEY_ATTACHMENT;

/**
 * Created by hamza.ahmed on 7/19/2018.
 */

public class TaskDetailsFragment extends BaseFragment implements OnItemClickListener {


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
    @BindView(R.id.txtStartedDuration)
    AnyTextView txtStartedDuration;
    @BindView(R.id.rvAttachments)
    RecyclerView rvAttachments;
    @BindView(R.id.txtCancel)
    AnyTextView txtCancel;
    @BindView(R.id.contButtons)
    LinearLayout contButtons;
    @BindView(R.id.contAttachment)
    LinearLayout contAttachment;
    private TaskReceivingModel taskReceivingModel;
    private int status;

    AttachmentAdapter adapter;
    ArrayList<String> arrAttachments;
    private File fileTemporaryProfilePicture;
    public final int ATTACHMENT_LIMIT_COUNT = 5;

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

        arrAttachments = new ArrayList<>();
        adapter = new AttachmentAdapter(getContext(), arrAttachments, this);

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

        if (status == AppConstants.TASK_STATUS_PENDING_ADMIN_APPROVAL || status == AppConstants.TASK_STATUS_COMPLETED) {
            contButtons.setVisibility(View.GONE);
            contAttachment.setVisibility(View.GONE);

        }


        txtTitle.setText(taskReceivingModel.getTitle());
        ImageLoaderHelper.loadImageWithAnimationsByPath(imgTask, taskReceivingModel.getIcon(), false);
        txtDate.setText(DateManager.convertToUserTimeZone(taskReceivingModel.getTaskUsers().getStartDate()));
        txtDuration.setText(taskReceivingModel.getDuration() + " days");
        txtPoints.setText(taskReceivingModel.getRewardPoints() + " points");
        txtDesc.setText(taskReceivingModel.getDescription());

        txtStartedDuration.setText("Started: " + DateHelper.getElapsedTimeNew(DateManager.convertToUserTimeZone(taskReceivingModel.getTaskUsers().getStartDate()), INPUT_DATE_FORMAT));

    }


    private void bindRecyclerView() {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rvAttachments.setLayoutManager(mLayoutManager);
        ((DefaultItemAnimator) rvAttachments.getItemAnimator()).setSupportsChangeAnimations(false);
        rvAttachments.setAdapter(adapter);


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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                fileTemporaryProfilePicture = new File(result.getUri().getPath());
                arrAttachments.add(fileTemporaryProfilePicture.getPath());
                adapter.notifyDataSetChanged();
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                error.printStackTrace();
            }
        }
    }

    @OnClick({R.id.imgAttachment, R.id.txtSubmit, R.id.txtCancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgAttachment:

                GenericDialogFragment genericDialogFragment = new GenericDialogFragment();
                genericDialogFragment.setMessage("Please Choose Select Image to Select a single image from Gallery or to Capture image from Camera, Choose Select File(s) to select any File(s)");
                genericDialogFragment.setButton1("Select Image", () ->
                {
                    genericDialogFragment.dismiss();
                    if (arrAttachments.size() >= ATTACHMENT_LIMIT_COUNT) {
                        UIHelper.showLongToastInCenter(getContext(), "You can't add more attachments than " + ATTACHMENT_LIMIT_COUNT);
                        return;
                    }
                    UIHelper.cropImagePicker(getContext(), TaskDetailsFragment.this);
                });
                genericDialogFragment.setButton1Visibility(View.VISIBLE);
                genericDialogFragment.setButton2("Select File(s)", () -> {
                    genericDialogFragment.dismiss();
                    if (arrAttachments.size() >= ATTACHMENT_LIMIT_COUNT) {
                        UIHelper.showLongToastInCenter(getContext(), "You can't add more attachments than " + ATTACHMENT_LIMIT_COUNT);
                        return;
                    }
                    showFilePicker();
                });
                genericDialogFragment.setButton1Visibility(View.VISIBLE);
                genericDialogFragment.setTitle("Select Attachment");
                genericDialogFragment.show(getBaseActivity().getSupportFragmentManager(), "Attachment Dialog");

                break;
            case R.id.txtSubmit:

                ArrayList<MultiFileModel> multiFileModelArrayList = new ArrayList<>();

                for (String arrAttachment : arrAttachments) {
                    if (arrAttachment.contains(".pdf")) {
                        multiFileModelArrayList.add(new MultiFileModel(new File(arrAttachment), FileType.DOCUMENT, WSC_KEY_ATTACHMENT));
                    } else {
                        multiFileModelArrayList.add(new MultiFileModel(new File(arrAttachment), FileType.IMAGE, WSC_KEY_ATTACHMENT));
                    }
                }

                TaskAcceptSendingModel taskAcceptSendingModel = new TaskAcceptSendingModel();
                taskAcceptSendingModel.setTaskId(taskReceivingModel.getId());


                getBaseWebService().postMultipartAPIWithSameKeyAttachments(WebServiceConstants.PATH_COMPLETE_TASK, multiFileModelArrayList, taskAcceptSendingModel.toString(), new WebServices.IRequestWebResponseAnyObjectCallBack() {
                    @Override
                    public void requestDataResponse(WebResponse<Object> webResponse) {
                        getBaseActivity().popBackStack();
                        UIHelper.showAlertDialog(getContext(), webResponse.message);
                    }

                    @Override
                    public void onError(Object object) {

                    }
                });


                break;


            case R.id.txtCancel:
                showNextBuildToast();
                break;
        }
    }

    public void showFilePicker() {
        UIHelper.showFilePickerDialog(getContext(), "Select Attachment", false, files -> {
            arrAttachments.addAll(Arrays.asList(files));
            adapter.notifyDataSetChanged();
        });
    }

    @Override
    public void onItemClick(int position, Object object, View view, Object type) {
        arrAttachments.remove(position);
        adapter.notifyDataSetChanged();
    }
}
