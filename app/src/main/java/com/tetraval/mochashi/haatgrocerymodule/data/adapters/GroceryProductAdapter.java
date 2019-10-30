package com.tetraval.mochashi.haatgrocerymodule.data.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tetraval.mochashi.R;
import com.tetraval.mochashi.haatgrocerymodule.data.models.GroceryProductModel;
import com.tetraval.mochashi.haatgrocerymodule.ui.activities.consumer.GroceryProductDetailActivity;

import java.text.DecimalFormat;
import java.util.List;

public class GroceryProductAdapter extends RecyclerView.Adapter<GroceryProductAdapter.GroceyProductViewHolder> {

    List<GroceryProductModel> groceryProductModelList;
    Context context;
    int savedamt = 0;
    double save = 0;
    DecimalFormat precision = new DecimalFormat("0.00");
    int counter = 1;
    GroceryProductAdapter adapter;


    public GroceryProductAdapter(List<GroceryProductModel> groceryProductModelList, Context context) {
        this.groceryProductModelList = groceryProductModelList;
        this.context = context;
        this.adapter = this;
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
        double mrp= Double.parseDouble(groceryProductModel.getProduct_mrpprice());
        holder.txtProductMRP.setText("MRP: ₹%s"+precision.format(mrp));
        double sale= Double.parseDouble(groceryProductModel.getProduct_saleprice());
        holder.txtProductSale.setText("Sale Price: ₹%s"+precision.format(sale));
        savedamt = Integer.parseInt(groceryProductModel.getProduct_mrpprice()) - Integer.parseInt(groceryProductModel.getProduct_saleprice());
        save = savedamt;
        holder.txtProductSave.setText(String.format("Save: ₹%s", precision.format(savedamt)));
        holder.constrainProduct.setOnClickListener(v -> {

            Intent productDtlIntent = new Intent(context, GroceryProductDetailActivity.class);
            Bundle productDtlBundle = new Bundle();

            productDtlBundle.putString("product_id", groceryProductModel.getProduct_id());
            productDtlBundle.putString("product_image", groceryProductModel.getProduct_image());
            productDtlBundle.putString("product_name", groceryProductModel.getProduct_name());
            productDtlBundle.putString("product_mrpprice", groceryProductModel.getProduct_mrpprice());
            productDtlBundle.putString("product_saleprice", groceryProductModel.getProduct_saleprice());
            productDtlBundle.putString("product_saveamt", save+"");
            productDtlBundle.putString("product_cat", groceryProductModel.getProduct_cat());
            productDtlBundle.putString("product_desc", groceryProductModel.getProduct_desc());
            productDtlBundle.putString("product_weight", groceryProductModel.getProduct_weight()+groceryProductModel.getProduct_unit());
            productDtlIntent.putExtras(productDtlBundle);
            context.startActivity(productDtlIntent);
        });

        holder.btnAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.btnAddCart.setVisibility(View.GONE);
                holder.linearLayout2.setVisibility(View.VISIBLE);
            }
        });
        holder.imgPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                holder.txtQty.setText(""+String.valueOf(Integer.parseInt(holder.txtQty.getText().toString())+1));
                counter =( Integer.parseInt(holder.txtQty.getText().toString()));
                adapter.notifyDataSetChanged();

            }
        });
        holder.imgMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (counter > 1) {
                    counter = (Integer.parseInt(holder.txtQty.getText().toString()) - 1);
                    holder.txtQty.setText("" + String.valueOf(Integer.parseInt(holder.txtQty.getText().toString()) - 1));
                    adapter.notifyDataSetChanged();
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return groceryProductModelList.size();
    }

    public class GroceyProductViewHolder extends RecyclerView.ViewHolder {

        ImageView imgProductImage, imgPlus, imgMinus;
        TextView txtProductName, txtProductCat, txtProductMRP, txtProductSale, txtProductSave, txtTotalCost, txtQty;
        ConstraintLayout constrainProduct;
        Button btnAddCart;
        LinearLayout linearLayout2;

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
            linearLayout2 = itemView.findViewById(R.id.linearLayout2);
            txtTotalCost = itemView.findViewById(R.id.txtTotalCost);
            imgPlus = itemView.findViewById(R.id.imgPlus);
            imgMinus = itemView.findViewById(R.id.imgMinus);
            txtQty = itemView.findViewById(R.id.txtQty);

        }
    }

}
