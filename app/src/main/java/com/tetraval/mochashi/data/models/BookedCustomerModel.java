package com.tetraval.mochashi.data.models;

public class BookedCustomerModel {

    String booking_id;
    double booking_lat;
    double booking_lng;
    String booking_name;
    String booking_photo;
    String booking_qty;

    public BookedCustomerModel(String booking_id, double booking_lat, double booking_lng, String booking_name, String booking_photo, String booking_qty) {
        this.booking_id = booking_id;
        this.booking_lat = booking_lat;
        this.booking_lng = booking_lng;
        this.booking_name = booking_name;
        this.booking_photo = booking_photo;
        this.booking_qty = booking_qty;
    }

    public String getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(String booking_id) {
        this.booking_id = booking_id;
    }

    public double getBooking_lat() {
        return booking_lat;
    }

    public void setBooking_lat(double booking_lat) {
        this.booking_lat = booking_lat;
    }

    public double getBooking_lng() {
        return booking_lng;
    }

    public void setBooking_lng(double booking_lng) {
        this.booking_lng = booking_lng;
    }

    public String getBooking_name() {
        return booking_name;
    }

    public void setBooking_name(String booking_name) {
        this.booking_name = booking_name;
    }

    public String getBooking_photo() {
        return booking_photo;
    }

    public void setBooking_photo(String booking_photo) {
        this.booking_photo = booking_photo;
    }

    public String getBooking_qty() {
        return booking_qty;
    }

    public void setBooking_qty(String booking_qty) {
        this.booking_qty = booking_qty;
    }
}
