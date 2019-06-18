package com.tekrevol.papp.fragments;

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

import com.tekrevol.papp.R;
import com.tekrevol.papp.adapters.recyleradapters.SessionsAdapterDummy;
import com.tekrevol.papp.callbacks.OnItemClickListener;
import com.tekrevol.papp.constatnts.Constants;
import com.tekrevol.papp.fragments.abstracts.BaseFragment;
import com.tekrevol.papp.helperclasses.ui.helper.UIHelper;
import com.tekrevol.papp.managers.DateManager;
import com.tekrevol.papp.models.general.SpinnerModel;
import com.tekrevol.papp.widget.AnyTextView;
import com.tekrevol.papp.widget.TitleBar;
import com.jcminarro.roundkornerlayout.RoundKornerLinearLayout;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import ru.slybeaver.slycalendarview.SlyCalendarDialog;

public class ViewSessionFragment extends BaseFragment implements OnItemClickListener, SlyCalendarDialog.Callback {


    Unbinder unbinder;

    SessionsAdapterDummy adapter;
    ArrayList<SpinnerModel> arrData;


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.empty_view)
    AnyTextView emptyView;
    @BindView(R.id.emptyview_container)
    LinearLayout emptyviewContainer;
    @BindView(R.id.contParent)
    RelativeLayout contParent;
    @BindView(R.id.contChat)
    LinearLayout contChat;
    @BindView(R.id.contSessions)
    LinearLayout contSessions;
    @BindView(R.id.imgHome)
    ImageView imgHome;
    @BindView(R.id.contBottomBar)
    RelativeLayout contBottomBar;
    @BindView(R.id.imgChat)
    ImageView imgChat;
    @BindView(R.id.txtChat)
    AnyTextView txtChat;
    @BindView(R.id.imgSession)
    ImageView imgSession;
    @BindView(R.id.txtSession)
    AnyTextView txtSession;
    @BindView(R.id.txtDate)
    AnyTextView txtDate;
    @BindView(R.id.contDate)
    RoundKornerLinearLayout contDate;


    public static ViewSessionFragment newInstance() {

        Bundle args = new Bundle();

        ViewSessionFragment fragment = new ViewSessionFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        arrData = new ArrayList<>();
        adapter = new SessionsAdapterDummy(getContext(), arrData, this, false);


    }

    @Override
    public int getDrawerLockMode() {
        return DrawerLayout.LOCK_MODE_LOCKED_CLOSED;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_view_sessions;
    }

    @Override
    public void setTitlebar(TitleBar titleBar) {
        titleBar.setVisibility(View.VISIBLE);
        titleBar.setTitle("Sessions");
        titleBar.showResideMenu(getHomeActivity());
        titleBar.showBackButtonInvisible();
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        imgSession.setImageResource(R.drawable.img_sessions_selected);
        txtSession.setTextColor(getContext().getResources().getColor(R.color.colorPrimaryDark));
        bindRecyclerView();


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

                if (isMentor()) {
                    getBaseActivity().addDockableFragment(LEASessionDetailsFragment.newInstance((SessionRecievingModel) object, onCreated), true);
                } else {
                    getBaseActivity().addDockableFragment(SessionDetailsFragment.newInstance(), true);

                }

                break;


            case R.id.imgDone:


                break;


            case R.id.imgCancel:


                arrData.remove(position);
                adapter.notifyDataSetChanged();

                break;
        }

    }

    @OnClick({R.id.contChat, R.id.imgHome, R.id.contDate})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.contChat:
                getBaseActivity().popBackStack();
                getBaseActivity().addDockableFragment(ChatListsFragment.newInstance(), false);
                break;
            case R.id.contDate:
                new SlyCalendarDialog()
                        .setSingle(false)
                        .setCallback(this)
                        .show(getBaseActivity().getSupportFragmentManager(), "TAG_SLYCALENDAR");
                break;
            case R.id.imgHome:
                getBaseActivity().popBackStack();
                if (isMentor()) {
                    getBaseActivity().addDockableFragment(DashboardLEAFragment.newInstance(), false);
                } else {
                    getBaseActivity().addDockableFragment(DashboardCivilianFragment.newInstance(), false);
                }
                break;
        }
    }

    @Override
    public void onCancelled() {

    }

    @Override
    public void onDataSelected(Calendar firstDate, Calendar secondDate, int hours, int minutes) {


        if (firstDate == null) {
            UIHelper.showShortToastInCenter(getBaseActivity(), "Please select date");
            return;
        }


        if (secondDate == null) {
            secondDate = firstDate;
        }

        txtDate.setText(DateManager.getFormattedDate(firstDate.getTimeInMillis()) + " - " + DateManager.getFormattedDate(secondDate.getTimeInMillis()));
        arrData.clear();
        arrData.addAll(Constants.getAddDependentsArray2());
        adapter.notifyDataSetChanged();
    }
}
