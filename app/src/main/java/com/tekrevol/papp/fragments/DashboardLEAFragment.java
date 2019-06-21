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

import com.google.gson.reflect.TypeToken;
import com.tekrevol.papp.R;
import com.tekrevol.papp.adapters.recyleradapters.CategoriesAdapter;
import com.tekrevol.papp.adapters.recyleradapters.SessionsAdapter;
import com.tekrevol.papp.callbacks.OnItemClickListener;
import com.tekrevol.papp.constatnts.AppConstants;
import com.tekrevol.papp.constatnts.Constants;
import com.tekrevol.papp.constatnts.WebServiceConstants;
import com.tekrevol.papp.fragments.abstracts.BaseFragment;
import com.tekrevol.papp.helperclasses.ui.helper.UIHelper;
import com.tekrevol.papp.managers.retrofit.GsonFactory;
import com.tekrevol.papp.managers.retrofit.WebServices;
import com.tekrevol.papp.models.general.SpinnerModel;
import com.tekrevol.papp.models.receiving_model.SessionRecievingModel;
import com.tekrevol.papp.models.wrappers.WebResponse;
import com.tekrevol.papp.widget.AnyEditTextView;
import com.tekrevol.papp.widget.AnyTextView;
import com.tekrevol.papp.widget.TitleBar;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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


    CategoriesAdapter sessionTypesAdapter;
    ArrayList<SpinnerModel> arrSessionTypes;


    SessionsAdapter adapterNewRequest;
    ArrayList<SessionRecievingModel> arrNewRequest;


    SessionsAdapter adapterUpcomingSession;
    ArrayList<SessionRecievingModel> arrUpcomingSession;
    @BindView(R.id.contNewRequest)
    LinearLayout contNewRequest;
    @BindView(R.id.contUpcomingSessions)
    LinearLayout contUpcomingSessions;
    @BindView(R.id.txtNewRequest)
    AnyTextView txtNewRequest;
    @BindView(R.id.txtUpcomingSession)
    AnyTextView txtUpcomingSession;
    private int selectedSessionType;


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
        sessionTypesAdapter = new CategoriesAdapter(getContext(), arrSessionTypes, this);


        arrNewRequest = new ArrayList<>();
        adapterNewRequest = new SessionsAdapter(getContext(), arrNewRequest, this, isMentor());


        arrUpcomingSession = new ArrayList<>();
        adapterUpcomingSession = new SessionsAdapter(getContext(), arrUpcomingSession, this, isMentor());
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

        if (arrSessionTypes.isEmpty()) {
            arrSessionTypes.clear();
            arrSessionTypes.addAll(Constants.getSessionTypes());
            arrSessionTypes.get(0).setSelected(true);
            sessionTypesAdapter.notifyDataSetChanged();
        }

        getSessions(4, false);
        getSessions(4, true);

        if (onCreated && !getBaseActivity().isReloadFragmentOnBack) {
            return;
        }
        getBaseActivity().isReloadFragmentOnBack = false;

        getCategoriesList();


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
        rvSessionTypes.setAdapter(sessionTypesAdapter);


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
                getSessions(0, true);
                break;
            case R.id.txtViewAllNewRequest:
                getSessions(0, false);
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

                switch (position) {
                    case 0:
                        selectedSessionType = 0;
                        break;

                    case 1:
                        selectedSessionType = AppConstants.SESSION_TYPE_AUDIO;
                        break;

                    case 2:
                        selectedSessionType = AppConstants.SESSION_TYPE_VIDEO;
                        break;

                    case 3:
                        selectedSessionType = AppConstants.SESSION_TYPE_ONE_ON_ONE;
                        break;
                }

                getSessions(4, false);
                getSessions(4, true);

                sessionTypesAdapter.notifyDataSetChanged();

            } else if (((String) type).equalsIgnoreCase(SessionsAdapter.class.getSimpleName())) {

                switch (view.getId()) {
                    case R.id.contParentLayout:
                        getBaseActivity().addDockableFragment(MentorSessionDetailsFragment.newInstance((SessionRecievingModel) object), true);
                        break;

                    case R.id.imgDone:
                        acceptSessionAPI(((SessionRecievingModel) object).getId());
                        break;


                    case R.id.imgCancel:
                        declineSessionAPI(((SessionRecievingModel) object).getId());
                        break;
                }
            }


        }


    }


    public void getCategoriesList() {
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put(WebServiceConstants.Q_PARAM_LIMIT, 0);
        queryMap.put(WebServiceConstants.Q_PARAM_OFFSET, 0);


        getBaseWebService().getAPIAnyObject(WebServiceConstants.PATH_GET_DEPARTMENTS, queryMap, new WebServices.IRequestWebResponseAnyObjectCallBack() {
            @Override
            public void requestDataResponse(WebResponse<Object> webResponse) {
                Type type = new TypeToken<ArrayList<SpinnerModel>>() {
                }.getType();
                ArrayList<SpinnerModel> arrayList = GsonFactory.getSimpleGson()
                        .fromJson(GsonFactory.getSimpleGson().toJson(webResponse.result)
                                , type);


                ArrayList<SpinnerModel> arrCategories = new ArrayList<>();
                arrCategories.clear();
                arrCategories.add(new SpinnerModel("All", 0));
                arrCategories.addAll(arrayList);
                arrCategories.get(0).setSelected(true);


                getHomeActivity().sparseArrayDepartments.clear();

                for (SpinnerModel model : arrCategories) {
                    getHomeActivity().sparseArrayDepartments.put(model.getId(), model.getText());
                }


            }

            @Override
            public void onError(Object object) {

            }
        });
    }


    public void getSessions(int limit, boolean isAcceptedUpcomingSessions) {

        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put(WebServiceConstants.Q_PARAM_LIMIT, limit);


        if (selectedSessionType != 0) {
            queryMap.put(WebServiceConstants.Q_PARAM_TYPE_FILTER, selectedSessionType);
        }

        if (isAcceptedUpcomingSessions) {
            queryMap.put(WebServiceConstants.Q_PARAM_UPCOMING_SESSION_REQUEST, "yes");
        } else {
            queryMap.put(WebServiceConstants.Q_PARAM_CURRENT_MENTOR, "yes");
        }


        getBaseWebService().getAPIAnyObject(WebServiceConstants.PATH_SESSIONS, queryMap, new WebServices.IRequestWebResponseAnyObjectCallBack() {
            @Override
            public void requestDataResponse(WebResponse<Object> webResponse) {
                Type type = new TypeToken<ArrayList<SessionRecievingModel>>() {
                }.getType();
                ArrayList<SessionRecievingModel> arrayList = GsonFactory.getSimpleGson()
                        .fromJson(GsonFactory.getSimpleGson().toJson(webResponse.result)
                                , type);


                // View all pressed if limit is 0

                if (limit == 0) {
                    if (isAcceptedUpcomingSessions) {
                        getBaseActivity().addDockableFragment(UpcomingSessionFragment.newInstance(arrayList), true);
                    } else {
                        getBaseActivity().addDockableFragment(NewSessionRequestsFragment.newInstance(arrayList), true);
                    }
                    return;
                }


                if (isAcceptedUpcomingSessions) {
                    arrUpcomingSession.clear();
                    arrUpcomingSession.addAll(arrayList);
                    if (arrUpcomingSession.isEmpty()) {
                        txtUpcomingSession.setVisibility(View.VISIBLE);
                        txtViewAllUpcomingSessions.setVisibility(View.GONE);
                    } else {
                        txtUpcomingSession.setVisibility(View.GONE);
                        txtViewAllNewRequest.setVisibility(View.VISIBLE);
                        txtViewAllUpcomingSessions.setVisibility(View.VISIBLE);
                    }

                } else {
                    arrNewRequest.clear();
                    arrNewRequest.addAll(arrayList);
                    if (arrNewRequest.isEmpty()) {
                        txtNewRequest.setVisibility(View.VISIBLE);
                        txtViewAllNewRequest.setVisibility(View.GONE);
                    } else {
                        txtNewRequest.setVisibility(View.GONE);
                        txtViewAllUpcomingSessions.setVisibility(View.VISIBLE);
                    }
                }


                adapterUpcomingSession.notifyDataSetChanged();
                adapterNewRequest.notifyDataSetChanged();

            }

            @Override
            public void onError(Object object) {

            }
        });
    }


    private void acceptSessionAPI(int id) {
        getBaseWebService().postAPIAnyObject(WebServiceConstants.PATH_ACCEPT_SESSION + id, "", new WebServices.IRequestWebResponseAnyObjectCallBack() {
            @Override
            public void requestDataResponse(WebResponse<Object> webResponse) {
                UIHelper.showShortToastInCenter(getContext(), webResponse.message);
                getSessions(4, false);
                getSessions(4, true);

            }

            @Override
            public void onError(Object object) {

            }
        });
    }

    private void declineSessionAPI(int id) {
        getBaseWebService().postAPIAnyObject(WebServiceConstants.PATH_DECLINE_SESSION + id, "", new WebServices.IRequestWebResponseAnyObjectCallBack() {
            @Override
            public void requestDataResponse(WebResponse<Object> webResponse) {
                UIHelper.showShortToastInCenter(getContext(), webResponse.message);
                getSessions(4, false);
                getSessions(4, true);

            }

            @Override
            public void onError(Object object) {

            }
        });
    }


}
