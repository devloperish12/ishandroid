package com.indiansmarthub.ish.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by g-10 on 31-08-2017.
 */

public class ModelResponseGetPayment {
    @SerializedName("success")
    String success;

    @SerializedName("message")
    String message;

    @SerializedName("getPayment")
    List<ModelGetPayment> getPayment;

    public String getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public List<ModelGetPayment> getGetPayment() {
        return getPayment;
    }
}
