package edu.aku.ehs.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.github.clans.fab.FloatingActionButton;
import com.google.gson.reflect.TypeToken;
import com.jcminarro.roundkornerlayout.RoundKornerLinearLayout;
import com.viewpagerindicator.CirclePageIndicator;

import java.lang.reflect.Type;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import edu.aku.ehs.R;
import edu.aku.ehs.adapters.EmployeeAssessmentPagerAdapter;
import edu.aku.ehs.callbacks.OnItemClickListener;
import edu.aku.ehs.constatnts.WebServiceConstants;
import edu.aku.ehs.enums.BaseURLTypes;
import edu.aku.ehs.enums.CategoryType;
import edu.aku.ehs.fragments.abstracts.BaseFragment;
import edu.aku.ehs.helperclasses.ui.helper.UIHelper;
import edu.aku.ehs.managers.retrofit.GsonFactory;
import edu.aku.ehs.managers.retrofit.WebServices;
import edu.aku.ehs.models.AssessmentQuestionModel;
import edu.aku.ehs.models.SessionDetailModel;
import edu.aku.ehs.models.sending_model.EmployeeSendingModel;
import edu.aku.ehs.models.wrappers.WebResponse;
import edu.aku.ehs.widget.AnyEditTextView;
import edu.aku.ehs.widget.AnyTextView;
import edu.aku.ehs.widget.CustomViewPager;
import edu.aku.ehs.widget.TitleBar;

public class NewAssessmentViewPagerFragment extends BaseFragment implements OnItemClickListener {

