package com.tetraval.mochashi.haatgrocerymodule.data.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tetraval.mochashi.R;
import com.tetraval.mochashi.haatgrocerymodule.data.models.GroceryCartModel;

import java.util.HashMap;
import java.util.List;

public class GroceryCartAdapter extends RecyclerView.Adapter<GroceryCartAdapter.GroceryViewHolder> {

    List<GroceryCartModel> groceryCartModelList;
    Context context;
    int savedamt = 0;
    SharedPreferences master;
    String uid;

    public GroceryCartAdapter(List<GroceryCartModel> groceryCartModelList, Context context) {
        this.groceryCartModelList = groceryCartModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public GroceryCartAdapter.GroceryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grocery_cart_list_item, parent, false);

        master = context.getSharedPreferences("MASTER", 0);
        uid = master.getString("id", "1");

        return new GroceryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroceryCartAdapter.GroceryViewHolder holder, int position) {
        GroceryCartModel groceryCartModel = groceryCartModelList.get(position);
        Glide.with(context).load(groceryCartModel.getProduct_image()).placeholder(R.drawable.productimage).into(holder.imgProductImage);
        holder.txtProductName.setText(groceryCartModel.getProduct_name());
        holder.txtProductCat.setText(groceryCartModel.getProduct_cat());
        holder.txtProductMRP.setText(String.format("MRP: ₹%s", groceryCartModel.getProduct_mrpprice()));
        holder.txtProductSale.setText(String.format("Sale Price: ₹%s", groceryCartModel.getProduct_saleprice()));
        savedamt = Integer.parseInt(groceryCartModel.getProduct_mrpprice()) - Integer.parseInt(groceryCartModel.getProduct_saleprice());
        holder.txtProductSave.setText(String.format("Save: ₹%s", savedamt));
        holder.txtQty.setText(groceryCartModel.getCart_quantity());
        holder.txtTotalPrice.setText("Total Price: ₹"+groceryCartModel.getCart_amount());
        holder.txtRemoveItem.setOnClickListener(view -> {
            DatabaseReference cartRef = FirebaseDatabase.getInstance().getReference("cart_instance");
            cartRef.child(uid).child(groceryCartModel.getCart_id()).removeValue();
        });
        holder.imgPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int qty = Integer.parseInt(groceryCartModel.getCart_quantity());
                int total = Integer.parseInt(groceryCartModel.getProduct_saleprice());
                Log.e("cart", "total=="+total );
                Log.e("cart", "qty=="+qty );
                if (qty >= 1){
                    qty = qty+1;
                    String productqty = String.valueOf(qty);
                    holder.txtQty.setText(productqty);
                    int cart_amount = qty*total;
                    HashMap hashMap = new HashMap();
                    hashMap.put("cart_quantity", productqty);
                    hashMap.put("cart_amount", cart_amount+"");
                    DatabaseReference cartRef = FirebaseDatabase.getInstance().getReference("cart_instance");
                    cartRef.child(uid).child(groceryCartModel.getCart_id()).updateChildren(hashMap);
                    //calculateTotal(product_saleprice);
                }
            }
        });
        holder.imgMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int qty = Integer.parseInt(groceryCartModel.getCart_quantity());
                int total = Integer.parseInt(groceryCartModel.getProduct_saleprice());
                if (qty >= 2){
                    qty = qty-1;
                    String productqty = String.valueOf(qty);
                    holder.txtQty.setText(productqty);
                    HashMap hashMap = new HashMap();
                    int cart_amount = qty*total;
                    hashMap.put("cart_quantity", productqty);
                    hashMap.put("cart_amount", cart_amount+"");
                    DatabaseReference cartRef = FirebaseDatabase.getInstance().getReference("cart_instance");
                    cartRef.child(uid).child(groceryCartModel.getCart_id()).updateChildren(hashMap);
                    //calculateTotal(product_saleprice);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return groceryCartModelList.size();
    }

    public class GroceryViewHolder extends RecyclerView.ViewHolder {

        ImageView imgProductImage, imgPlus, imgMinus;
        TextView txtProductName, txtProductCat, txtProductMRP, txtProductSale, txtProductSave, txtQty, txtTotalPrice, txtRemoveItem;


        public GroceryViewHolder(@NonNull View itemView) {
            super(itemView);

            imgProductImage = itemView.findViewById(R.id.imgProductImage);
            imgPlus = itemView.findViewById(R.id.imgPlus);
            imgMinus = itemView.findViewById(R.id.imgMinus);
            txtProductName = itemView.findViewById(R.id.txtProductName);
            txtProductCat = itemView.findViewById(R.id.txtProductCat);
            txtProductMRP = itemView.findViewById(R.id.txtProductMRP);
            txtProductSale = itemView.findViewById(R.id.txtProductSale);
            txtProductSave = itemView.findViewById(R.id.txtProductSave);
            txtQty = itemView.findViewById(R.id.txtQty);
            txtTotalPrice = itemView.findViewById(R.id.txtTotalPrice);
            txtRemoveItem = itemView.findViewById(R.id.txtRemoveItem);

        }
    }
}
