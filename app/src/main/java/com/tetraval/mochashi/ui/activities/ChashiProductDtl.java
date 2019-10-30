package com.tetraval.mochashi.ui.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.tetraval.mochashi.R;
import com.tetraval.mochashi.authmodule.LoginActivity;
import com.tetraval.mochashi.controller.StartActivity;
import com.tetraval.mochashi.data.adapters.SliderAdapterExample;
import com.tetraval.mochashi.utils.AppConst;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class ChashiProductDtl extends AppCompatActivity {

    ImageView imgChashiPhoto;
    SliderView sliderView;
    TextView txtProductNameAndAddress, txtQtyAvl, txtChshiProductRate, txtNetPrice, warn, txtDelCharge, txtTotalPrice, txtCharge;
    EditText editTextQty;
    String delivery;
    RadioButton rbYes, rbNo;
    Toolbar toolbarCOPD;
    String del_state = "nopickup";
    double rate, qty;
    TextView txtUnit;
    double hosted;
    Button btnAddToCart;
    SharedPreferences images;
    RequestQueue requestQueue;
    SharedPreferences master;
    SharedPreferences preferences, masterdata;
    String userid,address, vendor_img;
    DecimalFormat precision = new DecimalFormat("0.00");
    ProgressDialog progressDialog;
    private ProgressDialog pDialog;
    String cat_id;
    double ttl = 0;
    String prate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chashi_product_dtl);
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        master = getApplicationContext().getSharedPreferences("MASTER", 0);
        userid=master.getString("user_id","0");
        address=master.getString("address","0");
        sliderView = findViewById(R.id.imageSlider);
        imgChashiPhoto = findViewById(R.id.imageView6);
        txtProductNameAndAddress = findViewById(R.id.textView24);
        txtQtyAvl = findViewById(R.id.textView26);
        txtChshiProductRate = findViewById(R.id.textView28);
        editTextQty = findViewById(R.id.editTextQty);
        txtNetPrice = findViewById(R.id.textView29);
        warn = findViewById(R.id.textView54);
        txtTotalPrice = findViewById(R.id.txtTotalPrice);
        txtDelCharge = findViewById(R.id.textView55);
        txtCharge = findViewById(R.id.textView56);
        txtUnit = findViewById(R.id.txtUnit);
        btnAddToCart = findViewById(R.id.btnAddToCart);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        requestQueue = Volley.newRequestQueue(this);

        images = getApplicationContext().getSharedPreferences("slider", 0);

        preferences = getApplicationContext().getSharedPreferences("loginpref", 0);
        masterdata = getApplicationContext().getSharedPreferences("MASTER", 0);


        rbYes = findViewById(R.id.rbYes);

        rbYes.setOnClickListener(view -> {
            del_state = "pickup";
            txtCharge.setVisibility(View.GONE);
            txtDelCharge.setVisibility(View.GONE);
            cal();
        });
        rbNo = findViewById(R.id.rbNo);
        rbNo.setChecked(true);
        txtCharge.setVisibility(View.VISIBLE);
        txtDelCharge.setVisibility(View.VISIBLE);

        rbNo.setOnClickListener(view -> {
            del_state = "nopickup";
            txtCharge.setVisibility(View.VISIBLE);
            txtDelCharge.setVisibility(View.VISIBLE);
            cal();
        });

        Bundle chashiBundle = getIntent().getExtras();
        String product_id = chashiBundle.getString("product_id");
        String vendor_id = chashiBundle.getString("vendor_id");
        vendor_img = chashiBundle.getString("vendor_img");

        toolbarCOPD = findViewById(R.id.toolbarCOPD);
        setSupportActionBar(toolbarCOPD);
        toolbarCOPD.setTitleTextColor(Color.WHITE);
        toolbarCOPD.getOverflowIcon().setColorFilter(Color.WHITE , PorterDuff.Mode.SRC_ATOP);

//        SharedPreferences.Editor editor = images.edit();
//        editor.putString("img1", chashiBundle.getString("img1"));
//        editor.putString("img2", chashiBundle.getString("img2"));
//        editor.putString("img3", chashiBundle.getString("img3"));
//        editor.putString("img4", chashiBundle.getString("img4"));
//        editor.apply();
//        Glide.with(getApplicationContext()).load(chashiBundle.getString("img1")).placeholder(R.drawable.productimage).into(imgChashiProductImage);

