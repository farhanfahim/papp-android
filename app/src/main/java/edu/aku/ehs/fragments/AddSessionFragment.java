package edu.aku.ehs.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;

import com.jcminarro.roundkornerlayout.RoundKornerRelativeLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import edu.aku.ehs.R;
import edu.aku.ehs.constatnts.AppConstants;
import edu.aku.ehs.constatnts.WebServiceConstants;
import edu.aku.ehs.enums.BaseURLTypes;
import edu.aku.ehs.enums.SessionStatus;
import edu.aku.ehs.fragments.abstracts.BaseFragment;
import edu.aku.ehs.helperclasses.ui.helper.UIHelper;
import edu.aku.ehs.managers.DateManager;
import edu.aku.ehs.managers.retrofit.WebServices;
import edu.aku.ehs.models.SessionModel;
import edu.aku.ehs.models.wrappers.WebResponse;
import edu.aku.ehs.widget.TitleBar;
import studio.carbonylgroup.textfieldboxes.ExtendedEditText;
import studio.carbonylgroup.textfieldboxes.TextFieldBoxes;

import static edu.aku.ehs.constatnts.WebServiceConstants._token;


/**
 * Created by hamza.ahmed on 7/19/2018.
 */

public class AddSessionFragment extends BaseFragment {


    Unbinder unbinder;
    @BindView(R.id.edtSessionName)
    ExtendedEditText edtSessionName;
    @BindView(R.id.txtStartDate)
    ExtendedEditText txtStartDate;
    @BindView(R.id.tfSessionID)
    TextFieldBoxes tfSessionID;
    @BindView(R.id.tfStartDate)
    TextFieldBoxes tfStartDate;
    @BindView(R.id.txtEndDate)
    ExtendedEditText txtEndDate;
    @BindView(R.id.tfEndDate)
    TextFieldBoxes tfEndDate;
    @BindView(R.id.contLogo)
    RoundKornerRelativeLayout contLogo;
    @BindView(R.id.edtSessionID)
    ExtendedEditText edtSessionID;
    @BindView(R.id.btnDone)
    Button btnDone;
    private String startDateInSendingFormat;
    private String endDateInSendingFormat;
    private long startTimeInMillis = 0;
    private long endTimeInMillis = 0;
    private boolean isAddingNewSession;
    private SessionModel sessionModel;


    /**
     * For update send false and model,
     * For new Session send true and null
     *
     * @param isAddingNewSession
     * @param sessionModel
     * @return
     */

    public static AddSessionFragment newInstance(boolean isAddingNewSession, SessionModel sessionModel) {

        Bundle args = new Bundle();
        AddSessionFragment fragment = new AddSessionFragment();
        fragment.isAddingNewSession = isAddingNewSession;
        fragment.sessionModel = sessionModel;
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public int getDrawerLockMode() {
        return 0;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_add_session;
    }

    @Override
    public void setTitlebar(TitleBar titleBar) {
        titleBar.resetViews();
        titleBar.setVisibility(View.VISIBLE);
        if (isAddingNewSession) {
            titleBar.setTitle("Add Session");
        } else {
            if (sessionModel != null) {
                titleBar.setTitle("Update " + sessionModel.getDescription());
            } else {
                titleBar.setTitle("Update Session");
            }
        }
        titleBar.showHome(getBaseActivity());
        titleBar.showBackButton(getBaseActivity());
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (isAddingNewSession) {
            btnDone.setText("Add");
        } else {
            edtSessionID.setText(sessionModel.getSessionId());
            edtSessionName.setText(sessionModel.getDescription());
            txtStartDate.setText(sessionModel.getDisplayStartDate());
            txtEndDate.setText(sessionModel.getDisplayEndDate());
            tfSessionID.setEnabled(false);
            edtSessionID.setEnabled(false);
            btnDone.setText("Update");
            startDateInSendingFormat = sessionModel.getStartDate();
            endDateInSendingFormat = sessionModel.getEndDate();

            startTimeInMillis = DateManager.getTimeInMillis(sessionModel.getStartDate(), AppConstants.FORMAT_DATE_SEND);
            endTimeInMillis = DateManager.getTimeInMillis(sessionModel.getEndDate(), AppConstants.FORMAT_DATE_SEND);
        }
    }

    @Override
    public void setListeners() {

        txtStartDate.setOnFocusChangeListener((view, b) ->
        {
            if (b) tfStartDate.performClick();
        });

        txtEndDate.setOnFocusChangeListener((view, b) -> {
            if (b) tfEndDate.performClick();
        });
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.tfStartDate, R.id.txtStartDate, R.id.txtEndDate, R.id.tfEndDate, R.id.btnDone})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tfStartDate:
            case R.id.txtStartDate:
                setStartDate();
                break;

