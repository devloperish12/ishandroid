package com.indiansmarthub.ish.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelResponseOrderDetails {
    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("Order")
    @Expose
    private List<ModelOrderDetails> order = null;
    @SerializedName("items")
    @Expose
    private List<ModelOrderItem> items = null;

    @SerializedName("shipping address")
    @Expose
    private List<ModelShippingAddress> shippingAddress = null;

    public String getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public List<ModelOrderDetails> getOrder() {
        return order;
    }

    public List<ModelOrderItem> getItems() {
        return items;
    }

    public List<ModelShippingAddress> getShippingAddress() {
        return shippingAddress;
    }
}
