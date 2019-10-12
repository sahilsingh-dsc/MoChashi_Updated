package com.tetraval.mochashi.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

public class Master {

    private static SharedPreferences mSharedPreferences;
    static String TAG="MASTER";

    public static boolean setUserInfo(
            String user_id,
            String user_type,
            String fname,
            String email,
            String mobile,
            String shop_name,
            String img,
            String address,
            String country,
            String state,
            String city,
            String pincode,
            String password,
            String active, Context mContext) {

        mSharedPreferences = mContext.getSharedPreferences(TAG, 0);
        SharedPreferences.Editor editor = mSharedPreferences.edit();

        editor.putString("user_id", user_id);
        editor.apply();

        editor = mSharedPreferences.edit();
        editor.putString("user_type", user_type);
        editor.apply();

        editor = mSharedPreferences.edit();
        editor.putString("fname", fname);
        editor.apply();

        editor = mSharedPreferences.edit();
        editor.putString("email", email);
        editor.apply();

        editor = mSharedPreferences.edit();
        editor.putString("mobile", mobile);
        editor.apply();

        editor = mSharedPreferences.edit();
        editor.putString("shop_name", shop_name);
        editor.apply();

        editor = mSharedPreferences.edit();
        editor.putString("img", img);
        editor.apply();

        editor = mSharedPreferences.edit();
        editor.putString("address", address);
        editor.apply();

        editor = mSharedPreferences.edit();
        editor.putString("country", country);
        editor.apply();

        editor = mSharedPreferences.edit();
        editor.putString("state", state);
        editor.apply();

        editor = mSharedPreferences.edit();
        editor.putString("city", city);
        editor.apply();

        editor = mSharedPreferences.edit();
        editor.putString("pincode", pincode);
        editor.apply();

        editor = mSharedPreferences.edit();
        editor.putString("password", password);
        editor.apply();

        editor = mSharedPreferences.edit();
        editor.putString("active", active);
        editor.apply();

        return true;
    }


}
