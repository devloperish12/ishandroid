package com.indiansmarthub.ish.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ModelResponseProductDetails {

    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("product")
    @Expose
    private ArrayList<ModelProductDetails> product = null;
//    @SerializedName("Review")
//    @Expose
//    private List<Review> review = null;


    public String getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public List<ModelProductDetails> getProduct() {
        return product;
    }
}
