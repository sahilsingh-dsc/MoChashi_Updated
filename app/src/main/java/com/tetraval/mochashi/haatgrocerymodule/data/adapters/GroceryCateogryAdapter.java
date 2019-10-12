package com.tetraval.mochashi.haatgrocerymodule.data.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tetraval.mochashi.R;
import com.tetraval.mochashi.haatgrocerymodule.data.models.GroceryCategoryModel;
import com.tetraval.mochashi.haatgrocerymodule.ui.activities.GroceryProductActivity;

import java.util.ArrayList;
import java.util.List;

public class GroceryCateogryAdapter extends RecyclerView.Adapter<GroceryCateogryAdapter.GroceryCatViewHolder> {

    List<GroceryCategoryModel> groceryCategoryModelList;
    Context context;
    String[] product_cat;

    public GroceryCateogryAdapter(List<GroceryCategoryModel> groceryCategoryModelList, Context context) {
        this.groceryCategoryModelList = groceryCategoryModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public GroceryCateogryAdapter.GroceryCatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_list_item, parent, false);
        return new GroceryCatViewHolder(view);
    }

    public void setfilter(List<GroceryCategoryModel> newlist) {
        groceryCategoryModelList = new ArrayList<>();
        groceryCategoryModelList.addAll(newlist);
        notifyDataSetChanged();
    }


    @Override
    public void onBindViewHolder(@NonNull GroceryCateogryAdapter.GroceryCatViewHolder holder, int position) {
        GroceryCategoryModel groceryCategoryModel = groceryCategoryModelList.get(position);
        Glide.with(context).load(groceryCategoryModel.getCat_images()).placeholder(R.drawable.productimage).into(holder.imgProduct);
        holder.txtProduct.setText(groceryCategoryModel.getCat_name());
        holder.linearProduct.setOnClickListener(v -> {
            Intent productIntent = new Intent(context, GroceryProductActivity.class);
            Bundle productBundle = new Bundle();
            productBundle.putString("cat_id", groceryCategoryModel.getCat_id());
            productBundle.putString("cat_name", groceryCategoryModel.getCat_name());
            productIntent.putExtras(productBundle);
            context.startActivity(productIntent);
        });
    }

    @Override
    public int getItemCount() {
        return groceryCategoryModelList.size();
    }

    public class GroceryCatViewHolder extends RecyclerView.ViewHolder {

        LinearLayout linearProduct;
        ImageView imgProduct;
        TextView txtProduct;

        public GroceryCatViewHolder(@NonNull View itemView) {
            super(itemView);

            linearProduct = itemView.findViewById(R.id.linearProduct);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            txtProduct = itemView.findViewById(R.id.txtProduct);

        }
    }
}
