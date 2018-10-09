package edu.aku.ehs.fragments;

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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.jcminarro.roundkornerlayout.RoundKornerLinearLayout;

import java.text.DecimalFormat;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import edu.aku.ehs.R;
import edu.aku.ehs.adapters.recyleradapters.LabAdapter;
import edu.aku.ehs.adapters.recyleradapters.MetabolicSyndromeAdapter;
import edu.aku.ehs.callbacks.OnItemClickListener;
import edu.aku.ehs.constatnts.WebServiceConstants;
import edu.aku.ehs.enums.BaseURLTypes;
import edu.aku.ehs.enums.CVDRiskFactorTypes;
import edu.aku.ehs.enums.CategoryType;
import edu.aku.ehs.enums.EmployeeSessionState;
import edu.aku.ehs.enums.MeasurementsType;
import edu.aku.ehs.enums.ReferredTypes;
import edu.aku.ehs.fragments.abstracts.BaseFragment;
import edu.aku.ehs.helperclasses.Helper;
import edu.aku.ehs.helperclasses.StringHelper;
import edu.aku.ehs.helperclasses.ui.helper.UIHelper;
import edu.aku.ehs.managers.retrofit.GsonFactory;
import edu.aku.ehs.managers.retrofit.WebServices;
import edu.aku.ehs.models.ActiveMeasurementsModel;
import edu.aku.ehs.models.AssessmentQuestionModel;
import edu.aku.ehs.models.CVDRiskFactors;
import edu.aku.ehs.models.EmployeeSummaryDetailModel;
import edu.aku.ehs.models.SessionDetailModel;
import edu.aku.ehs.models.wrappers.WebResponse;
import edu.aku.ehs.widget.AnyTextView;
import edu.aku.ehs.widget.TitleBar;

public class EmployeeProfileViewerFragment extends BaseFragment implements OnItemClickListener {


    Unbinder unbinder;
    SessionDetailModel sessionDetailModel;
    LabAdapter labAdapter;
    MetabolicSyndromeAdapter metabolicSyndromeAdapter;