//        if (delivery.equals("1")){
//            warn.setVisibility(View.GONE);
//            rbNo.setVisibility(View.GONE);
//            rbYes.setVisibility(View.GONE);
//        }

        btnAddToCart.setOnClickListener(view -> {
            if (editTextQty.getText().toString().isEmpty() || editTextQty.getText().toString().equals("0")) {
                Toast.makeText(ChashiProductDtl.this, "Quantity must not be empty or 0", Toast.LENGTH_SHORT).show();
                editTextQty.requestFocus();
                return;
            }

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ChashiProductDtl.this);
                    alertDialogBuilder.setTitle("Place Order");
                    alertDialogBuilder.setMessage("Are sure you want to place order!");
                    alertDialogBuilder.setPositiveButton("Yes, Continue",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {

                                    ProductUpload();
                                }
                            });
                    alertDialogBuilder.setNegativeButton("No, Thanks", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();

//            }
            });


        editTextQty.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String txtQty = editTextQty.getText().toString();
                if (txtQty.isEmpty()){
                    qty = 0.00;
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                double delcharge = 0;

                String txtQty = editTextQty.getText().toString();
                if (!txtQty.isEmpty()){
                    qty = Double.parseDouble(txtQty);
                    double finalrate=qty*rate;
                    txtNetPrice.setText(precision.format(finalrate));
                    if (del_state.equals("nopickup")){
                        if (delivery.equals("0")){
                            delcharge = qty*0.20*10;
                            /*int ship = (int) delcharge;*/
                          /*  double shop_dou = ship;*/
                            txtDelCharge.setText(""+precision.format(delcharge));
                        }
                        double price = qty*rate;
                        double total = price+delcharge;
                        txtTotalPrice.setText(precision.format(total));
                        ttl = total;
                    }else {
                        double finalrate1=qty*rate;
                        txtTotalPrice.setText(precision.format(finalrate1));
                        if (qty > 5){
                            editTextQty.setError("Quantity cannot be more than 5 kgs");
                        }
                        if (qty > hosted){
                            editTextQty.setError("Quantity cannot be more than "+hosted+"kg");
                        }
                    }

                }else {
                    qty = 0.0;
                    txtDelCharge.setText("0.00");
                    txtNetPrice.setText("0.00");
                    txtTotalPrice.setText("0.00");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String txtQty = editTextQty.getText().toString();
                if (txtQty.isEmpty()){
                    qty = 0.00;
                }
            }
        });


        sliderView.setSliderAdapter(new SliderAdapterExample(ChashiProductDtl.this));
        sliderView.startAutoCycle();
        sliderView.setIndicatorAnimation(IndicatorAnimations.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);

        progressDialog.show();
        fetchChashiProduct(vendor_id, product_id);

    }

    private void fetchChashiProduct(String vendor_id, String product_id){

        StringRequest getRequest = new StringRequest(Request.Method.POST, AppConst.BASE_URL+"User_api/chashi_vendor_product",
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
                                String vendor = jsonObject1.getString("vendor_products");
                                JSONArray jsonArray = new JSONArray(vendor);
                                for (int i = 0; i<jsonArray.length(); i++){
                                    JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                                    if (jsonObject2.getString("product_id").equals(product_id)){

                                         cat_id = jsonObject2.getString("category_id");
                                        String p_name = jsonObject2.getString("product_name");
                                        String p_img1 = jsonObject2.getString("p_img1");
                                        String p_img2 = jsonObject2.getString("p_img2");
                                        String p_img3 = jsonObject2.getString("p_img3");
                                        String p_img4 = jsonObject2.getString("p_img4");
                                        String qty_hosted = jsonObject2.getString("qty_hosted");
                                        String qty_booked = jsonObject2.getString("qty_booked");
                                        String qty_avl = jsonObject2.getString("qty_avl");
                                        String unit = jsonObject2.getString("unit");
                                        prate = jsonObject2.getString("rate");
                                        String is_deliver = jsonObject2.getString("is_deliver");

                                        setProduct(cat_id, p_name, p_img1, p_img2, p_img3, p_img4, qty_hosted, qty_booked, qty_avl, unit, prate, is_deliver);
                                        progressDialog.dismiss();
                                    }
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
                params.put("vendor_id", vendor_id);
                return params;
            }
        };

        requestQueue.add(getRequest);

    }

    private void setProduct(String cat_id, String p_name, String p_img1, String p_img2, String p_img3, String p_img4, String qty_hosted, String qty_booked, String qty_avl, String unit, String dbrate, String is_deliver){

        getSupportActionBar().setTitle(p_name);
        SharedPreferences.Editor editor = images.edit();
        editor.putString("img1", p_img1);
        editor.putString("img2", p_img2);
        editor.putString("img3", p_img3);
        editor.putString("img4", p_img4);
        editor.apply();
        Glide.with(this).load(vendor_img).into(imgChashiPhoto);
        hosted = Double.parseDouble(qty_hosted);
        txtProductNameAndAddress.setText(p_name);
        txtQtyAvl.setText(qty_hosted+unit);
        delivery = is_deliver;
        if (delivery.equals("1")){
            txtDelCharge.setVisibility(View.GONE);
            warn.setVisibility(View.GONE);
            rbYes.setVisibility(View.GONE);
            txtCharge.setVisibility(View.GONE);
            rbNo.setVisibility(View.GONE);
        }
        if (delivery.equals("0")){
            txtDelCharge.setVisibility(View.VISIBLE);
            warn.setVisibility(View.VISIBLE);
            txtCharge.setVisibility(View.VISIBLE);
            rbYes.setVisibility(View.VISIBLE);
            rbNo.setVisibility(View.VISIBLE);
        }
        txtChshiProductRate.setText("₹"+precision.format(Double.parseDouble(String.valueOf(dbrate)))+"/"+unit);
        rate = Double.parseDouble(dbrate);
        txtUnit.setText(unit);
    }



    private void ProductUpload() {
        showpDialog();
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024);

        // Use HttpURLConnection as the HTTP client
        Network network = new BasicNetwork(new HurlStack());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConst.BASE_URL +"User_api/place_order",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Login", "onResponse: "+response);
                        hidepDialog();
                        try {
                            //converting response to json object
                            JSONObject obj = new JSONObject(response);
                            String status = obj.getString("status");
                            String message = obj.getString("message");
                            if (status.equals("200")) {
                                Toast.makeText(ChashiProductDtl.this, ""+message, Toast.LENGTH_SHORT).show();
                                finish();
                            }

                        } catch (JSONException e) {
                            hidepDialog();
                            Toast.makeText(ChashiProductDtl.this, ""+e, Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        hidepDialog();
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                //Adding the parameters to the request
                params.put("user_id",userid);
                params.put("vendor_id","0");
                params.put("total_price", txtTotalPrice.getText().toString());
                params.put("shipping_address",address);
                params.put("shipping_charge","0");
                params.put("tax","0");
                params.put("product_id",cat_id );
                params.put("product_qty", editTextQty.getText().toString());
                params.put("product_price",prate);
                Log.e("order detail",""+params);
                return params;
            }
        };
        RequestQueue queue = new RequestQueue(cache, network);
        queue.start();
        queue.add(stringRequest);
       /* RequestQueue requestQueue = Volley.newRequestQueue(GroceryCartActivity.this);
        requestQueue.add(stringRequest);*/
    }
    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }


    private void cal(){
        double delcharge = 0;

        String txtQty = editTextQty.getText().toString();
        if (!txtQty.isEmpty()){
            qty = Double.parseDouble(txtQty);
            txtNetPrice.setText(""+precision.format(Double.parseDouble(String.valueOf(rate*qty))));
            if (del_state.equals("nopickup")){
                if (delivery.equals("0")){
                    delcharge = qty*0.20*10;
                  /*  double ship = (int) delcharge;*/
                    txtDelCharge.setText(precision.format(delcharge));
                }
                double price = qty*rate;
                double total = price+delcharge;
                txtTotalPrice.setText(""+precision.format(total));
                ttl = total;
            }else {
                double price1 = qty*rate;
                txtTotalPrice.setText(precision.format(price1));
                if (qty > 5){
                    editTextQty.setError("Quantity cannot be more than 5 kgs");
                }
                if (qty > hosted){
                    editTextQty.setError("Quantity cannot be more than "+hosted+"kg");
                }
            }

        }else {
            qty = 0.0;
            txtNetPrice.setText("0.00");
            txtTotalPrice.setText("0.00");
        }
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
        } else if (id == R.id.menu_signout){
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
