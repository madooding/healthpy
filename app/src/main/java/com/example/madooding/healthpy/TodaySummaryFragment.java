package com.example.madooding.healthpy;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.madooding.healthpy.adapter.EatenSummaryRecyclerViewAdapter;
import com.example.madooding.healthpy.model.FoodListItemMinimal;
import com.example.madooding.healthpy.utility.AppEnv;
import com.example.madooding.healthpy.utility.DBUtils;

import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class TodaySummaryFragment extends Fragment {
    private RecyclerView eatenListView;
    private LinearLayoutManager eatenListViewLayoutManager;
    private AppEnv appEnv = AppEnv.getInstance();
    private List<FoodListItemMinimal> eatenList;
    public TodaySummaryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        eatenList = DBUtils.getEatingListByDate(appEnv.getUserData().getObjectId(), new Date(System.currentTimeMillis()));
        return inflater.inflate(R.layout.fragment_today_summary, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        eatenListView = (RecyclerView) view.findViewById(R.id.today_summary_recycler_view);
        eatenListView.setHasFixedSize(true);
        eatenListViewLayoutManager = new LinearLayoutManager(getContext());
        eatenListView.setLayoutManager(eatenListViewLayoutManager);



    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            eatenListView.setAdapter(new EatenSummaryRecyclerViewAdapter(getContext(), appEnv.getTodayEatenFoodList(), new EatenSummaryRecyclerViewAdapter.OnItemDeleteListener(){
                @Override
                public void onItemDelete(String objectId) {
                    Toast.makeText(getContext(), "food objectid " + objectId, Toast.LENGTH_SHORT).show();
                }
            }));
            Toast.makeText(getContext(), "tick signal detected!", Toast.LENGTH_SHORT).show();
        }
    }
}
