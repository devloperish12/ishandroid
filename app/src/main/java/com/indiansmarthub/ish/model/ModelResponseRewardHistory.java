package com.indiansmarthub.ish.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelResponseRewardHistory {
    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("balance")
    @Expose
    private String balance;
    @SerializedName("customerHistory")
    @Expose
    private List<ModelRewardHistory> customerHistory = null;

    public String getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public List<ModelRewardHistory> getCustomerHistory() {
        return customerHistory;
    }

    public String getBalance() {
        return balance;
    }
}
