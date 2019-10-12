package com.tetraval.mochashi.ui.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.tetraval.mochashi.R;
import com.tetraval.mochashi.authmodule.LoginActivity;

public class PDetailsActivity extends AppCompatActivity {

    Toolbar toolbarDtl;
    String product_id;
    String product_image;
    String product_name;
    String product_mrpprice;
    String product_saleprice;
    String product_saveamt;
    String product_disc;
    int qty = 1;
    SharedPreferences preferences, masterdata;

    ImageView imgDtlProduct, imgPlus, imgMinus;
    TextView txtDtlName, txtDtlSale, txtDtlDiscount, txtDtlDisc, txtQty;
    Button btnDtlAddtoCart;
     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdetails);

        Bundle productDtlBundle = getIntent().getExtras();
        product_image = productDtlBundle.getString("product_image");
        product_name = productDtlBundle.getString("product_name");
        product_mrpprice = productDtlBundle.getString("product_mrpprice");
        product_saleprice = productDtlBundle.getString("product_saleprice");
        product_saveamt = productDtlBundle.getString("product_saveamt");
        product_disc = productDtlBundle.getString("product_disc");

         preferences = getApplicationContext().getSharedPreferences("loginpref", 0);
         masterdata = getApplicationContext().getSharedPreferences("MASTER", 0);

        toolbarDtl = findViewById(R.id.toolbarDtl);
        setSupportActionBar(toolbarDtl);
        getSupportActionBar().setTitle(product_name);
        toolbarDtl.setTitleTextColor(Color.WHITE);
        toolbarDtl.getOverflowIcon().setColorFilter(Color.WHITE , PorterDuff.Mode.SRC_ATOP);

//        imgDtlProduct = findViewById(R.id.imgProductImage);
//        txtDtlName = findViewById(R.id.txtDtlName);
//        txtDtlName.setText(product_name);
//        txtDtlSale = findViewById(R.id.txtDtlSale);
//        txtDtlSale.setText(product_saleprice);
//        txtDtlDiscount = findViewById(R.id.txtDtlDiscount);
//        txtDtlDiscount.setText(product_saveamt);
//        txtDtlDisc = findViewById(R.id.txtDtlDisc);
//        txtDtlDisc.setText(product_disc);

//        imgPlus = findViewById(R.id.imgPlus);
//        imgMinus = findViewById(R.id.imgMinus);
//        txtQty = findViewById(R.id.txtQty);
//        imgPlus.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                trigger();
//                qty = qty+1;
//                txtQty.setText(""+qty);
//
//            }
//        });
//
//        imgMinus.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                trigger();
//                qty = qty-1;
//                txtQty.setText(""+qty);
//            }
//        });
//
//         btnDtlAddtoCart = findViewById(R.id.btnDtlAddtoCart);
//         btnDtlAddtoCart.setOnClickListener(new View.OnClickListener() {
//             @Override
//             public void onClick(View v) {
//                 btnDtlAddtoCart.setText("ADDED TO CART");
//                 startActivity(new Intent(PDetailsActivity.this, CartActivity.class));
//             }
//         });

    }

    private void trigger(){
        if (qty <=1){
            imgMinus.setEnabled(false);
        }else {
            imgMinus.setEnabled(true);
        }
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
            startActivity(new Intent(getApplicationContext(), CartActivity.class));
            return true;
        } else if (id == R.id.menu_myaccount){
            startActivity(new Intent(getApplicationContext(), MyAccountActivity.class));
            return true;
        } else if (id == R.id.menu_myorders){
            startActivity(new Intent(getApplicationContext(), MyOrdersActivity.class));
            return  true;
        } else if (id == R.id.menu_mycredits){
            startActivity(new Intent(getApplicationContext(), CreditActivity.class));
            return  true;
        } else if (id == R.id.menu_signout){
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("login_status", 0);
            editor.apply();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
            return  true;
        }
        return super.onOptionsItemSelected(item);
    }

}
