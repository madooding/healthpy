package com.example.madooding.healthpy;

import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.madooding.healthpy.adapter.NutritionListRecyclerViewAdapter;
import com.example.madooding.healthpy.model.NutritionType;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class FoodDetailActivity
        extends AppCompatActivity
        implements ObservableScrollViewCallbacks {

    private View imageView;
    private ObservableScrollView scrollView;
    private int parallaxImageHeight, actionBarSize;
    private TextView titleTextView;
    private AppBarLayout appBar;
    Display display;
    Point size = new Point();
    private int titleTextMargin;
    private LinearLayout titleContainer;
    private int screenWidth;
    private int titleBigTextSize;
    private String foodName;
    private String foodDetail;
    private int foodCalories;
    private HashMap<String, Float> nutrition;
    private int titleTextLeftIndentation;
    private PieChart pieChart;
    private RecyclerView nutritionTable;
    private RecyclerView.Adapter nutritionTableAdapter;
    private RecyclerView.LayoutManager nutrionTableLayoutManager;
    private FloatingActionButton addFoodFab;
    private boolean fabIsShown;
    private int fabLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("DB HelvethaicaMon X Bd.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        setContentView(R.layout.activity_food_detail);

        setSupportActionBar((Toolbar) findViewById(R.id.food_detail_toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(" ");

        display = getWindowManager().getDefaultDisplay();
        display.getSize(size);
        screenWidth = size.x;

        parallaxImageHeight = getResources().getDimensionPixelOffset(R.dimen.food_detail_parallax_image_height);
        actionBarSize = getResources().getDimensionPixelSize(R.dimen.action_bar_size);

        titleTextView = (TextView) findViewById(R.id.food_detail_title);
        titleTextView.measure(0,0);
        titleTextView.setMinimumHeight(parallaxImageHeight);
        titleContainer = (LinearLayout) findViewById(R.id.food_detail_title_container);
        titleTextMargin = (screenWidth - titleTextView.getMeasuredWidth()) / 2;
        titleContainer.setPadding(titleTextMargin - 1, 0, 0, 0);
        titleBigTextSize = (int)getResources().getDimensionPixelSize(R.dimen.food_detail_title_text_size);
        titleTextLeftIndentation = getResources().getDimensionPixelSize(R.dimen.food_detail_title_padding);

        appBar = (AppBarLayout) findViewById(R.id.food_detail_app_bar);
        appBar.setBackgroundColor(ScrollUtils.getColorWithAlpha(0, getResources().getColor(R.color.colorPrimary)));
        appBar.setMinimumHeight(parallaxImageHeight);

        imageView = findViewById(R.id.food_detail_image);

        pieChart = (PieChart) findViewById(R.id.food_detail_nutrition_pie_chart);
        // enable hole and configure
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleRadius(70);
        pieChart.setTransparentCircleRadius(10);

        //Disable a lot of thing
        pieChart.setTouchEnabled(false);
        pieChart.setDrawSliceText(false);
        pieChart.getLegend().setEnabled(false);
        pieChart.setDescription("");

        nutrition = new HashMap<>();
        nutrition.put("fat", (float)30.2);
        nutrition.put("carbohydrate", (float)20.3);
        nutrition.put("protein", (float)28);
        nutrition.put("cholesterol", (float)40.3);
        nutrition.put("sodium", (float)32.1);

        ArrayList<Entry> yVals = new ArrayList<>();
        ArrayList<String> xVals = new ArrayList<>();
        ArrayList<Integer> colors = new ArrayList<>();
        List<NutritionType> nutritionTypeList = new ArrayList<>();
        int count = 0;
        for(String key : nutrition.keySet()){
            yVals.add(new Entry(nutrition.get(key), count));
            xVals.add(key);
            switch (key){
                case "fat": colors.add(getResources().getColor(R.color.colorFat));
                            break;
                case "carbohydrate": colors.add(getResources().getColor(R.color.colorCarbohydrate));
                            break;
                case "protein" : colors.add(getResources().getColor(R.color.colorProtein));
                            break;
                case "cholesterol": colors.add(getResources().getColor(R.color.colorCholesterol));
                            break;
                case "sodium": colors.add(getResources().getColor(R.color.colorSodium));
                            break;
            }
            nutritionTypeList.add(new NutritionType(key, nutrition.get(key), colors.get(count)));
            count++;
        }


        PieDataSet dataSet = new PieDataSet(yVals, "Test Pie Chart");
        dataSet.setDrawValues(false);
        dataSet.setColors(colors);

        PieData data = new PieData(xVals, dataSet);
        pieChart.setData(data);
        pieChart.invalidate();

        nutritionTable = (RecyclerView) findViewById(R.id.food_detail_nutrition_table);
        nutrionTableLayoutManager = new LinearLayoutManager(this);
        nutritionTable.setLayoutManager(nutrionTableLayoutManager);
        nutritionTableAdapter = new NutritionListRecyclerViewAdapter(this, nutritionTypeList);
        nutritionTable.setAdapter(nutritionTableAdapter);

        addFoodFab = (FloatingActionButton) findViewById(R.id.food_detail_add_fab);
        fabLocation = (parallaxImageHeight - actionBarSize + getResources().getDimensionPixelSize(R.dimen.activity_vertical_margin) - 10) - (addFoodFab.getHeight());
        addFoodFab.setTranslationY(fabLocation);
        showFab();

        scrollView = (ObservableScrollView) findViewById(R.id.food_detail_scroll_view);
        scrollView.setScrollViewCallbacks(this);


    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {


        imageView.setTranslationY((float)scrollY/2);
        int baseColor = getResources().getColor(R.color.colorPrimary);
        float alpha = Math.min(1, (float) scrollY / (parallaxImageHeight - actionBarSize));
        titleTextView.measure(0,0);
        if(alpha == 1){
            setTitle("สวัสดี");
            titleTextView.setText(null);
        }else{
            setTitle(null);
            titleTextView.setText("สวัสดี");
        }
        appBar.setBackgroundColor(ScrollUtils.getColorWithAlpha(alpha, baseColor));
        appBar.setMinimumHeight(Math.max(actionBarSize,parallaxImageHeight - (scrollY)));
        titleTextView.setMinimumHeight(Math.max(actionBarSize,parallaxImageHeight - (scrollY)));
        titleTextView.setTextSize(28 + (int)((72 - 28) * (1 - alpha)));
        titleContainer.setPadding(Math.max(titleTextLeftIndentation,
                titleTextLeftIndentation + (int)((titleTextMargin - titleTextLeftIndentation) * (1 - alpha))), 0, 0, 0);

        int fabTranslationY = fabLocation - scrollY;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) addFoodFab.getLayoutParams();
            lp.topMargin = (int) fabTranslationY;
            addFoodFab.requestLayout();
        } else {
            addFoodFab.setTranslationY(fabTranslationY);
        }

        if(alpha >= 0.8){
            hideFab();
        }else{
            showFab();
        }

    }

    private void showFab() {
        if (!fabIsShown) {
            addFoodFab.animate().cancel();
            addFoodFab.animate().scaleX(1).scaleY(1).setDuration(200).start();
            fabIsShown = true;
        }
    }

    private void hideFab() {
        if (fabIsShown) {
            addFoodFab.animate().cancel();
            addFoodFab.animate().scaleX(0).scaleY(0).setDuration(200).start();
            fabIsShown = false;
        }
    }

    @Override
    public void onDownMotionEvent() {

    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
