package com.example.madooding.healthpy.model;

import com.example.madooding.healthpy.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by madooding on 10/31/2016 AD.
 */
public class FoodListItem implements Serializable {
    private String id;
    private int imageSrc;
    private String imageURL;
    private String name;
    private String description;
    private int calories;
    private HashMap<String, Float> nutrition;


    public FoodListItem(String objectId, String imageURL, String name, String description, int calories, JSONObject nutrition){
        this.id = objectId;
        this.setImageSrc(R.drawable.food_pic_1);
        this.setImageURL(imageURL);
        this.setName(name);
        this.setDescription(description);
        this.setCalories(calories);
        this.nutrition = new HashMap<>();
        String key;
        Iterator<String> keys = nutrition.keys();
        while(keys.hasNext()){
            key = keys.next();
            switch (key){
                case "fat":
                case "carbohydrate":
                case "protein":
                case "cholesterol":
                case "sodium":
                    try {
                        this.nutrition.put(key, (float)nutrition.getDouble(key));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
            }
        }
    }

    public String getObjectId(){ return id; }

    public void setImageURL(String imageURL){
        this.imageURL = imageURL;
    }

    public String getImageUrl(){
        return imageURL;
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
