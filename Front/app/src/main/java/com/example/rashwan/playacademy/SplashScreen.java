package com.example.rashwan.playacademy;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        int SPLASH_TIME_OUT = 3000;
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                startActivity(new Intent(SplashScreen.this, Login.class));

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
