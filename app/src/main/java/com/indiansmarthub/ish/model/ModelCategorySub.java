package com.indiansmarthub.ish.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelCategorySub {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("child")
    @Expose
    private String child;
    @SerializedName("img")
    @Expose
    private String img;

    @SerializedName("customurl")
    @Expose
    private String customurl;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getChild() {
        return child;
    }

    public String getImg() {
        return img;
    }

    public String getCustomurl() {
        return customurl;
    }
}
