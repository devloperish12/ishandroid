package com.indiansmarthub.ish.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelGetBillingAddress {
    @SerializedName("customer_address_id")
    @Expose
    private String customerAddressId;
    @SerializedName("firstname")
    @Expose
    private String firstname;
    @SerializedName("lastname")
    @Expose
    private String lastname;
    @SerializedName("street")
    @Expose
    private String street;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("region")
    @Expose
    private String region;
    @SerializedName("postcode")
    @Expose
    private String postcode;
    @SerializedName("country_id")
    @Expose
    private String countryId;
    @SerializedName("telephone")
    @Expose
    private String telephone;
    @SerializedName("is_default_billing")
    @Expose
    private String isDefaultBilling;
    @SerializedName("is_default_shipping")
    @Expose
    private String isDefaultShipping;

    public String getCustomerAddressId() {
        return customerAddressId;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getRegion() {
        return region;
    }

    public String getPostcode() {
        return postcode;
    }

    public String getCountryId() {
        return countryId;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getIsDefaultBilling() {
        return isDefaultBilling;
    }

    public String getIsDefaultShipping() {
        return isDefaultShipping;
    }
}