            case R.id.tfEndDate:
            case R.id.txtEndDate:
                setEndDate();
                break;

            case R.id.btnDone:
                if (startDateInSendingFormat == null || endDateInSendingFormat == null) {
                    UIHelper.showShortToastInCenter(getContext(), "Please select Start and End Dates");
                } else {
                    if (startTimeInMillis > endTimeInMillis) {
                        UIHelper.showShortToastInCenter(getContext(), "End Date must be greater than Start Date.");
                    } else {
                        if (edtSessionID.getText().length() > 0 && edtSessionName.getText().length() > 0) {
                            if (isAddingNewSession) {

                                String sessionID = edtSessionID.getText().toString().replaceAll("\\s+", "");
                                SessionModel sessionModel = new SessionModel();
                                sessionModel.setActive("Y");
                                sessionModel.setDescription(edtSessionName.getText().toString().trim());
                                sessionModel.setSessionId(edtSessionID.getText().toString().trim());
                                sessionModel.setLastFileUser(getCurrentUser().getName());
                                sessionModel.setStatusId(SessionStatus.OPENED.canonicalForm());
                                sessionModel.setStartDate(startDateInSendingFormat);
                                sessionModel.setEndDate(endDateInSendingFormat);

                                addSessionCall(sessionModel);
                            } else {
                                sessionModel.setDescription(edtSessionName.getText().toString().trim());
                                sessionModel.setSessionId(edtSessionID.getText().toString().trim());
                                sessionModel.setStartDate(startDateInSendingFormat);
                                sessionModel.setEndDate(endDateInSendingFormat);

                                updateSessionCall(sessionModel);
                            }
                        } else {
                            UIHelper.showShortToastInCenter(getContext(), "Session ID and Session Name can't be empty");
                        }
                    }
                }

                break;
        }
    }

    private void setStartDate() {
        DateManager.showDatePicker(getContext(), txtStartDate, AppConstants.FORMAT_DATE_SHOW, (datePicker, i, i1, i2) -> {
            final Calendar myCalendar = Calendar.getInstance();
            myCalendar.set(Calendar.YEAR, i);
            myCalendar.set(Calendar.MONTH, i1);
            myCalendar.set(Calendar.DAY_OF_MONTH, i2);
//                    myCalendar.set(Calendar.HOUR_OF_DAY, 0);
//                    myCalendar.set(Calendar.MINUTE, 0);
//                    myCalendar.set(Calendar.SECOND, 0);
            String myFormat = AppConstants.FORMAT_DATE_SEND; // In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            startDateInSendingFormat = sdf.format(myCalendar.getTime());
            startTimeInMillis = myCalendar.getTimeInMillis();
        }, false, true, null);
    }

    private void setEndDate() {
        DateManager.showDatePicker(getContext(), txtEndDate, AppConstants.FORMAT_DATE_SHOW, (datePicker, i, i1, i2) -> {
            final Calendar myCalendar = Calendar.getInstance();
            myCalendar.set(Calendar.YEAR, i);
            myCalendar.set(Calendar.MONTH, i1);
            myCalendar.set(Calendar.DAY_OF_MONTH, i2);
            myCalendar.set(Calendar.HOUR_OF_DAY, 0);
            myCalendar.set(Calendar.MINUTE, 0);
            myCalendar.set(Calendar.SECOND, 0);
            String myFormat = AppConstants.FORMAT_DATE_SEND; // In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            endDateInSendingFormat = sdf.format(myCalendar.getTime());
            endTimeInMillis = myCalendar.getTimeInMillis();
        }, false, true, null);
    }


    private void addSessionCall(SessionModel sessionModel) {
        new WebServices(getContext(), _token, BaseURLTypes.EHS_BASE_URL, true)
                .webServiceRequestAPIAnyObject(WebServiceConstants.METHOD_ADD_SESSION, sessionModel.toString(),
                        new WebServices.IRequestWebResponseAnyObjectCallBack() {
                            @Override
                            public void requestDataResponse(WebResponse<Object> webResponse) {
                                UIHelper.showToast(getContext(), webResponse.responseMessage);
                                AppConstants.isForcedResetFragment = true;
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


    private void updateSessionCall(SessionModel sessionModel) {
        new WebServices(getContext(), _token, BaseURLTypes.EHS_BASE_URL, true)
                .webServiceRequestAPIAnyObject(WebServiceConstants.METHOD_UPDATE_SESSION, sessionModel.toString(),
                        new WebServices.IRequestWebResponseAnyObjectCallBack() {
                            @Override
                            public void requestDataResponse(WebResponse<Object> webResponse) {
                                UIHelper.showToast(getContext(), webResponse.responseMessage);
                                AppConstants.isForcedResetFragment = true;
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


}
