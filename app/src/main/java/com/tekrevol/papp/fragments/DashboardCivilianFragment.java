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

import com.android.papp.R;
import com.google.gson.reflect.TypeToken;
import com.tekrevol.papp.adapters.recyleradapters.CategoriesAdapter;
import com.tekrevol.papp.adapters.recyleradapters.DependentsAdapter;
import com.tekrevol.papp.adapters.recyleradapters.MyMentorAdapter;
import com.tekrevol.papp.adapters.recyleradapters.TopMentorAdapter;
import com.tekrevol.papp.callbacks.OnItemClickListener;
import com.tekrevol.papp.constatnts.AppConstants;
import com.tekrevol.papp.constatnts.WebServiceConstants;
import com.tekrevol.papp.enums.MentorType;
import com.tekrevol.papp.fragments.abstracts.BaseFragment;
import com.tekrevol.papp.helperclasses.ui.helper.UIHelper;
import com.tekrevol.papp.managers.retrofit.GsonFactory;
import com.tekrevol.papp.managers.retrofit.WebServices;
import com.tekrevol.papp.models.SpinnerModel;
import com.tekrevol.papp.models.receiving_model.UserModel;
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

public class DashboardCivilianFragment extends BaseFragment implements OnItemClickListener {


    @BindView(R.id.edtSearch)
    AnyEditTextView edtSearch;
    @BindView(R.id.imgFilter)
    ImageView imgFilter;
    @BindView(R.id.rvCategories)
    RecyclerView rvCategories;
    @BindView(R.id.txtViewAllTopMentor)
    AnyTextView txtViewAllTopLEA;
    @BindView(R.id.rvTopLEA)
    RecyclerView rvTopLEA;
    @BindView(R.id.txtViewAllMyMentor)
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


    MyMentorAdapter myMentorAdapter;
    ArrayList<UserModel> arrMyMentor;


    TopMentorAdapter topMentorAdapter;
    ArrayList<UserModel> arrTopMentor;

    SpinnerModel selectedCategory;


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


        dependentsAdapter = new DependentsAdapter(getContext(), getCurrentUser().getDependants(), this);


        arrMyMentor = new ArrayList<>();
        myMentorAdapter = new MyMentorAdapter(getContext(), arrMyMentor, this);


        arrTopMentor = new ArrayList<>();
        topMentorAdapter = new TopMentorAdapter(getContext(), arrTopMentor, this);
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


        if (onCreated) {
            return;
        }

