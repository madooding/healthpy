package com.example.madooding.healthpy;

import android.content.Context;
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
import com.example.madooding.healthpy.utility.DBUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class FoodListActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private CarouselItem carouselItem;
    private ImageView imageView;
    private RecyclerView foodListItemsRecyclerView;
    private RecyclerView.Adapter foodListItemsRecyclerViewAdapter;
    private RecyclerView.LayoutManager foodListItemsLayoutManager;
    private TextView title;

    private List<FoodListItem> foodListItems;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("DB HelvethaicaMon X Bd.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        setContentView(R.layout.activity_food_list);
        carouselItem = (CarouselItem) getIntent().getSerializableExtra("FoodList");
        foodListItems = DBUtils.getFoodDataByObjectIds(carouselItem.getFoodObjectIds());
        toolbar = (Toolbar)findViewById(R.id.food_list_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(carouselItem.getName());


        imageView = (ImageView) findViewById(R.id.food_list_activity_image);
        Picasso.with(this).load(carouselItem.getImgUrl()).into(imageView);

        title = (TextView) findViewById(R.id.food_list_activity_text_view);
        title.setText(carouselItem.getName());

        foodListItemsRecyclerView = (RecyclerView) findViewById(R.id.food_list_activity_recycler_view);
        foodListItemsRecyclerView.setHasFixedSize(true);

        foodListItemsLayoutManager = new LinearLayoutManager(this);
        foodListItemsRecyclerView.setLayoutManager(foodListItemsLayoutManager);

        Toast.makeText(this, "food size : " + foodListItems.size(), Toast.LENGTH_SHORT).show();
        renderFoodList();

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

    private void renderFoodList(){
        foodListItemsRecyclerViewAdapter = new FoodListRecyclerViewAdapter(this, foodListItems, new FoodListRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(FoodListItem item) {

            }
        });
        foodListItemsRecyclerView.setAdapter(foodListItemsRecyclerViewAdapter);
    }
}
