package com.tetraval.mochashi.haatgrocerymodule.data.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tetraval.mochashi.R;
import com.tetraval.mochashi.haatgrocerymodule.data.models.GroceryProductModel;
import com.tetraval.mochashi.haatgrocerymodule.ui.activities.GroceryProductDetailActivity;

import java.util.List;

public class GroceryProductAdapter extends RecyclerView.Adapter<GroceryProductAdapter.GroceyProductViewHolder> {

    List<GroceryProductModel> groceryProductModelList;
    Context context;
    int savedamt = 0;


    public GroceryProductAdapter(List<GroceryProductModel> groceryProductModelList, Context context) {
        this.groceryProductModelList = groceryProductModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public GroceryProductAdapter.GroceyProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list_item, parent, false);
        return new GroceyProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroceryProductAdapter.GroceyProductViewHolder holder, int position) {
        GroceryProductModel groceryProductModel = groceryProductModelList.get(position);
        Glide.with(context).load(groceryProductModel.getProduct_image()).placeholder(R.drawable.productimage).into(holder.imgProductImage);
        holder.txtProductName.setText(String.format("%s (%s%s)", groceryProductModel.getProduct_name(), groceryProductModel.getProduct_weight(), groceryProductModel.getProduct_unit()));
        holder.txtProductCat.setText(groceryProductModel.getProduct_cat());
        holder.txtProductMRP.setText(String.format("MRP: ₹%s", groceryProductModel.getProduct_mrpprice()));
        holder.txtProductSale.setText(String.format("Sale Price: ₹%s", groceryProductModel.getProduct_saleprice()));
        savedamt = Integer.parseInt(groceryProductModel.getProduct_mrpprice()) - Integer.parseInt(groceryProductModel.getProduct_saleprice());
        holder.txtProductSave.setText(String.format("Save: ₹%s", savedamt));
        holder.constrainProduct.setOnClickListener(v -> {

            Intent productDtlIntent = new Intent(context, GroceryProductDetailActivity.class);
            Bundle productDtlBundle = new Bundle();

            productDtlBundle.putString("product_id", groceryProductModel.getProduct_id());
            productDtlBundle.putString("product_image", groceryProductModel.getProduct_image());
            productDtlBundle.putString("product_name", groceryProductModel.getProduct_name());
            productDtlBundle.putString("product_mrpprice", groceryProductModel.getProduct_mrpprice());
            productDtlBundle.putString("product_saleprice", groceryProductModel.getProduct_saleprice());
            productDtlBundle.putString("product_saveamt", savedamt+"");
            productDtlBundle.putString("product_cat", groceryProductModel.getProduct_cat());
            productDtlBundle.putString("product_desc", groceryProductModel.getProduct_desc());
            productDtlBundle.putString("product_weight", groceryProductModel.getProduct_weight()+groceryProductModel.getProduct_unit());
            productDtlIntent.putExtras(productDtlBundle);
            context.startActivity(productDtlIntent);
        });

        holder.btnAddCart.setOnClickListener(view -> holder.btnAddCart.setVisibility(View.GONE));

    }

    @Override
    public int getItemCount() {
        return groceryProductModelList.size();
    }

    public class GroceyProductViewHolder extends RecyclerView.ViewHolder {

        ImageView imgProductImage;
        TextView txtProductName, txtProductCat, txtProductMRP, txtProductSale, txtProductSave;
        ConstraintLayout constrainProduct;
        Button btnAddCart;

        public GroceyProductViewHolder(@NonNull View itemView) {
            super(itemView);

            imgProductImage = itemView.findViewById(R.id.imgProductImage);
            txtProductName = itemView.findViewById(R.id.txtProductName);
            txtProductCat = itemView.findViewById(R.id.txtProductCat);
            txtProductMRP = itemView.findViewById(R.id.txtProductMRP);
            txtProductSale = itemView.findViewById(R.id.txtProductSale);
            txtProductSave = itemView.findViewById(R.id.txtProductSave);
            constrainProduct = itemView.findViewById(R.id.constrainProduct);
            btnAddCart = itemView.findViewById(R.id.btnAddCart);

        }
    }
}
