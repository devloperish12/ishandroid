package com.indiansmarthub.ish.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelTree {
    @SerializedName("level1")
    @Expose
    private String level1;
    @SerializedName("level2")
    @Expose
    private String level2;

    public String getLevel1() {
        return level1;
    }

    public String getLevel2() {
        return level2;
    }
}
