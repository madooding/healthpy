package com.example.madooding.healthpy.utility;

import android.util.Log;

import com.example.madooding.healthpy.model.UserData;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

/**
 * Created by madooding on 11/12/2016 AD.
 */

public class DBUtils {
    public static final String APPLICATION_ID = "healthpy";
    public static final String SERVER_URL = "https://healthpy-doiinn.rhcloud.com/parse";
    public static final class ObjectCollections {
        public static final String USER_DATA = "UserData";
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
}
