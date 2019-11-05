package com.tetraval.mochashi.haatgrocerymodule.data.models;

import android.os.Parcel;
import android.os.Parcelable;

public class CartProductModel implements Parcelable {
    String product_id,product_prize,product_name,product_selected_qty,product_offer_prize,product_total_prize,cart_id,product_qty,product_discount,product_cat,product_image;

    public CartProductModel(String product_id, String product_prize, String product_name, String product_selected_qty, String product_offer_prize, String product_total_prize, String product_qty, String product_discount,String product_cat,String product_image) {
        this.product_id = product_id;
        this.product_prize = product_prize;
        this.product_name = product_name;
        this.product_selected_qty = product_selected_qty;
        this.product_offer_prize = product_offer_prize;
        this.product_total_prize = product_total_prize;
        this.product_qty = product_qty;
        this.product_discount = product_discount;
        this.product_cat = product_cat;
        this.product_image = product_image;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getProduct_prize() {
        return product_prize;
    }

    public void setProduct_prize(String product_prize) {
        this.product_prize = product_prize;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_selected_qty() {
        return product_selected_qty;
    }

    public void setProduct_selected_qty(String product_selected_qty) {
        this.product_selected_qty = product_selected_qty;
    }

    public String getProduct_offer_prize() {
        return product_offer_prize;
    }

    public void setProduct_offer_prize(String product_offer_prize) {
        this.product_offer_prize = product_offer_prize;
    }

    public String getProduct_total_prize() {
        return product_total_prize;
    }

    public void setProduct_total_prize(String product_total_prize) {
        this.product_total_prize = product_total_prize;
    }

    public String getCart_id() {
        return cart_id;
    }

    public void setCart_id(String cart_id) {
        this.cart_id = cart_id;
    }

    public String getProduct_qty() {
        return product_qty;
    }

    public void setProduct_qty(String product_qty) {
        this.product_qty = product_qty;
    }

    public String getProduct_discount() {
        return product_discount;
    }

    public void setProduct_discount(String product_discount) {
        this.product_discount = product_discount;
    }

    public String getProduct_cat() {
        return product_cat;
    }

    public void setProduct_cat(String product_cat) {
        this.product_cat = product_cat;
    }

    public String getProduct_image() {
        return product_image;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }



    public static Creator<CartProductModel> getCREATOR() {
        return CREATOR;
    }

    protected CartProductModel(Parcel in) {
        product_id = in.readString();
        product_prize = in.readString();
        product_name = in.readString();
        product_selected_qty = in.readString();
        product_offer_prize = in.readString();
        product_total_prize = in.readString();
        product_qty = in.readString();
        product_discount=in.readString();
        product_cat=in.readString();
        product_image=in.readString();
    }

    public static final Creator<CartProductModel> CREATOR = new Creator<CartProductModel>() {
        @Override
        public CartProductModel createFromParcel(Parcel in) {
            return new CartProductModel(in);
        }

        @Override
        public CartProductModel[] newArray(int size) {
            return new CartProductModel[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(product_id);
        dest.writeString(product_prize);
        dest.writeString(product_name);
        dest.writeString(product_selected_qty);
        dest.writeString(product_offer_prize);
        dest.writeString(product_total_prize);
        dest.writeString(product_qty);
        dest.writeString(product_discount);
        dest.writeString(product_cat);
        dest.writeString(product_image);
    }
}
