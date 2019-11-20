package com.indiansmarthub.ish.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelResponseGetAddress {
    @SerializedName("success")
    String success;

    @SerializedName("message")
    String message;

    @SerializedName("customerAddress")
    List<ModelGetAddress> customerAddress;

    public String getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public List<ModelGetAddress> getCustomerAddress() {
        return customerAddress;
    }
}
