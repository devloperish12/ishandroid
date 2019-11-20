package com.indiansmarthub.ish.model.searchresponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchResponseModel {
    @SerializedName("products")
    @Expose
    private List<Product> products = null;

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

}