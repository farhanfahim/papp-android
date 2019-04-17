package com.android.papp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.DefaultItemAnimator;
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
import com.android.papp.adapters.recyleradapters.SessionsAdapter;
import com.android.papp.callbacks.OnItemClickListener;
import com.android.papp.constatnts.AppConstants;
import com.android.papp.constatnts.Constants;
import com.android.papp.fragments.abstracts.BaseFragment;
import com.android.papp.helperclasses.ui.helper.UIHelper;
import com.android.papp.managers.DateManager;
import com.android.papp.models.SpinnerModel;
import com.android.papp.widget.AnyEditTextView;
import com.android.papp.widget.AnyTextView;
import com.android.papp.widget.TitleBar;
import com.github.clans.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import ru.slybeaver.slycalendarview.SlyCalendarDialog;

public class UpcomingSessionFragment extends BaseFragment implements OnItemClickListener {


    Unbinder unbinder;

    SessionsAdapter adapter;
    ArrayList<SpinnerModel> arrData;
    @BindView(R.id.edtSearchBar)
    AnyEditTextView edtSearchBar;
    @BindView(R.id.imgSearch)
    ImageView imgSearch;
    @BindView(R.id.contSearchBar)
    LinearLayout contSearchBar;
    @BindView(R.id.txtHeading)
    AnyTextView txtHeading;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.empty_view)
    AnyTextView emptyView;
    @BindView(R.id.emptyview_container)
    LinearLayout emptyviewContainer;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.contParent)
    RelativeLayout contParent;


    public static UpcomingSessionFragment newInstance() {

        Bundle args = new Bundle();

        UpcomingSessionFragment fragment = new UpcomingSessionFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        arrData = new ArrayList<>();
        adapter = new SessionsAdapter(getContext(), arrData, this, false);


    }

    @Override
    public int getDrawerLockMode() {
        return DrawerLayout.LOCK_MODE_LOCKED_CLOSED;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_generic_recycler_view;
    }

    @Override
    public void setTitlebar(TitleBar titleBar) {
        titleBar.setVisibility(View.VISIBLE);
        titleBar.setTitle("Upcoming Sessions");
        titleBar.showResideMenu(getHomeActivity());
        titleBar.showBackButton(getBaseActivity());
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        bindRecyclerView();


        arrData.clear();
        arrData.addAll(Constants.getAddDependentsArray2());


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
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        ((DefaultItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void onItemClick(int position, Object object, View view, Object type) {

        switch (view.getId()) {
            case R.id.contParentLayout:

                getBaseActivity().addDockableFragment(SessionDetailsFragment.newInstance(), true);

                break;


            case R.id.imgDone:

                break;


            case R.id.imgCancel:
                arrData.remove(position);
                UIHelper.showToast(getContext(), "Session has been cancelled");
                adapter.notifyDataSetChanged();

                break;
        }

    }

}
