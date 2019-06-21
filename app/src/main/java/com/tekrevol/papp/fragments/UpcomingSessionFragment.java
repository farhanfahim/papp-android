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
import com.tekrevol.papp.adapters.recyleradapters.SessionsAdapter;
import com.tekrevol.papp.callbacks.OnItemClickListener;
import com.tekrevol.papp.constatnts.WebServiceConstants;
import com.tekrevol.papp.fragments.abstracts.BaseFragment;
import com.tekrevol.papp.helperclasses.ui.helper.UIHelper;
import com.tekrevol.papp.managers.retrofit.WebServices;
import com.tekrevol.papp.models.receiving_model.SessionRecievingModel;
import com.tekrevol.papp.models.wrappers.WebResponse;
import com.tekrevol.papp.widget.AnyEditTextView;
import com.tekrevol.papp.widget.AnyTextView;
import com.tekrevol.papp.widget.TitleBar;
import com.github.clans.fab.FloatingActionButton;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class UpcomingSessionFragment extends BaseFragment implements OnItemClickListener {


    Unbinder unbinder;

    SessionsAdapter adapter;
    ArrayList<SessionRecievingModel> arrData = new ArrayList<>();

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


    public static UpcomingSessionFragment newInstance(ArrayList<SessionRecievingModel> arrData) {

        Bundle args = new Bundle();

        UpcomingSessionFragment fragment = new UpcomingSessionFragment();
        fragment.arrData.clear();
        fragment.arrData.addAll(arrData);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
                getBaseActivity().addDockableFragment(SessionDetailsFragment.newInstance((SessionRecievingModel) object), true);
                break;


            case R.id.imgDone:
                acceptSessionAPI(((SessionRecievingModel) object).getId());
                break;


            case R.id.imgCancel:
                declineSessionAPI(((SessionRecievingModel) object).getId());
                break;
        }

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

}
