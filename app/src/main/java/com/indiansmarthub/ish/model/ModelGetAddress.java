package com.indiansmarthub.ish.model;

import com.google.gson.annotations.SerializedName;

public class ModelGetAddress {
    @SerializedName("id")
    String id;

    @SerializedName("firstname")
    String firstname;

    @SerializedName("lastname")
    String lastname;

    @SerializedName("company")
    String company;

    @SerializedName("street")
    String street;

    @SerializedName("city")
    String city;

    @SerializedName("region")
    String region;

    @SerializedName("postcode")
    String postcode;

    @SerializedName("country_id")
    String country_id;

    @SerializedName("telephone")
    String telephone;

    @SerializedName("defaultbilling")
    String defaultbilling;

    @SerializedName("defaulshiping")
    String defaulshiping;

    public String getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getCompany() {
        return company;
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

    public String getCountry_id() {
        return country_id;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getDefaultbilling() {
        return defaultbilling;
    }

    public String getDefaulshiping() {
        return defaulshiping;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public void setCountry_id(String country_id) {
        this.country_id = country_id;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setDefaultbilling(String defaultbilling) {
        this.defaultbilling = defaultbilling;
    }

    public void setDefaulshiping(String defaulshiping) {
        this.defaulshiping = defaulshiping;
    }
}
