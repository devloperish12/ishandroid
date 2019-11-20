package com.indiansmarthub.ish.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelResponseFeaturedProduct {
    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("featuredProduct")
    @Expose
    private List<ModelFeaturedProduct> featuredProduct = null;

    public String getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public List<ModelFeaturedProduct> getFeaturedProduct() {
        return featuredProduct;
    }
}
