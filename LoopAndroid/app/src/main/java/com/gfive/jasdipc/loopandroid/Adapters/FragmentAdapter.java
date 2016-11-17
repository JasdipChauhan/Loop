package com.gfive.jasdipc.loopandroid.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.gfive.jasdipc.loopandroid.Fragments.AllRidesFragment;

/**
 * Created by JasdipC on 2016-11-16.
 */

public class FragmentAdapter extends FragmentPagerAdapter {

    final static private int NUM_TABS = 3;

    public FragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return AllRidesFragment.newInstance();
    }

    @Override
    public int getCount() {
        return NUM_TABS;
    }
}
