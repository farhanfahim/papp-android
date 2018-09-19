package edu.aku.ehs.fragments;

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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.github.clans.fab.FloatingActionButton;
import com.jcminarro.roundkornerlayout.RoundKornerLinearLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import edu.aku.ehs.R;
import edu.aku.ehs.adapters.recyleradapters.SelectEmployeesAdapter;
import edu.aku.ehs.callbacks.OnItemClickListener;
import edu.aku.ehs.constatnts.AppConstants;
import edu.aku.ehs.constatnts.Events;
import edu.aku.ehs.constatnts.WebServiceConstants;
import edu.aku.ehs.enums.BaseURLTypes;
import edu.aku.ehs.enums.EmployeeSessionState;
import edu.aku.ehs.enums.SearchByType;
import edu.aku.ehs.enums.SelectEmployeeActionType;
import edu.aku.ehs.fragments.abstracts.BaseFragment;
import edu.aku.ehs.fragments.abstracts.GenericDialogFragment;
import edu.aku.ehs.helperclasses.ui.helper.UIHelper;
import edu.aku.ehs.managers.retrofit.GsonFactory;
import edu.aku.ehs.managers.retrofit.WebServices;
import edu.aku.ehs.models.SessionDetailModel;
import edu.aku.ehs.models.SessionModel;
import edu.aku.ehs.models.peoplesoft.department.DEPT;
import edu.aku.ehs.models.peoplesoft.employee.EMPLOYEE;
import edu.aku.ehs.models.peoplesoft.employee.EmployeeWrapper;
import edu.aku.ehs.models.wrappers.WebResponse;
import edu.aku.ehs.widget.AnyEditTextView;
import edu.aku.ehs.widget.AnyTextView;
import edu.aku.ehs.widget.TitleBar;

/**
 * Created by hamza.ahmed on 7/23/2018.
 */

public class SelectEmployeeFragment extends BaseFragment implements OnItemClickListener {

    Unbinder unbinder;
    @BindView(R.id.imgBanner)
    ImageView imgBanner;
    @BindView(R.id.cbSelectAll)
    CheckBox cbSelectAll;
    @BindView(R.id.empty_view)
    AnyTextView emptyView;
    @BindView(R.id.imgSearch)
    ImageView imgSearch;
    @BindView(R.id.edtSearchBar)
    AnyEditTextView edtSearchBar;
    @BindView(R.id.contSearch)
    RoundKornerLinearLayout contSearch;
    @BindView(R.id.recylerView)
    RecyclerView recylerView;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.contParent)
    RelativeLayout contParent;
    @BindView(R.id.contOptionButtons)
    LinearLayout contOptionButtons;
    @BindView(R.id.btnAddEmail)
    Button btnAddEmail;
    @BindView(R.id.btnAddSchedule)
    Button btnAddSchedule;
    @BindView(R.id.btnAddEmployees)
    Button btnAddEmployees;
    @BindView(R.id.contSelection)
    LinearLayout contSelection;
    @BindView(R.id.txtSessionName)
    AnyTextView txtSessionName;

    private ArrayList<EMPLOYEE> arrData;
    private SelectEmployeesAdapter adapter;
    private String searchKeyword = "";


    GenericDialogFragment genericDialogFragment = GenericDialogFragment.newInstance();
    private SelectEmployeeActionType selectEmployeeActionType;
    SessionModel sessionModel;
    private SearchByType searchByType;
    private DEPT deptModel;
    private DEPT division;


    public static SelectEmployeeFragment newInstance(String searchKeyword, SelectEmployeeActionType selectEmployeeActionType, SearchByType searchByType, SessionModel sessionModel, DEPT dept, DEPT division) {
        Bundle args = new Bundle();

        SelectEmployeeFragment fragment = new SelectEmployeeFragment();
        fragment.searchKeyword = searchKeyword;
        fragment.selectEmployeeActionType = selectEmployeeActionType;
        fragment.searchByType = searchByType;
        fragment.sessionModel = sessionModel;
        fragment.deptModel = dept;
        fragment.division = division;
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public int getDrawerLockMode() {
        return 0;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_general_recyler_view_with_option_buttons;
    }

    @Override
    public void setTitlebar(TitleBar titleBar) {
        titleBar.resetViews();
        titleBar.setVisibility(View.VISIBLE);
        titleBar.setTitle(searchKeyword);
        titleBar.showBackButton(getBaseActivity());
        titleBar.showHome(getBaseActivity());
    }

    @Override
    public void setListeners() {

        cbSelectAll.setOnCheckedChangeListener((compoundButton, b) -> {

                    if (b) {
                        for (int i = 0; i < arrData.size(); i++) {
                            arrData.get(i).setSelected(true);
                        }
                    } else {
                        for (int i = 0; i < arrData.size(); i++) {
                            arrData.get(i).setSelected(false);
                        }
                    }

                    adapter.notifyDataSetChanged();
                }

        );


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

        arrData = new ArrayList<EMPLOYEE>();
        adapter = new SelectEmployeesAdapter(getBaseActivity(), arrData, this);
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
        contSearch.setVisibility(View.VISIBLE);
        imgBanner.setVisibility(View.VISIBLE);
        contSelection.setVisibility(View.VISIBLE);
        txtSessionName.setVisibility(View.VISIBLE);
        txtSessionName.setText(sessionModel.getDescription());
        fab.setVisibility(View.VISIBLE);
        fab.setImageResource(R.drawable.ic_done_white_18dp);

        bindView();

        switch (searchByType) {
            case MRNUMBER:
                getEmployeeListCall(WebServiceConstants.MR_NO_KEY, searchKeyword);
                break;
            case EMPLOYEENUMBER:
                getEmployeeListCall(WebServiceConstants.EMPLOYEE_NO_KEY, searchKeyword);
                break;
            case DEPARTMENT:
                getEmployeeListCall(WebServiceConstants.DEPT_KEY, deptModel.getDEPTID());
                break;
        }

    }


    private void bindView() {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getBaseActivity());
        recylerView.setLayoutManager(mLayoutManager);
        ((DefaultItemAnimator) recylerView.getItemAnimator()).setSupportsChangeAnimations(false);
        int resId = R.anim.layout_animation_fall_bottom;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getContext(), resId);