    @BindView(R.id.imgBanner)
    ImageView imgBanner;
    @BindView(R.id.txtDateMeasurements)
    AnyTextView txtDateMeasurements;
    @BindView(R.id.txtHeightDesc)
    AnyTextView txtHeightDesc;
    @BindView(R.id.txtHeight)
    AnyTextView txtHeight;
    @BindView(R.id.txtHeightUnit)
    AnyTextView txtHeightUnit;
    @BindView(R.id.txtWeightDesc)
    AnyTextView txtWeightDesc;
    @BindView(R.id.txtWeight)
    AnyTextView txtWeight;
    @BindView(R.id.txtWeightUnit)
    AnyTextView txtWeightUnit;
    @BindView(R.id.txtWaistDesc)
    AnyTextView txtWaistDesc;
    @BindView(R.id.txtWaist)
    AnyTextView txtWaist;
    @BindView(R.id.txtWaistUnit)
    AnyTextView txtWaistUnit;
    @BindView(R.id.txtBMIDesc)
    AnyTextView txtBMIDesc;
    @BindView(R.id.txtBMI)
    AnyTextView txtBMI;
    @BindView(R.id.txtBMIUnit)
    LinearLayout txtBMIUnit;
    @BindView(R.id.txtBPDesc)
    AnyTextView txtBPDesc;
    @BindView(R.id.txtBPSystolic)
    AnyTextView txtBPSystolic;
    @BindView(R.id.txtBPDiastolic)
    AnyTextView txtBPDiastolic;
    @BindView(R.id.txtBPUnit)
    AnyTextView txtBPUnit;
    @BindView(R.id.contMeasurements)
    RoundKornerLinearLayout contMeasurements;
    @BindView(R.id.recyclerViewLab)
    RecyclerView recyclerViewLab;
    @BindView(R.id.contLabs)
    RoundKornerLinearLayout contLabs;
    @BindView(R.id.txtAgeDesc)
    AnyTextView txtAgeDesc;
    @BindView(R.id.txtHDLDesc)
    AnyTextView txtHDLDesc;
    @BindView(R.id.txtTotalCholDesc)
    AnyTextView txtTotalCholDesc;
    @BindView(R.id.txtSBPTreatedDesc)
    AnyTextView txtSBPTreatedDesc;
    @BindView(R.id.txtSBPNotTreatedDesc)
    AnyTextView txtSBPNotTreatedDesc;
    @BindView(R.id.txtSmokerDesc)
    AnyTextView txtSmokerDesc;
    @BindView(R.id.txtDiabeticDesc)
    AnyTextView txtDiabeticDesc;
    @BindView(R.id.txtAgeValue)
    AnyTextView txtAgeValue;
    @BindView(R.id.txtHDLValue)
    AnyTextView txtHDLValue;
    @BindView(R.id.txtTotalCholValue)
    AnyTextView txtTotalCholValue;
    @BindView(R.id.txtSBPTreatedValue)
    AnyTextView txtSBPTreatedValue;
    @BindView(R.id.txtSBPNotTreatedValue)
    AnyTextView txtSBPNotTreatedValue;
    @BindView(R.id.txtSmokerValue)
    AnyTextView txtSmokerValue;
    @BindView(R.id.txtDiabeticValue)
    AnyTextView txtDiabeticValue;
    @BindView(R.id.txtAgeScore)
    AnyTextView txtAgeScore;
    @BindView(R.id.txtHDLScore)
    AnyTextView txtHDLScore;
    @BindView(R.id.txtTotalCholScore)
    AnyTextView txtTotalCholScore;
    @BindView(R.id.txtSBPTreatedScore)
    AnyTextView txtSBPTreatedScore;
    @BindView(R.id.txtSBPNotTreatedScore)
    AnyTextView txtSBPNotTreatedScore;
    @BindView(R.id.txtSmokerScore)
    AnyTextView txtSmokerScore;
    @BindView(R.id.txtDiabeticScore)
    AnyTextView txtDiabeticScore;
    @BindView(R.id.txtRiskSocre)
    AnyTextView txtRiskSocre;
    @BindView(R.id.txtTotalRiskValue)
    AnyTextView txtTotalRiskValue;
    @BindView(R.id.txtMedicalNone)
    AnyTextView txtMedicalNone;
    @BindView(R.id.contMedicalHistory)
    LinearLayout contMedicalHistory;
    @BindView(R.id.txtQuestionTitle)
    AnyTextView txtQuestionTitle;
    @BindView(R.id.txtFamilyNone)
    AnyTextView txtFamilyNone;
    @BindView(R.id.contFamilyHistory)
    LinearLayout contFamilyHistory;
    @BindView(R.id.txtPsycosocialNone)
    AnyTextView txtPsycosocialNone;
    @BindView(R.id.contPsychosocialHistory)
    LinearLayout contPsychosocialHistory;
    @BindView(R.id.txtSmokingBehaviorsNone)
    AnyTextView txtSmokingBehaviorsNone;
    @BindView(R.id.contSmokingBehaviors)
    LinearLayout contSmokingBehaviors;
    @BindView(R.id.contHistory)
    RoundKornerLinearLayout contHistory;
    @BindView(R.id.rbYes)
    RadioButton rbYes;
    @BindView(R.id.rbNo)
    RadioButton rbNo;
    @BindView(R.id.rgYesNoRecommend)
    RadioGroup rgYesNoRecommend;
    @BindView(R.id.contReferredTo)
    LinearLayout contReferredTo;
    @BindView(R.id.btnCancel)
    Button btnCancel;
    @BindView(R.id.btnDone)
    Button btnDone;
    @BindView(R.id.contBelow)
    LinearLayout contBelow;
    @BindView(R.id.contParent)
    RelativeLayout contParent;
    @BindView(R.id.txtHasMetabolicSyndrom)
    AnyTextView txtHasMetabolicSyndrom;
    @BindView(R.id.recyclerViewMetablic)
    RecyclerView recyclerViewMetablic;
    @BindView(R.id.contMetabolic)
    RoundKornerLinearLayout contMetabolic;
    @BindView(R.id.rbCHC)
    RadioButton rbCHC;
    @BindView(R.id.rbIMS)
    RadioButton rbIMS;
    @BindView(R.id.rbOther)
    RadioButton rbOther;
    @BindView(R.id.rgRefferedTo)
    RadioGroup rgRefferedTo;
    @BindView(R.id.contCVDRisk)
    RoundKornerLinearLayout contCVDRisk;
    private EmployeeSummaryDetailModel employeeSummaryDetailModel;


