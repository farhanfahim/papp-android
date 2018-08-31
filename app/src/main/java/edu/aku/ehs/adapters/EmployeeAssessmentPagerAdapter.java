package edu.aku.ehs.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import edu.aku.ehs.fragments.FamilyHistoryAssessmentFragment;
import edu.aku.ehs.fragments.MedicalHistoryAssessmentFragment;
import edu.aku.ehs.fragments.PsychosocialHistoryAssessmentFragment;
import edu.aku.ehs.fragments.SmokingBehaviorAssessmentFragment;


public class EmployeeAssessmentPagerAdapter extends FragmentStatePagerAdapter {



    public EmployeeAssessmentPagerAdapter(FragmentManager fm) {
        super(fm);
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
                return MedicalHistoryAssessmentFragment.newInstance();
            case 1:
                return FamilyHistoryAssessmentFragment.newInstance();
            case 2:
                return PsychosocialHistoryAssessmentFragment.newInstance();
            case 3:
                return SmokingBehaviorAssessmentFragment.newInstance();
            default:
                return MedicalHistoryAssessmentFragment.newInstance();
        }

    }


    @Override
    public int getCount() {
        return 4;
    }

}