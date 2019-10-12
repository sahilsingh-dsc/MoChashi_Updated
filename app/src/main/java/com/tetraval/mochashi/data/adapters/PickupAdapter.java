package com.tetraval.mochashi.data.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tetraval.mochashi.R;
import com.tetraval.mochashi.data.models.PickupModel;

import java.util.List;

public class PickupAdapter extends RecyclerView.Adapter<PickupAdapter.PickupViewHolder> {

    List<PickupModel> pickupModelList;
    Context context;

    public PickupAdapter(List<PickupModel> pickupModelList, Context context) {
        this.pickupModelList = pickupModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public PickupAdapter.PickupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pickup_list_item, parent, false);
        return new PickupViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PickupAdapter.PickupViewHolder holder, int position) {

        PickupModel pickupModel = pickupModelList.get(position);
        holder.txtVendorProductName.setText(pickupModel.getP_productname());
        holder.txtPickupAddress.setText(pickupModel.getP_address());
        holder.txtPayAmt.setText("₹"+pickupModel.getP_chashiamt());
        holder.txtChaName.setText(pickupModel.getP_chashiname());
        holder.txtQty.setText(pickupModel.getP_qty()+" kg(s) for ");

    }

    @Override
    public int getItemCount() {
        return pickupModelList.size();
    }

    public class PickupViewHolder extends RecyclerView.ViewHolder {

        TextView txtVendorProductName, txtPickupAddress, txtPayAmt, txtChaName ,txtQty;

        public PickupViewHolder(@NonNull View itemView) {
            super(itemView);

            txtVendorProductName = itemView.findViewById(R.id.txtVendorProductName);
            txtPickupAddress = itemView.findViewById(R.id.txtPickupAddress);
            txtPayAmt = itemView.findViewById(R.id.txtPayAmt);
            txtChaName = itemView.findViewById(R.id.txtChaName);
            txtQty = itemView.findViewById(R.id.txtQty);

        }
    }
}
