package com.tekrevol.papp.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jcminarro.roundkornerlayout.RoundKornerLinearLayout;
import com.tekrevol.papp.R;
import com.tekrevol.papp.activities.CallActivity;
import com.tekrevol.papp.adapters.recyleradapters.DependentsAdapter;
import com.tekrevol.papp.callbacks.OnItemClickListener;
import com.tekrevol.papp.constatnts.AppConstants;
import com.tekrevol.papp.constatnts.WebServiceConstants;
import com.tekrevol.papp.fragments.abstracts.BaseFragment;
import com.tekrevol.papp.helperclasses.GooglePlaceHelper;
import com.tekrevol.papp.helperclasses.StringHelper;
import com.tekrevol.papp.helperclasses.ui.helper.UIHelper;
import com.tekrevol.papp.libraries.imageloader.ImageLoaderHelper;
import com.tekrevol.papp.managers.DateManager;
import com.tekrevol.papp.managers.retrofit.WebServices;
import com.tekrevol.papp.models.receiving_model.OpenTokSessionRecModel;
import com.tekrevol.papp.models.receiving_model.SessionRecievingModel;
import com.tekrevol.papp.models.receiving_model.SessionUsers;
import com.tekrevol.papp.models.receiving_model.UserModel;
import com.tekrevol.papp.models.wrappers.WebResponse;
import com.tekrevol.papp.widget.AnyTextView;
import com.tekrevol.papp.widget.TitleBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by hamza.ahmed on 7/19/2018.
 */

public class SessionDetailsFragment extends BaseFragment implements OnItemClickListener {


    Unbinder unbinder;


    DependentsAdapter adapter;
    ArrayList<UserModel> arrData;

    @BindView(R.id.txtDesc)
    AnyTextView txtDesc;
    @BindView(R.id.rvDependents)
    RecyclerView rvDependents;
    @BindView(R.id.txtLocation)
    AnyTextView txtLocation;
    @BindView(R.id.txtDate)
    AnyTextView txtDate;
    @BindView(R.id.txtTime)
    AnyTextView txtTime;
    @BindView(R.id.txtMessage)
    AnyTextView txtMessage;
    @BindView(R.id.txtSessionType)
    AnyTextView txtSessionType;
    @BindView(R.id.imgOneOnOne)
    ImageView imgOneOnOne;
    @BindView(R.id.imgCall)
    ImageView imgCall;
    @BindView(R.id.imgVdoCall)
    ImageView imgVdoCall;
    @BindView(R.id.imgStop)
    ImageView imgStop;
    @BindView(R.id.txtTimer)
    AnyTextView txtTimer;
    @BindView(R.id.contAccept)
    LinearLayout contAccept;
    @BindView(R.id.contReject)
    LinearLayout contReject;
    @BindView(R.id.contRequest)
    LinearLayout contRequest;
    @BindView(R.id.contSessionStartOptions)
    RoundKornerLinearLayout contSessionStartOptions;
    @BindView(R.id.contPlace)
    LinearLayout contPlace;
    @BindView(R.id.txtSessionTypeDesc)
    AnyTextView txtSessionTypeDesc;
    @BindView(R.id.contSessionType)
    LinearLayout contSessionType;
    @BindView(R.id.contComplete)
    LinearLayout contComplete;
    private SessionRecievingModel sessionRecievingModel;

    boolean isOneOnOneSessionInProgress = false;


