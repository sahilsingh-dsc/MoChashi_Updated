package com.tetraval.mochashi.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tetraval.mochashi.R;
import com.tetraval.mochashi.data.adapters.DeliveryAdapters;
import com.tetraval.mochashi.data.adapters.PickupAdapter;
import com.tetraval.mochashi.data.adapters.VendorDeliveryAdapter;
import com.tetraval.mochashi.data.models.DeliveryModel;
import com.tetraval.mochashi.data.models.PickupModel;
import com.tetraval.mochashi.data.models.VendorDeliveryModel;
import com.tetraval.mochashi.utils.AppConst;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeliveryManActivity extends AppCompatActivity {

    Toolbar toolbarDelivery;
    RecyclerView recyclerDelivery;
    List<DeliveryModel> deliveryModelList;
    DeliveryAdapters deliveryAdapters;
   /* List<PickupModel> pickupModelList;
    PickupAdapter pickupAdapter;*/
    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    String pid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_man);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        requestQueue = Volley.newRequestQueue(this);
        toolbarDelivery = findViewById(R.id.toolbarDelivery);
        setSupportActionBar(toolbarDelivery);
        getSupportActionBar().setTitle("Delivery Partner");
        toolbarDelivery.setTitleTextColor(Color.WHITE);
        recyclerDelivery = findViewById(R.id.recyclerDelivery);
        deliveryModelList = new ArrayList<>();
        recyclerDelivery.setLayoutManager(new LinearLayoutManager(this));
        Intent intent=getIntent();
        pid=intent.getStringExtra("pid");
        FetchCustomerlist(pid);
    }

    private void FetchCustomerlist(String pid) {
        progressDialog.show();
        StringRequest getRequest = new StringRequest(Request.Method.POST, AppConst.BASE_URL + "User_api/chashi_booking_customer",
                response -> {
                    Log.d("Response", response);
                    try {
                        progressDialog.dismiss();
                        JSONObject jsonObject = new JSONObject(response);
                        String status = jsonObject.getString("status");
                        String result = jsonObject.getString("result");
                        if (status.equals("200")) {
                            JSONObject jsonObject1 = new JSONObject(result);
                            String orders = jsonObject1.getString("orders");
                            JSONArray jsonArray = new JSONArray(orders);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                                DeliveryModel deliveryModel = new DeliveryModel(
                                        jsonObject2.getString("order_id"),
                                        jsonObject2.getString("name"),
                                        jsonObject2.getString("mobile"),
                                        jsonObject2.getString("price"),
                                        jsonObject2.getString("quantity"),
                                        jsonObject2.getString("address")

                                );
                                deliveryModelList.add(deliveryModel);

                            }
                            deliveryAdapters = new DeliveryAdapters(deliveryModelList, DeliveryManActivity.this);
                            recyclerDelivery.setAdapter(deliveryAdapters);


                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                    }
                },
                error -> {
                    Log.d("Error.Response", error.toString());
                    progressDialog.dismiss();
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("product_id",pid );
                return params;
            }
        };
        requestQueue.add(getRequest);
    }
}
/*
        DatabaseReference deliveryRef = FirebaseDatabase.getInstance().getReference("delivery");
        deliveryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()) {
//                        Toast.makeText(DeliveryManActivity.this, ""+dataSnapshot2.child("d_cod").getValue().toString(), Toast.LENGTH_SHORT).show();
                        DeliveryModel deliveryModel = new DeliveryModel(
                                dataSnapshot2.child("d_id").getValue().toString(),
                                dataSnapshot2.child("d_name").getValue().toString(),
                                dataSnapshot2.child("d_contact").getValue().toString(),
                                dataSnapshot2.child("d_cod").getValue().toString(),
                                dataSnapshot2.child("d_address").getValue().toString()
                        );
                        deliveryModelList.add(deliveryModel);
                    }
                }

                deliveryAdapters = new DeliveryAdapters(deliveryModelList, DeliveryManActivity.this);
                recyclerDelivery.setAdapter(deliveryAdapters);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/