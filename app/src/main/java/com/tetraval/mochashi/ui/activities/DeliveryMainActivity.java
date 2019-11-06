package com.tetraval.mochashi.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.tetraval.mochashi.R;
import com.tetraval.mochashi.authmodule.LoginActivity;
import com.tetraval.mochashi.data.adapters.VendorDeliveryAdapter;
import com.tetraval.mochashi.data.models.VendorDeliveryModel;
import com.tetraval.mochashi.haatgrocerymodule.data.adapters.GroceryProductAdapter;
import com.tetraval.mochashi.haatgrocerymodule.data.models.GroceryProductModel;
import com.tetraval.mochashi.haatgrocerymodule.ui.activities.consumer.GroceryProductActivity;
import com.tetraval.mochashi.ui.fragments.CustomerListFragment;
import com.tetraval.mochashi.ui.fragments.VendorListFragment;
import com.tetraval.mochashi.utils.AppConst;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeliveryMainActivity extends AppCompatActivity {

    TabLayout tabDelivery;
    String tabstate = "vendor";
    Toolbar toolbar;
    SharedPreferences master;
    List<VendorDeliveryModel> vendorDeliveryModelList;
    VendorDeliveryAdapter vendorDeliveryAdapter;
    RecyclerView recyclerDelVendor;
    RequestQueue requestQueue;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_main);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Delivery Channel");
        requestQueue = Volley.newRequestQueue(this);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.getOverflowIcon().setColorFilter(Color.WHITE , PorterDuff.Mode.SRC_ATOP);
        master = getApplicationContext().getSharedPreferences("MASTER", 0);
        vendorDeliveryModelList = new ArrayList<>();
        recyclerDelVendor = findViewById(R.id.recyclerDelVendor);
        recyclerDelVendor.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        FetchVendorlist();

    }
    private void FetchVendorlist() {
        progressDialog.show();
        StringRequest getRequest = new StringRequest(Request.Method.POST, AppConst.BASE_URL + "User_api/chashi_booking_vendor",
                response -> {
                    Log.d("Response", response);
                    try {
                        progressDialog.dismiss();
                        JSONObject jsonObject = new JSONObject(response);
                        String status = jsonObject.getString("status");
                        String result = jsonObject.getString("result");
                        if (status.equals("200")) {
                            JSONObject jsonObject1 = new JSONObject(result);
                            String orders = jsonObject1.getString("orders");
                            JSONArray jsonArray = new JSONArray(orders);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                                VendorDeliveryModel vendorDeliveryModel = new VendorDeliveryModel(
                                        jsonObject2.getString("product_id"),
                                        jsonObject2.getString("shop_name"),
                                        jsonObject2.getString("shop_address"),
                                        jsonObject2.getString("product_name"),
                                        jsonObject2.getString("rate"),
                                        jsonObject2.getString("unit"),
                                        jsonObject2.getString("qty_hosted"),
                                        jsonObject2.getString("qty_booked")

                                );

                                vendorDeliveryModelList.add(vendorDeliveryModel);
                            }
                            vendorDeliveryAdapter = new VendorDeliveryAdapter(vendorDeliveryModelList, getApplicationContext());
                            recyclerDelVendor.setAdapter(vendorDeliveryAdapter);


                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                    }
                },
                error -> {
                    Log.d("Error.Response", error.toString());
                    progressDialog.dismiss();
                }
        ) /*{
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("", cat_id);
                return params;
            }
        }*/;
        requestQueue.add(getRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.vendor_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.vendor_menu_logout){
            SharedPreferences.Editor editor = master.edit();
            editor.clear();
            editor.apply();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


}

      /*  FragmentManager fm = getSupportFragmentManager();
        VendorListFragment vendorListFragment = new VendorListFragment();
        fm.beginTransaction().add(R.id.frameDelivery,vendorListFragment).commit();

        tabDelivery = findViewById(R.id.tabDelivery);
        tabDelivery.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Toast.makeText(DeliveryMainActivity.this, ""+tab.getText().toString(), Toast.LENGTH_SHORT).show();
                if (tab.getText().toString().equals("Vendor List")){
                    FragmentManager fm = getSupportFragmentManager();
                    VendorListFragment vendorListFragment = new VendorListFragment();
                    fm.beginTransaction().replace(R.id.frameDelivery,vendorListFragment).commit();
                }else if (tab.getText().toString().equals("Customer List")){
                    FragmentManager fm = getSupportFragmentManager();
                    CustomerListFragment customerListFragment = new CustomerListFragment();
                    fm.beginTransaction().replace(R.id.frameDelivery,customerListFragment).commit();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
//                Toast.makeText(DeliveryMainActivity.this, ""+tab.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });
*/
