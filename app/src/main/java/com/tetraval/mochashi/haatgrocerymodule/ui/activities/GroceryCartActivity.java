package com.tetraval.mochashi.haatgrocerymodule.ui.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tetraval.mochashi.R;
import com.tetraval.mochashi.authmodule.LoginActivity;
import com.tetraval.mochashi.controller.StartActivity;
import com.tetraval.mochashi.haatgrocerymodule.data.adapters.GroceryCartAdapter;
import com.tetraval.mochashi.haatgrocerymodule.data.models.GroceryCartModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GroceryCartActivity extends AppCompatActivity {

    Toolbar toolbarGroceryCart;
    RecyclerView recyclerGroceryCart;
    List<GroceryCartModel> groceryCartModelList;
    GroceryCartAdapter groceryCartAdapter;
    SharedPreferences master;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_cart);

        toolbarGroceryCart = findViewById(R.id.toolbarGroceryCart);
        setSupportActionBar(toolbarGroceryCart);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Your Cart");
        toolbarGroceryCart.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        Objects.requireNonNull(toolbarGroceryCart.getOverflowIcon()).setColorFilter(Color.WHITE , PorterDuff.Mode.SRC_ATOP);

        recyclerGroceryCart = findViewById(R.id.recyclerGroceryCart);
        recyclerGroceryCart.setLayoutManager(new LinearLayoutManager(this));

        progressDialog = new ProgressDialog(GroceryCartActivity.this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);

        groceryCartModelList = new ArrayList<>();
        groceryCartModelList.clear();

        master = getApplicationContext().getSharedPreferences("MASTER", 0);

        progressDialog.show();
        fetchCartItems();

    }

    private void fetchCartItems(){
        DatabaseReference cartRef = FirebaseDatabase.getInstance().getReference("cart_instance");
        //String uid = master.getString("id", "1");
        cartRef.child("1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                groceryCartModelList.clear();
                for (DataSnapshot cartSnap : dataSnapshot.getChildren()){
                    GroceryCartModel groceryCartModel = new GroceryCartModel(
                            Objects.requireNonNull(cartSnap.child("cart_id").getValue()).toString(),
                            Objects.requireNonNull(cartSnap.child("cart_amount").getValue()).toString(),
                            Objects.requireNonNull(cartSnap.child("cart_quantity").getValue()).toString(),
                            Objects.requireNonNull(cartSnap.child("uid").getValue()).toString(),
                            Objects.requireNonNull(cartSnap.child("product_id").getValue()).toString(),
                            Objects.requireNonNull(cartSnap.child("product_image").getValue()).toString(),
                            Objects.requireNonNull(cartSnap.child("product_name").getValue()).toString(),
                            Objects.requireNonNull(cartSnap.child("product_mrpprice").getValue()).toString(),
                            Objects.requireNonNull(cartSnap.child("product_saleprice").getValue()).toString(),
                            Objects.requireNonNull(cartSnap.child("product_saveamt").getValue()).toString(),
                            Objects.requireNonNull(cartSnap.child("product_cat").getValue()).toString()
                    );
                    groceryCartModelList.add(groceryCartModel);
                    progressDialog.dismiss();
                }

                groceryCartAdapter = new GroceryCartAdapter(groceryCartModelList, getApplicationContext());
                recyclerGroceryCart.setAdapter(groceryCartAdapter);
                progressDialog.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressDialog.dismiss();
            }
        });

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
            return true;
        }
        if (id == R.id.menu_home) {
            startActivity(new Intent(getApplicationContext(), StartActivity.class));
            finish();
            return true;
        }

        if (id == R.id.menu_signout){
            SharedPreferences.Editor editor = master.edit();
            editor.clear();
            editor.apply();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

}
