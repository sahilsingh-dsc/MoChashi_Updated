package com.tetraval.mochashi.haatgrocerymodule.data.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tetraval.mochashi.R;
import com.tetraval.mochashi.haatgrocerymodule.data.models.ShopModel;
import com.tetraval.mochashi.haatgrocerymodule.ui.activities.GroceryCategoryActivity;

import java.util.List;

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ShopViewHolder> {

    List<ShopModel> shopModelList;
    Context context;

    public ShopAdapter(List<ShopModel> shopModelList, Context context) {
        this.shopModelList = shopModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public ShopAdapter.ShopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_item_list, parent, false);
        return new ShopViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShopAdapter.ShopViewHolder holder, int position) {
        ShopModel shopModel = shopModelList.get(position);
        Glide.with(context).load(shopModel.getShop_image()).placeholder(R.drawable.productimage).into(holder.imgShopImage);
        holder.txtShopName.setText(shopModel.getShop_name());
        holder.txtShopLocation.setText(shopModel.getShop_location());
        holder.constrainShop.setOnClickListener(view -> {
            Intent categoryIntent = new Intent(context, GroceryCategoryActivity.class);
            Bundle categoryBundle = new Bundle();
            categoryBundle.putString("shop_id", shopModel.getShop_id());
            categoryBundle.putString("shop_name", shopModel.getShop_name());
            categoryIntent.putExtras(categoryBundle);
            context.startActivity(categoryIntent);
        });
    }

    @Override
    public int getItemCount() {
        return shopModelList.size();
    }

    public class ShopViewHolder extends RecyclerView.ViewHolder {

        ImageView imgShopImage;
        TextView txtShopName, txtShopLocation;
        ConstraintLayout constrainShop;

        public ShopViewHolder(@NonNull View itemView) {
            super(itemView);

            imgShopImage = itemView.findViewById(R.id.imgShopImage);
            txtShopName = itemView.findViewById(R.id.txtShopName);
            txtShopLocation = itemView.findViewById(R.id.txtShopLocation);
            constrainShop = itemView.findViewById(R.id.constrainShop);

        }
    }
}
