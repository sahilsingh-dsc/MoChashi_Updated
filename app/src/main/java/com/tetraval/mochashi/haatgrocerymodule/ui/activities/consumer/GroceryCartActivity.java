package com.tetraval.mochashi.haatgrocerymodule.ui.activities.consumer;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tetraval.mochashi.R;
import com.tetraval.mochashi.authmodule.LoginActivity;
import com.tetraval.mochashi.controller.StartActivity;
import com.tetraval.mochashi.haatgrocerymodule.data.adapters.GroceryCartAdapter;
import com.tetraval.mochashi.haatgrocerymodule.data.models.GroceryCartModel;
import com.tetraval.mochashi.ui.activities.MyAccountActivity;
import com.tetraval.mochashi.utils.AppConst;
import com.tetraval.mochashi.utils.SendMail;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class GroceryCartActivity extends AppCompatActivity {

    Toolbar toolbarGroceryCart;
    RecyclerView recyclerGroceryCart;
    List<GroceryCartModel> groceryCartModelList;
    GroceryCartAdapter groceryCartAdapter;
    SharedPreferences master;
    ProgressDialog progressDialog;
    Button checkout;
    TextView txtyes,txtno;
    private ProgressDialog pDialog;
    FirebaseAuth mAuth;
    static ArrayList<String> Productid = new ArrayList<String>();
    static ArrayList<String> Productprize = new ArrayList<String>();
    static ArrayList<String> ProductQuantity = new ArrayList<String>();
    double Total_price;;
    double Real_price;
    double Quantity;
    TextView amountpay;
    String userid,address;
    double finalamount;
    DecimalFormat precision = new DecimalFormat("0.00");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_cart);
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
        amountpay=findViewById(R.id.amountpay);

        toolbarGroceryCart = findViewById(R.id.toolbarGroceryCart);
        setSupportActionBar(toolbarGroceryCart);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Your Cart");
        toolbarGroceryCart.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        Objects.requireNonNull(toolbarGroceryCart.getOverflowIcon()).setColorFilter(Color.WHITE , PorterDuff.Mode.SRC_ATOP);

        recyclerGroceryCart = findViewById(R.id.recyclerGroceryCart);
        recyclerGroceryCart.setLayoutManager(new LinearLayoutManager(this));

        progressDialog = new ProgressDialog(GroceryCartActivity.this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);

        groceryCartModelList = new ArrayList<>();
        groceryCartModelList.clear();

        master = getApplicationContext().getSharedPreferences("MASTER", 0);
         userid=master.getString("user_id","0");
         address=master.getString("address","0");
        progressDialog.show();
        fetchCartItems();
        checkout=findViewById(R.id.btnCheckout);
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog alertDialog = new AlertDialog.Builder(GroceryCartActivity.this)
//set icon
                        .setIcon(android.R.drawable.ic_dialog_alert)
//set title
                        .setTitle("Confirmation")
//set message
                        .setMessage("Are you sure you want to proceed..")
//set positive button
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //set what would happen when positive button is clicked
                                //finish();
                                ProductUpload();
                            }
                        })
