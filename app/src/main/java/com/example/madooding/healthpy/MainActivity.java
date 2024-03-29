package com.example.madooding.healthpy;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cengalabs.flatui.FlatUI;
import com.example.madooding.healthpy.adapter.MainFragmentPagerAdapter;
import com.example.madooding.healthpy.listener.MainViewPagerListener;
import com.example.madooding.healthpy.model.UserData;
import com.example.madooding.healthpy.utility.AppEnv;
import com.example.madooding.healthpy.utility.CircularTransformation;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.squareup.picasso.Picasso;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    UserData userData;
    AppEnv appEnv;
    LoginManager loginManager;
    private ImageView imageView;
    private TextView navbarName;
    private TextView email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("DB HelvethaicaMon X Bd.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FlatUI.initDefaultValues(getApplicationContext());
        Bundle bundle = getIntent().getExtras();

        FacebookSdk.sdkInitialize(getApplicationContext());
        loginManager = LoginManager.getInstance();
        appEnv = AppEnv.getInstance();



//        Toast.makeText(this, "now is time for " + AppEnv.getAppropriateTimePeriod(),  Toast.LENGTH_SHORT).show();


        //All about Drawer
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        //Set up navigation view
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);


        imageView = (ImageView) header.findViewById(R.id.imageView);
        navbarName = (TextView) header.findViewById(R.id.navbar_name);
        email = (TextView) header.findViewById(R.id.textView);
        navbarName.setText(appEnv.getUserData().getName() + " " + appEnv.getUserData().getLastName());
        email.setText(appEnv.getUserData().getEmail());
        Picasso.with(this).load(appEnv.getUserData().getProfileImgURI()).transform(new CircularTransformation()).into(imageView);


        //Set up view pager and tab segment
        final ViewPager mainViewPager = (ViewPager) findViewById(R.id.mainViewPager);
        mainViewPager.setAdapter(new MainFragmentPagerAdapter(getSupportFragmentManager()));
        setTitle(getResources().getStringArray(R.array.main_viewpager_title)[1]);
        mainViewPager.setCurrentItem(1); //Set to center page
        mainViewPager.setOnPageChangeListener(new MainViewPagerListener(){

            @Override
            public void onPageScrollStateChanged(int state) {
                String[] mainViewPagerTitles = getResources().getStringArray(R.array.main_viewpager_title);
                setTitle(mainViewPagerTitles[mainViewPager.getCurrentItem()]);
            }
        });

        //This is about tab
        TabLayout mainViewSelectorTab = (TabLayout) findViewById(R.id.tab_on_actionbar);
        mainViewSelectorTab.setTabGravity(TabLayout.GRAVITY_FILL);
        mainViewSelectorTab.setupWithViewPager(mainViewPager);


        //Set up icon for main tab
        for (int i = 0; i < mainViewSelectorTab.getTabCount(); i++) {
            int[] iconId = {R.drawable.calendar, R.drawable.lamp, R.drawable.today};
            mainViewSelectorTab.getTabAt(i).setIcon(iconId[i]);
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navView = (NavigationView) findViewById(R.id.nav_view);
        drawer.closeDrawer(GravityCompat.START);
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_edit_profile) {
            // Handle the camera action
            Intent intent = new Intent(this, InformationGatheringActivity.class);
            intent.putExtra("RequestCode", InformationGatheringActivity.RequestCode.UPDATE_USER_INFORMATION);
            startActivityForResult(intent, InformationGatheringActivity.RequestCode.UPDATE_USER_INFORMATION);
        } else if (id == R.id.nav_logout) {

            item.setChecked(false);
            navView.setElevation(40);
            navView.setSelected(false);
//            Toast.makeText(MainActivity.this, "Logout nav is clicked", Toast.LENGTH_SHORT).show();
            loginManager.logOut();
            Intent intent = new Intent(this, FacebookLoginActivity.class);
            startActivity(intent);
            finish();

        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case InformationGatheringActivity.RequestCode.UPDATE_USER_INFORMATION:
                if(resultCode == InformationGatheringActivity.ResponseCode.UPDATE_USER_INFORMATION_COMPLETE){
//                    Toast.makeText(this, "Update complete", Toast.LENGTH_SHORT).show();
                    UserData userData = (UserData)data.getSerializableExtra("UserData");
                    appEnv.setUserData(userData);
                    appEnv.setUpdateSignal(true);
                }
                break;
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
