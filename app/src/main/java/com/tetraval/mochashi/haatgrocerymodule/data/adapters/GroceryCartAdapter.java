package com.tetraval.mochashi.haatgrocerymodule.data.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tetraval.mochashi.R;
import com.tetraval.mochashi.database.MySQLiteOpenHelper;
import com.tetraval.mochashi.haatgrocerymodule.data.models.CartProductModel;
import com.tetraval.mochashi.haatgrocerymodule.data.models.GroceryCartModel;
import com.tetraval.mochashi.haatgrocerymodule.ui.activities.consumer.GroceryCartActivity;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;

public class GroceryCartAdapter extends RecyclerView.Adapter<GroceryCartAdapter.GroceryViewHolder> {

    List<CartProductModel> groceryCartModelList;
    Context context;
    int savedamt = 0;
    SharedPreferences master;
    String uid;
    DecimalFormat precision = new DecimalFormat("0.00");
    MySQLiteOpenHelper db;
    GroceryCartAdapter adapter;
    int counter = 0;

    public GroceryCartAdapter(List<CartProductModel> groceryCartModelList, Context context) {
        this.groceryCartModelList = groceryCartModelList;
        this.context = context;
        db = new MySQLiteOpenHelper(context);
        this.adapter = this;
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
        CartProductModel groceryCartModel = groceryCartModelList.get(position);
        Glide.with(context).load(groceryCartModel.getProduct_image()).placeholder(R.drawable.productimage).into(holder.imgProductImage);
        holder.txtProductName.setText(groceryCartModel.getProduct_name());
        holder.txtProductCat.setText(groceryCartModel.getProduct_cat());
        double mrp= Double.parseDouble(groceryCartModel.getProduct_prize());
        holder.txtProductMRP.setText("MRP: ₹"+precision.format(mrp));
        double sale= Double.parseDouble(groceryCartModel.getProduct_offer_prize());
        holder.txtProductSale.setText("Sale Price: ₹"+precision.format(sale));
        savedamt = Integer.parseInt(groceryCartModel.getProduct_prize()) - Integer.parseInt(groceryCartModel.getProduct_offer_prize());
        holder.txtProductSave.setText(String.format("Save: ₹", precision.format(savedamt)));
        holder.txtQty.setText(groceryCartModel.getProduct_selected_qty());
        double total= Double.parseDouble(groceryCartModel.getProduct_total_prize());
        holder.txtTotalPrice.setText("Total Price: ₹"+precision.format(total));
        holder.txtRemoveItem.setOnClickListener(view -> {
            /*DatabaseReference cartRef = FirebaseDatabase.getInstance().getReference("cart_instance");
            cartRef.child(uid).child(groceryCartModel.getCart_id()).removeValue();*/
            String id = groceryCartModel.getCart_id();
            long y = db.deleteItem(id);
            if(y>0){
                Toast.makeText(context,"Item deleted from cart",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(context,"Something went wrong",Toast.LENGTH_SHORT).show();
            }
            groceryCartModelList.remove(groceryCartModel);
            adapter.notifyDataSetChanged();
            Intent intent=new Intent(context, GroceryCartActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(intent);
        });
        holder.imgPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.txtQty.setText(""+String.valueOf(Integer.parseInt(holder.txtQty.getText().toString())+1));
                counter =( Integer.parseInt(holder.txtQty.getText().toString()));
                int disproductprize= Integer.parseInt(groceryCartModel.getProduct_offer_prize());
                int productprize= Integer.parseInt(groceryCartModel.getProduct_prize());
                String  TotalPrize= String.valueOf(disproductprize*counter);
                double Discount_price=productprize-disproductprize;
                String FinalDiscount_price = String.valueOf(Discount_price*counter);
                // counter = (Integer.parseInt(myViewHolder.tv_qty.getText().toString())+1);
                db.updateData(groceryCartModel.getProduct_id(), String.valueOf(counter),TotalPrize,FinalDiscount_price);
                adapter.notifyDataSetChanged();
                Intent intent=new Intent(context, GroceryCartActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);


            }
        });
        holder.imgMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                counter =( Integer.parseInt(holder.txtQty.getText().toString())-1);
                if (counter>0) {
                    holder.txtQty.setText(""+String.valueOf(Integer.parseInt(holder.txtQty.getText().toString())-1));
                    int disproductprize= Integer.parseInt(groceryCartModel.getProduct_offer_prize());
                    int productprize= Integer.parseInt(groceryCartModel.getProduct_prize());
                    String  TotalPrize= String.valueOf(disproductprize*counter);
                    double Discount_price=productprize-disproductprize;
                    String FinalDiscount_price = String.valueOf(Discount_price*counter);
                    db.updateData(groceryCartModel.getProduct_id(), String.valueOf(counter),TotalPrize,FinalDiscount_price);
                    adapter.notifyDataSetChanged();
                    Intent intent=new Intent(context, GroceryCartActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(intent);

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
