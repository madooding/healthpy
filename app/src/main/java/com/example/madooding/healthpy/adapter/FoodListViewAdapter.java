package com.example.madooding.healthpy.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.madooding.healthpy.R;
import com.example.madooding.healthpy.model.FoodListItem;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by madooding on 10/31/2016 AD.
 */
public class FoodListViewAdapter extends ArrayAdapter<FoodListItem> {
    private final List<FoodListItem> foodListItems;

    public FoodListViewAdapter(Context context, int resource, List<FoodListItem> foodList) {
        super(context, resource, foodList);
        this.foodListItems = foodList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FoodListItem foodListItem = foodListItems.get(position);

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);

        View foodCardView = inflater.inflate(R.layout.food_list_item, null);

        TextView foodName = (TextView) foodCardView.findViewById(R.id.food_list_item_name);
        foodName.setText(foodListItem.getName());

        TextView foodDescription = (TextView) foodCardView.findViewById(R.id.food_list_item_description);
        foodDescription.setText(foodListItem.getDescription());

        TextView foodCalories = (TextView) foodCardView.findViewById(R.id.food_list_item_calories);
        foodCalories.setText(Integer.toString(foodListItem.getCalories()));

        ImageView foodImg = (ImageView) foodCardView.findViewById(R.id.food_list_item_image);
        foodImg.setImageResource(foodListItem.getImageSrc());

        return foodCardView;
    }

    @Override
    public int getCount() {
        return foodListItems.size();
    }
}
