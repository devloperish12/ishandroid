package com.indiansmarthub.ish.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CategoryProduct1 {

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
    private Integer price;
    @SerializedName("regular_price")
    @Expose
    private Integer regularPrice;
    @SerializedName("rewardpoint")
    @Expose
    private String rewardpoint;
    @SerializedName("wishlist")
    @Expose
    private String wishlist;
    @SerializedName("cart")
    @Expose
    private String cart;
    @SerializedName("qty")
    @Expose
    private Integer qty;
    @SerializedName("stock_avail")
    @Expose
    private String stockAvail;
    @SerializedName("shortdescription")
    @Expose
    private String shortdescription;
    @SerializedName("img")
    @Expose
    private String img;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getRegularPrice() {
        return regularPrice;
    }

    public void setRegularPrice(Integer regularPrice) {
        this.regularPrice = regularPrice;
    }

    public String getRewardpoint() {
        return rewardpoint;
    }

    public void setRewardpoint(String rewardpoint) {
        this.rewardpoint = rewardpoint;
    }

    public String getWishlist() {
        return wishlist;
    }

    public void setWishlist(String wishlist) {
        this.wishlist = wishlist;
    }

    public String getCart() {
        return cart;
    }

    public void setCart(String cart) {
        this.cart = cart;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public String getStockAvail() {
        return stockAvail;
    }

    public void setStockAvail(String stockAvail) {
        this.stockAvail = stockAvail;
    }

    public String getShortdescription() {
        return shortdescription;
    }

    public void setShortdescription(String shortdescription) {
        this.shortdescription = shortdescription;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

}
