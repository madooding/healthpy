package com.example.madooding.healthpy.utility;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

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
        final boolean[] result = new boolean[1];
        ParseQuery<ParseObject> query = ParseQuery.getQuery(ObjectCollections.USER_DATA);
        query.whereEqualTo("fb_id", fid);
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if (object == null){
                    result[0] = false;
                }else{
                    result[0] = object.getBoolean("isRegistered");
                }
            }
        });
        return result[0];
    }
}
