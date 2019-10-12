package com.tetraval.mochashi.haatgrocerymodule.data.models;

public class GroceryProductModel {

    String product_id;
    String product_name;
    String product_desc;
    String product_image;
    String product_mrpprice;
    String product_saleprice;
    String product_weight;
    String product_unit;
    String product_stock;
    String product_cat;

    public GroceryProductModel(String product_id, String product_name, String product_desc, String product_image, String product_mrpprice, String product_saleprice, String product_weight, String product_unit, String product_stock, String product_cat) {
        this.product_id = product_id;
        this.product_name = product_name;
        this.product_desc = product_desc;
        this.product_image = product_image;
        this.product_mrpprice = product_mrpprice;
        this.product_saleprice = product_saleprice;
        this.product_weight = product_weight;
        this.product_unit = product_unit;
        this.product_stock = product_stock;
        this.product_cat = product_cat;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_desc() {
        return product_desc;
    }

    public void setProduct_desc(String product_desc) {
        this.product_desc = product_desc;
    }

    public String getProduct_image() {
        return product_image;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
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

    public String getProduct_weight() {
        return product_weight;
    }

    public void setProduct_weight(String product_weight) {
        this.product_weight = product_weight;
    }

    public String getProduct_unit() {
        return product_unit;
    }

    public void setProduct_unit(String product_unit) {
        this.product_unit = product_unit;
    }

    public String getProduct_stock() {
        return product_stock;
    }

    public void setProduct_stock(String product_stock) {
        this.product_stock = product_stock;
    }

    public String getProduct_cat() {
        return product_cat;
    }

    public void setProduct_cat(String product_cat) {
        this.product_cat = product_cat;
    }
}
