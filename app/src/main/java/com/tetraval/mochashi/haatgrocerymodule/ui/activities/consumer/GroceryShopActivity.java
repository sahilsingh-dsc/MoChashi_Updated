package com.tetraval.mochashi.haatgrocerymodule.ui.activities.consumer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import com.tetraval.mochashi.R;
import com.tetraval.mochashi.authmodule.LoginActivity;
import com.tetraval.mochashi.controller.StartActivity;
import com.tetraval.mochashi.haatgrocerymodule.data.adapters.ShopAdapter;
import com.tetraval.mochashi.haatgrocerymodule.data.models.ShopModel;
import com.tetraval.mochashi.ui.activities.MyAccountActivity;
import com.tetraval.mochashi.utils.AppConst;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class GroceryShopActivity extends AppCompatActivity {

    Toolbar toolbarShop;
    RecyclerView recyclerShop;
    ConstraintLayout constrainShop;

    List<ShopModel> shopModelList;
    ShopAdapter shopAdapter;
    RequestQueue requestQueue;
    ProgressDialog progressDialog;
    String tool_title;
    SharedPreferences master;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_shop);

        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        int account_status = bundle.getInt("account_status", 3);
        if (account_status == 3) {
            tool_title = "MoChashi 's Daily Haat";
        } else if (account_status == 2) {
            tool_title = "MoChashi 's Grocery";
        }

        master = this.getSharedPreferences("MASTER", 0);

        toolbarShop = findViewById(R.id.toolbarShop);
        setSupportActionBar(toolbarShop);
        Objects.requireNonNull(getSupportActionBar()).setTitle(tool_title);
        toolbarShop.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        Objects.requireNonNull(toolbarShop.getOverflowIcon()).setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);

        constrainShop = findViewById(R.id.constrainShop);

        recyclerShop = findViewById(R.id.recyclerShop);
        recyclerShop.setLayoutManager(new LinearLayoutManager(this));

        requestQueue = Volley.newRequestQueue(this);

        shopModelList = new ArrayList<>();
        shopModelList.clear();

        progressDialog.show();
        fetchShops(account_status);

    }

    private void fetchShops(int account_status) {

        StringRequest getRequest = new StringRequest(Request.Method.POST, AppConst.BASE_URL + "User_api/fetch_shops",
                response -> {
                    Log.d("Response", response);
                    try {

                        JSONObject jsonObject = new JSONObject(response);
                        String status = jsonObject.getString("status");
                        String result = jsonObject.getString("result");
                        if (status.equals("200")) {
                            JSONObject jsonObject1 = new JSONObject(result);
                            String shops = jsonObject1.getString("shops");
                            JSONArray jsonArray = new JSONArray(shops);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                                ShopModel shopModel = new ShopModel(
                                        jsonObject2.getString("shop_id"),
                                        jsonObject2.getString("shop_name"),
                                        jsonObject2.getString("shop_img"),
                                        jsonObject2.getString("city")
                                );
                                shopModelList.add(shopModel);
                            }
                        }
                        shopAdapter = new ShopAdapter(shopModelList, GroceryShopActivity.this);
                        recyclerShop.setAdapter(shopAdapter);
                        if (shopModelList.isEmpty()){
                            Snackbar snackbar = Snackbar
                                    .make(constrainShop, "Currently there no registered vendors.", Snackbar.LENGTH_INDEFINITE);
                            snackbar.show();
                        }
                        progressDialog.dismiss();

                    } catch (Exception e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                    }
                },
                error -> {
                    Log.d("Error.Response", error.toString());
                    progressDialog.dismiss();
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("type", account_status + "");
                return params;
            }
        };

        requestQueue.add(getRequest);
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
