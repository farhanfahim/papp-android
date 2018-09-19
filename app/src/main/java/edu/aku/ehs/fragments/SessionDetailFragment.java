package edu.aku.ehs.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.google.gson.reflect.TypeToken;
import com.jcminarro.roundkornerlayout.RoundKornerLinearLayout;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import edu.aku.ehs.R;
import edu.aku.ehs.adapters.recyleradapters.SessionDetailAdapter;
import edu.aku.ehs.callbacks.GenericClickableInterface;
import edu.aku.ehs.callbacks.OnItemClickListener;
import edu.aku.ehs.constatnts.AppConstants;
import edu.aku.ehs.constatnts.WebServiceConstants;
import edu.aku.ehs.enums.BaseURLTypes;
import edu.aku.ehs.enums.EmployeeSessionState;
import edu.aku.ehs.enums.SelectEmployeeActionType;
import edu.aku.ehs.fragments.abstracts.BaseFragment;
import edu.aku.ehs.fragments.abstracts.GenericDialogFragment;
import edu.aku.ehs.helperclasses.ui.helper.KeyboardHelper;
import edu.aku.ehs.helperclasses.ui.helper.UIHelper;
import edu.aku.ehs.managers.DateManager;
import edu.aku.ehs.managers.retrofit.GsonFactory;
import edu.aku.ehs.managers.retrofit.WebServices;
import edu.aku.ehs.models.SessionDetailModel;
import edu.aku.ehs.models.SessionModel;
import edu.aku.ehs.models.sending_model.SearchSendingModel;
import edu.aku.ehs.models.wrappers.WebResponse;
import edu.aku.ehs.widget.AnyEditTextView;
import edu.aku.ehs.widget.AnyTextView;
import edu.aku.ehs.widget.TitleBar;

/**
 * Created by hamza.ahmed on 7/23/2018.
 */

public class SessionDetailFragment extends BaseFragment implements OnItemClickListener {

    Unbinder unbinder;
    @BindView(R.id.imgBanner)
    ImageView imgBanner;
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
    @BindView(R.id.btnGetLabs)
    Button btnGetLabs;
    @BindView(R.id.contSelection)
    LinearLayout contSelection;
    @BindView(R.id.txtSessionName)
    AnyTextView txtSessionName;
    @BindView(R.id.cbSelectAll)
    CheckBox cbSelectAll;
    @BindView(R.id.imgClose)
    ImageView imgClose;

    private ArrayList<SessionDetailModel> arrData;
    private SessionDetailAdapter adapter;

    GenericDialogFragment genericDialogFragment = GenericDialogFragment.newInstance();
    private SessionModel sessionModel;
    public static boolean isSelectingEmployeesForSchedule = false;
    private String scheduledDateInSendingFormat;
    private String scheduledDateInShowFormat;


    public static SessionDetailFragment newInstance(SessionModel sessionModel) {

        Bundle args = new Bundle();

        SessionDetailFragment fragment = new SessionDetailFragment();
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
        return R.layout.fragment_general_recyler_view_with_option_buttons;
    }

    @Override
    public void setTitlebar(TitleBar titleBar) {
        resetTitlebar(titleBar);
    }

