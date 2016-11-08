package com.example.madooding.healthpy.model;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by madooding on 10/31/2016 AD.
 */
public class FoodListItem implements Serializable {
    private String id;
    private int imageSrc;
    private String name;
    private String description;
    private int calories;
    private HashMap<String, Float> nutrition;

    public FoodListItem(int imageSrc, String name, String description, int calories, HashMap<String, Float> nutrition){
        this.setImageSrc(imageSrc);
        this.setName(name);
        this.setDescription(description);
        this.setCalories(calories);
        this.nutrition = nutrition;
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

    public HashMap<String, Float> getNutrition(){
        return nutrition;
    }
}
