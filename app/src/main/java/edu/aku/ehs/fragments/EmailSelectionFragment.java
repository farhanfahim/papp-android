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
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import edu.aku.ehs.R;
import edu.aku.ehs.adapters.recyleradapters.EmailSelectionAdapter;
import edu.aku.ehs.callbacks.GenericClickableInterface;
import edu.aku.ehs.callbacks.OnItemClickListener;
import edu.aku.ehs.constatnts.AppConstants;
import edu.aku.ehs.enums.EmailSortType;
import edu.aku.ehs.fragments.abstracts.BaseFragment;
import edu.aku.ehs.helperclasses.ui.helper.KeyboardHelper;
import edu.aku.ehs.managers.retrofit.GsonFactory;
import edu.aku.ehs.models.SessionDetailModel;
import edu.aku.ehs.models.SessionModel;
import edu.aku.ehs.models.sending_model.EmployeeSendingModel;
import edu.aku.ehs.widget.AnyEditTextView;
import edu.aku.ehs.widget.AnyTextView;
import edu.aku.ehs.widget.TitleBar;

/**
 * Created by hamza.ahmed on 7/23/2018.
 */

public class EmailSelectionFragment extends BaseFragment implements OnItemClickListener {

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
    @BindView(R.id.contEmailOptions)
    LinearLayout contEmailOptions;

    private ArrayList<SessionDetailModel> arrData = new ArrayList<>();
    private EmailSelectionAdapter adapter;
    private SessionModel sessionModel;
    private EmailSortType emailSortType;
    private String jsonData = "";


    public static EmailSelectionFragment newInstance(SessionModel sessionModel, String jsonData, EmailSortType emailSortType) {

        Bundle args = new Bundle();

        EmailSelectionFragment fragment = new EmailSelectionFragment();
        fragment.sessionModel = sessionModel;
        fragment.jsonData = jsonData;
        fragment.emailSortType = emailSortType;
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
        titleBar.setTitle("Select Employees For Email");
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
        adapter = new EmailSelectionAdapter(getBaseActivity(), arrData, this);
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



        Type type = new TypeToken<ArrayList<SessionDetailModel>>() {
        }.getType();
        ArrayList<SessionDetailModel> arrayList = GsonFactory.getSimpleGson()
                .fromJson(jsonData
                        , type);

        arrData.clear();
        arrData.addAll(arrayList);

        contEmailOptions.setVisibility(View.VISIBLE);
        contSearch.setVisibility(View.GONE);
        imgBanner.setVisibility(View.GONE);
        contOptionButtons.setVisibility(View.GONE);
        contSelection.setVisibility(View.VISIBLE);
        txtSessionName.setVisibility(View.GONE);
//        txtSessionName.setText(sessionModel.getDescription());

        bindView();


        EmailSelectionPagerFragment parentFragment = (EmailSelectionPagerFragment) getParentFragment();

        switch (emailSortType) {
            case ALL:
                if (arrData.size() > 0) {
                    parentFragment.txtCountAll.setVisibility(View.VISIBLE);
                    parentFragment.txtCountAll.setText(arrData.size() + "");
                } else {
                    parentFragment.txtCountAll.setVisibility(View.GONE);
                    contSelection.setVisibility(View.GONE);
                    emptyView.setVisibility(View.VISIBLE);
                }
                break;
            case INCOMPLETE:
                arrData.removeIf(p -> p.getisLabResultDone());

                if (arrData.size() > 0) {
                    parentFragment.txtCountInComplete.setVisibility(View.VISIBLE);
                    parentFragment.txtCountInComplete.setText(arrData.size() + "");
                } else {
                    parentFragment.txtCountInComplete.setVisibility(View.GONE);
                    contSelection.setVisibility(View.GONE);
                    emptyView.setVisibility(View.VISIBLE);
                }

                break;
            case COMPLETED:
//                arrData.removeIf(p -> p.getStatusEnum() != EmployeeSessionState.COMPLETED);

                arrData.removeIf(p -> !p.getisLabResultDone());

                if (arrData.size() > 0) {
                    parentFragment.txtCountComplete.setVisibility(View.VISIBLE);
                    parentFragment.txtCountComplete.setText(arrData.size() + "");
                } else {
                    parentFragment.txtCountComplete.setVisibility(View.GONE);
                    contSelection.setVisibility(View.GONE);
                    showEmptyView(AppConstants.NO_RECORD_FOUND);
                }

                break;
        }


        adapter.notifyDataSetChanged();

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
        EmployeeSendingModel sendingModel = new EmployeeSendingModel();
        sendingModel.setSessionID(sessionModel.getSessionId());
        sendingModel.setEmployeeNo(sessionDetailModel.getEmployeeNo());
        switch (view.getId()) {

            case R.id.contListItem:
                adapter.getFilteredData().get(position).setSelected(!adapter.getFilteredData().get(position).isSelected());
                adapter.notifyItemChanged(position);
                break;
        }

    }


//    private ArrayList<SessionDetailModel> getSelectedEmployeesArray(EmployeeSessionState state) {
//        ArrayList<SessionDetailModel> arrayList = new ArrayList<>();
//        for (SessionDetailModel sessionDetailModel : arrData) {
//            if (sessionDetailModel.isSelected()) {
//                sessionDetailModel.setStatusID(state.canonicalForm());
//                sessionDetailModel.setScheduledDTTM(scheduledDateInSendingFormat);
//                sessionDetailModel.setScheduledBy(getCurrentUser().getName());
//                sessionDetailModel.setLastFileUser(getCurrentUser().getName());
//                arrayList.add(sessionDetailModel);
//            }
//        }
//
//        return arrayList;
//    }


//    private ArrayList<SessionDetailModel> getSingleEmployeeArray(SessionDetailModel sessionDetailModel, EmployeeSessionState state) {
//        ArrayList<SessionDetailModel> arrayList = new ArrayList<>();
//        sessionDetailModel.setStatusID(state.canonicalForm());
//        switch (state) {
//            case SCHEDULED:
//                sessionDetailModel.setScheduledDTTM(scheduledDateInSendingFormat);
//                sessionDetailModel.setScheduledBy(getCurrentUser().getName());
//                break;
//        }
//        sessionDetailModel.setLastFileUser(getCurrentUser().getName());
//        arrayList.add(sessionDetailModel);
//
//        return arrayList;
//    }


    private void showEmptyView(String text) {
        emptyView.setText(text);
        emptyView.setVisibility(View.VISIBLE);
    }


    @OnClick(R.id.imgClose)
    public void onViewClicked() {
        edtSearchBar.setText("");
        KeyboardHelper.hideSoftKeyboard(getContext(), edtSearchBar);
    }


}
