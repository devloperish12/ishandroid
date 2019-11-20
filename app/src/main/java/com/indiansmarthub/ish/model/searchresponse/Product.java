package com.indiansmarthub.ish.model.searchresponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Product {
    @SerializedName("label")
    @Expose
    private String label;
    @SerializedName("wishlist")
    @Expose
    private String wishlist;
    @SerializedName("cart")
    @Expose
    private String cart;
    @SerializedName("img")
    @Expose
    private String img;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("sku")
    @Expose
    private String sku;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("regular_price")
    @Expose
    private Object regularPrice;
    @SerializedName("shortdescription")
    @Expose
    private String shortdescription;
    @SerializedName("rewardpoint")
    @Expose
    private Object rewardpoint;
    @SerializedName("qty")
    @Expose
    private Object qty;
    @SerializedName("configurable")
    @Expose
    private Integer configurable;
    @SerializedName("product_image")
    @Expose
    private List<ProductImage> productImage = null;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Object getRegularPrice() {
        return regularPrice;
    }

    public void setRegularPrice(Object regularPrice) {
        this.regularPrice = regularPrice;
    }

    public String getShortdescription() {
        return shortdescription;
    }

    public void setShortdescription(String shortdescription) {
        this.shortdescription = shortdescription;
    }

    public Object getRewardpoint() {
        return rewardpoint;
    }

    public void setRewardpoint(Object rewardpoint) {
        this.rewardpoint = rewardpoint;
    }

    public Object getQty() {
        return qty;
    }

    public void setQty(Object qty) {
        this.qty = qty;
    }

    public Integer getConfigurable() {
        return configurable;
    }

    public void setConfigurable(Integer configurable) {
        this.configurable = configurable;
    }

    public List<ProductImage> getProductImage() {
        return productImage;
    }

    public void setProductImage(List<ProductImage> productImage) {
        this.productImage = productImage;
    }

}
