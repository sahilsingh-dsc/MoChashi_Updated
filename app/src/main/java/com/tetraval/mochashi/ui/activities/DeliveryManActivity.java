package com.tetraval.mochashi.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tetraval.mochashi.R;
import com.tetraval.mochashi.data.adapters.DeliveryAdapters;
import com.tetraval.mochashi.data.adapters.PickupAdapter;
import com.tetraval.mochashi.data.models.DeliveryModel;
import com.tetraval.mochashi.data.models.PickupModel;

import java.util.ArrayList;
import java.util.List;

public class DeliveryManActivity extends AppCompatActivity {

    Toolbar toolbarDelivery;
    RecyclerView recyclerDelivery;
    List<DeliveryModel> deliveryModelList;
    DeliveryAdapters deliveryAdapters;
    List<PickupModel> pickupModelList;
    PickupAdapter pickupAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_man);

        toolbarDelivery = findViewById(R.id.toolbarDelivery);
        setSupportActionBar(toolbarDelivery);
        getSupportActionBar().setTitle("Delivery Partner");
        toolbarDelivery.setTitleTextColor(Color.WHITE);
        recyclerDelivery = findViewById(R.id.recyclerDelivery);
        deliveryModelList = new ArrayList<>();
        recyclerDelivery.setLayoutManager(new LinearLayoutManager(this));

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
        });
    }
}
