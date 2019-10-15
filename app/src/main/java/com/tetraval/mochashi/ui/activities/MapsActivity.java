package com.tetraval.mochashi.ui.activities;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import androidx.annotation.DrawableRes;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.snackbar.Snackbar;
import com.tetraval.mochashi.R;
import com.tetraval.mochashi.data.adapters.ChashiAdapter;
import com.tetraval.mochashi.data.models.ChashiModel;
import com.tetraval.mochashi.utils.AppConst;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.tetraval.mochashi.ui.activities.CustomerMapsActivity.createCustomMarker;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    RequestQueue requestQueue;
    String mo_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Bundle momapBundel = getIntent().getExtras();
         mo_id = momapBundel.getString("id");

        requestQueue = Volley.newRequestQueue(this);

    //    fetchProducts(id);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        Objects.requireNonNull(mapFragment).getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        StringRequest getRequest = new StringRequest(Request.Method.GET, AppConst.BASE_URL+"productlist/?chashi_cate="+mo_id,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response", response);
                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            for (int i=0; i < jsonArray.length(); i++){

                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String id = jsonObject.getString("id");
                                String product_image = jsonObject.getString("product_image");
                                String name = jsonObject.getString("name");

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
                return params;
            }
        };

        requestQueue.add(getRequest);





        LatLng mo1 = new LatLng(22.7067496,75.8512642);
        LatLng mo2 = new LatLng(22.713954, 75.863882);
        LatLng mo3 = new LatLng(22.702949, 75.864525);
        LatLng mo4 = new LatLng(22.720849, 75.870835);
        LatLng mo5 = new LatLng(22.701621, 75.933110);
        mMap.addMarker(new MarkerOptions().position(mo1).
                icon(BitmapDescriptorFactory.fromBitmap(
                        createCustomMarker(MapsActivity.this,"50")))).setTitle("Sample Chashi Name");
        mMap.moveCamera(CameraUpdateFactory.newLatLng(mo2));
        mMap.addMarker(new MarkerOptions().position(mo1).
                icon(BitmapDescriptorFactory.fromBitmap(
                        createCustomMarker(MapsActivity.this,"90")))).setTitle("Sample Chashi Name");
        mMap.moveCamera(CameraUpdateFactory.newLatLng(mo2));
        mMap.addMarker(new MarkerOptions().position(mo3).
                icon(BitmapDescriptorFactory.fromBitmap(
                        createCustomMarker(MapsActivity.this,"60")))).setTitle("Sample Chashi Name");
        mMap.moveCamera(CameraUpdateFactory.newLatLng(mo3));
        mMap.addMarker(new MarkerOptions().position(mo4).
                icon(BitmapDescriptorFactory.fromBitmap(
                        createCustomMarker(MapsActivity.this,"45")))).setTitle("Sample Chashi Name");
        mMap.moveCamera(CameraUpdateFactory.newLatLng(mo4));
        mMap.addMarker(new MarkerOptions().position(mo5).
                icon(BitmapDescriptorFactory.fromBitmap(
                        createCustomMarker(MapsActivity.this,"10")))).setTitle("Sample Chashi Name");
        mMap.moveCamera(CameraUpdateFactory.newLatLng(mo5));
    }



    private void fetchProducts(String mo_id){


    }


}
