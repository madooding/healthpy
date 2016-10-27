package com.example.madooding.healthpy.model;

/**
 * Created by madooding on 10/27/2016 AD.
 */
public class FoodsCategory {
    private String name;
    private int imgSrc;


    public FoodsCategory(String name, int imgSrc){
        this.name = name;
        this.imgSrc = imgSrc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(int imgSrc) {
        this.imgSrc = imgSrc;
    }
}
