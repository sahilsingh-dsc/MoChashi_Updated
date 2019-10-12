package com.tetraval.mochashi.data.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.tetraval.mochashi.R;

import java.util.List;

public class CartAdapters {

//    List<CartModel> cartModelList;
//    Context context;
//    String[] qty = {"1kg", "5kg", "15kg", "25kg"};
//
//    public CartAdapters(List<CartModel> cartModelList, Context context) {
//        this.cartModelList = cartModelList;
//        this.context = context;
//    }
//
//    @NonNull
//    @Override
//    public CartAdapters.CartViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cart_list_item, viewGroup, false);
//        return new CartViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull CartAdapters.CartViewHolder productViewHolder, final int i) {
//
//
//        final CartModel productModel = cartModelList.get(i);
//        productViewHolder.imgProductImage.setImageResource(R.drawable.buscits);
//        productViewHolder.txtProductName.setText(productModel.getProduct_name());
//        productViewHolder.txtMrpPrice.setText(productModel.getProduct_mrpprice());
//        productViewHolder.txtSalePrice.setText(productModel.getProduct_saleprice());
//        productViewHolder.txtSaveAmt.setText(productModel.getProduct_saveamt());
//        productViewHolder.btnAddtoCart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//
////        productViewHolder.constrainProduct.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////
////                Intent productDtlIntent = new Intent(context, PDetailsActivity.class);
////                Bundle productDtlBundle = new Bundle();
////                productDtlBundle.putString("product_image", productModel.getProduct_image());
////                productDtlBundle.putString("product_name", productModel.getProduct_name());
////                productDtlBundle.putString("product_mrp", productModel.getProduct_mrpprice());
////                productDtlBundle.putString("product_saleprice", productModel.getProduct_saleprice());
////                productDtlBundle.putString("product_saveamt", productModel.getProduct_saveamt());
////                productDtlBundle.putString("product_disc", productModel.getProduct_disc());
////                productDtlIntent.putExtras(productDtlBundle);
////                context.startActivity(productDtlIntent);
////            }
////        });
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return cartModelList.size();
//    }
//
//    public class CartViewHolder extends RecyclerView.ViewHolder {
//
//        ImageView imgProductImage;
//        TextView txtProductName, txtMrpPrice, txtSalePrice, txtSaveAmt;
//        Spinner spinnerQty;
//        Button btnAddtoCart;
//        ConstraintLayout constrainProduct;
//
//        public CartViewHolder(@NonNull View itemView) {
//            super(itemView);
//
//            imgProductImage = itemView.findViewById(R.id.imgProductImage);
//            txtProductName = itemView.findViewById(R.id.txtProductName);
//            txtMrpPrice = itemView.findViewById(R.id.txtMrpPrice);
//            txtSalePrice = itemView.findViewById(R.id.txtSalePrice);
//            txtSaveAmt = itemView.findViewById(R.id.txtSaveAmt);
////            spinnerQty = itemView.findViewById(R.id.spinnerQty);
//            btnAddtoCart = itemView.findViewById(R.id.btnAddtoCart);
//            constrainProduct = itemView.findViewById(R.id.constrainProduct);
//
//
//        }
//    }
}
