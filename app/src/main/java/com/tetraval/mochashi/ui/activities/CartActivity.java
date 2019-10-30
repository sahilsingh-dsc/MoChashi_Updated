package com.tetraval.mochashi.ui.activities;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tetraval.mochashi.R;
import com.tetraval.mochashi.data.adapters.CartAdapters;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    Toolbar toolbarCart;
    RecyclerView recyclerCart;
    CartAdapters cartAdapters;
//    List<CartModel> cartModelList;
DecimalFormat precision = new DecimalFormat("0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        toolbarCart = findViewById(R.id.toolbarCart);
        setSupportActionBar(toolbarCart);
        getSupportActionBar().setTitle("Cart");
        toolbarCart.setTitleTextColor(Color.WHITE);
        toolbarCart.getOverflowIcon().setColorFilter(Color.WHITE , PorterDuff.Mode.SRC_ATOP);

        recyclerCart = findViewById(R.id.recyclerCart);
      //  cartModelList = new ArrayList<>();
        Context context;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerCart.setLayoutManager(linearLayoutManager);
        fetchCart();

    }

    private void fetchCart(){
//        cartModelList.add(new CartModel("1", "", "Some Product Name Brand Name Some Brand 1", " 2500", "1500", "1000", "Product disc is very good this product is used by many consumers."));
//        cartModelList.add(new CartModel("2", "", "Some Product Name Brand Name Some Brand 2", " 2500", "1500", "1000", "Product disc is very good this product is used by many consumers."));
//        cartModelList.add(new CartModel("3", "", "Some Product Name Brand Name Some Brand 3", " 2500", "1500", "1000", "Product disc is very good this product is used by many consumers."));
//        cartModelList.add(new CartModel("4", "", "Some Product Name Brand Name Some Brand 4", " 2500", "1500", "1000", "Product disc is very good this product is used by many consumers."));
//        cartModelList.add(new CartModel("5", "", "Some Product Name Brand Name Some Brand 5", " 2500", "1500", "1000", "Product disc is very good this product is used by many consumers."));
//        cartModelList.add(new CartModel("6", "", "Some Product Name Brand Name Some Brand 6", " 2500", "1500", "1000", "Product disc is very good this product is used by many consumers."));
//        cartModelList.add(new CartModel("7", "", "Some Product Name Brand Name Some Brand 7", " 2500", "1500", "1000", "Product disc is very good this product is used by many consumers."));
//        cartModelList.add(new CartModel("8", "", "Some Product Name Brand Name Some Brand 8", " 2500", "1500", "1000", "Product disc is very good this product is used by many consumers."));
//        cartModelList.add(new CartModel("9", "", "Some Product Name Brand Name Some Brand 9", " 2500", "1500", "1000", "Product disc is very good this product is used by many consumers."));

//        cartAdapters = new CartAdapters(cartModelList, this);
       // recyclerCart.setAdapter(cartAdapters);

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
            Toast.makeText(CartActivity.this, "Action clicked", Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
