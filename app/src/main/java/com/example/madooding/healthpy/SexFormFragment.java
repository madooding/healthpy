package com.example.madooding.healthpy;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cengalabs.flatui.views.FlatRadioButton;
import com.example.madooding.healthpy.utility.AppEnv;
import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;

import org.json.JSONException;
import org.json.JSONObject;

import static com.facebook.FacebookSdk.getApplicationContext;


/**
 * A simple {@link Fragment} subclass.
 */
public class SexFormFragment extends Fragment {

    private FlatRadioButton male, female;

    public SexFormFragment() {
        // Required empty public constructor
    }

    public static SexFormFragment newInstance(){
        SexFormFragment fragment = new SexFormFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FacebookSdk.sdkInitialize(getApplicationContext());
        return inflater.inflate(R.layout.fragment_sex_form, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        male = (FlatRadioButton) view.findViewById(R.id.information_gathering_sex_male);
        female = (FlatRadioButton) view.findViewById(R.id.information_gathering_sex_female);

        //Facebook Request
        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                try {
                    if(object.getString("gender").equals("male")){
                        male.setChecked(true);
                    }else{
                        female.setChecked(true);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        Bundle bundle = new Bundle();
        bundle.putString("fields", "gender");
        request.setParameters(bundle);
        request.executeAsync();

        try {
            AppEnv appEnv = AppEnv.getInstance();
            if(appEnv.getUserData().getSex().equals("ชาย")){
                male.setChecked(true);
            }else{
                female.setChecked(true);
            }
        } catch (Exception e){

        }

    }

    public String getSex(){
        if(male.isChecked()){
            return "ชาย";
        }
        return "หญิง";
    }
}
