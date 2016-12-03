package com.example.madooding.healthpy.utility;

import android.support.v4.view.ViewPager;
import android.util.Log;

import com.example.madooding.healthpy.model.CarouselItem;
import com.example.madooding.healthpy.model.FoodListItem;
import com.example.madooding.healthpy.model.UserData;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by madooding on 11/12/2016 AD.
 */

public class DBUtils {
    public static final String APPLICATION_ID = "healthpy";
    public static final String SERVER_URL = "https://healthpy-doiinn.rhcloud.com/parse";
    public static final class ObjectCollections {
        public static final String USER_DATA = "UserData";
        public static final String FOOD_DATA = "FoodData";
        public static final String FEATURED_FOODS = "FeaturedFoods";
    }
    public static boolean isRegistered(String fid)
    {
        boolean result = false;
        ParseQuery<ParseObject> query = ParseQuery.getQuery(ObjectCollections.USER_DATA);
        query.whereEqualTo("fb_id", fid);
        try {
            ParseObject object = query.getFirst();
            result = object.getBoolean("isRegistered");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void regisUser(UserData userData) {
        final boolean[] registerStatus = {false};
        ParseObject parseUserData = new ParseObject("UserData");
        parseUserData.put("name", userData.getName());
        parseUserData.put("lastName", userData.getLastName());
        parseUserData.put("email", userData.getEmail());
        parseUserData.put("sex", userData.getSex());
        parseUserData.put("birthdate", userData.getBirthDate());
        parseUserData.put("weight", userData.getWeight());
        parseUserData.put("height", userData.getHeight());
        parseUserData.put("fb_id", userData.getFb_id());
        parseUserData.put("profileImgURI", userData.getProfileImgURI());
        parseUserData.put("isRegistered", userData.isRegistered());
        parseUserData.put("isVegetarian", userData.isVegetarian());
        parseUserData.put("cannotEat", userData.getUneatable());
        parseUserData.put("congenitalDisease", userData.getCongenitalDisease());
        parseUserData.saveInBackground();
    }

    public static UserData getUserData(String fid){
        UserData userData;
        Calendar cal = Calendar.getInstance();
        String objectId, name, lastName, email, sex, profileImgURI;
        int weight, height;
        boolean isRegistered, isVegetarian;
        List<String> uneatable, congenitalDisease;
        Date birthdate;
        ParseQuery<ParseObject> query = ParseQuery.getQuery(ObjectCollections.USER_DATA);
        query.whereEqualTo("fb_id", fid);
        try {
            ParseObject object = query.getFirst();
            objectId = object.getObjectId();
            name = object.getString("name");
            lastName = object.getString("lastName");
            email = object.getString("email");
            sex = object.getString("sex");
            profileImgURI = object.getString("profileImgURI");
            weight = object.getInt("weight");
            height = object.getInt("height");
            isRegistered = object.getBoolean("isRegistered");
            isVegetarian = object.getBoolean("isVegetarian");
            birthdate = object.getDate("birthdate");
            uneatable = object.getList("cannotEat");
            congenitalDisease = object.getList("congenitalDisease");
            cal.setTime(birthdate);
            userData = new UserData(objectId, name, lastName, email, sex, cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.MONTH), cal.get(Calendar.YEAR), weight, height, uneatable, congenitalDisease, fid, profileImgURI);
            userData.setRegistered(isRegistered);
            userData.setVegetarian(isVegetarian);
            return userData;

        }catch (ParseException e){
            e.printStackTrace();
        }
        return null;
    }

    public static List<FoodListItem> getFoodListByUserData(UserData userData){
        final List<FoodListItem> foodListItems = new ArrayList<>();
        ParseQuery<ParseObject> query = ParseQuery.getQuery(ObjectCollections.FOOD_DATA);
        query.whereNotContainedIn("type", userData.getUneatable());
        query.whereNotContainedIn("caution", userData.getCongenitalDisease());
        query.orderByAscending("calories");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e == null){
                    for(ParseObject object : objects){
                        try {
                            foodListItems.add(new FoodListItem(object.getParseFile("picture").getUrl(), object.getString("name"), object.getString("description"), object.getInt("calories"), object.getJSONArray("nutrient").getJSONObject(0)));
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }
        });
        return foodListItems;
    }

    public static List<CarouselItem> getFeaturedFoodList(){
        final List<CarouselItem> carouselItems = new ArrayList<>();
        ParseQuery<ParseObject> query = ParseQuery.getQuery(ObjectCollections.FEATURED_FOODS);
        List<ParseObject> objects = null;
        try {
            objects = query.find();
            for(ParseObject object : objects){
                List<String> foodList = new ArrayList<String>();
                for(int i = 0; i < object.getJSONArray("food_list").length(); i++){
                    try {
                        foodList.add(object.getJSONArray("food_list").getString(i));
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                }
                carouselItems.add(new CarouselItem(object.getParseFile("picture").getUrl(), object.getString("name"), foodList));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return carouselItems;
    }

    public static FoodListItem getFoodDataByObjectId(String objectId){
        FoodListItem foodListItem = null;
        ParseQuery<ParseObject> query = ParseQuery.getQuery(ObjectCollections.FOOD_DATA);
        query.whereEqualTo("objectId", objectId);
        try{
            ParseObject object = query.getFirst();
            foodListItem = new FoodListItem(object.getParseFile("picture").getUrl(), object.getString("name"), object.getString("description"), object.getInt("calories"), object.getJSONArray("nutrient").getJSONObject(0));
        }catch (ParseException e){
            e.printStackTrace();
        }catch (JSONException e){
            e.printStackTrace();
        }
        return foodListItem;
    }

    public static List<FoodListItem> getFoodDataByObjectIds(List<String> objectIds){
        final List<FoodListItem> foodListItems = new ArrayList<>();
        ParseQuery<ParseObject> query = ParseQuery.getQuery(ObjectCollections.FOOD_DATA);
        query.whereContainedIn("objectId", objectIds);
        query.addAscendingOrder("calories");
        try {
            List<ParseObject> objects = query.find();
            for(ParseObject object : objects){
                try {
                    foodListItems.add(new FoodListItem(object.getParseFile("picture").getUrl(), object.getString("name"), object.getString("description"), object.getInt("calories"), object.getJSONArray("nutrient").getJSONObject(0)));
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return foodListItems;
    }
}
