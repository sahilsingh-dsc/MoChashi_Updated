package com.tetraval.mochashi.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.tetraval.mochashi.R;
import com.tetraval.mochashi.authmodule.LoginActivity;
import com.tetraval.mochashi.chashimodule.ui.activities.VendorActivity;

import java.util.Objects;

public class SplashActivity extends AppCompatActivity {

    SharedPreferences master;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        master = this.getSharedPreferences("MASTER", 0);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(() -> {
            if (Objects.equals(master.getString("active", "0"), "1")){
                if (Objects.equals(master.getString("user_type", "1"), "4")){
                    startActivity(new Intent(SplashActivity.this, VendorActivity.class));
                    finish();
                }else {
                    startActivity(new Intent(SplashActivity.this, StartActivity.class));
                    finish();
                }
            }else {
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                finish();
            }
        }, 3500);
    }
}
