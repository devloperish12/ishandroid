package com.indiansmarthub.ish.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelResponseProductList {
    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("categoryProduct")
    @Expose
    private List<ModelProductList> categoryProduct = null;

    public String getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public List<ModelProductList> getCategoryProduct() {
        return categoryProduct;
    }
}
