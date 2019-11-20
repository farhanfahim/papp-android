package com.tekrevol.papp.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.LinearLayout;

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
import com.tekrevol.papp.helperclasses.ui.helper.UIHelper;
import com.tekrevol.papp.managers.retrofit.GsonFactory;
import com.tekrevol.papp.managers.retrofit.WebServices;
import com.tekrevol.papp.models.receiving_model.UserModel;
import com.tekrevol.papp.models.wrappers.DependentDetailWrapper;
import com.tekrevol.papp.models.wrappers.WebResponse;
import com.tekrevol.papp.widget.AnyTextView;
import com.tekrevol.papp.widget.TitleBar;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import co.chatsdk.core.dao.Keys;
import co.chatsdk.core.dao.Thread;
import co.chatsdk.core.dao.User;
import co.chatsdk.core.interfaces.ThreadType;
import co.chatsdk.core.session.ChatSDK;
import co.chatsdk.core.types.ConnectionType;
import io.reactivex.CompletableObserver;
import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

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
    @BindView(R.id.contFilters)
    LinearLayout contFilters;
    private MentorType mentorType;
    private ArrayList<UserModel> arrAccess;
    private ArrayList<UserModel> arrNotAccess;
    private String text = "";
    boolean isAccessibleSelected = true;

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
        adapter = new KidsCommunityAdapter(getContext(), arrData, this, isMentor());
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
            if (isAccessibleSelected) {
                filter(R.color.base_amber, R.color.white, R.color.white, R.color.dark_gray, arrAccess);
            } else {
                filter(R.color.white, R.color.base_amber, R.color.dark_gray, R.color.white, arrNotAccess);
            }
            adapter.notifyDataSetChanged();
            return;
        }


        if (isMentor()) {
            getAccessibleDependant();
            getNotAccessibleDependant();
            contFilters.setVisibility(View.VISIBLE);
        } else if (isDependent()) {
            getAllDependant();
            contFilters.setVisibility(View.GONE);
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

        if (isMentor()) {
            if (userModel.getAccessable() == AppConstants.ACCESSIBLE) {
                getDependantDetail(userModel.getId());
            } else {
                getBaseActivity().addDockableFragment(ChildProfileFragment.newInstance(userModel, null), true);
            }
        } else if (isDependent()) {

            final List<User> existingContacts = ChatSDK.contact().contacts();


            for (User existingContact : existingContacts) {
                if (Integer.valueOf(existingContact.getEntityID()) == userModel.getId()) {
                    createThread(existingContact);
                    return;
                }
            }

            searchUser(userModel);
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
                filter(R.color.base_amber, R.color.white, R.color.white, R.color.dark_gray, arrAccess);
                isAccessibleSelected = true;
                break;
            case R.id.contNotAccessible:
                filter(R.color.white, R.color.base_amber, R.color.dark_gray, R.color.white, arrNotAccess);
                isAccessibleSelected = false;
                break;
        }
    }

    private void filter(int p, int p2, int p3, int p4, ArrayList<UserModel> arrayFiltered) {
        contAccessible.setBackgroundColor(getBaseActivity().getResources().getColor(p));
        contNotAccessible.setBackgroundColor(getBaseActivity().getResources().getColor(p2));
        txtAccessible.setTextColor(getBaseActivity().getResources().getColor(p3));
        txtNotAccessible.setTextColor(getBaseActivity().getResources().getColor(p4));

        arrData.clear();
        arrData.addAll(arrayFiltered);
        adapter.notifyDataSetChanged();
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

    private void getDependantDetail(int id) {

        Map<String, Object> queryMap = new HashMap<>();


        getBaseWebService().getAPIAnyObject(WebServiceConstants.PATH_GET_USERS_SLASH + id, queryMap, new WebServices.IRequestWebResponseAnyObjectCallBack() {
            @Override
            public void requestDataResponse(WebResponse<Object> webResponse) {

                DependentDetailWrapper dependentDetailWrapper = getGson().fromJson(getGson().toJson(webResponse.result), DependentDetailWrapper.class);
                getBaseActivity().addDockableFragment(ChildProfileFragment.newInstance(dependentDetailWrapper.getDependent(), dependentDetailWrapper.getParent()), true);

            }

            @Override
            public void onError(Object object) {

            }
        });
    }


    private void searchUser(UserModel dependentModel) {
        ChatSDK.search().usersForIndex(dependentModel.getEmail(), Keys.Email).subscribe(new Observer<User>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(User user) {

                addContactAndCreateThread(user);


            }

            @Override
            public void onError(Throwable e) {
                UIHelper.showToast(getContext(), e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void addContactAndCreateThread(User user) {
        Log.d(TAG, "onNext: " + user.toString());

        ChatSDK.contact().addContact(user, ConnectionType.Contact).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onComplete() {
                createThread(user);
            }

            @Override
            public void onError(Throwable e) {

            }
        });

    }

    private void createThread(User user) {
        Log.d(TAG, "onNext: " + user.toString());
        List<User> userList = new ArrayList<>();
        userList.add(ChatSDK.currentUser());
        userList.add(user);
//                        getCurrentUser().getId() + ":" + mentorModel.getId()
//        mentorModel.getUserDetails().getFullName()
        ChatSDK.thread()
                .createThread("", userList, ThreadType.Private1to1, null)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Thread>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.e("abc", "onSubscribe");
                    }

                    @Override
                    public void onSuccess(Thread thread) {
                        Log.e("abc", "onSuccess");


                        ChatSDK.ui().startChatActivityForID(getContext(), thread.getEntityID());

//                                        ChatSDK.thread().sendMessageWithText("1010", thread)
//                                                .observeOn(AndroidSchedulers.mainThread())
//                                                .subscribe(new Observer<MessageSendProgress>() {
//                                                    @Override
//                                                    public void onSubscribe(Disposable d) {
//
//                                                    }
//
//                                                    @Override
//                                                    public void onNext(MessageSendProgress messageSendProgress) {
//
//                                                    }
//
//                                                    @Override
//                                                    public void onError(Throwable e) {
//
//                                                    }
//
//                                                    @Override
//                                                    public void onComplete() {
//
//                                                    }
//                                                });

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("abc", "onError");

                    }
                });
    }
}
