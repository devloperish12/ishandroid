package com.indiansmarthub.ish.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelProductList {
    @SerializedName("ids")
    @Expose
    private String ids;
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
    @SerializedName("qty")
    @Expose
    private String qty;
    @SerializedName("stock_avail")
    @Expose
    private String stockAvail;
    @SerializedName("shortdescription")
    @Expose
    private String shortdescription;
    @SerializedName("img")
    @Expose
    private String img;

    @SerializedName("wishlist")
    @Expose
    private String wishlist;

    public String getIds() {
        return ids;
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

    public String getQty() {
        return qty;
    }

    public String getStockAvail() {
        return stockAvail;
    }

    public String getShortdescription() {
        return shortdescription;
    }

    public String getImg() {
        return img;
    }

    public String getWishlist() {
        return wishlist;
    }
}
