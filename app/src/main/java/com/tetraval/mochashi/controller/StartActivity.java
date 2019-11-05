package com.tetraval.mochashi.controller;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import com.tetraval.mochashi.R;
import com.tetraval.mochashi.chashimodule.ui.activities.ChashiCategoryActivity;
import com.tetraval.mochashi.database.MySQLiteOpenHelper;
import com.tetraval.mochashi.haatgrocerymodule.ui.activities.consumer.GroceryCartActivity;
import com.tetraval.mochashi.haatgrocerymodule.ui.activities.consumer.GroceryShopActivity;

public class StartActivity extends AppCompatActivity {

    TextView txtCustomerName;
    CardView cardChashi, cardHaat, cardGrocery;
    SharedPreferences master;
    int count = 0;
    ProgressDialog progressDialog;
    MySQLiteOpenHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

     /*   progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();*/
        db = new MySQLiteOpenHelper(getApplicationContext());
        count= db.getProfilesCount();
       // Toast.makeText(this, ""+count, Toast.LENGTH_SHORT).show();
       /* DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("cart_instance");
        databaseReference.child("1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                count = dataSnapshot.getChildrenCount();
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/

        //Toast.makeText(this, ""+count, Toast.LENGTH_SHORT).show();

        master = this.getSharedPreferences("MASTER", 0);

        cardChashi = findViewById(R.id.cardChashi);
        cardHaat = findViewById(R.id.cardHaat);
        cardGrocery = findViewById(R.id.cardGrocery);
        txtCustomerName = findViewById(R.id.txtCustomerName);
        txtCustomerName.setText("Welcome, "+master.getString("fname", "Welcome User"));

        cardChashi.setOnClickListener(view -> {
            if (count == 0){
                Intent intent = new Intent(getApplicationContext(), ChashiCategoryActivity.class);
                startActivity(intent);
            }else {
                AlertDialog alertDialog = new AlertDialog.Builder(StartActivity.this)
//set icon
                        .setIcon(android.R.drawable.ic_dialog_alert)
//set title
                        .setTitle("Please ")
//set message
                        .setMessage("Please clear cart before change category")
//set positive button
                        .setPositiveButton("Checkout", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                startActivity(new Intent(getApplicationContext(), GroceryCartActivity.class));
                            }
                        })
//set negative button
                        .setNegativeButton("Clear Cart", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //databaseReference.child("1").removeValue();
                                db.deleteAll();
                                startActivity(new Intent(getApplicationContext(),StartActivity.class));
                                finish();
                                Toast.makeText(StartActivity.this, "Cart Cleared!", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();
            }

        });

        cardHaat.setOnClickListener(view -> {
            if (count == 0){
                Intent intent = new Intent(getApplicationContext(), GroceryShopActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("account_status", 3);
                intent.putExtras(bundle);
                startActivity(intent);
            }else {
                AlertDialog alertDialog = new AlertDialog.Builder(StartActivity.this)
//set icon
                        .setIcon(android.R.drawable.ic_dialog_alert)
//set title
                        .setTitle("Please Checkout")
//set message
                        .setMessage("Please clear cart before change category")
//set positive button
                        .setPositiveButton("Checkout", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                startActivity(new Intent(getApplicationContext(), GroceryCartActivity.class));
                            }
                        })
//set negative button
                        .setNegativeButton("Clear Cart", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                               // databaseReference.child("1").removeValue();
                                db.deleteAll();
                                startActivity(new Intent(getApplicationContext(),StartActivity.class));
                                finish();
                                Toast.makeText(StartActivity.this, "Cart Cleared!", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();
            }
        });

        cardGrocery.setOnClickListener(view -> {
            if (count == 0){
                Intent intent = new Intent(getApplicationContext(), GroceryShopActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("account_status", 2);
                intent.putExtras(bundle);
                startActivity(intent);
            }else {
                AlertDialog alertDialog = new AlertDialog.Builder(StartActivity.this)

                        .setIcon(android.R.drawable.ic_dialog_alert)

                        .setTitle("Please Checkout")

                        .setMessage("Please clear cart before change category")

                        .setPositiveButton("Checkout", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                startActivity(new Intent(getApplicationContext(), GroceryCartActivity.class));
                            }
                        })
                        .setNegativeButton("Clear Cart", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                              //  databaseReference.child("1").removeValue();
                                db.deleteAll();
                                startActivity(new Intent(getApplicationContext(),StartActivity.class));
                                finish();
                                Toast.makeText(StartActivity.this, "Cart Cleared!", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        db = new MySQLiteOpenHelper(getApplicationContext());
        count= db.getProfilesCount();
    }
}