package com.tetraval.mochashi.data.models;

public class OrdersModel {

    String odr_id;
    String odr_name;
    String odr_qty;
    String total_amount;
    String odr_product_image;
    String odr_date;
    String Status;
    String productid;

    public OrdersModel(String odr_id, String odr_name, String odr_qty, String total_amount, String odr_product_image, String odr_date,String status,String productid) {
        this.odr_id = odr_id;
        this.odr_name = odr_name;
        this.odr_qty = odr_qty;
        this.total_amount = total_amount;
        this.odr_product_image = odr_product_image;
        this.odr_date = odr_date;
        this.Status = status;
        this.productid = productid;
    }

    public String getOdr_id() {
        return odr_id;
    }

    public void setOdr_id(String odr_id) {
        this.odr_id = odr_id;
    }

    public String getOdr_name() {
        return odr_name;
    }

    public void setOdr_name(String odr_name) {
        this.odr_name = odr_name;
    }

    public String getOdr_qty() {
        return odr_qty;
    }

    public void setOdr_qty(String odr_qty) {
        this.odr_qty = odr_qty;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    public String getOdr_product_image() {
        return odr_product_image;
    }

    public void setOdr_product_image(String odr_product_image) {
        this.odr_product_image = odr_product_image;
    }

    public String getOdr_date() {
        return odr_date;
    }

    public void setOdr_date(String odr_date) {
        this.odr_date = odr_date;
    }

    public String getStatus() {
        return Status;
    }

    public String getProductid() {
        return productid;
    }
}
