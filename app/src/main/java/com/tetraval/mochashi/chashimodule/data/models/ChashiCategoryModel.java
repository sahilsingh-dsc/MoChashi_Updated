package com.tetraval.mochashi.chashimodule.data.models;

public class ChashiCategoryModel {

    String product_id;
    String product_name;
    String product_image;

    public ChashiCategoryModel(String product_id, String product_name, String product_image) {
        this.product_id = product_id;
        this.product_name = product_name;
        this.product_image = product_image;
    }

    public String getProduct_id() {
        return product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public String getProduct_image() {
        return product_image;
    }
}
