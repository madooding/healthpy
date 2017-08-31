package com.example.madooding.healthpy;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cengalabs.flatui.views.FlatEditText;
import com.example.madooding.healthpy.adapter.CarouselViewPagerAdapter;
import com.example.madooding.healthpy.adapter.FoodListRecyclerViewAdapter;
import com.example.madooding.healthpy.adapter.SearchAutoCompleteAdapter;
import com.example.madooding.healthpy.interfaces.Observer;
import com.example.madooding.healthpy.model.CarouselItem;
import com.example.madooding.healthpy.model.FoodListItem;
import com.example.madooding.healthpy.model.FoodListItemMinimal;
import com.example.madooding.healthpy.model.FoodsCategory;
import com.example.madooding.healthpy.utility.AppEnv;
import com.example.madooding.healthpy.utility.CircleTransform;
import com.example.madooding.healthpy.utility.DBUtils;
import com.example.madooding.healthpy.utility.DelayAutoCompleteTextView;
import com.facebook.FacebookSdk;
import com.squareup.picasso.Picasso;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment implements Observer{

    ViewPager carouselViewPager;
    CirclePageIndicator carouselCircleIndicator;
    GestureDetector tapGestureDetector;
    FloatingActionButton fab;
    DelayAutoCompleteTextView searchBox;

    //Should retrive from server
    List<FoodsCategory> foodsCategoryList;
    List<CarouselItem> carouselItemList;

    private RecyclerView foodListItemsRecyclerView;
    private RecyclerView.Adapter foodListItemsRecyclerViewAdapter;
    private RecyclerView.LayoutManager foodListItemsLayoutManager;

    String currentPeriod;
    AppEnv appEnv = AppEnv.getInstance();
    Random rn = new Random();

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getContext());
        currentPeriod = AppEnv.getAppropriateTimePeriod();
        appEnv.registerObserver(this);
        carouselItemList = DBUtils.getFeaturedFoodList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        //Toast.makeText(getContext(), "ข้าว " + DBUtils.searchByCharSequence("ข้าว").size(), Toast.LENGTH_SHORT).show();

        searchBox = (DelayAutoCompleteTextView) view.findViewById(R.id.search_box);
        searchBox.setThreshold(3);
        searchBox.setAdapter(new SearchAutoCompleteAdapter(getContext()));
        searchBox.setLoadingIndicator(
                (android.widget.ProgressBar) view.findViewById(R.id.pb_loading_indicator));
        searchBox.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                FoodListItem food = (FoodListItem) adapterView.getItemAtPosition(i);
                searchBox.setText(food.getName());
                callSearchResultActivity(food.getName());
            }
        });

        searchBox.setOnEditorActionListener(new AutoCompleteTextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i == EditorInfo.IME_ACTION_SEARCH){
                    if(searchBox.getText().toString().length() > 0) {
                        callSearchResultActivity(searchBox.getText().toString());
                    }
                    return true;
                }
                return false;
            }
        });


        //Toast.makeText(getContext(), "food list size " + carouselItemList.size(), Toast.LENGTH_SHORT).show();
        carouselViewPager = (ViewPager) view.findViewById(R.id.carouselViewPager);
        carouselViewPager.setAdapter(new CarouselViewPagerAdapter(getActivity().getSupportFragmentManager(), carouselItemList));
        carouselViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                tapGestureDetector.onTouchEvent(motionEvent);
                return false;
            }
        });
        carouselCircleIndicator = (CirclePageIndicator) view.findViewById(R.id.carouselCircleIndicator);
        carouselCircleIndicator.setViewPager(carouselViewPager);
        carouselCircleIndicator.setSnap(true);

        tapGestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
