package edu.aku.ehs.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.text.DecimalFormat;

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
import edu.aku.ehs.widget.custom_seekbar.OnRangeChangedListener;
import edu.aku.ehs.widget.custom_seekbar.RangeSeekBar;

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
    @BindView(R.id.txtHeight)
    AnyTextView txtHeight;
    @BindView(R.id.sbHeight)
    RangeSeekBar sbHeight;
    @BindView(R.id.contHeight)
    LinearLayout contHeight;
    @BindView(R.id.txtWeight)
    AnyTextView txtWeight;
    @BindView(R.id.sbWeight)
    RangeSeekBar sbWeight;
    @BindView(R.id.contWeight)
    LinearLayout contWeight;
    @BindView(R.id.txtWaist)
    AnyTextView txtWaist;
    @BindView(R.id.sbWaist)
    RangeSeekBar sbWaist;
    @BindView(R.id.contWaist)
    LinearLayout contWaist;
    @BindView(R.id.txtBPSystolic)
    AnyTextView txtBPSystolic;
    @BindView(R.id.sbSystolicBP)
    RangeSeekBar sbSystolicBP;
    @BindView(R.id.contSystolicBP)
    LinearLayout contSystolicBP;
    @BindView(R.id.txtBPDiastolic)
    AnyTextView txtBPDiastolic;
    @BindView(R.id.sbDiastolicBP)
    RangeSeekBar sbDiastolicBP;
    @BindView(R.id.contDiastolicBP)
    LinearLayout contDiastolicBP;
    @BindView(R.id.contParent)
    RelativeLayout contParent;
    int height = 60;
    int weight = 0;
    SessionDetailModel sessionDetailModel;


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
        return R.layout.fragment_employee_anthropometric_measurements_v2;
    }

    @Override
    public void setTitlebar(TitleBar titleBar) {
        titleBar.resetViews();
        titleBar.setVisibility(View.VISIBLE);
        titleBar.showHome(getBaseActivity());
        titleBar.setTitle("Employee Anthropometric Measurements");
        titleBar.showBackButton(getBaseActivity());
        titleBar.setEmployeeHeader(sessionDetailModel, getContext());
    }

    @Override
    public void setListeners() {

        sbHeight.setOnRangeChangedListener(new OnRangeChangedListener() {
            @Override
            public void onRangeChanged(RangeSeekBar view, float leftValue, float rightValue, boolean isFromUser) {
                txtHeight.setText(String.valueOf(Math.round(leftValue)));
                height = Math.round(leftValue);
                setBMI();
            }

            @Override
            public void onStartTrackingTouch(RangeSeekBar view, boolean isLeft) {

            }

            @Override
            public void onStopTrackingTouch(RangeSeekBar view, boolean isLeft) {

            }
        });

        sbWeight.setOnRangeChangedListener(new OnRangeChangedListener() {
            @Override
            public void onRangeChanged(RangeSeekBar view, float leftValue, float rightValue, boolean isFromUser) {
                txtWeight.setText(String.valueOf(Math.round(leftValue)));
                weight = Math.round(leftValue);
                setBMI();
            }

            @Override
            public void onStartTrackingTouch(RangeSeekBar view, boolean isLeft) {

            }

            @Override
            public void onStopTrackingTouch(RangeSeekBar view, boolean isLeft) {

            }
        });

        sbWaist.setOnRangeChangedListener(new OnRangeChangedListener() {
            @Override
            public void onRangeChanged(RangeSeekBar view, float leftValue, float rightValue, boolean isFromUser) {
                txtWaist.setText(String.valueOf(Math.round(leftValue)));
            }

            @Override
            public void onStartTrackingTouch(RangeSeekBar view, boolean isLeft) {

            }

            @Override
            public void onStopTrackingTouch(RangeSeekBar view, boolean isLeft) {

            }
        });

        sbSystolicBP.setOnRangeChangedListener(new OnRangeChangedListener() {
            @Override
            public void onRangeChanged(RangeSeekBar view, float leftValue, float rightValue, boolean isFromUser) {
                txtBPSystolic.setText(String.valueOf(Math.round(leftValue)));
            }

            @Override
            public void onStartTrackingTouch(RangeSeekBar view, boolean isLeft) {

            }

            @Override
            public void onStopTrackingTouch(RangeSeekBar view, boolean isLeft) {

            }
        });

        sbDiastolicBP.setOnRangeChangedListener(new OnRangeChangedListener() {
            @Override
            public void onRangeChanged(RangeSeekBar view, float leftValue, float rightValue, boolean isFromUser) {
                txtBPDiastolic.setText(String.valueOf(Math.round(leftValue)));
            }

            @Override
            public void onStartTrackingTouch(RangeSeekBar view, boolean isLeft) {

            }

            @Override
            public void onStopTrackingTouch(RangeSeekBar view, boolean isLeft) {

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
        setBMI();

    }

    private void setBMI() {
        double heightInMeters = (height / 100f);

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

    @OnClick(R.id.fab)
    public void onViewClicked() {
        getBaseActivity().addDockableFragment(EmployeeProfileViewerFragment.newInstance(), false);
    }
}
