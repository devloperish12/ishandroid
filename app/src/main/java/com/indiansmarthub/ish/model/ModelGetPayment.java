package com.indiansmarthub.ish.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by g-10 on 31-08-2017.
 */

public class ModelGetPayment
{
    @SerializedName("method")
    String method;

    @SerializedName("title")
    String title;

    public String getMethod() {
        return method;
    }

    public String getTitle() {
        return title;
    }
}
