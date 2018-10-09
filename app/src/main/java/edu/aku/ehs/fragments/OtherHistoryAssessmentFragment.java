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

import com.github.clans.fab.FloatingActionButton;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import edu.aku.ehs.R;
import edu.aku.ehs.adapters.recyleradapters.AssessmentQuestionAdapter;
import edu.aku.ehs.callbacks.OnItemClickListener;
import edu.aku.ehs.callbacks.RadioGroupAdapterListner;
import edu.aku.ehs.constatnts.WebServiceConstants;
import edu.aku.ehs.enums.BaseURLTypes;
import edu.aku.ehs.enums.EmployeeSessionState;
import edu.aku.ehs.fragments.abstracts.BaseFragment;
import edu.aku.ehs.fragments.dialogs.PinDialogFragment;
import edu.aku.ehs.helperclasses.ui.helper.UIHelper;
import edu.aku.ehs.managers.retrofit.GsonFactory;
import edu.aku.ehs.managers.retrofit.WebServices;
import edu.aku.ehs.models.AssessmentQuestionModel;
import edu.aku.ehs.models.SessionDetailModel;
import edu.aku.ehs.models.wrappers.WebResponse;
import edu.aku.ehs.widget.AnyTextView;
import edu.aku.ehs.widget.TitleBar;
import edu.aku.ehs.widget.recyclerview_layout.CustomLayoutManager;

public class OtherHistoryAssessmentFragment extends BaseFragment implements OnItemClickListener {


    Unbinder unbinder;
    NewAssessmentViewPagerFragment parentFragment;
    @BindView(R.id.txtQuestionTitle1)
    AnyTextView txtTitleFamily;
    @BindView(R.id.txtQuestionSubTitle1)
    AnyTextView txtSubtitleFamily;
    @BindView(R.id.recylerView1)
    RecyclerView recylerView1;
    @BindView(R.id.txtQuestionTitle2)
    AnyTextView txtTitleSocial;
    @BindView(R.id.txtQuestionSubTitle2)
    AnyTextView txtSubtitleSocial;
    @BindView(R.id.recylerView2)
    RecyclerView recylerView2;
    @BindView(R.id.txtQuestionTitle3)
    AnyTextView txtTitleSmoking;
    @BindView(R.id.txtQuestionSubTitle3)
    AnyTextView txtSubtitleSmoking;
    @BindView(R.id.recylerView3)
    RecyclerView recylerView3;
    @BindView(R.id.btnDone)
    public Button btnDone;
    @BindView(R.id.fabBack)
    FloatingActionButton fabBack;


    private ArrayList<AssessmentQuestionModel> arrFamilyHistory;
    private ArrayList<AssessmentQuestionModel> arrSocialHistory;
    private ArrayList<AssessmentQuestionModel> arrSmokingBehavior;
    private AssessmentQuestionAdapter adaptFamilyHistory;
    private AssessmentQuestionAdapter adaptSocialHistory;
    private AssessmentQuestionAdapter adaptSmokingBehavior;
    private RadioGroupAdapterListner onSwitchListener1_family;
    private RadioGroupAdapterListner onSwitchListener2_family;
    private RadioGroupAdapterListner onSwitchListener1_social;
    private RadioGroupAdapterListner onSwitchListener2_social;
    private RadioGroupAdapterListner onSwitchListener1_smoking;
    private RadioGroupAdapterListner onSwitchListener2_smoking;
    private SessionDetailModel sessionDetailModel;

    ArrayList<AssessmentQuestionModel> dataArray = new ArrayList<>();


    public static OtherHistoryAssessmentFragment newInstance(SessionDetailModel sessionDetailModel) {

        Bundle args = new Bundle();

        OtherHistoryAssessmentFragment fragment = new OtherHistoryAssessmentFragment();
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
        return R.layout.fragment_assessment_question_list2;
    }

    @Override
    public void setTitlebar(TitleBar titleBar) {
        titleBar.resetViews();
        titleBar.setVisibility(View.VISIBLE);
        titleBar.showHome(getBaseActivity());
        titleBar.setTitle("Employee Assessment");
        titleBar.showBackButton(getBaseActivity());
        titleBar.setEmployeeHeader(sessionDetailModel, getContext());

    }

    @Override
    public void setListeners() {



    }

