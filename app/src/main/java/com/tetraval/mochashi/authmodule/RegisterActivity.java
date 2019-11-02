package com.tetraval.mochashi.authmodule;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.tetraval.mochashi.R;
import com.tetraval.mochashi.utils.AppConst;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import static android.graphics.Color.WHITE;

public class RegisterActivity extends AppCompatActivity {

    Toolbar toolbarSignup;
    Spinner spinnerAccountType,spinnerlastname;
    EditText txtFirsName, txtLastName, txtEmailId, txtMobileNumber, txtShopName, txtPincode, txtCity, txtState, txtPassword, txtRePassword;
    ImageView imgShopImage;
    TextView txtAddress, txtExistingAccount;
    Button btnRegister;

    String[] account_type = {"Customer", "Chashi Online", "Daily Haat Vendor", "Grocery Vendor"};
    String[] lastnametype = {"SIR","MADAM","BABU","DADA","DIDI","BHAI","MAUSA","MAUSI","OTHER"};
    String LastnameHolder;
    private static final String CUSTOMER = "Customer";
    private static final String CHASHI = "Chashi Online";
    private static final String HAAT = "Daily Haat Vendor";
    private static final String GROCERY = "Grocery Vendor";
    String imgurl;
    int account_state = 1;
    private static final int CAMERA_REQUEST = 188;
    private static final int MY_CAMERA_PERMISSION_CODE = 10;
    private final String[] permissions = new String[]{Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
    Manifest.permission.ACCESS_FINE_LOCATION,
    Manifest.permission.ACCESS_COARSE_LOCATION};

    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    int REQUEST_CODE_PLACEPICKER = 1;
    double lat=20.8444;
    double lng=85.1511;
    double lat1=20.8444;
    double lng1=85.1511;
    LatLng inbound =new LatLng(lat,lng);
    LatLng outbound =new LatLng(lat1,lng1);


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        toolbarSignup = findViewById(R.id.toolbarSignup);
        toolbarSignup.setTitle("User Registration");
        toolbarSignup.setTitleTextColor(WHITE);
        spinnerAccountType = findViewById(R.id.spinnerAccountType);
        spinnerlastname = findViewById(R.id.spinnerlastname);

