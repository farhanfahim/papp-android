package com.tekrevol.papp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.reflect.TypeToken;
import com.jcminarro.roundkornerlayout.RoundKornerLinearLayout;
import com.tekrevol.papp.R;
import com.tekrevol.papp.adapters.recyleradapters.KidsCommunityAdapter;
import com.tekrevol.papp.callbacks.OnItemClickListener;
import com.tekrevol.papp.constatnts.AppConstants;
import com.tekrevol.papp.constatnts.WebServiceConstants;
import com.tekrevol.papp.enums.MentorType;
import com.tekrevol.papp.fragments.abstracts.BaseFragment;
import com.tekrevol.papp.managers.retrofit.GsonFactory;
import com.tekrevol.papp.managers.retrofit.WebServices;
import com.tekrevol.papp.models.receiving_model.UserModel;
import com.tekrevol.papp.models.wrappers.WebResponse;
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

public class KidsCommunityFragment extends BaseFragment implements OnItemClickListener {

    Unbinder unbinder;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.txtNotAccessible)
    AnyTextView txtNotAccessible;
    @BindView(R.id.txtAccessible)
    AnyTextView txtAccessible;

    KidsCommunityAdapter adapter;
    ArrayList<UserModel> arrData;
    @BindView(R.id.contAccessible)
    RoundKornerLinearLayout contAccessible;
    @BindView(R.id.contNotAccessible)
    RoundKornerLinearLayout contNotAccessible;
    private MentorType mentorType;
    private ArrayList<UserModel> arrAccess;
    private ArrayList<UserModel> arrNotAccess;
    private String text = "";


    public static KidsCommunityFragment newInstance() {

        Bundle args = new Bundle();

        KidsCommunityFragment fragment = new KidsCommunityFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getDrawerLockMode() {
        return 0;
    }

    @Override
    protected int getFragmentLayout() {

        return R.layout.fragment_kids_community;
    }

    @Override
    public void setTitlebar(TitleBar titleBar) {
        titleBar.resetViews();
        titleBar.setVisibility(View.VISIBLE);
        titleBar.showBackButton(getBaseActivity());
        titleBar.setTitle("Kids Community");
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

        arrData = new ArrayList<>();
        arrAccess = new ArrayList<>();
        arrNotAccess = new ArrayList<>();
        adapter = new KidsCommunityAdapter(getContext(), arrData, this);
        adapter.notifyDataSetChanged();
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


        bindRecyclerView();


        if (onCreated) {
            adapter.notifyDataSetChanged();
            return;
        }


        if (isMentor()) {
            getAccessibleDependant();
            getNotAccessibleDependant();
            adapter.notifyDataSetChanged();
        } else if (isDependent()) {
            getAllDependant();
            adapter.notifyDataSetChanged();
        }

    }


    private void bindRecyclerView() {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        ((DefaultItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        int resId = R.anim.layout_animation_fall_bottom;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getContext(), resId);
//      recylerView.setLayoutAnimation(animation);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(int position, Object object, View view, Object type) {
        UserModel userModel = (UserModel) object;

        if (userModel.getAccessable() == AppConstants.ACCESSIBLE) {

        } else {
            getBaseActivity().addDockableFragment(ChildProfileFragment.newInstance(userModel, null), true);
        }


    }

    private void getAccessibleDependant() {

        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put(WebServiceConstants.Q_PARAM_ACCESSIBLE, AppConstants.ACCESSIBLE);
        queryMap.put(WebServiceConstants.Q_PARAM_ROLE, AppConstants.DEPENDENT_ROLE);


        getBaseWebService().getAPIAnyObject(WebServiceConstants.PATH_GET_USERS, queryMap, new WebServices.IRequestWebResponseAnyObjectCallBack() {
            @Override
            public void requestDataResponse(WebResponse<Object> webResponse) {
                Type type = new TypeToken<ArrayList<UserModel>>() {
                }.getType();
                ArrayList<UserModel> arrayList = GsonFactory.getSimpleGson()
                        .fromJson(GsonFactory.getSimpleGson().toJson(webResponse.result)
                                , type);


                arrAccess.clear();
                arrAccess.addAll(arrayList);

                arrData.clear();
                arrData.addAll(arrAccess);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onError(Object object) {

            }
        });
    }

    private void getNotAccessibleDependant() {

        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put(WebServiceConstants.Q_PARAM_ACCESSIBLE, AppConstants.NOT_ACCESSIBLE);
        queryMap.put(WebServiceConstants.Q_PARAM_ROLE, AppConstants.DEPENDENT_ROLE);


        getBaseWebService().getAPIAnyObject(WebServiceConstants.PATH_GET_USERS, queryMap, new WebServices.IRequestWebResponseAnyObjectCallBack() {
            @Override
            public void requestDataResponse(WebResponse<Object> webResponse) {
                Type type = new TypeToken<ArrayList<UserModel>>() {
                }.getType();
                ArrayList<UserModel> arrayList = GsonFactory.getSimpleGson()
                        .fromJson(GsonFactory.getSimpleGson().toJson(webResponse.result)
                                , type);


                arrNotAccess.clear();
                arrNotAccess.addAll(arrayList);

                arrData.clear();
                arrData.addAll(arrNotAccess);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Object object) {

            }
        });
    }

    @OnClick({R.id.contAccessible, R.id.contNotAccessible})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.contAccessible:
                contAccessible.setBackgroundColor(getBaseActivity().getResources().getColor(R.color.base_amber));
                contNotAccessible.setBackgroundColor(getBaseActivity().getResources().getColor(R.color.white));
                txtAccessible.setTextColor(getBaseActivity().getResources().getColor(R.color.white));
                txtNotAccessible.setTextColor(getBaseActivity().getResources().getColor(R.color.dark_gray));


                arrData.clear();
                arrData.addAll(arrAccess);
                adapter.notifyDataSetChanged();

                break;
            case R.id.contNotAccessible:
                contAccessible.setBackgroundColor(getBaseActivity().getResources().getColor(R.color.white));
                contNotAccessible.setBackgroundColor(getBaseActivity().getResources().getColor(R.color.base_amber));
                txtAccessible.setTextColor(getBaseActivity().getResources().getColor(R.color.dark_gray));
                txtNotAccessible.setTextColor(getBaseActivity().getResources().getColor(R.color.white));


                arrData.clear();
                arrData.addAll(arrNotAccess);
                adapter.notifyDataSetChanged();

                break;
        }
    }

    private void getAllDependant() {

        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put(WebServiceConstants.Q_PARAM_ROLE, AppConstants.DEPENDENT_ROLE);


        getBaseWebService().getAPIAnyObject(WebServiceConstants.PATH_GET_USERS, queryMap, new WebServices.IRequestWebResponseAnyObjectCallBack() {
            @Override
            public void requestDataResponse(WebResponse<Object> webResponse) {
                Type type = new TypeToken<ArrayList<UserModel>>() {
                }.getType();
                ArrayList<UserModel> arrayList = GsonFactory.getSimpleGson()
                        .fromJson(GsonFactory.getSimpleGson().toJson(webResponse.result)
                                , type);


                arrData.clear();
                arrData.addAll(arrayList);
                adapter.notifyDataSetChanged();
                contAccessible.setVisibility(View.GONE);
                contNotAccessible.setVisibility(View.GONE);
            }

            @Override
            public void onError(Object object) {

            }
        });
    }
}