    private boolean validateQuestions(ArrayList<AssessmentQuestionModel> dataArray) {
        // Medical History
        for (AssessmentQuestionModel assessmentQuestionModel : ((MedicalHistoryAssessmentFragment) parentFragment.getChildFragmentManager().getFragments().get(0)).getArrData()) {
            if (assessmentQuestionModel.getIsAnswerYes() == null || assessmentQuestionModel.getIsAnswerYes().isEmpty()) {
                UIHelper.showShortToastInCenter(getContext(), "Kindly Answer all the Questions of " + assessmentQuestionModel.getCategoryID());
                return false;
            } else if (assessmentQuestionModel.getIsAnswerYes().equalsIgnoreCase("Y") && assessmentQuestionModel.getAskIsUnderTreatment() != null && assessmentQuestionModel.getAskIsUnderTreatment().equalsIgnoreCase("Y")) {
                if (assessmentQuestionModel.getIsUnderTreatment() == null || assessmentQuestionModel.getIsUnderTreatment().isEmpty()) {
                    UIHelper.showShortToastInCenter(getContext(), "Kindly Answer all the Questions of " + assessmentQuestionModel.getCategoryID());
                    return false;
                } else {
                    dataArray.add(assessmentQuestionModel);
                }
            } else {
                dataArray.add(assessmentQuestionModel);
            }
        }


        // Family History
        for (AssessmentQuestionModel assessmentQuestionModel : arrFamilyHistory) {
            if (assessmentQuestionModel.getIsAnswerYes() == null || assessmentQuestionModel.getIsAnswerYes().isEmpty()) {
                UIHelper.showShortToastInCenter(getContext(), "Kindly Answer all the Questions of " + assessmentQuestionModel.getCategoryID());
                return false;
            } else if (assessmentQuestionModel.getAskIsUnderTreatment() != null && assessmentQuestionModel.getAskIsUnderTreatment().equalsIgnoreCase("Y")) {
                if (assessmentQuestionModel.getIsUnderTreatment() == null || assessmentQuestionModel.getIsUnderTreatment().isEmpty()) {
                    UIHelper.showShortToastInCenter(getContext(), "Kindly Answer all the Questions of " + assessmentQuestionModel.getCategoryID());
                    return false;
                } else {
                    dataArray.add(assessmentQuestionModel);
                }
            } else {
                dataArray.add(assessmentQuestionModel);
            }
        }


        // Social History
        for (AssessmentQuestionModel assessmentQuestionModel : arrSocialHistory) {
            if (assessmentQuestionModel.getIsAnswerYes() == null || assessmentQuestionModel.getIsAnswerYes().isEmpty()) {
                UIHelper.showShortToastInCenter(getContext(), "Kindly Answer all the Questions of " + assessmentQuestionModel.getCategoryID());
                return false;
            } else if (assessmentQuestionModel.getAskIsUnderTreatment() != null && assessmentQuestionModel.getAskIsUnderTreatment().equalsIgnoreCase("Y")) {
                if (assessmentQuestionModel.getIsUnderTreatment() == null || assessmentQuestionModel.getIsUnderTreatment().isEmpty()) {
                    UIHelper.showShortToastInCenter(getContext(), "Kindly Answer all the Questions of " + assessmentQuestionModel.getCategoryID());
                    return false;
                } else {
                    dataArray.add(assessmentQuestionModel);
                }
            } else {
                dataArray.add(assessmentQuestionModel);
            }
        }


        // Smoking behavior
        for (AssessmentQuestionModel assessmentQuestionModel : arrSmokingBehavior) {
            if (assessmentQuestionModel.getIsAnswerYes() == null || assessmentQuestionModel.getIsAnswerYes().isEmpty()) {
                UIHelper.showShortToastInCenter(getContext(), "Kindly Answer all the Questions of " + assessmentQuestionModel.getCategoryID());
                return false;
            } else if (assessmentQuestionModel.getAskIsUnderTreatment() != null && assessmentQuestionModel.getAskIsUnderTreatment().equalsIgnoreCase("Y")) {
                if (assessmentQuestionModel.getIsUnderTreatment() == null || assessmentQuestionModel.getIsUnderTreatment().isEmpty()) {
                    UIHelper.showShortToastInCenter(getContext(), "Kindly Answer all the Questions of " + assessmentQuestionModel.getCategoryID());
                    return false;
                } else {
                    dataArray.add(assessmentQuestionModel);
                }
            } else {
                dataArray.add(assessmentQuestionModel);
            }
        }
        return true;
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

        initSwitchListener();

    }

