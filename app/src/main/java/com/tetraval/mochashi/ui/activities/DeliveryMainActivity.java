package com.tetraval.mochashi.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.tetraval.mochashi.R;
import com.tetraval.mochashi.authmodule.LoginActivity;
import com.tetraval.mochashi.ui.fragments.CustomerListFragment;
import com.tetraval.mochashi.ui.fragments.VendorListFragment;

public class DeliveryMainActivity extends AppCompatActivity {

    TabLayout tabDelivery;
    String tabstate = "vendor";
    Toolbar toolbar;
    SharedPreferences master;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_main);

        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Delivery Channel");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.getOverflowIcon().setColorFilter(Color.WHITE , PorterDuff.Mode.SRC_ATOP);

        master = getApplicationContext().getSharedPreferences("MASTER", 0);

        FragmentManager fm = getSupportFragmentManager();
        VendorListFragment vendorListFragment = new VendorListFragment();
        fm.beginTransaction().add(R.id.frameDelivery,vendorListFragment).commit();

        tabDelivery = findViewById(R.id.tabDelivery);
        tabDelivery.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Toast.makeText(DeliveryMainActivity.this, ""+tab.getText().toString(), Toast.LENGTH_SHORT).show();
                if (tab.getText().toString().equals("Vendor List")){
                    FragmentManager fm = getSupportFragmentManager();
                    VendorListFragment vendorListFragment = new VendorListFragment();
                    fm.beginTransaction().replace(R.id.frameDelivery,vendorListFragment).commit();
                }else if (tab.getText().toString().equals("Customer List")){
                    FragmentManager fm = getSupportFragmentManager();
                    CustomerListFragment customerListFragment = new CustomerListFragment();
                    fm.beginTransaction().replace(R.id.frameDelivery,customerListFragment).commit();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
//                Toast.makeText(DeliveryMainActivity.this, ""+tab.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });

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
