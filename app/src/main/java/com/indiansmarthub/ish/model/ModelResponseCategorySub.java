package com.indiansmarthub.ish.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelResponseCategorySub {
    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("category")
    @Expose
    private List<ModelCategorySub> category = null;

    public String getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public String getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }

    public List<ModelCategorySub> getCategory() {
        return category;
    }
}