    private void resetTitlebar(TitleBar titleBar) {
        titleBar.resetViews();
        titleBar.setVisibility(View.VISIBLE);
        titleBar.setTitle(sessionModel.getDescription());
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

        getBaseActivity().setGenericClickableInterface(new GenericClickableInterface() {
            @Override
            public void click() {
                unFilterEnrolledData();
            }
        });

        edtSearchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                adapter.getFilter().filter(charSequence);
                if (edtSearchBar.getStringTrimmed().length() == 0) {
                    imgClose.setVisibility(View.GONE);
                } else {
                    imgClose.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

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

        arrData = new ArrayList<SessionDetailModel>();
        adapter = new SessionDetailAdapter(getBaseActivity(), arrData, this);
        isSelectingEmployeesForSchedule = false;
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
        contOptionButtons.setVisibility(View.VISIBLE);

        bindView();

        if (onCreated) {
            if (AppConstants.isForcedResetFragment) {
                AppConstants.isForcedResetFragment = false;
                SearchSendingModel searchSendingModel = new SearchSendingModel();
                searchSendingModel.setSessionID(sessionModel.getSessionId());
                getSessionEmployeesCall(searchSendingModel);
            }
        } else {
            SearchSendingModel searchSendingModel = new SearchSendingModel();
            searchSendingModel.setSessionID(sessionModel.getSessionId());
            getSessionEmployeesCall(searchSendingModel);
        }
    }

    private void unFilterEnrolledData() {
        isSelectingEmployeesForSchedule = false;
        resetTitlebar(getBaseActivity().getTitleBar());
        fab.setVisibility(View.GONE);
        contOptionButtons.setVisibility(View.VISIBLE);
        txtSessionName.setVisibility(View.INVISIBLE);
        contSearch.setVisibility(View.VISIBLE);

        for (int i = 0; i < arrData.size(); i++) {
            arrData.get(i).setSelected(false);
        }

        adapter.getFilter().filter("");
        contSelection.setVisibility(View.GONE);
    }

    private void filterOnlyEnrolledData() {
        fab.setVisibility(View.VISIBLE);
        isSelectingEmployeesForSchedule = true;
        contSearch.setVisibility(View.INVISIBLE);
        getBaseActivity().getTitleBar().setTitle("Select Employees");
        txtSessionName.setVisibility(View.VISIBLE);
        txtSessionName.setText(sessionModel.getDescription());
        contOptionButtons.setVisibility(View.GONE);
        contSelection.setVisibility(View.VISIBLE);
        adapter.getFilter().filter(EmployeeSessionState.ENROLLED.canonicalForm());
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
        SessionDetailModel sessionDetailModel = (SessionDetailModel) object;

        switch (view.getId()) {
            case R.id.btnSchedule:
                editSingleEmployeeSchedule(sessionDetailModel);
                break;

            case R.id.btnDelete:
                deleteEmployee(sessionDetailModel, position);
                break;

            case R.id.contListItem:
                if (isSelectingEmployeesForSchedule) {
                    arrData.get(position).setSelected(!arrData.get(position).isSelected());
                    adapter.notifyItemChanged(position);
                } else {
                    getBaseActivity().addDockableFragment(EmployeeAssessmentDashboardFragment.newInstance(), false);
                }

                break;
        }

    }

    private void deleteEmployee(SessionDetailModel sessionDetailModel, int position) {
        UIHelper.genericPopUp(getBaseActivity(), genericDialogFragment, "Remove", "Do you want to remove " + sessionDetailModel.getEmployeeName() + " ?", "Remove", "Cancel",
                () -> {
                    genericDialogFragment.dismiss();
                    updateSelectedEmployees(getSingleEmployeeArray(sessionDetailModel, EmployeeSessionState.CANCELLED));
                }, () -> {
                    genericDialogFragment.dismiss();
                }, false, true);
    }

    private void editSingleEmployeeSchedule(final SessionDetailModel sessionDetailModel) {

        switch (sessionDetailModel.getStatusEnum()) {
            case ENROLLED:
                UIHelper.genericPopUp(getBaseActivity(), genericDialogFragment, "Schedule", "Do you want to add Schedule for " + sessionDetailModel.getEmployeeName() + "?", "Add", "Cancel",
                        () -> {
                            genericDialogFragment.dismiss();
                            pickScheduleDate(false, sessionDetailModel, EmployeeSessionState.SCHEDULED);
                        }, genericDialogFragment::dismiss, false, true);
                break;

            case SCHEDULED:
                UIHelper.genericPopUp(getBaseActivity(), genericDialogFragment, "Schedule", "Do you want to update Schedule " + sessionDetailModel.getEmployeeName() + "?", "Update", "Cancel",
                        () -> {
                            genericDialogFragment.dismiss();
                            pickScheduleDate(false, sessionDetailModel, EmployeeSessionState.SCHEDULED);
                        }, genericDialogFragment::dismiss, false, true);

                break;

        }
    }

    private void pickScheduleDate(boolean showDialogeAfter, SessionDetailModel sessionDetailModel, EmployeeSessionState state) {
        DateManager.showDatePicker(getContext(), null, AppConstants.FORMAT_DATE_SHOW, (datePicker, i, i1, i2) -> {
            final Calendar myCalendar = Calendar.getInstance();
            myCalendar.set(Calendar.YEAR, i);
            myCalendar.set(Calendar.MONTH, i1);
            myCalendar.set(Calendar.DAY_OF_MONTH, i2);
//                    myCalendar.set(Calendar.HOUR_OF_DAY, 0);
//                    myCalendar.set(Calendar.MINUTE, 0);
//                    myCalendar.set(Calendar.SECOND, 0);

            String myFormat = AppConstants.FORMAT_DATE_SEND; // In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            scheduledDateInSendingFormat = sdf.format(myCalendar.getTime());

            String showFormat = AppConstants.FORMAT_DATE_SHOW; // In which you need put here
            SimpleDateFormat sdfShow = new SimpleDateFormat(showFormat, Locale.US);
            scheduledDateInShowFormat = sdfShow.format(myCalendar.getTime());

            if (showDialogeAfter) {
                ArrayList<SessionDetailModel> selectedArray;
                selectedArray = getSelectedEmployeesArray(state);
                String employeekeyword = "employees";
                if (selectedArray.size() <= 1) {
                    employeekeyword = "employee";
                }
                UIHelper.genericPopUp(getBaseActivity(), genericDialogFragment, "Schedule", "You are about to schedule " + selectedArray.size() + " " + employeekeyword + " for " + scheduledDateInShowFormat + ".", "Add", "Cancel",
                        () -> {
                            genericDialogFragment.dismiss();
                            updateSelectedEmployees(selectedArray);
                        }, genericDialogFragment::dismiss, false, true);

            } else {
                updateSelectedEmployees(getSingleEmployeeArray(sessionDetailModel, state));
            }


        }, false, true, null);
    }

    private ArrayList<SessionDetailModel> getSelectedEmployeesArray(EmployeeSessionState state) {
        ArrayList<SessionDetailModel> arrayList = new ArrayList<>();
        for (SessionDetailModel sessionDetailModel : arrData) {
            if (sessionDetailModel.isSelected()) {
                sessionDetailModel.setStatusID(state.canonicalForm());
                sessionDetailModel.setScheduledDTTM(scheduledDateInSendingFormat);
                sessionDetailModel.setScheduledBy(AppConstants.tempUserName);
                sessionDetailModel.setLastFileUser(AppConstants.tempUserName);
                arrayList.add(sessionDetailModel);
            }
        }

        return arrayList;
    }


    private ArrayList<SessionDetailModel> getSingleEmployeeArray(SessionDetailModel sessionDetailModel, EmployeeSessionState state) {
        ArrayList<SessionDetailModel> arrayList = new ArrayList<>();
        sessionDetailModel.setStatusID(state.canonicalForm());
        switch (state) {
            case SCHEDULED:
                sessionDetailModel.setScheduledDTTM(scheduledDateInSendingFormat);
                sessionDetailModel.setScheduledBy(AppConstants.tempUserName);
                break;
        }
        sessionDetailModel.setLastFileUser(AppConstants.tempUserName);
        arrayList.add(sessionDetailModel);

        return arrayList;
    }

    @OnClick({R.id.btnGetLabs, R.id.btnAddEmail, R.id.btnAddSchedule, R.id.btnAddEmployees, R.id.fab})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnAddEmail:
                getBaseActivity().addDockableFragment(EmailFragment.newInstance(sessionModel, arrData), false);

                break;
            case R.id.btnAddSchedule:
                filterOnlyEnrolledData();

                break;
            case R.id.btnAddEmployees:
                getBaseActivity().addDockableFragment(SearchFragment.newInstance(SelectEmployeeActionType.ADDEMPLOYEE, sessionModel), false);
                break;

            case R.id.btnGetLabs:
                UIHelper.showToast(getContext(), "Get Lab Webservice will be available in Beta version");
                break;


            case R.id.fab:
                pickScheduleDate(true, null, EmployeeSessionState.SCHEDULED);
                break;
        }
    }

    private void getSessionEmployeesCall(SearchSendingModel searchSendingModel) {
        new WebServices(getContext(), "", BaseURLTypes.EHS_BASE_URL, true)
                .webServiceRequestAPIAnyObject(WebServiceConstants.METHOD_GET_SESSION_EMPLOYEES, searchSendingModel.toString(),
                        new WebServices.IRequestWebResponseAnyObjectCallBack() {
                            @Override
                            public void requestDataResponse(WebResponse<Object> webResponse) {
                                if (webResponse.result instanceof ArrayList) {

                                    Type type = new TypeToken<ArrayList<SessionDetailModel>>() {
                                    }.getType();
                                    ArrayList<SessionDetailModel> arrayList = GsonFactory.getSimpleGson()
                                            .fromJson(GsonFactory.getSimpleGson().toJson(webResponse.result)
                                                    , type);

                                    arrData.clear();
                                    arrData.addAll(arrayList);

                                    if (isSelectingEmployeesForSchedule) {
                                        unFilterEnrolledData();
                                    } else {
                                        adapter.notifyDataSetChanged();
                                    }


                                    if (arrData.size() > 0) {
                                        emptyView.setVisibility(View.GONE);
                                    } else {
                                        emptyView.setVisibility(View.VISIBLE);
                                    }
                                }
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


    @OnClick(R.id.imgClose)
    public void onViewClicked() {
        edtSearchBar.setText("");
        KeyboardHelper.hideSoftKeyboard(getContext(), edtSearchBar);
    }


    private void updateSelectedEmployees(ArrayList<SessionDetailModel> arrData) {
        ArrayList<SessionDetailModel> sessionDetailModelArrayList = arrData;

        if (sessionDetailModelArrayList == null || sessionDetailModelArrayList.isEmpty()) {
            sessionDetailModelArrayList = new ArrayList<>();
        }

        String jsonArrayData = GsonFactory.getConfiguredGson().toJson(sessionDetailModelArrayList);

        updateEmployeesInSessionCall(jsonArrayData);
    }


    private void updateEmployeesInSessionCall(String jsonArrayData) {
        new WebServices(getContext(), "", BaseURLTypes.EHS_BASE_URL, true)
                .webServiceRequestAPIAnyObject(WebServiceConstants.METHOD_UPDATE_SESSION_EMPLOYEE, jsonArrayData,
                        new WebServices.IRequestWebResponseAnyObjectCallBack() {
                            @Override
                            public void requestDataResponse(WebResponse<Object> webResponse) {
                                UIHelper.showToast(getContext(), webResponse.responseMessage);
                                SearchSendingModel searchSendingModel = new SearchSendingModel();
                                searchSendingModel.setSessionID(sessionModel.getSessionId());
                                getSessionEmployeesCall(searchSendingModel);

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