    private void initSwitchListener() {

        // List 1
        onSwitchListener1_family = (radioGroup, i, adapterPosition) -> {
            if (i == R.id.rbQues1Yes) {
                arrFamilyHistory.get(adapterPosition).setAnswer1(true);
                adaptFamilyHistory.notifyItemChanged(adapterPosition);
            } else {
                arrFamilyHistory.get(adapterPosition).setAnswer1(false);
                arrFamilyHistory.get(adapterPosition).setIsUnderTreatment("");
                adaptFamilyHistory.notifyItemChanged(adapterPosition);
            }
            parentFragment.dirtyCheck = true;
            btnDone.setAlpha(1f);
        };

        onSwitchListener2_family = (radioGroup, i, adapterPosition) -> {
            if (i == R.id.rbQues2Yes) {
                arrFamilyHistory.get(adapterPosition).setAnswer2(true);
                adaptFamilyHistory.notifyItemChanged(adapterPosition);
            } else {
                arrFamilyHistory.get(adapterPosition).setAnswer2(false);
                adaptFamilyHistory.notifyItemChanged(adapterPosition);
            }
            parentFragment.dirtyCheck = true;
            btnDone.setAlpha(1f);
        };

        // For List 2
        onSwitchListener1_social = (radioGroup, i, adapterPosition) -> {
            if (i == R.id.rbQues1Yes) {
                arrSocialHistory.get(adapterPosition).setAnswer1(true);
                adaptSocialHistory.notifyItemChanged(adapterPosition);
            } else {
                arrSocialHistory.get(adapterPosition).setAnswer1(false);
                arrSocialHistory.get(adapterPosition).setIsUnderTreatment("");
                adaptSocialHistory.notifyItemChanged(adapterPosition);
            }
            parentFragment.dirtyCheck = true;
            btnDone.setAlpha(1f);
        };

        onSwitchListener2_social = (radioGroup, i, adapterPosition) -> {
            if (i == R.id.rbQues2Yes) {
                arrSocialHistory.get(adapterPosition).setAnswer2(true);
                adaptSocialHistory.notifyItemChanged(adapterPosition);
            } else {
                arrSocialHistory.get(adapterPosition).setAnswer2(false);
                adaptSocialHistory.notifyItemChanged(adapterPosition);
            }
            parentFragment.dirtyCheck = true;
            btnDone.setAlpha(1f);
        };


        // For List 3
        onSwitchListener1_smoking = (radioGroup, i, adapterPosition) -> {
            if (i == R.id.rbQues1Yes) {
                arrSmokingBehavior.get(adapterPosition).setAnswer1(true);
                adaptSmokingBehavior.notifyItemChanged(adapterPosition);
            } else {
                arrSmokingBehavior.get(adapterPosition).setAnswer1(false);
                arrSmokingBehavior.get(adapterPosition).setIsUnderTreatment("");
                adaptSmokingBehavior.notifyItemChanged(adapterPosition);
            }
            parentFragment.dirtyCheck = true;
            btnDone.setAlpha(1f);
        };

        onSwitchListener2_smoking = (radioGroup, i, adapterPosition) -> {
            if (i == R.id.rbQues2Yes) {
                arrSmokingBehavior.get(adapterPosition).setAnswer2(true);
                adaptSmokingBehavior.notifyItemChanged(adapterPosition);
            } else {
                arrSmokingBehavior.get(adapterPosition).setAnswer2(false);
                adaptSmokingBehavior.notifyItemChanged(adapterPosition);
            }
            parentFragment.dirtyCheck = true;
            btnDone.setAlpha(1f);
        };

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
        parentFragment = (NewAssessmentViewPagerFragment) getParentFragment();
        getDataAndBindAdapter();

        setTitleData();
        btnDone.setVisibility(View.VISIBLE);

        bindView();
        bindData();
    }

    private void getDataAndBindAdapter() {
        arrFamilyHistory = parentFragment.getArrFamilyHistory();
        arrSmokingBehavior = parentFragment.getArrSmokingBehavior();
        arrSocialHistory = parentFragment.getArrSocialHistory();

        adaptFamilyHistory = new AssessmentQuestionAdapter(getBaseActivity(), arrFamilyHistory, this, onSwitchListener1_family, onSwitchListener2_family);
        adaptSocialHistory = new AssessmentQuestionAdapter(getBaseActivity(), arrSocialHistory, this, onSwitchListener1_social, onSwitchListener2_social);
        adaptSmokingBehavior = new AssessmentQuestionAdapter(getBaseActivity(), arrSmokingBehavior, this, onSwitchListener1_smoking, onSwitchListener2_smoking);
    }

