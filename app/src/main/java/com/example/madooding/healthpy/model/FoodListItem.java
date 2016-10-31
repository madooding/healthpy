package com.example.madooding.healthpy.model;

/**
 * Created by madooding on 10/31/2016 AD.
 */
public class FoodListItem {
    private int imageSrc;
    private String name;
    private String description;
    private int calories;

    public FoodListItem(int imageSrc, String name, String description, int calories){
        this.setImageSrc(imageSrc);
        this.setName(name);
        this.setDescription(description);
        this.setCalories(calories);
    }


    public int getImageSrc() {
        return imageSrc;
    }

    public void setImageSrc(int imageSrc) {
        this.imageSrc = imageSrc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }
}
