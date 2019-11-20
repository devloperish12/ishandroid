
        package com.indiansmarthub.ish.model;

        import java.util.List;
        import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;

public class SecurityShutterHomeProducts {

    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("cateid")
    @Expose
    private String cateid;
    @SerializedName("categoryProduct")
    @Expose
    private List<CategoryProduct1> categoryProduct = null;

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

    public String getCateid() {
        return cateid;
    }

    public void setCateid(String cateid) {
        this.cateid = cateid;
    }

    public List<CategoryProduct1> getCategoryProduct() {
        return categoryProduct;
    }

    public void setCategoryProduct(List<CategoryProduct1> categoryProduct) {
        this.categoryProduct = categoryProduct;
    }

}