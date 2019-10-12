package com.tetraval.mochashi.ui.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tetraval.mochashi.R;
import com.tetraval.mochashi.authmodule.LoginActivity;
import com.tetraval.mochashi.data.adapters.CreditAdapter;
import com.tetraval.mochashi.data.models.CreditModel;

import java.util.ArrayList;
import java.util.List;

public class CreditActivity extends AppCompatActivity {

    Toolbar toolbarCredit;
    RecyclerView recyclerCredit;
    CreditAdapter creditAdapter;
    List<CreditModel> creditModelList;
    SharedPreferences preferences, masterdata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit);
        toolbarCredit = findViewById(R.id.toolbarCredit);
        setSupportActionBar(toolbarCredit);
        getSupportActionBar().setTitle("My Credits");
        toolbarCredit.setTitleTextColor(Color.WHITE);
        toolbarCredit.getOverflowIcon().setColorFilter(Color.WHITE , PorterDuff.Mode.SRC_ATOP);

        recyclerCredit = findViewById(R.id.recyclerCredit);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerCredit.setLayoutManager(linearLayoutManager);
        creditModelList = new ArrayList<>();

        preferences = getApplicationContext().getSharedPreferences("loginpref", 0);
        masterdata = getApplicationContext().getSharedPreferences("MASTER", 0);


        fetchCredits();

    }

    private void fetchCredits(){

        creditModelList.add(new CreditModel("100", "Last purchase made on MoChashi App", "25-7-2018"));
        creditModelList.add(new CreditModel("500", "Last purchase made on MoChashi App", "25-7-2018"));
        creditModelList.add(new CreditModel("150", "Last purchase made on MoChashi App", "25-7-2018"));
        creditModelList.add(new CreditModel("100", "Last purchase made on MoChashi App", "25-7-2018"));
        creditModelList.add(new CreditModel("200", "Last purchase made on MoChashi App", "25-7-2018"));
        creditModelList.add(new CreditModel("300", "Last purchase made on MoChashi App", "25-7-2018"));
        creditModelList.add(new CreditModel("200", "Last purchase made on MoChashi App", "25-7-2018"));
        creditModelList.add(new CreditModel("100", "Last purchase made on MoChashi App", "25-7-2018"));

        creditAdapter = new CreditAdapter(creditModelList, this);
        recyclerCredit.setAdapter(creditAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.header_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_favorite) {
            startActivity(new Intent(getApplicationContext(), CartActivity.class));
            return true;
        } else if (id == R.id.menu_myaccount){
            startActivity(new Intent(getApplicationContext(), MyAccountActivity.class));
            return true;
        } else if (id == R.id.menu_myorders){
            startActivity(new Intent(getApplicationContext(), MyOrdersActivity.class));
            return  true;
        } else if (id == R.id.menu_mycredits){
            startActivity(new Intent(getApplicationContext(), CreditActivity.class));
            return  true;
        } else if (id == R.id.menu_signout){
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("login_status", 0);
            editor.apply();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
            return  true;
        }
        return super.onOptionsItemSelected(item);
    }

}
