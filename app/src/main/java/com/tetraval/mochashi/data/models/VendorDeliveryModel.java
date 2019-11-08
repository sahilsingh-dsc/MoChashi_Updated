package com.tetraval.mochashi.data.models;

public class VendorDeliveryModel {

    String p_id;
    String shop_name;
    String shop_location;
    String product_name;
    String product_rate;
    String product_unit;
    String f_hosted;
    String f_booked;



    public VendorDeliveryModel(String p_id, String shop_name, String shop_location, String product_name, String product_rate, String product_unit, String f_hosted, String f_booked ) {
        this.p_id = p_id;
        this.shop_name = shop_name;
        this.shop_location = shop_location;
        this.product_name = product_name;
        this.product_rate = product_rate;
        this.product_unit = product_unit;
        this.f_hosted = f_hosted;
        this.f_booked = f_booked;

    }

    public String getP_id() {
        return p_id;
    }

    public String getShop_name() {
        return shop_name;
    }

    public String getShop_location() {
        return shop_location;
    }

    public String getProduct_name() {
        return product_name;
    }

    public String getProduct_rate() {
        return product_rate;
    }

    public String getProduct_unit() {
        return product_unit;
    }

    public String getF_hosted() {
        return f_hosted;
    }

    public String getF_booked() {
        return f_booked;
    }
}
