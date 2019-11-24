package com.indiansmarthub.ish.retrofit;

import com.indiansmarthub.ish.model.AcHomeProducts;
import com.indiansmarthub.ish.model.BannerOfferModel;
import com.indiansmarthub.ish.model.CartItems;
import com.indiansmarthub.ish.model.Count;
import com.indiansmarthub.ish.model.CutomerAddressModel;
import com.indiansmarthub.ish.model.GeneralModel;
import com.indiansmarthub.ish.model.HologramHomeProducts;
import com.indiansmarthub.ish.model.LedHomeProducts;
import com.indiansmarthub.ish.model.ModelGenralResponse;
import com.indiansmarthub.ish.model.ModelResponseBanner;
import com.indiansmarthub.ish.model.ModelResponseBlog;
import com.indiansmarthub.ish.model.ModelResponseBrand;
import com.indiansmarthub.ish.model.ModelResponseCategory;
import com.indiansmarthub.ish.model.ModelResponseCategorySub;
import com.indiansmarthub.ish.model.ModelResponseCountry;
import com.indiansmarthub.ish.model.ModelResponseFeaturedProduct;
import com.indiansmarthub.ish.model.ModelResponseGetAddress;
import com.indiansmarthub.ish.model.ModelResponseGetPayment;
import com.indiansmarthub.ish.model.ModelResponseGetShippingMethodd;
import com.indiansmarthub.ish.model.ModelResponseNewArrival;
import com.indiansmarthub.ish.model.ModelResponseOrderDetails;
import com.indiansmarthub.ish.model.ModelResponseOrderHistory;
import com.indiansmarthub.ish.model.ModelResponseProductDetails;
import com.indiansmarthub.ish.model.ModelResponseProductList;
import com.indiansmarthub.ish.model.ModelResponseRegister;
import com.indiansmarthub.ish.model.ModelResponseRewardHistory;
import com.indiansmarthub.ish.model.ModelResponseSearchProduct;
import com.indiansmarthub.ish.model.ModelResponseSlider;
import com.indiansmarthub.ish.model.ModelResponseTodayDeal;
import com.indiansmarthub.ish.model.ModelResponseTree;
import com.indiansmarthub.ish.model.ModelResponseWalletBalance;
import com.indiansmarthub.ish.model.ModelResponseWishList;
import com.indiansmarthub.ish.model.SecurityShutterHomeProducts;
import com.indiansmarthub.ish.model.ServicesModel;
import com.indiansmarthub.ish.model.SubCategoryProduct;
import com.indiansmarthub.ish.model.ViewCategoryProducts;
import com.indiansmarthub.ish.model.searchresponse.SearchResponseModel;
import com.indiansmarthub.ish.pojo.UserUpdateResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface NetworkService {

    @FormUrlEncoded
    @POST("register")
    Call<ModelResponseRegister> sendRegistration(@Field("user_type") String utype, @Field("email")
            String email, @Field("name") String fname, @Field("last_name") String lname, @Field("password") String pwd,@Field("role")
            String role,@Field("middle_name") String mname,@Field("confirm_password") String confirm_password);

    @FormUrlEncoded
    @POST("customerLogin.php")
    Call<ModelResponseRegister> sendLogin(@Field("hashkey") String hashkey, @Field("email") String email, @Field("pwd") String pwd);

    @FormUrlEncoded
    @POST("forgotpassword.php")
    Call<ModelGenralResponse> sendForgotPass(@Field("hashkey") String hashkey, @Field("email") String email);

    @FormUrlEncoded
    @POST("slider.php")
    Call<ModelResponseSlider> getSliderImage(@Field("hashkey") String hashkey);

    @FormUrlEncoded
    @POST("ourservices.php")
    Call<ModelResponseCategory> getOurServices(@Field("hashkey") String hashkey);

    @FormUrlEncoded
    @POST("product_home.php")
    Call<ModelResponseProductList> sendProductList(@Field("hashkey") String hashkey, @Field("cate_id") String cate_id);

    @FormUrlEncoded
    @POST("brand.php")
    Call<ModelResponseBrand> sendBrand(@Field("hashkey") String hashkey);

    @FormUrlEncoded
    @POST("featured_product_home.php")
    Call<ModelResponseFeaturedProduct> getFeaturedProduct(@Field("hashkey") String hashkey, @Field("cust_id") String cust_id);

    @FormUrlEncoded
    @POST("subservices.php")
    Call<SubCategoryProduct> getSubCategoryProduct(@Field("hashkey") String hashkey);


    @FormUrlEncoded
    @POST("newarrival_home.php")
    Call<ModelResponseNewArrival> getNewArrivalsProduct(@Field("hashkey") String hashkey, @Field("cust_id") String cust_id);

    @FormUrlEncoded
    @POST("blog.php")
    Call<ModelResponseBlog> getBlog(@Field("hashkey") String hashkey);

    @FormUrlEncoded
    @POST("featured_product.php")
    Call<ModelResponseFeaturedProduct> getfeaturedproduct(@Field("hashkey") String hashkey, @Field("cust_id") String cust_id);

    @FormUrlEncoded
    @POST("newarrival.php")
    Call<ModelResponseFeaturedProduct> getnewArrivaleproduct(@Field("hashkey") String hashkey, @Field("cust_id") String cust_id);

    @FormUrlEncoded
    @POST("new_cate_product.php")
    Call<ModelResponseProductList> sendProductListMultiple(@Field("hashkey") String hashkey, @Field("cate_id") String cate_id, @Field("cust_id") String cust_id);

    @FormUrlEncoded
    @POST("productView.php")
    Call<ModelResponseProductDetails> getProductDetials(@Field("hashkey") String hashkey, @Field("product_id") String product_id, @Field("cust_id") String cust_id);


    @FormUrlEncoded
    @POST("productSearch.php")
    Call<ModelResponseSearchProduct> getSearchProduct(@Field("hashkey") String hashkey, @Field("name") String name, @Field("cust_id") String cust_id);

    @FormUrlEncoded
    @POST("configAdd.php")
    Call<GeneralModel> CheckoutProduct(@Field("hashkey") String hashkey, @Field("cart") String cart);

    @FormUrlEncoded
    @POST("cutomerAddressCreate.php")
    Call<GeneralModel> AddNewAddress(@Field("hashkey") String hashkey, @Field("custid") String custid, @Field("firstname") String firstname, @Field("lastname") String lastname, @Field("street") String street, @Field("streetone") String streetone, @Field("city") String city, @Field("region") String region, @Field("postcode") String postcode, @Field("country_id") String country_id, @Field("telephone") String telephone, @Field("defaultbill") String defaultbill, @Field("defaultship") String defaultship);

    @FormUrlEncoded
    @POST("checkOut.php")
    Call<GeneralModel> setBothAddress(@Field("hashkey") String hashkey, @Field("custid") String custid, @Field("action") String action, @Field("ship_address_id") String ship_address_id, @Field("bill_address_id") String bill_address_id);

    @FormUrlEncoded
    @POST("checkOut.php")
    Call<GeneralModel> getBillingAddress(@Field("hashkey") String hashkey, @Field("custid") String custid, @Field("bill_firstname") String bill_firstname, @Field("bill_lastname") String bill_lastname, @Field("bill_street") String bill_street, @Field("bill_city") String bill_city, @Field("bill_region") String bill_region, @Field("bill_postcode") String bill_postcode, @Field("bill_country_id") String bill_country_id, @Field("bill_telephone") String bill_telephone,@Field("action") String action);

    @FormUrlEncoded
    @POST("cutomerAddress.php")
    Call<ModelResponseGetAddress> getCustomerAddress(@Field("hashkey") String hashkey, @Field("custid") String custid);

    @FormUrlEncoded
    @POST("cutomerAddressDelete.php")
    Call<GeneralModel> deleteAddress(@Field("hashkey") String hashkey, @Field("id") String id);

    @FormUrlEncoded
    @POST("cutomerAddressUpdate.php")
    Call<GeneralModel> updateAddress(@Field("hashkey") String hashkey, @Field("id") String id, @Field("firstname") String firstname, @Field("lastname") String lastname, @Field("street") String street, @Field("streetone") String streetone, @Field("city") String city, @Field("region") String region, @Field("postcode") String postcode, @Field("country_id") String country_id, @Field("telephone") String telephone, @Field("region_id") String region_id, @Field("defaultbill") String defaultbill, @Field("defaultship") String defaultship);

    @FormUrlEncoded
    @POST("checkout_guest.php")
    Call<GeneralModel> GuestPlaceOrder(@Field("hashkey") String hashkey, @Field("cart") String cart, @Field("guest_fname") String guest_fname, @Field("guest_lname") String guest_lname, @Field("guest_email") String guest_email, @Field("bill_firstname") String bill_firstname, @Field("bill_lastname") String bill_lastname, @Field("bill_street") String bill_street, @Field("bill_city") String bill_city, @Field("bill_region") String bill_region, @Field("bill_postcode") String bill_postcode, @Field("bill_country_id") String bill_country_id, @Field("bill_telephone") String bill_telephone, @Field("ship_firstname") String ship_firstname, @Field("ship_lastname") String ship_lastname, @Field("ship_street") String ship_street, @Field("ship_city") String ship_city, @Field("ship_region") String ship_region, @Field("ship_postcode") String ship_postcode, @Field("ship_country_id") String ship_country_id, @Field("ship_telephone") String ship_telephone);

    @FormUrlEncoded
    @POST("shipingmethod.php")
    Call<ModelResponseGetShippingMethodd> getShippingMethod(@Field("hashkey") String hashkey, @Field("custid") String custid);

    @FormUrlEncoded
    @POST("shipingmethod.php")
    Call<GeneralModel> setShippingMethod(@Field("hashkey") String hashkey, @Field("custid") String custid, @Field("action") String action, @Field("code") String code);

    @FormUrlEncoded
    @POST("paymentMethod.php")
    Call<ModelResponseGetPayment> getPaymentMethod(@Field("hashkey") String hashkey, @Field("custid") String custid);

    @FormUrlEncoded
    @POST("paymentMethod.php")
    Call<GeneralModel> setPaymentMethod(@Field("hashkey") String hashkey, @Field("custid") String custid, @Field("action") String action, @Field("method") String method);

    @FormUrlEncoded
    @POST("placeOrder.php")
    Call<GeneralModel> placeOrders(@Field("hashkey") String hashkey, @Field("custid") String custid, @Field("balance_used") String balance_used);

    @FormUrlEncoded
    @POST("reward_history.php")
    Call<ModelResponseRewardHistory> getRewardHistory(@Field("hashkey") String hashkey, @Field("custid") String custid);

    @FormUrlEncoded
    @POST("reward_point.php")
    Call<ModelResponseWalletBalance> getWalletBalance(@Field("hashkey") String hashkey, @Field("custid") String custid);

    @FormUrlEncoded
    @POST("wishlist_items.php")
    Call<ModelResponseWishList> getWishList(@Field("hashkey") String hashkey, @Field("custid") String custid);

    @FormUrlEncoded
    @POST("wishlist.php")
    Call<GeneralModel> addWishList(@Field("hashkey") String hashkey, @Field("custid") String custid, @Field("prodid") String prodid, @Field("sku") String sku);

    @FormUrlEncoded
    @POST("wishlist_delete.php")
    Call<GeneralModel> removeWishList(@Field("hashkey") String hashkey, @Field("custid") String custid, @Field("prodid") String prodid);

    @FormUrlEncoded
    @POST("customerOrder.php")
    Call<ModelResponseOrderHistory> getordetHistory(@Field("hashkey") String hashkey, @Field("custid") String custid);

    @FormUrlEncoded
    @POST("customerOrderDetails.php")
    Call<ModelResponseOrderDetails> getordetDetails(@Field("hashkey") String hashkey, @Field("orderid") String orderid);

    @FormUrlEncoded
    @POST("categories.php")
    Call<ModelResponseCategorySub> getCategory(@Field("hashkey") String hashkey, @Field("cate_id") String cate_id);

    @Multipart
    @POST("order_documents.php")
    Call<GeneralModel> uploadfile(@Part("hashkey") RequestBody hashkey, @Part MultipartBody.Part file);

    @FormUrlEncoded
    @POST("blog_all.php")
    Call<ModelResponseBlog> getBlogVies(@Field("hashkey") String hashkey);

    @FormUrlEncoded
    @POST("frenchies.php")
    Call<ModelResponseTree> getTree(@Field("hashkey") String hashkey, @Field("custid") String custid);

    @FormUrlEncoded
    @POST("countries.php")
    Call<ModelResponseCountry> getContry(@Field("hashkey") String hashkey);

    @FormUrlEncoded
    @POST("becomeAgent.php")
    Call<GeneralModel> setAgentData(@Field("hashkey") String hashkey, @Field("firstname") String firstname, @Field("lastname") String lastname, @Field("email_id") String email_id, @Field("mobile_number") String mobile_number, @Field("alternate_mobile_number") String alternate_mobile_number, @Field("address") String address, @Field("country") String country, @Field("pincode") String pincode);

    @FormUrlEncoded
    @POST("orderTrack.php")
    Call<ModelResponseOrderDetails> getTrakingorder(@Field("hashkey") String hashkey, @Field("orderid") String orderid, @Field("email") String email);

    @FormUrlEncoded
    @POST("contactus")
    Call<GeneralModel> setContacts(@Field("name") String name, @Field("email") String email_id, @Field("phone") String mobile_number, @Field("comment") String comment);

    @FormUrlEncoded
    @POST("banner_offer.php")
    Call<BannerOfferModel> gettodaybaner(@Field("hashkey") String hashkey);

    @FormUrlEncoded
    @POST("banner.php")
    Call<ModelResponseBanner> getbeener(@Field("hashkey") String hashkey);

    @FormUrlEncoded
    @POST("view_category_products.php")
    Call<ViewCategoryProducts> getservicesproduct(@Field("hashkey") String hashkey,@Field("cate_id") String cate_id,@Field("cust_id") String custid);

    @FormUrlEncoded
    @POST("cart.php")
    Call<GeneralModel> addCart(@Field("hashkey") String hashkey, @Field("cart") String cart);

    @FormUrlEncoded
    @POST("cart_items.php")
    Call<CartItems> getCartItems(@Field("hashkey") String hashkey, @Field("custid") String cate_id);

    @FormUrlEncoded
    @POST("cart_delete.php")
    Call<GeneralModel> getCartDeleteItems(@Field("hashkey") String hashkey, @Field("custid") String cate_id, @Field("itemid") String item_id);

    @FormUrlEncoded
    @POST("hologram_home_products.php")
    Call<HologramHomeProducts> gethologramProducts(@Field("hashkey") String hashkey);

    @FormUrlEncoded
    @POST("led_home_products.php")
    Call<LedHomeProducts> getledProducts(@Field("hashkey") String hashkey);

    @FormUrlEncoded
    @POST("security_shutter_home_products.php")
    Call<SecurityShutterHomeProducts> getSecurityShutterProducts(@Field("hashkey") String hashkey);

    @FormUrlEncoded
    @POST("ac_home_products.php")
    Call<AcHomeProducts> getAcProducts(@Field("hashkey") String hashkey);

    @FormUrlEncoded
    @POST("count.php")
    Call<Count> getcount(@Field("hashkey") String hashkey, @Field("custid") String custid);

    @FormUrlEncoded
    @POST("cutomerAddress.php")
    Call<CutomerAddressModel> getcustomerAddress(@Field("hashkey") String hashkey, @Field("custid") String cust_id);

    @FormUrlEncoded
    @POST("cutomerAddressCreate.php")
    Call<GeneralModel> cutomerAddressCreate(@Field("hashkey") String hashkey, @Field("custid") String custid, @Field("firstname") String bill_firstname, @Field("lastname") String bill_lastname, @Field("street") String bill_street, @Field("streetone") String streetone, @Field("city") String bill_city, @Field("region") String bill_region, @Field("postcode") String bill_postcode, @Field("country_id") String bill_country_id, @Field("telephone") String bill_telephone, @Field("defaultbill") String defaultbill, @Field("defaultship") String defaultship);

    @FormUrlEncoded
    @POST("services.php")
    Call<ServicesModel> services(@Field("hashkey") String hashkey);

    @FormUrlEncoded
    @POST("cutomerAddressDelete.php")
    Call<GeneralModel> cutomerAddressDelete(@Field("hashkey") String hashkey, @Field("id") String id);
    @FormUrlEncoded
    @POST("search")
    Call<SearchResponseModel> searchApi(@Field("search") String search);
    @POST("userupdate")
    Call<UserUpdateResponse>updateUser(@Query("name") String name,
                                       @Query("last_name") String lastname,
                                       @Query("address") String address,
                                       @Query("address1") String address1,
                                       @Query("mobile") String mobile,
                                       @Query("city") String city,
                                       @Query("state") String state,
                                       @Query("zip") String zip,
                                       @Query("tax_vat_number") String tax_vat_number
                                  );


}
