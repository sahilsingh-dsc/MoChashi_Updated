package com.tetraval.mochashi.data.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tetraval.mochashi.R;
import com.tetraval.mochashi.data.models.DeliveryModel;
import com.tetraval.mochashi.data.models.PickupModel;

import java.util.ArrayList;
import java.util.List;

public class DeliveryAdapters extends RecyclerView.Adapter<DeliveryAdapters.DeliveryViewHolder> {

    List<DeliveryModel> deliveryModelList;
    Context context;
    List<PickupModel> pickupModelList;
    PickupAdapter pickupAdapter;

    public DeliveryAdapters(List<DeliveryModel> deliveryModelList, Context context) {
        this.deliveryModelList = deliveryModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public DeliveryAdapters.DeliveryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.delivery_list_item, parent, false);
        pickupModelList = new ArrayList<>();
        pickupModelList.clear();
        return new DeliveryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeliveryAdapters.DeliveryViewHolder holder, int position) {
        DeliveryModel deliveryModel = deliveryModelList.get(position);
        holder.txtDelCustomerName.setText(deliveryModel.getD_name());
        holder.txtDelContact.setText(deliveryModel.getD_contact());
        holder.txtCODAmount.setText("₹"+deliveryModel.getD_cod());
        holder.txtCustomerDelivery.setText(deliveryModel.getD_address());

        DatabaseReference deliveryRef = FirebaseDatabase.getInstance().getReference("pickups");
        deliveryRef.child(deliveryModel.getD_id()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                pickupModelList.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        PickupModel pickupModel = new PickupModel(
                                dataSnapshot1.child("pickup_id").getValue().toString(),
                                dataSnapshot1.child("p_chashiamt").getValue().toString(),
                                dataSnapshot1.child("p_chashiname").getValue().toString(),
                                dataSnapshot1.child("p_productname").getValue().toString(),
                                dataSnapshot1.child("p_address").getValue().toString(),
                                dataSnapshot1.child("p_qty").getValue().toString()
                        );
                        pickupModelList.add(pickupModel);
                }
                holder.listPickupAddress.setLayoutManager(new LinearLayoutManager(context));
                pickupAdapter = new PickupAdapter(pickupModelList, context);
                holder.listPickupAddress.setAdapter(pickupAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    @Override
    public int getItemCount() {
        return deliveryModelList.size();
    }

    public class DeliveryViewHolder extends RecyclerView.ViewHolder {

        RecyclerView listPickupAddress;
        TextView txtDelCustomerName, txtDelContact, txtCODAmount, txtCustomerDelivery;

        public DeliveryViewHolder(@NonNull View itemView) {
            super(itemView);

            listPickupAddress = itemView.findViewById(R.id.listPickupAddress);
            txtDelCustomerName = itemView.findViewById(R.id.txtDelCustomerName);
            txtDelContact = itemView.findViewById(R.id.txtDelContact);
            txtCODAmount = itemView.findViewById(R.id.txtCODAmount);
            txtCustomerDelivery = itemView.findViewById(R.id.txtCustomerDelivery);

        }
    }
}
