package edu.aku.family_hifazat.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.gdacciaro.iOSDialog.iOSDialog;
import com.gdacciaro.iOSDialog.iOSDialogBuilder;
import com.gdacciaro.iOSDialog.iOSDialogClickListener;

import edu.aku.family_hifazat.callbacks.OnNewPacketReceivedListener;
import edu.aku.family_hifazat.widget.AnyTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import edu.aku.family_hifazat.R;
import edu.aku.family_hifazat.activities.HomeActivity;
import edu.aku.family_hifazat.activities.MainActivity;
import edu.aku.family_hifazat.fragments.abstracts.BaseFragment;
import edu.aku.family_hifazat.fragments.abstracts.GenericContentFragment;
import edu.aku.family_hifazat.fragments.abstracts.GenericDialogFragment;
import edu.aku.family_hifazat.widget.TitleBar;

import static edu.aku.family_hifazat.constatnts.AppConstants.AboutUs;

/**
 * Created by khanhamza on 09-May-17.
 */

public class LeftSideMenuFragment extends BaseFragment implements OnNewPacketReceivedListener {

    Unbinder unbinder;
    @BindView(R.id.txtHome)
    AnyTextView txtHome;
    @BindView(R.id.txtCardSubscription)
    AnyTextView txtCardSubscription;
    @BindView(R.id.txtAbout)
    AnyTextView txtAbout;
    @BindView(R.id.txtPreferences)
    AnyTextView txtPreferences;
    @BindView(R.id.txtLogout)
    AnyTextView txtLogout;
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    @BindView(R.id.imgBackground)
    ImageView imgBackground;
    String b = " \"<H2>Milk</H2><Br /><P>Jul 26, 2017 11:49</P>\",";

    public static LeftSideMenuFragment newInstance() {

        Bundle args = new Bundle();

        LeftSideMenuFragment fragment = new LeftSideMenuFragment();
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

        ////        scrollToTop();
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

    public static void logoutClick(final BaseFragment baseFragment) {
//        final GenericDialogFragment genericDialogFragment = GenericDialogFragment.newInstance();
//
//        genericDialogFragment.setTitle(baseFragment.getString(R.string.logout));
//        genericDialogFragment.setMessage(baseFragment.getString(R.string.areYouSureToLogout));
//
//        genericDialogFragment.setButton1("Yes", () -> {
//            genericDialogFragment.getDialog().dismiss();
//            baseFragment.sharedPreferenceManager.clearDB();
//            baseFragment.getBaseActivity().clearAllActivitiesExceptThis(MainActivity.class);
//
//        });
//
//        genericDialogFragment.setButton2("No", () -> genericDialogFragment.getDialog().dismiss());
//        genericDialogFragment.show(baseFragment.getFragmentManager(), null);

        Context context = baseFragment.getContext();

        new iOSDialogBuilder(context)
                .setTitle(context.getString(R.string.logout))
                .setSubtitle(context.getString(R.string.areYouSureToLogout))
                .setBoldPositiveLabel(false)
                .setCancelable(false)
                .setPositiveListener(context.getString(R.string.yes), dialog -> {
                    dialog.dismiss();
                    baseFragment.sharedPreferenceManager.clearDB();
                    baseFragment.getBaseActivity().clearAllActivitiesExceptThis(MainActivity.class);
                })
                .setNegativeListener(context.getString(R.string.no), dialog -> dialog.dismiss())
                .build().show();


    }

    public void scrollToTop() {
        scrollView.post(new Runnable() {
            public void run() {
                scrollView.scrollTo(0, 0);
//                scrollView.fullScroll(View.FOCUS_UP);
            }
        });
    }


    @OnClick({R.id.txtHome, R.id.txtCardSubscription, R.id.txtAbout, R.id.txtLogout, R.id.txtPreferences})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.txtHome:
                if (getActivity() instanceof HomeActivity) {
                    getBaseActivity().reload();
                } else {
                    getBaseActivity().clearAllActivitiesExceptThis(HomeActivity.class);
                }
                break;

            case R.id.txtCardSubscription:
//                getBaseActivity().addDockableFragment(CardSubscriptionFragment.newInstance());
                showNextBuildToast();
                break;

            case R.id.txtAbout:
                getBaseActivity().addDockableFragment(GenericContentFragment.newInstance("About", AboutUs), false);
                break;
            case R.id.txtLogout:
                logoutClick(this);
                break;
            case R.id.txtPreferences:
                getBaseActivity().addDockableFragment(SettingsFragment.newInstance(), false);
                break;
        }
    }


}
