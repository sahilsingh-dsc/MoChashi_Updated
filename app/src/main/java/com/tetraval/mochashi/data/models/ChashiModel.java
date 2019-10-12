package com.tetraval.mochashi.data.models;

public class ChashiModel {

    String product_id;
    String vendor_id;
    String vendor_name;
    String product_name;
    String vendor_img;
    String qty_avl;
    String unit;
    String rate;
    String is_sold;

    public ChashiModel(String product_id, String vendor_id, String vendor_name, String product_name, String vendor_img, String qty_avl, String unit, String rate, String is_sold) {
        this.product_id = product_id;
        this.vendor_id = vendor_id;
        this.vendor_name = vendor_name;
        this.product_name = product_name;
        this.vendor_img = vendor_img;
        this.qty_avl = qty_avl;
        this.unit = unit;
        this.rate = rate;
        this.is_sold = is_sold;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getVendor_id() {
        return vendor_id;
    }

    public void setVendor_id(String vendor_id) {
        this.vendor_id = vendor_id;
    }

    public String getVendor_name() {
        return vendor_name;
    }

    public void setVendor_name(String vendor_name) {
        this.vendor_name = vendor_name;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getVendor_img() {
        return vendor_img;
    }

    public void setVendor_img(String vendor_img) {
        this.vendor_img = vendor_img;
    }

    public String getQty_avl() {
        return qty_avl;
    }

    public void setQty_avl(String qty_avl) {
        this.qty_avl = qty_avl;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getIs_sold() {
        return is_sold;
    }

    public void setIs_sold(String is_sold) {
        this.is_sold = is_sold;
    }
}
