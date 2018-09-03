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
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.jcminarro.roundkornerlayout.RoundKornerLinearLayout;
import com.shawnlin.numberpicker.NumberPicker;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import edu.aku.ehs.R;
import edu.aku.ehs.adapters.recyleradapters.AssessmentQuestionAdapter;
import edu.aku.ehs.callbacks.OnItemClickListener;
import edu.aku.ehs.enums.QuestionTypeEnum;
import edu.aku.ehs.fragments.abstracts.BaseFragment;
import edu.aku.ehs.helperclasses.ui.helper.UIHelper;
import edu.aku.ehs.models.AssessmentQuestionModel;
import edu.aku.ehs.widget.AnyEditTextView;
import edu.aku.ehs.widget.AnyTextView;
import edu.aku.ehs.widget.TitleBar;

public class EmployeeAnthropometricMeasurmentsFragment extends BaseFragment implements OnItemClickListener {


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
    @BindView(R.id.btnRecordMenually)
    AnyTextView btnRecordMenually;
    @BindView(R.id.txtHeightDesc)
    AnyTextView txtHeightDesc;
    @BindView(R.id.pickerHeight)
    NumberPicker pickerHeight;
    @BindView(R.id.contHeight)
    LinearLayout contHeight;
    @BindView(R.id.txtWeightDesc)
    AnyTextView txtWeightDesc;
    @BindView(R.id.pickerWeight)
    NumberPicker pickerWeight;
    @BindView(R.id.contWaist)
    LinearLayout contWaist;
    @BindView(R.id.contBMI)
    LinearLayout contBMI;
    @BindView(R.id.txtWaistDesc)
    AnyTextView txtWaistDesc;
    @BindView(R.id.pickerWaist)
    NumberPicker pickerWaist;
    @BindView(R.id.contWeight)
    LinearLayout contWeight;
    @BindView(R.id.txtBPsystolicDes)
    AnyTextView txtBPsystolicDes;
    @BindView(R.id.pickerSystolic)
    NumberPicker pickerSystolic;
    @BindView(R.id.contSystolicBP)
    LinearLayout contSystolicBP;
    @BindView(R.id.txtBPDiastolicDes)
    AnyTextView txtBPDiastolicDes;
    @BindView(R.id.pickerDiastolic)
    NumberPicker pickerDiastolic;
    @BindView(R.id.contDystolicBP)
    LinearLayout contDystolicBP;
    @BindView(R.id.contParent)
    RelativeLayout contParent;


    public static EmployeeAnthropometricMeasurmentsFragment newInstance() {

        Bundle args = new Bundle();

        EmployeeAnthropometricMeasurmentsFragment fragment = new EmployeeAnthropometricMeasurmentsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getDrawerLockMode() {
        return DrawerLayout.LOCK_MODE_LOCKED_CLOSED;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_employee_anthropometric_measurements;
    }

    @Override
    public void setTitlebar(TitleBar titleBar) {
        titleBar.resetViews();
        titleBar.setVisibility(View.VISIBLE);
        titleBar.showHome(getBaseActivity());
        titleBar.setTitle("Employee Anthropometric Measurments");
        titleBar.showBackButton(getBaseActivity());
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

}
