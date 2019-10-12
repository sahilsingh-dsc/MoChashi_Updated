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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tetraval.mochashi.R;
import com.tetraval.mochashi.authmodule.LoginActivity;
import com.tetraval.mochashi.data.adapters.ProductAdapters;
import com.tetraval.mochashi.utils.AppConst;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductActivity extends AppCompatActivity {

    Toolbar toolbarProducts;
    String productname, productcat;
    RecyclerView recyclerProduct;
    ProductAdapters productAdapters;
    //List<ProductModel> productModelList;
    SharedPreferences preferences, masterdata;
    RequestQueue requestQueue;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

//        Bundle productBundle = getIntent().getExtras();
//        productname = productBundle.getString("productname");
//        productcat = productBundle.getString("productcat");
//
//        preferences = getApplicationContext().getSharedPreferences("loginpref", 0);
//        masterdata = getApplicationContext().getSharedPreferences("MASTER", 0);
//
//        requestQueue = Volley.newRequestQueue(this);
//
//        progressDialog = new ProgressDialog(this);
//        progressDialog.setMessage("Please Wait...");
//
//        toolbarProducts = findViewById(R.id.toolbarProducts);
//        setSupportActionBar(toolbarProducts);
//        getSupportActionBar().setTitle(productname);
//        toolbarProducts.setTitleTextColor(Color.WHITE);
//        toolbarProducts.getOverflowIcon().setColorFilter(Color.WHITE , PorterDuff.Mode.SRC_ATOP);
//
//        recyclerProduct = findViewById(R.id.recyclerProduct);
//        productModelList = new ArrayList<>();
//
//        fetchProducts();


    }

//    private void fetchProducts(){
//        progressDialog.show();
//        recyclerProduct.setLayoutManager(new LinearLayoutManager(this));
//
//        StringRequest getRequest = new StringRequest(Request.Method.GET, AppConst.BASE_URL+"productlist/?category="+productcat,
//                new Response.Listener<String>()
//                {
//                    @Override
//                    public void onResponse(String response) {
//                        Log.d("Response", response);
//                        try {
//                            JSONArray jsonArray = new JSONArray(response);
//                            Toast.makeText(ProductActivity.this, ""+jsonArray.length(), Toast.LENGTH_SHORT).show();
//                            for (int i=0; i < jsonArray.length(); i++){
//                                JSONObject jsonObject = jsonArray.getJSONObject(i);
//                                String id = jsonObject.getString("id");
//                                String product_image = jsonObject.getString("product_image");
//                                String name = jsonObject.getString("name");
//                                String brand = jsonObject.getString("brand");
//                                String description = jsonObject.getString("description");
//                                String in_stock = jsonObject.getString("in_stock");
//                                String category = jsonObject.getString("category");
//                                String user = jsonObject.getString("user");
//
////                                ProductModel productModel = new ProductModel(
////                                        id,
////                                        product_image,
////                                        name,
////                                        "500",
////                                        "500",
////                                        "500",
////                                        description,
////                                        category,
////                                        brand,
////                                        in_stock,
////                                        user);
//
//                             //    productModelList.add(productModel);
//                            }
//
//                            productAdapters = new ProductAdapters(productModelList, ProductActivity.this);
//                            recyclerProduct.setAdapter(productAdapters);
//                            progressDialog.dismiss();
//
//
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                            progressDialog.dismiss();
//
//                        }
//
//                    }
//                },
//                new Response.ErrorListener()
//                {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.d("Error.Response", error.toString());
//                        progressDialog.dismiss();
//
//                    }
//                }
//        ) {
//            @Override
//            protected Map<String, String> getParams()
//            {
//                Map<String, String>  params = new HashMap<String, String>();
//                return params;
//            }
//        };
//
//        requestQueue.add(getRequest);
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.header_menu, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        if (id == R.id.action_favorite) {
//            startActivity(new Intent(getApplicationContext(), CartActivity.class));
//            return true;
//        } else if (id == R.id.menu_myaccount){
//            startActivity(new Intent(getApplicationContext(), MyAccountActivity.class));
//            return true;
//        } else if (id == R.id.menu_myorders){
//            startActivity(new Intent(getApplicationContext(), MyOrdersActivity.class));
//            return  true;
//        } else if (id == R.id.menu_mycredits){
//            startActivity(new Intent(getApplicationContext(), CreditActivity.class));
//            return  true;
//        } else if (id == R.id.menu_signout){
//            SharedPreferences.Editor editor = preferences.edit();
//            editor.putInt("login_status", 0);
//            editor.apply();
//            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
//            finish();
//            return  true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

}
