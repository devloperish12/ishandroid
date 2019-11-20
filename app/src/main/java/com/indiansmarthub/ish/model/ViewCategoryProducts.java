     package com.indiansmarthub.ish.model;

        import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;

        import java.util.List;

public class ViewCategoryProducts {

    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("categoryProduct")
    @Expose
    private List<CategoryProduct> categoryProduct = null;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<CategoryProduct> getCategoryProduct() {
        return categoryProduct;
    }

    public void setCategoryProduct(List<CategoryProduct> categoryProduct) {
        this.categoryProduct = categoryProduct;
    }

}