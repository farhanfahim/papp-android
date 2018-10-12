package edu.aku.ehs.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import edu.aku.ehs.fragments.OtherHistoryAssessmentFragment;
import edu.aku.ehs.fragments.MedicalHistoryAssessmentFragment;
import edu.aku.ehs.models.SessionDetailModel;


public class EmployeeAssessmentPagerAdapter extends FragmentStatePagerAdapter {

    private SessionDetailModel sessionDetailModel;

    public EmployeeAssessmentPagerAdapter(FragmentManager fm, SessionDetailModel sessionDetailModel) {
        super(fm);
        this.sessionDetailModel = sessionDetailModel;
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
//        return ForgotPasswordFragment.newInstance();
        switch (position) {
            case 0:
                return MedicalHistoryAssessmentFragment.newInstance(sessionDetailModel);
            case 1:
                return OtherHistoryAssessmentFragment.newInstance(sessionDetailModel);
            default:
                return MedicalHistoryAssessmentFragment.newInstance(sessionDetailModel);
        }

    }


    @Override
    public int getCount() {
        return 2;
    }

}