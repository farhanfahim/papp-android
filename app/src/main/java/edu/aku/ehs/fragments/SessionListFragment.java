package edu.aku.ehs.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.github.clans.fab.FloatingActionButton;
import com.google.gson.reflect.TypeToken;
import com.jcminarro.roundkornerlayout.RoundKornerLinearLayout;

import java.lang.reflect.Type;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import edu.aku.ehs.R;
import edu.aku.ehs.adapters.recyleradapters.SessionAdapter;
import edu.aku.ehs.callbacks.OnItemClickListener;
import edu.aku.ehs.constatnts.AppConstants;
import edu.aku.ehs.constatnts.WebServiceConstants;
import edu.aku.ehs.enums.BaseURLTypes;
import edu.aku.ehs.enums.SessionStatus;
import edu.aku.ehs.fragments.abstracts.BaseFragment;
import edu.aku.ehs.fragments.abstracts.GenericDialogFragment;
import edu.aku.ehs.helperclasses.ui.helper.KeyboardHelper;
import edu.aku.ehs.helperclasses.ui.helper.UIHelper;
import edu.aku.ehs.managers.retrofit.GsonFactory;
import edu.aku.ehs.managers.retrofit.WebServices;
import edu.aku.ehs.models.SessionModel;
import edu.aku.ehs.models.wrappers.WebResponse;
import edu.aku.ehs.widget.AnyEditTextView;
import edu.aku.ehs.widget.AnyTextView;
import edu.aku.ehs.widget.TitleBar;
import info.hoang8f.widget.FButton;

/**
 * Created by hamza.ahmed on 7/23/2018.
 */

public class SessionListFragment extends BaseFragment implements OnItemClickListener {

