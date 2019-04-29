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
import com.android.papp.adapters.recyleradapters.CategoriesAdapter;
import com.android.papp.adapters.recyleradapters.SessionsAdapter;
import com.android.papp.callbacks.OnItemClickListener;
import com.android.papp.constatnts.Constants;
import com.android.papp.fragments.abstracts.BaseFragment;
import com.android.papp.helperclasses.ui.helper.UIHelper;
import com.android.papp.models.SpinnerModel;
import com.android.papp.widget.AnyEditTextView;
import com.android.papp.widget.AnyTextView;
import com.android.papp.widget.TitleBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class DashboardLEAFragment extends BaseFragment implements OnItemClickListener {


    Unbinder unbinder;


    @BindView(R.id.edtSearch)
    AnyEditTextView edtSearch;
    @BindView(R.id.rvSessionTypes)
    RecyclerView rvSessionTypes;
    @BindView(R.id.txtViewAllNewRequest)
    AnyTextView txtViewAllNewRequest;
    @BindView(R.id.rvNewRequest)
    RecyclerView rvNewRequest;
    @BindView(R.id.txtViewAllUpcomingSessions)
    AnyTextView txtViewAllUpcomingSessions;
    @BindView(R.id.rvUpcomingSessions)
    RecyclerView rvUpcomingSessions;
    @BindView(R.id.imgChat)
    ImageView imgChat;
    @BindView(R.id.txtChat)
    AnyTextView txtChat;
    @BindView(R.id.contChat)
    LinearLayout contChat;
    @BindView(R.id.imgSession)
    ImageView imgSession;
    @BindView(R.id.txtSession)
    AnyTextView txtSession;
    @BindView(R.id.contSessions)
    LinearLayout contSessions;
    @BindView(R.id.imgHome)
    ImageView imgHome;
    @BindView(R.id.contBottomBar)
    RelativeLayout contBottomBar;


    CategoriesAdapter categoriesAdapter;
    ArrayList<SpinnerModel> arrSessionTypes;


    SessionsAdapter adapterNewRequest;
    ArrayList<SpinnerModel> arrNewRequest;


    SessionsAdapter adapterUpcomingSession;
    ArrayList<SpinnerModel> arrUpcomingSession;


    public static DashboardLEAFragment newInstance() {

        Bundle args = new Bundle();

        DashboardLEAFragment fragment = new DashboardLEAFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        arrSessionTypes = new ArrayList<>();
        categoriesAdapter = new CategoriesAdapter(getContext(), arrSessionTypes, this);


        arrNewRequest = new ArrayList<>();
        adapterNewRequest = new SessionsAdapter(getContext(), arrNewRequest, this, true);


        arrUpcomingSession = new ArrayList<>();
        adapterUpcomingSession = new SessionsAdapter(getContext(), arrUpcomingSession, this, false);


    }

    @Override
    public int getDrawerLockMode() {
        return DrawerLayout.LOCK_MODE_LOCKED_CLOSED;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_dashboard_lea;
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

        arrSessionTypes.clear();

        arrSessionTypes.addAll(Constants.getSessionTypes());
        arrSessionTypes.get(0).setSelected(true);

        arrNewRequest.clear();
        arrNewRequest.addAll(Constants.getAddDependentsArray2());


        arrUpcomingSession.clear();
        arrUpcomingSession.addAll(Constants.getAddDependentsArray2());
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
        rvSessionTypes.setLayoutManager(mLayoutManager);
        ((DefaultItemAnimator) rvSessionTypes.getItemAnimator()).setSupportsChangeAnimations(false);
        rvSessionTypes.setAdapter(categoriesAdapter);


        RecyclerView.LayoutManager mLayoutManager2 = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rvNewRequest.setLayoutManager(mLayoutManager2);
        ((DefaultItemAnimator) rvNewRequest.getItemAnimator()).setSupportsChangeAnimations(false);
        rvNewRequest.setAdapter(adapterNewRequest);


        RecyclerView.LayoutManager mLayoutManager3 = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rvUpcomingSessions.setLayoutManager(mLayoutManager3);
        ((DefaultItemAnimator) rvUpcomingSessions.getItemAnimator()).setSupportsChangeAnimations(false);
        rvUpcomingSessions.setAdapter(adapterUpcomingSession);


    }


    @OnClick({R.id.txtViewAllUpcomingSessions, R.id.txtViewAllNewRequest, R.id.contChat, R.id.contSessions, R.id.imgHome})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.txtViewAllUpcomingSessions:
                getBaseActivity().addDockableFragment(UpcomingSessionFragment.newInstance(), true);
                break;
            case R.id.txtViewAllNewRequest:
                getBaseActivity().addDockableFragment(NewSessionRequestsFragment.newInstance(), true);
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
                for (SpinnerModel arrCategory : arrSessionTypes) {
                    arrCategory.setSelected(false);
                }

                arrSessionTypes.get(position).setSelected(true);

                categoriesAdapter.notifyDataSetChanged();
            } else if (((String) type).equalsIgnoreCase(SessionsAdapter.class.getSimpleName())) {

                switch (view.getId()) {
                    case R.id.contParentLayout:
                        getBaseActivity().addDockableFragment(LEASessionDetailsFragment.newInstance(), true);
                        break;

                    case R.id.imgDone:
                        break;

                    case R.id.imgCancel:
                        arrUpcomingSession.remove(position);
                        adapterUpcomingSession.notifyDataSetChanged();

                        break;
                }
            } else if (((String) type).equalsIgnoreCase(SessionsAdapter.class.getSimpleName() + "request")) {

                switch (view.getId()) {

                    case R.id.imgDone:
                        arrUpcomingSession.add((SpinnerModel) object);
                        arrNewRequest.remove(position);
                        UIHelper.showToast(getContext(), "Session request has been accepted");
                        adapterNewRequest.notifyDataSetChanged();
                        adapterUpcomingSession.notifyDataSetChanged();
                        break;


                    case R.id.imgCancel:
                        arrNewRequest.remove(position);
                        UIHelper.showToast(getContext(), "Session has been cancelled");

                        adapterNewRequest.notifyDataSetChanged();
                        break;
                }
            }


        }


    }
}
