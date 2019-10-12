package com.tetraval.mochashi.data.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tetraval.mochashi.R;
import com.tetraval.mochashi.data.models.OrdersModel;

import java.util.List;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.OrdersViewHolder> {

    List<OrdersModel> ordersModelList;
    Context context;

    public OrdersAdapter(List<OrdersModel> ordersModelList, Context context) {
        this.ordersModelList = ordersModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public OrdersAdapter.OrdersViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.myorder_list_item, viewGroup, false);
        return new OrdersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrdersAdapter.OrdersViewHolder ordersViewHolder, int i) {

        OrdersModel ordersModel = ordersModelList.get(i);
        ordersViewHolder.imgOrderImage.setImageResource(R.drawable.buscits);
        ordersViewHolder.txtOrderName.setText(ordersModel.getOdr_name());
        ordersViewHolder.txtOrderQuantity.setText(ordersModel.getOdr_qty());
        ordersViewHolder.txtOrderID.setText(ordersModel.getOdr_id());
        ordersViewHolder.txtOrderDate.setText(ordersModel.getOdr_date());
        ordersViewHolder.txtOrderAmt.setText("₹"+ordersModel.getTotal_amount());

    }

    @Override
    public int getItemCount() {
        return ordersModelList.size();
    }

    public class OrdersViewHolder extends RecyclerView.ViewHolder {

        ImageView imgOrderImage;
        TextView txtOrderName, txtOrderQuantity, txtOrderID, txtOrderDate, txtOrderAmt;

        public OrdersViewHolder(@NonNull View itemView) {
            super(itemView);

            imgOrderImage = itemView.findViewById(R.id.imgOrderImage);
            txtOrderName = itemView.findViewById(R.id.txtOrderName);
            txtOrderQuantity = itemView.findViewById(R.id.txtOrderQuantity);
            txtOrderID = itemView.findViewById(R.id.txtOrderID);
            txtOrderDate = itemView.findViewById(R.id.txtOrderDate);
            txtOrderAmt = itemView.findViewById(R.id.txtOrderAmt);

        }
    }
}
