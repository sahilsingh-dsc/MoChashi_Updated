package com.tetraval.mochashi.data.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tetraval.mochashi.R;
import com.tetraval.mochashi.data.models.VendorDeliveryModel;
import com.tetraval.mochashi.data.models.VendorProductModel;

import java.util.HashMap;
import java.util.List;

public class VendorDeliveryAdapter extends RecyclerView.Adapter<VendorDeliveryAdapter.VedorDeliverViewHolder> {


    List<VendorDeliveryModel> vendorDeliveryModelList;
    Context context;

    public VendorDeliveryAdapter(List<VendorDeliveryModel> vendorDeliveryModelList, Context context) {
        this.vendorDeliveryModelList = vendorDeliveryModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public VendorDeliveryAdapter.VedorDeliverViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.farmer_list, parent, false);
        return new VedorDeliverViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VendorDeliveryAdapter.VedorDeliverViewHolder holder, int position) {
        VendorDeliveryModel vendorDeliveryModel = vendorDeliveryModelList.get(position);
        holder.txtFarmerName.setText(vendorDeliveryModel.getF_name());
        holder.txtLocation.setText(vendorDeliveryModel.getF_location());
        holder.txtFarmerCate.setText(vendorDeliveryModel.getF_category());
        holder.txtFramerRate.setText("₹"+Double.parseDouble(vendorDeliveryModel.getF_rate()));
        holder.txtFarmerHosted.setText(Double.parseDouble(vendorDeliveryModel.getF_hosted())+" Kg(s)");
        holder.txtFramerBooked.setText(Double.parseDouble(vendorDeliveryModel.getF_booked())+" Kg(s)");
        holder.txtRece.setText(Double.parseDouble(vendorDeliveryModel.getF_received())+" Kg(s)");

        holder.btnOk.setOnClickListener(view -> {
            String recevied_qty = holder.txtFarmerRece.getText().toString();
            if (recevied_qty.isEmpty()){
                Toast.makeText(context, "Please fill received quantity...", Toast.LENGTH_SHORT).show();
                return;
            }
            double finalbooked = Double.parseDouble(vendorDeliveryModel.getF_booked()) - Double.parseDouble(recevied_qty);
            DatabaseReference vendordeliveryRef = FirebaseDatabase.getInstance().getReference("farmer-delivery");
            if (finalbooked < 0.0){
                Toast.makeText(context, "There is no booked quantity!", Toast.LENGTH_SHORT).show();
            }else {
                vendordeliveryRef.child(vendorDeliveryModel.getF_id()).child("f_received").setValue(recevied_qty);
                vendordeliveryRef.child(vendorDeliveryModel.getF_id()).child("f_booked").setValue(""+finalbooked);
                Toast.makeText(context, "Received Quantity Updated!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return vendorDeliveryModelList.size();
    }

    public class VedorDeliverViewHolder extends RecyclerView.ViewHolder {

        TextView txtFarmerName, txtLocation, txtFarmerCate, txtFramerRate, txtFarmerHosted, txtFramerBooked, txtRece;
        EditText txtFarmerRece;
        Button btnOk;

        public VedorDeliverViewHolder(@NonNull View itemView) {
            super(itemView);
            txtFarmerName = itemView.findViewById(R.id.txtFarmerName);
            txtLocation = itemView.findViewById(R.id.txtLocation);
            txtFarmerCate = itemView.findViewById(R.id.txtFarmerCate);
            txtFramerRate = itemView.findViewById(R.id.txtFramerRate);
            txtFarmerHosted = itemView.findViewById(R.id.txtFarmerHosted);
            txtFramerBooked = itemView.findViewById(R.id.txtFramerBooked);
            txtFarmerRece = itemView.findViewById(R.id.txtFarmerRece);
            txtRece = itemView.findViewById(R.id.txtRece);
            btnOk = itemView.findViewById(R.id.btnOk);
        }
    }
}
