package com.tetraval.mochashi.chashimodule.ui.activities;

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
import com.tetraval.mochashi.controller.StartActivity;
import com.tetraval.mochashi.chashimodule.data.adapters.ChashiCategoryAdapter;
import com.tetraval.mochashi.chashimodule.data.models.ChashiCategoryModel;
import com.tetraval.mochashi.ui.activities.CreditActivity;
import com.tetraval.mochashi.ui.activities.MyOrdersActivity;
import com.tetraval.mochashi.utils.AppConst;
import com.tetraval.mochashi.utils.SpacesItemDecoration;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ChashiCategoryActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    Toolbar toolbarGroceryCategory;
    SearchView searchGroceryCategory;
    RecyclerView recyclerGroceryCategory;
    ConstraintLayout constrainCategory;

    List<ChashiCategoryModel> chashiCategoryModelList;
    ChashiCategoryAdapter chashiCategoryAdapter;
    RequestQueue requestQueue;
    SharedPreferences master;

    int spanCount = 2;
    int spacing = 50;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chashi_category);

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        progressDialog = new ProgressDialog(ChashiCategoryActivity.this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);

        master = this.getSharedPreferences("MASTER", 0);

        toolbarGroceryCategory = findViewById(R.id.toolbarGroceryCategory);
        setSupportActionBar(toolbarGroceryCategory);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Chashi Online");
        toolbarGroceryCategory.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        Objects.requireNonNull(toolbarGroceryCategory.getOverflowIcon()).setColorFilter(Color.WHITE , PorterDuff.Mode.SRC_ATOP);

        searchGroceryCategory = findViewById(R.id.searchGroceryCategory);
        searchGroceryCategory.setOnQueryTextListener(this);
        recyclerGroceryCategory = findViewById(R.id.recyclerGroceryCategory);
        recyclerGroceryCategory.setLayoutManager(new GridLayoutManager(this, 2));

        constrainCategory = findViewById(R.id.constrainCategory);

        chashiCategoryModelList = new ArrayList<>();
        chashiCategoryModelList.clear();

        progressDialog.show();
        fetchGroceryCategory();

    }

    private void fetchGroceryCategory(){
        recyclerGroceryCategory.addItemDecoration(new SpacesItemDecoration(spanCount, spacing, true));
        StringRequest getRequest = new StringRequest(Request.Method.POST, AppConst.BASE_URL+"User_api/chashi_category",
                response -> {
                    Log.d("Response", response);
                    try {

                        JSONObject jsonObject = new JSONObject(response);
                        String status =  jsonObject.getString("status");
                        String result =  jsonObject.getString("result");
                        if (status.equals("200")) {
                            JSONObject jsonObject1 = new JSONObject(result);
                            String category = jsonObject1.getString("category");
                            JSONArray jsonArray = new JSONArray(category);
                            for (int i = 0; i<jsonArray.length(); i++){
                                JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                                ChashiCategoryModel chashiCategoryModel = new ChashiCategoryModel(
                                        jsonObject2.getString("cat_id"),
                                        jsonObject2.getString("cat_name"),
                                        jsonObject2.getString("cat_img")
                                );
                                chashiCategoryModelList.add(chashiCategoryModel);
                            }
                        }
                        chashiCategoryAdapter = new ChashiCategoryAdapter(chashiCategoryModelList,
                                ChashiCategoryActivity.this);
                        recyclerGroceryCategory.setAdapter(chashiCategoryAdapter);
                        progressDialog.dismiss();
                        if (chashiCategoryModelList.isEmpty()){
                            Snackbar snackbar = Snackbar
                                    .make(constrainCategory, "Chashi online does not have products currently.", Snackbar.LENGTH_INDEFINITE);
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
        List<ChashiCategoryModel> chashiCategoryModelListNew = new ArrayList<>();
        for (ChashiCategoryModel chashiCategoryModel : chashiCategoryModelList){
            String cat_name = chashiCategoryModel.getProduct_name().toLowerCase().replace(" ", "");
            if (cat_name.contains(newText))
                chashiCategoryModelListNew.add(chashiCategoryModel);
        }
        chashiCategoryAdapter.setfilter(chashiCategoryModelListNew);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.chashi_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.home_menuitem) {
            startActivity(new Intent(getApplicationContext(), StartActivity.class));
            finish();
            return true;
        }
        else if (id == R.id.menu_myorders){
            startActivity(new Intent(getApplicationContext(), ChasiMyOrdersActivity.class));
            return  true;
        } else if (id == R.id.menu_mycredits) {
            startActivity(new Intent(getApplicationContext(), CreditActivity.class));
            return true;
        }
        else if (id == R.id.logout_menuitem){
            SharedPreferences.Editor editor = master.edit();
            editor.clear();
            editor.apply();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
        }

        return super.onOptionsItemSelected(item);
    }


}
