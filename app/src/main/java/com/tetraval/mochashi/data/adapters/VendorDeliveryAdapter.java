package com.tetraval.mochashi.data.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tetraval.mochashi.R;
import com.tetraval.mochashi.data.models.VendorDeliveryModel;
import com.tetraval.mochashi.data.models.VendorProductModel;
import com.tetraval.mochashi.ui.activities.DeliveryManActivity;

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
        holder.txtFarmerName.setText(vendorDeliveryModel.getShop_name());
        holder.txtLocation.setText(vendorDeliveryModel.getShop_location());
        holder.txtFarmerCate.setText(vendorDeliveryModel.getProduct_name());
        holder.txtFramerRate.setText("₹"+Double.parseDouble(vendorDeliveryModel.getProduct_rate()));
        holder.txtFarmerHosted.setText(Double.parseDouble(vendorDeliveryModel.getF_hosted())+vendorDeliveryModel.getProduct_unit());
        holder.txtFramerBooked.setText(Double.parseDouble(vendorDeliveryModel.getF_booked())+vendorDeliveryModel.getProduct_unit());

      holder.cardview.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              Intent intent=new Intent(context, DeliveryManActivity.class);
              intent.putExtra("pid",vendorDeliveryModel.getP_id());
              intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
              context.startActivity(intent);

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
        CardView cardview;
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
            cardview = itemView.findViewById(R.id.cardview);
            txtRece = itemView.findViewById(R.id.txtRece);
            btnOk = itemView.findViewById(R.id.btnOk);
        }
    }
}
