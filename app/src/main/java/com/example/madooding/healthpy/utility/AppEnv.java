package com.example.madooding.healthpy.utility;

import com.example.madooding.healthpy.interfaces.AppEnvSubject;
import com.example.madooding.healthpy.interfaces.Observer;
import com.example.madooding.healthpy.model.FoodListItem;
import com.example.madooding.healthpy.model.FoodListItemMinimal;
import com.example.madooding.healthpy.model.UserData;

import android.text.format.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
    private String currentDate;
    private int sumOfEatenCalories = 0;
    private int recommendedCalories = 0;

    private List<FoodListItem> foodListItems;

    private AppEnv(UserData userData){
        this.observers = new ArrayList<>();
        this.foodListItems = new ArrayList<>();
        this.userData = userData;
        this.currentPeriod = getAppropriateTimePeriod();
        foodListItems = DBUtils.getFoodListByUserData(userData);
        todayEaten = DBUtils.getEatingListByDate(userData.getObjectId(), new Date(System.currentTimeMillis()));
        currentDate = (String)DateFormat.format("yyyy-MM-dd", new Date(System.currentTimeMillis()));
        calulateEatenCalories();
        this.recommendedCalories = calulateRecommendedCalories();
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
        String dateNow = (String) DateFormat.format("yyyy-MM-dd", new Date(System.currentTimeMillis()));
        if(!timePeriod.equals(currentPeriod)){
            currentPeriod = timePeriod;
            foodListItems = DBUtils.getFoodListByUserData(userData);
            notifyObservers();
        }
        if(!currentDate.equals(dateNow)){
            currentDate = dateNow;
            todayEaten.clear();
            todayEaten = DBUtils.getEatingListByDate(userData.getObjectId(), new Date(System.currentTimeMillis()));
            calulateEatenCalories();
        }
    }

    public String getCurrentDate(){
        return currentDate;
    }

    public void calulateEatenCalories(){
        for(FoodListItemMinimal obj : todayEaten){
            addEatenCalories(obj.getCalories());
        }
    }

    public int calulateRecommendedCalories(){
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();
        int recommendedCalories;
        dob.set(userData.getBirthYear(), userData.getBirthMonth(), userData.getBirthDay());
        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
            age--;
        }
        if(userData.getSex().equals("male")){
            recommendedCalories =  (int) (66 + (13.7 * userData.getWeight()) + (5 * userData.getHeight()) - (6.8 * age));
        }else{
            recommendedCalories = (int) ((665 + (9.6 * userData.getWeight()) + (1.8 * userData.getHeight()) - (4.7 * age)));
        }
        return recommendedCalories;
    }

    public int getRecommendedCalories(){ return recommendedCalories; }

    public int getSumEatenCalories(){ return sumOfEatenCalories;}

    public void setSumEatenCalories(int calories){ sumOfEatenCalories = calories; }

    public void addEatenCalories(int calories) { sumOfEatenCalories += calories; }

    public void subtractEatenCalories(int calories){ sumOfEatenCalories -= calories; }

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
