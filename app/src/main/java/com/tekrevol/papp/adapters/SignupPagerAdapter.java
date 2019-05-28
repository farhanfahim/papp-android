package com.tekrevol.papp.adapters;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.tekrevol.papp.fragments.SignUpCivilianFragment;
import com.tekrevol.papp.fragments.SignUpMentorFragment;
import com.tekrevol.papp.fragments.SignUpCivilianFragment;
import com.tekrevol.papp.fragments.SignUpMentorFragment;


public class SignupPagerAdapter extends FragmentStatePagerAdapter {


    public SignupPagerAdapter(FragmentManager fm, boolean isFromMentor) {
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
        switch (position) {

            case 0:
                return SignUpCivilianFragment.newInstance();

            case 1:
                return SignUpMentorFragment.newInstance();

            default:
                return SignUpCivilianFragment.newInstance();
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
                return "Sign Up as Civilian";

            case 1:
                return "Sign Up as Agent";

            default:
                return "Login as Civilian";

        }
    }
}