    Unbinder unbinder;
    @BindView(R.id.empty_view)
    AnyTextView emptyView;
    @BindView(R.id.imgBanner)
    ImageView imgBanner;
    @BindView(R.id.btnGetLabs)
    FButton btnGetLabs;
    @BindView(R.id.btnAddEmail)
    FButton btnAddEmail;
    @BindView(R.id.btnAddSchedule)
    FButton btnAddSchedule;
    @BindView(R.id.btnAddEmployees)
    FButton btnAddEmployees;
    @BindView(R.id.contOptionButtons)
    LinearLayout contOptionButtons;
    @BindView(R.id.cbSelectAll)
    CheckBox cbSelectAll;
    @BindView(R.id.contSelection)
    LinearLayout contSelection;
    @BindView(R.id.imgSearch)
    ImageView imgSearch;
    @BindView(R.id.edtSearchBar)
    AnyEditTextView edtSearchBar;
    @BindView(R.id.contSearch)
    RoundKornerLinearLayout contSearch;
    @BindView(R.id.recylerView)
    RecyclerView recylerView;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.contParent)
    RelativeLayout contParent;
    @BindView(R.id.imgClose)
    ImageView imgClose;


    private ArrayList<SessionModel> arrData;
    private SessionAdapter adapter;
    GenericDialogFragment genericDialogFragment = GenericDialogFragment.newInstance();


    public static SessionListFragment newInstance() {

        Bundle args = new Bundle();

        SessionListFragment fragment = new SessionListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getDrawerLockMode() {
        return DrawerLayout.LOCK_MODE_LOCKED_CLOSED;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_general_recyler_view_with_header;
    }

    @Override
    public void setTitlebar(TitleBar titleBar) {
        titleBar.resetViews();
        titleBar.setVisibility(View.VISIBLE);
        titleBar.setTitle("Sessions");
        titleBar.showBackButton(getBaseActivity());
        titleBar.showHome(getBaseActivity());
    }

    @Override
    public void setListeners() {

        edtSearchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                adapter.getFilter().filter(charSequence);
                if (edtSearchBar.getStringTrimmed().length() == 0) {
                    imgClose.setVisibility(View.GONE);
                } else {
                    imgClose.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

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

        arrData = new ArrayList<SessionModel>();
        adapter = new SessionAdapter(getBaseActivity(), arrData, this);
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
        fab.setVisibility(View.VISIBLE);
        contSearch.setVisibility(View.VISIBLE);
        imgBanner.setVisibility(View.VISIBLE);

        bindView();

        if (onCreated) {
            if (AppConstants.isForcedResetFragment) {
                //TODO ask to hamza
                AppConstants.isForcedResetFragment = false;
                new Handler().postDelayed(this::getSessionListCall, 200);
            }
        } else {
            new Handler().postDelayed(this::getSessionListCall, 200);
        }


    }

    @Override
    public void onResume() {
        super.onResume();
        getSessionListCall();
    }

    private void getSessionListCall() {
        new WebServices(getContext(), "", BaseURLTypes.EHS_BASE_URL, true)
                .webServiceRequestAPIAnyObject(WebServiceConstants.METHOD_GET_SESSION_LIST, "",
                        new WebServices.IRequestWebResponseAnyObjectCallBack() {
                            @Override
                            public void requestDataResponse(WebResponse<Object> webResponse) {
                                if (webResponse.result instanceof ArrayList) {

                                    Type type = new TypeToken<ArrayList<SessionModel>>() {
                                    }.getType();
                                    ArrayList<SessionModel> arrayList = GsonFactory.getSimpleGson()
                                            .fromJson(GsonFactory.getSimpleGson().toJson(webResponse.result)
                                                    , type);

                                    arrData.clear();
                                    arrData.addAll(arrayList);
                                    adapter.notifyDataSetChanged();
                                    if (arrData.size() > 0) {
                                        emptyView.setVisibility(View.GONE);
                                    } else {
                                        showEmptyView(webResponse.responseMessage);
                                    }
                                }
                            }

                            @Override
                            public void onError(Object object) {
                                if (object instanceof String) {
                                    showEmptyView((String) object);
                                }
                            }
                        });
    }

    private void showEmptyView(String text) {
        emptyView.setText(text);
        emptyView.setVisibility(View.VISIBLE);
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

        SessionModel model = (SessionModel) object;

        if (model.getStatusId().equalsIgnoreCase(SessionStatus.CLOSED.canonicalForm())) {
            return;
        }


        switch (view.getId()) {
            case R.id.contListItem:
                getBaseActivity().addDockableFragment(SessionDetailFragment.newInstance((SessionModel) object), false);
                break;


            case R.id.btnSchedule:
                getBaseActivity().addDockableFragment(AddSessionFragment.newInstance(false, model), false);
                break;


            case R.id.btnClose:
                closeSession((SessionModel) object, position);
                break;
        }
    }


    @OnClick({R.id.imgClose, R.id.fab})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgClose:
                edtSearchBar.setText("");
                KeyboardHelper.hideSoftKeyboard(getContext(), edtSearchBar);
                break;
            case R.id.fab:
                getBaseActivity().addDockableFragment(AddSessionFragment.newInstance(true, null), false);
                break;
        }
    }


    private void closeSession(SessionModel sessionModel, int position) {
        UIHelper.genericPopUp(getBaseActivity(), genericDialogFragment, "Close Session", "Do you want to close \"" + sessionModel.getDescription() + "\" session?", "Close", "Cancel",
                () -> {
                    sessionModel.setStatusId(SessionStatus.CLOSED.canonicalForm());
                    sessionModel.setClosedBy(getCurrentUser().getName());
                    sessionModel.setLastFileUser(getCurrentUser().getName());
                    genericDialogFragment.dismiss();
                    closeSessionCall(sessionModel);
                }, () -> {
                    genericDialogFragment.dismiss();
                }, false, true);
    }


    private void closeSessionCall(SessionModel sessionModel) {
        new WebServices(getContext(), "", BaseURLTypes.EHS_BASE_URL, true)
                .webServiceRequestAPIAnyObject(WebServiceConstants.METHOD_UPDATE_SESSION, sessionModel.toString(),
                        new WebServices.IRequestWebResponseAnyObjectCallBack() {
                            @Override
                            public void requestDataResponse(WebResponse<Object> webResponse) {
                                UIHelper.showToast(getContext(), webResponse.responseMessage);
                                getSessionListCall();
                            }

                            @Override
                            public void onError(Object object) {
                                if (object instanceof String) {
                                    UIHelper.showToast(getContext(), (String) object);
                                }
                            }
                        });
    }


}
