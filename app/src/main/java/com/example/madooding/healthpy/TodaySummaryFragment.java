package com.example.madooding.healthpy;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.madooding.healthpy.adapter.EatenSummaryRecyclerViewAdapter;
import com.example.madooding.healthpy.model.FoodListItemMinimal;
import com.example.madooding.healthpy.utility.AppEnv;
import com.example.madooding.healthpy.utility.DBUtils;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class TodaySummaryFragment extends Fragment {
    private TextView description;
    private RecyclerView eatenListView;
    private PieChart pieChart;
    private TextView caloriesRatio;
    private LinearLayoutManager eatenListViewLayoutManager;
    private AppEnv appEnv = AppEnv.getInstance();
    private List<FoodListItemMinimal> eatenList;
    ArrayList<String> xVals = new ArrayList<>();
    ArrayList<Entry> yVals = new ArrayList<>();
    ArrayList<Integer> colors = new ArrayList<>();
    PieDataSet dataSet;
    private EatenSummaryRecyclerViewAdapter eatenSummaryRecyclerViewAdapter;
    public TodaySummaryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_today_summary, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        pieChart = (PieChart) view.findViewById(R.id.today_summary_pie_chart);
        caloriesRatio = (TextView) view.findViewById(R.id.eaten_summary_calories_ratio);
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
        caloriesRatio.setText(appEnv.getSumEatenCalories() + "/" + appEnv.getRecommendedCalories());

        description = (TextView) view.findViewById(R.id.eaten_summary_description);
        description.setText("* " + appEnv.getRecommendedCalories() + " กิโลแคลอรี่ เป็นปริมาณการบริโภคต่อวันที่แนะนำสำหรับคุณ");

        eatenListView = (RecyclerView) view.findViewById(R.id.today_summary_recycler_view);
        eatenListView.setHasFixedSize(true);
        eatenListViewLayoutManager = new LinearLayoutManager(getContext());
        eatenListView.setLayoutManager(eatenListViewLayoutManager);

        eatenSummaryRecyclerViewAdapter = new EatenSummaryRecyclerViewAdapter(getContext(), appEnv.getTodayEatenFoodList(), new EatenSummaryRecyclerViewAdapter.OnItemDeleteListener(){
            @Override
            public void onItemDelete(int position, FoodListItemMinimal foodListItemMinimal) {
//                Toast.makeText(getContext(), "food objectid " + foodListItemMinimal.getObjectId(), Toast.LENGTH_SHORT).show();
                DBUtils.deleteEatingItem(foodListItemMinimal.getObjectId());
                appEnv.subtractEatenCalories(foodListItemMinimal.getCalories());
                caloriesRatio.setText(appEnv.getSumEatenCalories() + "/" + appEnv.getRecommendedCalories());
                try {
                    renderPiechart();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        eatenListView.setAdapter(eatenSummaryRecyclerViewAdapter);

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            appEnv.checkForUpdate();
            eatenSummaryRecyclerViewAdapter.notifyDataSetChanged();
            caloriesRatio.setText(appEnv.getSumEatenCalories() + "/" + appEnv.getRecommendedCalories());
            try {
                renderPiechart();
            } catch (Exception e) {
                e.printStackTrace();
            }
//            Toast.makeText(getContext(), "Eaten Calories Summary : " + appEnv.getSumEatenCalories() + " Kilo Calories", Toast.LENGTH_SHORT).show();
        }
    }

    private void renderPiechart() throws Exception{
        xVals.clear();
        yVals.clear();

        xVals.add("eatenCalories");
        xVals.add("remainingCalories");



        yVals.add(new Entry(appEnv.getSumEatenCalories(), 0));
        yVals.add(new Entry(Math.max(appEnv.getRecommendedCalories() - appEnv.getSumEatenCalories(), 0), 1));

        colors.add(getResources().getColor(R.color.colorPrimary));
        colors.add(getResources().getColor(R.color.colorPrimaryLight));

        PieDataSet dataSet = new PieDataSet(yVals, "Calories");
        dataSet.setDrawValues(false);
        dataSet.setColors(colors);


        PieData data = new PieData(xVals, dataSet);
        pieChart.setData(data);
        pieChart.invalidate();
    }
}
