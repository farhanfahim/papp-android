package edu.aku.ehs.fragments.dialogs;

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
import android.widget.ImageView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import edu.aku.ehs.R;
import edu.aku.ehs.adapters.SpinnerDialogAdapter;
import edu.aku.ehs.callbacks.OnSpinnerItemClickListener;
import edu.aku.ehs.callbacks.OnSpinnerOKPressedListener;
import edu.aku.ehs.models.SpinnerModel;
import edu.aku.ehs.widget.AnyTextView;
import edu.aku.ehs.widget.recyclerview_layout.CustomLayoutManager;

/**
 * Created by khanhamza on 21-Feb-17.
 */

public class SpinnerDialogFragment extends DialogFragment {

    String title;
    Unbinder unbinder;
    SpinnerDialogAdapter adapter;

    @BindView(R.id.txtTitle)
    AnyTextView txtTitle;
    @BindView(R.id.btnClose)
    ImageView btnClose;
    @BindView(R.id.txtOK)
    edu.aku.ehs.widget.AnyTextView txtOK;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private ArrayList<SpinnerModel> arrData;

    private OnSpinnerItemClickListener onItemClickListener;
    private OnSpinnerOKPressedListener onSpinnerOKPressedListener;
    private int scrollToPosition;

    public SpinnerDialogFragment() {
    }

    public static SpinnerDialogFragment newInstance(String title, ArrayList<SpinnerModel> arrData,
                                                    OnSpinnerItemClickListener onItemClickListener, OnSpinnerOKPressedListener onSpinnerOKPressedListener, int scrollToPosition) {
        SpinnerDialogFragment frag = new SpinnerDialogFragment();

        Bundle args = new Bundle();
        frag.title = title;
        frag.arrData = arrData;
        frag.onItemClickListener = onItemClickListener;
        frag.scrollToPosition = scrollToPosition;
        frag.onSpinnerOKPressedListener = onSpinnerOKPressedListener;
        frag.setArguments(args);

        return frag;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, R.style.DialogTheme);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_spinner_popup, container);
        unbinder = ButterKnife.bind(this, view);

        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setListeners();
        adapter = new SpinnerDialogAdapter(getActivity(), arrData, onItemClickListener);
        adapter.notifyDataSetChanged();
        txtTitle.setText(title);

        bindView();
//        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
    }

    private void setListeners() {

    }

    private void bindView() {
        RecyclerView.LayoutManager mLayoutManager = new CustomLayoutManager(getContext(), LinearLayoutManager.VERTICAL, true);
        recyclerView.setLayoutManager(mLayoutManager);
        ((DefaultItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        int resId = R.anim.layout_animation_fall_bottom;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getContext(), resId);
        recyclerView.setLayoutAnimation(animation);
        recyclerView.setAdapter(adapter);
        scrollToPosition(scrollToPosition);

    }

    public void scrollToPosition(int scrollToPosition) {
        if (scrollToPosition > -1) {
            recyclerView.scrollToPosition(scrollToPosition);
        } else {
            recyclerView.scrollToPosition(0);
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick({R.id.btnClose, R.id.txtOK})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnClose:
                this.dismiss();
                break;
            case R.id.txtOK:
                if (onSpinnerOKPressedListener != null) {
                    onSpinnerOKPressedListener.onItemSelect(null);
                    this.dismiss();
                }
                this.dismiss();
                break;
        }
    }
}

