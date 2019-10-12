package com.tetraval.mochashi.haatgrocerymodule.data.models;

public class GroceryCategoryModel {

    String cat_id;
    String cat_images;
    String cat_name;

    public GroceryCategoryModel(String cat_id, String cat_images, String cat_name) {
        this.cat_id = cat_id;
        this.cat_images = cat_images;
        this.cat_name = cat_name;
    }

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    public String getCat_images() {
        return cat_images;
    }

    public void setCat_images(String cat_images) {
        this.cat_images = cat_images;
    }

    public String getCat_name() {
        return cat_name;
    }

    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }



}
