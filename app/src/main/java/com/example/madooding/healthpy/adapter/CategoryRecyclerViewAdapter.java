package com.example.madooding.healthpy.adapter;

import android.content.Context;
import android.icu.util.ULocale;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.madooding.healthpy.R;
import com.example.madooding.healthpy.model.FoodsCategory;

import java.util.List;

/**
 * Created by madooding on 10/27/2016 AD.
 */
public class CategoryRecyclerViewAdapter extends RecyclerView.Adapter<CategoryRecyclerViewAdapter.ViewHolder> {

    private List<FoodsCategory> foodsCategoryList;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView recyclerItemsImage;
        public LinearLayout recyclerLinearLayout;
        public ViewHolder(View view) {
            super(view);

            recyclerItemsImage = (ImageView) view.findViewById(R.id.recycler_image_view);
            recyclerLinearLayout = (LinearLayout) view.findViewById(R.id.recycler_linear_layout);
        }
    }

    public CategoryRecyclerViewAdapter(Context context, List<FoodsCategory> foodsCategoryList){
        this.context = context;
        this.foodsCategoryList = foodsCategoryList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_category_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        FoodsCategory foodsCategory = foodsCategoryList.get(position);

        holder.recyclerItemsImage.setImageResource(foodsCategory.getImgSrc());
        if(position != foodsCategoryList.size()-1){
            holder.recyclerLinearLayout.setPadding(0,0,16,0);
        }

    }


    @Override
    public int getItemCount() {
        return foodsCategoryList.size();
    }
}
