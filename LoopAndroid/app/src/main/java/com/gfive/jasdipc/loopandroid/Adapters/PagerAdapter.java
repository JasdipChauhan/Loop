package com.gfive.jasdipc.loopandroid.Adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.gfive.jasdipc.loopandroid.Fragments.Tabs.AllRidesFragment;
import com.gfive.jasdipc.loopandroid.Fragments.Tabs.MyRidesFragment;
import com.gfive.jasdipc.loopandroid.R;

/**
 * Created by JasdipC on 2016-11-16.
 */

public class PagerAdapter extends FragmentPagerAdapter {

    final static private int NUM_TABS = 3;

    final static public int DRIVER_RIDES = 0;
    final static public int ALL_RIDES = 1;
    final static public int MY_RIDES = 2;

    private Context mContext;

    public PagerAdapter(FragmentManager fm, Context context) {
        super(fm);

        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {

        //TODO: CREATE AND HOOKUP OTHER FRAGMENTS
        switch (position) {

            case DRIVER_RIDES:
                return AllRidesFragment.newInstance();
            case ALL_RIDES:
                return AllRidesFragment.newInstance();
            case MY_RIDES:
                return MyRidesFragment.newInstance();

            default:

            return null;
        }
    }

    @Override
    public int getCount() {
        return NUM_TABS;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        //TODO: CREATE AND HOOKUP OTHER FRAGMENTS
        switch (position) {

            case DRIVER_RIDES:
                return mContext.getString(R.string.driver_rides_page_title);
            case ALL_RIDES:
                return mContext.getString(R.string.all_rides_page_title);
            case MY_RIDES:
                return mContext.getString(R.string.my_rides_page_title);

            default:

                return null;
        }
    }
}
