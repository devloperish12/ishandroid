  package com.indiansmarthub.ish.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.indiansmarthub.ish.model.Setaddress;

public class GeneralModel {

    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("setaddress")
    @Expose
    private Setaddress setaddress;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Setaddress getSetaddress() {
        return setaddress;
    }

    public void setSetaddress(Setaddress setaddress) {
        this.setaddress = setaddress;
    }

}