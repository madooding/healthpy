package com.example.madooding.healthpy.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.madooding.healthpy.AreYouVegetarianFormFragment;
import com.example.madooding.healthpy.CongenitalDiseaseFormFragment;
import com.example.madooding.healthpy.KindOfAnimalCantEatFormFragment;
import com.example.madooding.healthpy.PersonalInfoFormFragment;

import java.util.List;

/**
 * Created by madooding on 11/10/2016 AD.
 */

public class InformationGatheringViewPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragmentList;

    public InformationGatheringViewPagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
