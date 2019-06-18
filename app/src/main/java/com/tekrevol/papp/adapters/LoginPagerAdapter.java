package com.tekrevol.papp.adapters;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.tekrevol.papp.fragments.LoginDetailFragment;
import com.tekrevol.papp.fragments.LoginDetailFragment;


public class LoginPagerAdapter extends FragmentStatePagerAdapter {



    public LoginPagerAdapter(FragmentManager fm) {
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
                return LoginDetailFragment.newInstance();

            case 1:
                return LoginDetailFragment.newInstance();

            default:
                return LoginDetailFragment.newInstance();
        }
    }


    @Override
    public int getCount() {
        return 1;
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Login";

            case 1:
                return "Login";

            default:
                return "Login";

        }
    }
}