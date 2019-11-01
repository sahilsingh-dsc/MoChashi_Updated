package com.tetraval.mochashi.data.models;

public class HatOrdersModel {

    String odr_id;
    String total_amount;
    String odr_date;
    String Status;


    public HatOrdersModel(String odr_id, String total_amount,String odr_date, String status) {
        this.odr_id = odr_id;
        this.total_amount = total_amount;
        this.odr_date = odr_date;
        this.Status = status;

    }

    public String getOdr_id() {
        return odr_id;
    }

    public void setOdr_id(String odr_id) {
        this.odr_id = odr_id;
    }


    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
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

}
