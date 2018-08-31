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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.github.clans.fab.FloatingActionButton;
import com.jcminarro.roundkornerlayout.RoundKornerLinearLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import edu.aku.ehs.R;
import edu.aku.ehs.adapters.recyleradapters.SessionDetailAdapter;
import edu.aku.ehs.callbacks.GenericClickableInterface;
import edu.aku.ehs.callbacks.OnItemClickListener;
import edu.aku.ehs.enums.EmployeeSessionState;
import edu.aku.ehs.enums.SelectEmployeeActionType;
import edu.aku.ehs.fragments.abstracts.BaseFragment;
import edu.aku.ehs.fragments.abstracts.GenericDialogFragment;
import edu.aku.ehs.helperclasses.ui.helper.UIHelper;
import edu.aku.ehs.managers.DateManager;
import edu.aku.ehs.models.SessionDetailModel;
import edu.aku.ehs.models.SessionModel;
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

    private ArrayList<SessionDetailModel> arrData;
    private SessionDetailAdapter adapter;

    GenericDialogFragment genericDialogFragment = GenericDialogFragment.newInstance();
    private SessionModel sessionModel;
    public static boolean isSelectingEmployeesForSchedule = false;


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
        return R.layout.fragment_general_recyler_view;
    }

    @Override
    public void setTitlebar(TitleBar titleBar) {
        resetTitlebar(titleBar);
    }

    private void resetTitlebar(TitleBar titleBar) {
        titleBar.resetViews();
        titleBar.setVisibility(View.VISIBLE);
        titleBar.setTitle(sessionModel.getSessionName());
        titleBar.showBackButton(getBaseActivity());
        titleBar.showHome(getBaseActivity());
    }

    @Override
    public void setListeners() {

        getBaseActivity().setGenericClickableInterface(new GenericClickableInterface() {
            @Override
            public void click() {
                onBackPressed();
            }
        });

    }

    private void onBackPressed() {
        isSelectingEmployeesForSchedule = false;
        resetTitlebar(getBaseActivity().getTitleBar());
        fab.setVisibility(View.GONE);
        contOptionButtons.setVisibility(View.VISIBLE);
        bindData();
        contSelection.setVisibility(View.GONE);
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

        bindData();
    }

    private void bindData() {
        arrData.clear();
        SessionDetailModel sessionDetailModel;

        for (int i = 0; i < 8; i++) {
            if (i < 2) {
                sessionDetailModel = new SessionDetailModel("Hamza Ahmed Khan", EmployeeSessionState.ENROLLED);
            } else if (i >= 2 && i < 4) {
                sessionDetailModel = new SessionDetailModel("Haris Maaz ", EmployeeSessionState.SCHEDULED);
            } else if (i >= 4 && i < 6) {
                sessionDetailModel = new SessionDetailModel("Aqsa Sarwar ", EmployeeSessionState.INPROGRESS);
            } else {
                sessionDetailModel = new SessionDetailModel("Mahrukh Mehmood ", EmployeeSessionState.CLOSED);

            }
            arrData.add(sessionDetailModel);
        }
        adapter.notifyDataSetChanged();
    }

    private void bindOnlyEnrolledData() {
        arrData.clear();
        SessionDetailModel sessionDetailModel;

        for (int i = 0; i < 4; i++) {
            sessionDetailModel = new SessionDetailModel("Hamza Ahmed Khan", EmployeeSessionState.ENROLLED);
            sessionDetailModel.setInSelectionMode(true);
            arrData.add(sessionDetailModel);
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

        switch (view.getId()) {
            case R.id.btnSchedule:
                editSchedule(sessionDetailModel);
                break;

            case R.id.btnDelete:
                deleteEmployee(sessionDetailModel, position);
                break;

            case R.id.contListItem:

                if (isSelectingEmployeesForSchedule) {
                    arrData.get(position).setSelected(!arrData.get(position).isSelected());
                    adapter.notifyItemChanged(position);
                } else {
                    getBaseActivity().addDockableFragment(EmployeeAssessmentListFragment.newInstance(), false);
                }

                break;
        }

    }

    private void deleteEmployee(SessionDetailModel sessionDetailModel, int position) {
        UIHelper.genericPopUp(getBaseActivity(), genericDialogFragment, "Remove", "Do you want to remove " + sessionDetailModel.getEmployeeName() + " ?", "Remove", "Cancel",
                () -> {
                    genericDialogFragment.dismiss();
                    arrData.remove(position);
                    adapter.notifyDataSetChanged();
                }, () -> {
                    genericDialogFragment.dismiss();
                }, false, true);
    }

    private void editSchedule(SessionDetailModel sessionDetailModel) {


        switch (sessionDetailModel.getStatus()) {
            case ENROLLED:
                UIHelper.genericPopUp(getBaseActivity(), genericDialogFragment, "Schedule", "Do you want to add Schedule?", "Add", "Cancel",
                        () -> {
                            genericDialogFragment.dismiss();
                            DateManager.showDatePicker(getContext(), date -> UIHelper.showToast(getContext(), date), false, true);
                        }, () -> {
                            genericDialogFragment.dismiss();
                        }, false, true);
                break;
            case SCHEDULED:
                UIHelper.genericPopUp(getBaseActivity(), genericDialogFragment, "Schedule", "Do you want to update Schedule?", "Update", "Cancel",
                        () -> {
                            genericDialogFragment.dismiss();
                            DateManager.showDatePicker(getContext(), date -> UIHelper.showToast(getContext(), date), false, true);
                        }, () -> {
                            genericDialogFragment.dismiss();
                        }, false, true);

                break;
            case INPROGRESS:
                UIHelper.showIOSPopup(getContext(), "Cancel Schedule", "Do you really want to cancel this schedule",
                        "Yes", "No", dialog -> {
                        }, dialog -> {
                        });
                break;
        }
    }

    @OnClick({R.id.btnGetLabs, R.id.btnAddEmail, R.id.btnAddSchedule, R.id.btnAddEmployees, R.id.fab})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnAddEmail:
                getBaseActivity().addDockableFragment(EmailFragment.newInstance(), false);

                break;
            case R.id.btnAddSchedule:
                fab.setVisibility(View.VISIBLE);
                isSelectingEmployeesForSchedule = true;
                getBaseActivity().getTitleBar().setTitle("Select Employees");
                contOptionButtons.setVisibility(View.GONE);
                bindOnlyEnrolledData();
                contSelection.setVisibility(View.VISIBLE);


                break;
            case R.id.btnAddEmployees:
                getBaseActivity().addDockableFragment(SearchFragment.newInstance(SelectEmployeeActionType.ADDEMPLOYEE), false);
                break;

            case R.id.btnGetLabs:
                getBaseActivity().addDockableFragment(SearchFragment.newInstance(SelectEmployeeActionType.ADDEMPLOYEE), false);
                break;


            case R.id.fab:

                UIHelper.genericPopUp(getBaseActivity(), genericDialogFragment, "Schedule", "Do you want to Add Schedule?", "Add", "Cancel",
                        () -> {
                            genericDialogFragment.dismiss();
                            DateManager.showDatePicker(getContext(), date -> onBackPressed(), false, true);
                        }, () -> {
                            genericDialogFragment.dismiss();
                        }, false, true);


                break;
        }
    }
}
