package com.example.madooding.healthpy;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.example.madooding.healthpy.utility.AppEnv;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class CongenitalDiseaseFormFragment extends Fragment {
    private CheckBox highBloodPressure, heartAndKidney, diabetes, gout;

    public CongenitalDiseaseFormFragment() {
        // Required empty public constructor
    }

    public static CongenitalDiseaseFormFragment newInstance(){
        CongenitalDiseaseFormFragment fragment = new CongenitalDiseaseFormFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_congenital_disease_form, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        highBloodPressure = (CheckBox) view.findViewById(R.id.disease_high_blood_pressure);
        heartAndKidney = (CheckBox) view.findViewById(R.id.disease_heart_and_kidney_disease);
        diabetes = (CheckBox) view.findViewById(R.id.disease_diabetes);
        gout = (CheckBox) view.findViewById(R.id.disease_gout);

        try {
            AppEnv appEnv = AppEnv.getInstance();
            List<String> diseases = appEnv.getUserData().getCongenitalDisease();
            for(String obj : diseases){
                if(obj.equals("โรคความดันโลหิตสูง")){
                    highBloodPressure.setChecked(true);
                }
                if(obj.equals("โรคหัวใจหรือโรคไต")){
                    heartAndKidney.setChecked(true);
                }
                if(obj.equals("โรคเบาหวาน")){
                    diabetes.setChecked(true);
                }
                if(obj.equals("โรคเก๊าท์")){
                    gout.setChecked(true);
                }
            }
        } catch (Exception e){

        }

    }

    public List<String> getCongenitalDiseasesList(){
        List<String> congenitalDiseasesList = new LinkedList<>();
        CheckBox[] checkBoxes = {highBloodPressure, heartAndKidney, diabetes, gout};
        for(CheckBox checkBox : checkBoxes){
            if(checkBox.isChecked()){
                congenitalDiseasesList.add(checkBox.getText().toString());
            }
        }
        return congenitalDiseasesList;

    }
}