        txtFirsName = findViewById(R.id.txtFirsName);
        txtLastName = findViewById(R.id.txtLastName);
        txtEmailId = findViewById(R.id.txtEmailId);
        txtMobileNumber = findViewById(R.id.txtMobileNumber);
        txtShopName = findViewById(R.id.txtShopName);
        txtPincode = findViewById(R.id.txtPincode);
        txtCity = findViewById(R.id.txtCity);
        txtState = findViewById(R.id.txtState);
        txtPassword = findViewById(R.id.txtPassword);
        txtRePassword = findViewById(R.id.txtRePassword);
        imgShopImage = findViewById(R.id.imgShopImage);
        txtAddress = findViewById(R.id.txtAddress);
        txtExistingAccount = findViewById(R.id.txtExistingAccount);
        txtExistingAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                finish();
            }
        });
        btnRegister = findViewById(R.id.btnRegister);

        requestQueue = Volley.newRequestQueue(this);

        if (ActivityCompat.checkSelfPermission(RegisterActivity.this, permissions[0]) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(RegisterActivity.this, permissions[1]) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(RegisterActivity.this, permissions[2]) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(RegisterActivity.this, permissions, MY_CAMERA_PERMISSION_CODE);
        }

        progressDialog = new ProgressDialog(this);

        ArrayAdapter accountTypeAdapter = new ArrayAdapter<>(RegisterActivity.this, R.layout.support_simple_spinner_dropdown_item, account_type);
        spinnerAccountType.setAdapter(accountTypeAdapter);
        spinnerAccountType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (adapterView.getItemAtPosition(i).toString().equals(CUSTOMER)) {
                    account_state = 1;
                    customerRegistrationFields();
                } else if (adapterView.getItemAtPosition(i).toString().equals(CHASHI)) {
                    account_state = 4;
                    merchantRegistrationFields();
                } else if (adapterView.getItemAtPosition(i).toString().equals(HAAT)) {
                    account_state = 3;
                    merchantRegistrationFields();
                } else if (adapterView.getItemAtPosition(i).toString().equals(GROCERY)) {
                    account_state = 2;
                    merchantRegistrationFields();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        ArrayAdapter lastnameadapter = new ArrayAdapter<>(RegisterActivity.this, R.layout.support_simple_spinner_dropdown_item, lastnametype);
        spinnerlastname.setAdapter(lastnameadapter);
        spinnerlastname.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (adapterView.getItemAtPosition(i).toString().equals("OTHER")) {
                    txtLastName.setVisibility(View.VISIBLE);
                }else{
                    txtLastName.setVisibility(View.GONE);
                    LastnameHolder=adapterView.getItemAtPosition(i).toString();
                    Log.e("register", "Lastname=="+LastnameHolder );
                }



            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        imgShopImage.setOnClickListener(view -> {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
            } else {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });

        txtAddress.setOnClickListener(view -> startPlacePickerActivity());
        btnRegister.setOnClickListener(view -> {

            String first_name = txtFirsName.getText().toString();
            LastnameHolder = txtLastName.getText().toString();
            String email_id = txtEmailId.getText().toString();
            String mobile_number = txtMobileNumber.getText().toString();
            String shop_name = txtShopName.getText().toString();
            String pincode = txtPincode.getText().toString();
            String city = txtCity.getText().toString();
            String state = txtState.getText().toString();
            String pass = txtPassword.getText().toString();
            String re_pass = txtRePassword.getText().toString();
            String vendor_image = imgurl;
            String address = txtAddress.getText().toString();

            if (account_state == 1) {

                if (TextUtils.isEmpty(first_name)) {
                    txtFirsName.setError("Please Enter First Name");
                    return;
                }
              /*  if (TextUtils.isEmpty(last_name)) {
                    txtLastName.setError("Please Enter Last Name");
                    return;
                }*/
                if (TextUtils.isEmpty(email_id)) {
                    txtEmailId.setError("Please Enter Email ID");
                    return;
                }
                if (TextUtils.isEmpty(mobile_number)) {
                    txtMobileNumber.setError("Please Enter Mobile Number");
                    return;
                }
                if (TextUtils.isEmpty(pass)) {
                    txtPassword.setError("Please Enter Password");
                    return;
                }
                if (TextUtils.isEmpty(re_pass)) {
                    txtRePassword.setError("Please Enter RePassword");
                    return;
                }
                if (!pass.equals(re_pass)) {
                    txtPassword.setError("Password not match");
                    txtRePassword.setError("Password not match");
                    return;
                }
                if (TextUtils.isEmpty(address)) {
                    txtAddress.setError("Please select address");
                    return;
                }

                registerUser(account_state,
                        first_name,
                        LastnameHolder,
                        email_id,
                        mobile_number,
                        shop_name,
                        pincode,
                        city,
                        state,
                        pass,
                        "imurl.jpg",
                        address);

            } else {

                if (TextUtils.isEmpty(first_name)) {
                    txtFirsName.setError("Please Enter First Name");
                    return;
                }
              /*  if (TextUtils.isEmpty(last_name)) {
                    txtLastName.setError("Please Enter Last Name");
                    return;
                }*/
                if (TextUtils.isEmpty(email_id)) {
                    txtEmailId.setError("Please Enter Email ID");
                    return;
                }
                if (TextUtils.isEmpty(mobile_number)) {
                    txtMobileNumber.setError("Please Enter Mobile Number");
                    return;
                }
                if (TextUtils.isEmpty(shop_name)) {
                    txtShopName.setError("Please Enter Shop Name");
                    return;
                }
                if (TextUtils.isEmpty(pincode)) {
                    txtPincode.setError("Please Enter Pincode");
                    return;
                }
                if (TextUtils.isEmpty(city)) {
                    txtCity.setError("Please Enter City");
                    return;
                }
                if (TextUtils.isEmpty(state)) {
                    txtState.setError("Please Enter State");
                    return;
                }
                if (TextUtils.isEmpty(pass)) {
                    txtPassword.setError("Please Enter Password");
                    return;
                }
                if (TextUtils.isEmpty(re_pass)) {
                    txtRePassword.setError("Please Enter RePassword");
                    return;
                }
                if (!pass.equals(re_pass)) {
                    txtPassword.setError("Password not match");
                    txtRePassword.setError("Password not match");
                    return;
                }
                if (TextUtils.isEmpty(address)) {
                    txtAddress.setError("Please select address");
                    return;
                }

                registerUser(account_state,
                        first_name,
                        LastnameHolder,
                        email_id,
                        mobile_number,
                        shop_name,
                        pincode,
                        city,
                        state,
                        pass,
                        vendor_image,
                        address);

            }

        });

    }

    private void registerUser(int account_state, String first_name, String last_name, String email_id, String mobile_number, String shop_name, String pincode, String city, String state, String pass, String vendor_image, String address) {

        StringRequest getRequest = new StringRequest(Request.Method.POST, AppConst.BASE_URL + "User_api/user_signup",
                response -> {
                    Log.d("Response", response);
                    try {

                        JSONObject jsonObject = new JSONObject(response);
                        String status = jsonObject.getString("status");
                        if (status.equals("200")) {
                            Toast.makeText(this, "Registration Succesfull.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                            finish();
                        } else if (status.equals("404")) {
                            Toast.makeText(this, "Email already Exists.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
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
                params.put("user_type", account_state+"");
                params.put("name", first_name+" "+last_name);
                params.put("email", email_id);
                params.put("mobile", mobile_number);
                params.put("shop_name", shop_name);
                params.put("address", address);
                params.put("state", "21");
                params.put("city", "2229");
                params.put("pincode", pincode);
                params.put("password", pass);
                params.put("img", vendor_image);
                params.put("device_key", "somekey");
                params.put("device_type", "android");
                return params;
            }
        };

        requestQueue.add(getRequest);

    }

    private void merchantRegistrationFields() {
        txtShopName.setVisibility(View.VISIBLE);
        imgShopImage.setVisibility(View.VISIBLE);
        txtPincode.setVisibility(View.VISIBLE);
        txtCity.setVisibility(View.VISIBLE);
        txtState.setVisibility(View.VISIBLE);
    }

    private void customerRegistrationFields() {
        txtShopName.setVisibility(View.GONE);
        imgShopImage.setVisibility(View.GONE);
        txtPincode.setVisibility(View.GONE);
        txtCity.setVisibility(View.GONE);
        txtState.setVisibility(View.GONE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap bitmap = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");
            imgShopImage.setImageBitmap(bitmap);
            assert bitmap != null;
            Uri filePath = getImageUri(getApplicationContext(), bitmap);
            if (filePath != null) {
                uploadImage(filePath);
            }

        } else if (requestCode == REQUEST_CODE_PLACEPICKER && resultCode == Activity.RESULT_OK){
            displaySelectedPlaceFromPlacePicker(data);
       }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }


    private void uploadImage(Uri filePath) {

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference();

        if (filePath != null) {
            progressDialog.setMessage("Uploading...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            final StorageReference ref = storageReference.child("images/" + UUID.randomUUID().toString());
            ref.putFile(filePath)
                    .addOnSuccessListener(taskSnapshot -> {
                        progressDialog.dismiss();
                        ref.getDownloadUrl().addOnSuccessListener(uri -> imgurl = uri.toString());
                    })
                    .addOnFailureListener(e -> progressDialog.dismiss());
        }
    }

    private void startPlacePickerActivity() {

        try {
            LatLngBounds bounds = new LatLngBounds(inbound ,outbound);
            PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
            builder.setLatLngBounds(bounds);

            try {
                startActivityForResult(builder.build(this), REQUEST_CODE_PLACEPICKER);
            } catch (GooglePlayServicesRepairableException e) {
                e.printStackTrace();
            }
        }catch (GooglePlayServicesNotAvailableException e) {
                e.printStackTrace();
            }

    }

    private void displaySelectedPlaceFromPlacePicker(Intent data) {
        Place placeSelected = null;
       // if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            placeSelected = PlacePicker.getPlace(data, this);
            placeSelected.getAddress();
            //String place = Objects.requireNonNull(placeSelected.getAddress()).toString();
            //List<String> items = Arrays.asList(place.split("\\s*,\\s*"));

      //  }

        assert placeSelected != null;
        txtAddress.setText(Objects.requireNonNull(placeSelected.getAddress()).toString());

    }


}