        bindData();

    }


    public void bindData() {


        dependentsAdapter.notifyDataSetChanged();


        getCategoriesList();
        getTopMentors(3);
        getMyMentors(4);
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


        RecyclerView.LayoutManager mLayoutManager2 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rvDependents.setLayoutManager(mLayoutManager2);
        ((DefaultItemAnimator) rvDependents.getItemAnimator()).setSupportsChangeAnimations(false);
        rvDependents.setAdapter(dependentsAdapter);


        RecyclerView.LayoutManager mLayoutManager3 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rvMyLEA.setLayoutManager(mLayoutManager3);
        ((DefaultItemAnimator) rvMyLEA.getItemAnimator()).setSupportsChangeAnimations(false);
        rvMyLEA.setAdapter(myMentorAdapter);


        RecyclerView.LayoutManager mLayoutManager4 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rvTopLEA.setLayoutManager(mLayoutManager4);
        ((DefaultItemAnimator) rvTopLEA.getItemAnimator()).setSupportsChangeAnimations(false);
        rvTopLEA.setAdapter(topMentorAdapter);
    }


    @OnClick({R.id.imgFilter, R.id.txtViewAllTopMentor, R.id.txtViewAllMyMentor, R.id.txtViewAllDependents, R.id.contChat, R.id.contSessions, R.id.imgHome})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgFilter:
                if (edtSearch.getStringTrimmed().isEmpty()) {
                    UIHelper.showAlertDialog(getContext(), "Kindly type something to search");
                    return;
                }
                searchMentors(edtSearch.getStringTrimmed());
                break;
            case R.id.txtViewAllTopMentor:
                getAllTopMentors();
                break;
            case R.id.txtViewAllMyMentor:
                getAllMyMentors();
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
                selectedCategory = arrCategories.get(position);
                categoriesAdapter.notifyDataSetChanged();

            } else if (((String) type).equalsIgnoreCase(MyMentorAdapter.class.getSimpleName())) {
                getBaseActivity().addDockableFragment(LEAProfileFragment.newInstance(), true);
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


                arrCategories.clear();
                arrCategories.add(new SpinnerModel("All", 0));
                arrCategories.addAll(arrayList);
                arrCategories.get(0).setSelected(true);

                categoriesAdapter.notifyDataSetChanged();

            }

            @Override
            public void onError(Object object) {

            }
        });
    }


    public void getTopMentors(int limit) {

        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put(WebServiceConstants.Q_PARAM_LIMIT, limit);
        queryMap.put(WebServiceConstants.Q_PARAM_OFFSET, 0);
        queryMap.put(WebServiceConstants.Q_PARAM_ROLE, AppConstants.MENTOR_ROLE);
        queryMap.put(WebServiceConstants.Q_PARAM_TOP_MENTOR, "yes");
        if (selectedCategory != null && selectedCategory.getId() != 0) {
            queryMap.put(WebServiceConstants.Q_PARAM_DEPT_ID, selectedCategory.getId());
        }


        getBaseWebService().getAPIAnyObject(WebServiceConstants.PATH_GET_USERS, queryMap, new WebServices.IRequestWebResponseAnyObjectCallBack() {
            @Override
            public void requestDataResponse(WebResponse<Object> webResponse) {
                Type type = new TypeToken<ArrayList<UserModel>>() {
                }.getType();
                ArrayList<UserModel> arrayList = GsonFactory.getSimpleGson()
                        .fromJson(GsonFactory.getSimpleGson().toJson(webResponse.result)
                                , type);


                arrTopMentor.clear();
                arrTopMentor.addAll(arrayList);
                topMentorAdapter.notifyDataSetChanged();

            }

            @Override
            public void onError(Object object) {

            }
        });
    }


    public void getMyMentors(int limit) {

        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put(WebServiceConstants.Q_PARAM_LIMIT, limit);
        queryMap.put(WebServiceConstants.Q_PARAM_OFFSET, 0);
        queryMap.put(WebServiceConstants.Q_PARAM_ROLE, AppConstants.MENTOR_ROLE);
        queryMap.put(WebServiceConstants.Q_PARAM_MY_MENTOR, "yes");
        if (selectedCategory != null && selectedCategory.getId() != 0) {
            queryMap.put(WebServiceConstants.Q_PARAM_DEPT_ID, selectedCategory.getId());
        }


        getBaseWebService().getAPIAnyObject(WebServiceConstants.PATH_GET_USERS, queryMap, new WebServices.IRequestWebResponseAnyObjectCallBack() {
            @Override
            public void requestDataResponse(WebResponse<Object> webResponse) {
                Type type = new TypeToken<ArrayList<UserModel>>() {
                }.getType();
                ArrayList<UserModel> arrayList = GsonFactory.getSimpleGson()
                        .fromJson(GsonFactory.getSimpleGson().toJson(webResponse.result)
                                , type);


                arrMyMentor.clear();
                arrMyMentor.addAll(arrayList);
                myMentorAdapter.notifyDataSetChanged();

            }

            @Override
            public void onError(Object object) {

            }
        });
    }


    public void searchMentors(String text) {
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put(WebServiceConstants.Q_PARAM_ROLE, AppConstants.MENTOR_ROLE);
        queryMap.put(WebServiceConstants.Q_PARAM_OFFSET, 0);
        queryMap.put(WebServiceConstants.Q_PARAM_LIMIT, 0);
        queryMap.put(WebServiceConstants.Q_PARAM_SEARCH, text);



        getBaseWebService().getAPIAnyObject(WebServiceConstants.PATH_GET_USERS, queryMap,
                new WebServices.IRequestWebResponseAnyObjectCallBack() {
                    @Override
                    public void requestDataResponse(WebResponse<Object> webResponse) {

                        Type type = new TypeToken<ArrayList<UserModel>>() {
                        }.getType();
                        ArrayList<UserModel> arrayList = GsonFactory.getSimpleGson()
                                .fromJson(GsonFactory.getSimpleGson().toJson(webResponse.result)
                                        , type);

                        if (arrayList.isEmpty()) {
                            UIHelper.showAlertDialog(getContext(), "No Mentors Found on \"" + text + "\"");
                            return;
                        }

                        getBaseActivity().addDockableFragment(ViewLEAListFragment.newInstance(MentorType.SEARCHMENTOR, arrayList, text), false);

                    }

                    @Override
                    public void onError(Object object) {

                    }
                });
    }

    public void getAllMyMentors() {
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put(WebServiceConstants.Q_PARAM_ROLE, AppConstants.MENTOR_ROLE);
        queryMap.put(WebServiceConstants.Q_PARAM_OFFSET, 0);
        queryMap.put(WebServiceConstants.Q_PARAM_LIMIT, 0);
        queryMap.put(WebServiceConstants.Q_PARAM_MY_MENTOR, "yes");
        if (selectedCategory != null && selectedCategory.getId() != 0) {
            queryMap.put(WebServiceConstants.Q_PARAM_DEPT_ID, selectedCategory.getId());
        }


        getBaseWebService().getAPIAnyObject(WebServiceConstants.PATH_GET_USERS, queryMap,
                new WebServices.IRequestWebResponseAnyObjectCallBack() {
                    @Override
                    public void requestDataResponse(WebResponse<Object> webResponse) {

                        Type type = new TypeToken<ArrayList<UserModel>>() {
                        }.getType();
                        ArrayList<UserModel> arrayList = GsonFactory.getSimpleGson()
                                .fromJson(GsonFactory.getSimpleGson().toJson(webResponse.result)
                                        , type);

                        if (arrayList.isEmpty()) {
                            UIHelper.showToast(getContext(), "No Mentors Found");
                            return;
                        }

                        getBaseActivity().addDockableFragment(ViewLEAListFragment.newInstance(MentorType.MYMENTOR, arrayList, ""), false);

                    }

                    @Override
                    public void onError(Object object) {

                    }
                });
    }

    public void getAllTopMentors() {
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put(WebServiceConstants.Q_PARAM_ROLE, AppConstants.MENTOR_ROLE);
        queryMap.put(WebServiceConstants.Q_PARAM_LIMIT, 0);
        queryMap.put(WebServiceConstants.Q_PARAM_OFFSET, 0);
        queryMap.put(WebServiceConstants.Q_PARAM_TOP_MENTOR, "yes");
        if (selectedCategory != null && selectedCategory.getId() != 0) {
            queryMap.put(WebServiceConstants.Q_PARAM_DEPT_ID, selectedCategory.getId());
        }


        getBaseWebService().getAPIAnyObject(WebServiceConstants.PATH_GET_USERS, queryMap,
                new WebServices.IRequestWebResponseAnyObjectCallBack() {
                    @Override
                    public void requestDataResponse(WebResponse<Object> webResponse) {

                        Type type = new TypeToken<ArrayList<UserModel>>() {
                        }.getType();
                        ArrayList<UserModel> arrayList = GsonFactory.getSimpleGson()
                                .fromJson(GsonFactory.getSimpleGson().toJson(webResponse.result)
                                        , type);


                        if (arrayList.isEmpty()) {
                            UIHelper.showToast(getContext(), "No Mentors Found");
                            return;
                        }
                        getBaseActivity().addDockableFragment(ViewLEAListFragment.newInstance(MentorType.TOPMENTOR, arrayList, ""), false);

                    }

                    @Override
                    public void onError(Object object) {

                    }
                });
    }


}
