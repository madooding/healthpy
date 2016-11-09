package com.example.madooding.healthpy.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.madooding.healthpy.PersonalInfoFormFragment;

/**
 * Created by madooding on 11/10/2016 AD.
 */

public class InformationGatheringViewPagerAdapter extends FragmentPagerAdapter {

    public InformationGatheringViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return new PersonalInfoFormFragment();
    }

    @Override
    public int getCount() {
        return 1;
    }
}
