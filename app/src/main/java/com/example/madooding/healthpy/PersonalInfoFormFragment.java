package com.example.madooding.healthpy;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
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
import com.google.common.collect.Range;


public class PersonalInfoFormFragment extends DialogFragment {
    private EditText name, lastname, email, birthday, weight, height;
    private int day, month, year;
    private static int DATE_PICKER_DIALOG = 1;
    AwesomeValidation validator = new AwesomeValidation(ValidationStyle.BASIC);

    public PersonalInfoFormFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
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

        //Validation
        validator.setContext(getContext());
        validator.addValidation(name, RegexTemplate.NOT_EMPTY, "คุณจำเป็นต้องกรอกชื่อ");
        validator.addValidation(lastname, RegexTemplate.NOT_EMPTY, "คุณจำเป็นต้องกรอกนามสกุล");
        validator.addValidation(email, Patterns.EMAIL_ADDRESS, "คุณต้องกรอก E-mail ให้ถูกต้อง");
        validator.addValidation(birthday, RegexTemplate.NOT_EMPTY, "คุณจำเป็นต้องกรอกวันเกิด");
        validator.addValidation(weight, Range.greaterThan(10), "น้ำหนักต้องมากกว่า 10 กิโลกรัมขึ้นไป");
        validator.addValidation(height, Range.closed(80, 250), "ส่วนสูงต้องอยู่ในช่วง 80 - 250 เซนติเมตร");

        birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), datePickerListener, 2016, 3, 4);
                datePickerDialog.show();
            }
        });
    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            Toast.makeText(getContext(), day + ", " + month + ", " + year, Toast.LENGTH_SHORT).show();
            birthday.setText(day+"/"+month+"/"+year);
            validator.validate();
        }
    };
}
