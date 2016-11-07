package com.example.madooding.healthpy.model;

import java.util.HashMap;

/**
 * Created by madooding on 10/31/2016 AD.
 */
public class FoodListItem {
    private int imageSrc;
    private String name;
    private String description;
    private int calories;
    private HashMap<String, Integer> nutrition;

    public FoodListItem(int imageSrc, String name, String description, int calories){
        this.setImageSrc(imageSrc);
        this.setName(name);
        this.setDescription(description);
        this.setCalories(calories);
        //this.nutrition = nutrition;
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

    public HashMap<String, Integer> getNutrition(){
        return nutrition;
    }
}