    public static SessionDetailsFragment newInstance(SessionRecievingModel sessionRecievingModel) {

        Bundle args = new Bundle();

        SessionDetailsFragment fragment = new SessionDetailsFragment();
        fragment.sessionRecievingModel = sessionRecievingModel;
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public int getDrawerLockMode() {
        return 0;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_session_detail;
    }

    @Override
    public void setTitlebar(TitleBar titleBar) {

        titleBar.resetViews();
        titleBar.setVisibility(View.VISIBLE);
        titleBar.setTitle("Session Details");
//        titleBar.showResideMenu(getHomeActivity());
        titleBar.showBackButton(getBaseActivity());
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        arrData = new ArrayList<>();
        adapter = new DependentsAdapter(getContext(), arrData, this);
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


        bindData();


        isOneOnOneSessionInProgress = ((sessionRecievingModel.getStatus() == AppConstants.SESSION_STATUS_ACCEPTED_BY_MENTOR) && sessionRecievingModel.getSessionType() == AppConstants.SESSION_TYPE_ONE_ON_ONE && !StringHelper.isNullOrEmpty(sessionRecievingModel.getStartDate()));

        if (isOneOnOneSessionInProgress) {
            txtSessionType.setText("Stop Session");
            imgOneOnOne.setVisibility(View.GONE);
            imgStop.setVisibility(View.VISIBLE);
        }

        if (!isMentor()) {
            contRequest.setVisibility(View.GONE);
            contSessionStartOptions.setVisibility(View.GONE);
            txtDesc.setText(sessionRecievingModel.getDuration() + " hour session with Mentor: " + sessionRecievingModel.getMentor().getUserDetails().getFullName());
        }


    }

    public void bindData() {

        if (sessionRecievingModel.getStatus() == AppConstants.SESSION_STATUS_PENDING) {
            contRequest.setVisibility(View.VISIBLE);
            contSessionStartOptions.setVisibility(View.GONE);
        } else if (sessionRecievingModel.getStatus() == AppConstants.SESSION_STATUS_ACCEPTED_BY_MENTOR) {
            contRequest.setVisibility(View.GONE);
            contSessionStartOptions.setVisibility(View.VISIBLE);

            imgOneOnOne.setVisibility(View.GONE);
            imgCall.setVisibility(View.GONE);
            imgVdoCall.setVisibility(View.GONE);

            if (sessionRecievingModel.getSessionType() == AppConstants.SESSION_TYPE_AUDIO) {
                imgCall.setVisibility(View.VISIBLE);
            } else if (sessionRecievingModel.getSessionType() == AppConstants.SESSION_TYPE_VIDEO) {
                imgVdoCall.setVisibility(View.VISIBLE);
            } else if (sessionRecievingModel.getSessionType() == AppConstants.SESSION_TYPE_ONE_ON_ONE) {
                imgOneOnOne.setVisibility(View.VISIBLE);
            }

            if (StringHelper.isNullOrEmpty(sessionRecievingModel.getStartDate())) {
                contComplete.setVisibility(View.GONE);
            } else {
                contComplete.setVisibility(View.VISIBLE);
            }


        } else {
            contRequest.setVisibility(View.GONE);
            contSessionStartOptions.setVisibility(View.GONE);
        }

        if (sessionRecievingModel.getSessionType() == AppConstants.SESSION_TYPE_ONE_ON_ONE) {
            contPlace.setVisibility(View.VISIBLE);
        } else {
            contPlace.setVisibility(View.GONE);
        }


        txtDesc.setText(sessionRecievingModel.getDuration() + " hour session with Dependent of " + sessionRecievingModel.getUser().getUserDetails().getFullName());
        txtLocation.setText(sessionRecievingModel.getAddress());
        txtDate.setText(DateManager.getDate(sessionRecievingModel.getScheduleDate(), AppConstants.DISPLAY_DATE_ONLY_FORMAT) + " - ");
        txtTime.setText(DateManager.getDate(sessionRecievingModel.getScheduleDate(), AppConstants.DISPLAY_TIME_ONLY_FORMAT));
        txtMessage.setText(sessionRecievingModel.getMessage());
        txtSessionTypeDesc.setText(sessionRecievingModel.getTypeText());


        arrData.clear();

        for (SessionUsers sessionUser : sessionRecievingModel.getSessionUsers()) {
            for (UserModel dependant : sessionRecievingModel.getUser().getDependants()) {
                if (dependant.getId() == sessionUser.getDependantId()) {
                    arrData.add(dependant);
                }
            }

        }

        adapter.notifyDataSetChanged();


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


    private void bindRecyclerView() {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rvDependents.setLayoutManager(mLayoutManager);
        ((DefaultItemAnimator) rvDependents.getItemAnimator()).setSupportsChangeAnimations(false);
        int resId = R.anim.layout_animation_fall_bottom;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getContext(), resId);
//        recylerView.setLayoutAnimation(animation);
        rvDependents.setAdapter(adapter);
    }


    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onItemClick(int position, Object object, View view, Object type) {

    }


    @OnClick({R.id.imgOneOnOne, R.id.imgCall, R.id.imgVdoCall, R.id.txtLocation, R.id.imgStop, R.id.contAccept, R.id.contReject, R.id.contComplete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgOneOnOne:
                startSessionAPI(sessionRecievingModel.getId(), sessionRecievingModel.getSessionType());
                break;
            case R.id.imgCall:
                startSessionAPI(sessionRecievingModel.getId(), sessionRecievingModel.getSessionType());
                break;
            case R.id.imgVdoCall:
                startSessionAPI(sessionRecievingModel.getId(), sessionRecievingModel.getSessionType());
                break;
            case R.id.imgStop:
                completeSessionAPI(sessionRecievingModel.getId());
                break;

            case R.id.txtLocation:
                GooglePlaceHelper.intentOpenMap(getBaseActivity(), Double.valueOf(sessionRecievingModel.getLat()), Double.valueOf(sessionRecievingModel.getLng()), sessionRecievingModel.getAddress());
                break;

            case R.id.contAccept:
                acceptSessionAPI(sessionRecievingModel.getId());
                break;

            case R.id.contReject:
                declineSessionAPI(sessionRecievingModel.getId());
                break;


            case R.id.contComplete:
                completeSessionAPI(sessionRecievingModel.getId());
                break;
        }
    }


    private void completeSessionAPI(int id) {

        UIHelper.showAlertDialog("Are you sure you want to complete this session?", "Complete Session", (dialog, which) -> {
            getBaseWebService().postAPIAnyObject(WebServiceConstants.PATH_COMPLETE_SESSION + id, "", new WebServices.IRequestWebResponseAnyObjectCallBack() {
                @Override
                public void requestDataResponse(WebResponse<Object> webResponse) {
                    UIHelper.showShortToastInCenter(getContext(), webResponse.message);
                    getBaseActivity().isReloadFragmentOnBack = true;
                    getBaseActivity().popStackTill(1);
                }

                @Override
                public void onError(Object object) {

                }
            });
        }, getContext());
    }


    private void acceptSessionAPI(int id) {
        getBaseWebService().postAPIAnyObject(WebServiceConstants.PATH_ACCEPT_SESSION + id, "", new WebServices.IRequestWebResponseAnyObjectCallBack() {
            @Override
            public void requestDataResponse(WebResponse<Object> webResponse) {
                UIHelper.showShortToastInCenter(getContext(), webResponse.message);
                getBaseActivity().isReloadFragmentOnBack = true;
                getBaseActivity().popStackTill(1);

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
                getBaseActivity().isReloadFragmentOnBack = true;
                getBaseActivity().popStackTill(1);

            }

            @Override
            public void onError(Object object) {

            }
        });
    }


    private void startSessionAPI(int sessionId, int sessionType) {
        getBaseWebService().postAPIAnyObject(WebServiceConstants.PATH_START_SESSION + sessionId, "", new WebServices.IRequestWebResponseAnyObjectCallBack() {
            @Override
            public void requestDataResponse(WebResponse<Object> webResponse) {
                if (sessionType == AppConstants.SESSION_TYPE_ONE_ON_ONE) {
                    txtSessionType.setText("Stop Session");
                    imgOneOnOne.setVisibility(View.GONE);
                    imgStop.setVisibility(View.VISIBLE);
                } else if (sessionType == AppConstants.SESSION_TYPE_VIDEO) {
                    startCallActivity(sessionId, sessionType);
                } else if (sessionType == AppConstants.SESSION_TYPE_AUDIO) {
                    startCallActivity(sessionId, sessionType);
                }
            }

            @Override
            public void onError(Object object) {

            }
        });
    }

    private void startCallActivity(int sessionId, int sessionType) {
        contComplete.setVisibility(View.VISIBLE);

        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put(WebServiceConstants.Q_PARAM_SESSION_ID, sessionId);
        if (sessionRecievingModel.getSessionUsers() == null || sessionRecievingModel.getSessionUsers().isEmpty()) {
            UIHelper.showToast(getContext(), "No Session User Exist");
            return;
        }
        queryMap.put(WebServiceConstants.Q_PARAM_DEPENDANT_ID, sessionRecievingModel.getSessionUsers().get(0).getDependantId());
        queryMap.put(WebServiceConstants.Q_PARAM_SESSION_TYPE, sessionType);

        getBaseWebService().getAPIAnyObject(WebServiceConstants.PATH_CREATE_OPENTOK_SESSION, queryMap, new WebServices.IRequestWebResponseAnyObjectCallBack() {
            @Override
            public void requestDataResponse(WebResponse<Object> webResponse) {

                OpenTokSessionRecModel openTokSessionRecModel = getGson().fromJson(getGson().toJson(webResponse.result), OpenTokSessionRecModel.class);
                openTokSessionRecModel.setCaller(true);
                openTokSessionRecModel.setSessionType(String.valueOf(AppConstants.SESSION_TYPE_VIDEO));
                openTokSessionRecModel.setMentorName(sessionRecievingModel.getDependent().getUserDetails().getFullName());
                openTokSessionRecModel.setMentorImage(ImageLoaderHelper.getImageURLFromPath(sessionRecievingModel.getDependent().getUserDetails().getImage()));


                Log.d(TAG, "requestDataResponse: " + openTokSessionRecModel.toString());
                getBaseActivity().openActivity(CallActivity.class, openTokSessionRecModel.toString());

            }

            @Override
            public void onError(Object object) {

            }
        });
    }

}
