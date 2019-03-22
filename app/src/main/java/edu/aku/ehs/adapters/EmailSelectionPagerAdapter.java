package edu.aku.ehs.adapters;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import java.util.ArrayList;

import edu.aku.ehs.enums.EmailSortType;
import edu.aku.ehs.fragments.EmailSelectionFragment;
import edu.aku.ehs.fragments.EmailSelectionPagerFragment;
import edu.aku.ehs.managers.retrofit.GsonFactory;
import edu.aku.ehs.models.SessionDetailModel;
import edu.aku.ehs.models.SessionModel;


public class EmailSelectionPagerAdapter extends FragmentStatePagerAdapter {
    private final ArrayList<SessionDetailModel> arrData;
    private SessionModel sessionModel;
    private String jsonData;

    public EmailSelectionPagerAdapter(FragmentManager fm, ArrayList<SessionDetailModel> arrData, SessionModel sessionModel) {
        super(fm);
        this.sessionModel = sessionModel;
        this.arrData = arrData;
        jsonData = GsonFactory.getSimpleGson().toJson(arrData);

    }

    // CURRENT FRAGMENT
    private SparseArray<Fragment> registeredFragments = new SparseArray<>();

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        registeredFragments.put(position, fragment);
        return fragment;
    }


    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return EmailSelectionFragment.newInstance(sessionModel, jsonData, EmailSortType.ALL);
            case 1:
                return EmailSelectionFragment.newInstance(sessionModel, jsonData, EmailSortType.INCOMPLETE);
            case 2:
                return EmailSelectionFragment.newInstance(sessionModel, jsonData, EmailSortType.COMPLETED);
            default:
                return EmailSelectionFragment.newInstance(sessionModel, jsonData, EmailSortType.ALL);

        }
    }


    @Override
    public int getCount() {
        return 3;
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "All";

            case 1:
                return "Incomplete";

            case 2:
                return "Completed";

            default:
                return "All";

        }
    }
}