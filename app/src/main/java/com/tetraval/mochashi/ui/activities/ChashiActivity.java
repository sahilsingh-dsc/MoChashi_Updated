package com.tetraval.mochashi.ui.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.tetraval.mochashi.R;
import com.tetraval.mochashi.authmodule.LoginActivity;
import com.tetraval.mochashi.chashimodule.ui.activities.ChasiMyOrdersActivity;
import com.tetraval.mochashi.controller.StartActivity;
import com.tetraval.mochashi.data.adapters.ChashiAdapter;
import com.tetraval.mochashi.data.models.ChashiModel;
import com.tetraval.mochashi.utils.AppConst;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChashiActivity extends AppCompatActivity {

    Toolbar toolbarChashi;
    RecyclerView recyclerChashi;
    List<ChashiModel> chashiModelList;
    ChashiAdapter chashiAdapter;
    SharedPreferences preferences, masterdata;
    ImageView imageView3;
    ConstraintLayout constrainChashiProduct;
    String mo_id, mo_name, mo_image;
    Spinner spinnerSortBy;
    String[] sortby = {"Sort Products By...", "Newly Added Products", "Low To High Price", "High To Low Price"};
    RequestQueue requestQueue;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chashi);

        preferences = getApplicationContext().getSharedPreferences("loginpref", 0);
        masterdata = getApplicationContext().getSharedPreferences("MASTER", 0);

        Bundle coBundle = getIntent().getExtras();
        mo_id = coBundle.getString("mo_id");
        mo_name = coBundle.getString("mo_name");
        mo_image = coBundle.getString("mo_image");

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait...");

        spinnerSortBy = findViewById(R.id.spinnerSortBy);
        ArrayAdapter sort = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, sortby);
        spinnerSortBy.setAdapter(sort);

        spinnerSortBy.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String sortoptions = adapterView.getItemAtPosition(i).toString();
                if (sortoptions.equals("Newly Added Products")){
                    sortByNew();
                    chashiAdapter.notifyDataSetChanged();
                } else if (sortoptions.equals("Low To High Price")){
                    sortByPriceLH();
                    chashiAdapter.notifyDataSetChanged();
                } else if (sortoptions.equals("High To Low Price")){
                    sortByPriceHL();
                    chashiAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        constrainChashiProduct = findViewById(R.id.constrainChashiProduct);

        requestQueue = Volley.newRequestQueue(this);

        toolbarChashi = findViewById(R.id.toolbarChashi);
        setSupportActionBar(toolbarChashi);
        getSupportActionBar().setTitle(mo_name);
        toolbarChashi.setTitleTextColor(Color.WHITE);
        toolbarChashi.getOverflowIcon().setColorFilter(Color.WHITE , PorterDuff.Mode.SRC_ATOP);

        recyclerChashi = findViewById(R.id.recyclerChashi);
        chashiModelList = new ArrayList<>();

        imageView3 = findViewById(R.id.imageView3);
        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle momapBundle = new Bundle();
                momapBundle.putString("id", mo_id);
                Intent intent = new Intent(ChashiActivity.this, MapsActivity.class);
                intent.putExtras(momapBundle);
                startActivity(intent);
            }
        });

        progressDialog.show();
        fetchProducts(mo_id);
    }

    private void sortByPriceHL() {
        Collections.sort(chashiModelList, (l1, l2) -> {
            if (Double.parseDouble(l1.getRate()) < Double.parseDouble(l2.getRate())) {
                return 1;
            } else if (Double.parseDouble(l1.getRate()) > Double.parseDouble(l2.getRate())) {
                return -1;
            } else {
                return 0;
            }
        });
    }

    private void sortByPriceLH() {
        Collections.sort(chashiModelList, (l1, l2) -> {
            if (Double.parseDouble(l1.getRate()) > Double.parseDouble(l2.getRate())) {
                return 1;
            } else if (Double.parseDouble(l1.getRate()) < Double.parseDouble(l2.getRate())) {
                return -1;
            } else {
                return 0;
            }
        });
    }

    private void sortByNew() {
//        Collections.sort(chashiModelList, (l1, l2) -> {
//            if (Integer.parseInt(l1.getP_timestamp()) < Integer.parseInt(l2.getP_timestamp())) {
//                return 1;
//            } else if (Integer.parseInt(l1.getP_timestamp()) > Integer.parseInt(l2.getP_timestamp())) {
//                return -1;
//            } else {
//                return 0;
//            }
//        });
    }

