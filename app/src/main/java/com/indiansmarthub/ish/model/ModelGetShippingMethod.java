package com.indiansmarthub.ish.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by g-10 on 31-08-2017.
 */

public class ModelGetShippingMethod
{
    @SerializedName("code")
    String code;

    @SerializedName("method")
    String method;

    @SerializedName("price")
    String price;

    public String getCode() {
        return code;
    }

    public String getMethod() {
        return method;
    }

    public String getPrice() {
        return price;
    }
}
