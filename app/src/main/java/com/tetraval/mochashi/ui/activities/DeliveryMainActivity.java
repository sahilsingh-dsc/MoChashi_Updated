package com.tetraval.mochashi.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.tetraval.mochashi.R;
import com.tetraval.mochashi.ui.fragments.CustomerListFragment;
import com.tetraval.mochashi.ui.fragments.VendorListFragment;

public class DeliveryMainActivity extends AppCompatActivity {

    TabLayout tabDelivery;
    String tabstate = "vendor";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_main);

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
}
