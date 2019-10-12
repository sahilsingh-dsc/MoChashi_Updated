package com.tetraval.mochashi.ui.activities;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.tetraval.mochashi.R;
import com.tetraval.mochashi.chashimodule.ui.activities.VendorActivity;
import com.tetraval.mochashi.haatgrocerymodule.data.adapters.SpinnerCategoryAdapter;
import com.tetraval.mochashi.haatgrocerymodule.data.models.SpinnerCategoryModel;
import com.tetraval.mochashi.utils.AppConst;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class AddVendorProductActivity extends AppCompatActivity {

    Toolbar toolbarAddPdt;
    Button btnAddProduct;
    private ImageView img1, img2, img3, img4;
    String imgstate = "null";
    EditText txtProductName, txtAvlQty, txtRate;
    Switch switchDelivery;
    RequestQueue requestQueue;
    Spinner spinnerCat;
    SharedPreferences master, preferences;
    Boolean i1 = false, i2 = false, i3 = false, i4 = false;
    String selected_cat, delivery_state = "nodelivery";
    String icatid;
    ProgressDialog progressDialog;
    FirebaseStorage storage;
    StorageReference storageReference;
    FirebaseAuth mAuth;
    Spinner spinner2;
    String unit;
    SpinnerCategoryAdapter spinnerCategoryAdapter;
    ArrayList<SpinnerCategoryModel> spinnerCategoryModelArrayList;
    String imgurl1, imgurl2, imgurl3, imgurl4;
    private static final int CAMERA_REQUEST = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vendor_product);

        toolbarAddPdt = findViewById(R.id.toolbarAddPdt);
        toolbarAddPdt.setTitle("Add Product");
        toolbarAddPdt.setTitleTextColor(Color.WHITE);

        requestQueue = Volley.newRequestQueue(this);
        master = getApplicationContext().getSharedPreferences("MASTER", 0);
        preferences = getApplicationContext().getSharedPreferences("loginpref", 0);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
        } else {
            signInAnonymously();
        }

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");


        img1 = findViewById(R.id.imageView7);
        img1.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                i1 = false;
                imgstate = "img1";
                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                } else {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                }


            }
        });
        img2 = findViewById(R.id.imageView8);
        img2.setOnClickListener(view -> {
            i2 = false;
            imgstate = "img2";
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
            } else {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }


        });
        img3 = findViewById(R.id.imageView9);
        img3.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                i3 = false;
                imgstate = "img3";
                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                } else {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                }
            }

        });

        img4 = findViewById(R.id.imageView10);
        img4.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                i4 = false;
                imgstate = "img4";
                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                } else {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                }
            }
        });

        txtProductName = findViewById(R.id.txtProductName);
        txtAvlQty = findViewById(R.id.txtAvlQty);
        txtRate = findViewById(R.id.txtRate);
        switchDelivery = findViewById(R.id.switchDelivery);

        switchDelivery.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    delivery_state = "1";
                } else {
                    delivery_state = "2";
                }
            }
        });

        spinnerCategoryModelArrayList = new ArrayList<>();

        spinnerCat = findViewById(R.id.spinnerCat);

        String[] units = {"Kg", "L", "M"};
        ArrayAdapter arrayAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, units);
        spinner2 = findViewById(R.id.spinner2);
        spinner2.setAdapter(arrayAdapter);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                unit = (String) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnAddProduct = findViewById(R.id.btnAddProduct);
        btnAddProduct.setOnClickListener(view -> {

            if (!i1) {
                Toast.makeText(AddVendorProductActivity.this, "Please upload 1st image.", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!i2) {
                Toast.makeText(AddVendorProductActivity.this, "Please upload 2nd image.", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!i3) {
                Toast.makeText(AddVendorProductActivity.this, "Please upload 3rd image.", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!i4) {
                Toast.makeText(AddVendorProductActivity.this, "Please upload 4th image.", Toast.LENGTH_SHORT).show();
                return;
            }

            String p_name = txtProductName.getText().toString();
            String p_avlqty = txtAvlQty.getText().toString();
            String p_rate = txtRate.getText().toString();

            if (TextUtils.isEmpty(p_name)) {
                Toast.makeText(AddVendorProductActivity.this, "Please enter product name.", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!TextUtils.isDigitsOnly(p_avlqty) || TextUtils.isEmpty(p_avlqty)) {
                Toast.makeText(AddVendorProductActivity.this, "Please enter valid quantity.", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!TextUtils.isDigitsOnly(p_rate) || TextUtils.isEmpty(p_rate)) {
                Toast.makeText(AddVendorProductActivity.this, "Please enter valid rate.", Toast.LENGTH_SHORT).show();
                return;
            }
            if (selected_cat.equals("Select Product Category")) {
                Toast.makeText(AddVendorProductActivity.this, "Please select product category.", Toast.LENGTH_SHORT).show();
                return;
            }
            if (delivery_state == null) {
                return;
            }

            btnAddProduct.setVisibility(View.INVISIBLE);
            addProduct(p_name, p_avlqty, p_rate, selected_cat, delivery_state);

        });

        spinnerCat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                TextView catid = adapterView.findViewById(R.id.catid);
                TextView txtSpinnerItem = adapterView.findViewById(R.id.txtSpinnerItem);
                selected_cat = catid.getText().toString();
                Toast.makeText(AddVendorProductActivity.this, ""+catid.getText().toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        progressDialog.show();
        fetchChashiProduct();

    }

    private void addProduct(String p_name, String p_avlqty, String p_rate, String selected_cat, String delivery_state) {

        long tsLong = System.currentTimeMillis() / 1000;
        String ts = Long.toString(tsLong);

        StringRequest getRequest = new StringRequest(Request.Method.POST, AppConst.BASE_URL + "User_api/add_product",
                response -> {
                    Log.d("Response", response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String status = jsonObject.getString("status");
                        if (status.equals("200")) {
                            Toast.makeText(AddVendorProductActivity.this, "Product Added!", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            startActivity(new Intent(getApplicationContext(), VendorActivity.class));
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
                params.put("user_id", Objects.requireNonNull(master.getString("user_id", "")));
                params.put("category_id", selected_cat);
                params.put("p_name", p_name);
                params.put("p_img1", imgurl1);
                params.put("p_img2", imgurl2);
                params.put("p_img3", imgurl3);
                params.put("p_img4", imgurl4);
                params.put("qty_hosted", p_avlqty);
                params.put("unit", unit);
                params.put("rate", p_rate);
                params.put("deliver", delivery_state);
                return params;
            }
        };

        requestQueue.add(getRequest);


}

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK)
        {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            Uri filePath = getImageUri(getApplicationContext(), bitmap);
            Log.e("vendor", "filepath==="+filePath );
                if (imgstate.equals("img1")) {
                    img1.setImageBitmap(bitmap);
                    i1 = true;
                    if (filePath != null){
                        uploadImage1(filePath);
                    }
                }
                if (imgstate.equals("img2")) {
                    img2.setImageBitmap(bitmap);
                    i2 = true;
                    uploadImage2(filePath);
                }
                if (imgstate.equals("img3")) {
                    img3.setImageBitmap(bitmap);
                    i3 = true;
                    uploadImage3(filePath);
                }
                if (imgstate.equals("img4")) {
                    img4.setImageBitmap(bitmap);
                    i4 = true;
                    uploadImage4(filePath);
                }


        }
    }
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }



    private void fetchChashiProduct() {
        StringRequest getRequest = new StringRequest(Request.Method.POST, AppConst.BASE_URL + "User_api/chashi_category",
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
                            progressDialog.dismiss();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.toString());
                        progressDialog.dismiss();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }
        };
        requestQueue.add(getRequest);
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
                            Toast.makeText(AddVendorProductActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(AddVendorProductActivity.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

        }
    }


    private void uploadImage2(Uri filePath) {

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
                            Toast.makeText(AddVendorProductActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    imgurl2 = uri.toString();
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(AddVendorProductActivity.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

        }
    }

    private void uploadImage3(Uri filePath) {

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
                            Toast.makeText(AddVendorProductActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    imgurl3 = uri.toString();
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(AddVendorProductActivity.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

        }
    }

    private void uploadImage4(Uri filePath) {

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
                            Toast.makeText(AddVendorProductActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    imgurl4 = uri.toString();
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(AddVendorProductActivity.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }


    private void signInAnonymously() {
        mAuth.signInAnonymously().addOnSuccessListener(this, new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                // do your stuff
            }
        })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {

                    }
                });
    }


}