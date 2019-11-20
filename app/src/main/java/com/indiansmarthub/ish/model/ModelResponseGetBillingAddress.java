package com.indiansmarthub.ish.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelResponseGetBillingAddress {

    @SerializedName("success")
    String success;

    @SerializedName("message")
    String message;

    @SerializedName("getaddress")
    List<ModelGetBillingAddress> getaddress;

    public String getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public List<ModelGetBillingAddress> getGetaddress() {
        return getaddress;
    }
}
