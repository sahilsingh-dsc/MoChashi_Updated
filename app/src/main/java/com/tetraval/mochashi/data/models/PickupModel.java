package com.tetraval.mochashi.data.models;

public class PickupModel {

    String pickup_id;
    String p_chashiamt;
    String p_chashiname;
    String p_productname;
    String p_address;
    String p_qty;


    public PickupModel(String pickup_id, String p_chashiamt, String p_chashiname, String p_productname, String p_address, String p_qty) {
        this.pickup_id = pickup_id;
        this.p_chashiamt = p_chashiamt;
        this.p_chashiname = p_chashiname;
        this.p_productname = p_productname;
        this.p_address = p_address;
        this.p_qty = p_qty;
    }

    public String getPickup_id() {
        return pickup_id;
    }

    public void setPickup_id(String pickup_id) {
        this.pickup_id = pickup_id;
    }

    public String getP_chashiamt() {
        return p_chashiamt;
    }

    public void setP_chashiamt(String p_chashiamt) {
        this.p_chashiamt = p_chashiamt;
    }

    public String getP_chashiname() {
        return p_chashiname;
    }

    public void setP_chashiname(String p_chashiname) {
        this.p_chashiname = p_chashiname;
    }

    public String getP_productname() {
        return p_productname;
    }

    public void setP_productname(String p_productname) {
        this.p_productname = p_productname;
    }

    public String getP_address() {
        return p_address;
    }

    public void setP_address(String p_address) {
        this.p_address = p_address;
    }

    public String getP_qty() {
        return p_qty;
    }

    public void setP_qty(String p_qty) {
        this.p_qty = p_qty;
    }
}
