package com.example.madooding.healthpy;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;

public class Splash extends AppCompatActivity {
    private Handler handler;
    private Runnable runnable;
    private long delay_time;
    private long time = 3000L;
    private CallbackManager callbackManager;
    private AccessToken accessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        FacebookSdk.sdkInitialize(getApplicationContext());

        handler = new Handler();

        runnable = new Runnable() {
            public void run() {
                Intent intent;
                if(AccessToken.getCurrentAccessToken() == null || AccessToken.getCurrentAccessToken().isExpired()){
                    intent = new Intent(Splash.this, FacebookLoginActivity.class);
                }else{
                    intent = new Intent(Splash.this, MainActivity.class);
                }
                startActivity(intent);
                finish();
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        delay_time = time;
        handler.postDelayed(runnable, delay_time);
        time = System.currentTimeMillis();
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
        time = delay_time - (System.currentTimeMillis() - time);
    }
}