//set negative button
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //set what should happen when negative button is clicked
                                Toast.makeText(getApplicationContext(),"Nothing Happened",Toast.LENGTH_LONG).show();
                            }
                        })
                        .show();


     /*
                LayoutInflater layoutInflater = LayoutInflater.from(GroceryCartActivity.this);
                final View imageChooser = layoutInflater.inflate(R.layout.checkout_alert, null);
                final AlertDialog addDealDialog = new AlertDialog.Builder(getApplicationContext()).create();
                addDealDialog.setView(imageChooser);
                txtyes=imageChooser.findViewById(R.id.txtyes);
                txtno=imageChooser.findViewById(R.id.txtno);
                txtyes.findViewById(R.id.txtyes).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addDealDialog.dismiss();
                       *//* if (getActivity()!=null) {
                            if (isNetworkConnected()) {
                                presenter.ViewDeal(Status);
                            } else {
                                showAlert("Please connect to internet.", R.style.DialogAnimation);
                            }
                        }*//*
                    }
                });
                txtno.findViewById(R.id.txtno).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addDealDialog.dismiss();
                       *//* if (getActivity()!=null) {
                            if (isNetworkConnected()) {
                                presenter.ViewDeal(Status);
                            } else {
                                showAlert("Please connect to internet.", R.style.DialogAnimation);
                            }
                        }*//*
                    }
                });
                addDealDialog.show();*/
            }
        });

    }

    private void fetchCartItems(){
        Productprize.clear();
        DatabaseReference cartRef = FirebaseDatabase.getInstance().getReference("cart_instance");
        //String uid = master.getString("id", "1");
        cartRef.child("1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                groceryCartModelList.clear();
                for (DataSnapshot cartSnap : dataSnapshot.getChildren()){
                    GroceryCartModel groceryCartModel = new GroceryCartModel(
                            Objects.requireNonNull(cartSnap.child("cart_id").getValue()).toString(),
                            Objects.requireNonNull(cartSnap.child("cart_amount").getValue()).toString(),
                            Objects.requireNonNull(cartSnap.child("cart_quantity").getValue()).toString(),
                            Objects.requireNonNull(cartSnap.child("uid").getValue()).toString(),
                            Objects.requireNonNull(cartSnap.child("product_id").getValue()).toString(),
                            Objects.requireNonNull(cartSnap.child("product_image").getValue()).toString(),
                            Objects.requireNonNull(cartSnap.child("product_name").getValue()).toString(),
                            Objects.requireNonNull(cartSnap.child("product_mrpprice").getValue()).toString(),
                            Objects.requireNonNull(cartSnap.child("product_saleprice").getValue()).toString(),
                            Objects.requireNonNull(cartSnap.child("product_saveamt").getValue()).toString(),
                            Objects.requireNonNull(cartSnap.child("product_cat").getValue()).toString()
                    );
                    Productid.add(cartSnap.child("product_id").getValue().toString());
                    Productprize.add(cartSnap.child("cart_amount").getValue().toString());
                    ProductQuantity.add(cartSnap.child("cart_quantity").getValue().toString());

                    //Log.e("cart", "Productid== "+Productid );
                  //  Log.e("cart", "Productprize== "+Productprize );
                  //  Log.e("cart", "ProductQuantity== "+ProductQuantity );

                    groceryCartModelList.add(groceryCartModel);
                    progressDialog.dismiss();
                }
                totalprize();

                groceryCartAdapter = new GroceryCartAdapter(groceryCartModelList, getApplicationContext());
                recyclerGroceryCart.setAdapter(groceryCartAdapter);
                progressDialog.dismiss();

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressDialog.dismiss();
            }
        });

    }
    public void totalprize(){
        finalamount=0;
        Log.e("cart", "finalamount== "+finalamount );
        Total_price=0;
        Quantity=0;
        for (int i = 0; i < groceryCartModelList.size(); i++) {
            finalamount+=Double.valueOf(groceryCartModelList.get(i).getCart_amount());
            Total_price = Double.valueOf(groceryCartModelList.get(i).getProduct_saleprice());
            Quantity = Double.valueOf(groceryCartModelList.get(i).getCart_quantity());
            Real_price=Total_price*Quantity;
            //finalamount=finalamount-Real_price;
            amountpay.setText(precision.format(finalamount));

            Log.e("cart", "ProductQuantity== "+Quantity );

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
            return true;
        }
        if (id == R.id.menu_home) {
            startActivity(new Intent(getApplicationContext(), StartActivity.class));
            finish();
            return true;
        }
        if (id==R.id.menu_myaccount){
            startActivity(new Intent(getApplicationContext(), MyAccountActivity.class));
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
    private void ProductUpload() {
        showpDialog();
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024);

        // Use HttpURLConnection as the HTTP client
        Network network = new BasicNetwork(new HurlStack());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConst.BASE_URL+"User_api/place_order",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Login", "onResponse: "+response);
                        hidepDialog();
                        try {
                            //converting response to json object
                            JSONObject obj = new JSONObject(response);
                            String status = obj.getString("status");
                            String message = obj.getString("message");
                            if (status.equals("200")) {
                                String subject = "Regarding your latest course";
                             //   String name = booking_for;
                                String mailmessage = "Hello, ";

                                SendMail sm = new SendMail(GroceryCartActivity.this, "sahilsingh.dsc@gmail.com", subject, mailmessage);
                                  sm.execute();
                                Toast.makeText(GroceryCartActivity.this, ""+message, Toast.LENGTH_SHORT).show();
                                finish();

                            }


                        } catch (JSONException e) {
                            hidepDialog();
                            //Toast.makeText(GroceryCartActivity.this, ""+e, Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        hidepDialog();
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                //Adding the parameters to the request
                params.put("user_id",userid);
                params.put("vendor_id","0");
                params.put("total_price", String.valueOf(Total_price));
                params.put("shipping_address",address);
                params.put("shipping_charge","0");
                params.put("tax","0");
                params.put("product_id", String.valueOf(Productid));
                params.put("product_qty", String.valueOf(ProductQuantity));
                params.put("product_price", String.valueOf(Productprize));
                Log.e("order detail",""+params);
                return params;
            }
        };
        RequestQueue queue = new RequestQueue(cache, network);
        queue.start();
        queue.add(stringRequest);
       /* RequestQueue requestQueue = Volley.newRequestQueue(GroceryCartActivity.this);
        requestQueue.add(stringRequest);*/
    }
    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

}