//    private void fetchProduct(String mo_name){
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        recyclerChashi.setLayoutManager(linearLayoutManager);
////        Query bookingRef = FirebaseDatabase.getInstance().getReference("products").getString()(mo_name);
////        bookingRef.addListenerForSingleValueEvent(new ValueEventListener() {
////            @Override
////            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
////                chashiModelList.clear();
////
////                    for (DataSnapshot jsonObject2 : dataSnapshot.getgetString()ren()){
////                        ChashiModel chashiModel = new ChashiModel(
////                                jsonObject2.getString()("p_id"),
////                                jsonObject2.getString()("img1"),
////                                jsonObject2.getString()("img2"),
////                                jsonObject2.getString()("img3"),
////                                jsonObject2.getString()("img4"),
////                                jsonObject2.getString()("p_name"),
////                                jsonObject2.getString()("p_avlqty"),
////                                jsonObject2.getString()("p_remain_avlqty"),
////                                jsonObject2.getString()("p_unit"),
////                                jsonObject2.getString()("p_rate"),
////                                jsonObject2.getString()("p_delivery"),
////                                jsonObject2.getString()("p_timestamp")
////                        );
////                        chashiModelList.add(chashiModel);
////
////                }
////                    progressDialog.dismiss();
////                if (chashiModelList.size() == 0){
////                    Snackbar snackbar = Snackbar
////                            .make(constrainChashiProduct, "Currently there are no products for this category.", Snackbar.LENGTH_INDEFINITE);
////                    snackbar.show();
////                }
////                chashiAdapter = new ChashiAdapter(chashiModelList, ChashiActivity.this);
////                recyclerChashi.setAdapter(chashiAdapter);
////                chashiAdapter.notifyDataSetChanged();
////
////
////            }
////
////            @Override
////            public void onCancelled(@NonNull DatabaseError databaseError) {
////                progressDialog.dismiss();
////            }
////        });

//    }

    private void fetchProducts(String mo_id){

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerChashi.setLayoutManager(linearLayoutManager);
        StringRequest getRequest = new StringRequest(Request.Method.POST, AppConst.BASE_URL+"User_api/chashi_vendor",
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String status =  jsonObject.getString("status");
                            String result =  jsonObject.getString("result");
                            if (status.equals("200")) {
                                JSONObject jsonObject1 = new JSONObject(result);
                                String vendor = jsonObject1.getString("vendor");
                                JSONArray jsonArray = new JSONArray(vendor);
                                for (int i = 0; i<jsonArray.length(); i++){
                                    JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                                    ChashiModel chashiModel = new ChashiModel(
                                            jsonObject2.getString("product_id"),
                                            jsonObject2.getString("vendor_id"),
                                            jsonObject2.getString("vendor_name"),
                                            jsonObject2.getString("product_name"),
                                            jsonObject2.getString("vendor_img"),
                                            jsonObject2.getString("qty_avl"),
                                            jsonObject2.getString("unit"),
                                            jsonObject2.getString("rate"),
                                            jsonObject2.getString("is_sold")
                                    );
                                    chashiModelList.add(chashiModel);
                                }
                                chashiAdapter = new ChashiAdapter(chashiModelList, ChashiActivity.this);
                                recyclerChashi.setAdapter(chashiAdapter);
                                if (chashiModelList.isEmpty()){
                                    Snackbar snackbar = Snackbar
                                            .make(constrainChashiProduct, "Products sold out & yet to be added freshly", Snackbar.LENGTH_INDEFINITE);
                                    snackbar.show();
                                    progressDialog.dismiss();
                                }
                                progressDialog.dismiss();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            progressDialog.dismiss();

                        }

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.toString());

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("category_id", mo_id);
                return params;
            }
        };

        requestQueue.add(getRequest);

        chashiAdapter = new ChashiAdapter(chashiModelList, this);
        recyclerChashi.setAdapter(chashiAdapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.header_menu_chashi, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.home_menuitem) {
            startActivity(new Intent(getApplicationContext(), StartActivity.class));
            finish();
            return true;
        }else if (id == R.id.menu_myaccount){
            startActivity(new Intent(getApplicationContext(), MyAccountActivity.class));
            return  true;
        }
        else if (id == R.id.menu_myorders){
            startActivity(new Intent(getApplicationContext(), ChasiMyOrdersActivity.class));
            return  true;
        } else if (id == R.id.menu_mycredits) {
            startActivity(new Intent(getApplicationContext(), CreditActivity.class));
            return true;
        }
        else if (id == R.id.logout_menuitem){
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.apply();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
        }

        return super.onOptionsItemSelected(item);
    }


}
