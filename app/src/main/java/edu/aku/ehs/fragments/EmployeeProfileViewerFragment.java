package edu.aku.ehs.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.github.clans.fab.FloatingActionButton;

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

public class EmployeeProfileViewerFragment extends BaseFragment implements OnItemClickListener {


    Unbinder unbinder;
    @BindView(R.id.imgBanner)
    ImageView imgBanner;
    @BindView(R.id.txtHeight)
    AnyTextView txtHeight;
    @BindView(R.id.txtHeightUnit)
    AnyTextView txtHeightUnit;
    @BindView(R.id.txtWeight)
    AnyTextView txtWeight;
    @BindView(R.id.txtWeightUnit)
    AnyTextView txtWeightUnit;
    @BindView(R.id.txtWaist)
    AnyTextView txtWaist;
    @BindView(R.id.txtBMI)
    AnyTextView txtBMI;
    @BindView(R.id.txtBSAUnit)
    LinearLayout txtBSAUnit;
    @BindView(R.id.txtBPSystolic)
    AnyTextView txtBPSystolic;
    @BindView(R.id.txtBPDiastolic)
    AnyTextView txtBPDiastolic;
    @BindView(R.id.cardMeasurement)
    CardView cardMeasurement;
    @BindView(R.id.txtResultHB)
    AnyTextView txtResultHB;
    @BindView(R.id.txtUnitHB)
    AnyTextView txtUnitHB;
    @BindView(R.id.txtResultFBS)
    AnyTextView txtResultFBS;
    @BindView(R.id.txtUnitFBS)
    AnyTextView txtUnitFBS;
    @BindView(R.id.txtResultLDL)
    AnyTextView txtResultLDL;
    @BindView(R.id.txtUnitLDL)
    AnyTextView txtUnitLDL;
    @BindView(R.id.txtResultHDL)
    AnyTextView txtResultHDL;
    @BindView(R.id.txtUnitHDL)
    AnyTextView txtUnitHDL;
    @BindView(R.id.txtResultTriglyceride)
    AnyTextView txtResultTriglyceride;
    @BindView(R.id.txtUnitTriglyceride)
    AnyTextView txtUnitTriglyceride;
    @BindView(R.id.txtResultHEPC)
    AnyTextView txtResultHEPC;
    @BindView(R.id.txtUnitHEPC)
    AnyTextView txtUnitHEPC;
    @BindView(R.id.btnDone)
    FloatingActionButton btnDone;
    @BindView(R.id.contParent)
    RelativeLayout contParent;
    SessionDetailModel sessionDetailModel;


    public static EmployeeProfileViewerFragment newInstance() {

        Bundle args = new Bundle();

        EmployeeProfileViewerFragment fragment = new EmployeeProfileViewerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getDrawerLockMode() {
        return DrawerLayout.LOCK_MODE_LOCKED_CLOSED;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_patient_profile_viewer;
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
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onItemClick(int position, Object object, View view) {

    }

    @OnClick(R.id.btnDone)
    public void onViewClicked() {
        getBaseActivity().popStackTill(EmployeeAssessmentDashboardFragment.class.getSimpleName());
    }
}
