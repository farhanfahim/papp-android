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

import com.badoualy.stepperindicator.StepperIndicator;
import com.jcminarro.roundkornerlayout.RoundKornerLinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import edu.aku.ehs.R;
import edu.aku.ehs.callbacks.OnItemClickListener;
import edu.aku.ehs.fragments.abstracts.BaseFragment;
import edu.aku.ehs.models.SessionDetailModel;
import edu.aku.ehs.widget.AnyTextView;
import edu.aku.ehs.widget.TitleBar;

public class EmployeeAssessmentDashboardFragment extends BaseFragment implements OnItemClickListener {


    Unbinder unbinder;
    @BindView(R.id.imgBanner)
    ImageView imgBanner;
    @BindView(R.id.txtEmployeeName)
    AnyTextView txtEmployeeName;
    @BindView(R.id.txtEmployeeGender)
    AnyTextView txtEmployeeGender;
    @BindView(R.id.txtMRN)
    AnyTextView txtMRN;
    @BindView(R.id.txDate)
    AnyTextView txDate;
    @BindView(R.id.txtStatus)
    AnyTextView txtStatus;
    @BindView(R.id.stepView)
    StepperIndicator stepView;
    @BindView(R.id.btnDelete)
    ImageView btnDelete;
    @BindView(R.id.btnSchedule)
    ImageView btnSchedule;
    @BindView(R.id.contListItem)
    RoundKornerLinearLayout contListItem;
    @BindView(R.id.imgSelected)
    ImageView imgSelected;
    @BindView(R.id.contParent)
    RelativeLayout contParent;
    SessionDetailModel sessionDetailModel;


    public static EmployeeAssessmentDashboardFragment newInstance() {

        Bundle args = new Bundle();

        EmployeeAssessmentDashboardFragment fragment = new EmployeeAssessmentDashboardFragment();
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
        titleBar.setTitle("Employee Assessment Dashboard");
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
        btnDelete.setVisibility(View.GONE);
        btnSchedule.setVisibility(View.GONE);
        txtEmployeeName.setText("Kamran Nayyer");
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
                getBaseActivity().addDockableFragment(NewAssessmentViewPagerFragment.newInstance(), false);
                break;
            case R.id.contMeasurements:
                getBaseActivity().addDockableFragment(EmployeeAnthropometricMeasurmentsFragment.newInstance(), false);
                break;
            case R.id.contRefer:
                getBaseActivity().addDockableFragment(EmployeeProfileViewerFragment.newInstance(), false);
                break;
        }
    }
}
