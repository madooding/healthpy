package com.example.madooding.healthpy.model;

import com.example.madooding.healthpy.CarouselFragment;

/**
 * Created by madooding on 10/30/2016 AD.
 */
public class CarouselItem {
    private int imgResource;
    private String name;
    private CarouselFragment Carousel;

    public CarouselItem(int imgResource, String name){
        this.imgResource = imgResource;
        this.name = name;

    }

    public int getImgResource(){
        return this.imgResource;
    }

    public String getName(){return this.name;}

    public CarouselFragment getCarouselFragment(){
        return CarouselFragment.newInstance(this.imgResource, this.name);
    }

}
