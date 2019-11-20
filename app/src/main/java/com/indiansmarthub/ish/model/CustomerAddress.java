package com.indiansmarthub.ish.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CustomerAddress {

    @SerializedName("id")
    @Expose
    private Integer id;
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
    @SerializedName("defaultbilling")
    @Expose
    private Boolean defaultbilling;
    @SerializedName("defaulshiping")
    @Expose
    private Boolean defaulshiping;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Boolean getDefaultbilling() {
        return defaultbilling;
    }

    public void setDefaultbilling(Boolean defaultbilling) {
        this.defaultbilling = defaultbilling;
    }

    public Boolean getDefaulshiping() {
        return defaulshiping;
    }

    public void setDefaulshiping(Boolean defaulshiping) {
        this.defaulshiping = defaulshiping;
    }

}
