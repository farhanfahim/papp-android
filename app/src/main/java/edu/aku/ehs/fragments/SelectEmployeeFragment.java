package edu.aku.ehs.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
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

import com.github.clans.fab.FloatingActionButton;
import com.jcminarro.roundkornerlayout.RoundKornerLinearLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import edu.aku.ehs.R;
import edu.aku.ehs.adapters.recyleradapters.SelectEmployeesAdapter;
import edu.aku.ehs.callbacks.OnItemClickListener;
import edu.aku.ehs.enums.SelectEmployeeActionType;
import edu.aku.ehs.fragments.abstracts.BaseFragment;
import edu.aku.ehs.fragments.abstracts.GenericDialogFragment;
import edu.aku.ehs.models.EmployeeModel;
import edu.aku.ehs.models.SessionModel;
import edu.aku.ehs.widget.AnyEditTextView;
import edu.aku.ehs.widget.AnyTextView;
import edu.aku.ehs.widget.TitleBar;

/**
 * Created by hamza.ahmed on 7/23/2018.
 */

public class SelectEmployeeFragment extends BaseFragment implements OnItemClickListener {

    Unbinder unbinder;
    @BindView(R.id.imgBanner)
    ImageView imgBanner;
    @BindView(R.id.cbSelectAll)
    CheckBox cbSelectAll;
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
    @BindView(R.id.contSelection)
    LinearLayout contSelection;
    @BindView(R.id.txtSessionName)
    AnyTextView txtSessionName;

    private ArrayList<EmployeeModel> arrData;
    private SelectEmployeesAdapter adapter;
    private String searchKeyword = "";


    GenericDialogFragment genericDialogFragment = GenericDialogFragment.newInstance();
    private SelectEmployeeActionType selectEmployeeActionType;
    SessionModel sessionModel;


    public static SelectEmployeeFragment newInstance(String searchKeyword, SelectEmployeeActionType selectEmployeeActionType, SessionModel sessionModel) {
        Bundle args = new Bundle();

        SelectEmployeeFragment fragment = new SelectEmployeeFragment();
        fragment.searchKeyword = searchKeyword;
        fragment.selectEmployeeActionType = selectEmployeeActionType;
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
        return R.layout.fragment_general_recyler_view_with_option_buttons;
    }

    @Override
    public void setTitlebar(TitleBar titleBar) {
        titleBar.resetViews();
        titleBar.setVisibility(View.VISIBLE);
        titleBar.setTitle(searchKeyword);
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

        arrData = new ArrayList<EmployeeModel>();
        adapter = new SelectEmployeesAdapter(getBaseActivity(), arrData, this);
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
        contSelection.setVisibility(View.VISIBLE);
        txtSessionName.setVisibility(View.VISIBLE);
        txtSessionName.setText(sessionModel.getSessionName());
        fab.setVisibility(View.VISIBLE);
        fab.setImageResource(R.drawable.ic_done_white_18dp);

        bindView();

        bindData();
    }

    private void bindData() {
        arrData.clear();
        EmployeeModel sessionDetailModel;

        for (int i = 0; i < 8; i++) {
            if (i < 2) {
                sessionDetailModel = new EmployeeModel("Hamza Ahmed Khan");
            } else if (i >= 2 && i < 4) {
                sessionDetailModel = new EmployeeModel("Haris Maaz");
            } else if (i >= 4 && i < 6) {
                sessionDetailModel = new EmployeeModel("Aqsa Sarwar");
            } else {
                sessionDetailModel = new EmployeeModel("Mahrukh Mehmood");

            }
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
        switch (view.getId()) {
            case R.id.contListItem:
                arrData.get(position).setSelected(!arrData.get(position).isSelected());
                adapter.notifyItemChanged(position);
                break;
        }
    }


    @OnClick({R.id.contSelection, R.id.fab})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.contSelection:
                cbSelectAll.performClick();
                break;
            case R.id.fab:
                getBaseActivity().popBackStack();
                getBaseActivity().popBackStack();
                break;
        }
    }
}
