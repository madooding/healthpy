package com.example.madooding.healthpy;

import android.content.Intent;
import android.os.Handler;
import android.os.TokenWatcher;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.example.madooding.healthpy.model.UserData;
import com.example.madooding.healthpy.utility.AppEnv;
import com.example.madooding.healthpy.utility.DBUtils;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.parse.Parse;

public class Splash extends AppCompatActivity {
    private Handler handler;
    private Runnable runnable;
    private long delay_time;
    private long time = 3000L;
    private CallbackManager callbackManager;
    private AccessToken accessToken;
    private UserData userData;
    private AppEnv appEnv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        FacebookSdk.sdkInitialize(getApplicationContext());

        try {
            Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
                    .applicationId(DBUtils.APPLICATION_ID)
                    .server(DBUtils.SERVER_URL)
                    .build()
            );
        }catch (RuntimeException e){
            e.printStackTrace();
        }

        handler = new Handler();

        runnable = new Runnable() {
            public void run() {
                Intent intent;
                AccessToken.refreshCurrentAccessTokenAsync();
                AccessToken accessToken = AccessToken.getCurrentAccessToken();

                boolean isUserRegistered = false;
                try{
                    isUserRegistered = DBUtils.isRegistered(accessToken.getUserId());
                }catch (Exception e){
                    isUserRegistered = false;
                }


                Bundle bundle = new Bundle();
                if(accessToken == null || accessToken.isExpired() || !isUserRegistered){
                    if(accessToken == null){
                        bundle.putCharSequence("isAccessTokenNull", "null");
                    }else{
                        bundle.putCharSequence("isAccessTokenNull", "NotNull");
                    }
                    bundle.putBoolean("isRegistered", isUserRegistered);
                    intent = new Intent(Splash.this, FacebookLoginActivity.class);
                }else{
                    intent = new Intent(Splash.this, MainActivity.class);
                    userData = DBUtils.getUserData(accessToken.getUserId());
                    appEnv = AppEnv.newInstance(userData);
//                    if(DBUtils.isRegistered(Profile.getCurrentProfile().getId())){
//                        Toast.makeText(Splash.this, "Registered", Toast.LENGTH_SHORT).show();
//                    }else{
//                        Toast.makeText(Splash.this, "Unregistered", Toast.LENGTH_SHORT).show();
//                    }
                }
                startActivity(intent, bundle);
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
