package com.indiansmarthub.ish.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelOrderHistory {
    @SerializedName("orderid")
    @Expose
    private String orderid;
    @SerializedName("DeliveryCharge")
    @Expose
    private String deliveryCharge;
    @SerializedName("Skmused")
    @Expose
    private String skmused;
    @SerializedName("orderdate")
    @Expose
    private String orderdate;
    @SerializedName("ordertotal")
    @Expose
    private String ordertotal;
    @SerializedName("status")
    @Expose
    private String status;


    @SerializedName("expectedtime")
    @Expose
    private String expectedtime;

    public String getOrderid() {
        return orderid;
    }

    public String getDeliveryCharge() {
        return deliveryCharge;
    }

    public String getSkmused() {
        return skmused;
    }

    public String getOrderdate() {
        return orderdate;
    }

    public String getOrdertotal() {
        return ordertotal;
    }

    public String getStatus() {
        return status;
    }

    public String getExpectedtime() {
        return expectedtime;
    }

    public void setExpectedtime(String expectedtime) {
        this.expectedtime = expectedtime;
    }
}
