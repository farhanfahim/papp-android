package edu.aku.ehs.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.gson.reflect.TypeToken;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.lang.reflect.Type;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import edu.aku.ehs.R;
import edu.aku.ehs.callbacks.OnItemClickListener;
import edu.aku.ehs.constatnts.WebServiceConstants;
import edu.aku.ehs.enums.BaseURLTypes;
import edu.aku.ehs.enums.EmployeeSessionState;
import edu.aku.ehs.fragments.abstracts.BaseFragment;
import edu.aku.ehs.fragments.dialogs.PinDialogFragment;
import edu.aku.ehs.helperclasses.ui.helper.UIHelper;
import edu.aku.ehs.managers.retrofit.GsonFactory;
import edu.aku.ehs.managers.retrofit.WebServices;
import edu.aku.ehs.models.AssessmentQuestionModel;
import edu.aku.ehs.models.EmployeeSummaryDetailModel;
import edu.aku.ehs.models.SessionDetailModel;
import edu.aku.ehs.models.sending_model.EmployeeSendingModel;
import edu.aku.ehs.models.wrappers.WebResponse;
import edu.aku.ehs.widget.TitleBar;

public class EmployeeAssessmentDashboardFragment extends BaseFragment implements OnItemClickListener {


    Unbinder unbinder;
    @BindView(R.id.imgBanner)
    ImageView imgBanner;
    @BindView(R.id.imgAssessmentDone)
    ImageView imgAssessmentDone;
    @BindView(R.id.contAssessment)
    RelativeLayout contAssessment;
    @BindView(R.id.imgMeasurementsDone)
    ImageView imgMeasurementsDone;
    @BindView(R.id.contMeasurements)
    RelativeLayout contMeasurements;
    @BindView(R.id.imgProfileDone)
    ImageView imgProfileDone;
    @BindView(R.id.contRefer)
    RelativeLayout contRefer;
    @BindView(R.id.contParent)
    RelativeLayout contParent;
    private PinDialogFragment pinDialogFragment;
    int lastID = 0;


    private SessionDetailModel sessionDetailModel;
    EmployeeSummaryDetailModel employeeSummaryDetailModel;

    boolean isAssessmentDone = false;
    boolean isMeasurementDone = false;
    boolean isLabDone = false;

    KProgressHUD dialog;

    public static EmployeeAssessmentDashboardFragment newInstance(SessionDetailModel sessionDetailModel) {

        Bundle args = new Bundle();

        EmployeeAssessmentDashboardFragment fragment = new EmployeeAssessmentDashboardFragment();
        fragment.sessionDetailModel = sessionDetailModel;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getDrawerLockMode() {
        return DrawerLayout.LOCK_MODE_LOCKED_CLOSED;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_employee_assessment_dashboard;
    }

    @Override
    public void setTitlebar(TitleBar titleBar) {
        titleBar.resetViews();
        titleBar.setVisibility(View.VISIBLE);
        titleBar.showHome(getBaseActivity());
        titleBar.setTitle("Employee Dashboard");
        titleBar.showBackButton(getBaseActivity());
        titleBar.setEmployeeHeader(sessionDetailModel, getContext());
    }

    @Override
    public void setListeners() {
        pinDialogFragment = PinDialogFragment.newInstance(view -> {
        }, view -> {
            switch (lastID) {
                case R.id.contAssessment:
                    getBaseActivity().addDockableFragment(NewAssessmentViewPagerFragment.newInstance(sessionDetailModel), false);
                    break;
                case R.id.contMeasurements:
                    getBaseActivity().addDockableFragment(EmployeeAnthropometricMeasurmentsFragment.newInstance(sessionDetailModel), false);
                    break;
                case R.id.contRefer:
                    getBaseActivity().addDockableFragment(EmployeeProfileViewerFragment.newInstance(sessionDetailModel, employeeSummaryDetailModel), false);
                    break;
            }
        });
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

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
        dialog = UIHelper.getProgressHUD(getContext());


        EmployeeSendingModel model = new EmployeeSendingModel();
        model.setSessionID(sessionDetailModel.getSessionID());
        model.setEmployeeNo(sessionDetailModel.getEmployeeNo());

        isLabDone = sessionDetailModel.getisLabResultDone();

        dialog.show();
        getEmployeeSummaryDetail(model);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onItemClick(int position, Object object, View view) {

    }


    @OnClick({R.id.contAssessment, R.id.contMeasurements, R.id.contRefer})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.contAssessment:
                lastID = R.id.contAssessment;
                showPin();
                break;
            case R.id.contMeasurements:
                lastID = R.id.contMeasurements;
                showPin();
                break;
            case R.id.contRefer:
                lastID = R.id.contRefer;
                if (employeeSummaryDetailModel != null && isAssessmentDone && isLabDone && isMeasurementDone) {
                    showPin();
                } else {
                    UIHelper.showShortToastInCenter(getContext(), "Kindly complete all processes to view profile.");
                }
                break;
        }
    }

