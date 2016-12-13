package com.example.madooding.healthpy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.madooding.healthpy.R;
import com.example.madooding.healthpy.model.FoodListItem;
import com.example.madooding.healthpy.model.FoodListItemMinimal;
import com.example.madooding.healthpy.utility.DBUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by madooding on 12/13/2016 AD.
 */

public class SearchAutoCompleteAdapter extends BaseAdapter implements Filterable{

    public static final int MAX_RESULT = 8;
    private Context context;
    private List<FoodListItem> foodListItems = new ArrayList<FoodListItem>();

    public SearchAutoCompleteAdapter(Context context){
        this.context = context;
    }

    @Override
    public int getCount() {
        return foodListItems.size();
    }

    @Override
    public FoodListItem getItem(int i) {
        return foodListItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.search_list_item, null);
        }
        ((TextView)view.findViewById(R.id.search_list_title)).setText(getItem(i).getName());
        return view;
    }


    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint != null) {
                    List<FoodListItem> foods = DBUtils.searchByCharSequence(constraint.toString());

                    // Assign the data to the FilterResults
                    filterResults.values = foods;
                    filterResults.count = foods.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    foodListItems = (List<FoodListItem>) results.values;
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }};
        return filter;
    }
}
