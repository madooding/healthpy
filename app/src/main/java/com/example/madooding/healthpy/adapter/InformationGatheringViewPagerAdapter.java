package com.example.madooding.healthpy.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.madooding.healthpy.AreYouVegetarianFormFragment;
import com.example.madooding.healthpy.KindOfAnimalCantEatFormFragment;
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
        switch(position){
            case 0: return new PersonalInfoFormFragment();
            case 1: return new AreYouVegetarianFormFragment();
            case 2: return new KindOfAnimalCantEatFormFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
