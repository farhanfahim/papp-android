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
import android.widget.RelativeLayout;

import com.shawnlin.numberpicker.NumberPicker;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import edu.aku.ehs.R;
import edu.aku.ehs.callbacks.OnItemClickListener;
import edu.aku.ehs.fragments.abstracts.BaseFragment;
import edu.aku.ehs.widget.AnyTextView;
import edu.aku.ehs.widget.TitleBar;

public class EmployeeAnthropometricMeasurmentsFragment extends BaseFragment implements OnItemClickListener {


    Unbinder unbinder;
    @BindView(R.id.imgBanner)
    ImageView imgBanner;
    @BindView(R.id.btnRecordMenually)
    AnyTextView btnRecordMenually;
    @BindView(R.id.txtBMI)
    AnyTextView txtBMI;
    @BindView(R.id.contBMI)
    LinearLayout contBMI;
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
    @BindView(R.id.btnDone)
    Button btnDone;
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
        titleBar.setTitle("Employee Anthropometric Measurements");
        titleBar.showBackButton(getBaseActivity());
    }

    @Override
    public void setListeners() {

        pickerWeight.setOnValueChangedListener((picker, oldVal, newVal) -> setBMI());
        pickerHeight.setOnValueChangedListener((picker, oldVal, newVal) -> setBMI());
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
        setBMI();

    }

    private void setBMI() {
        double heightInMeters = (pickerHeight.getValue() / 100f);
        int weight = pickerWeight.getValue();


//        double round = Math.sqrt((double) pickerHeight.getValue() * pickerWeight.getValue() / 3600);


        double result = (weight / (heightInMeters * heightInMeters));
        txtBMI.setText(new DecimalFormat("##.#").format(result));
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
        getBaseActivity().addDockableFragment(EmployeeProfileViewerFragment.newInstance(), false);
    }
}
