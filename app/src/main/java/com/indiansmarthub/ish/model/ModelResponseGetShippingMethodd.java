package com.indiansmarthub.ish.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by g-10 on 31-08-2017.
 */

public class ModelResponseGetShippingMethodd {
    @SerializedName("success")
    String success;

    @SerializedName("message")
    String message;

    @SerializedName("getShipping")
    List<ModelGetShippingMethod> getShipping;

    public String getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public List<ModelGetShippingMethod> getGetShipping() {
        return getShipping;
    }
}
