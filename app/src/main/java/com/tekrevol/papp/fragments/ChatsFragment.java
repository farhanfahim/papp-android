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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tekrevol.papp.R;
import com.tekrevol.papp.adapters.recyleradapters.ChatsAdapter;
import com.tekrevol.papp.callbacks.OnItemAdd;
import com.tekrevol.papp.callbacks.OnItemClickListener;
import com.tekrevol.papp.constatnts.Constants;
import com.tekrevol.papp.fragments.abstracts.BaseFragment;
import com.tekrevol.papp.helperclasses.ui.helper.UIHelper;
import com.tekrevol.papp.models.general.SpinnerModel;
import com.tekrevol.papp.widget.AnyEditTextView;
import com.tekrevol.papp.widget.AnyTextView;
import com.tekrevol.papp.widget.TitleBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by hamza.ahmed on 7/19/2018.
 */

public class ChatsFragment extends BaseFragment implements OnItemClickListener, OnItemAdd {


    Unbinder unbinder;


    ChatsAdapter adapter;
    ArrayList<SpinnerModel> arrData;
    @BindView(R.id.btnBack)
    TextView btnBack;
    @BindView(R.id.imgProfile)
    CircleImageView imgProfile;
    @BindView(R.id.txtName)
    AnyTextView txtName;
    @BindView(R.id.txtTime)
    AnyTextView txtTime;
    @BindView(R.id.containerTitlebar1)
    LinearLayout containerTitlebar1;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.edtMessage)
    AnyEditTextView edtMessage;
    @BindView(R.id.imgCamera)
    ImageView imgCamera;
    @BindView(R.id.imgAttachment)
    ImageView imgAttachment;


    public static ChatsFragment newInstance() {

        Bundle args = new Bundle();

        ChatsFragment fragment = new ChatsFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public int getDrawerLockMode() {
        return 0;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_chat;
    }

    @Override
    public void setTitlebar(TitleBar titleBar) {

        titleBar.resetViews();
        titleBar.setVisibility(View.GONE);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        arrData = new ArrayList<>();
        adapter = new ChatsAdapter(getContext(), arrData, this);
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
        arrData.addAll(Constants.getAddDependentsArray2());
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

    @OnClick({R.id.btnBack, R.id.imgCamera, R.id.imgAttachment})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnBack:
                getBaseActivity().onBackPressed();
                break;
            case R.id.imgCamera:
                UIHelper.cropImagePicker(getContext(), ChatsFragment.this);
                break;
            case R.id.imgAttachment:
                showNextBuildToast();
                break;
        }
    }
}
