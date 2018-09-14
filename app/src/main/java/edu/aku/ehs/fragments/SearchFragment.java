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
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.jcminarro.roundkornerlayout.RoundKornerLinearLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import edu.aku.ehs.R;
import edu.aku.ehs.adapters.recyleradapters.DepartmentAdapter;
import edu.aku.ehs.callbacks.OnItemClickListener;
import edu.aku.ehs.enums.SelectEmployeeActionType;
import edu.aku.ehs.fragments.abstracts.BaseFragment;
import edu.aku.ehs.fragments.abstracts.GenericDialogFragment;
import edu.aku.ehs.models.DepartmentModel;
import edu.aku.ehs.models.SessionModel;
import edu.aku.ehs.widget.AnyEditTextView;
import edu.aku.ehs.widget.AnyTextView;
import edu.aku.ehs.widget.TitleBar;

/**
 * Created by hamza.ahmed on 7/23/2018.
 */

public class SearchFragment extends BaseFragment implements OnItemClickListener {

    Unbinder unbinder;
    @BindView(R.id.empty_view)
    AnyTextView emptyView;
    @BindView(R.id.imgBanner)
    ImageView imgBanner;
    @BindView(R.id.edtSearchEmployee)
    AnyEditTextView edtSearchEmployee;
    @BindView(R.id.imgSearchEmployee)
    ImageView imgSearchEmployee;
    @BindView(R.id.contSearchByName)
    RoundKornerLinearLayout contSearchByName;
    @BindView(R.id.txtSelectDivision)
    AnyTextView txtSelectDivision;
    @BindView(R.id.contSelectDivision)
    RoundKornerLinearLayout contSelectDivision;
    @BindView(R.id.imgSearch)
    ImageView imgSearch;
    @BindView(R.id.edtSearchBar)
    AnyEditTextView edtSearchBar;
    @BindView(R.id.contSearch)
    RoundKornerLinearLayout contSearch;
    @BindView(R.id.recylerView)
    RecyclerView recylerView;
    @BindView(R.id.contParent)
    RelativeLayout contParent;
    @BindView(R.id.txtSessionName)
    AnyTextView txtSessionName;


    private ArrayList<DepartmentModel> arrData;
    private DepartmentAdapter adapter;
    private SelectEmployeeActionType selectEmployeeActionType;

    GenericDialogFragment genericDialogFragment = GenericDialogFragment.newInstance();
    SessionModel sessionModel;


    public static SearchFragment newInstance(SelectEmployeeActionType selectEmployeeActionType, SessionModel sessionModel) {
        Bundle args = new Bundle();
        SearchFragment fragment = new SearchFragment();
        fragment.selectEmployeeActionType = selectEmployeeActionType;
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
        return R.layout.fragment_search_department_employee;
    }

    @Override
    public void setTitlebar(TitleBar titleBar) {
        titleBar.resetViews();
        titleBar.setVisibility(View.VISIBLE);
        titleBar.setTitle("Search");
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

        arrData = new ArrayList<DepartmentModel>();
        adapter = new DepartmentAdapter(getBaseActivity(), arrData, this);
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
        txtSessionName.setText(sessionModel.getSessionName());

        bindView();
        bindData();
    }

    private void bindData() {
        arrData.clear();
        DepartmentModel sessionDetailModel;

        for (int i = 0; i < 8; i++) {
            if (i < 2) {
                sessionDetailModel = new DepartmentModel("IT HIS");
            } else if (i >= 2 && i < 4) {
                sessionDetailModel = new DepartmentModel("NICU");
            } else if (i >= 4 && i < 6) {
                sessionDetailModel = new DepartmentModel("Administration");
            } else {
                sessionDetailModel = new DepartmentModel("HR department");

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
        DepartmentModel model = (DepartmentModel) object;

        switch (view.getId()) {
            case R.id.contListItem:
                getBaseActivity().addDockableFragment(SelectEmployeeFragment.newInstance(model.getDeptName(), selectEmployeeActionType, sessionModel), false);
                break;
        }
    }

    @OnClick(R.id.imgSearchEmployee)
    public void onViewClicked() {
        getBaseActivity().addDockableFragment(SelectEmployeeFragment.newInstance(edtSearchEmployee.getStringTrimmed(), selectEmployeeActionType, sessionModel), false);
    }
}