//                Toast.makeText(getContext(), "Clicked", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), FoodListActivity.class);
                intent.putExtra("FoodList", carouselItemList.get(carouselViewPager.getCurrentItem()));
                intent.putExtra("RequestCode", FoodListActivity.RequestCode.CALL_ACTIVITY_WITH_FEATURED_FOOD);
                startActivityForResult(intent, FoodListActivity.RequestCode.CALL_ACTIVITY_WITH_FEATURED_FOOD);
                return false;
            }
        });

        foodListItemsRecyclerView = (RecyclerView) view.findViewById(R.id.food_list_item_recycler_view);
        foodListItemsRecyclerView.setHasFixedSize(true);

        foodListItemsLayoutManager = new LinearLayoutManager(getContext());
        foodListItemsRecyclerView.setLayoutManager(foodListItemsLayoutManager);

        foodListItemsRecyclerViewAdapter = new FoodListRecyclerViewAdapter(getContext(), appEnv.getFoodListItems(), new FoodListRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(FoodListItem item) {
                Intent intent = new Intent(getActivity(), FoodDetailActivity.class);
                intent.putExtra("food_info", item);
                startActivityForResult(intent, FoodDetailActivity.RequestCode.CALL_ACTIVITY_WITH_INFORMATION);
            }
        });
        foodListItemsRecyclerView.setAdapter(foodListItemsRecyclerViewAdapter);
        foodListItemsRecyclerView.setNestedScrollingEnabled(false);



        setCategoryImage(view);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.random_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int randomNumber = rn.nextInt(appEnv.getFoodListItems().size());
                Intent intent = new Intent(getContext(), FoodDetailActivity.class);
                intent.putExtra("food_info", appEnv.getFoodListItems().get(randomNumber));
                startActivityForResult(intent, FoodDetailActivity.RequestCode.CALL_ACTIVITY_WITH_INFORMATION);
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        appEnv.checkForUpdate();

    }

    protected void setCategoryImage(View view){
        foodsCategoryList = DBUtils.getFoodCategoryList();
        Log.d("CategorySize", "setCategoryImage() called with: view = [" + view + "] size = " + foodsCategoryList.size());

        LinearLayout foodsCategoryLinearLayout = (LinearLayout) view.findViewById(R.id.foods_category_linear_layout);
        final LayoutInflater inflater = (LayoutInflater) getContext().getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);



        for (int i = 0; i < foodsCategoryList.size(); i++) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            if(i != foodsCategoryList.size() - 1){
                layoutParams.setMargins(0, 0, 16, 0);
            }

            RelativeLayout categoryItemRelativeLayout = (RelativeLayout)inflater.inflate(R.layout.catagory_item, null);
            layoutParams.gravity = Gravity.CENTER;
            ImageView foodsCategoryImage = (ImageView) categoryItemRelativeLayout.findViewById(R.id.category_image);
            TextView foodsCategoryName = (TextView) categoryItemRelativeLayout.findViewById(R.id.category_name);
            Picasso.with(getContext()).load(foodsCategoryList.get(i).getImgUrl()).transform(new CircleTransform()).into(foodsCategoryImage);
            foodsCategoryName.setText(foodsCategoryList.get(i).getName());
            categoryItemRelativeLayout.setLayoutParams(layoutParams);
            final int finalI = i;
            categoryItemRelativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getContext(), FoodListActivity.class);
                    intent.putExtra("FoodsCategory", foodsCategoryList.get(finalI));
                    intent.putExtra("RequestCode", FoodListActivity.RequestCode.CALL_ACTIVITY_WITH_CATEGORY_TAG);
                    startActivityForResult(intent, FoodListActivity.RequestCode.CALL_ACTIVITY_WITH_CATEGORY_TAG);
                }
            });
            foodsCategoryLinearLayout.addView(categoryItemRelativeLayout);

        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case FoodDetailActivity.RequestCode.CALL_ACTIVITY_WITH_INFORMATION:
            case FoodListActivity.RequestCode.CALL_ACTIVITY_WITH_CATEGORY_TAG:
            case FoodListActivity.RequestCode.CALL_ACTIVITY_WITH_FEATURED_FOOD:
            case SearchResultActivity.RequestCode.CALL_ACTIVITY_WITH_STRING:
                if(resultCode == FoodDetailActivity.ResponseCode.ADD_FOOD){
                    FoodListItem food = (FoodListItem)data.getSerializableExtra("food_info");
                    DBUtils.addEatenData(appEnv.getUserData().getObjectId(), food.getObjectId(), food.getName(), food.getCalories());
                    appEnv.getTodayEatenFoodList().add(new FoodListItemMinimal(food.getObjectId(), appEnv.getUserData().getObjectId(), food.getName(), food.getCalories(), new Date(System.currentTimeMillis())));
                    appEnv.addEatenCalories(food.getCalories());
                    Toast.makeText(getContext(), food.getName() + " ถูกเพิ่มในรายการกินแล้ว", Toast.LENGTH_SHORT).show();
                }else{

                }
                break;
        }
    }

    @Override
    public void onDataUpdate() {
        if(!currentPeriod.equals(appEnv.getCurrentPeriod())){
            //Toast.makeText(getContext(), "onTimeUpdated", Toast.LENGTH_SHORT).show();
            currentPeriod = appEnv.getCurrentPeriod();
        }
        foodListItemsRecyclerViewAdapter.notifyDataSetChanged();
        //Toast.makeText(getContext(), "notify " + appEnv.getFoodListItems().size(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            appEnv.checkForUpdate();
        }
    }

    private void callSearchResultActivity(String constraint){
        Intent intent = new Intent(getContext(), SearchResultActivity.class);
        intent.putExtra("Constraint", constraint);
        startActivityForResult(intent, SearchResultActivity.RequestCode.CALL_ACTIVITY_WITH_STRING);
    }
}
