package com.example.madooding.healthpy;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.madooding.healthpy.adapter.CarouselViewPagerAdapter;
import com.example.madooding.healthpy.adapter.CategoryRecyclerViewAdapter;
import com.example.madooding.healthpy.model.CarouselItem;
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
    RecyclerView categoryRecyclerView;
    RecyclerView.Adapter categoryRecyclerViewAdapter;
    RecyclerView.LayoutManager categoryRecyclerViewLayoutMgr;

    //Should retrive from server
    List<FoodsCategory> foodsCategoryList = new ArrayList<FoodsCategory>();
    List<CarouselItem> carouselItemList = new ArrayList<>();

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


        carouselItemList.add(new CarouselItem(R.drawable.image_1));
        carouselItemList.add(new CarouselItem(R.drawable.image_2));

        carouselViewPager = (ViewPager) view.findViewById(R.id.carouselViewPager);
        carouselViewPager.setAdapter(new CarouselViewPagerAdapter(getActivity().getSupportFragmentManager(), carouselItemList));
        carouselCircleIndicator = (CirclePageIndicator) view.findViewById(R.id.carouselCircleIndicator);
        carouselCircleIndicator.setViewPager(carouselViewPager);
        carouselCircleIndicator.setSnap(true);


        //Category Recycler View
//        categoryRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_foods_category);
//        categoryRecyclerView.setHasFixedSize(true);
//
//        categoryRecyclerViewLayoutMgr = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
//        categoryRecyclerView.setLayoutManager(categoryRecyclerViewLayoutMgr);
//
//
//        categoryRecyclerViewAdapter = new CategoryRecyclerViewAdapter(getContext(), foodsCategoryList);
//        categoryRecyclerView.setAdapter(categoryRecyclerViewAdapter);


        setCategoryImage(view);



    }

    protected void setCategoryImage(View view){
        foodsCategoryList.add(new FoodsCategory("Normal", R.drawable.category_1));
        foodsCategoryList.add(new FoodsCategory("Noodle", R.drawable.category_2));
        foodsCategoryList.add(new FoodsCategory("Dessert", R.drawable.category_3));
        foodsCategoryList.add(new FoodsCategory("Drink", R.drawable.category_4));



        LinearLayout foodsCategoryLinearLayout = (LinearLayout) view.findViewById(R.id.foods_category_linear_layout);


        for (int i = 0; i < foodsCategoryList.size(); i++) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            if(i != foodsCategoryList.size() - 1){
                layoutParams.setMargins(0, 0, 16, 0);
            }

            layoutParams.gravity = Gravity.CENTER;
            ImageView foodsCategoryItem = new ImageView(getActivity());
            foodsCategoryItem.setImageResource(foodsCategoryList.get(i).getImgSrc());
            foodsCategoryItem.setLayoutParams(layoutParams);

            foodsCategoryLinearLayout.addView(foodsCategoryItem);

        }

    }

}
