package com.example.madooding.healthpy.model;

import java.io.Serializable;

/**
 * Created by madooding on 10/27/2016 AD.
 */
public class FoodsCategory implements Serializable{
    private String name, imgUrl, linker;
    private int imgSrc;

    public FoodsCategory(String name, int imgSrc){
        this.name = name;
        this.imgSrc = imgSrc;
    }

    public FoodsCategory(String name, String imgUrl, String linker){
        this.name = name;
        this.imgUrl = imgUrl;
        this.linker = linker;
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

    public String getImgUrl(){ return imgUrl; }

    public String getLinker(){ return linker; }
}
