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
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import com.tetraval.mochashi.R;
import com.tetraval.mochashi.authmodule.LoginActivity;
import com.tetraval.mochashi.chashimodule.ui.activities.ChasiMyOrdersActivity;
import com.tetraval.mochashi.controller.StartActivity;
import com.tetraval.mochashi.haatgrocerymodule.data.adapters.GroceryCateogryAdapter;
import com.tetraval.mochashi.haatgrocerymodule.data.models.GroceryCategoryModel;
import com.tetraval.mochashi.ui.activities.CreditActivity;
import com.tetraval.mochashi.ui.activities.MyAccountActivity;
import com.tetraval.mochashi.utils.AppConst;
import com.tetraval.mochashi.utils.SpacesItemDecoration;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class GroceryCategoryActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    Toolbar toolbarGroceryCategory;
    SearchView searchGroceryCategory;
    RecyclerView recyclerGroceryCategory;
    ConstraintLayout constrainCategory;

    List<GroceryCategoryModel> groceryCategoryModelList;
    GroceryCateogryAdapter groceryCateogryAdapter;
    RequestQueue requestQueue;
    Bundle categoryBundle;
    SharedPreferences master, shopPref;



    int spanCount = 2;
    int spacing = 50;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_category);

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        progressDialog = new ProgressDialog(GroceryCategoryActivity.this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);

        categoryBundle = getIntent().getExtras();
        assert categoryBundle != null;
        String shop_id = categoryBundle.getString("shop_id");
        String shop_name = categoryBundle.getString("shop_name");

        shopPref = this.getSharedPreferences("shopPref", 0);
        master = this.getSharedPreferences("MASTER", 0);
        SharedPreferences.Editor editor = shopPref.edit();
        editor.putString("shop_name", shop_name);
        editor.putString("shop_id", shop_id);
        editor.apply();

        toolbarGroceryCategory = findViewById(R.id.toolbarGroceryCategory);
        setSupportActionBar(toolbarGroceryCategory);
        Objects.requireNonNull(getSupportActionBar()).setTitle(shop_name);
        toolbarGroceryCategory.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        Objects.requireNonNull(toolbarGroceryCategory.getOverflowIcon()).setColorFilter(Color.WHITE , PorterDuff.Mode.SRC_ATOP);

        searchGroceryCategory = findViewById(R.id.searchGroceryCategory);
        searchGroceryCategory.setOnQueryTextListener(this);
        recyclerGroceryCategory = findViewById(R.id.recyclerGroceryCategory);
        recyclerGroceryCategory.setLayoutManager(new GridLayoutManager(this, 2));

        constrainCategory = findViewById(R.id.constrainCategory);

        groceryCategoryModelList = new ArrayList<>();
        groceryCategoryModelList.clear();

        //if ()
        progressDialog.show();
        fetchGroceryCategory(shop_id, shop_name);

    }

    private void fetchGroceryCategory(String shop_id, String shop_name){
        recyclerGroceryCategory.addItemDecoration(new SpacesItemDecoration(spanCount, spacing, true));
        StringRequest getRequest = new StringRequest(Request.Method.POST, AppConst.BASE_URL+"User_api/fetch_category",
                response -> {
                    Log.d("Response", response);
                    try {

                        JSONObject jsonObject = new JSONObject(response);
                        String status =  jsonObject.getString("status");
                        //String message = jsonObject.getString("message");
                        String result =  jsonObject.getString("result");
                        if (status.equals("200")) {
                            JSONObject jsonObject1 = new JSONObject(result);
                            String category = jsonObject1.getString("category");
                            JSONArray jsonArray = new JSONArray(category);
                            for (int i = 0; i<jsonArray.length(); i++){
                                JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                                GroceryCategoryModel groceryCategoryModel = new GroceryCategoryModel(
                                        jsonObject2.getString("cat_id"),
                                        jsonObject2.getString("cat_img"),
                                        jsonObject2.getString("cat_name")
                                );
                                groceryCategoryModelList.add(groceryCategoryModel);
                            }
                        }
                        groceryCateogryAdapter = new GroceryCateogryAdapter(groceryCategoryModelList,
                                GroceryCategoryActivity.this);
                        recyclerGroceryCategory.setAdapter(groceryCateogryAdapter);
                        progressDialog.dismiss();
                        if (groceryCategoryModelList.isEmpty()){
                            Snackbar snackbar = Snackbar
                                    .make(constrainCategory, shop_name+" does not have products currently.", Snackbar.LENGTH_INDEFINITE);
                            snackbar.show();
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
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<>();
                params.put("shop_id", shop_id);
                return params;
            }
        };

        requestQueue.add(getRequest);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        List<GroceryCategoryModel> groceryCategoryModelListNew = new ArrayList<>();
        for (GroceryCategoryModel groceryCategoryModel : groceryCategoryModelList){
            String cat_name = groceryCategoryModel.getCat_name().toLowerCase().replace(" ", "");
            if (cat_name.contains(newText))
                groceryCategoryModelListNew.add(groceryCategoryModel);
        }
        groceryCateogryAdapter.setfilter(groceryCategoryModelListNew);
        return true;
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
        }else if (id == R.id.menu_myaccount){
            startActivity(new Intent(getApplicationContext(), MyAccountActivity.class));
            return true;
        }else if (id == R.id.menu_myorders){
            startActivity(new Intent(getApplicationContext(), ChasiMyOrdersActivity.class));
            return  true;
        }else if (id == R.id.menu_home) {
            startActivity(new Intent(getApplicationContext(), StartActivity.class));
            return true;
        }else if (id == R.id.menu_mycredits){
            startActivity(new Intent(getApplicationContext(), CreditActivity.class));
            return  true;
        }else if (id == R.id.menu_signout){
            SharedPreferences.Editor editor = master.edit();
            editor.clear();
            editor.apply();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
        }

        return super.onOptionsItemSelected(item);
    }


}
