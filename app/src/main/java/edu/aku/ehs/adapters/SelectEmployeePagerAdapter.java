package edu.aku.ehs.adapters;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import edu.aku.ehs.enums.SearchByType;
import edu.aku.ehs.enums.SelectEmployeeActionType;
import edu.aku.ehs.fragments.SelectEmployeeFragment;
import edu.aku.ehs.models.SessionDetailModel;
import edu.aku.ehs.models.SessionModel;
import edu.aku.ehs.models.peoplesoft.department.DEPT;


public class SelectEmployeePagerAdapter extends FragmentStatePagerAdapter {

    private SessionDetailModel sessionDetailModel;
    private String searchKeyword = "";


    private SelectEmployeeActionType selectEmployeeActionType;
    private SessionModel sessionModel;
    private SearchByType searchByType;
    private DEPT deptModel;
    private DEPT division;


    public SelectEmployeePagerAdapter(FragmentManager fm, String searchKeyword, SelectEmployeeActionType selectEmployeeActionType, SearchByType searchByType, SessionModel sessionModel, DEPT dept, DEPT division) {
        super(fm);
        this.searchKeyword = searchKeyword;
        this.selectEmployeeActionType = selectEmployeeActionType;
        this.searchByType = searchByType;
        this.sessionModel = sessionModel;
        this.deptModel = dept;
        this.division = division;
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
                return SelectEmployeeFragment.newInstance(true, searchKeyword, selectEmployeeActionType, searchByType, sessionModel, deptModel, division);
            case 1:
                return SelectEmployeeFragment.newInstance(false, searchKeyword, selectEmployeeActionType, searchByType, sessionModel, deptModel, division);
            default:
                return SelectEmployeeFragment.newInstance(true, searchKeyword, selectEmployeeActionType, searchByType, sessionModel, deptModel, division);
        }
    }


    @Override
    public int getCount() {
        return 2;
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Full Time";

            case 1:
                return "Part Time";

            default:
                return "Full Time";

        }
    }
}