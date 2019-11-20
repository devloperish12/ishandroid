package com.indiansmarthub.ish.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelResponseBrand {

    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("brand")
    @Expose
    private List<ModalBrand> brand = null;

    public String getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public List<ModalBrand> getBrand() {
        return brand;
    }
}
