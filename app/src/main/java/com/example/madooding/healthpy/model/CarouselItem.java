package com.example.madooding.healthpy.model;

import com.example.madooding.healthpy.CarouselFragment;

import java.util.List;

/**
 * Created by madooding on 10/30/2016 AD.
 */
public class CarouselItem {
    private int imgResource;
    private String name;
    private CarouselFragment Carousel;
    private List<String> foodObjectIds;
    private String imgUrl;

    public CarouselItem(int imgResource, String name){
        this.imgResource = imgResource;
        this.name = name;

    }

    public CarouselItem(String imgUrl, String name, List<String> foodObjectIds){
        this.imgUrl = imgUrl;
        this.foodObjectIds = foodObjectIds;
        this.name = name;

    }

    public int getImgResource(){
        return this.imgResource;
    }

    public String getImgUrl(){
        return imgUrl;
    }

    public List<String> getFoodObjectIds(){
        return foodObjectIds;
    }

    public String getName(){return this.name;}

    public CarouselFragment getCarouselFragment(){
        return CarouselFragment.newInstance(this.imgUrl, this.name);
    }

}
