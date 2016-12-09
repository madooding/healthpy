package com.example.madooding.healthpy.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.madooding.healthpy.CarouselFragment;
import com.example.madooding.healthpy.model.CarouselItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by madooding on 10/27/2016 AD.
 */
public class CarouselViewPagerAdapter extends FragmentPagerAdapter {

    List<CarouselItem> carouselItemList;

    public CarouselViewPagerAdapter(FragmentManager fm, List<CarouselItem> carouselItemList)
    {
        super(fm);
        this.carouselItemList = carouselItemList;
    }

    @Override
    public Fragment getItem(int position) {
        return carouselItemList.get(position).getCarouselFragment();
    }

    @Override
    public int getCount() {
        return carouselItemList.size();
    }
}
