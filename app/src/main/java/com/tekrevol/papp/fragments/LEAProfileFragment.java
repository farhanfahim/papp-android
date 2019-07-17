package com.tekrevol.papp.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jcminarro.roundkornerlayout.RoundKornerLinearLayout;
import com.tekrevol.papp.R;
import com.tekrevol.papp.adapters.recyleradapters.MedalAdapter;
import com.tekrevol.papp.adapters.recyleradapters.SpecialityAdapter;
import com.tekrevol.papp.callbacks.OnItemClickListener;
import com.tekrevol.papp.constatnts.Constants;
import com.tekrevol.papp.fragments.abstracts.BaseFragment;
import com.tekrevol.papp.helperclasses.GooglePlaceHelper;
import com.tekrevol.papp.helperclasses.StringHelper;
import com.tekrevol.papp.helperclasses.ui.helper.UIHelper;
import com.tekrevol.papp.libraries.imageloader.ImageLoaderHelper;
import com.tekrevol.papp.libraries.residemenu.ResideMenu;
import com.tekrevol.papp.models.general.SpinnerModel;
import com.tekrevol.papp.models.receiving_model.UserModel;
import com.tekrevol.papp.widget.AnyTextView;
import com.tekrevol.papp.widget.TitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import co.chatsdk.core.dao.Keys;
import co.chatsdk.core.dao.Thread;
import co.chatsdk.core.dao.User;
import co.chatsdk.core.interfaces.ThreadType;
import co.chatsdk.core.session.ChatSDK;
import co.chatsdk.core.types.ConnectionType;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.CompletableObserver;
import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class LEAProfileFragment extends BaseFragment implements OnItemClickListener {


    Unbinder unbinder;


    SpecialityAdapter specialityAdapter;
    ArrayList<SpinnerModel> arrSpecialization;

    MedalAdapter medalAdapter;
    ArrayList<SpinnerModel> arrMedals;
    @BindView(R.id.btnLeft1)
    TextView btnLeft1;
    @BindView(R.id.txtTitle)
    AnyTextView txtTitle;
    @BindView(R.id.btnRight1)
    ImageView btnRight1;
    @BindView(R.id.containerTitlebar1)
    LinearLayout containerTitlebar1;
    @BindView(R.id.imgProfile)
    CircleImageView imgProfile;
    @BindView(R.id.imgEdit)
    ImageView imgEdit;
    @BindView(R.id.txtEdit)
    AnyTextView txtEdit;
    @BindView(R.id.txtName)
    AnyTextView txtName;
    @BindView(R.id.txtDesignation)
    AnyTextView txtDesignation;
    @BindView(R.id.txtLocation)
    AnyTextView txtLocation;
    @BindView(R.id.imgChat)
    ImageView imgChat;
    @BindView(R.id.contChat)
    LinearLayout contChat;
    @BindView(R.id.rvMilestones)
    RecyclerView rvMilestones;
    @BindView(R.id.contMilestones)
    RoundKornerLinearLayout contMilestones;
    @BindView(R.id.txtAgency)
    AnyTextView txtAgency;
    @BindView(R.id.txtDepartment)
    AnyTextView txtDepartment;
    @BindView(R.id.txtPoints)
    AnyTextView txtPoints;
    @BindView(R.id.contPointsEarned)
    LinearLayout contPointsEarned;
    @BindView(R.id.ratingbar)
    AppCompatRatingBar ratingbar;
    @BindView(R.id.txtReviews)
    AnyTextView txtReviews;
    @BindView(R.id.contReviews)
    LinearLayout contReviews;
    @BindView(R.id.rvSpecialization)
    RecyclerView rvSpecialization;
    @BindView(R.id.contEditPersonalInfo)
    LinearLayout contEditPersonalInfo;
    @BindView(R.id.webViewInfo)
    WebView webViewInfo;
    @BindView(R.id.txtScheduleMeeting)
    AnyTextView txtScheduleMeeting;


    private UserModel mentorModel;


    public static LEAProfileFragment newInstance(UserModel mentorModel) {

        Bundle args = new Bundle();

        LEAProfileFragment fragment = new LEAProfileFragment();
        fragment.mentorModel = mentorModel;
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        arrSpecialization = new ArrayList<>();
        specialityAdapter = new SpecialityAdapter(getContext(), arrSpecialization, this, false);


        arrMedals = new ArrayList<>();
        medalAdapter = new MedalAdapter(getContext(), arrMedals, this);

    }

    @Override
    public int getDrawerLockMode() {
        return DrawerLayout.LOCK_MODE_LOCKED_CLOSED;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_lea_profile;
    }

    @Override
    public void setTitlebar(TitleBar titleBar) {
        titleBar.resetViews();
        titleBar.setVisibility(View.GONE);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        if (isMentor()) {
            mentorModel = getCurrentUser();
        }
        bindRecyclerView();
        webViewInfo.getSettings().setJavaScriptEnabled(true);
        if (StringHelper.isNullOrEmpty(getCurrentUser().getUserDetails().getAbout())) {
            webViewInfo.loadData("No Info Available", "text", "UTF-8");
        } else {
            webViewInfo.loadData(getCurrentUser().getUserDetails().getAbout(), "text/html", "UTF-8");
        }


        bindData();




    }

    private void bindData() {
        if (isMentor()) {
            txtEdit.setVisibility(View.VISIBLE);
            imgEdit.setVisibility(View.VISIBLE);
            btnRight1.setVisibility(View.GONE);
            txtScheduleMeeting.setVisibility(View.GONE);
            contChat.setVisibility(View.GONE);
            txtTitle.setText("My Profile");
            contPointsEarned.setVisibility(View.VISIBLE);
        } else if (isDependent()) {
            txtScheduleMeeting.setVisibility(View.GONE);
            btnRight1.setVisibility(View.VISIBLE);
            txtEdit.setVisibility(View.GONE);
            imgEdit.setVisibility(View.GONE);
            txtTitle.setText("Mentor Profile");
            contPointsEarned.setVisibility(View.GONE);


            contChat.setVisibility(View.VISIBLE);
            if (mentorModel.getChatEnabled()) {
                contChat.setVisibility(View.VISIBLE);
            } else {
                contChat.setVisibility(View.GONE);
            }

        } else {
            txtScheduleMeeting.setVisibility(View.VISIBLE);
            btnRight1.setVisibility(View.VISIBLE);
            txtEdit.setVisibility(View.GONE);
            imgEdit.setVisibility(View.GONE);
            txtTitle.setText("Mentor Profile");
            contPointsEarned.setVisibility(View.GONE);


            if (mentorModel.getChatEnabled()) {
                contChat.setVisibility(View.VISIBLE);
            } else {
                contChat.setVisibility(View.GONE);
            }
        }


        txtName.setText(mentorModel.getUserDetails().getFullName());
        txtDesignation.setText(mentorModel.getUserDetails().getDesignation());
        txtLocation.setText(mentorModel.getUserDetails().getAddress());
        ImageLoaderHelper.loadImageWithAnimationsByPath(imgProfile, mentorModel.getUserDetails().getImage(), true);
        txtAgency.setText(mentorModel.getUserDetails().getAgency());
        txtDepartment.setText(getHomeActivity().sparseArrayDepartments.get(mentorModel.getUserDetails().getDepartmentId(), ""));

        txtPoints.setText(mentorModel.getUserDetails().getTotalPoints() + " pts");
        ratingbar.setRating((float) mentorModel.getUserDetails().getAvgRating());
        txtReviews.setText("(" + mentorModel.getUserDetails().getReview_count() + ")");
        arrSpecialization.clear();

        if (mentorModel.getSpecializations() != null && !mentorModel.getSpecializations().isEmpty()) {
            arrSpecialization.addAll(mentorModel.getSpecializations());
        }

        arrMedals.clear();
        arrMedals.addAll(Constants.getMedalURL());


        specialityAdapter.notifyDataSetChanged();
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
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rvSpecialization.setLayoutManager(mLayoutManager);
        ((DefaultItemAnimator) rvSpecialization.getItemAnimator()).setSupportsChangeAnimations(false);
        rvSpecialization.setAdapter(specialityAdapter);


        RecyclerView.LayoutManager mLayoutManager2 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rvMilestones.setLayoutManager(mLayoutManager2);
        ((DefaultItemAnimator) rvMilestones.getItemAnimator()).setSupportsChangeAnimations(false);
        rvMilestones.setAdapter(medalAdapter);

    }


    @Override
    public void onItemClick(int position, Object object, View view, Object type) {

    }

    @OnClick({R.id.btnLeft1, R.id.txtScheduleMeeting, R.id.contChat, R.id.btnRight1, R.id.contMilestones, R.id.contReviews, R.id.txtEdit, R.id.txtLocation, R.id.contEditPersonalInfo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.txtScheduleMeeting:
                getBaseActivity().addDockableFragment(ScheduleAMeetingFragment.newInstance(mentorModel), true);
                break;
            case R.id.btnLeft1:
                getBaseActivity().onBackPressed();
                break;
            case R.id.btnRight1:
                getResideMenu().openMenu(ResideMenu.DIRECTION_RIGHT);
                break;
            case R.id.contMilestones:
                break;
            case R.id.contChat:
//                getBaseActivity().openActivity(ChatActivity.class);


                final List<User> existingContacts = ChatSDK.contact().contacts();


                for (User existingContact : existingContacts) {
                    if (Integer.valueOf(existingContact.getEntityID()) == mentorModel.getId()) {
                        createThread(existingContact);
                        return;
                    }
                }

                searchUser();
                break;
            case R.id.contReviews:
//                if (isMentor() || isDependent()) {
//                    getBaseActivity().addDockableFragment(ReviewsFragment.newInstance(mentorModel, false), true);
//                } else {
//                    getBaseActivity().addDockableFragment(ReviewsFragment.newInstance(mentorModel, mentorModel.getReviewEnabled()), true);
//                }

                getBaseActivity().addDockableFragment(ReviewsFragment.newInstance(mentorModel, false), true);

                break;
            case R.id.txtEdit:
                getBaseActivity().addDockableFragment(EditLeaProfileFragment.newInstance(), false);
                break;
            case R.id.txtLocation:
                if (isMentor()) {
                    if (!StringHelper.isNullOrEmpty(getCurrentUser().getUserDetails().getAddress())) {
                        GooglePlaceHelper.intentOpenMap(getBaseActivity(), getCurrentUser().getUserDetails().getLat(), getCurrentUser().getUserDetails().getLng(), getCurrentUser().getUserDetails().getAddress());
                    }
                } else {
                    if (!StringHelper.isNullOrEmpty(mentorModel.getUserDetails().getAddress())) {
                        GooglePlaceHelper.intentOpenMap(getBaseActivity(), mentorModel.getUserDetails().getLat(), mentorModel.getUserDetails().getLng(), mentorModel.getUserDetails().getAddress());
                    }
                }

                break;


            case R.id.contEditPersonalInfo:
                getBaseActivity().addDockableFragment(EditPersonalInfoFragment.newInstance(), false);
                break;
        }
    }

    private void searchUser() {
        ChatSDK.search().usersForIndex(mentorModel.getEmail(), Keys.Email).subscribe(new Observer<User>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(User user) {

                addContactAndCreateThread(user);


            }

            @Override
            public void onError(Throwable e) {
                UIHelper.showToast(getContext(), e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void addContactAndCreateThread(User user) {
        Log.d(TAG, "onNext: " + user.toString());

        ChatSDK.contact().addContact(user, ConnectionType.Contact).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onComplete() {
                createThread(user);
            }

            @Override
            public void onError(Throwable e) {

            }
        });

    }

    private void createThread(User user) {
        Log.d(TAG, "onNext: " + user.toString());
        List<User> userList = new ArrayList<>();
        userList.add(ChatSDK.currentUser());
        userList.add(user);
//                        getCurrentUser().getId() + ":" + mentorModel.getId()
//        mentorModel.getUserDetails().getFullName()
        ChatSDK.thread()
                .createThread("", userList, ThreadType.Private1to1, null)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Thread>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.e("abc", "onSubscribe");
                    }

                    @Override
                    public void onSuccess(Thread thread) {
                        Log.e("abc", "onSuccess");


                        ChatSDK.ui().startChatActivityForID(getContext(), thread.getEntityID());

//                                        ChatSDK.thread().sendMessageWithText("1010", thread)
//                                                .observeOn(AndroidSchedulers.mainThread())
//                                                .subscribe(new Observer<MessageSendProgress>() {
//                                                    @Override
//                                                    public void onSubscribe(Disposable d) {
//
//                                                    }
//
//                                                    @Override
//                                                    public void onNext(MessageSendProgress messageSendProgress) {
//
//                                                    }
//
//                                                    @Override
//                                                    public void onError(Throwable e) {
//
//                                                    }
//
//                                                    @Override
//                                                    public void onComplete() {
//
//                                                    }
//                                                });

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("abc", "onError");

                    }
                });
    }
}
