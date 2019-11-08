package com.tetraval.mochashi.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import com.tetraval.mochashi.R;
import com.tetraval.mochashi.authmodule.LoginActivity;

public class WelcomeActivity extends AppCompatActivity {

    SharedPreferences pref;
    Button button3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        button3 = findViewById(R.id.button3);

        pref =  getApplicationContext().getSharedPreferences("welcome", 0);
        if (pref.getInt("state", -1) == 1){
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
        }

        button3.setOnClickListener(view -> {
            SharedPreferences.Editor editor = pref.edit();
            editor.putInt("state", 1);
            editor.apply();
            Intent intent=new Intent(getApplicationContext(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

    }
}
