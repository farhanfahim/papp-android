package com.tekrevol.papp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.google.gson.reflect.TypeToken;
import com.tekrevol.papp.R;
import com.tekrevol.papp.callbacks.OnNewPacketReceivedListener;
import com.tekrevol.papp.constatnts.AppConstants;
import com.tekrevol.papp.constatnts.WebServiceConstants;
import com.tekrevol.papp.fragments.abstracts.BaseFragment;
import com.tekrevol.papp.fragments.abstracts.GenericContentFragment;
import com.tekrevol.papp.helperclasses.ui.helper.UIHelper;
import com.tekrevol.papp.libraries.imageloader.ImageLoaderHelper;
import com.tekrevol.papp.managers.retrofit.GsonFactory;
import com.tekrevol.papp.managers.retrofit.WebServices;
import com.tekrevol.papp.models.receiving_model.PagesModel;
import com.tekrevol.papp.models.receiving_model.TaskReceivingModel;
import com.tekrevol.papp.models.wrappers.WebResponse;
import com.tekrevol.papp.widget.AnyTextView;
import com.tekrevol.papp.widget.TitleBar;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.tekrevol.papp.constatnts.AppConstants.PAGE_SLUG_ABOUT;
import static com.tekrevol.papp.constatnts.AppConstants.PAGE_SLUG_TERMS_AND_CONDITION;
import static com.tekrevol.papp.constatnts.WebServiceConstants.Q_PARAM_SLUG;

/**
 * Created by khanhamza on 09-May-17.
 */

public class RightSideMenuFragment extends BaseFragment implements OnNewPacketReceivedListener {

    Unbinder unbinder;
    @BindView(R.id.imgBackground)
    ImageView imgBackground;
    @BindView(R.id.imgBack)
    ImageView imgBack;
    @BindView(R.id.txtUserName)
    AnyTextView txtUserName;
    @BindView(R.id.txtEmail)
    AnyTextView txtEmail;
    @BindView(R.id.contNofitications)
    LinearLayout contNofitications;
    @BindView(R.id.contEditProfile)
    LinearLayout contEditProfile;
    @BindView(R.id.contMyProfile)
    LinearLayout contMyProfile;
    @BindView(R.id.conSessionHistory)
    LinearLayout conSessionHistory;
    @BindView(R.id.contSessionPayoutHistory)
    LinearLayout contSessionPayoutHistory;
    @BindView(R.id.contMyGifts)
    LinearLayout contMyGifts;
    @BindView(R.id.contGiftsAndRewards)
    LinearLayout contGiftsAndRewards;
    @BindView(R.id.contMilestones)
    LinearLayout contMilestones;
    @BindView(R.id.contTasks)
    LinearLayout contTasks;
    @BindView(R.id.contAboutApp)
    LinearLayout contAboutApp;
    @BindView(R.id.contTermsAndConditions)
    LinearLayout contTermsAndConditions;
    @BindView(R.id.contLogout)
    LinearLayout contLogout;
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    @BindView(R.id.imgProfile)
    CircleImageView imgProfile;
    @BindView(R.id.contSponsors)
    LinearLayout contSponsors;


    PagesModel about;
    PagesModel termsAndCondition;


    public static RightSideMenuFragment newInstance() {

        Bundle args = new Bundle();

        RightSideMenuFragment fragment = new RightSideMenuFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_sidebar;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (sharedPreferenceManager.isMentor()) {
            contEditProfile.setVisibility(View.GONE);
            contMyProfile.setVisibility(View.VISIBLE);
            contSessionPayoutHistory.setVisibility(View.VISIBLE);
        } else {
            contEditProfile.setVisibility(View.VISIBLE);
            contMyProfile.setVisibility(View.GONE);
            contSessionPayoutHistory.setVisibility(View.GONE);
        }
        ////        scrollToTop();


        if (getCurrentUser() != null) {
            ImageLoaderHelper.loadImageWithAnimationsByPath(imgProfile, getCurrentUser().getUserDetails().getImage(), true);
            txtUserName.setText(getCurrentUser().getUserDetails().getFullName());
            txtEmail.setText(getCurrentUser().getEmail());
        }


    }

