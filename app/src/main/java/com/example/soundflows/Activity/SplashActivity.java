package com.example.soundflows.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.soundflows.R;
import com.example.soundflows.constant.LoginConstant;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        try {
            LoginConstant.isFromPush = getIntent().getExtras().getBoolean("ispushnoti", false);
        } catch (Exception e) {
            LoginConstant.isFromPush = false;
        }
        try {
            LoginConstant.isFromNoti = getIntent().getExtras().getBoolean("isnoti", false);
        } catch (Exception e) {
            LoginConstant.isFromNoti = false;
        }

        if(!LoginConstant.isFromNoti) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    openMainActivity();
                }
            }, 1000);
        } else {
            openMainActivity();
        }
    }

    private void openMainActivity() {
        Intent intent= new Intent(SplashActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}