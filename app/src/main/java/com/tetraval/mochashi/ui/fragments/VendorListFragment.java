package com.tetraval.mochashi.ui.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tetraval.mochashi.R;
import com.tetraval.mochashi.data.adapters.VendorDeliveryAdapter;
import com.tetraval.mochashi.data.models.VendorDeliveryModel;

import java.util.ArrayList;
import java.util.List;

public class VendorListFragment extends Fragment {


    List<VendorDeliveryModel> vendorDeliveryModelList;
    VendorDeliveryAdapter vendorDeliveryAdapter;
    RecyclerView recyclerDelVendor;

    public VendorListFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_vendor_list, container, false);

        vendorDeliveryModelList = new ArrayList<>();
        recyclerDelVendor = view.findViewById(R.id.recyclerDelVendor);
        recyclerDelVendor.setLayoutManager(new LinearLayoutManager(getContext()));

        DatabaseReference vendordeliveryRef = FirebaseDatabase.getInstance().getReference("farmer-delivery");
        vendordeliveryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                vendorDeliveryModelList.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    VendorDeliveryModel vendorDeliveryModel = new VendorDeliveryModel(
                            dataSnapshot1.child("f_id").getValue().toString(),
                            dataSnapshot1.child("f_name").getValue().toString(),
                            dataSnapshot1.child("f_location").getValue().toString(),
                            dataSnapshot1.child("f_category").getValue().toString(),
                            dataSnapshot1.child("f_rate").getValue().toString(),
                            dataSnapshot1.child("f_hosted").getValue().toString(),
                            dataSnapshot1.child("f_booked").getValue().toString(),
                            dataSnapshot1.child("f_received").getValue().toString()
                    );

                    vendorDeliveryModelList.add(vendorDeliveryModel);
                }


                vendorDeliveryAdapter = new VendorDeliveryAdapter(vendorDeliveryModelList, getContext());
                recyclerDelVendor.setAdapter(vendorDeliveryAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;

    }

}
