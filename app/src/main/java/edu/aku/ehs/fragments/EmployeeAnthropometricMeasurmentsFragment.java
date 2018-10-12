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

import com.github.clans.fab.FloatingActionButton;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import edu.aku.ehs.R;
import edu.aku.ehs.callbacks.OnItemClickListener;
import edu.aku.ehs.constatnts.WebServiceConstants;
import edu.aku.ehs.enums.BaseURLTypes;
import edu.aku.ehs.enums.EmployeeSessionState;
import edu.aku.ehs.enums.MeasurementsType;
import edu.aku.ehs.fragments.abstracts.BaseFragment;
import edu.aku.ehs.helperclasses.ui.helper.UIHelper;
import edu.aku.ehs.managers.retrofit.GsonFactory;
import edu.aku.ehs.managers.retrofit.WebServices;
import edu.aku.ehs.models.ActiveMeasurementsModel;
import edu.aku.ehs.models.SessionDetailModel;
import edu.aku.ehs.models.sending_model.EmployeeSendingModel;
import edu.aku.ehs.models.wrappers.WebResponse;
import edu.aku.ehs.widget.AnyEditTextView;
import edu.aku.ehs.widget.AnyTextView;
import edu.aku.ehs.widget.TitleBar;
import edu.aku.ehs.widget.custom_seekbar.OnRangeChangedListener;
import edu.aku.ehs.widget.custom_seekbar.RangeSeekBar;

public class EmployeeAnthropometricMeasurmentsFragment extends BaseFragment implements OnItemClickListener {


    Unbinder unbinder;

    int height = 60;
    int weight = 0;
    SessionDetailModel sessionDetailModel;

    ArrayList<ActiveMeasurementsModel> arrData;
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
    @BindView(R.id.txtHeight)
    AnyEditTextView txtHeight;
    @BindView(R.id.sbHeight)
    RangeSeekBar sbHeight;
    @BindView(R.id.contHeight)
    LinearLayout contHeight;
    @BindView(R.id.txtWeightDesc)
    AnyTextView txtWeightDesc;
    @BindView(R.id.txtWeight)
    AnyEditTextView txtWeight;
    @BindView(R.id.sbWeight)
    RangeSeekBar sbWeight;
    @BindView(R.id.contWeight)
    LinearLayout contWeight;
    @BindView(R.id.txtWaistDesc)
    AnyTextView txtWaistDesc;
    @BindView(R.id.txtWaist)
    AnyEditTextView txtWaist;
    @BindView(R.id.sbWaist)
    RangeSeekBar sbWaist;
    @BindView(R.id.contWaist)
    LinearLayout contWaist;
    @BindView(R.id.txtBPSystolicDesc)
    AnyTextView txtBPSystolicDesc;
    @BindView(R.id.txtBPSystolic)
    AnyEditTextView txtBPSystolic;
    @BindView(R.id.sbSystolicBP)
    RangeSeekBar sbSystolicBP;
    @BindView(R.id.contSystolicBP)
    LinearLayout contSystolicBP;
    @BindView(R.id.txtBPDiastolicDesc)
    AnyTextView txtBPDiastolicDesc;
    @BindView(R.id.txtBPDiastolic)
    AnyEditTextView txtBPDiastolic;
    @BindView(R.id.sbDiastolicBP)
    RangeSeekBar sbDiastolicBP;
    @BindView(R.id.contDiastolicBP)
    LinearLayout contDiastolicBP;
    @BindView(R.id.fabNext)
    FloatingActionButton fabNext;
    @BindView(R.id.btnCancel)
    Button btnCancel;
    @BindView(R.id.btnDone)
    Button btnDone;
    @BindView(R.id.contParent)
    LinearLayout contParent;


