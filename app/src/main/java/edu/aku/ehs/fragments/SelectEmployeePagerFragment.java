package edu.aku.ehs.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import edu.aku.ehs.R;
import edu.aku.ehs.adapters.SelectEmployeePagerAdapter;
import edu.aku.ehs.callbacks.OnItemClickListener;
import edu.aku.ehs.enums.SearchByType;
import edu.aku.ehs.enums.SelectEmployeeActionType;
import edu.aku.ehs.fragments.abstracts.BaseFragment;
import edu.aku.ehs.models.SessionModel;
import edu.aku.ehs.models.peoplesoft.department.DEPT;
import edu.aku.ehs.models.peoplesoft.employee.EMPLOYEE;
import edu.aku.ehs.widget.AnyTextView;
import edu.aku.ehs.widget.CustomViewPager;
import edu.aku.ehs.widget.TitleBar;

public class SelectEmployeePagerFragment extends BaseFragment implements OnItemClickListener {

    Unbinder unbinder;

    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.viewpager)
    CustomViewPager viewpager;
    @BindView(R.id.imgBanner)
    ImageView imgBanner;
    @BindView(R.id.txtSessionName)
    AnyTextView txtSessionName;
    @BindView(R.id.txtCountFT)
    public AnyTextView txtCountFT;
    @BindView(R.id.txtCountPT)
    public AnyTextView txtCountPT;


    private SelectEmployeePagerAdapter adapter;

    private ArrayList<EMPLOYEE> arrData;
    private String searchKeyword = "";


    private SelectEmployeeActionType selectEmployeeActionType;
    SessionModel sessionModel;
    private SearchByType searchByType;
    private DEPT deptModel;
    private DEPT division;

    public static SelectEmployeePagerFragment newInstance(String searchKeyword, SelectEmployeeActionType selectEmployeeActionType, SearchByType searchByType, SessionModel sessionModel, DEPT dept, DEPT division) {
        Bundle args = new Bundle();

        SelectEmployeePagerFragment fragment = new SelectEmployeePagerFragment();
        fragment.searchKeyword = searchKeyword;
        fragment.selectEmployeeActionType = selectEmployeeActionType;
        fragment.searchByType = searchByType;
        fragment.sessionModel = sessionModel;
        fragment.deptModel = dept;
        fragment.division = division;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getDrawerLockMode() {
        return DrawerLayout.LOCK_MODE_LOCKED_CLOSED;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_tablayout;
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

        txtSessionName.setText(sessionModel.getDescription());

        setViewPagerAdapter();

    }

    private void setViewPagerAdapter() {
        adapter = new SelectEmployeePagerAdapter(getChildFragmentManager(), searchKeyword, selectEmployeeActionType, searchByType, sessionModel, deptModel, division);
        viewpager.setAdapter(adapter);
        viewpager.setPagingEnabled(true);
        tabs.setupWithViewPager(viewpager);

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
