       package com.indiansmarthub.ish.model;

        import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;

        import java.util.List;

public class CartItems {

    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("Total Amount")
    @Expose
    private String totalamount;


    @SerializedName("Total Qty")
    @Expose
    private String totalqty;

    @SerializedName("addtocart")
    @Expose
    private List<Addtocart> addtocart = null;

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

    public String getTotalamount() {
        return totalamount;
    }

    public void setTotalamount(String totalamount) {
        this.totalamount = totalamount;
    }

    public String getTotalqty() {
        return totalqty;
    }

    public void setTotalqty(String totalqty) {
        this.totalqty = totalqty;
    }


    public List<Addtocart> getAddtocart() {
        return addtocart;
    }

    public void setAddtocart(List<Addtocart> addtocart) {
        this.addtocart = addtocart;
    }

}