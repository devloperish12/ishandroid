package com.indiansmarthub.ish.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelOrderDetails {
    @SerializedName("tax_amount")
    @Expose
    private String taxAmount;
    @SerializedName("DeliveryCharge")
    @Expose
    private String deliveryCharge;
    @SerializedName("Frenchies")
    @Expose
    private String frenchies;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("expectedtime")
    @Expose
    private String expectedtime;
    @SerializedName("forquerycontact")
    @Expose
    private String forquerycontact;
    @SerializedName("rewardused")
    @Expose
    private String rewardused;
    @SerializedName("discount_amount")
    @Expose
    private String discountAmount;
    @SerializedName("subtotal")
    @Expose
    private String subtotal;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("status")
    @Expose
    private String status;


    @SerializedName("ordertotal")
    @Expose
    private String mRemainingordertotal;

    @SerializedName("order_id")
    @Expose
    private String order_id;
    public String getTaxAmount() {
        return taxAmount;
    }

    public String getDeliveryCharge() {
        return deliveryCharge;
    }

    public String getFrenchies() {
        return frenchies;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getExpectedtime() {
        return expectedtime;
    }

    public String getForquerycontact() {
        return forquerycontact;
    }

    public String getRewardused() {
        return rewardused;
    }

    public String getDiscountAmount() {
        return discountAmount;
    }

    public String getSubtotal() {
        return subtotal;
    }

    public String getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }

    public String getOrder_id() {
        return order_id;
    }

    public String getmRemainingordertotal() {
        return mRemainingordertotal;
    }

    public void setmRemainingordertotal(String mRemainingordertotal) {
        this.mRemainingordertotal = mRemainingordertotal;
    }
}

