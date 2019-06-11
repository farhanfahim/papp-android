package com.tekrevol.papp.fragments.dialogs;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.tekrevol.papp.R;
import com.tekrevol.papp.adapters.recyleradapters.ErrorAdapter;
import com.tekrevol.papp.helperclasses.ui.helper.KeyboardHelper;
import com.tekrevol.papp.managers.retrofit.entities.ErrorModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import info.hoang8f.widget.FButton;

/**
 * Created by khanhamza on 21-Feb-17.
 */

public class ErrorDialogFragment extends DialogFragment {


    Unbinder unbinder;

    ErrorAdapter errorAdapter;
    ArrayList<ErrorModel> arrData;
    @BindView(R.id.rvErrors)
    RecyclerView rvErrors;
    @BindView(R.id.btnOK)
    FButton btnOK;


    private View.OnClickListener onCanceButtonClick;


    public ErrorDialogFragment() {
    }

    public static ErrorDialogFragment newInstance(ArrayList<ErrorModel> arrData, View.OnClickListener onDonePress) {
        ErrorDialogFragment frag = new ErrorDialogFragment();

        frag.onCanceButtonClick = onDonePress;
        frag.arrData = arrData;

        Bundle args = new Bundle();
        frag.setArguments(args);

        return frag;
    }

    @Override
    public void onStart() {
        super.onStart();
//        getDialog().getWindow()
//                .setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
//                        getResources().getDimensionPixelSize(R.dimen.x400dp));


        getDialog().getWindow()
                .setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, R.style.DialogTheme);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_error_dialog, container);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        errorAdapter = new ErrorAdapter(getContext(), arrData, null);
        bindRecyclerView();
        errorAdapter.notifyDataSetChanged();

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }


    private void bindRecyclerView() {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        rvErrors.setLayoutManager(mLayoutManager);
        ((DefaultItemAnimator) rvErrors.getItemAnimator()).setSupportsChangeAnimations(false);
        int resId = R.anim.layout_animation_fall_bottom;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getContext(), resId);
//        recylerView.setLayoutAnimation(animation);
        rvErrors.setAdapter(errorAdapter);
    }


    @Override
    public void onResume() {
        super.onResume();

        KeyboardHelper.hideSoftKeyboard(getContext(), getView());
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btnOK})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnOK:
                if (onCanceButtonClick != null) {
                    onCanceButtonClick.onClick(view);
                }
                dismiss();
                break;
        }
    }
}

