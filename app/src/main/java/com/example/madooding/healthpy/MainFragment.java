package com.example.madooding.healthpy;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.madooding.healthpy.adapter.CarouselViewPagerAdapter;
import com.example.madooding.healthpy.adapter.FoodListRecyclerViewAdapter;
import com.example.madooding.healthpy.adapter.FoodListViewAdapter;
import com.example.madooding.healthpy.model.CarouselItem;
import com.example.madooding.healthpy.model.FoodListItem;
import com.example.madooding.healthpy.model.FoodsCategory;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    ViewPager carouselViewPager;
    CirclePageIndicator carouselCircleIndicator;

    //Should retrive from server
    List<FoodsCategory> foodsCategoryList = new ArrayList<FoodsCategory>();
    List<CarouselItem> carouselItemList = new ArrayList<>();
    List<FoodListItem> foodListItems = new ArrayList<>();

    private RecyclerView foodListItemsRecyclerView;
    private RecyclerView.Adapter foodListItemsRecyclerViewAdapter;
    private RecyclerView.LayoutManager foodListItemsLayoutManager;


    public MainFragment() {
        // Required empty public constructor
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


        carouselItemList.add(new CarouselItem(R.drawable.image_1, "รวมเมนูชาวหอ"));
        carouselItemList.add(new CarouselItem(R.drawable.image_2, "รวมเมนูไข่"));

        carouselViewPager = (ViewPager) view.findViewById(R.id.carouselViewPager);
        carouselViewPager.setAdapter(new CarouselViewPagerAdapter(getActivity().getSupportFragmentManager(), carouselItemList));
        carouselCircleIndicator = (CirclePageIndicator) view.findViewById(R.id.carouselCircleIndicator);
        carouselCircleIndicator.setViewPager(carouselViewPager);
        carouselCircleIndicator.setSnap(true);


        foodListItems.add(new FoodListItem(R.drawable.food_pic_1, "ขาหมู", "ยอดอาหาร", 369));
        foodListItems.add(new FoodListItem(R.drawable.food_pic_1, "ขาหมูสูตร Low fat", "สูตรนี้จะลดไขมัน เหมาะกับผู้ที่ต้องการลดความอ้วน", 230));

        foodListItemsRecyclerView = (RecyclerView) view.findViewById(R.id.food_list_item_recycler_view);
        foodListItemsRecyclerView.setHasFixedSize(true);

        foodListItemsLayoutManager = new LinearLayoutManager(getContext());
        foodListItemsRecyclerView.setLayoutManager(foodListItemsLayoutManager);

        foodListItemsRecyclerViewAdapter = new FoodListRecyclerViewAdapter(getContext(), foodListItems, new FoodListRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(FoodListItem item) {
                Intent intent = new Intent(getActivity(), FoodDetailActivity.class);
                startActivity(intent);
            }
        });
        foodListItemsRecyclerView.setAdapter(foodListItemsRecyclerViewAdapter);




        setCategoryImage(view);

    }

    protected void setCategoryImage(View view){
        foodsCategoryList.add(new FoodsCategory("Normal", R.drawable.category_1));
        foodsCategoryList.add(new FoodsCategory("Noodle", R.drawable.category_2));
        foodsCategoryList.add(new FoodsCategory("Dessert", R.drawable.category_3));
        foodsCategoryList.add(new FoodsCategory("Drink", R.drawable.category_4));



        LinearLayout foodsCategoryLinearLayout = (LinearLayout) view.findViewById(R.id.foods_category_linear_layout);
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService
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
            foodsCategoryImage.setImageResource(foodsCategoryList.get(i).getImgSrc());
            foodsCategoryName.setText(foodsCategoryList.get(i).getName());
            categoryItemRelativeLayout.setLayoutParams(layoutParams);

            foodsCategoryLinearLayout.addView(categoryItemRelativeLayout);

        }

    }

}
