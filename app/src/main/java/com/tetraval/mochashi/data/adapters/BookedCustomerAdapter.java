package com.tetraval.mochashi.data.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tetraval.mochashi.R;
import com.tetraval.mochashi.data.models.BookedCustomerModel;
import com.tetraval.mochashi.ui.activities.CustomerMapsActivity;

import java.util.List;

public class BookedCustomerAdapter extends RecyclerView.Adapter<BookedCustomerAdapter.BookedViewHolder> {

    List<BookedCustomerModel> bookedCustomerModelList;
    Context context;

    public BookedCustomerAdapter(List<BookedCustomerModel> bookedCustomerModelList, Context context) {
        this.bookedCustomerModelList = bookedCustomerModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public BookedCustomerAdapter.BookedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_list_item, parent, false);
        return new BookedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookedCustomerAdapter.BookedViewHolder holder, int position) {

        final BookedCustomerModel bookedCustomerModel = bookedCustomerModelList.get(position);
        Glide.with(context).load(bookedCustomerModel.getBooking_photo()).placeholder(R.drawable.ic_person_black_24dp).into(holder.imgBookedCustomer);
        holder.txtCustomerName.setText(bookedCustomerModel.getBooking_name());
        holder.txtBookedQty.setText(bookedCustomerModel.getBooking_qty()+"Kg(s)");
        holder.imgCustomerLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent map = new Intent(context, CustomerMapsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putDouble("lat", bookedCustomerModel.getBooking_lat());
                bundle.putDouble("lng", bookedCustomerModel.getBooking_lng());
                bundle.putString("name", bookedCustomerModel.getBooking_name());
                bundle.putString("qty", bookedCustomerModel.getBooking_qty()+"Kg(s)");
                bundle.putString("photo", bookedCustomerModel.getBooking_photo());
                map.putExtras(bundle);
                context.startActivity(map);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookedCustomerModelList.size();
    }

    public class BookedViewHolder extends RecyclerView.ViewHolder {

        ImageView imgBookedCustomer, imgCustomerLocation;
        TextView txtCustomerName, txtBookedQty;

        public BookedViewHolder(@NonNull View itemView) {
            super(itemView);


            imgBookedCustomer = itemView.findViewById(R.id.imgBookedCustomer);
            imgCustomerLocation = itemView.findViewById(R.id.imgCustomerLocation);
            txtCustomerName = itemView.findViewById(R.id.txtCustomerName);
            txtBookedQty = itemView.findViewById(R.id.txtBookedQty);

        }
    }
}
