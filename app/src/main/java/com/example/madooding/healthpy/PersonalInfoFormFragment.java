package com.example.madooding.healthpy;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.example.madooding.healthpy.utility.AppEnv;
import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.google.common.collect.Range;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;

import static com.facebook.FacebookSdk.getApplicationContext;


public class PersonalInfoFormFragment extends DialogFragment {
    private EditText name, lastname, email, birthday, weight, height;
    private int day_x, month_x, year_x;
    private static int DATE_PICKER_DIALOG = 1;
    private Calendar calendar;
    private Profile profile = Profile.getCurrentProfile();
    private AccessToken accessToken = AccessToken.getCurrentAccessToken();
    private AppEnv appEnv;
    AwesomeValidation validator = new AwesomeValidation(ValidationStyle.BASIC);

    public PersonalInfoFormFragment() {
        // Required empty public constructor
    }

    public static PersonalInfoFormFragment newInstance() {
        PersonalInfoFormFragment fragment = new PersonalInfoFormFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FacebookSdk.sdkInitialize(getApplicationContext());
        return inflater.inflate(R.layout.fragment_personal_info_form, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        name = (EditText) getActivity().findViewById(R.id.information_gathering_name);
        lastname = (EditText) getActivity().findViewById(R.id.information_gathering_lastname);
        email = (EditText) getActivity().findViewById(R.id.information_gathering_email);
        birthday = (EditText) getActivity().findViewById(R.id.information_gathering_birthday);
        weight = (EditText) getActivity().findViewById(R.id.information_gathering_weight);
        height = (EditText) getActivity().findViewById(R.id.information_gathering_height);

        //Request information from facebook
        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                try {
                    name.setText(object.getString("first_name"));
                    lastname.setText(object.getString("last_name"));
                    email.setText(object.getString("email"));
                    String rawBirthday = object.getString("birthday");
                    String[] splitedBirthday = rawBirthday.split("/");
                    month_x = Integer.parseInt(splitedBirthday[0]);
                    day_x = Integer.parseInt(splitedBirthday[1]);
                    year_x = Integer.parseInt(splitedBirthday[2]);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e){
                    e.printStackTrace();
                }

            }


        });
        Bundle bundle = new Bundle();
        bundle.putString("fields", "id, first_name, last_name, birthday, email");
        request.setParameters(bundle);
        request.executeAsync();



        //Validation
        //validator.setContext(getContext());
        validator.addValidation(name, RegexTemplate.NOT_EMPTY, "คุณจำเป็นต้องกรอกชื่อ");
        validator.addValidation(lastname, RegexTemplate.NOT_EMPTY, "คุณจำเป็นต้องกรอกนามสกุล");
        validator.addValidation(email, Patterns.EMAIL_ADDRESS, "คุณต้องกรอก E-mail ให้ถูกต้อง");
        validator.addValidation(birthday, RegexTemplate.NOT_EMPTY, "คุณจำเป็นต้องกรอกวันเกิด");
        validator.addValidation(weight, Range.greaterThan(10), "น้ำหนักต้องมากกว่า 10 กิโลกรัมขึ้นไป");
        validator.addValidation(height, Range.closed(80, 250), "ส่วนสูงต้องอยู่ในช่วง 80 - 250 เซนติเมตร");


        birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), datePickerListener, year_x, month_x - 1, day_x);
                datePickerDialog.show();
            }
        });

        try {
            usingDataFromAppEnv();
        } catch (Exception e){

        }
    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            //Toast.makeText(getContext(), day + ", " + month + ", " + year, Toast.LENGTH_SHORT).show();
            year_x = year;
            month_x = month + 1;
            day_x = day;
            birthday.setText(day+"/"+(month + 1)+"/"+year);
            Toast.makeText(getContext(), year_x + ", " + month_x + ", " + day_x, Toast.LENGTH_SHORT).show();
            validateForm();

        }
    };

    public boolean validateForm(){
        validator.clear();
        return validator.validate();
    }

    public void usingDataFromAppEnv(){
        appEnv = AppEnv.getInstance();
        name.setText(appEnv.getUserData().getName());
        lastname.setText(appEnv.getUserData().getLastName());
        email.setText(appEnv.getUserData().getEmail());
        day_x = appEnv.getUserData().getBirthDay();
        month_x = appEnv.getUserData().getBirthMonth();
        year_x = appEnv.getUserData().getBirthYear();
        birthday.setText((day_x - 1)+"/"+(month_x + 1)+"/"+year_x);
        weight.setText(Integer.toString(appEnv.getUserData().getWeight()));
        height.setText(Integer.toString(appEnv.getUserData().getHeight()));
    }

    public String getName(){
        return name.getText().toString();
    }

    public String getLastName(){
        return lastname.getText().toString();
    }

    public String getEmail(){
        return email.getText().toString();
    }

    public HashMap<String, Integer> getBirthDay(){
        HashMap<String, Integer> hashMap = new HashMap<>();
        hashMap.put("day", day_x);
        hashMap.put("month", month_x);
        hashMap.put("year", year_x);
        return hashMap;
    }

    public int getWeight(){
        return Integer.parseInt(weight.getText().toString());
    }
    public int getHeight(){
        return Integer.parseInt(height.getText().toString());
    }

}