    Unbinder unbinder;
    @BindView(R.id.empty_view)
    AnyTextView emptyView;
    @BindView(R.id.imgBanner)
    ImageView imgBanner;
    @BindView(R.id.btnGetLabs)
    Button btnGetLabs;
    @BindView(R.id.btnAddEmail)
    Button btnAddEmail;
    @BindView(R.id.btnAddSchedule)
    Button btnAddSchedule;
    @BindView(R.id.btnAddEmployees)
    Button btnAddEmployees;
    @BindView(R.id.contOptionButtons)
    LinearLayout contOptionButtons;
    @BindView(R.id.cbSelectAll)
    CheckBox cbSelectAll;
    @BindView(R.id.contSelection)
    LinearLayout contSelection;
    @BindView(R.id.imgSearch)
    ImageView imgSearch;
    @BindView(R.id.edtSearchBar)
    AnyEditTextView edtSearchBar;
    @BindView(R.id.contSearch)
    RoundKornerLinearLayout contSearch;
    @BindView(R.id.pagerIndicator)
    public CirclePageIndicator pagerIndicator;
    @BindView(R.id.viewpager)
    public CustomViewPager viewpager;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.contParent)
    RelativeLayout contParent;

    SessionDetailModel sessionDetailModel;
    public boolean dirtyCheck = false;


    private EmployeeAssessmentPagerAdapter adapter;
    private ArrayList<AssessmentQuestionModel> arrMedicalHistory;
    private ArrayList<AssessmentQuestionModel> arrFamilyHistory;
    private ArrayList<AssessmentQuestionModel> arrSocialHistory;
    private ArrayList<AssessmentQuestionModel> arrSmokingBehavior;

    public static NewAssessmentViewPagerFragment newInstance(SessionDetailModel sessionDetailModel) {

        Bundle args = new Bundle();

        NewAssessmentViewPagerFragment fragment = new NewAssessmentViewPagerFragment();
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
        return R.layout.fragment_circular_viewpage;
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

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        arrFamilyHistory = new ArrayList<>();
        arrMedicalHistory = new ArrayList<>();
        arrSmokingBehavior = new ArrayList<>();
        arrSocialHistory = new ArrayList<>();

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
        if (onCreated) {
            setViewPagerAdapter();
            return;
        }

        EmployeeSendingModel model = new EmployeeSendingModel();
        model.setEmployeeNo(sessionDetailModel.getEmployeeNo());
        model.setSessionID(sessionDetailModel.getSessionID());
        getEmployeeAssessmentList(model);
    }

    private void setViewPagerAdapter() {
        adapter = new EmployeeAssessmentPagerAdapter(getChildFragmentManager(), sessionDetailModel);
        viewpager.setAdapter(adapter);
        pagerIndicator.setViewPager(viewpager);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onItemClick(int position, Object object, View view) {

    }


    private void getEmployeeAssessmentList(EmployeeSendingModel model) {
        new WebServices(getContext(), "", BaseURLTypes.EHS_BASE_URL, true)
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

                                    for (AssessmentQuestionModel assessmentQuestionModel : arrayList) {
                                        assessmentQuestionModel.setCategoryType(CategoryType.fromCanonicalForm(assessmentQuestionModel.getCategoryID()));
                                        assessmentQuestionModel.setSessionID(sessionDetailModel.getSessionID());
                                        assessmentQuestionModel.setEmployeeNo(sessionDetailModel.getEmployeeNo());
                                        switch (assessmentQuestionModel.getCategoryType()) {
                                            case MEDICALHISTORY:
                                                arrMedicalHistory.add(assessmentQuestionModel);
                                                break;
                                            case FAMILYHISTORY:
                                                arrFamilyHistory.add(assessmentQuestionModel);
                                                break;
                                            case SOCIALHISTORY:
                                                arrSocialHistory.add(assessmentQuestionModel);
                                                break;
                                            case SMOKINGBEHAVIOR:
                                                arrSmokingBehavior.add(assessmentQuestionModel);
                                                break;
                                        }
                                    }
                                    setViewPagerAdapter();

                                }
                            }

                            @Override
                            public void onError(Object object) {
//                                if (object instanceof String) {
//                                    UIHelper.showToast(getContext(), (String) object);
//                                }
                                getActiveQuestionList();
                            }
                        });
    }

    private void getActiveQuestionList() {
        new WebServices(getContext(), "", BaseURLTypes.EHS_BASE_URL, true)
                .webServiceRequestAPIAnyObject(WebServiceConstants.METHOD_GET_ACTIVE_QUESTION_LIST, "",
                        new WebServices.IRequestWebResponseAnyObjectCallBack() {
                            @Override
                            public void requestDataResponse(WebResponse<Object> webResponse) {
                                if (webResponse.result instanceof ArrayList) {

                                    Type type = new TypeToken<ArrayList<AssessmentQuestionModel>>() {
                                    }.getType();
                                    ArrayList<AssessmentQuestionModel> arrayList = GsonFactory.getSimpleGson()
                                            .fromJson(GsonFactory.getSimpleGson().toJson(webResponse.result)
                                                    , type);

                                    for (AssessmentQuestionModel assessmentQuestionModel : arrayList) {
                                        assessmentQuestionModel.setCategoryType(CategoryType.fromCanonicalForm(assessmentQuestionModel.getCategoryID()));
                                        assessmentQuestionModel.setSessionID(sessionDetailModel.getSessionID());
                                        assessmentQuestionModel.setEmployeeNo(sessionDetailModel.getEmployeeNo());
                                        switch (assessmentQuestionModel.getCategoryType()) {
                                            case MEDICALHISTORY:
                                                arrMedicalHistory.add(assessmentQuestionModel);
                                                break;
                                            case FAMILYHISTORY:
                                                arrFamilyHistory.add(assessmentQuestionModel);
                                                break;
                                            case SOCIALHISTORY:
                                                arrSocialHistory.add(assessmentQuestionModel);
                                                break;
                                            case SMOKINGBEHAVIOR:
                                                arrSmokingBehavior.add(assessmentQuestionModel);
                                                break;
                                        }
                                    }
                                    setViewPagerAdapter();
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


    public ArrayList<AssessmentQuestionModel> getArrMedicalHistory() {
        return arrMedicalHistory;
    }

    public ArrayList<AssessmentQuestionModel> getArrFamilyHistory() {
        return arrFamilyHistory;
    }

    public ArrayList<AssessmentQuestionModel> getArrSocialHistory() {
        return arrSocialHistory;
    }

    public ArrayList<AssessmentQuestionModel> getArrSmokingBehavior() {
        return arrSmokingBehavior;
    }
}
