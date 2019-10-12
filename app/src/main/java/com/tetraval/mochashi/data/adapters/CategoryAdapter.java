package com.tetraval.mochashi.data.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tetraval.mochashi.R;
import com.tetraval.mochashi.data.models.CategoryModel;
import com.tetraval.mochashi.ui.activities.ProductActivity;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    List<CategoryModel> categoryModelList;
    Context context;

    public CategoryAdapter(List<CategoryModel> categoryModelList, Context context) {
        this.categoryModelList = categoryModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public CategoryAdapter.CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.category_list_item, viewGroup, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.CategoryViewHolder categoryViewHolder, int i) {
        final CategoryModel categoryModel = categoryModelList.get(i);
//        categoryViewHolder.imgProduct.setImageResource(R.drawable.baby);
        Glide.with(context).load(categoryModel.getCat_images()).placeholder(R.drawable.productimage).into(categoryViewHolder.imgProduct);
        categoryViewHolder.txtProduct.setText(categoryModel.getCat_name());
        categoryViewHolder.linearProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, ""+categoryModel.getCat_name(), Toast.LENGTH_SHORT).show();
                Intent productIntent = new Intent(context, ProductActivity.class);
                Bundle productBundle = new Bundle();
                productBundle.putString("productcat", categoryModel.getCat_id());
                productBundle.putString("productname", categoryModel.getCat_name());
                productIntent.putExtras(productBundle);
                context.startActivity(productIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryModelList.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {

        LinearLayout linearProduct;
        ImageView imgProduct;
        TextView txtProduct;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            linearProduct = itemView.findViewById(R.id.linearProduct);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            txtProduct = itemView.findViewById(R.id.txtProduct);

        }
    }
}
