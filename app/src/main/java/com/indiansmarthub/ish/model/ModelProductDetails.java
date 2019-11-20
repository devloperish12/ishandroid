package com.indiansmarthub.ish.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelProductDetails {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("sku")
    @Expose
    private String sku;
    @SerializedName("stock_avail")
    @Expose
    private String stockAvail;
    @SerializedName("name")
    @Expose
    private String name;
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
    @SerializedName("qty")
    @Expose
    private String qty;
    @SerializedName("minqty")
    @Expose
    private String minqty;
    @SerializedName("vendor")
    @Expose
    private String vendor;
    @SerializedName("avalable")
    @Expose
    private String avalable;
    @SerializedName("image")
    @Expose
    private String image;


    @SerializedName("cart")
    @Expose
    private String cart;

    @SerializedName("wishlist")
    @Expose
    private String wishlist;

    public String getId() {
        return id;
    }

    public String getSku() {
        return sku;
    }

    public String getStockAvail() {
        return stockAvail;
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

    public String getShortdescription() {
        return shortdescription;
    }

    public String getDescription() {
        return description;
    }

    public String getQty() {
        return qty;
    }

    public String getMinqty() {
        return minqty;
    }

    public String getVendor() {
        return vendor;
    }

    public String getAvalable() {
        return avalable;
    }

    public String getImage() {
        return image;
    }

    public String getWishlist() {
        return wishlist;
    }

    public String getCart() {
        return cart;
    }

    public void setCart(String cart) {
        this.cart = cart;
    }

}
