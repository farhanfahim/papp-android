package edu.aku.ehs.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jcminarro.roundkornerlayout.RoundKornerLinearLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import edu.aku.ehs.R;
import edu.aku.ehs.adapters.recyleradapters.DepartmentAdapter;
import edu.aku.ehs.callbacks.OnItemClickListener;
import edu.aku.ehs.constatnts.AppConstants;
import edu.aku.ehs.enums.BaseURLTypes;
import edu.aku.ehs.enums.SearchByType;
import edu.aku.ehs.enums.SelectEmployeeActionType;
import edu.aku.ehs.fragments.abstracts.BaseFragment;
import edu.aku.ehs.helperclasses.kotlinhelper.KotlinScriptsEHS;
import edu.aku.ehs.helperclasses.ui.helper.UIHelper;
import edu.aku.ehs.libraries.maskformatter.MaskFormatter;
import edu.aku.ehs.managers.retrofit.WebServices;
import edu.aku.ehs.models.IntWrapper;
import edu.aku.ehs.models.SessionModel;
import edu.aku.ehs.models.SpinnerModel;
import edu.aku.ehs.models.peoplesoft.department.DEPT;
import edu.aku.ehs.models.peoplesoft.department.DepartmentWrapper;
import edu.aku.ehs.widget.AnyEditTextView;
import edu.aku.ehs.widget.AnyTextView;
import edu.aku.ehs.widget.TitleBar;

import static edu.aku.ehs.constatnts.WebServiceConstants.DIV_KEY;
import static edu.aku.ehs.constatnts.WebServiceConstants.GET_ALL_KEY;

/**
 * Created by hamza.ahmed on 7/23/2018.
 */

public class SearchFragment extends BaseFragment implements OnItemClickListener {

    Unbinder unbinder;
    @BindView(R.id.empty_view)
    AnyTextView emptyView;
    @BindView(R.id.imgBanner)
    ImageView imgBanner;
    @BindView(R.id.txtSessionName)
    AnyTextView txtSessionName;
    @BindView(R.id.edtMRNumber)
    AnyEditTextView edtMRNumber;
    @BindView(R.id.imgSearchByMR)
    ImageView imgSearchByMR;
    @BindView(R.id.contSearchByMR)
    RoundKornerLinearLayout contSearchByMR;
    @BindView(R.id.edtEmployeeID)
    AnyEditTextView edtEmployeeID;
    @BindView(R.id.imgSearchByEmplID)
    ImageView imgSearchByEmplID;
    @BindView(R.id.contSearchByEmplID)
    RoundKornerLinearLayout contSearchByEmplID;
    @BindView(R.id.txtSelectDivision)
    AnyTextView txtSelectDivision;
    @BindView(R.id.contSelectDivision)
    RoundKornerLinearLayout contSelectDivision;
    @BindView(R.id.imgSearch)
    ImageView imgSearch;
    @BindView(R.id.edtSearchBar)
    AnyEditTextView edtSearchBar;
    @BindView(R.id.contSearch)
    RoundKornerLinearLayout contSearch;
    @BindView(R.id.recylerView)
    RecyclerView recylerView;
    @BindView(R.id.contParent)
    RelativeLayout contParent;


    private ArrayList<DEPT> arrDivisions;

    private ArrayList<DEPT> arrDept;
    private DepartmentAdapter adapter;
    private SelectEmployeeActionType selectEmployeeActionType;
    private SpinnerModel tempSpinnerModel;
    private IntWrapper currentDivisionPosition = new IntWrapper(-1);
    private ArrayList<SpinnerModel> arrDivisionSpinner;

    SessionModel sessionModel;


