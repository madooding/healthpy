package com.example.madooding.healthpy;

import android.app.ActionBar;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class FoodDetailActivity
        extends AppCompatActivity
        implements ObservableScrollViewCallbacks {

    private View imageView;
    private View toolbarView;
    private ObservableScrollView scrollView;
    private int parallaxImageHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }



        imageView = findViewById(R.id.food_detail_image);

        scrollView = (ObservableScrollView) findViewById(R.id.food_detail_scroll_view);
        scrollView.setScrollViewCallbacks(this);

        parallaxImageHeight = getResources().getDimensionPixelOffset(R.dimen.food_detail_parallax_image_height);


    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
        imageView.setTranslationY((float)scrollY/2);
//        int baseColor = getResources().getColor(R.color.colorPrimary);
//        float alpha = Math.min(1, (float) scrollY / parallaxImageHeight);
//        toolbarView.setBackgroundColor(ScrollUtils.getColorWithAlpha(alpha, baseColor));

    }

    @Override
    public void onDownMotionEvent() {

    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
