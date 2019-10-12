package com.tetraval.mochashi.haatgrocerymodule.ui.activities;

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
import android.widget.Spinner;
import android.widget.TextView;

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
import com.tetraval.mochashi.haatgrocerymodule.data.adapters.GroceryProductAdapter;
import com.tetraval.mochashi.haatgrocerymodule.data.adapters.SpinnerCategoryAdapter;
import com.tetraval.mochashi.haatgrocerymodule.data.models.GroceryProductModel;
import com.tetraval.mochashi.haatgrocerymodule.data.models.SpinnerCategoryModel;
import com.tetraval.mochashi.utils.AppConst;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class GroceryProductActivity extends AppCompatActivity {

    Toolbar toolbarGroceryProduct;
    RecyclerView recyclerGroceryProduct;
    ConstraintLayout constrainProducts;

    Bundle productBundle;
    List<GroceryProductModel> groceryProductModelList;
    TextView txtSelectedCategory;

    GroceryProductAdapter groceryProductAdapter;
    RequestQueue requestQueue;
    SpinnerCategoryAdapter spinnerCategoryAdapter;
    ArrayList<SpinnerCategoryModel> spinnerCategoryModelArrayList;
    SharedPreferences master, shopPref;
    ProgressDialog progressDialog;

    Spinner spinnerCategory, spinnerWeight, spinnerSort;
    String[] sortby = {"Sort By", "Price Low to High", "Price High to Low"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_product);

        toolbarGroceryProduct = findViewById(R.id.toolbarGroceryProduct);

        shopPref = this.getSharedPreferences("shopPref", 0);
        master = this.getSharedPreferences("MASTER", 0);

        productBundle = getIntent().getExtras();
        assert productBundle != null;

        String cat_id = productBundle.getString("cat_id");
        String cat_name = productBundle.getString("cat_name");

        constrainProducts = findViewById(R.id.constrainProducts);

        setSupportActionBar(toolbarGroceryProduct);
        Objects.requireNonNull(getSupportActionBar()).setTitle(shopPref.getString("shop_name", cat_name));
        toolbarGroceryProduct.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        Objects.requireNonNull(toolbarGroceryProduct.getOverflowIcon()).setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);

        recyclerGroceryProduct = findViewById(R.id.recyclerGroceryProduct);
        recyclerGroceryProduct.setLayoutManager(new LinearLayoutManager(this));

        txtSelectedCategory = findViewById(R.id.txtSelectedCategory);
        txtSelectedCategory.setText(cat_name);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);

        requestQueue = Volley.newRequestQueue(this);

        groceryProductModelList = new ArrayList<>();
        groceryProductModelList.clear();

        spinnerCategoryModelArrayList = new ArrayList<>();

        spinnerCategory = findViewById(R.id.spinnerCategory);
        spinnerWeight = findViewById(R.id.spinnerWeight);
        spinnerSort = findViewById(R.id.spinnerSort);

        ArrayAdapter<String> sortAdapter = new ArrayAdapter<>(GroceryProductActivity.this, R.layout.support_simple_spinner_dropdown_item, sortby);
        spinnerSort.setAdapter(sortAdapter);
        spinnerSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String sortoptions = adapterView.getItemAtPosition(i).toString();
                if (sortoptions.equals("Price Low to High")){
                    sortByPriceLH();
                    groceryProductAdapter.notifyDataSetChanged();
                } else if (sortoptions.equals("Price High to Low")){
                    sortByPriceHL();
                    groceryProductAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                TextView catid = adapterView.findViewById(R.id.catid);
                TextView txtSpinnerItem = adapterView.findViewById(R.id.txtSpinnerItem);
                if (!catid.getText().toString().equals("0")) {
                    fetechGroceryProducts(catid.getText().toString(), txtSpinnerItem.getText().toString());
                    txtSelectedCategory.setText(txtSpinnerItem.getText().toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        fetechGroceryProducts(cat_id, cat_name);
        fetchGroceryCategory();

    }

    private void fetechGroceryProducts(String cat_id, String cat_name) {
        progressDialog.show();
        StringRequest getRequest = new StringRequest(Request.Method.POST, AppConst.BASE_URL + "User_api/fetch_products",
                response -> {
                    Log.d("Response", response);
                    try {

                        JSONObject jsonObject = new JSONObject(response);
                        String status = jsonObject.getString("status");
                        String result = jsonObject.getString("result");
                        if (status.equals("200")) {
                            ArrayList<String> weight = new ArrayList<>();
                            weight.add("Select Weight");
                            JSONObject jsonObject1 = new JSONObject(result);
                            String products = jsonObject1.getString("products");
                            JSONArray jsonArray = new JSONArray(products);
                            groceryProductModelList.clear();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                                GroceryProductModel groceryProductModel = new GroceryProductModel(
                                        jsonObject2.getString("p_id"),
                                        jsonObject2.getString("p_name"),
                                        jsonObject2.getString("p_desc"),
                                        jsonObject2.getString("p_img"),
                                        jsonObject2.getString("mrp"),
                                        jsonObject2.getString("sale_price"),
                                        jsonObject2.getString("p_qty"),
                                        jsonObject2.getString("unit"),
                                        jsonObject2.getString("available"),
                                        cat_name
                                );
                                String qty = jsonObject2.getString("p_qty");
                                String unit = jsonObject2.getString("unit");
                                String product_weight = qty + unit;
                                weight.add(product_weight);
                                groceryProductModelList.add(groceryProductModel);

                                progressDialog.dismiss();
                            }

                            ArrayAdapter<String> mArrayAdapter = new ArrayAdapter<>(GroceryProductActivity.this,
                                    R.layout.support_simple_spinner_dropdown_item, weight);
                            spinnerWeight.setAdapter(mArrayAdapter);

                        }
                        groceryProductAdapter = new GroceryProductAdapter(groceryProductModelList, GroceryProductActivity.this);
                        recyclerGroceryProduct.setAdapter(groceryProductAdapter);
                        if (groceryProductModelList.isEmpty()){
                            Snackbar snackbar = Snackbar
                                    .make(constrainProducts, cat_name+" are not available currently.", Snackbar.LENGTH_LONG);
                            snackbar.show();
                        }
                        spinnerCategory.setSelection(0);
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
                params.put("cat_id", cat_id);
                return params;
            }
        };

        requestQueue.add(getRequest);
    }

    private void fetchGroceryCategory() {
        progressDialog.show();
        StringRequest getRequest = new StringRequest(Request.Method.POST, AppConst.BASE_URL + "User_api/fetch_category",
                response -> {
                    Log.d("Response", response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String status = jsonObject.getString("status");
                        String result = jsonObject.getString("result");
                        if (status.equals("200")) {
                            JSONObject jsonObject1 = new JSONObject(result);
                            String category = jsonObject1.getString("category");
                            JSONArray jsonArray = new JSONArray(category);
                            spinnerCategoryModelArrayList.add(new SpinnerCategoryModel("Select Category", "0"));
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                                SpinnerCategoryModel spinnerCategoryModel = new SpinnerCategoryModel(
                                        jsonObject2.getString("cat_name"),
                                        jsonObject2.getString("cat_id")
                                );
                                spinnerCategoryModelArrayList.add(spinnerCategoryModel);
                            }
                            spinnerCategoryAdapter = new SpinnerCategoryAdapter(getApplicationContext(), spinnerCategoryModelArrayList);
                            spinnerCategory.setAdapter(spinnerCategoryAdapter);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                },
                error -> Log.d("Error.Response", error.toString())
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("shop_id", Objects.requireNonNull(shopPref.getString("shop_id", "")));
                return params;
            }
        };

        requestQueue.add(getRequest);
    }

    private void sortByPriceHL() {
        Collections.sort(groceryProductModelList, (l1, l2) -> {
            if (Double.parseDouble(l1.getProduct_saleprice()) < Double.parseDouble(l2.getProduct_saleprice())) {
                return 1;
            } else if (Double.parseDouble(l1.getProduct_saleprice()) > Double.parseDouble(l2.getProduct_saleprice())) {
                return -1;
            } else {
                return 0;
            }
        });
    }

    private void sortByPriceLH() {
        Collections.sort(groceryProductModelList, (l1, l2) -> {
            if (Double.parseDouble(l1.getProduct_saleprice()) > Double.parseDouble(l2.getProduct_saleprice())) {
                return 1;
            } else if (Double.parseDouble(l1.getProduct_saleprice()) < Double.parseDouble(l2.getProduct_saleprice())) {
                return -1;
            } else {
                return 0;
            }
        });
    }

//    private void sortByNew() {
//        Collections.sort(chashiModelList, (l1, l2) -> {
//            if (Integer.parseInt(l1.getP_timestamp()) < Integer.parseInt(l2.getP_timestamp())) {
//                return 1;
//            } else if (Integer.parseInt(l1.getP_timestamp()) > Integer.parseInt(l2.getP_timestamp())) {
//                return -1;
//            } else {
//                return 0;
//            }
//        });
//    }


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
