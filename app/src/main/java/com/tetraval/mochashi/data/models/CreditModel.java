package com.tetraval.mochashi.data.models;

public class CreditModel {

    String credit_amount;
    String credit_reason;
    String credit_date;

    public CreditModel(String credit_amount, String credit_reason, String credit_date) {
        this.credit_amount = credit_amount;
        this.credit_reason = credit_reason;
        this.credit_date = credit_date;
    }

    public String getCredit_amount() {
        return credit_amount;
    }

    public void setCredit_amount(String credit_amount) {
        this.credit_amount = credit_amount;
    }

    public String getCredit_reason() {
        return credit_reason;
    }

    public void setCredit_reason(String credit_reason) {
        this.credit_reason = credit_reason;
    }

    public String getCredit_date() {
        return credit_date;
    }

    public void setCredit_date(String credit_date) {
        this.credit_date = credit_date;
    }
}
