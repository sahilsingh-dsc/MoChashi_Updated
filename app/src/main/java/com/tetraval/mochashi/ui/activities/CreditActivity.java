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
import android.widget.TextView;

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
import com.tetraval.mochashi.chashimodule.data.adapters.OrdersAdapter;
import com.tetraval.mochashi.chashimodule.ui.activities.ChasiMyOrdersActivity;
import com.tetraval.mochashi.controller.StartActivity;
import com.tetraval.mochashi.data.adapters.CreditAdapter;
import com.tetraval.mochashi.data.models.CreditModel;
import com.tetraval.mochashi.data.models.OrdersModel;
import com.tetraval.mochashi.utils.AppConst;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreditActivity extends AppCompatActivity {

    Toolbar toolbarCredit;
    RecyclerView recyclerCredit;
    CreditAdapter creditAdapter;
    List<CreditModel> creditModelList;
    SharedPreferences preferences, masterdata;
    RequestQueue requestQueue;
    String userid;
    ProgressDialog progressDialog;
    TextView txttotalamount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit);
        requestQueue = Volley.newRequestQueue(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        toolbarCredit = findViewById(R.id.toolbarCredit);
        txttotalamount = findViewById(R.id.textView11);
        setSupportActionBar(toolbarCredit);
        getSupportActionBar().setTitle("My Credits");
        toolbarCredit.setTitleTextColor(Color.WHITE);
        toolbarCredit.getOverflowIcon().setColorFilter(Color.WHITE , PorterDuff.Mode.SRC_ATOP);

        recyclerCredit = findViewById(R.id.recyclerCredit);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerCredit.setLayoutManager(linearLayoutManager);
        creditModelList = new ArrayList<>();

        preferences = getApplicationContext().getSharedPreferences("loginpref", 0);
        masterdata = getApplicationContext().getSharedPreferences("MASTER", 0);
        userid=masterdata.getString("user_id","0");

        fetchOrders();

    }

    private void fetchOrders() {
        progressDialog.show();

        StringRequest getRequest = new StringRequest(Request.Method.POST, AppConst.BASE_URL+"User_api/fetch_chashi_mycredits",
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response", response);
                        try {
                            progressDialog.dismiss();
                            JSONObject jsonObject = new JSONObject(response);
                            String status =  jsonObject.getString("status");
                            String totalcredit =  jsonObject.getString("total_credit");
                            txttotalamount.setText(totalcredit);
                            String result =  jsonObject.getString("result");
                            if (status.equals("200")) {
                                JSONObject jsonObject1 = new JSONObject(result);
                                String credits = jsonObject1.getString("credits");
                                JSONArray jsonArray = new JSONArray(credits);
                                for (int i = 0; i<jsonArray.length(); i++){
                                    JSONObject jsonObject2 = jsonArray.getJSONObject(i);

                                    CreditModel creditModel=new CreditModel(
                                            jsonObject2.getString("credit"),
                                            jsonObject2.getString("date")

                                    );
                                    creditModelList.add(creditModel);

                                }
                                creditAdapter = new CreditAdapter(creditModelList, getApplicationContext());
                                recyclerCredit.setAdapter(creditAdapter);


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
                        progressDialog.dismiss();

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("user_id", userid);
                Log.d("chasi","params=="+params);
                return params;
            }
        };

        requestQueue.add(getRequest);



    }

   /* private void fetchCredits(){

        creditModelList.add(new CreditModel("100", "Last purchase made on MoChashi App", "25-7-2018"));
        creditModelList.add(new CreditModel("500", "Last purchase made on MoChashi App", "25-7-2018"));
        creditModelList.add(new CreditModel("150", "Last purchase made on MoChashi App", "25-7-2018"));
        creditModelList.add(new CreditModel("100", "Last purchase made on MoChashi App", "25-7-2018"));
        creditModelList.add(new CreditModel("200", "Last purchase made on MoChashi App", "25-7-2018"));
        creditModelList.add(new CreditModel("300", "Last purchase made on MoChashi App", "25-7-2018"));
        creditModelList.add(new CreditModel("200", "Last purchase made on MoChashi App", "25-7-2018"));
        creditModelList.add(new CreditModel("100", "Last purchase made on MoChashi App", "25-7-2018"));

        creditAdapter = new CreditAdapter(creditModelList, this);
        recyclerCredit.setAdapter(creditAdapter);
    }*/


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
            SharedPreferences.Editor editor = masterdata.edit();
            editor.clear();
            editor.apply();
            Intent intent=new Intent(getApplicationContext(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

}
