package com.tekrevol.papp.fragments;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.android.papp.R;
import com.tekrevol.papp.activities.ChatActivity;
import com.tekrevol.papp.adapters.recyleradapters.ChatListAdapter;
import com.tekrevol.papp.callbacks.OnItemClickListener;
import com.tekrevol.papp.constatnts.AppConstants;
import com.tekrevol.papp.constatnts.Constants;
import com.tekrevol.papp.fragments.abstracts.BaseFragment;
import com.tekrevol.papp.models.general.SpinnerModel;
import com.tekrevol.papp.widget.AnyEditTextView;
import com.tekrevol.papp.widget.AnyTextView;
import com.tekrevol.papp.widget.TitleBar;
import com.github.clans.fab.FloatingActionButton;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.huannguyen.swipeablerv.SWItemDelegate;
import io.huannguyen.swipeablerv.view.SWRecyclerView;

/**
 * Created by hamza.ahmed on 7/19/2018.
 */

public class ChatListsFragment extends BaseFragment implements OnItemClickListener, SWItemDelegate {


    Unbinder unbinder;


    ChatListAdapter adapter;
    ArrayList<SpinnerModel> arrData;

    @BindView(R.id.imgSearch)
    ImageView imgSearch;
    @BindView(R.id.edtSearchBar)
    AnyEditTextView edtSearchBar;
    @BindView(R.id.contSearchBar)
    LinearLayout contSearchBar;
    @BindView(R.id.txtHeading)
    AnyTextView txtHeading;
    @BindView(R.id.recycler_view)
    SWRecyclerView recyclerView;
    @BindView(R.id.empty_view)
    AnyTextView emptyView;
    @BindView(R.id.emptyview_container)
    LinearLayout emptyviewContainer;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.contParent)
    RelativeLayout contParent;
    @BindView(R.id.imgChat)
    ImageView imgChat;
    @BindView(R.id.txtChat)
    AnyTextView txtChat;
    @BindView(R.id.contChat)
    LinearLayout contChat;
    @BindView(R.id.imgSession)
    ImageView imgSession;
    @BindView(R.id.txtSession)
    AnyTextView txtSession;
    @BindView(R.id.contSessions)
    LinearLayout contSessions;
    @BindView(R.id.imgHome)
    ImageView imgHome;
    @BindView(R.id.contBottomBar)
    RelativeLayout contBottomBar;


    public static ChatListsFragment newInstance() {

        Bundle args = new Bundle();

        ChatListsFragment fragment = new ChatListsFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public int getDrawerLockMode() {
        return 0;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_chats_list;
    }

    @Override
    public void setTitlebar(TitleBar titleBar) {

        titleBar.resetViews();
        titleBar.setVisibility(View.VISIBLE);
        titleBar.setTitle("Chats");
        titleBar.showResideMenu(getHomeActivity());
        titleBar.showBackButtonInvisible();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        arrData = new ArrayList<>();
        adapter = new ChatListAdapter(getContext(), arrData, this, this);
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


        fab.setVisibility(View.GONE);

        imgChat.setImageResource(R.drawable.img_chat_selected);
        txtChat.setTextColor(getContext().getResources().getColor(R.color.colorPrimaryDark));


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
        recyclerView.setupSwipeToDismiss(adapter, ItemTouchHelper.LEFT);


        recyclerView.setHasBorder(true);
        recyclerView.setBorderColor(getResources().getColor(R.color.base_dark_gray));
        recyclerView.setRTLSwipeBackground(getResources().getColor(R.color.base_reddish_light));
        recyclerView.setRTLSwipeIcon(BitmapFactory.decodeResource(getContext().getResources(),
                R.drawable.img_delete_with_text));
        recyclerView.setSwipeIconHeight((int) getResources().getDimension(R.dimen.x50dp));
        recyclerView.setSwipeIconWidth((int) getResources().getDimension(R.dimen.x50dp));
        recyclerView.setRTLSwipeIconMargin((int) getResources().getDimension(R.dimen.x5dp));

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

        getBaseActivity().openActivity(ChatActivity.class);

    }


    @OnClick({R.id.contChat, R.id.contSessions, R.id.imgHome})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.contChat:
                break;
            case R.id.contSessions:
                getBaseActivity().popBackStack();
                getBaseActivity().addDockableFragment(ViewSessionFragment.newInstance(), false);
                break;
            case R.id.imgHome:
                getBaseActivity().popBackStack();
                if (sharedPreferenceManager.getBoolean(AppConstants.KEY_IS_MENTOR)) {
                    getBaseActivity().addDockableFragment(DashboardLEAFragment.newInstance(), false);
                } else {
                    getBaseActivity().addDockableFragment(DashboardCivilianFragment.newInstance(), false);
                }
                break;
        }
    }

    @Override
    public Object getItemAtAdapterPosition(int adapterPosition) {
        return arrData.get(adapterPosition);
    }

    @Override
    public void removeItemAtAdapterPosition(int adapterPosition) {
        arrData.remove(adapterPosition);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void addItemWithAdapterPosition(Object item, int adapterPosition) {
        arrData.add(adapterPosition, (SpinnerModel) item);
    }
}
