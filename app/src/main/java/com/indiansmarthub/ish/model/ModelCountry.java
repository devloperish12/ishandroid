package com.indiansmarthub.ish.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelCountry {
    @SerializedName("countryID")
    @Expose
    private String countryID;
    @SerializedName("countryName")
    @Expose
    private String countryName;

    public String getCountryID() {
        return countryID;
    }

    public String getCountryName() {
        return countryName;
    }
}
