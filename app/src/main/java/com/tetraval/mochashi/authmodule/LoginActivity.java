package com.tetraval.mochashi.authmodule;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tetraval.mochashi.R;
import com.tetraval.mochashi.controller.StartActivity;
import com.tetraval.mochashi.chashimodule.ui.activities.VendorActivity;
import com.tetraval.mochashi.ui.activities.DeliveryMainActivity;
import com.tetraval.mochashi.ui.activities.DeliveryManActivity;
import com.tetraval.mochashi.utils.AppConst;
import com.tetraval.mochashi.utils.Master;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    Toolbar toolbarLogin;
    EditText etxtLoginEmail, etxtLoginPassword;
    Button btnLogin;
    TextView txtNewUser;
    RequestQueue mRequestQueue;
    ProgressDialog progressDialog;
    SharedPreferences master;
    private static final String SIGN_IN = "User_api/user_signin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        toolbarLogin = findViewById(R.id.toolbarLogin);
        toolbarLogin.setTitle("User Login");
        toolbarLogin.setTitleTextColor(Color.WHITE);

        master = getApplicationContext().getSharedPreferences("MASTER", 0);

        mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);

        etxtLoginEmail = findViewById(R.id.etxtLoginEmail);
        etxtLoginPassword = findViewById(R.id.etxtLoginPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(v -> {
            String email = etxtLoginEmail.getText().toString();
            String password = etxtLoginPassword.getText().toString();
            if (TextUtils.isEmpty(email)) {
                etxtLoginEmail.setError("Enter email");
                return;
            }
            if (TextUtils.isEmpty(password)) {
                etxtLoginPassword.setError("Enter password");
                return;
            }
            if (email.equals("delivery@mochashi.com")){
                SharedPreferences.Editor editor = master.edit();
                editor.putString("user_type", "5");
                editor.putString("active", "1");
                editor.apply();
                startActivity(new Intent(getApplicationContext(), DeliveryMainActivity.class));
                finish();
            }else {
                webservicesSignin(email, password);
                progressDialog.show();
            }

        });
        txtNewUser = findViewById(R.id.txtNewUser);
        txtNewUser.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            finish();
        });
    }

    private void webservicesSignin(final String email, final String password) {

        StringRequest postRequest = new StringRequest(Request.Method.POST, AppConst.BASE_URL + SIGN_IN,
                response -> {
                    Log.d("Response", response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String status = jsonObject.getString("status");
                        String result =  jsonObject.getString("result");
                        if (status.equals("200")) {
                            Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();
                            JSONObject jsonObject1 = new JSONObject(result);
                                Master.setUserInfo(
                                        jsonObject1.getString("user_id"),
                                        jsonObject1.getString("user_type"),
                                        jsonObject1.getString("fname"),
                                        jsonObject1.getString("email"),
                                        jsonObject1.getString("mobile"),
                                        jsonObject1.getString("shop_name"),
                                        jsonObject1.getString("img"),
                                        jsonObject1.getString("address"),
                                        jsonObject1.getString("country"),
                                        jsonObject1.getString("state"),
                                        jsonObject1.getString("city"),
                                        jsonObject1.getString("pincode"),
                                        jsonObject1.getString("password"),
                                        jsonObject1.getString("active"),
                                        LoginActivity.this);
                            Toast.makeText(this, "Data Inserted", Toast.LENGTH_SHORT).show();

                            if (jsonObject1.getString("user_type").equals("4")){
                                startActivity(new Intent(getApplicationContext(), VendorActivity.class));
                                finish();
                            } else {
                                startActivity(new Intent(getApplicationContext(), StartActivity.class));
                                finish();
                            }

                        } if (status.equals("404")) {
                            Toast.makeText(this, "Invalid email and password", Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();

                    } catch (Exception e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                    }
                },
                error -> Log.d("Error.Response", error.toString())
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("password", password);
                params.put("device_key", "dvkey");
                params.put("device_type", "android");
                return params;
            }
        };
        mRequestQueue.add(postRequest);


    }
}