    public static EmployeeProfileViewerFragment newInstance(SessionDetailModel sessionDetailModel, EmployeeSummaryDetailModel employeeSummaryDetailModel) {

        Bundle args = new Bundle();

        EmployeeProfileViewerFragment fragment = new EmployeeProfileViewerFragment();
        fragment.sessionDetailModel = sessionDetailModel;
        fragment.employeeSummaryDetailModel = employeeSummaryDetailModel;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getDrawerLockMode() {
        return DrawerLayout.LOCK_MODE_LOCKED_CLOSED;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_patient_profile_viewer_v2;
    }

    @Override
    public void setTitlebar(TitleBar titleBar) {
        titleBar.resetViews();
        titleBar.setVisibility(View.VISIBLE);
        titleBar.showHome(getBaseActivity());
        titleBar.setTitle("Employee Profile Viewer");
        titleBar.showBackButton(getBaseActivity());
        titleBar.setEmployeeHeader(sessionDetailModel, getContext());
    }

    @Override
    public void setListeners() {
        rgYesNoRecommend.setOnCheckedChangeListener((radioGroup, i) -> {
            switch (i) {
                case R.id.rbYes:
                    contReferredTo.setVisibility(View.VISIBLE);
                    break;

                case R.id.rbNo:
                    contReferredTo.setVisibility(View.GONE);
                    rgRefferedTo.clearCheck();
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

        contHistory.setVisibility(View.GONE);
        contMeasurements.setVisibility(View.GONE);
        contLabs.setVisibility(View.GONE);
        contMetabolic.setVisibility(View.GONE);
        contCVDRisk.setVisibility(View.GONE);


        if (StringHelper.checkNotNullAndNotEmpty(sessionDetailModel.getIsReferred())) {
            if (sessionDetailModel.getIsReferred().equalsIgnoreCase("Y")) {
                contReferredTo.setVisibility(View.VISIBLE);
                rbYes.setChecked(true);
                if (StringHelper.checkNotNullAndNotEmpty(sessionDetailModel.getReferredToID())) {
                    ReferredTypes referredTypes = ReferredTypes.fromCanonicalForm(sessionDetailModel.getReferredToID());
                    switch (referredTypes) {
                        case CHC:
                            rbCHC.setChecked(true);
                            break;
                        case IMS:
                            rbIMS.setChecked(true);
                            break;
                        case OTHER:
                            rbOther.setChecked(true);
                            break;
                    }

                }

            } else {
                contReferredTo.setVisibility(View.GONE);
                rbNo.setChecked(true);
            }
        }

        if (sessionDetailModel.getStatusEnum() == EmployeeSessionState.COMPLETED) {
            btnDone.setVisibility(View.GONE);
            btnCancel.setText("Back");
        }


        setData(employeeSummaryDetailModel);


    }

    private void bindRecyclerView() {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getBaseActivity());

        recyclerViewLab.setLayoutManager(mLayoutManager);
        ((DefaultItemAnimator) recyclerViewLab.getItemAnimator()).setSupportsChangeAnimations(false);
        recyclerViewLab.setAdapter(labAdapter);

        RecyclerView.LayoutManager mLayoutManager2 = new LinearLayoutManager(getBaseActivity());

        recyclerViewMetablic.setLayoutManager(mLayoutManager2);
        ((DefaultItemAnimator) recyclerViewMetablic.getItemAnimator()).setSupportsChangeAnimations(false);
        recyclerViewMetablic.setAdapter(metabolicSyndromeAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onItemClick(int position, Object object, View view) {

    }


    private void setData(EmployeeSummaryDetailModel model) {
        if (model.getEmpMeasurements() != null && !model.getEmpMeasurements().isEmpty()) {
            setMeasurements(model);
            contMeasurements.setVisibility(View.VISIBLE);
        }


        if (model.getEmpAssessments() != null && !model.getEmpAssessments().isEmpty()) {
            setAssessments(model);
            contHistory.setVisibility(View.VISIBLE);
        }

        if (model.getEmpLabs() != null && !model.getEmpLabs().isEmpty()) {
            setLabs(model);
            contLabs.setVisibility(View.VISIBLE);
        }

        if (model.getCVDRiskFactors() != null && !model.getCVDRiskFactors().isEmpty()) {
            setCVDRisk(model);
            contCVDRisk.setVisibility(View.VISIBLE);
        }

        if (model.getMetabolicSyndromeDetail().getMetabolicSyndromeRules() != null && !model.getEmpLabs().isEmpty()) {
            setMetabolicBox(model);
            contMetabolic.setVisibility(View.VISIBLE);
        }

        sessionDetailModel.setHasMetabolicSyndrome(model.isHasMetabolicSyndrome());
        sessionDetailModel.setRiskScore(model.getRiskScore());

    }

    private void setCVDRisk(EmployeeSummaryDetailModel model) {

        int totalScore = 0;
        for (CVDRiskFactors cvdRiskFactors : model.getCVDRiskFactors()) {
            totalScore += cvdRiskFactors.getPOINTS();
            cvdRiskFactors.setCvdRiskFactorTypes(CVDRiskFactorTypes.fromCanonicalForm(cvdRiskFactors.getFACTORID()));
            switch (cvdRiskFactors.getCvdRiskFactorTypes()) {
                case AGE:
                    txtAgeDesc.setText(cvdRiskFactors.getDESCRIPTION());
                    txtAgeValue.setText(cvdRiskFactors.getVALUE());
                    txtAgeScore.setText(cvdRiskFactors.getPOINTS() + "");
                    break;
                case HDL:
                    txtHDLDesc.setText(cvdRiskFactors.getDESCRIPTION());
                    txtHDLValue.setText(cvdRiskFactors.getVALUE());
                    txtHDLScore.setText(cvdRiskFactors.getPOINTS() + "");
                    break;
                case TOTALCHOL:
                    txtTotalCholDesc.setText(cvdRiskFactors.getDESCRIPTION());
                    txtTotalCholValue.setText(cvdRiskFactors.getVALUE());
                    txtTotalCholScore.setText(cvdRiskFactors.getPOINTS() + "");
                    break;
                case SBPTREATED:
//                    txtSBPTreatedDesc.setText(cvdRiskFactors.getDESCRIPTION());
                    txtSBPTreatedValue.setText(cvdRiskFactors.getVALUE());
                    txtSBPTreatedScore.setText(cvdRiskFactors.getPOINTS() + "");
                    break;
                case SBPNOTTREATED:
//                    txtSBPNotTreatedDesc.setText(cvdRiskFactors.getDESCRIPTION());
                    txtSBPNotTreatedValue.setText(cvdRiskFactors.getVALUE());
                    txtSBPNotTreatedScore.setText(cvdRiskFactors.getPOINTS() + "");
                    break;
                case SMOKER:
                    txtSmokerDesc.setText(cvdRiskFactors.getDESCRIPTION());
                    txtSmokerValue.setText(cvdRiskFactors.getVALUE());
                    txtSmokerScore.setText(cvdRiskFactors.getPOINTS() + "");
                    break;
                case DIABETIC:
                    txtDiabeticDesc.setText(cvdRiskFactors.getDESCRIPTION());
                    txtDiabeticValue.setText(cvdRiskFactors.getVALUE());
                    txtDiabeticScore.setText(cvdRiskFactors.getPOINTS() + "");
                    break;
            }

        }

        txtTotalRiskValue.setText(totalScore + "");
        txtRiskSocre.setText(model.getRiskScore());
    }

    private void setLabs(EmployeeSummaryDetailModel model) {

        labAdapter = new LabAdapter(getBaseActivity(), model.getEmpLabs(), null);
        bindRecyclerView();
        labAdapter.notifyDataSetChanged();
    }

    private void setMetabolicBox(EmployeeSummaryDetailModel model) {
        metabolicSyndromeAdapter = new MetabolicSyndromeAdapter(getBaseActivity(), model.getMetabolicSyndromeDetail().getMetabolicSyndromeRules(), null);
        bindRecyclerView();
        metabolicSyndromeAdapter.notifyDataSetChanged();

        if (model.isHasMetabolicSyndrome()) {
            txtHasMetabolicSyndrom.setText("Has Metablic Syndrome");
        } else {
            txtHasMetabolicSyndrom.setTextColor(getContext().getColor(R.color.txtBlue));
            txtHasMetabolicSyndrom.setText("No Metablic Syndrome");
        }
    }

    private void setAssessments(EmployeeSummaryDetailModel detailModel) {
        for (AssessmentQuestionModel assessmentQuestionModel : detailModel.getEmpAssessments()) {
            assessmentQuestionModel.setCategoryType(CategoryType.fromCanonicalForm(assessmentQuestionModel.getCategoryID()));

            switch (assessmentQuestionModel.getCategoryType()) {
                case MEDICALHISTORY:
                    txtMedicalNone.setVisibility(View.GONE);
                    if (assessmentQuestionModel.isAnswer2()) {
                        Helper.addTextView(getContext(), contMedicalHistory, assessmentQuestionModel.getQuestionDescription() + " (Treated)",
                                (int) getBaseActivity().getResources().getDimension(R.dimen.s7), getBaseActivity().getResources().getColor(R.color.txtBlue));
                    } else {
                        Helper.addTextView(getContext(), contMedicalHistory, assessmentQuestionModel.getQuestionDescription() + " (Not Treated)",
                                (int) getBaseActivity().getResources().getDimension(R.dimen.s7), getBaseActivity().getResources().getColor(R.color.txtBlue));
                    }

                    break;
                case FAMILYHISTORY:
                    txtFamilyNone.setVisibility(View.GONE);
                    Helper.addTextView(getContext(), contFamilyHistory, assessmentQuestionModel.getQuestionDescription() + " (Yes)",
                            (int) getBaseActivity().getResources().getDimension(R.dimen.s7), getBaseActivity().getResources().getColor(R.color.txtBlue));

                    break;
                case SOCIALHISTORY:
                    txtPsycosocialNone.setVisibility(View.GONE);
                    Helper.addTextView(getContext(), contPsychosocialHistory, assessmentQuestionModel.getQuestionDescription() + " (Yes)",
                            (int) getBaseActivity().getResources().getDimension(R.dimen.s7), getBaseActivity().getResources().getColor(R.color.txtBlue));

                    break;
                case SMOKINGBEHAVIOR:
                    txtSmokingBehaviorsNone.setVisibility(View.GONE);
                    Helper.addTextView(getContext(), contSmokingBehaviors, assessmentQuestionModel.getQuestionDescription() + " (Yes)",
                            (int) getBaseActivity().getResources().getDimension(R.dimen.s7), getBaseActivity().getResources().getColor(R.color.txtBlue));
                    break;
            }

        }

    }

    private void setMeasurements(EmployeeSummaryDetailModel model) {
        for (ActiveMeasurementsModel activeMeasurementsModel : model.getEmpMeasurements()) {
            activeMeasurementsModel.setMeasurementsType(MeasurementsType.fromCanonicalForm(activeMeasurementsModel.getMeasurementID()));
            switch (activeMeasurementsModel.getMeasurementsType()) {
                case WAIST:
                    txtWaist.setText(activeMeasurementsModel.getValue());
                    txtWaistUnit.setText(activeMeasurementsModel.getUnitofMeasure());
                    txtWaistDesc.setText(activeMeasurementsModel.getDescription());

                    if (sessionDetailModel.getGender().equalsIgnoreCase("M")) {
                        if (Integer.valueOf(activeMeasurementsModel.getValue()) > activeMeasurementsModel.getNormalMenMax()) {
                            txtWaist.setTextColor(getBaseActivity().getColor(R.color.red_resistant));
                        }
                    } else {
                        if (Integer.valueOf(activeMeasurementsModel.getValue()) > activeMeasurementsModel.getNormalWomenMax()) {
                            txtWaist.setTextColor(getBaseActivity().getColor(R.color.red_resistant));
                        }
                    }

                    break;
                case SBP:
                    txtBPSystolic.setText(activeMeasurementsModel.getValue());
                    txtBPUnit.setText(activeMeasurementsModel.getUnitofMeasure());
                    if (sessionDetailModel.getGender().equalsIgnoreCase("M")) {
                        if (Integer.valueOf(activeMeasurementsModel.getValue()) > activeMeasurementsModel.getNormalMenMax()) {
                            txtBPSystolic.setTextColor(getBaseActivity().getColor(R.color.red_resistant));
                        }
                    } else {
                        if (Integer.valueOf(activeMeasurementsModel.getValue()) > activeMeasurementsModel.getNormalWomenMax()) {
                            txtBPSystolic.setTextColor(getBaseActivity().getColor(R.color.red_resistant));
                        }
                    }


                    break;
                case DBP:
                    txtBPDiastolic.setText(activeMeasurementsModel.getValue());
                    txtBPUnit.setText(activeMeasurementsModel.getUnitofMeasure());
                    if (sessionDetailModel.getGender().equalsIgnoreCase("M")) {
                        if (Integer.valueOf(activeMeasurementsModel.getValue()) > activeMeasurementsModel.getNormalMenMax()) {
                            txtBPDiastolic.setTextColor(getBaseActivity().getColor(R.color.red_resistant));
                        }
                    } else {
                        if (Integer.valueOf(activeMeasurementsModel.getValue()) > activeMeasurementsModel.getNormalWomenMax()) {
                            txtBPDiastolic.setTextColor(getBaseActivity().getColor(R.color.red_resistant));
                        }
                    }

                    break;
                case HEIGHT:
                    txtHeight.setText(activeMeasurementsModel.getValue());
                    txtHeightUnit.setText(activeMeasurementsModel.getUnitofMeasure());
                    txtHeightDesc.setText(activeMeasurementsModel.getDescription());
                    if (sessionDetailModel.getGender().equalsIgnoreCase("M")) {
                        if (Integer.valueOf(activeMeasurementsModel.getValue()) > activeMeasurementsModel.getNormalMenMax()) {
                            txtHeight.setTextColor(getBaseActivity().getColor(R.color.red_resistant));
                        }
                    } else {
                        if (Integer.valueOf(activeMeasurementsModel.getValue()) > activeMeasurementsModel.getNormalWomenMax()) {
                            txtHeight.setTextColor(getBaseActivity().getColor(R.color.red_resistant));
                        }
                    }

                    break;
                case WEIGHT:
                    txtWeight.setText(activeMeasurementsModel.getValue());
                    txtWeightUnit.setText(activeMeasurementsModel.getUnitofMeasure());
                    txtWeightDesc.setText(activeMeasurementsModel.getDescription());
                    if (sessionDetailModel.getGender().equalsIgnoreCase("M")) {
                        if (Integer.valueOf(activeMeasurementsModel.getValue()) > activeMeasurementsModel.getNormalMenMax()) {
                            txtWeight.setTextColor(getBaseActivity().getColor(R.color.red_resistant));
                        }
                    } else {
                        if (Integer.valueOf(activeMeasurementsModel.getValue()) > activeMeasurementsModel.getNormalWomenMax()) {
                            txtWeight.setTextColor(getBaseActivity().getColor(R.color.red_resistant));
                        }
                    }
                    break;
            }
        }
        txtDateMeasurements.setText(model.getEmpMeasurements().get(0).getLastFileDateTime());
        setBMI(Integer.valueOf(txtHeight.getText().toString()), Integer.valueOf(txtWeight.getText().toString()));
    }

    private void setBMI(int height, int weight) {
        double heightInMeters = (height / 100f);

        double result = (weight / (heightInMeters * heightInMeters));
        txtBMI.setText(new DecimalFormat("##.#").format(result));
    }


    private void updateEmployeeInSessionCall(String jsonArrayData) {
        new WebServices(getContext(), "", BaseURLTypes.EHS_BASE_URL, true)
                .webServiceRequestAPIAnyObject(WebServiceConstants.METHOD_UPDATE_SESSION_EMPLOYEE, jsonArrayData,
                        new WebServices.IRequestWebResponseAnyObjectCallBack() {
                            @Override
                            public void requestDataResponse(WebResponse<Object> webResponse) {
                                UIHelper.showToast(getContext(), webResponse.responseMessage);
                                getBaseActivity().popBackStack();
                                getBaseActivity().popBackStack();
                            }

                            @Override
                            public void onError(Object object) {
                                if (object instanceof String) {
                                    UIHelper.showToast(getContext(), (String) object);
                                }
                            }
                        });
    }


    @OnClick({R.id.btnCancel, R.id.btnDone})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnCancel:
                getBaseActivity().popBackStack();
                break;
            case R.id.btnDone:
                ArrayList<SessionDetailModel> sessionDetailModelArrayList = new ArrayList<>();


                if (rbYes.isChecked()) {
                    sessionDetailModel.setIsReferred("Y");
                    if (rbCHC.isChecked()) {
                        sessionDetailModel.setReferredToID(ReferredTypes.CHC.canonicalForm());
                        sessionDetailModel.setReferredToDescription(ReferredTypes.CHC.canonicalForm());
                    } else if (rbIMS.isChecked()) {
                        sessionDetailModel.setReferredToID(ReferredTypes.IMS.canonicalForm());
                        sessionDetailModel.setReferredToDescription(ReferredTypes.IMS.canonicalForm());
                    } else if (rbOther.isChecked()) {
                        sessionDetailModel.setReferredToID(ReferredTypes.OTHER.canonicalForm());
                        sessionDetailModel.setReferredToDescription(ReferredTypes.OTHER.canonicalForm());
                    } else {
                        UIHelper.showShortToastInCenter(getContext(), "Please recommend referred location.");
                        return;
                    }
                } else if (rbNo.isChecked()) {
                    sessionDetailModel.setIsReferred("N");
                } else {
                    UIHelper.showShortToastInCenter(getContext(), "Do you recommend patient to be referred? Please answer");
                    return;
                }

                sessionDetailModel.setStatusID(EmployeeSessionState.COMPLETED.canonicalForm());
                sessionDetailModel.setCompletedBy(getCurrentUser().getName());

                sessionDetailModelArrayList.add(sessionDetailModel);
                String jsonArrayData = GsonFactory.getConfiguredGson().toJson(sessionDetailModelArrayList);
                updateEmployeeInSessionCall(jsonArrayData);
                break;
        }
    }
}