//        recylerView.setLayoutAnimation(animation);
        recylerView.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onItemClick(int position, Object object, View view) {
        switch (view.getId()) {
            case R.id.contListItem:
                arrData.get(position).setSelected(!arrData.get(position).isSelected());
                adapter.notifyItemChanged(position);
                break;
        }
    }


    @OnClick({R.id.contSelection, R.id.fab})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.contSelection:
                cbSelectAll.performClick();
                break;
            case R.id.fab:
                switch (selectEmployeeActionType) {
                    case SENDEMAIL:

                        ArrayList<String> arrayList = new ArrayList<>();
                        for (EMPLOYEE employee : arrData) {
                            if (employee.isSelected()) {
                                arrayList.add(employee.getEMAIL_ADDR());
                            }
                        }
                        notifyToAll(Events.ON_EMPLOYEES_SELECTED_FOR_EMAIL, arrayList);
                        getBaseActivity().popStackTill(EmailFragment.class.getSimpleName());
                        break;
                    case ADDEMPLOYEE:
                        addSelectedEmployees();
                        break;
                }

                break;
        }
    }

    private void addSelectedEmployees() {
        ArrayList<SessionDetailModel> sessionDetailModelArrayList = new ArrayList<>();
        for (EMPLOYEE employee : arrData) {
            if (employee.isSelected()) {
                SessionDetailModel sessionDetailModel = new SessionDetailModel();
                sessionDetailModel.setSessionID(sessionModel.getSessionId());
                sessionDetailModel.setEmployeeNo(employee.getEMPLID());
                sessionDetailModel.setMedicalRecordNo(employee.getAKU_MRNO());
                sessionDetailModel.setStatusID(EmployeeSessionState.ENROLLED.canonicalForm());
                sessionDetailModel.setDepartmentID(employee.getDEPTID());
                sessionDetailModel.setDepartmentName(employee.getDESCR());


                if (division != null) {
                    sessionDetailModel.setDivisionID(division.getDEPTID());
                    sessionDetailModel.setDivisionName(division.getDESCR());
                }
                sessionDetailModel.setEnrolledBy(AppConstants.tempUserName);
                sessionDetailModel.setLastFileUser(AppConstants.tempUserName);

                sessionDetailModelArrayList.add(sessionDetailModel);
            }
        }

        String jsonArrayData = GsonFactory.getConfiguredGson().toJson(sessionDetailModelArrayList);
        addEmployeesInSessionCall(jsonArrayData);
    }


    private void getEmployeeListCall(String type, String value) {
        new WebServices(getContext(),
                "",
                BaseURLTypes.GET_EMP_DEPT_URL, true)
                .webServiceGetEmployees(type, value, new WebServices.IRequestWebResponseEmployeeList() {
                    @Override
                    public void requestDataResponse(EmployeeWrapper webResponse) {

                        if (webResponse.getAKU_WA_DEPT_EMPS().getEMPLOYEE() != null && !webResponse.getAKU_WA_DEPT_EMPS().getEMPLOYEE().isEmpty()) {
                            arrData.clear();
                            arrData.addAll(webResponse.getAKU_WA_DEPT_EMPS().getEMPLOYEE());
                            adapter.notifyDataSetChanged();
                            emptyView.setVisibility(View.GONE);

                        } else {
                            showEmptyView("No Record Found");
                        }
                    }

                    @Override
                    public void onError(Object object) {

                        showEmptyView("No Record Found");
                    }
                });
    }


    private void addEmployeesInSessionCall(String jsonArrayData) {
        new WebServices(getContext(), "", BaseURLTypes.EHS_BASE_URL, true)
                .webServiceRequestAPIAnyObject(WebServiceConstants.METHOD_ADD_SESSION_EMPLOYEE, jsonArrayData,
                        new WebServices.IRequestWebResponseAnyObjectCallBack() {
                            @Override
                            public void requestDataResponse(WebResponse<Object> webResponse) {
                                UIHelper.showToast(getContext(), webResponse.responseMessage);
                                AppConstants.isForcedResetFragment = true;
                                getBaseActivity().popStackTill(SessionDetailFragment.class.getSimpleName());
                            }

                            @Override
                            public void onError(Object object) {
                                emptyView.setVisibility(View.VISIBLE);
                                if (object instanceof String) {
                                    UIHelper.showToast(getContext(), (String) object);
                                }
                            }
                        });
    }


    private void showEmptyView(String text) {
        emptyView.setText(text);
        emptyView.setVisibility(View.VISIBLE);
    }

}