    public static SearchFragment newInstance(SelectEmployeeActionType selectEmployeeActionType, SessionModel sessionModel) {
        Bundle args = new Bundle();
        SearchFragment fragment = new SearchFragment();
        fragment.selectEmployeeActionType = selectEmployeeActionType;
        fragment.sessionModel = sessionModel;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getDrawerLockMode() {
        return DrawerLayout.LOCK_MODE_LOCKED_CLOSED;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_search_department_employee;
    }

    @Override
    public void setTitlebar(TitleBar titleBar) {
        titleBar.resetViews();
        titleBar.setVisibility(View.VISIBLE);
        titleBar.setTitle("Add Employees");
        titleBar.showBackButton(getBaseActivity());
        titleBar.showHome(getBaseActivity());
    }

    @Override
    public void setListeners() {


        edtMRNumber.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    imgSearchByMR.performClick();
                    return true;
                }
                return false;
            }
        });


        edtEmployeeID.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    imgSearchByEmplID.performClick();
                    return true;
                }
                return false;
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

        arrDivisions = new ArrayList<DEPT>();
        arrDept = new ArrayList<DEPT>();
        arrDivisionSpinner = new ArrayList<>();
        adapter = new DepartmentAdapter(getBaseActivity(), arrDept, this);

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

        txtSessionName.setText(sessionModel.getDescription());
        edtMRNumber.addTextChangedListener(new MaskFormatter(AppConstants.MR_NUMBER_MASK, edtMRNumber, '-'));
        if (tempSpinnerModel != null) {
            txtSelectDivision.setText(tempSpinnerModel.getText());
        }

        bindView();


        if (onCreated) {
            return;
        }

        emptyView.setVisibility(View.VISIBLE);
        getAllDivisionsCall();
    }

    private void getAllDivisionsCall() {
        new WebServices(getContext(),
                "",
                BaseURLTypes.GET_EMP_DEPT_URL, true)
                .webServiceGetDeptDiv(DIV_KEY, GET_ALL_KEY, new WebServices.IRequestWebResponseDeptDivList() {
                    @Override
                    public void requestDataResponse(DepartmentWrapper webResponse) {

                        if (webResponse.getAKU_WA_DEPT_EMPS().getDEPT() != null && !webResponse.getAKU_WA_DEPT_EMPS().getDEPT().isEmpty()) {
                            arrDivisions.clear();


                            arrDivisions.addAll(KotlinScriptsEHS.INSTANCE.sortDeptArray(webResponse.getAKU_WA_DEPT_EMPS().getDEPT()));

                            for (int i = 0; i < arrDivisions.size(); i++) {
                                arrDivisionSpinner.add(new SpinnerModel(arrDivisions.get(i).getDESCR()));
                            }


                        } else {
                            UIHelper.showToast(getContext(), "No Data Division List");
                        }
                    }

                    @Override
                    public void onError(Object object) {

                    }
                });
    }


    private void getDepartmentsCall(String value) {
        new WebServices(getContext(),
                "",
                BaseURLTypes.GET_EMP_DEPT_URL, true)
                .webServiceGetDeptDiv(DIV_KEY, value, new WebServices.IRequestWebResponseDeptDivList() {
                    @Override
                    public void requestDataResponse(DepartmentWrapper webResponse) {

                        if (webResponse.getAKU_WA_DEPT_EMPS().getDEPT() != null && !webResponse.getAKU_WA_DEPT_EMPS().getDEPT().isEmpty()) {
                            arrDept.clear();
                            arrDept.addAll(KotlinScriptsEHS.INSTANCE.sortDeptArray(webResponse.getAKU_WA_DEPT_EMPS().getDEPT()));
                            adapter.notifyDataSetChanged();
                            emptyView.setVisibility(View.GONE);
                        } else {
                            emptyView.setVisibility(View.VISIBLE);
                            UIHelper.showToast(getContext(), "No Data on Division Code: " + value);
                        }
                    }

                    @Override
                    public void onError(Object object) {
                        arrDept.clear();
                        adapter.notifyDataSetChanged();
                        emptyView.setVisibility(View.VISIBLE);
                    }
                });
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
    public void onDestroy() {
        super.onDestroy();


    }

    @Override
    public void onItemClick(int position, Object object, View view) {
        DEPT model = (DEPT) object;

        switch (view.getId()) {
            case R.id.contListItem:
                getBaseActivity().addDockableFragment(SelectEmployeePagerFragment.newInstance(model.getDESCR(), selectEmployeeActionType, SearchByType.DEPARTMENT, sessionModel, model, arrDivisions.get(currentDivisionPosition.value)), false);
                break;
        }
    }

    @OnClick({R.id.imgSearchByMR, R.id.imgSearchByEmplID, R.id.contSelectDivision})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgSearchByMR:
                getBaseActivity().addDockableFragment(SelectEmployeePagerFragment.newInstance(edtMRNumber.getStringTrimmed(), selectEmployeeActionType, SearchByType.MRNUMBER, sessionModel, null, null), false);
                break;
            case R.id.imgSearchByEmplID:
                getBaseActivity().addDockableFragment(SelectEmployeePagerFragment.newInstance(edtEmployeeID.getStringTrimmed(), selectEmployeeActionType, SearchByType.EMPLOYEENUMBER, sessionModel, null, null), false);
                break;
            case R.id.contSelectDivision:
                showSpinner();
                break;
        }
    }

    private void showSpinner() {
        UIHelper.showSpinnerDialog(this, arrDivisionSpinner, "Select Division", txtSelectDivision, (position, object, adapter) -> {
            tempSpinnerModel = (SpinnerModel) object;
            tempSpinnerModel.setPositionInList(adapter.getArrData().indexOf(tempSpinnerModel));

            for (SpinnerModel arrDatum : adapter.getArrData()) {
                arrDatum.setSelected(false);
            }
            adapter.getArrData().get(tempSpinnerModel.getPositionInList()).setSelected(true);
            adapter.notifyDataSetChanged();
        }, data -> {
            if (tempSpinnerModel == null) {
                return;
            }
            txtSelectDivision.setText(tempSpinnerModel.getText());
            for (SpinnerModel arrDatum : arrDivisionSpinner) {
                arrDatum.setSelected(false);
            }
            arrDivisionSpinner.get(tempSpinnerModel.getPositionInList()).setSelected(true);
            currentDivisionPosition.value = tempSpinnerModel.getPositionInList();

            if (arrDivisions.get(currentDivisionPosition.value) != null) {
                getDepartmentsCall(arrDivisions.get(currentDivisionPosition.value).getDEPTID());
            } else {
                UIHelper.showToast(getContext(), "Wrong position");
            }

        }, currentDivisionPosition);
    }


}
