package com.example.madooding.healthpy;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.example.madooding.healthpy.adapter.InformationGatheringViewPagerAdapter;
import com.example.madooding.healthpy.utility.NonSwipeableViewPager;

import java.util.ArrayList;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class InformationGatheringActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private NonSwipeableViewPager viewPager;
    private Button nextButton;
    private Button previousButton;
    private int currentFragmentPosition = 0;
    private List<Fragment> fragmentArrayList = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("DB HelvethaicaMon X Bd.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        setContentView(R.layout.activity_information_gathering);
        toolbar = (Toolbar) findViewById(R.id.information_gathering_toolbar);
        setSupportActionBar(toolbar);
        setTitle("เก็บข้อมูลเพิ่มเติม");

        //Button variable initialization
        nextButton = (Button) findViewById(R.id.information_gathering_next_button);
        previousButton = (Button) findViewById(R.id.information_gathering_previous_button);
        setButtonState();

        //Set Fragment
        fragmentArrayList.add(new PersonalInfoFormFragment());
        fragmentArrayList.add(new KindOfAnimalCantEatFormFragment());
        fragmentArrayList.add(new CongenitalDiseaseFormFragment());

        viewPager = (NonSwipeableViewPager) findViewById(R.id.information_gathering_viewpager);
        viewPager.setAdapter(new InformationGatheringViewPagerAdapter(getSupportFragmentManager(), fragmentArrayList));
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentFragmentPosition < fragmentArrayList.size() - 1) {
                    viewPager.setCurrentItem(++currentFragmentPosition);
                    setButtonState();
                }else{
                    //Thing to do when user answer question complete
                }
            }
        });
        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(--currentFragmentPosition);
                setButtonState();
            }
        });

    }

    private void setButtonState(){
        if(currentFragmentPosition == 0){
            previousButton.setEnabled(false);
        }else{
            previousButton.setEnabled(true);
        }
        if(currentFragmentPosition == fragmentArrayList.size() - 1){
            nextButton.setText("เสร็จสิ้น");
        }else{
            nextButton.setText("ถัดไป");
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
