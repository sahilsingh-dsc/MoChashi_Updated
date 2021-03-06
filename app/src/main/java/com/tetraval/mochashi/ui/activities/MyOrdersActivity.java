package com.tetraval.mochashi.ui.activities;

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
import com.tetraval.mochashi.data.adapters.HatOrdersAdapter;
import com.tetraval.mochashi.data.models.HatOrdersModel;
import com.tetraval.mochashi.utils.AppConst;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyOrdersActivity extends AppCompatActivity {

    Toolbar toolbarMyOrder;
    RecyclerView recyclerOrder;
    List<HatOrdersModel> hatOrdersModelList;
    HatOrdersAdapter hatOrdersAdapter;
    SharedPreferences preferences, masterdata;
    RequestQueue requestQueue;
    String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);
        requestQueue = Volley.newRequestQueue(this);
        toolbarMyOrder = findViewById(R.id.toolbarMyOrder);
        setSupportActionBar(toolbarMyOrder);
        getSupportActionBar().setTitle("My Orders");
        toolbarMyOrder.setTitleTextColor(Color.WHITE);
        toolbarMyOrder.getOverflowIcon().setColorFilter(Color.WHITE , PorterDuff.Mode.SRC_ATOP);

        recyclerOrder = findViewById(R.id.recyclerOrder);
        hatOrdersModelList = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerOrder.setLayoutManager(linearLayoutManager);

        preferences = getApplicationContext().getSharedPreferences("loginpref", 0);
        masterdata = getApplicationContext().getSharedPreferences("MASTER", 0);
        userid=masterdata.getString("user_id","0");

        fetchOrder();

    }

    private void fetchOrder() {

        StringRequest getRequest = new StringRequest(Request.Method.POST, AppConst.BASE_URL+"User_api/my_orders_grocery",
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
                                String orders = jsonObject1.getString("orders");
                                JSONArray jsonArray = new JSONArray(orders);
                                for (int i = 0; i<jsonArray.length(); i++){
                                    JSONObject jsonObject2 = jsonArray.getJSONObject(i);

                                    HatOrdersModel hatOrdersModel=new HatOrdersModel(
                                            jsonObject2.getString("order_id"),
                                            jsonObject2.getString("total_price"),
                                            jsonObject2.getString("date"),
                                            jsonObject2.getString("order_status")

                                    );
                                    hatOrdersModelList.add(hatOrdersModel);

                                }
                                hatOrdersAdapter = new HatOrdersAdapter(hatOrdersModelList, MyOrdersActivity.this);
                                recyclerOrder.setAdapter(hatOrdersAdapter);


                            }
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
                params.put("user_id", userid);
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
        } else if (id == R.id.menu_mycredits){
            startActivity(new Intent(getApplicationContext(), CreditActivity.class));
            return  true;
        } else if (id == R.id.menu_signout){
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("login_status", 0);
            editor.apply();
            Intent intent=new Intent(getApplicationContext(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
            return  true;
        }
        return super.onOptionsItemSelected(item);
    }

}
