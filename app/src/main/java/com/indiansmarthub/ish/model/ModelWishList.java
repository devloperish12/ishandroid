package com.indiansmarthub.ish.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelWishList {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("qty")
    @Expose
    private String qty;
    @SerializedName("image")
    @Expose
    private String image;

    @SerializedName("sku")
    @Expose
    private String sku;

    @SerializedName("wishlist")
    @Expose
    private String wishlist;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getQty() {
        return qty;
    }

    public String getImage() {
        return image;
    }

    public String getSku() {
        return sku;
    }

    public String getWishlist() {
        return wishlist;
    }
}
