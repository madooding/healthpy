package com.example.madooding.healthpy;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.madooding.healthpy.adapter.InformationGatheringViewPagerAdapter;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class InformationGatheringActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ViewPager viewPager;
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

        viewPager = (ViewPager) findViewById(R.id.information_gathering_viewpager);
        viewPager.setAdapter(new InformationGatheringViewPagerAdapter(getSupportFragmentManager()));

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
