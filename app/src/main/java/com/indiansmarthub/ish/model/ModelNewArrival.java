package com.indiansmarthub.ish.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelNewArrival {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("sku")
    @Expose
    private String sku;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("regular_price")
    @Expose
    private String regularPrice;
    @SerializedName("label")
    @Expose
    private String label;
    @SerializedName("shortdescription")
    @Expose
    private String shortdescription;
    @SerializedName("img")
    @Expose
    private String img;
    @SerializedName("configurable")
    @Expose
    private String configurable;

    @SerializedName("wishlist")
    @Expose
    private String wishlist;

    public String getId() {
        return id;
    }

    public String getSku() {
        return sku;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getRegularPrice() {
        return regularPrice;
    }

    public String getLabel() {
        return label;
    }

    public String getShortdescription() {
        return shortdescription;
    }

    public String getImg() {
        return img;
    }

    public String getConfigurable() {
        return configurable;
    }

    public String getWishlist() {
        return wishlist;
    }
}
