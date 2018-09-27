package edu.aku.ehs.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import edu.aku.ehs.R;
import edu.aku.ehs.callbacks.OnItemClickListener;
import edu.aku.ehs.fragments.abstracts.BaseFragment;
import edu.aku.ehs.helperclasses.Helper;
import edu.aku.ehs.models.SessionDetailModel;
import edu.aku.ehs.widget.AnyTextView;
import edu.aku.ehs.widget.TitleBar;

public class EmployeeProfileViewerFragment extends BaseFragment implements OnItemClickListener {


    Unbinder unbinder;
    SessionDetailModel sessionDetailModel;
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
    @BindView(R.id.txtSmokingBehaviors)
    AnyTextView txtSmokingBehaviors;
    @BindView(R.id.contSmokingBehaviors)
    LinearLayout contSmokingBehaviors;
    @BindView(R.id.rbYes)
    RadioButton rbYes;
    @BindView(R.id.rbNo)
    RadioButton rbNo;
    @BindView(R.id.rgYesNoRecommend)
    RadioGroup rgYesNoRecommend;
    @BindView(R.id.contReferredTo)
    LinearLayout contReferredTo;
    @BindView(R.id.btnDone)
    Button btnDone;
    @BindView(R.id.contBelow)
    LinearLayout contBelow;
    @BindView(R.id.contParent)
    RelativeLayout contParent;


    public static EmployeeProfileViewerFragment newInstance(SessionDetailModel sessionDetailModel) {

        Bundle args = new Bundle();

        EmployeeProfileViewerFragment fragment = new EmployeeProfileViewerFragment();
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

        txtPsycosocialNone.setVisibility(View.VISIBLE);

// Medical History\
        Helper.addTextView(getContext(), contMedicalHistory, "High Blood Pressure (Treated)",
                (int) getBaseActivity().getResources().getDimension(R.dimen.s7), getBaseActivity().getResources().getColor(R.color.txtBlue));
        Helper.addTextView(getContext(), contMedicalHistory, "Hep B (Not Treated)",
                (int) getBaseActivity().getResources().getDimension(R.dimen.s7), getBaseActivity().getResources().getColor(R.color.txtBlue));

// Smoking Behaviors
        Helper.addTextView(getContext(), contSmokingBehaviors, "Smoker",
                (int) getBaseActivity().getResources().getDimension(R.dimen.s7), getBaseActivity().getResources().getColor(R.color.txtBlue));
        Helper.addTextView(getContext(), contSmokingBehaviors, "Thought of Quitting",
                (int) getBaseActivity().getResources().getDimension(R.dimen.s7), getBaseActivity().getResources().getColor(R.color.txtBlue));

// Family History
        Helper.addTextView(getContext(), contFamilyHistory, "High Blood Pressure",
                (int) getBaseActivity().getResources().getDimension(R.dimen.s7), getBaseActivity().getResources().getColor(R.color.txtBlue));
        Helper.addTextView(getContext(), contFamilyHistory, "Diabetes",
                (int) getBaseActivity().getResources().getDimension(R.dimen.s7), getBaseActivity().getResources().getColor(R.color.txtBlue));


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
