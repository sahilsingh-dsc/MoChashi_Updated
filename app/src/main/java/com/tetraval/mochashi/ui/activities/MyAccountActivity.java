package com.tetraval.mochashi.ui.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.tetraval.mochashi.R;
import com.tetraval.mochashi.authmodule.LoginActivity;
import com.tetraval.mochashi.chashimodule.ui.activities.ChasiMyOrdersActivity;
import com.tetraval.mochashi.controller.StartActivity;

public class MyAccountActivity extends AppCompatActivity {

    SharedPreferences preferences, masterdata;
    Toolbar toolbarMyAccount;
    ImageView profilepic;
    TextView name,email,phone,address;
    String Nameholder,Addressholder,Emailholder,Phoneholder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        toolbarMyAccount = findViewById(R.id.toolbarMyAccount);
        setSupportActionBar(toolbarMyAccount);
        getSupportActionBar().setTitle("My Account");
        toolbarMyAccount.setTitleTextColor(Color.WHITE);
        toolbarMyAccount.getOverflowIcon().setColorFilter(Color.WHITE , PorterDuff.Mode.SRC_ATOP);

        preferences = getApplicationContext().getSharedPreferences("loginpref", 0);
        masterdata = getApplicationContext().getSharedPreferences("MASTER", 0);
        Nameholder=masterdata.getString("fname","0");
        Phoneholder=masterdata.getString("mobile","0");
        Emailholder=masterdata.getString("email","0");
        Addressholder=masterdata.getString("address","0");
        profilepic=findViewById(R.id.profilepic);
        Glide.with(getApplicationContext()).load(masterdata.getString("img","0")).placeholder(R.drawable.productimage).into(profilepic);
        name=findViewById(R.id.name);
        name.setText(Nameholder);
        email=findViewById(R.id.email);
        email.setText(Emailholder);
        phone=findViewById(R.id.phone);
        phone.setText(Phoneholder);
        address=findViewById(R.id.address);
        address.setText(Addressholder);

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
