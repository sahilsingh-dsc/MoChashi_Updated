package com.tetraval.mochashi.controller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.tetraval.mochashi.R;
import com.tetraval.mochashi.chashimodule.ui.activities.ChashiCategoryActivity;
import com.tetraval.mochashi.haatgrocerymodule.ui.activities.GroceryShopActivity;

public class StartActivity extends AppCompatActivity {


    TextView txtCustomerName;
    CardView cardChashi, cardHaat, cardGrocery;
    SharedPreferences master;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        master = this.getSharedPreferences("MASTER", 0);

        cardChashi = findViewById(R.id.cardChashi);
        cardHaat = findViewById(R.id.cardHaat);
        cardGrocery = findViewById(R.id.cardGrocery);
        txtCustomerName = findViewById(R.id.txtCustomerName);
        txtCustomerName.setText("Welcome, "+master.getString("fname", "Welcome User"));

        cardChashi.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), ChashiCategoryActivity.class);
            startActivity(intent);
            finish();
        });

        cardHaat.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), GroceryShopActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("account_status", 3);
            intent.putExtras(bundle);
            startActivity(intent);
            finish();
        });

        cardGrocery.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), GroceryShopActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("account_status", 2);
            intent.putExtras(bundle);
            startActivity(intent);
            finish();
        });
    }
}
