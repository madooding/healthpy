package com.example.madooding.healthpy;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.madooding.healthpy.adapter.InformationGatheringViewPagerAdapter;
import com.example.madooding.healthpy.model.UserData;
import com.example.madooding.healthpy.utility.DBUtils;
import com.example.madooding.healthpy.utility.NonSwipeableViewPager;
import com.facebook.Profile;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
    private String name, lastName, email, sex;
    private int birthDay, birthMonth, birthYear, weight, height;
    private List<String> uneatableMeats, congenitalDiseasesList;
    private String profileImgURI;
    private UserData userData;
    private Date birthDate;
    private Intent intent;

    public static class RequestCode {
        public static final int REGISTRATION_REQUEST = 200;
    }

    public static class ResponseCode {
        public static final int REGISTRATION_COMPLETE = 200;
        public static final int REGISTRATION_CANCEL = 210;
        public static final int REGISTRATION_ERROR = 220;
    }

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

        intent = new Intent();

        //Button variable initialization
        nextButton = (Button) findViewById(R.id.information_gathering_next_button);
        previousButton = (Button) findViewById(R.id.information_gathering_previous_button);
        setButtonState();

        //Set Fragment
        fragmentArrayList.add(PersonalInfoFormFragment.newInstance());
        fragmentArrayList.add(SexFormFragment.newInstance());
        fragmentArrayList.add(KindOfAnimalCantEatFormFragment.newInstance());
        fragmentArrayList.add(CongenitalDiseaseFormFragment.newInstance());

        viewPager = (NonSwipeableViewPager) findViewById(R.id.information_gathering_viewpager);
        viewPager.setAdapter(new InformationGatheringViewPagerAdapter(getSupportFragmentManager(), fragmentArrayList));
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (currentFragmentPosition){
                    case 0: PersonalInfoFormFragment personalInfoFormFragment = (PersonalInfoFormFragment)getActiveFragment(viewPager, currentFragmentPosition);
                            if(!personalInfoFormFragment.validateForm()){
                                return;
                            }else{
                                name = personalInfoFormFragment.getName();
                                lastName = personalInfoFormFragment.getLastName();
                                email = personalInfoFormFragment.getEmail();
                                HashMap<String, Integer> birthdate = personalInfoFormFragment.getBirthDay();
                                birthDay = birthdate.get("day");
                                birthMonth = birthdate.get("month");
                                birthYear = birthdate.get("year");
                                weight = personalInfoFormFragment.getWeight();
                                height = personalInfoFormFragment.getHeight();
                            }
                            break;
                    case 1: SexFormFragment sexFormFragment = (SexFormFragment) getActiveFragment(viewPager, currentFragmentPosition);
                            sex = sexFormFragment.getSex();
                            break;
                    case 2: KindOfAnimalCantEatFormFragment kindOfAnimalCantEatFormFragment = (KindOfAnimalCantEatFormFragment)getActiveFragment(viewPager, currentFragmentPosition);
                            uneatableMeats = kindOfAnimalCantEatFormFragment.getUneatableMeatsList();
                            break;
                    case 3: CongenitalDiseaseFormFragment congenitalDiseaseFormFragment = (CongenitalDiseaseFormFragment) getActiveFragment(viewPager, currentFragmentPosition);
                            congenitalDiseasesList = congenitalDiseaseFormFragment.getCongenitalDiseasesList();
                            break;
                }
                if(currentFragmentPosition < fragmentArrayList.size() - 1) {
                    viewPager.setCurrentItem(++currentFragmentPosition);
                    setButtonState();
                }else{
                    //Thing to do when user answer question complete
                    birthDate = new Date(birthYear, birthMonth, birthDay);
                    userData = new UserData(name, lastName, email, sex, birthDay, birthMonth, birthYear, weight, height, uneatableMeats, congenitalDiseasesList, Profile.getCurrentProfile().getId(), Profile.getCurrentProfile().getProfilePictureUri(100, 100).toString());
                    userData.setRegistered(true);
                    DBUtils.regisUser(userData);
                    intent.putExtra("UserData", userData);
                    setResult(ResponseCode.REGISTRATION_COMPLETE, intent);
                    finish();
                    Toast.makeText(InformationGatheringActivity.this, birthDate.toString(), Toast.LENGTH_SHORT).show();
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

    public Fragment getActiveFragment(ViewPager container, int position) {
        String name = "android:switcher:" + container.getId() + ":" + position;
        return getSupportFragmentManager().findFragmentByTag(name);
    }

    @Override
    public void onBackPressed() {
        setResult(ResponseCode.REGISTRATION_CANCEL);
        finish();
        super.onBackPressed();
    }
}
