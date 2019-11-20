package com.indiansmarthub.ish.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelRewardHistory {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("point")
    @Expose
    private String point;
    @SerializedName("comment")
    @Expose
    private String comment;
    @SerializedName("date")
    @Expose
    private String date;

    public String getId() {
        return id;
    }

    public String getPoint() {
        return point;
    }

    public String getComment() {
        return comment;
    }

    public String getDate() {
        return date;
    }
}
