package com.tetraval.mochashi.data.models;

public class DeliveryModel {

    String d_id;
    String d_name;
    String d_contact;
    String d_cod;
    String d_address;

    public DeliveryModel(String d_id, String d_name, String d_contact, String d_cod, String d_address) {
        this.d_id = d_id;
        this.d_name = d_name;
        this.d_contact = d_contact;
        this.d_cod = d_cod;
        this.d_address = d_address;
    }

    public String getD_id() {
        return d_id;
    }

    public void setD_id(String d_id) {
        this.d_id = d_id;
    }

    public String getD_name() {
        return d_name;
    }

    public void setD_name(String d_name) {
        this.d_name = d_name;
    }

    public String getD_contact() {
        return d_contact;
    }

    public void setD_contact(String d_contact) {
        this.d_contact = d_contact;
    }

    public String getD_cod() {
        return d_cod;
    }

    public void setD_cod(String d_cod) {
        this.d_cod = d_cod;
    }

    public String getD_address() {
        return d_address;
    }

    public void setD_address(String d_address) {
        this.d_address = d_address;
    }
}
