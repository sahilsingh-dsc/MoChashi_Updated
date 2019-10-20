package com.tetraval.mochashi.chashimodule.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tetraval.mochashi.R;
import com.tetraval.mochashi.authmodule.LoginActivity;
import com.tetraval.mochashi.data.adapters.VendorPdtAdapter;
import com.tetraval.mochashi.data.models.VendorProductModel;
import com.tetraval.mochashi.ui.activities.AddVendorProductActivity;
import com.tetraval.mochashi.utils.AppConst;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class VendorActivity extends AppCompatActivity {

    Toolbar toolbarVendor;
    RecyclerView recyclerVendor;
    List<VendorProductModel> vendorProductModelList;
    VendorPdtAdapter vendorPdtAdapter;
    SharedPreferences preferences, master;
    Button btnAddMoreProduct;
    ConstraintLayout constrainVendor;
    RequestQueue requestQueue;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor);

        toolbarVendor = findViewById(R.id.toolbarVendor);
        setSupportActionBar(toolbarVendor);
        getSupportActionBar().setTitle("My Products");
        toolbarVendor.setTitleTextColor(Color.WHITE);
        toolbarVendor.getOverflowIcon().setColorFilter(Color.WHITE , PorterDuff.Mode.SRC_ATOP);

        progressDialog = new ProgressDialog(VendorActivity.this);
        progressDialog.setMessage("Please Wait...");
        requestQueue = Volley.newRequestQueue(this);

        constrainVendor = findViewById(R.id.constrainVendor);

        preferences = getApplicationContext().getSharedPreferences("loginpref", 0);
        master = getApplicationContext().getSharedPreferences("MASTER", 0);

        recyclerVendor = findViewById(R.id.recyclerVendor);
        recyclerVendor.setLayoutManager(new LinearLayoutManager(this));

        btnAddMoreProduct = findViewById(R.id.btnAddMoreProduct);
        btnAddMoreProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(VendorActivity.this, AddVendorProductActivity.class));
            }
        });

        vendorProductModelList = new ArrayList<>();
        vendorProductModelList.clear();
        progressDialog.show();
        fetchChashiProduct();

//        DatabaseReference addproductRef = FirebaseDatabase.getInstance().getReference("products");
//        addproductRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                vendorProductModelList.clear();
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    for (DataSnapshot snapshot1: snapshot.getChildren()) {
//                        Log.d("snap", snapshot1.toString());
////                        for (DataSnapshot snapshot1: snapshot2.getChildren()){
//                            VendorProductModel vendorProductModel = new VendorProductModel(
//                                    snapshot1.child("p_id").getValue().toString(),
//                                    snapshot1.child("img1").getValue().toString(),
//                                    snapshot1.child("img2").getValue().toString(),
//                                    snapshot1.child("img3").getValue().toString(),
//                                    snapshot1.child("img4").getValue().toString(),
//                                    snapshot1.child("p_name").getValue().toString(),
//                                    snapshot1.child("p_avlqty").getValue().toString(),
//                                    snapshot1.child("p_remain_avlqty").getValue().toString(),
//                                    snapshot1.child("p_rate").getValue().toString(),
//                                    snapshot1.child("p_delivery").getValue().toString()
//                            );
//
//                            vendorProductModelList.add(vendorProductModel);
//
//                    }
//                }
//                if (vendorProductModelList.size() == 0){
//                    Snackbar snackbar = Snackbar
//                            .make(constrainVendor, "Currently there are no products.", Snackbar.LENGTH_LONG);
//                    snackbar.show();
//                }
//                vendorPdtAdapter = new VendorPdtAdapter(vendorProductModelList, VendorActivity.this);
//                vendorPdtAdapter.notifyDataSetChanged();
//                recyclerVendor.setAdapter(vendorPdtAdapter);
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

    }

    private void fetchChashiProduct(){

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerVendor.setLayoutManager(linearLayoutManager);
        StringRequest getRequest = new StringRequest(Request.Method.POST, AppConst.BASE_URL+"User_api/chashi_vendor_product",
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String status =  jsonObject.getString("status");
                            String result =  jsonObject.getString("result");
                            if (status.equals("200")) {
                                JSONObject jsonObject1 = new JSONObject(result);
                                String vendor = jsonObject1.getString("vendor_products");
                                JSONArray jsonArray = new JSONArray(vendor);
                                vendorProductModelList.clear();
                                for (int i = 0; i<jsonArray.length(); i++){
                                    JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                                    VendorProductModel vendorProductModel = new VendorProductModel(
                                            jsonObject2.getString("product_id"),
                                            jsonObject2.getString("category_id"),
                                            jsonObject2.getString("product_name"),
                                            jsonObject2.getString("p_img1"),
                                            jsonObject2.getString("qty_hosted"),
                                            jsonObject2.getString("qty_booked"),
                                            jsonObject2.getString("qty_avl"),
                                            jsonObject2.getString("unit"),
                                            jsonObject2.getString("rate"),
                                            jsonObject2.getString("is_deliver")
                                    );
                                    vendorProductModelList.add(vendorProductModel);
                                }
                                vendorPdtAdapter = new VendorPdtAdapter(vendorProductModelList, VendorActivity.this);
                                recyclerVendor.setAdapter(vendorPdtAdapter);
                                if (vendorProductModelList.isEmpty()){
                                    Snackbar snackbar = Snackbar
                                            .make(constrainVendor, "No products added yet.", Snackbar.LENGTH_LONG);
                                    snackbar.show();
                                    progressDialog.dismiss();
                                }
                                progressDialog.dismiss();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            progressDialog.dismiss();

                        }

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.toString());

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("vendor_id", Objects.requireNonNull(master.getString("user_id", "")));
                return params;
            }
        };

        requestQueue.add(getRequest);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.vendor_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
            if (id == R.id.vendor_menu_logout){
            SharedPreferences.Editor editor = master.edit();
            editor.clear();
            editor.apply();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }



}
