package com.tekrevol.papp.fragments;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.tekrevol.papp.R;
import com.tekrevol.papp.adapters.recyleradapters.ViewAllDependentsAdapter;
import com.tekrevol.papp.callbacks.OnItemAdd;
import com.tekrevol.papp.callbacks.OnItemClickListener;
import com.tekrevol.papp.constatnts.WebServiceConstants;
import com.tekrevol.papp.fragments.abstracts.BaseFragment;
import com.tekrevol.papp.helperclasses.ui.helper.UIHelper;
import com.tekrevol.papp.managers.retrofit.WebServices;
import com.tekrevol.papp.models.receiving_model.UserModel;
import com.tekrevol.papp.models.wrappers.WebResponse;
import com.tekrevol.papp.widget.AnyEditTextView;
import com.tekrevol.papp.widget.AnyTextView;
import com.tekrevol.papp.widget.TitleBar;
import com.github.clans.fab.FloatingActionButton;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by hamza.ahmed on 7/19/2018.
 */

public class ViewAllDependentsFragment extends BaseFragment implements OnItemClickListener, OnItemAdd {


    Unbinder unbinder;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.empty_view)
    AnyTextView emptyView;
    @BindView(R.id.emptyview_container)
    LinearLayout emptyviewContainer;
    @BindView(R.id.edtSearchBar)
    AnyEditTextView edtSearchBar;
    @BindView(R.id.imgSearch)
    ImageView imgSearch;
    @BindView(R.id.contSearchBar)
    LinearLayout contSearchBar;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.contParent)
    RelativeLayout contParent;


    ArrayList<UserModel> arrData;
    ViewAllDependentsAdapter adapter;


    public static ViewAllDependentsFragment newInstance() {

        Bundle args = new Bundle();

        ViewAllDependentsFragment fragment = new ViewAllDependentsFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public int getDrawerLockMode() {
        return 0;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_generic_recycler_view;
    }

    @Override
    public void setTitlebar(TitleBar titleBar) {

        titleBar.resetViews();
        titleBar.setVisibility(View.VISIBLE);
        titleBar.setTitle("My Dependents");
        titleBar.showBackButton(getBaseActivity());
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        arrData = new ArrayList<>();
        adapter = new ViewAllDependentsAdapter(getContext(), arrData, this);

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

        bindRecyclerView();
        arrData.clear();
        arrData.addAll(getCurrentUser().getDependants());
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
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        ((DefaultItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        int resId = R.anim.layout_animation_fall_bottom;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getContext(), resId);
//        recylerView.setLayoutAnimation(animation);
        recyclerView.setAdapter(adapter);
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

        UserModel model = (UserModel) object;

        switch (view.getId()) {
            case R.id.imgRemove:
                UIHelper.showAlertDialog("Are you sure you want to remove " + model.getUserDetails().getFullName() + "?",
                        "Alert", (dialogInterface, i) -> {

                            getBaseWebService().deleteAPIAnyObject(WebServiceConstants.PATH_GET_USERS_SLASH + model.getId(), "",
                                    new WebServices.IRequestWebResponseAnyObjectCallBack() {
                                        @Override
                                        public void requestDataResponse(WebResponse<Object> webResponse) {
                                            UserModel userModel = getCurrentUser();
                                            userModel.getDependants().remove(position);
                                            setCurrentUser(userModel);
                                            arrData.clear();
                                            arrData.addAll(userModel.getDependants());
                                            adapter.notifyDataSetChanged();

                                            UIHelper.showToast(getContext(), webResponse.message);
                                        }

                                        @Override
                                        public void onError(Object object) {

                                        }
                                    });
                        }, getContext());
                break;

            case R.id.imgEdit:
                getBaseActivity().addDockableFragment(EditDependentFragment.newInstance(model), false);
                break;
        }


    }

    @Override
    public void onItemAdd(Object object) {
//        arrCategories.add(new SpinnerModel("John Doe"));
//        adapter.notifyDataSetChanged();
    }

    @OnClick({R.id.imgSearch, R.id.fab})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgSearch:
                break;
            case R.id.fab:
                getBaseActivity().addDockableFragment(AddDependentFragment.newInstance(false, null), true);
                break;
        }
    }
}
