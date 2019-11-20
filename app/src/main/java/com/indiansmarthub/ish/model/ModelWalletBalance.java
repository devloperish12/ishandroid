package com.indiansmarthub.ish.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelWalletBalance {
    @SerializedName("customerIsExit")
    @Expose
    private String customerIsExit;
    @SerializedName("point")
    @Expose
    private String point;

    public String getCustomerIsExit() {
        return customerIsExit;
    }

    public String getPoint() {
        return point;
    }
}
