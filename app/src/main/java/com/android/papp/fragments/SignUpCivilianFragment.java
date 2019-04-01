package com.android.papp.fragments;

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

import com.android.papp.R;
import com.android.papp.adapters.recyleradapters.AddDependentsAdapter;
import com.android.papp.callbacks.OnItemAdd;
import com.android.papp.callbacks.OnItemClickListener;
import com.android.papp.constatnts.Constants;
import com.android.papp.fragments.abstracts.BaseFragment;
import com.android.papp.helperclasses.ui.helper.UIHelper;
import com.android.papp.models.SpinnerModel;
import com.android.papp.widget.AnyEditTextView;
import com.android.papp.widget.AnyTextView;
import com.android.papp.widget.TitleBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by hamza.ahmed on 7/19/2018.
 */

public class SignUpCivilianFragment extends BaseFragment implements OnItemClickListener, OnItemAdd {


    Unbinder unbinder;
    @BindView(R.id.edtFirstName)
    AnyEditTextView edtFirstName;
    @BindView(R.id.edtLastName)
    AnyEditTextView edtLastName;
    @BindView(R.id.edtEmailAddress)
    AnyEditTextView edtEmailAddress;
    @BindView(R.id.edtPassword)
    AnyEditTextView edtPassword;
    @BindView(R.id.contAddDependents)
    LinearLayout contAddDependents;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.contBtnSignUp)
    LinearLayout contBtnSignUp;
    @BindView(R.id.txtOrLoginWith)
    AnyTextView txtOrLoginWith;
    @BindView(R.id.contFacebookLogin)
    LinearLayout contFacebookLogin;
    @BindView(R.id.contTwitterLogin)
    LinearLayout contTwitterLogin;
    @BindView(R.id.contSocialLogin)
    LinearLayout contSocialLogin;

    AddDependentsAdapter adapter;
    ArrayList<SpinnerModel> arrData;

    public static SignUpCivilianFragment newInstance() {

        Bundle args = new Bundle();

        SignUpCivilianFragment fragment = new SignUpCivilianFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public int getDrawerLockMode() {
        return 0;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_signup_civilian;
    }

    @Override
    public void setTitlebar(TitleBar titleBar) {

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        arrData = new ArrayList<>();
        adapter = new AddDependentsAdapter(getContext(), arrData, this);
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

        arrData.clear();
        arrData.addAll(Constants.getAddDependentsArray());
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


    @OnClick({R.id.contAddDependents, R.id.contBtnSignUp, R.id.contFacebookLogin, R.id.contTwitterLogin})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.contAddDependents:
                getBaseActivity().addDockableFragment(AddDependentFragment.newInstance(arrData), false);
                break;
            case R.id.contBtnSignUp:
                break;
            case R.id.contFacebookLogin:
                break;
            case R.id.contTwitterLogin:
                break;
        }
    }

    @Override
    public void onItemClick(int position, Object object, View view, Object type) {

        SpinnerModel model = (SpinnerModel) object;

        UIHelper.showAlertDialog("Are you sure you want to remove " + model.getText() + "?",
                "Alert", (dialogInterface, i) -> {
                    arrData.remove(position);
                    adapter.notifyDataSetChanged();
                }, getContext());
    }

    @Override
    public void onItemAdd(Object object) {
//        arrCategories.add(new SpinnerModel("John Doe"));
//        adapter.notifyDataSetChanged();
    }
}
