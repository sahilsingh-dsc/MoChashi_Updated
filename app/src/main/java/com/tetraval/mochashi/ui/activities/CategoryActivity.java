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

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tetraval.mochashi.R;
import com.tetraval.mochashi.authmodule.LoginActivity;
import com.tetraval.mochashi.data.adapters.CategoryAdapter;
import com.tetraval.mochashi.data.models.CategoryModel;
import com.tetraval.mochashi.utils.AppConst;
import com.tetraval.mochashi.utils.SpacesItemDecoration;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryActivity extends AppCompatActivity {

    Toolbar toolbarCat;
    RecyclerView recyclerCategory;
    CategoryAdapter categoryAdapter;
    List<CategoryModel> categoryModelList;
    SharedPreferences preferences, masterdata;
    RequestQueue requestQueue;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");

        preferences = getApplicationContext().getSharedPreferences("loginpref", 0);
        masterdata = getApplicationContext().getSharedPreferences("MASTER", 0);

        Bundle catBundle = getIntent().getExtras();
        String shopname = catBundle.getString("shop_name");
        toolbarCat = findViewById(R.id.toolbarCat);
        setSupportActionBar(toolbarCat);
        getSupportActionBar().setTitle(shopname);
        toolbarCat.setTitleTextColor(Color.WHITE);
        toolbarCat.getOverflowIcon().setColorFilter(Color.WHITE , PorterDuff.Mode.SRC_ATOP);

        requestQueue = Volley.newRequestQueue(this);

        recyclerCategory = findViewById(R.id.recyclerCategory);
        categoryModelList = new ArrayList<>();

        fetchCategory();


    }

    private void fetchCategory(){
        progressDialog.show();
        recyclerCategory.setLayoutManager(new GridLayoutManager(this, 2));
        int spanCount = 2; // 3 columns
        int spacing = 50; // 50px
        boolean includeEdge = true;
        recyclerCategory.addItemDecoration(new SpacesItemDecoration(spanCount, spacing, includeEdge));

        StringRequest getRequest = new StringRequest(Request.Method.GET, AppConst.BASE_URL+"pro_category/list/",
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response", response);
                        try {
                            JSONArray jsonArray = new JSONArray(response);
//                            Toast.makeText(CategoryActivity.this, ""+jsonArray.length(), Toast.LENGTH_SHORT).show();
                            for (int i=0; i < jsonArray.length(); i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String id = jsonObject.getString("id");
                                String name = jsonObject.getString("name");
                                String image = jsonObject.getString("image");
                                CategoryModel categoryModel = new CategoryModel(id, image, name);
                                categoryModelList.add(categoryModel);
                            }
                            categoryAdapter = new CategoryAdapter(categoryModelList, CategoryActivity.this);
                            recyclerCategory.setAdapter(categoryAdapter);
                            progressDialog.dismiss();

                        } catch (Exception e) {
                            e.printStackTrace();
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
            startActivity(new Intent(getApplicationContext(), CartActivity.class));
            return true;
        } else if (id == R.id.menu_myaccount){
            startActivity(new Intent(getApplicationContext(), MyAccountActivity.class));
            return true;
        } else if (id == R.id.menu_myorders){
            startActivity(new Intent(getApplicationContext(), MyOrdersActivity.class));
            return  true;
        } else if (id == R.id.menu_mycredits) {
            startActivity(new Intent(getApplicationContext(), CreditActivity.class));
            return true;
        }
        else if (id == R.id.menu_signout){
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