    @Override
    public void setTitlebar(TitleBar titleBar) {

    }

    @Override
    public void setListeners() {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public int getDrawerLockMode() {
        return DrawerLayout.LOCK_MODE_UNDEFINED;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }

//    public void scrollToTop() {
//        scrollView.post(new Runnable() {
//            public void run() {
//                scrollView.scrollTo(0, 0);
////                scrollView.fullScroll(View.FOCUS_UP);
//            }
//        });
//    }


    @OnClick({R.id.imgBack, R.id.contNofitications, R.id.contEditProfile, R.id.contMyProfile, R.id.conSessionHistory, R.id.contSessionPayoutHistory, R.id.contMyGifts, R.id.contGiftsAndRewards, R.id.contMilestones, R.id.contTasks, R.id.contSponsors, R.id.contAboutApp, R.id.contTermsAndConditions, R.id.contLogout})
    public void onViewClicked(View view) {

        closeMenu();

        switch (view.getId()) {
            case R.id.imgBack:
                break;
            case R.id.contNofitications:
                getBaseActivity().addDockableFragment(NotificationsFragment.newInstance(), false);
                break;
            case R.id.contEditProfile:
                getBaseActivity().addDockableFragment(EditCivilianProfileFragment.newInstance(), false);
                break;
            case R.id.contMyProfile:
                getBaseActivity().addDockableFragment(LEAProfileFragment.newInstance(null), false);
                break;
            case R.id.conSessionHistory:
                getBaseActivity().addDockableFragment(SessionHistoryFragment.newInstance(), false);
                break;
            case R.id.contSessionPayoutHistory:
                getBaseActivity().addDockableFragment(SessionPayoutHistoryFragment.newInstance(), false);
                break;
            case R.id.contMyGifts:
                getBaseActivity().addDockableFragment(MyGiftsFragment.newInstance(), false);
                break;
            case R.id.contGiftsAndRewards:
                getBaseActivity().addDockableFragment(GiftAndRewardsFragment.newInstance(), false);
                break;
            case R.id.contMilestones:
                getBaseActivity().addDockableFragment(MilestoneFragment.newInstance(), false);
                break;

            case R.id.contTasks:
                getBaseActivity().addDockableFragment(TasksFragment.newInstance(), false);
                break;
            case R.id.contSponsors:
                getBaseActivity().addDockableFragment(SponsorFragment.newInstance(), false);
                break;
            case R.id.contAboutApp:
                getPagesInfo(PAGE_SLUG_ABOUT);
                break;
            case R.id.contTermsAndConditions:
                getPagesInfo(PAGE_SLUG_TERMS_AND_CONDITION);
                break;
            case R.id.contLogout:
                logoutClick(this);
                break;
        }
    }


    private void getPagesInfo(String slug) {
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put(Q_PARAM_SLUG, slug);


        getBaseWebService().getAPIAnyObject(WebServiceConstants.PATH_PAGES, queryMap, new WebServices.IRequestWebResponseAnyObjectCallBack() {
            @Override
            public void requestDataResponse(WebResponse<Object> webResponse) {


                Type type = new TypeToken<ArrayList<PagesModel>>() {
                }.getType();
                ArrayList<PagesModel> arrayList = GsonFactory.getSimpleGson()
                        .fromJson(GsonFactory.getSimpleGson().toJson(webResponse.result)
                                , type);


                if (arrayList.isEmpty()) {
                    UIHelper.showShortToastInCenter(getContext(), "No Data");
                    return;
                }

                if (slug.equalsIgnoreCase(PAGE_SLUG_TERMS_AND_CONDITION)) {
                    termsAndCondition = arrayList.get(0);
                    getBaseActivity().addDockableFragment(GenericContentFragment.newInstance(termsAndCondition.getTitle(), termsAndCondition.getContent(), true), false);

                } else if (slug.equalsIgnoreCase(PAGE_SLUG_ABOUT)) {
                    about = arrayList.get(0);
                    getBaseActivity().addDockableFragment(GenericContentFragment.newInstance(about.getTitle(), about.getContent(), true), false);
                }

            }

            @Override
            public void onError(Object object) {

            }
        });
    }
}
