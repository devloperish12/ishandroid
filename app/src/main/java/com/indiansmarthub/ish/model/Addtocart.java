package com.indiansmarthub.ish.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Addtocart {

    @SerializedName("auto_id")
    @Expose
    private String autoId;

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("item_id")
    @Expose
    private String item_id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("cart")
    @Expose
    private String cart;
    @SerializedName("sku")
    @Expose
    private String sku;
    @SerializedName("qty")
    @Expose
    private Integer qty;

    public float getPricefloat() {
        return pricefloat;
    }

    public void setPricefloat(float pricefloat) {
        this.pricefloat = pricefloat;
    }

    @SerializedName("image")
    @Expose
    private String image;
    @Expose
    private float pricefloat;
    public String getAutoId() {
        return autoId;
    }

    public void setAutoId(String autoId) {
        this.autoId = autoId;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
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

    public String getCart() {
        return cart;
    }

    public void setCart(String cart) {
        this.cart = cart;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
