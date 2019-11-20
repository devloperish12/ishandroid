package com.indiansmarthub.ish.model;

/**
 * Created by g-10 on 21-09-2017.
 */

public class ModelLocalCart {
    String productId;
    String productQty;
    String isConfigurable;
    String superAttribute;
    String sku;
    String price;
    String autoId;
    String name;
    String label;
    String image;


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }



    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAutoId() {
        return autoId;
    }

    public void setAutoId(String autoId) {
        this.autoId = autoId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductQty() {
        return productQty;
    }

    public void setProductQty(String productQty) {
        this.productQty = productQty;
    }

    public String getIsConfigurable() {
        return isConfigurable;
    }

    public void setIsConfigurable(String isConfigurable) {
        this.isConfigurable = isConfigurable;
    }

    public String getSuperAttribute() {
        return superAttribute;
    }

    public void setSuperAttribute(String superAttribute) {
        this.superAttribute = superAttribute;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getValueIndex() {
        return valueIndex;
    }

    public void setValueIndex(String valueIndex) {
        this.valueIndex = valueIndex;
    }

    String valueIndex;
}
