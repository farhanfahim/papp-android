package com.tekrevol.papp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.tekrevol.papp.R;
import com.tekrevol.papp.adapters.recyleradapters.ReviewsAdapter;
import com.tekrevol.papp.callbacks.OnItemAdd;
import com.tekrevol.papp.callbacks.OnItemClickListener;
import com.tekrevol.papp.constatnts.Constants;
import com.tekrevol.papp.fragments.abstracts.BaseFragment;
import com.tekrevol.papp.helperclasses.ui.helper.UIHelper;
import com.tekrevol.papp.models.general.SpinnerModel;
import com.tekrevol.papp.widget.AnyTextView;
import com.tekrevol.papp.widget.TitleBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by hamza.ahmed on 7/19/2018.
 */

public class ReviewsFragment extends BaseFragment implements OnItemClickListener, OnItemAdd {


    Unbinder unbinder;


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.empty_view)
    AnyTextView emptyView;
    @BindView(R.id.emptyview_container)
    LinearLayout emptyviewContainer;
    @BindView(R.id.contParent)
    RelativeLayout contParent;
    @BindView(R.id.contSend)
    LinearLayout contSend;


    ReviewsAdapter adapter;
    ArrayList<SpinnerModel> arrData;
    @BindView(R.id.contMessage)
    LinearLayout contMessage;


    public static ReviewsFragment newInstance() {

        Bundle args = new Bundle();

        ReviewsFragment fragment = new ReviewsFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public int getDrawerLockMode() {
        return 0;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_reviews;
    }

    @Override
    public void setTitlebar(TitleBar titleBar) {

        titleBar.resetViews();
        titleBar.setVisibility(View.VISIBLE);
        titleBar.setTitle("Reviews");
        titleBar.showBackButton(getBaseActivity());
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        arrData = new ArrayList<>();
        adapter = new ReviewsAdapter(getContext(), arrData, this);
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

        if (isMentor()) {
            contMessage.setVisibility(View.GONE);
        } else {
            contMessage.setVisibility(View.VISIBLE);
        }


        if (onCreated) {
            adapter.notifyDataSetChanged();
            return;
        }


        arrData.clear();
        arrData.addAll(Constants.getAddDependentsArray2());
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


    }

    @Override
    public void onItemAdd(Object object) {
//        arrCategories.add(new SpinnerModel("John Doe"));
//        adapter.notifyDataSetChanged();
    }


    @OnClick(R.id.contSend)
    public void onViewClicked() {
        UIHelper.showAlertDialogWithCallback("Thanks for submitting your review", "Review", (dialogInterface, i) -> {
            dialogInterface.dismiss();
            getBaseActivity().onBackPressed();
        }, getContext());
    }
}