    private void setTitleData() {
        txtTitleFamily.setText("Family History");
        txtTitleSocial.setText("Psychosocial History");
        txtTitleSmoking.setText("Smoking Behaviors");

        if (!arrFamilyHistory.isEmpty()) {
            txtSubtitleFamily.setText(arrFamilyHistory.get(0).getCategoryDescription());
        } else {
            txtSubtitleFamily.setText("");
        }

        if (!arrSocialHistory.isEmpty()) {
            txtSubtitleSocial.setText(arrSocialHistory.get(0).getCategoryDescription());
        } else {
            txtSubtitleSocial.setText("");
        }

        if (!arrSmokingBehavior.isEmpty()) {
            txtSubtitleSmoking.setText(arrSmokingBehavior.get(0).getCategoryDescription());
        } else {
            txtSubtitleSmoking.setText("");
        }
    }

    private void bindData() {
        adaptFamilyHistory.notifyDataSetChanged();
        adaptSocialHistory.notifyDataSetChanged();
        adaptSmokingBehavior.notifyDataSetChanged();
    }


    private void bindView() {
        // List 1
        RecyclerView.LayoutManager mLayoutManager1 = new CustomLayoutManager(getBaseActivity(), LinearLayoutManager.VERTICAL, false);
        recylerView1.setLayoutManager(mLayoutManager1);
        ((DefaultItemAnimator) recylerView1.getItemAnimator()).setSupportsChangeAnimations(false);
        recylerView1.setAdapter(adaptFamilyHistory);

        // List 2
        RecyclerView.LayoutManager mLayoutManager2 = new CustomLayoutManager(getBaseActivity(), LinearLayoutManager.VERTICAL, false);
        recylerView2.setLayoutManager(mLayoutManager2);
        ((DefaultItemAnimator) recylerView2.getItemAnimator()).setSupportsChangeAnimations(false);
        recylerView2.setAdapter(adaptSocialHistory);

        // List 3
        RecyclerView.LayoutManager mLayoutManager = new CustomLayoutManager(getBaseActivity(), LinearLayoutManager.VERTICAL, false);
        recylerView3.setLayoutManager(mLayoutManager);
        ((DefaultItemAnimator) recylerView3.getItemAnimator()).setSupportsChangeAnimations(false);
        recylerView3.setAdapter(adaptSmokingBehavior);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onItemClick(int position, Object object, View view) {

    }


    @OnClick({R.id.fabBack, R.id.btnCancel, R.id.btnDone})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fabBack:
                parentFragment.viewpager.movePrevious();
                break;
            case R.id.btnCancel:
                getBaseActivity().popBackStack();
                break;
            case R.id.btnDone:
                if (!parentFragment.dirtyCheck) return;


                if (!validateQuestions(dataArray)) return;

                String jsonArrayData = GsonFactory.getConfiguredGson().toJson(dataArray);
                saveEmployeeAssessment(jsonArrayData);

                break;
        }
    }


    private void saveEmployeeAssessment(String jsonArrayData) {
        new WebServices(getContext(), "", BaseURLTypes.EHS_BASE_URL, true)
                .webServiceRequestAPIAnyObject(WebServiceConstants.METHOD_SAVE_EMPLOYEE_ASSESSMENT, jsonArrayData,
                        new WebServices.IRequestWebResponseAnyObjectCallBack() {
                            @Override
                            public void requestDataResponse(WebResponse<Object> webResponse) {


                                if (sessionDetailModel.getStatusEnum() != EmployeeSessionState.INPROGRESS) {
                                    ArrayList<SessionDetailModel> sessionDetailModelArrayList = new ArrayList<>();

                                    sessionDetailModel.setStatusID(EmployeeSessionState.INPROGRESS.canonicalForm());
                                    sessionDetailModel.setLastFileUser(getCurrentUser().getName());

                                    sessionDetailModelArrayList.add(sessionDetailModel);
                                    String jsonArrayData = GsonFactory.getConfiguredGson().toJson(sessionDetailModelArrayList);
                                    updateEmployeeInSessionCall(jsonArrayData);
                                } else {
                                    UIHelper.showToast(getContext(), webResponse.responseMessage);
                                    getBaseActivity().popBackStack();
                                }

                            }

                            @Override
                            public void onError(Object object) {
                                if (object instanceof String) {
                                    UIHelper.showToast(getContext(), (String) object);
                                }
                            }
                        });
    }


    private void updateEmployeeInSessionCall(String jsonArrayData) {
        new WebServices(getContext(), "", BaseURLTypes.EHS_BASE_URL, true)
                .webServiceRequestAPIAnyObject(WebServiceConstants.METHOD_UPDATE_SESSION_EMPLOYEE, jsonArrayData,
                        new WebServices.IRequestWebResponseAnyObjectCallBack() {
                            @Override
                            public void requestDataResponse(WebResponse<Object> webResponse) {
                                UIHelper.showToast(getContext(), webResponse.responseMessage);
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


}