    public static EmployeeAnthropometricMeasurmentsFragment newInstance(SessionDetailModel sessionDetailModel) {

        Bundle args = new Bundle();

        EmployeeAnthropometricMeasurmentsFragment fragment = new EmployeeAnthropometricMeasurmentsFragment();
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


        txtHeight.setOnFocusChangeListener((view, b) -> {
            if (txtHeight == null) return;
            if (!b && !txtHeight.getStringTrimmed().isEmpty()) {
                if (Float.valueOf(txtHeight.getStringTrimmed()) > sbHeight.getMaxProgress()) {
                    txtHeight.setText("" + (int) sbHeight.getMaxProgress());
                } else if (Float.valueOf(txtHeight.getStringTrimmed()) < sbHeight.getMinProgress()) {
                    txtHeight.setText("" + (int) sbHeight.getMinProgress());
                }

                sbHeight.setValue(Float.valueOf(txtHeight.getStringTrimmed()));
            } else if (!b && txtHeight.getStringTrimmed().isEmpty()) {
                txtHeight.setText("" + (int) sbHeight.getMinProgress());
                sbHeight.setValue(Float.valueOf(txtHeight.getStringTrimmed()));
            }
        });


        txtWeight.setOnFocusChangeListener((view, b) -> {
            if (txtWeight == null) return;
            if (!b && !txtWeight.getStringTrimmed().isEmpty()) {
                if (Float.valueOf(txtWeight.getStringTrimmed()) > sbWeight.getMaxProgress()) {
                    txtWeight.setText("" + (int) sbWeight.getMaxProgress());
                } else if (Float.valueOf(txtWeight.getStringTrimmed()) < sbWeight.getMinProgress()) {
                    txtWeight.setText("" + (int) sbWeight.getMinProgress());
                }

                sbWeight.setValue(Float.valueOf(txtWeight.getStringTrimmed()));
            } else if (!b && txtWeight.getStringTrimmed().isEmpty()) {
                txtWeight.setText("" + (int) sbWeight.getMinProgress());
                sbWeight.setValue(Float.valueOf(txtWeight.getStringTrimmed()));
            }
        });


        txtWaist.setOnFocusChangeListener((view, b) -> {
            if (txtWaist == null) return;
            if (!b && !txtWaist.getStringTrimmed().isEmpty()) {
                if (Float.valueOf(txtWaist.getStringTrimmed()) > sbWaist.getMaxProgress()) {
                    txtWaist.setText("" + (int) sbWaist.getMaxProgress());
                } else if (Float.valueOf(txtWaist.getStringTrimmed()) < sbWaist.getMinProgress()) {
                    txtWaist.setText("" + (int) sbWaist.getMinProgress());
                }

                sbWaist.setValue(Float.valueOf(txtWaist.getStringTrimmed()));
            } else if (!b && txtWaist.getStringTrimmed().isEmpty()) {
                txtWaist.setText("" + (int) sbWaist.getMinProgress());
                sbWaist.setValue(Float.valueOf(txtWaist.getStringTrimmed()));
            }
        });


        txtBPSystolic.setOnFocusChangeListener((view, b) -> {
            if (txtBPSystolic == null) return;
            if (!b && !txtBPSystolic.getStringTrimmed().isEmpty()) {
                if (Float.valueOf(txtBPSystolic.getStringTrimmed()) > sbSystolicBP.getMaxProgress()) {
                    txtBPSystolic.setText("" + (int) sbSystolicBP.getMaxProgress());
                } else if (Float.valueOf(txtBPSystolic.getStringTrimmed()) < sbSystolicBP.getMinProgress()) {
                    txtBPSystolic.setText("" + (int) sbSystolicBP.getMinProgress());
                }

                sbSystolicBP.setValue(Float.valueOf(txtBPSystolic.getStringTrimmed()));
            } else if (!b && txtBPSystolic.getStringTrimmed().isEmpty()) {
                txtBPSystolic.setText("" + (int) sbSystolicBP.getMinProgress());
                sbSystolicBP.setValue(Float.valueOf(txtBPSystolic.getStringTrimmed()));
            }
        });


        txtBPDiastolic.setOnFocusChangeListener((view, b) -> {
            if (txtBPDiastolic == null) return;
            if (!b && !txtBPDiastolic.getStringTrimmed().isEmpty()) {
                if (Float.valueOf(txtBPDiastolic.getStringTrimmed()) > sbDiastolicBP.getMaxProgress()) {
                    txtBPDiastolic.setText("" + (int) sbDiastolicBP.getMaxProgress());
                } else if (Float.valueOf(txtBPDiastolic.getStringTrimmed()) < sbDiastolicBP.getMinProgress()) {
                    txtBPDiastolic.setText("" + (int) sbDiastolicBP.getMinProgress());
                }
                sbDiastolicBP.setValue(Float.valueOf(txtBPDiastolic.getStringTrimmed()));
            } else if (!b && txtBPDiastolic.getStringTrimmed().isEmpty()) {
                txtBPDiastolic.setText("" + (int) sbDiastolicBP.getMinProgress());
                sbDiastolicBP.setValue(Float.valueOf(txtBPDiastolic.getStringTrimmed()));
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
        arrData = new ArrayList<>();
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
//        getActiveMeasurementsList();

        EmployeeSendingModel model = new EmployeeSendingModel();
        model.setEmployeeNo(sessionDetailModel.getEmployeeNo());
        model.setSessionID(sessionDetailModel.getSessionID());
        getEmployeeMeasurementsList(model);

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


    private void onDonePressed() {
        ArrayList<ActiveMeasurementsModel> arrMeasurementToSend = new ArrayList<>();
        if (txtHeight.testValidity() && txtWeight.testValidity() && txtWaist.testValidity() && txtBPSystolic.testValidity() && txtBPDiastolic.testValidity()) {

            if (Integer.valueOf(txtBPSystolic.getStringTrimmed()) < Integer.valueOf(txtBPDiastolic.getStringTrimmed())) {
                UIHelper.showShortToastInCenter(getContext(), "Systolic should be greater than Diastolic BP.");
                return;
            }

            for (ActiveMeasurementsModel activeMeasurementsModel : arrData) {
                activeMeasurementsModel.setSessionID(sessionDetailModel.getSessionID());
                activeMeasurementsModel.setEmployeeNo(sessionDetailModel.getEmployeeNo());
                switch (activeMeasurementsModel.getMeasurementsType()) {
                    case WAIST:
                        activeMeasurementsModel.setValue(txtWaist.getStringTrimmed());
                        break;
                    case SBP:
                        activeMeasurementsModel.setValue(txtBPSystolic.getStringTrimmed());
                        break;
                    case DBP:
                        activeMeasurementsModel.setValue(txtBPDiastolic.getStringTrimmed());
                        break;
                    case HEIGHT:
                        activeMeasurementsModel.setValue(txtHeight.getStringTrimmed());
                        break;
                    case WEIGHT:
                        activeMeasurementsModel.setValue(txtWeight.getStringTrimmed());
                        break;
                }
                arrMeasurementToSend.add(activeMeasurementsModel);
            }

            String jsonArrayData = GsonFactory.getConfiguredGson().toJson(arrMeasurementToSend);
            saveEmployeeMeasurements(jsonArrayData);

        }
    }


    private void getActiveMeasurementsList() {
        new WebServices(getContext(), "", BaseURLTypes.EHS_BASE_URL, true)
                .webServiceRequestAPIAnyObject(WebServiceConstants.METHOD_GET_ACTIVE_MEASUREMENTS_LIST, "",
                        new WebServices.IRequestWebResponseAnyObjectCallBack() {
                            @Override
                            public void requestDataResponse(WebResponse<Object> webResponse) {
                                if (webResponse.result instanceof ArrayList) {

                                    Type type = new TypeToken<ArrayList<ActiveMeasurementsModel>>() {
                                    }.getType();
                                    ArrayList<ActiveMeasurementsModel> arrayList = GsonFactory.getSimpleGson()
                                            .fromJson(GsonFactory.getSimpleGson().toJson(webResponse.result)
                                                    , type);

                                    for (ActiveMeasurementsModel activeMeasurementsModel : arrayList) {
                                        activeMeasurementsModel.setMeasurementsType(MeasurementsType.fromCanonicalForm(activeMeasurementsModel.getMeasurementID()));
                                        switch (activeMeasurementsModel.getMeasurementsType()) {
                                            case WAIST:
                                                txtWaistDesc.setText(activeMeasurementsModel.getDescription() + " (" + activeMeasurementsModel.getUnitofMeasure() + ")");
                                                sbWaist.setRange(activeMeasurementsModel.getMinRange(), activeMeasurementsModel.getMaxRange());
                                                txtWaist.setText(activeMeasurementsModel.getMinRange() + "");
                                                break;
                                            case SBP:
                                                txtBPSystolicDesc.setText(activeMeasurementsModel.getDescription() + " (" + activeMeasurementsModel.getUnitofMeasure() + ")");
                                                sbSystolicBP.setRange(activeMeasurementsModel.getMinRange(), activeMeasurementsModel.getMaxRange());
                                                txtBPSystolic.setText(activeMeasurementsModel.getMinRange() + "");
                                                break;
                                            case DBP:
                                                txtBPDiastolicDesc.setText(activeMeasurementsModel.getDescription() + " (" + activeMeasurementsModel.getUnitofMeasure() + ")");
                                                sbDiastolicBP.setRange(activeMeasurementsModel.getMinRange(), activeMeasurementsModel.getMaxRange());
                                                txtBPDiastolic.setText(activeMeasurementsModel.getMinRange() + "");
                                                break;
                                            case HEIGHT:
                                                txtHeightDesc.setText(activeMeasurementsModel.getDescription() + " (" + activeMeasurementsModel.getUnitofMeasure() + ")");
                                                sbHeight.setRange(activeMeasurementsModel.getMinRange(), activeMeasurementsModel.getMaxRange());
                                                txtHeight.setText(activeMeasurementsModel.getMinRange() + "");
                                                height = activeMeasurementsModel.getMinRange();
                                                break;
                                            case WEIGHT:
                                                txtWeightDesc.setText(activeMeasurementsModel.getDescription() + " (" + activeMeasurementsModel.getUnitofMeasure() + ")");
                                                sbWeight.setRange(activeMeasurementsModel.getMinRange(), activeMeasurementsModel.getMaxRange());
                                                txtWeight.setText(activeMeasurementsModel.getMinRange() + "");
                                                weight = activeMeasurementsModel.getMinRange();
                                                break;
                                        }
                                    }
                                    arrData.clear();
                                    arrData.addAll(arrayList);
                                    setBMI();

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


    private void getEmployeeMeasurementsList(EmployeeSendingModel model) {
        new WebServices(getContext(), "", BaseURLTypes.EHS_BASE_URL, true)
                .webServiceRequestAPIAnyObject(WebServiceConstants.METHOD_GET_EMPLOYEE_MEASUREMENTS, model.toString(),
                        new WebServices.IRequestWebResponseAnyObjectCallBack() {
                            @Override
                            public void requestDataResponse(WebResponse<Object> webResponse) {
                                if (webResponse.result instanceof ArrayList) {

                                    Type type = new TypeToken<ArrayList<ActiveMeasurementsModel>>() {
                                    }.getType();
                                    ArrayList<ActiveMeasurementsModel> arrayList = GsonFactory.getSimpleGson()
                                            .fromJson(GsonFactory.getSimpleGson().toJson(webResponse.result)
                                                    , type);

                                    for (ActiveMeasurementsModel activeMeasurementsModel : arrayList) {
                                        activeMeasurementsModel.setMeasurementsType(MeasurementsType.fromCanonicalForm(activeMeasurementsModel.getMeasurementID()));
                                        switch (activeMeasurementsModel.getMeasurementsType()) {
                                            case WAIST:
                                                txtWaistDesc.setText(activeMeasurementsModel.getDescription() + " (" + activeMeasurementsModel.getUnitofMeasure() + ")");
                                                sbWaist.setRange(activeMeasurementsModel.getMinRange(), activeMeasurementsModel.getMaxRange());
                                                sbWaist.setValue(Float.valueOf(activeMeasurementsModel.getValue()));
                                                break;
                                            case SBP:
                                                txtBPSystolicDesc.setText(activeMeasurementsModel.getDescription() + " (" + activeMeasurementsModel.getUnitofMeasure() + ")");
                                                sbSystolicBP.setRange(activeMeasurementsModel.getMinRange(), activeMeasurementsModel.getMaxRange());
                                                sbSystolicBP.setValue(Float.valueOf(activeMeasurementsModel.getValue()));
                                                break;
                                            case DBP:
                                                txtBPDiastolicDesc.setText(activeMeasurementsModel.getDescription() + " (" + activeMeasurementsModel.getUnitofMeasure() + ")");
                                                sbDiastolicBP.setRange(activeMeasurementsModel.getMinRange(), activeMeasurementsModel.getMaxRange());
                                                sbDiastolicBP.setValue(Float.valueOf(activeMeasurementsModel.getValue()));
                                                break;
                                            case HEIGHT:
                                                txtHeightDesc.setText(activeMeasurementsModel.getDescription() + " (" + activeMeasurementsModel.getUnitofMeasure() + ")");
                                                sbHeight.setRange(activeMeasurementsModel.getMinRange(), activeMeasurementsModel.getMaxRange());
                                                sbHeight.setValue(Float.valueOf(activeMeasurementsModel.getValue()));
                                                break;
                                            case WEIGHT:
                                                txtWeightDesc.setText(activeMeasurementsModel.getDescription() + " (" + activeMeasurementsModel.getUnitofMeasure() + ")");
                                                sbWeight.setRange(activeMeasurementsModel.getMinRange(), activeMeasurementsModel.getMaxRange());
                                                sbWeight.setValue(Float.valueOf(activeMeasurementsModel.getValue()));
                                                break;
                                        }
                                    }
                                    arrData.clear();
                                    arrData.addAll(arrayList);
                                    setBMI();

                                }
                            }

                            @Override
                            public void onError(Object object) {
//                                if (object instanceof String) {
//                                    UIHelper.showToast(getContext(), (String) object);
//                                }
                                getActiveMeasurementsList();
                            }
                        });
    }


    private void saveEmployeeMeasurements(String jsonArrayData) {
        new WebServices(getContext(), "", BaseURLTypes.EHS_BASE_URL, true)
                .webServiceRequestAPIAnyObject(WebServiceConstants.METHOD_SAVE_EMPLOYEE_MEASUREMENTS, jsonArrayData,
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

    @OnClick({R.id.btnCancel, R.id.btnDone})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnCancel:
                getBaseActivity().popBackStack();
                break;
            case R.id.btnDone:
                onDonePressed();
                break;
        }
    }
}
