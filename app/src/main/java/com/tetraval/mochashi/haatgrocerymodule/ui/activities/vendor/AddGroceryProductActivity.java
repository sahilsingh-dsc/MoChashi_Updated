package com.tetraval.mochashi.haatgrocerymodule.ui.activities.vendor;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.tetraval.mochashi.R;
import com.tetraval.mochashi.authmodule.LoginActivity;
import com.tetraval.mochashi.haatgrocerymodule.data.adapters.SpinnerCategoryAdapter;
import com.tetraval.mochashi.haatgrocerymodule.data.models.SpinnerCategoryModel;
import com.tetraval.mochashi.utils.AppConst;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AddGroceryProductActivity extends AppCompatActivity {

    Toolbar toolbarAddPdt;
    ImageView pImage;
    EditText txtProductName, txtAvlQty, txtMRP, txtSALE, txtPDesc;
    Spinner spinnerUnit, spinnerCat;
    Button btnAddProduct;
    Boolean i1;
    String unit, imgurl1;
    String selected_cat ="17";
    private static final int CAMERA_REQUEST = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    ProgressDialog progressDialog;
    FirebaseStorage storage;
    StorageReference storageReference;
    RequestQueue requestQueue;
    SpinnerCategoryAdapter spinnerCategoryAdapter;
    ArrayList<SpinnerCategoryModel> spinnerCategoryModelArrayList;
    SharedPreferences master;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_grocery_product);

        master = getApplication().getSharedPreferences("MASTER", 0);

        toolbarAddPdt = findViewById(R.id.toolbarAddPdt);
        setSupportActionBar(toolbarAddPdt);
        getSupportActionBar().setTitle("Add Product");
        toolbarAddPdt.setTitleTextColor(Color.WHITE);
        toolbarAddPdt.getOverflowIcon().setColorFilter(Color.WHITE , PorterDuff.Mode.SRC_ATOP);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");

        requestQueue = Volley.newRequestQueue(this);

        pImage = findViewById(R.id.imageView7);
        spinnerUnit = findViewById(R.id.spinner2);
        spinnerCat = findViewById(R.id.spinnerCat);
        txtPDesc = findViewById(R.id.txtPDesc);
        btnAddProduct = findViewById(R.id.btnAddProduct);
        txtProductName = findViewById(R.id.txtProductName);
        txtAvlQty = findViewById(R.id.txtAvlQty);
        txtMRP = findViewById(R.id.txtMRP);
        txtSALE = findViewById(R.id.txtSALE);

        String[] units = {"Kg", "L", "M"};
        ArrayAdapter arrayAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, units);
        spinnerUnit = findViewById(R.id.spinner2);
        spinnerUnit.setAdapter(arrayAdapter);
        spinnerUnit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                unit = (String) adapterView.getItemAtPosition(i);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        pImage.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                i1 = false;
                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                } else {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                }
            }
        });

      /*  spinnerCategoryModelArrayList = new ArrayList<>();
        if (spinnerCategoryModelArrayList.size()<= 0){
            btnAddProduct.setVisibility(View.VISIBLE);
        }else{
            btnAddProduct.setVisibility(View.GONE);
        }*/
        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!i1) {
                    Toast.makeText(AddGroceryProductActivity.this, "Please upload image.", Toast.LENGTH_SHORT).show();
                    return;
                }

                String p_name = txtProductName.getText().toString();
                String p_avlqty = txtAvlQty.getText().toString();
                String p_mrp = txtMRP.getText().toString();
                String p_sale = txtMRP.getText().toString();
                String p_desc = txtPDesc.getText().toString();

                if (TextUtils.isEmpty(p_name)) {
                    Toast.makeText(AddGroceryProductActivity.this, "Please enter product name.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(p_desc)) {
                    Toast.makeText(AddGroceryProductActivity.this, "Please enter product description.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!TextUtils.isDigitsOnly(p_avlqty) || TextUtils.isEmpty(p_avlqty)) {
                    Toast.makeText(AddGroceryProductActivity.this, "Please enter valid quantity.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!TextUtils.isDigitsOnly(p_mrp) || TextUtils.isEmpty(p_mrp)) {
                    Toast.makeText(AddGroceryProductActivity.this, "Please enter valid  MRP Price.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!TextUtils.isDigitsOnly(p_sale) || TextUtils.isEmpty(p_sale)) {
                    Toast.makeText(AddGroceryProductActivity.this, "Please enter valid SALE Price.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (selected_cat.equals("Select Product Category")) {
                    Toast.makeText(AddGroceryProductActivity.this, "Please select product category.", Toast.LENGTH_SHORT).show();
                    return;
                }



                addProduct(selected_cat, p_name, p_desc, imgurl1, p_mrp, p_sale, p_avlqty, unit);

            }
        });

    }

    private void addProduct(String selected_cat, String p_name, String p_desc, String imgurl1, String p_mrp, String p_sale, String p_avlqty, String unit){
        long tsLong = System.currentTimeMillis() / 1000;
        String ts = Long.toString(tsLong);

        StringRequest getRequest = new StringRequest(Request.Method.POST, AppConst.BASE_URL + "User_api/add_vendor_product",
                response -> {
                    Log.d("Response", response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String status = jsonObject.getString("status");
                        if (status.equals("200")) {
                            Toast.makeText(AddGroceryProductActivity.this, "Product Added!", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            startActivity(new Intent(getApplicationContext(), AddGroceryProductActivity.class));
                            btnAddProduct.setVisibility(View.VISIBLE);
                            finish();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                        btnAddProduct.setVisibility(View.VISIBLE);
                    }
                },
                error -> Log.d("Error.Response", error.toString())
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
               // params.put("user_id", Objects.requireNonNull(master.getString("user_id", "")));
                params.put("cat_id", selected_cat);
                params.put("p_name", p_name);
                params.put("p_desc", p_desc);
                params.put("p_img", imgurl1);
                params.put("mrp", p_mrp);
                params.put("sale_price", p_sale);
                params.put("p_qty", p_avlqty);
                params.put("unit", unit);
                return params;
            }
        };

        requestQueue.add(getRequest);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK)
        {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            Uri filePath = getImageUri(getApplicationContext(), bitmap);
            Log.e("vendor", "filepath==="+filePath );

                pImage.setImageBitmap(bitmap);
                i1 = true;
                if (filePath != null){
                    uploadImage1(filePath);
                }
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    private void uploadImage1(Uri filePath) {

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();


        if (filePath != null) {
            progressDialog.setMessage("Uploading...");
            progressDialog.show();

            final StorageReference ref = storageReference.child("images/" + UUID.randomUUID().toString());
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(AddGroceryProductActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    imgurl1 = uri.toString();
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(AddGroceryProductActivity.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

        }
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
                            spinnerCat.setAdapter(spinnerCategoryAdapter);
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
                //params.put("shop_id", Objects.requireNonNull(shopPref.getString("shop_id", "")));
                return params;
            }
        };

        requestQueue.add(getRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.vendor_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.vendor_menu_logout){
            SharedPreferences.Editor editor = master.edit();
            editor.clear();
            editor.apply();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


}
