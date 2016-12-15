package com.example.madooding.healthpy;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.madooding.healthpy.adapter.EatenSummaryRecyclerViewAdapter;
import com.example.madooding.healthpy.model.FoodListItem;
import com.example.madooding.healthpy.model.FoodListItemMinimal;
import com.example.madooding.healthpy.utility.AppEnv;
import com.example.madooding.healthpy.utility.DBUtils;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class DaySummaryActivity extends AppCompatActivity {

    private PieChart pieChart;
    private TextView caloriesRatio;
    private TextView description, notFoundTextView;
    private AppEnv appEnv = AppEnv.getInstance();
    private RecyclerView eatenListView;
    private LinearLayoutManager eatenListViewLayoutManager;
    private EatenSummaryRecyclerViewAdapter eatenSummaryRecyclerViewAdapter;

    private Integer eatingCalories = 0;
    private int remainingCalories;

    private List<FoodListItemMinimal> eatingList;
    private ArrayList<String> xVals = new ArrayList<>();
    private ArrayList<Entry> yVals = new ArrayList<>();
    private ArrayList<Integer> colors = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("DB HelvethaicaMon X Bd.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        setContentView(R.layout.activity_day_summary);

        Bundle bundle = getIntent().getExtras();
        final Date date = (Date)bundle.getSerializable("date");
        final String dateStr = (String) DateFormat.format("dd/MM/yyyy", date);
        setSupportActionBar((Toolbar) findViewById(R.id.day_summary_toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(dateStr);

        notFoundTextView = (TextView) findViewById(R.id.day_summary_not_found);
        if(dateStr.equals((String)DateFormat.format("dd/MM/yyyy", new Date(System.currentTimeMillis())))){
            eatingList = appEnv.getTodayEatenFoodList();
            eatingCalories = appEnv.getSumEatenCalories();
        }else {
            eatingList = DBUtils.getEatingListByDate(appEnv.getUserData().getObjectId(), date);
            for(FoodListItemMinimal obj : eatingList){
                eatingCalories += obj.getCalories();

            }

        }

        pieChart = (PieChart) findViewById(R.id.day_summary_pie_chart);
        caloriesRatio = (TextView) findViewById(R.id.day_summary_calories_ratio);
        description = (TextView) findViewById(R.id.day_eaten_summary_description);
        eatenListView = (RecyclerView) findViewById(R.id.day_summary_recycler_view);

        // enable hole and configure
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleRadius(90);
        pieChart.setTransparentCircleRadius(10);

        //Disable a lot of thing
        pieChart.setTouchEnabled(false);
        pieChart.setDrawSliceText(false);
        pieChart.getLegend().setEnabled(false);
        pieChart.setDescription("");

        try {
            renderPiechart();
        } catch (Exception e) {
            e.printStackTrace();
        }
        caloriesRatio.setText(eatingCalories + "/" + appEnv.getRecommendedCalories());

        description.setText("* " + appEnv.getRecommendedCalories() + " กิโลแคลอรี่ เป็นปริมาณการบริโภคต่อวันที่แนะนำสำหรับคุณ");

        eatenListView.setHasFixedSize(true);
        eatenListViewLayoutManager = new LinearLayoutManager(this);
        eatenListView.setLayoutManager(eatenListViewLayoutManager);

        eatenSummaryRecyclerViewAdapter = new EatenSummaryRecyclerViewAdapter(this, eatingList, new EatenSummaryRecyclerViewAdapter.OnItemDeleteListener(){
            @Override
            public void onItemDelete(int position, FoodListItemMinimal foodListItemMinimal) {
                DBUtils.deleteEatingItem(foodListItemMinimal.getObjectId());

                caloriesRatio.setText(eatingCalories + "/" + appEnv.getRecommendedCalories());
                if(dateStr.equals((String) DateFormat.format("dd/MM/yyyy", date))){
//                    Toast.makeText(DaySummaryActivity.this, "ตรงกันไอสัส", Toast.LENGTH_SHORT).show();
                    if(dateStr.equals((String)DateFormat.format("dd/MM/yyyy", new Date(System.currentTimeMillis())))) {
                        appEnv.subtractEatenCalories(foodListItemMinimal.getCalories());
                    }
                    eatingCalories -= foodListItemMinimal.getCalories();
                    caloriesRatio.setText(eatingCalories + "/" + appEnv.getRecommendedCalories());
                    //eatingList.remove(position);
                }
                try {
                    renderPiechart();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        eatenListView.setAdapter(eatenSummaryRecyclerViewAdapter);
        eatenListView.setNestedScrollingEnabled(false);

        if(eatingList.size() == 0){
            notFoundTextView.setVisibility(View.VISIBLE);
        }
    }

    private void renderPiechart() throws Exception{
        xVals.clear();
        yVals.clear();

        xVals.add("eatenCalories");
        xVals.add("remainingCalories");


        yVals.add(new Entry(eatingCalories, 0));
        yVals.add(new Entry(Math.max(appEnv.getRecommendedCalories() - eatingCalories, 0), 1));

        colors.add(getResources().getColor(R.color.colorPrimary));
        colors.add(getResources().getColor(R.color.colorPrimaryLight));

        PieDataSet dataSet = new PieDataSet(yVals, "Calories");
        dataSet.setDrawValues(false);
        dataSet.setColors(colors);


        PieData data = new PieData(xVals, dataSet);
        pieChart.setData(data);
        pieChart.invalidate();
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
