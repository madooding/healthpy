package com.example.madooding.healthpy;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.cengalabs.flatui.FlatUI;
import com.example.madooding.healthpy.utility.DBUtils;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.parse.Parse;

import java.util.Arrays;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class FacebookLoginActivity extends AppCompatActivity {
    private CallbackManager callbackManager;
    private Button facebookLoginButton;
    private String name = "Ding";
    private ProfileTracker profileTracker;
    protected AccessToken accessToken;
    private  Intent intent;
    private  Bundle bundle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("DB HelvethaicaMon X Bd.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        setContentView(R.layout.activity_facebook_login);


        FlatUI.initDefaultValues(getApplicationContext());
        facebookLoginButton = (Button) findViewById(R.id.facebook_login_button);

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        accessToken = AccessToken.getCurrentAccessToken();


        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Toast.makeText(FacebookLoginActivity.this, "Success!, Hello, " + name, Toast.LENGTH_SHORT).show();
                        accessToken = AccessToken.getCurrentAccessToken();
                        doRegister();

                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(FacebookLoginActivity.this, "Cancel", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Toast.makeText(FacebookLoginActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                });

        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                name = currentProfile.getName();
            }
        };



        facebookLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(accessToken == null || accessToken.isExpired()) {
                    callFacebookLogin();
                }else{
                    Toast.makeText(FacebookLoginActivity.this, "You are already logged in.", Toast.LENGTH_SHORT).show();
                    doRegister();
                }

            }
        });



    }


    private void doRegister(){
        boolean isRegisterd = getIntent().getBooleanExtra("isRegistered", false);
        if(!isRegisterd){
            intent = new Intent(this, InformationGatheringActivity.class);
        }else{
            intent = new Intent(this, MainActivity.class);
        }
        startActivity(intent);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    private void callFacebookLogin(){
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email", "user_birthday"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        profileTracker.stopTracking();
    }
}
