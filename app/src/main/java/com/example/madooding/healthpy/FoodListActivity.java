package com.example.madooding.healthpy;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.madooding.healthpy.adapter.FoodListRecyclerViewAdapter;
import com.example.madooding.healthpy.model.CarouselItem;
import com.example.madooding.healthpy.model.FoodListItem;
import com.example.madooding.healthpy.model.FoodsCategory;
import com.example.madooding.healthpy.utility.CircleTransform;
import com.example.madooding.healthpy.utility.DBUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class FoodListActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private CarouselItem carouselItem;
    private FoodsCategory foodsCategory;
    private ImageView imageView;
    private RecyclerView foodListItemsRecyclerView;
    private FoodListRecyclerViewAdapter foodListItemsRecyclerViewAdapter;
    private RecyclerView.LayoutManager foodListItemsLayoutManager;
    private TextView title;

    private String titleText;
    private String coverImgUrl;

    private Intent intent;
    private List<FoodListItem> foodListItems = new ArrayList<>();

    public static class RequestCode{
        public static final int CALL_ACTIVITY_WITH_FEATURED_FOOD = 300;
        public static final int CALL_ACTIVITY_WITH_CATEGORY_TAG = 310;
    }

    public static class ResponseCode{
        public static final int ADD_FOOD = 300;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("DB HelvethaicaMon X Bd.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        setContentView(R.layout.activity_food_list);
        intent = getIntent();
        int action  = intent.getExtras().getInt("RequestCode");
        switch(action){
            case RequestCode.CALL_ACTIVITY_WITH_FEATURED_FOOD:
                carouselItem = (CarouselItem) getIntent().getSerializableExtra("FoodList");
                titleText = carouselItem.getName();
                coverImgUrl = carouselItem.getImgUrl();
                break;
            case RequestCode.CALL_ACTIVITY_WITH_CATEGORY_TAG:
                foodsCategory = (FoodsCategory) getIntent().getSerializableExtra("FoodsCategory");
                titleText = foodsCategory.getName();
                coverImgUrl = foodsCategory.getImgUrl();
                break;
            default:
                foodListItems = new ArrayList<>();
        }

        toolbar = (Toolbar)findViewById(R.id.food_list_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(titleText);


        imageView = (ImageView) findViewById(R.id.food_list_activity_image);
        Picasso.with(this).load(coverImgUrl).transform(new CircleTransform()).into(imageView);

        title = (TextView) findViewById(R.id.food_list_activity_text_view);
        title.setText(titleText);

        foodListItemsRecyclerView = (RecyclerView) findViewById(R.id.food_list_activity_recycler_view);
        foodListItemsRecyclerView.setFocusable(false);
        foodListItemsRecyclerView.setHasFixedSize(true);

        foodListItemsLayoutManager = new LinearLayoutManager(this);
        foodListItemsRecyclerView.setLayoutManager(foodListItemsLayoutManager);

//        Toast.makeText(this, "food size : " + foodListItems.size(), Toast.LENGTH_SHORT).show();
        renderFoodList(action);

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

    private void renderFoodList(int action){
        foodListItemsRecyclerViewAdapter = new FoodListRecyclerViewAdapter(this, foodListItems, new FoodListRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(FoodListItem item) {
                Intent intent = new Intent(FoodListActivity.this, FoodDetailActivity.class);
                intent.putExtra("food_info", item);
                startActivityForResult(intent, FoodDetailActivity.RequestCode.CALL_ACTIVITY_WITH_INFORMATION);
            }
        });
        foodListItemsRecyclerView.setAdapter(foodListItemsRecyclerViewAdapter);
        switch (action) {
            case RequestCode.CALL_ACTIVITY_WITH_CATEGORY_TAG:
                foodListItems = DBUtils.getFoodDataByObjectTag(foodListItemsRecyclerViewAdapter, foodListItems, foodsCategory.getLinker());
                break;
            case RequestCode.CALL_ACTIVITY_WITH_FEATURED_FOOD:
                foodListItems = DBUtils.getFoodDataByObjectIds(foodListItemsRecyclerViewAdapter, foodListItems, carouselItem.getFoodObjectIds());
                break;
        }
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
}
