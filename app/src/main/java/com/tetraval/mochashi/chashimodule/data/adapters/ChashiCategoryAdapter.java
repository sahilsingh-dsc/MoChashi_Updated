package com.tetraval.mochashi.chashimodule.data.adapters;

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
import com.tetraval.mochashi.chashimodule.data.models.ChashiCategoryModel;
import com.tetraval.mochashi.ui.activities.ChashiActivity;

import java.util.ArrayList;
import java.util.List;

public class ChashiCategoryAdapter extends RecyclerView.Adapter<ChashiCategoryAdapter.ChashiOnlineViewHolder> {

    List<ChashiCategoryModel> chashiCategoryModelList;
    Context context;

    public ChashiCategoryAdapter(List<ChashiCategoryModel> chashiCategoryModelList, Context context) {
        this.chashiCategoryModelList = chashiCategoryModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public ChashiCategoryAdapter.ChashiOnlineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mo_vege_list_item, parent, false);
        return new ChashiOnlineViewHolder(view);
    }

    public void setfilter(List<ChashiCategoryModel> newlist) {
        chashiCategoryModelList = new ArrayList<>();
        chashiCategoryModelList.addAll(newlist);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ChashiCategoryAdapter.ChashiOnlineViewHolder holder, int position) {
        ChashiCategoryModel chashiCategoryModel = chashiCategoryModelList.get(position);
        holder.txtProduct.setText(chashiCategoryModel.getProduct_name());
        Glide.with(context).load(chashiCategoryModel.getProduct_image()).placeholder(R.drawable.productimage).into(holder.imgProduct);
        holder.linearProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle coBundle = new Bundle();
                coBundle.putString("mo_id", chashiCategoryModel.getProduct_id());
                coBundle.putString("mo_name", chashiCategoryModel.getProduct_name());
                coBundle.putString("mo_image", chashiCategoryModel.getProduct_image());
                Intent coIntent = new Intent(context, ChashiActivity.class);
                coIntent.putExtras(coBundle);
                context.startActivity(coIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return chashiCategoryModelList.size();
    }

    public class ChashiOnlineViewHolder extends RecyclerView.ViewHolder {

        LinearLayout linearProduct;
        ImageView imgProduct;
        TextView txtProduct;

        public ChashiOnlineViewHolder(@NonNull View itemView) {
            super(itemView);

            linearProduct = itemView.findViewById(R.id.linearProduct);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            txtProduct = itemView.findViewById(R.id.txtProduct);

        }
    }
}
