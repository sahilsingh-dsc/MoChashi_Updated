package com.tetraval.mochashi.data.models;

public class VendorDeliveryModel {

    String f_id;
    String f_name;
    String f_location;
    String f_category;
    String f_rate;
    String f_hosted;
    String f_booked;
    String f_received;


    public VendorDeliveryModel(String f_id, String f_name, String f_location, String f_category, String f_rate, String f_hosted, String f_booked, String f_received) {
        this.f_id = f_id;
        this.f_name = f_name;
        this.f_location = f_location;
        this.f_category = f_category;
        this.f_rate = f_rate;
        this.f_hosted = f_hosted;
        this.f_booked = f_booked;
        this.f_received = f_received;
    }


    public String getF_id() {
        return f_id;
    }

    public void setF_id(String f_id) {
        this.f_id = f_id;
    }

    public String getF_name() {
        return f_name;
    }

    public void setF_name(String f_name) {
        this.f_name = f_name;
    }

    public String getF_location() {
        return f_location;
    }

    public void setF_location(String f_location) {
        this.f_location = f_location;
    }

    public String getF_category() {
        return f_category;
    }

    public void setF_category(String f_category) {
        this.f_category = f_category;
    }

    public String getF_rate() {
        return f_rate;
    }

    public void setF_rate(String f_rate) {
        this.f_rate = f_rate;
    }

    public String getF_hosted() {
        return f_hosted;
    }

    public void setF_hosted(String f_hosted) {
        this.f_hosted = f_hosted;
    }

    public String getF_booked() {
        return f_booked;
    }

    public void setF_booked(String f_booked) {
        this.f_booked = f_booked;
    }

    public String getF_received() {
        return f_received;
    }

    public void setF_received(String f_received) {
        this.f_received = f_received;
    }
}
