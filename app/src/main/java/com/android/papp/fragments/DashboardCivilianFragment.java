package com.android.papp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.android.papp.R;
import com.android.papp.adapters.recyleradapters.CategoriesAdapter;
import com.android.papp.adapters.recyleradapters.DependentsAdapter;
import com.android.papp.adapters.recyleradapters.LEAAdapter;
import com.android.papp.callbacks.OnItemClickListener;
import com.android.papp.constatnts.Constants;
import com.android.papp.enums.LeaType;
import com.android.papp.fragments.abstracts.BaseFragment;
import com.android.papp.models.SpinnerModel;
import com.android.papp.widget.AnyEditTextView;
import com.android.papp.widget.AnyTextView;
import com.android.papp.widget.TitleBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class DashboardCivilianFragment extends BaseFragment implements OnItemClickListener {


    @BindView(R.id.edtSearch)
    AnyEditTextView edtSearch;
    @BindView(R.id.imgFilter)
    ImageView imgFilter;
    @BindView(R.id.rvCategories)
    RecyclerView rvCategories;
    @BindView(R.id.txtViewAllTopLEA)
    AnyTextView txtViewAllTopLEA;
    @BindView(R.id.rvTopLEA)
    RecyclerView rvTopLEA;
    @BindView(R.id.txtViewAllMyLEA)
    AnyTextView txtViewAllMyLEA;
    @BindView(R.id.rvMyLEA)
    RecyclerView rvMyLEA;
    @BindView(R.id.txtViewAllDependents)
    AnyTextView txtViewAllDependents;
    @BindView(R.id.rvDependents)
    RecyclerView rvDependents;
    @BindView(R.id.contChat)
    LinearLayout contChat;
    @BindView(R.id.contSessions)
    LinearLayout contSessions;
    @BindView(R.id.imgHome)
    ImageView imgHome;
    @BindView(R.id.contBottomBar)
    RelativeLayout contBottomBar;
    Unbinder unbinder;


    CategoriesAdapter categoriesAdapter;
    ArrayList<SpinnerModel> arrCategories;

    DependentsAdapter dependentsAdapter;
    ArrayList<SpinnerModel> arrDependents;

    LEAAdapter myLEAAdapter;
    ArrayList<SpinnerModel> arrMyLEA;


    LEAAdapter topLEAAdapter;
    ArrayList<SpinnerModel> arrTopLEA;



    public static DashboardCivilianFragment newInstance() {

        Bundle args = new Bundle();

        DashboardCivilianFragment fragment = new DashboardCivilianFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        arrCategories = new ArrayList<>();
        categoriesAdapter = new CategoriesAdapter(getContext(), arrCategories, this);


        arrDependents = new ArrayList<>();
        dependentsAdapter = new DependentsAdapter(getContext(), arrDependents, this);


        arrMyLEA = new ArrayList<>();
        myLEAAdapter = new LEAAdapter(getContext(), arrMyLEA, this);


        arrTopLEA = new ArrayList<>();
        topLEAAdapter = new LEAAdapter(getContext(), arrTopLEA, this);
    }

    @Override
    public int getDrawerLockMode() {
        return DrawerLayout.LOCK_MODE_LOCKED_CLOSED;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_dashboard_civilian;
    }

    @Override
    public void setTitlebar(TitleBar titleBar) {
        titleBar.setVisibility(View.VISIBLE);
        titleBar.setTitle("Dashboard");
        titleBar.showResideMenu(getHomeActivity());
        titleBar.showBackButtonInvisible();
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        bindRecyclerView();

        arrCategories.clear();

        arrCategories.addAll(Constants.getCategories());
        arrCategories.get(0).setSelected(true);

        arrDependents.clear();
        arrDependents.addAll(Constants.getAddDependentsArray2());

        arrMyLEA.clear();
        arrMyLEA.addAll(Constants.getAddDependentsArray2());


        arrTopLEA.clear();
        arrTopLEA.addAll(Constants.getTopLEA());
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


    private void bindRecyclerView() {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rvCategories.setLayoutManager(mLayoutManager);
        ((DefaultItemAnimator) rvCategories.getItemAnimator()).setSupportsChangeAnimations(false);
        rvCategories.setAdapter(categoriesAdapter);


        GridLayoutManager mLayoutManager2 = new GridLayoutManager(getContext(), 4);
        rvDependents.setLayoutManager(mLayoutManager2);
        ((DefaultItemAnimator) rvDependents.getItemAnimator()).setSupportsChangeAnimations(false);
        rvDependents.setAdapter(dependentsAdapter);


        GridLayoutManager mLayoutManager3 = new GridLayoutManager(getContext(), 4);
        rvMyLEA.setLayoutManager(mLayoutManager3);
        ((DefaultItemAnimator) rvDependents.getItemAnimator()).setSupportsChangeAnimations(false);
        rvMyLEA.setAdapter(myLEAAdapter);


        GridLayoutManager mLayoutManager4 = new GridLayoutManager(getContext(), 3);
        rvTopLEA.setLayoutManager(mLayoutManager4);
        ((DefaultItemAnimator) rvTopLEA.getItemAnimator()).setSupportsChangeAnimations(false);
        rvTopLEA.setAdapter(topLEAAdapter);
    }



    @OnClick({R.id.imgFilter, R.id.txtViewAllTopLEA, R.id.txtViewAllMyLEA, R.id.txtViewAllDependents, R.id.contChat, R.id.contSessions, R.id.imgHome})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgFilter:
                break;
            case R.id.txtViewAllTopLEA:
                getBaseActivity().addDockableFragment(ViewLEAListFragment.newInstance(LeaType.TOPLEA), false);
                break;
            case R.id.txtViewAllMyLEA:
                getBaseActivity().addDockableFragment(ViewLEAListFragment.newInstance(LeaType.MYLEA), false);
                break;
            case R.id.txtViewAllDependents:
                getBaseActivity().addDockableFragment(ViewAllDependentsFragment.newInstance(), false);
                break;
            case R.id.contChat:
                getBaseActivity().popBackStack();
                getBaseActivity().addDockableFragment(ChatListsFragment.newInstance(), false);
                break;
            case R.id.contSessions:
                getBaseActivity().popBackStack();
                getBaseActivity().addDockableFragment(ViewSessionFragment.newInstance(), false);
                break;
            case R.id.imgHome:
                break;
        }
    }

    @Override
    public void onItemClick(int position, Object object, View view, Object type) {
        if (type instanceof String) {
            if (((String) type).equalsIgnoreCase(CategoriesAdapter.class.getSimpleName())) {
                for (SpinnerModel arrCategory : arrCategories) {
                    arrCategory.setSelected(false);
                }

                arrCategories.get(position).setSelected(true);

                categoriesAdapter.notifyDataSetChanged();
            }

        }


    }
}
