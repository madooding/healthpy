package com.example.madooding.healthpy.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.madooding.healthpy.R;
import com.example.madooding.healthpy.model.FoodListItem;
import com.example.madooding.healthpy.model.FoodListItemMinimal;

import java.util.List;

/**
 * Created by madooding on 12/6/2016 AD.
 */

public class EatenSummaryRecyclerViewAdapter extends RecyclerView.Adapter<EatenSummaryRecyclerViewAdapter.ViewHolder>{
    private List<FoodListItemMinimal> list;
    private Context context;
    private OnItemDeleteListener listener;

    public interface OnItemDeleteListener{
        void onItemDelete(String objectId);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView eatenTime;
        public TextView foodName;
        public TextView foodCalories;
        public ImageView deleteBtn;
        public LinearLayout wrapper;


        public ViewHolder(View itemView) {
            super(itemView);
            this.eatenTime = (TextView) itemView.findViewById(R.id.eaten_summary_item_time);
            this.foodName = (TextView) itemView.findViewById(R.id.eaten_summary_item_name);
            this.foodCalories = (TextView) itemView.findViewById(R.id.eaten_summary_item_calories);
            this.deleteBtn = (ImageView) itemView.findViewById(R.id.eaten_summary_item_delete_btn);
            this.wrapper = (LinearLayout) itemView.findViewById(R.id.bottom_wrapper);

        }

        public void bind(final FoodListItemMinimal item, final OnItemDeleteListener listener){
            wrapper.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemDelete(item.getObjectId());
                }
            });
        }
    }

    public EatenSummaryRecyclerViewAdapter(Context context, List<FoodListItemMinimal> list, OnItemDeleteListener listener){
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.eaten_summary_list_row, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        FoodListItemMinimal foodListItem = list.get(position);

        String time = (String) DateFormat.format("hh:mm", foodListItem.getEatenDate());
        holder.foodName.setText(foodListItem.getName());
        holder.foodCalories.setText(Integer.toString(foodListItem.getCalories()) + " Kcal");
        holder.eatenTime.setText(time+ " น.");
        holder.bind(foodListItem, listener);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
