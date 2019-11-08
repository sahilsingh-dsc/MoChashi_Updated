package com.tetraval.mochashi.ui.activities;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import androidx.annotation.DrawableRes;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;
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

public class MapsActivity extends FragmentActivity /*implements OnMapReadyCallback */{

    public GoogleMap mMap;
    RequestQueue requestQueue;
    String mo_id;
    double lat=20.8444;
    double lng=85.1511;
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
      //  Objects.requireNonNull(mapFragment).getMapAsync(this);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap=googleMap;
                StringRequest request = new StringRequest(Request.Method.POST,AppConst.BASE_URL+"User_api/fetch_lat_long", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            String status = jsonObj.getString("status");
                            String result = jsonObj.getString("result");
                            //if (status.equals("200")) {
                            JSONObject jsonObject1 = new JSONObject(result);
                            String addre = jsonObject1.getString("address");
                            JSONArray jsonArray = new JSONArray(addre);

                            for (int i=0; i < jsonArray.length(); i++){

                                JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                                String address = jsonObject2.getString("address");
                                String latitude = jsonObject2.getString("lat");
                                String longitude = jsonObject2.getString("long");
                                lat = Double.parseDouble(latitude);
                                lng = Double.parseDouble(longitude);
                                LatLng latLng = new LatLng(lat, lng);
                                String imageurl=jsonObject2.getString("img");
                                Bitmap bitmap=getMarkerBitmapFromView(imageurl);
                                mMap.addMarker(new MarkerOptions()
                                        .position(latLng)
                                        .title("Vendors")
                                        .snippet(address)
                                        .icon(BitmapDescriptorFactory.fromBitmap(bitmap)));
                                CameraPosition Liberty= CameraPosition.builder().target(latLng).zoom(20).bearing(0).tilt(45).build();
                                mMap.moveCamera(CameraUpdateFactory.newCameraPosition(Liberty));


                            }
                        } catch (Exception e) {
                            e.printStackTrace();

                        }
                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams()
                    {
                        Map<String, String>  params = new HashMap<String, String>();
                        params.put("cat_id",mo_id);
                        return params;
                    }
                };

                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                queue.add(request);
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                CameraPosition Liberty= CameraPosition.builder().target(new LatLng(lat,lng)).zoom(20).bearing(0).tilt(45).build();
                mMap.moveCamera(CameraUpdateFactory.newCameraPosition(Liberty));
                mMap.getUiSettings().setZoomControlsEnabled(true);
            }

        });
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
   /* @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        StringRequest getRequest = new StringRequest(Request.Method.POST, AppConst.BASE_URL+"User_api/fetch_lat_long",
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String status = jsonObject.getString("status");
                            String result = jsonObject.getString("result");
                            //if (status.equals("200")) {
                                JSONObject jsonObject1 = new JSONObject(result);
                                String addre = jsonObject1.getString("address");
                            JSONArray jsonArray = new JSONArray(addre);

                            for (int i=0; i < jsonArray.length(); i++){

                                JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                                String address = jsonObject2.getString("address");
                                String latitude = jsonObject2.getString("lat");
                                String longitude = jsonObject2.getString("long");
                                Toast.makeText(MapsActivity.this, ""+latitude, Toast.LENGTH_SHORT).show();
                                Toast.makeText(MapsActivity.this, ""+longitude, Toast.LENGTH_SHORT).show();

                                 lat = Double.parseDouble(latitude);
                                 lng = Double.parseDouble(longitude);
                                LatLng latLng = new LatLng(lat, lng);
                                String imageurl=jsonObject2.getString("img");
                                Bitmap bitmap=getMarkerBitmapFromView(imageurl);
                                mMap.addMarker(new MarkerOptions()
                                        .position(latLng)
                                        .title("Vendors")
                                        .snippet(address)
                                        .icon(BitmapDescriptorFactory.fromBitmap(bitmap)));
                                CameraPosition Liberty= CameraPosition.builder().target(latLng).zoom(9).bearing(0).tilt(45).build();
                                mMap.moveCamera(CameraUpdateFactory.newCameraPosition(Liberty));


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
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        CameraPosition Liberty= CameraPosition.builder().target(new LatLng(lat,lng)).zoom(16).bearing(0).tilt(45).build();
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(Liberty));
*/




      /*  LatLng mo1 = new LatLng(22.7067496,75.8512642);
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
        mMap.moveCamera(CameraUpdateFactory.newLatLng(mo5));*/
 //   }



    private Bitmap getMarkerBitmapFromView(String url) {

        View customMarkerView = ((LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.view_custom_marker, null);
        ImageView markerImageView = (ImageView) customMarkerView.findViewById(R.id.profile_image);
        //Picasso.get().load(url).noPlaceholder().into(markerImageView);
        Glide.with(getApplicationContext()).load(url).placeholder(R.drawable.ic_person_black_24dp).into(markerImageView);
        /*   markerImageView.setImageResource(resId);*/
        customMarkerView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        customMarkerView.layout(0, 0, customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight());
        customMarkerView.buildDrawingCache();
        Bitmap returnedBitmap = Bitmap.createBitmap(customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        canvas.drawColor(Color.WHITE, PorterDuff.Mode.SRC_IN);
        Drawable drawable = customMarkerView.getBackground();
        if (drawable != null)
            drawable.draw(canvas);
        customMarkerView.draw(canvas);
        return returnedBitmap;
    }


}
