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
import com.tetraval.mochashi.data.adapters.OrdersAdapter;
import com.tetraval.mochashi.data.models.OrdersModel;

import java.util.ArrayList;
import java.util.List;

public class MyOrdersActivity extends AppCompatActivity {

    Toolbar toolbarMyOrder;
    RecyclerView recyclerOrder;
    List<OrdersModel> ordersModelList;
    OrdersAdapter ordersAdapter;
    SharedPreferences preferences, masterdata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);

        toolbarMyOrder = findViewById(R.id.toolbarMyOrder);
        setSupportActionBar(toolbarMyOrder);
        getSupportActionBar().setTitle("My Orders");
        toolbarMyOrder.setTitleTextColor(Color.WHITE);
        toolbarMyOrder.getOverflowIcon().setColorFilter(Color.WHITE , PorterDuff.Mode.SRC_ATOP);

        recyclerOrder = findViewById(R.id.recyclerOrder);
        ordersModelList = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerOrder.setLayoutManager(linearLayoutManager);

        preferences = getApplicationContext().getSharedPreferences("loginpref", 0);
        masterdata = getApplicationContext().getSharedPreferences("MASTER", 0);

        fetchOrders();

    }

    private void fetchOrders() {

        ordersModelList.add(new OrdersModel("MO00001", "Some Product Some Category 1", "6", "5000", "", "17-07-2019"));
        ordersModelList.add(new OrdersModel("MO00002", "Some Product Some Category 2", "10", "3000", "", "17-07-2019"));
        ordersModelList.add(new OrdersModel("MO00003", "Some Product Some Category 3", "7", "1000", "", "17-07-2019"));
        ordersModelList.add(new OrdersModel("MO00004", "Some Product Some Category 4", "8", "900", "", "17-07-2019"));
        ordersModelList.add(new OrdersModel("MO00005", "Some Product Some Category 5", "5", "600", "", "17-07-2019"));
        ordersModelList.add(new OrdersModel("MO00006", "Some Product Some Category 6", "4", "8000", "", "17-07-2019"));
        ordersModelList.add(new OrdersModel("MO00007", "Some Product Some Category 7", "2", "3000", "", "17-07-2019"));
        ordersModelList.add(new OrdersModel("MO00008", "Some Product Some Category 8", "53", "500", "", "17-07-2019"));
        ordersAdapter = new OrdersAdapter(ordersModelList, this);
        recyclerOrder.setAdapter(ordersAdapter);
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
