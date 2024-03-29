
        package com.indiansmarthub.ish.model;

        import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;

        import java.util.List;

public class BannerOfferModel {

    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("banner")
    @Expose
    private List<BannerOffer> banner = null;

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

    public List<BannerOffer> getBanner() {
        return banner;
    }

    public void setBanner(List<BannerOffer> banner) {
        this.banner = banner;
    }

}