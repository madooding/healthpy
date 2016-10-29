package com.example.madooding.healthpy.model;

import com.example.madooding.healthpy.CarouselFragment;

/**
 * Created by madooding on 10/30/2016 AD.
 */
public class CarouselItem {
    private int imgResource;
    private CarouselFragment Carousel;

    public CarouselItem(int imgResource){
        this.imgResource= imgResource;

    }

    public int getImgResource(){
        return this.imgResource;
    }

    public CarouselFragment getCarouselFragment(){
        return CarouselFragment.newInstance(this.imgResource);
    }

}
