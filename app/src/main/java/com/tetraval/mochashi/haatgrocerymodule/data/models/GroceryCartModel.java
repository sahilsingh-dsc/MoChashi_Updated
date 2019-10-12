package com.tetraval.mochashi.haatgrocerymodule.data.models;

public class GroceryCartModel {

    String cart_id;
    String cart_amount;
    String cart_quantity;
    String uid;
    String product_id;
    String product_image;
    String product_name;
    String product_mrpprice;
    String product_saleprice;
    String product_saveamt;
    String product_cat;

    public GroceryCartModel(String cart_id, String cart_amount, String cart_quantity, String uid, String product_id, String product_image, String product_name, String product_mrpprice, String product_saleprice, String product_saveamt, String product_cat) {
        this.cart_id = cart_id;
        this.cart_amount = cart_amount;
        this.cart_quantity = cart_quantity;
        this.uid = uid;
        this.product_id = product_id;
        this.product_image = product_image;
        this.product_name = product_name;
        this.product_mrpprice = product_mrpprice;
        this.product_saleprice = product_saleprice;
        this.product_saveamt = product_saveamt;
        this.product_cat = product_cat;
    }

    public String getCart_id() {
        return cart_id;
    }

    public void setCart_id(String cart_id) {
        this.cart_id = cart_id;
    }

    public String getCart_amount() {
        return cart_amount;
    }

    public void setCart_amount(String cart_amount) {
        this.cart_amount = cart_amount;
    }

    public String getCart_quantity() {
        return cart_quantity;
    }

    public void setCart_quantity(String cart_quantity) {
        this.cart_quantity = cart_quantity;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getProduct_image() {
        return product_image;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_mrpprice() {
        return product_mrpprice;
    }

    public void setProduct_mrpprice(String product_mrpprice) {
        this.product_mrpprice = product_mrpprice;
    }

    public String getProduct_saleprice() {
        return product_saleprice;
    }

    public void setProduct_saleprice(String product_saleprice) {
        this.product_saleprice = product_saleprice;
    }

    public String getProduct_saveamt() {
        return product_saveamt;
    }

    public void setProduct_saveamt(String product_saveamt) {
        this.product_saveamt = product_saveamt;
    }

    public String getProduct_cat() {
        return product_cat;
    }

    public void setProduct_cat(String product_cat) {
        this.product_cat = product_cat;
    }
}
