package com.example.madooding.healthpy;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.madooding.healthpy.adapter.FoodListRecyclerViewAdapter;
import com.example.madooding.healthpy.listener.QueryListener;
import com.example.madooding.healthpy.model.FoodListItem;
import com.example.madooding.healthpy.utility.DBUtils;

import java.util.ArrayList;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SearchResultActivity extends AppCompatActivity {

    private Intent intent;
    private String constraint = "";
    private Toolbar toolbar;

    private RecyclerView foodListItemsRecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private FoodListRecyclerViewAdapter foodListRecyclerViewAdapter;

    private TextView notFoundTextView;

    private List<FoodListItem> foods = new ArrayList<>();

    public static class RequestCode {
        public static final int CALL_ACTIVITY_WITH_STRING = 400;
    }

    public static class ResponseCode {
        public static final int ADD_FOOD = 410;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("DB HelvethaicaMon X Bd.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        setContentView(R.layout.activity_search_result);

        intent = getIntent();
        constraint = intent.getStringExtra("Constraint");

        toolbar = (Toolbar)findViewById(R.id.search_result_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("ผลการค้นหา : " + constraint);

        notFoundTextView = (TextView) findViewById(R.id.search_result_not_found);

        foodListItemsRecyclerView = (RecyclerView) findViewById(R.id.search_result_activity_recycler_view);
        foodListItemsRecyclerView.setFocusable(false);
        foodListItemsRecyclerView.setHasFixedSize(true);

        linearLayoutManager = new LinearLayoutManager(this);
        foodListItemsRecyclerView.setLayoutManager(linearLayoutManager);

        renderFoodList();

    }


    private void renderFoodList(){
        foodListRecyclerViewAdapter = new FoodListRecyclerViewAdapter(this, foods, new FoodListRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(FoodListItem item) {
                Intent intent = new Intent(SearchResultActivity.this, FoodDetailActivity.class);
                intent.putExtra("food_info", item);
                startActivityForResult(intent, FoodDetailActivity.RequestCode.CALL_ACTIVITY_WITH_INFORMATION);
            }
        });
        foodListItemsRecyclerView.setAdapter(foodListRecyclerViewAdapter);
        foods = DBUtils.searchByCharSequence(foodListRecyclerViewAdapter, foods, constraint, new QueryListener() {
            @Override
            public void onQueryDone() {
                if(foods.size() == 0){
                    notFoundTextView.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case FoodDetailActivity.RequestCode.CALL_ACTIVITY_WITH_INFORMATION:
                if(resultCode == FoodDetailActivity.ResponseCode.ADD_FOOD){
                    setResult(FoodDetailActivity.ResponseCode.ADD_FOOD, data);
                    finish();
                }
        }
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
