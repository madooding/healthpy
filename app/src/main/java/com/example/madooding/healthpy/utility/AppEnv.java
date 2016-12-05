package com.example.madooding.healthpy.utility;

import com.example.madooding.healthpy.interfaces.AppEnvSubject;
import com.example.madooding.healthpy.interfaces.Observer;
import com.example.madooding.healthpy.model.FoodListItem;
import com.example.madooding.healthpy.model.FoodListItemMinimal;
import com.example.madooding.healthpy.model.UserData;

import android.text.format.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by madooding on 12/2/2016 AD.
 */

public class AppEnv implements AppEnvSubject{
    private List<Observer> observers;
    private List<FoodListItemMinimal> todayEaten;
    private static AppEnv instance;
    private UserData userData;
    private String[] timePeriod = {"อาหารเช้า", "อาหารกลางวัน", "อาหารเย็น", "อาหารดึก"};
    private String currentPeriod;

    private List<FoodListItem> foodListItems;

    private AppEnv(UserData userData){
        this.observers = new ArrayList<>();
        this.foodListItems = new ArrayList<>();
        this.userData = userData;
        this.currentPeriod = getAppropriateTimePeriod();
        foodListItems = DBUtils.getFoodListByUserData(userData);
        todayEaten = DBUtils.getEatingListByDate(userData.getObjectId(), new Date(System.currentTimeMillis()));
    }

    public static AppEnv newInstance(UserData userData){
        if(instance == null){
            instance = new AppEnv(userData);
        }
        return instance;
    }

    public static AppEnv getInstance(){
        return instance;
    }

    public UserData getUserData(){
        return userData;
    }

    public static String getAppropriateTimePeriod(){
        long currentTime = System.currentTimeMillis();
        String timePeriod;
        String[] date = ((String)DateFormat.format("HH,mm", new Date(currentTime))).split(",");
        int hour = Integer.parseInt(date[0]);
        if((hour >= 0 && hour < 5) || hour > 20){
            timePeriod = "อาหารดึก";
        } else if(hour > 4 && hour < 11){
            timePeriod = "อาหารเช้า";
        } else if(hour > 10 && hour < 16){
            timePeriod = "อาหารเที่ยง";
        } else {
            timePeriod = "อาหารเย็น";
        }
        return timePeriod;
    }


    public List<FoodListItemMinimal> getTodayEatenFoodList(){
        return todayEaten;
    }


    public void checkForUpdate(){
        String timePeriod = getAppropriateTimePeriod();
        if(!timePeriod.equals(currentPeriod)){
            currentPeriod = timePeriod;
            foodListItems = DBUtils.getFoodListByUserData(userData);
            notifyObservers();
        }
    }

    public String getCurrentPeriod(){
        return currentPeriod;
    }

    public List<FoodListItem> getFoodListItems(){
        return  foodListItems;
    }

    @Override
    public void registerObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        for(Observer o : observers){
            o.onTimeUpdate();
        }
    }
}
