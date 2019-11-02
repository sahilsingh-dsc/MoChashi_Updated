package com.tetraval.mochashi.haatgrocerymodule.data.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tetraval.mochashi.R;
import com.tetraval.mochashi.haatgrocerymodule.data.models.GroceryCartModel;
import com.tetraval.mochashi.haatgrocerymodule.data.models.GroceryProductModel;
import com.tetraval.mochashi.haatgrocerymodule.ui.activities.consumer.GroceryProductDetailActivity;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;

public class GroceryProductAdapter extends RecyclerView.Adapter<GroceryProductAdapter.GroceyProductViewHolder> {

    List<GroceryProductModel> groceryProductModelList;
    Context context;
    int savedamt = 0;
    double save = 0;
    DecimalFormat precision = new DecimalFormat("0.00");
    int counter = 0;
    GroceryProductAdapter adapter;
    DatabaseReference cartRef;
    String datafound="notfound";
    int oldcount;
    String cart_id;
    String uid = "1";

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
                if (counter==0){
                    Toast.makeText(context, "quantity", Toast.LENGTH_SHORT).show();
                }else{
                    if (datafound.equals("found")){
                        counter=oldcount+counter;
                        double saleprice= Double.parseDouble(groceryProductModel.getProduct_saleprice());
                        double cart_amount = counter*saleprice;
                        HashMap hashMap = new HashMap();
                        hashMap.put("cart_quantity", counter);
                        hashMap.put("cart_amount", cart_amount+"");
                        DatabaseReference cartRef = FirebaseDatabase.getInstance().getReference("cart_instance");
                        cartRef.child(uid).child(cart_id).updateChildren(hashMap);
                    }else if (datafound.equals("notfound")){
                        processAddToCart(groceryProductModel.getProduct_id(),
                                uid,
                                groceryProductModel.getProduct_image(),
                                groceryProductModel.getProduct_name(),
                                groceryProductModel.getProduct_mrpprice(),
                                groceryProductModel.getProduct_saleprice(),
                                save+"",
                                groceryProductModel.getProduct_desc(),
                                groceryProductModel.getProduct_cat());

                    }

                }



            }
        });
        holder.imgPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cartRef = FirebaseDatabase.getInstance().getReference("cart_instance");
                cartRef.child(uid).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnaphot) {
                        Log.e("adpter", "dataSnaphot=="+dataSnaphot );
                        for (DataSnapshot dataSnapshot1 : dataSnaphot.getChildren()){
                            if (dataSnapshot1.child("product_id").getValue().toString().equals(groceryProductModel.getProduct_id())){
                                oldcount= Integer.parseInt(dataSnapshot1.child("cart_quantity").getValue().toString());
                                cart_id = dataSnapshot1.child("cart_id").getValue().toString();
                                Toast.makeText(context, "found", Toast.LENGTH_SHORT).show();
                                datafound="found";
                            }
                        }
                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                holder.txtQty.setText(""+String.valueOf(Integer.parseInt(holder.txtQty.getText().toString())+1));
                counter =( Integer.parseInt(holder.txtQty.getText().toString()));
                adapter.notifyDataSetChanged();

            }
        });
        holder.imgMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (counter > 1) {
                    cartRef = FirebaseDatabase.getInstance().getReference("cart_instance");
                    cartRef.child(uid).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnaphot) {
                            Log.e("adpter", "dataSnaphot=="+dataSnaphot );
                            for (DataSnapshot dataSnapshot1 : dataSnaphot.getChildren()){
                                if (dataSnapshot1.child("product_id").getValue().toString().equals(groceryProductModel.getProduct_id())){
                                    oldcount= Integer.parseInt(dataSnapshot1.child("cart_quantity").getValue().toString());
                                    cart_id = dataSnapshot1.child("cart_id").getValue().toString();
                                    Toast.makeText(context, "found", Toast.LENGTH_SHORT).show();
                                    datafound="found";
                                }
                            }
                        }


                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
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
    private void processAddToCart(String product_id, String uid, String product_image, String product_name, String product_mrpprice, String product_saleprice, String product_saveamt, String product_desc, String product_cat){
        cartRef = FirebaseDatabase.getInstance().getReference("cart_instance");
        String cart_id = cartRef.push().getKey();
        int cartAmt = Integer.parseInt(product_saleprice)*counter;
        GroceryCartModel groceryCartModel = new GroceryCartModel(
                cart_id,
                cartAmt+"",
                counter+"",
                uid,
                product_id,
                product_image,
                product_name,
                product_mrpprice,
                product_saleprice,
                product_saveamt,
                product_cat);
        assert cart_id != null;
        cartRef.child(uid).child(cart_id).setValue(groceryCartModel);
    }

}
