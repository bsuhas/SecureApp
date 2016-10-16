package com.vs.kook;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.vs.kook.view.activity.LoginScreenActivity;
import com.vs.kook.view.activity.MainActivity;
import com.vs.kook.view.services.SMSSendService;

/*
 * Created by SUHAS on 27/09/2016.
 */
public class SplashScreenActivity extends AppCompatActivity{
    private static final long SPLASH_DELAY = 3000;
    private Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen_layout);
        mContext = this;
        displayLandingScreen();

        Intent smsServiceIntent = new Intent(mContext, SMSSendService.class);
                    mContext.startService(smsServiceIntent);

    }

    public void displayLandingScreen() {
        Handler handler = new Handler();
        handler.postDelayed(runnable, SplashScreenActivity.SPLASH_DELAY);
    }

    private Runnable runnable = new Runnable() {
        public void run() {
            checkUserLogin();
        }
    };

    private void checkUserLogin() {
//        boolean isLoggedIn = UserPreferences.getInstance(mContext).isUserLogin();
        boolean isLoggedIn = false;
        if (isLoggedIn) {
            Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            Intent loginIntent = new Intent(SplashScreenActivity.this, LoginScreenActivity.class);
            startActivity(loginIntent);
            finish();
        }
    }
}
