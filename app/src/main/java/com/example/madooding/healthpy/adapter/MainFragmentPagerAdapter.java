package com.example.madooding.healthpy.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.madooding.healthpy.CalendarFragment;
import com.example.madooding.healthpy.MainFragment;
import com.example.madooding.healthpy.TodaySummaryFragment;

/**
 * Created by madooding on 10/25/2016 AD.
 */
public class MainFragmentPagerAdapter extends FragmentPagerAdapter {


    public MainFragmentPagerAdapter(FragmentManager fm) {
        super(fm);

    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return new CalendarFragment();
            case 1: return new MainFragment();
            case 2: return new TodaySummaryFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
