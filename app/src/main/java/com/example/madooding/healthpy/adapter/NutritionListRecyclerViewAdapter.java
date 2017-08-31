package com.example.madooding.healthpy.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.madooding.healthpy.R;
import com.example.madooding.healthpy.model.NutritionType;

import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by madooding on 11/8/2016 AD.
 */

public class NutritionListRecyclerViewAdapter extends RecyclerView.Adapter<NutritionListRecyclerViewAdapter.ViewHolder> {

    private List<NutritionType> nutritionTypeList;
    private Context context;


    public NutritionListRecyclerViewAdapter(Context context, List<NutritionType> nutritionTypeList){
        this.context = context;
        this.nutritionTypeList = nutritionTypeList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.nutrition_list_layout, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        NutritionType nutritionType = nutritionTypeList.get(position);

        holder.name.setText(nutritionType.getThaiName());
        holder.value.setText(Float.toString(nutritionType.getValue()) + " g");
        holder.color.setBackgroundColor(nutritionType.getColor());
    }

    @Override
    public int getItemCount() {
        return nutritionTypeList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView name;
        public TextView value;
        public LinearLayout color;

        public ViewHolder(View itemView) {
            super(itemView);
            this.name = (TextView) itemView.findViewById(R.id.food_detail_nutrition_name);
            this.value = (TextView) itemView.findViewById(R.id.food_detail_nutrition_value);
            this.color = (LinearLayout) itemView.findViewById(R.id.food_detail_nutrition_color);
        }
    }
}
