package com.tetraval.mochashi.data.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tetraval.mochashi.R;
import com.tetraval.mochashi.ui.activities.PDetailsActivity;

import java.util.List;

public class ProductAdapters {

//   // List<ProductModel> productModelList;
//    Context context;
//    String[] qty = {"1kg", "5kg", "15kg", "25kg"};
//
//    public ProductAdapters(List<ProductModel> productModelList, Context context) {
//        this.productModelList = productModelList;
//        this.context = context;
//    }
//
//    @NonNull
//    @Override
//    public ProductAdapters.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.product_list_item, viewGroup, false);
//        return new ProductViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ProductAdapters.ProductViewHolder productViewHolder, int i) {
//
//        final ArrayAdapter qtyAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, qty);
//
//        final ProductModel productModel = productModelList.get(i);
//        Glide.with(context).load(productModel.getProduct_image()).placeholder(R.drawable.productimage).into(productViewHolder.imgProductImage);
//        productViewHolder.txtProductName.setText(productModel.getProduct_name());
//        productViewHolder.txtProductCat.setText(productModel.getProduct_cat());
//        productViewHolder.txtProductMRP.setText("MRP ₹"+productModel.getProduct_mrpprice());
//        productViewHolder.txtProductSale.setText("Sale Price ₹"+productModel.getProduct_saleprice());
//        productViewHolder.txtProductSave.setText("Save ₹"+productModel.getProduct_saveamt());
////        productViewHolder.spinnerProductPackage.setAdapter(qtyAdapter);
////        productViewHolder.txtPAddToCart.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                Toast.makeText(context, "Add to cart", Toast.LENGTH_SHORT).show();
////            }
////        });
//
//        productViewHolder.constrainProduct.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent productDtlIntent = new Intent(context, PDetailsActivity.class);
//                Bundle productDtlBundle = new Bundle();
//                productDtlBundle.putString("product_image", productModel.getProduct_image());
//                productDtlBundle.putString("product_name", productModel.getProduct_name());
//                productDtlBundle.putString("product_mrp", productModel.getProduct_mrpprice());
//                productDtlBundle.putString("product_saleprice", productModel.getProduct_saleprice());
//                productDtlBundle.putString("product_saveamt", productModel.getProduct_saveamt());
//                productDtlBundle.putString("product_disc", productModel.getProduct_disc());
//                productDtlIntent.putExtras(productDtlBundle);
//                context.startActivity(productDtlIntent);
//            }
//        });
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return productModelList.size();
//    }
//
//    public class ProductViewHolder extends RecyclerView.ViewHolder {
//
//        ImageView imgProductImage;
//        TextView txtProductName, txtProductCat, txtProductMRP, txtProductSale, txtProductSave, txtPAddToCart;
//        ConstraintLayout constrainProduct;
//
//        public ProductViewHolder(@NonNull View itemView) {
//            super(itemView);
//
//            imgProductImage = itemView.findViewById(R.id.imgProductImage);
//            txtProductName = itemView.findViewById(R.id.txtProductName);
//            txtProductCat = itemView.findViewById(R.id.txtProductCat);
//            txtProductMRP = itemView.findViewById(R.id.txtProductMRP);
//            txtProductSale = itemView.findViewById(R.id.txtProductSale);
//            txtProductSave = itemView.findViewById(R.id.txtProductSave);
//            constrainProduct = itemView.findViewById(R.id.constrainProduct);
//
//
//        }
//    }
}
