package com.example.madooding.healthpy.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
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

    private class ViewHolder{
        public TextView foodName;
        public TextView foodDescription;
        public TextView foodCalories;
        public ImageView foodImg;

        public ViewHolder(View convertView){
            this.foodName = (TextView) convertView.findViewById(R.id.food_list_item_name);
            this.foodDescription = (TextView) convertView.findViewById(R.id.food_list_item_description);
            this.foodCalories = (TextView) convertView.findViewById(R.id.food_list_item_calories);
            this.foodImg = (ImageView) convertView.findViewById(R.id.food_list_item_image);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FoodListItem foodListItem = foodListItems.get(position);
        ViewHolder viewHolder;

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        if(convertView == null) {
            convertView = inflater.inflate(R.layout.food_list_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else  {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.foodName.setText(foodListItem.getName());

        viewHolder.foodDescription.setText(foodListItem.getDescription());

        viewHolder.foodCalories.setText(Integer.toString(foodListItem.getCalories()));

        URL url = null;
        try {
            url = new URL(foodListItem.getImageUrl());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Bitmap bmp = null;
        try {
            bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        viewHolder.foodImg.setImageBitmap(bmp);

        return convertView;
    }

    @Override
    public int getCount() {
        return foodListItems.size();
    }

}
