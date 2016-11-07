package com.example.madooding.healthpy;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class FoodDetailActivity
        extends AppCompatActivity
        implements ObservableScrollViewCallbacks {

    private View imageView;
    private View toolbarView;
    private ObservableScrollView scrollView;
    private int parallaxImageHeight, actionBarSize;
    private TextView titleTextView;
    private View overlayView;
    private AppBarLayout appBar;
    Display display;
    Point size = new Point();
    private int titleTextMargin;
    private LinearLayout titleContainer;
    private int screenWidth;
    private int titleBigTextSize;
    private String foodName;
    private String foodDetail;
    private int titleTextLeftIndentation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("DB HelvethaicaMon X Bd.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        setContentView(R.layout.activity_food_detail);

        setSupportActionBar((Toolbar) findViewById(R.id.food_detail_toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(" ");

        display = getWindowManager().getDefaultDisplay();
        display.getSize(size);
        screenWidth = size.x;

        parallaxImageHeight = getResources().getDimensionPixelOffset(R.dimen.food_detail_parallax_image_height);
        actionBarSize = getResources().getDimensionPixelSize(R.dimen.action_bar_size);
        toolbarView = findViewById(R.id.food_detail_toolbar);

        titleTextView = (TextView) findViewById(R.id.food_detail_title);
        titleTextView.measure(0,0);
        titleTextView.setMinimumHeight(parallaxImageHeight);
        titleContainer = (LinearLayout) findViewById(R.id.food_detail_title_container);
        titleTextMargin = (screenWidth - titleTextView.getMeasuredWidth()) / 2;
        titleContainer.setPadding(titleTextMargin, 0, 0, 0);
        titleBigTextSize = (int)getResources().getDimensionPixelSize(R.dimen.food_detail_title_text_size);
        titleTextLeftIndentation = getResources().getDimensionPixelSize(R.dimen.food_detail_title_padding);

        appBar = (AppBarLayout) findViewById(R.id.food_detail_app_bar);
        appBar.setBackgroundColor(ScrollUtils.getColorWithAlpha(0, getResources().getColor(R.color.colorPrimary)));
        appBar.setMinimumHeight(parallaxImageHeight);

        imageView = findViewById(R.id.food_detail_image);

        scrollView = (ObservableScrollView) findViewById(R.id.food_detail_scroll_view);
        scrollView.setScrollViewCallbacks(this);;


    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {


        imageView.setTranslationY((float)scrollY/2);
        int baseColor = getResources().getColor(R.color.colorPrimary);
        float alpha = Math.min(1, (float) scrollY / (parallaxImageHeight - actionBarSize));
        titleTextView.measure(0,0);
        titleTextMargin = (screenWidth - titleTextView.getMeasuredWidth()) / 2;
        if(alpha == 1){
            setTitle("สวัสดี");
            titleTextView.setText(null);
        }else{
            setTitle(null);
            titleTextView.setText("สวัสดี");
        }
        appBar.setBackgroundColor(ScrollUtils.getColorWithAlpha(alpha, baseColor));
        appBar.setMinimumHeight(Math.max(actionBarSize,parallaxImageHeight - (scrollY)));
        titleTextView.setMinimumHeight(Math.max(actionBarSize,parallaxImageHeight - (scrollY)));
        titleTextView.setTextSize(28 + (int)((72 - 28) * (1 - alpha)));
        titleContainer.setPadding(Math.max(titleTextLeftIndentation,
                titleTextLeftIndentation + (int)((titleTextMargin - titleTextLeftIndentation) * (1 - alpha))), 0, 0, 0);

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
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
