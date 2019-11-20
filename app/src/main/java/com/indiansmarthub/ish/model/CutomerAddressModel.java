      package com.indiansmarthub.ish.model;

      import com.google.gson.annotations.Expose;
      import com.google.gson.annotations.SerializedName;

      import java.util.List;

      public class CutomerAddressModel {

    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("customerAddress")
    @Expose
    private List<CustomerAddress> customerAddress = null;

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<CustomerAddress> getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(List<CustomerAddress> customerAddress) {
        this.customerAddress = customerAddress;
    }

}