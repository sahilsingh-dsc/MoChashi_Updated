package com.tetraval.mochashi.data.models;

public class VendorProductModel {

    String product_id;
    String category_id;
    String product_name;
    String p_img1;
    String qty_hosted;
    String qty_booked;
    String qty_avl;
    String unit;
    String rate;
    String is_deliver;

    public VendorProductModel(String product_id, String category_id, String product_name, String p_img1, String qty_hosted, String qty_booked, String qty_avl, String unit, String rate, String is_deliver) {
        this.product_id = product_id;
        this.category_id = category_id;
        this.product_name = product_name;
        this.p_img1 = p_img1;
        this.qty_hosted = qty_hosted;
        this.qty_booked = qty_booked;
        this.qty_avl = qty_avl;
        this.unit = unit;
        this.rate = rate;
        this.is_deliver = is_deliver;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getP_img1() {
        return p_img1;
    }

    public void setP_img1(String p_img1) {
        this.p_img1 = p_img1;
    }

    public String getQty_hosted() {
        return qty_hosted;
    }

    public void setQty_hosted(String qty_hosted) {
        this.qty_hosted = qty_hosted;
    }

    public String getQty_booked() {
        return qty_booked;
    }

    public void setQty_booked(String qty_booked) {
        this.qty_booked = qty_booked;
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

    public String getIs_deliver() {
        return is_deliver;
    }

    public void setIs_deliver(String is_deliver) {
        this.is_deliver = is_deliver;
    }
}
