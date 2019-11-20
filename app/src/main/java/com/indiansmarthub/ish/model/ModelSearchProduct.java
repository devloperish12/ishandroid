package com.indiansmarthub.ish.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelSearchProduct {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("sku")
    @Expose
    private String sku;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("regular_price")
    @Expose
    private String regularPrice;
    @SerializedName("shortdescription")
    @Expose
    private String shortdescription;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("qty")
    @Expose
    private String qty;
    @SerializedName("stock_avail")
    @Expose
    private String stockAvail;
    @SerializedName("configurable")
    @Expose
    private String configurable;

    @SerializedName("wishlist")
    @Expose
    private String wishlist;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSku() {
        return sku;
    }

    public String getPrice() {
        return price;
    }

    public String getRegularPrice() {
        return regularPrice;
    }

    public String getShortdescription() {
        return shortdescription;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public String getQty() {
        return qty;
    }

    public String getStockAvail() {
        return stockAvail;
    }

    public String getConfigurable() {
        return configurable;
    }

    public String getWishlist() {
        return wishlist;
    }
}
