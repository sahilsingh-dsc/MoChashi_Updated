package com.tetraval.mochashi.haatgrocerymodule.ui.activities.consumer;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tetraval.mochashi.R;
import com.tetraval.mochashi.authmodule.LoginActivity;
import com.tetraval.mochashi.controller.StartActivity;
import com.tetraval.mochashi.haatgrocerymodule.data.models.GroceryCartModel;
import com.tetraval.mochashi.ui.activities.MyAccountActivity;

import java.text.DecimalFormat;

public class GroceryProductDetailActivity extends AppCompatActivity {

    Toolbar toolbarGroceryProductDetail;
    ImageView imgDtlProduct, imgPlus, imgMinus;
    TextView txtDtlName, txtProductCat, txtProductMRP, txtProductSale, txtProductSave, txtProductDesc, txtAddToCat, txtQty, txtTotalPrice;
    Bundle productDtlBundle;
    TextView spinner;
    SharedPreferences master;
    DatabaseReference cartRef;
    DecimalFormat precision = new DecimalFormat("0.00");

    int qty = 1;
    double totalamt;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_product_detail);

        toolbarGroceryProductDetail = findViewById(R.id.toolbarGroceryProductDetail);

        productDtlBundle = getIntent().getExtras();
        assert productDtlBundle != null;
        String product_id = productDtlBundle.getString("product_id");
        String product_image = productDtlBundle.getString("product_image");
        String product_name = productDtlBundle.getString("product_name");
        String product_mrpprice = productDtlBundle.getString("product_mrpprice");
        String product_saleprice = productDtlBundle.getString("product_saleprice");
        String product_saveamt = productDtlBundle.getString("product_saveamt");
        String product_desc = productDtlBundle.getString("product_desc");
        String product_cat = productDtlBundle.getString("product_cat");
        String product_weight = productDtlBundle.getString("product_weight");
        master = getApplicationContext().getSharedPreferences("MASTER", 0);

        setSupportActionBar(toolbarGroceryProductDetail);
        getSupportActionBar().setTitle(product_name);
        toolbarGroceryProductDetail.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        toolbarGroceryProductDetail.getOverflowIcon().setColorFilter(Color.WHITE , PorterDuff.Mode.SRC_ATOP);

        imgDtlProduct = findViewById(R.id.imgDtlProduct);
        txtProductCat = findViewById(R.id.txtProductCat);
        txtProductMRP = findViewById(R.id.txtProductMRP);
        txtProductSale = findViewById(R.id.txtProductSale);
        txtProductSave = findViewById(R.id.txtProductSave);
        txtDtlName = findViewById(R.id.txtDtlName);
        txtAddToCat = findViewById(R.id.txtAddToCat);
        txtProductDesc = findViewById(R.id.txtProductDesc);
        spinner = findViewById(R.id.spinner);
        imgPlus = findViewById(R.id.imgPlus);
        imgMinus = findViewById(R.id.imgMinus);
        txtQty = findViewById(R.id.txtQty);
        txtTotalPrice = findViewById(R.id.txtTotalPrice);

        calculateTotal(product_saleprice);

        imgPlus.setOnClickListener(v -> {
            if (qty >= 1){
                qty = qty+1;
                String productqty = String.valueOf(qty);
                txtQty.setText(productqty);
                calculateTotal(product_saleprice);
            }
        });

        imgMinus.setOnClickListener(v -> {
            if (qty >= 2){
                qty = qty-1;
                String productqty = String.valueOf(qty);
                txtQty.setText(productqty);
                calculateTotal(product_saleprice);
            }
        });

        setProductData(product_image, product_name, product_mrpprice, product_saleprice, product_saveamt, product_desc, product_cat, product_weight);

        txtAddToCat.setOnClickListener(view -> {

            String uid = "1";
            if (uid != null &&
                    product_id != null &&
                    product_image != null &&
                    product_name != null &&
                    product_mrpprice != null &&
                    product_saleprice != null &&
                    product_saveamt != null &&
                    product_desc != null &&
                    product_cat != null){
                Toast.makeText(this, product_name+" Added to cart!", Toast.LENGTH_SHORT).show();
                txtAddToCat.setText("ADDED TO CART");
                AlertDialog.Builder addToCartBuilder = new AlertDialog.Builder(GroceryProductDetailActivity.this);
                addToCartBuilder.setTitle("ADDED TO CART");
                addToCartBuilder.setMessage("Explore more products");
                addToCartBuilder.setCancelable(false);
                addToCartBuilder.setPositiveButton(
                        "Continue Shopping",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //startActivity(new Intent(getApplicationContext(), StartActivity.class));
                                dialog.cancel();
                            }
                        });

                addToCartBuilder.setNegativeButton(
                        "Checkout Please",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                startActivity(new Intent(getApplicationContext(), GroceryCartActivity.class));
                                dialog.cancel();
                            }
                        });

                AlertDialog cartAlert = addToCartBuilder.create();
                cartAlert.show();
                processAddToCart(product_id,
                        uid,
                        product_image,
                        product_name,
                        product_mrpprice,
                        product_saleprice,
                        product_saveamt,
                        product_desc,
                        product_cat);
            }else {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void calculateTotal(String product_saleprice){
        totalamt = Double.parseDouble(product_saleprice)*qty;
        txtTotalPrice.setText("Total Price: ₹"+ precision.format(totalamt));
    }

    private void setProductData(String product_image, String product_name, String product_mrpprice, String product_saleprice, String product_saveamt, String product_desc, String product_cat, String product_weight){
        Glide.with(this).load(product_image).placeholder(R.drawable.productimage).into(imgDtlProduct);
        txtProductCat.setText(product_cat);
        txtProductMRP.setText(String.format("MRP: ₹%s", precision.format(Double.parseDouble(product_mrpprice))));
        txtProductSale.setText(String.format("Sale Price: ₹%s",  precision.format(Double.parseDouble(product_saleprice))));
        txtProductSave.setText(String.format("Save: ₹%s",  precision.format(Double.parseDouble(product_saveamt))));
        txtDtlName.setText(product_name);
        txtProductDesc.setText(product_desc);
        spinner.setText(product_weight);
    }

    private void processAddToCart(String product_id, String uid, String product_image, String product_name, String product_mrpprice, String product_saleprice, String product_saveamt, String product_desc, String product_cat){
        cartRef = FirebaseDatabase.getInstance().getReference("cart_instance");
        String cart_id = cartRef.push().getKey();
        int cartAmt = Integer.parseInt(product_saleprice)*qty;
        GroceryCartModel groceryCartModel = new GroceryCartModel(
                cart_id,
                cartAmt+"",
                qty+"",
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.header_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_favorite) {
            startActivity(new Intent(getApplicationContext(), GroceryCartActivity.class));
            return true;
        }
        if (id == R.id.menu_myaccount) {
            startActivity(new Intent(getApplicationContext(), MyAccountActivity.class));
            finish();
            return true;
        }

        if (id == R.id.menu_home) {
            startActivity(new Intent(getApplicationContext(), StartActivity.class));
            finish();
            return true;
        }
        if (id == R.id.menu_signout){
            SharedPreferences.Editor editor = master.edit();
            editor.clear();
            editor.apply();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
        }

        return super.onOptionsItemSelected(item);
    }


}
