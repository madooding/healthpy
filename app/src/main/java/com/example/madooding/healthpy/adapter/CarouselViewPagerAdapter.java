package com.example.madooding.healthpy.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.madooding.healthpy.Carousel_1;
import com.example.madooding.healthpy.Carousel_2;
import com.example.madooding.healthpy.Carousel_3;

/**
 * Created by madooding on 10/27/2016 AD.
 */
public class CarouselViewPagerAdapter extends FragmentPagerAdapter {

    public CarouselViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment[] carouselFragments = {new Carousel_1(), new Carousel_2(), new Carousel_3()};

        return carouselFragments[position];
    }

    @Override
    public int getCount() {
        return 3;
    }
}
