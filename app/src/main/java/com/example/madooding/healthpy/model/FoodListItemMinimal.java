package com.example.madooding.healthpy.model;

import org.json.JSONObject;

import java.util.Date;

/**
 * Created by madooding on 12/6/2016 AD.
 */

public class FoodListItemMinimal{
    private String objectId;
    private Date eatenDate;
    private String name;
    private int calories;
    private String userObjectId;

    public FoodListItemMinimal(String objectId, String userObjectId, String name, int calories, Date eatenDate){
        setName(name);
        setCalories(calories);
        setEatenDate(eatenDate);
        setObjectId(objectId);
        setUserObjectId(userObjectId);
    }


    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public Date getEatenDate() {
        return eatenDate;
    }

    public void setEatenDate(Date eatenDate) {
        this.eatenDate = eatenDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public String getUserObjectId() {
        return userObjectId;
    }

    public void setUserObjectId(String userObjectId) {
        this.userObjectId = userObjectId;
    }
}
