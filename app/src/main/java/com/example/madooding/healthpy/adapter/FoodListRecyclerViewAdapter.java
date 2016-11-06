package com.example.madooding.healthpy.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.madooding.healthpy.MainActivity;
import com.example.madooding.healthpy.R;
import com.example.madooding.healthpy.model.FoodListItem;

import java.util.List;

/**
 * Created by madooding on 11/6/2016 AD.
 */
public class FoodListRecyclerViewAdapter extends RecyclerView.Adapter<FoodListRecyclerViewAdapter.ViewHolder> {
    private List<FoodListItem> foodListItems;
    private Context context;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(FoodListItem item);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView foodName;
        public TextView foodDescription;
        public TextView foodCalories;
        public ImageView foodImg;
        public ViewHolder(View itemView) {
            super(itemView);
            this.foodName = (TextView) itemView.findViewById(R.id.food_list_item_name);
            this.foodDescription = (TextView) itemView.findViewById(R.id.food_list_item_description);
            this.foodCalories = (TextView) itemView.findViewById(R.id.food_list_item_calories);
            this.foodImg = (ImageView) itemView.findViewById(R.id.food_list_item_image);
        }

        public void bind(final FoodListItem item, final OnItemClickListener listener){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(item);
                }
            });
        }
    }

    public FoodListRecyclerViewAdapter(Context context, List<FoodListItem> foodListItems, OnItemClickListener listener){
        this.context = context;
        this.foodListItems = foodListItems;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.food_list_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        return  viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        FoodListItem foodListItem = foodListItems.get(position);

        holder.foodName.setText(foodListItem.getName());
        holder.foodDescription.setText(foodListItem.getDescription());
        holder.foodCalories.setText(Integer.toString(foodListItem.getCalories()));
        holder.foodImg.setImageResource(foodListItem.getImageSrc());

        holder.bind(foodListItem, listener);

    }


    @Override
    public int getItemCount() {
        return foodListItems.size();
    }



}
