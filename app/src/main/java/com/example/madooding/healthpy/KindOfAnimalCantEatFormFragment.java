package com.example.madooding.healthpy;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static android.R.id.list;


/**
 * A simple {@link Fragment} subclass.
 */
public class KindOfAnimalCantEatFormFragment extends Fragment {
    private CheckBox pig, cow, chicken, shrimp, squid;

    public KindOfAnimalCantEatFormFragment() {
        // Required empty public constructor
    }

    public static KindOfAnimalCantEatFormFragment newInstance(){
        KindOfAnimalCantEatFormFragment fragment = new KindOfAnimalCantEatFormFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_kind_of_animal_cant_eat_form, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        pig = (CheckBox) view.findViewById(R.id.uneatable_pig);
        cow = (CheckBox) view.findViewById(R.id.uneatable_cow);
        chicken = (CheckBox) view.findViewById(R.id.uneatable_chicken);
        shrimp = (CheckBox) view.findViewById(R.id.uneatable_shrimp);
        squid = (CheckBox) view.findViewById(R.id.uneatable_squid);

    }

    public List<String> getUneatableMeatsList(){
        List<String> uneatableMeats = new LinkedList<>();
        CheckBox[] checkBoxes = {pig, cow, chicken, shrimp, squid};
        for(CheckBox checkBoxItem : checkBoxes){
            if(checkBoxItem.isChecked()){
                uneatableMeats.add(checkBoxItem.getText().toString());
            }
        }
        return uneatableMeats;
    }


}
