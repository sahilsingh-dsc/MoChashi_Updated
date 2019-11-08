package com.tetraval.mochashi.data.models;

public class DeliveryModel {

    String customer_id;
    String customer_name;
    String customer_contact;
    String customer_rate;
    String customer_qty;
    String customer_address;
    String product_id;
    String status;
    String userid;

    public DeliveryModel(String customer_id, String customer_name, String customer_contact, String customer_rate, String customer_qty,String customer_address,String product_id,String status,String userid) {
        this.customer_id = customer_id;
        this.customer_name = customer_name;
        this.customer_contact = customer_contact;
        this.customer_rate = customer_rate;
        this.customer_qty = customer_qty;
        this.customer_address = customer_address;
        this.product_id = product_id;
        this.status = status;
        this.userid = userid;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public String getCustomer_contact() {
        return customer_contact;
    }

    public String getCustomer_rate() {
        return customer_rate;
    }

    public String getCustomer_qty() {
        return customer_qty;
    }

    public String getCustomer_address() {
        return customer_address;
    }

    public String getProduct_id() {
        return product_id;
    }

    public String getStatus() {
        return status;
    }

    public String getUserid() {
        return userid;
    }
}