    private void showPin() {
        pinDialogFragment.setTitle("Enter Pin");
        pinDialogFragment.setCancelable(false);
        pinDialogFragment.clearField();
        pinDialogFragment.show(getBaseActivity().getSupportFragmentManager(), null);
    }


    private void getEmployeeSummaryDetail(EmployeeSendingModel model) {
        new WebServices(getContext(), "", BaseURLTypes.EHS_BASE_URL, false)
                .webServiceRequestAPIAnyObject(WebServiceConstants.METHOD_GET_EMPLOYEE_SUMMARY_DETAIL, model.toString(),
                        new WebServices.IRequestWebResponseAnyObjectCallBack() {
                            @Override
                            public void requestDataResponse(WebResponse<Object> webResponse) {

                                employeeSummaryDetailModel = GsonFactory.getSimpleGson()
                                        .fromJson(GsonFactory.getSimpleGson().toJson(webResponse.result), EmployeeSummaryDetailModel.class);

                                if (sessionDetailModel.getStatusEnum() == EmployeeSessionState.COMPLETED) {
                                    imgProfileDone.setVisibility(View.VISIBLE);
                                } else {
                                    imgProfileDone.setVisibility(View.GONE);
                                }


                                if (employeeSummaryDetailModel.getEmpMeasurements() != null && !employeeSummaryDetailModel.getEmpMeasurements().isEmpty()) {
                                    isMeasurementDone = true;
                                    imgMeasurementsDone.setVisibility(View.VISIBLE);
                                } else {
                                    imgMeasurementsDone.setVisibility(View.GONE);
                                }


                                getEmployeeAssessmentList(model);

                            }

                            @Override
                            public void onError(Object object) {
                                dialog.dismiss();
                                if (object instanceof String) {
                                    UIHelper.showToast(getContext(), (String) object);
                                }
                            }
                        });
    }

    private void getEmployeeAssessmentList(EmployeeSendingModel model) {
        new WebServices(getContext(), "", BaseURLTypes.EHS_BASE_URL, false)
                .webServiceRequestAPIAnyObject(WebServiceConstants.METHOD_GET_EMPLOYEE_ASSESSMENTS, model.toString(),
                        new WebServices.IRequestWebResponseAnyObjectCallBack() {
                            @Override
                            public void requestDataResponse(WebResponse<Object> webResponse) {
                                if (webResponse.result instanceof ArrayList) {

                                    Type type = new TypeToken<ArrayList<AssessmentQuestionModel>>() {
                                    }.getType();
                                    ArrayList<AssessmentQuestionModel> arrayList = GsonFactory.getSimpleGson()
                                            .fromJson(GsonFactory.getSimpleGson().toJson(webResponse.result)
                                                    , type);


                                    if (arrayList != null && !arrayList.isEmpty()) {
                                        isAssessmentDone = true;
                                        imgAssessmentDone.setVisibility(View.VISIBLE);
                                    } else {
                                        imgAssessmentDone.setVisibility(View.GONE);
                                    }

                                    dialog.dismiss();

                                }
                            }

                            @Override
                            public void onError(Object object) {
                                dialog.dismiss();
                                isAssessmentDone = false;
                                imgAssessmentDone.setVisibility(View.GONE);
                            }
                        });
    }

}
