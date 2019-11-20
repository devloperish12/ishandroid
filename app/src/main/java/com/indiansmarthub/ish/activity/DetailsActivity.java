package com.indiansmarthub.ish.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpResponse;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.indiansmarthub.ish.MainActivity;
import com.indiansmarthub.ish.R;
import com.indiansmarthub.ish.custom.GeneralCode;
import com.indiansmarthub.ish.javaclass.Add_Remove_WishList;
import com.indiansmarthub.ish.javaclass.BottomMenuHelper;
import com.indiansmarthub.ish.model.GeneralModel;
import com.indiansmarthub.ish.model.ModelProductDetails;
import com.indiansmarthub.ish.model.ModelResponseProductDetails;
import com.indiansmarthub.ish.retrofit.NetworkClient;
import com.indiansmarthub.ish.retrofit.NetworkService;
import com.indiansmarthub.ish.sqlite.DatabaseHandler;
import com.squareup.picasso.Picasso;

import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import at.blogc.android.views.ExpandableTextView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.indiansmarthub.ish.MainActivity.mBottomNavigationView;
import static com.indiansmarthub.ish.custom.GeneralCode.encodeToBase64;

public class DetailsActivity extends AppCompatActivity {

    String product_id;
    int wishlist_status;
    //  CircularTextView cartCount;
    private Toolbar mToolbar;
    ImageView button_toggle;
    // ExpandableTextView tvDescription;
    private ImageView ivProductImage, minus_bestProduct, plus_bestProduct, cart_icon_home, ivWishListRemovedetails, ivWishListAdddetails;
    private TextView tvProductName, tvProductPrice, enquiry, qty_addtocart_bestProduct, addTocart_ProductDetails, tvbuynow, tvVenderName;
    private LinearLayout layoutAddTocartBest;
    private static final int PICK_FILE_REQUEST = 1;
    List<ModelProductDetails> getProductDetailsModel;
    LinearLayoutManager linearLayoutManagerPrroductList;
    SharedPreferences prefManager;
    DatabaseHandler databaseHandler;
    ExpandableTextView tvDescription;
    private ProgressDialog progressDialog;
    private String product_Id;
    double cartAmount = 0;
    ArrayList<String> wishlist;
    ArrayList<String> cartlist;
    AlertDialog passwordDialog = null;

    SharedPreferences.Editor editor;
    JSONObject jsonObject;
    Bitmap documentbit = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

      /*  mToolbar = findViewById(R.id.toolBar);
        mToolbar.setTitle("Product Details");
        mToolbar.setNavigationIcon(R.drawable.left_arrow);
        setSupportActionBar(mToolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       */

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        prefManager = DetailsActivity.this.getSharedPreferences("ISH", this.MODE_PRIVATE);
        getWishList();
        getcarts();
        if (getIntent().hasExtra("product_id")) {
            try {
                init();
                product_id = getIntent().getStringExtra("product_id");
                try
                {
                    String jsoninst = getIntent().getStringExtra("detail");
                    jsonObject = new JSONObject(jsoninst);
                    if(jsonObject.length()==0)
                    {
                          getDetail(product_id);


                    }
                }catch (Exception e)
                {

                    e.printStackTrace();
                }
//
                setDetail();
                //  getDetail(product_id);
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
        // init();
        if (GeneralCode.isConnectingToInternet(DetailsActivity.this)) {
            // getDetailsProduct();


        } else {
            GeneralCode.showDialog(DetailsActivity.this);
        }

        ivWishListRemovedetails.setOnClickListener(view -> {
            final String token = prefManager.getString("cust_id", "");
            if (token.equalsIgnoreCase("")) {

            } else {
//                if (wishlist.contains(product_id)) {
//                    addWishList();
//                } else {
                    addWishList();
              //  }

            }
        });
        ivWishListAdddetails.setOnClickListener(view -> {
            final String token = prefManager.getString("cust_id", "");
            if (token.equalsIgnoreCase("")) {

            } else {

                addWishList();
            }
        });

/*
        cartCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DetailsActivity.this, CartActivity.class));
            }
        });
*/
/*
        cart_icon_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DetailsActivity.this, CartActivity.class));
            }
        });
*/
/*
        tvbuynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
          //      startActivity(new Intent(DetailsActivity.this, CartActivity.class));
            }
        });
*/


        addTocart_ProductDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //    if (prefManager.getBoolean("isMembership", true)) {
                if (!prefManager.getString("cust_id", "").isEmpty()) {
                    if (addTocart_ProductDetails.getText().toString().equalsIgnoreCase(("ADD TO CART"))) {
                        addTocart(product_id);
                        // getDetailsProduct();

                    } else if (addTocart_ProductDetails.getText().toString().equalsIgnoreCase(("Go To Cart"))) {
                        Intent intent = new Intent(DetailsActivity.this, CartActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    } else if (addTocart_ProductDetails.getText().toString().equalsIgnoreCase("Send Enquiry")) {
                        showEnquiry();
                    }

                } else {


                    if (addTocart_ProductDetails.getText().toString().equalsIgnoreCase("Send Enquiry")) {
                        showEnquiry();
                    } else {
                        Intent intent = new Intent(DetailsActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                    //   startActivity(new Intent(DetailsActivity.this, LoginActivity.class));
                }
                //  }
            }
        });


    }

    private void init() {
        databaseHandler = new DatabaseHandler(DetailsActivity.this);
        getProductDetailsModel = new ArrayList<>();
        enquiry = findViewById(R.id.enquiry);
        enquiry.setVisibility(View.GONE);
        button_toggle = findViewById(R.id.button_toggle);
        tvbuynow = findViewById(R.id.tvbuynow);
        tvVenderName = findViewById(R.id.tvVenderName);
        //  cartCount = findViewById(R.id.cartCount);
        //  cart_icon_home = findViewById(R.id.cart_icon_home);
        ivProductImage = findViewById(R.id.ivProductImage);
        minus_bestProduct = findViewById(R.id.minus_bestProduct);
        plus_bestProduct = findViewById(R.id.plus_bestProduct);
        tvProductName = findViewById(R.id.tvProductName);
        tvProductPrice = findViewById(R.id.tvProductPrice);
        tvDescription = findViewById(R.id.tvDescription_);
        qty_addtocart_bestProduct = findViewById(R.id.qty_addtocart_bestProduct);
        addTocart_ProductDetails = findViewById(R.id.addTocart_ProductDetails);
        layoutAddTocartBest = findViewById(R.id.layoutAddTocartBest);
        ivWishListRemovedetails = findViewById(R.id.ivWishListRemovedetails);
        ivWishListAdddetails = findViewById(R.id.ivWishListAdddetails);

        // tvDescriptionivWishListAdddetails.setAnimationDuration(750L);

        // set interpolators for both expanding and collapsing animations
        tvDescription.setInterpolator(new OvershootInterpolator());
        try {
//            JSONObject disjson = jsonObject.getJSONObject("category");
//            String discription=disjson.getString("description");
//            tvDescription.setText(discription);
        } catch (Exception e) {
            e.printStackTrace();
        }

// or set them separately
        tvDescription.setExpandInterpolator(new OvershootInterpolator());
        tvDescription.setCollapseInterpolator(new OvershootInterpolator());

// toggle the ExpandableTextView
        button_toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                //tvDescription.setText(tvDescription.isExpanded() ? R.string.expand : R.string.collapse);
                tvDescription.toggle();
            }
        });

// but, you can also do the checks yourself
        button_toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (tvDescription.isExpanded()) {

                    tvDescription.collapse();
                    button_toggle.setImageResource(R.drawable.arrow_down);

                } else {
                    tvDescription.expand();
                    button_toggle.setImageResource(R.drawable.arrow_up);

                }
            }
        });
        enquiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEnquiry();
            }
        });

// listen for expand / collapse events


    }


    private void getDetailsProduct() {

        progressDialog = new ProgressDialog(DetailsActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        NetworkService networkService = NetworkClient.getClient().create(NetworkService.class);
        Call<ModelResponseProductDetails> categoriesCall = networkService.getProductDetials("NXqXAgFZA0jiWp5t6+=lGpgWJXEkbo", product_id, prefManager.getString("cust_id", ""));

        categoriesCall.enqueue(new Callback<ModelResponseProductDetails>() {
            @Override
            public void onResponse(final Call<ModelResponseProductDetails> call, Response<ModelResponseProductDetails> response) {
                if (response.body() != null) {
                    if (response.body().getSuccess().equals("1")) {
                        if (response.body().getProduct() != null) {
                            linearLayoutManagerPrroductList = new LinearLayoutManager(DetailsActivity.this, LinearLayoutManager.VERTICAL, false);

                            Log.e("tag_res", response.body().getMessage());
                            getProductDetailsModel.clear();
                            getProductDetailsModel = response.body().getProduct();

                            editor = prefManager.edit();
                            editor.putString("id", getProductDetailsModel.get(0).getId());
                            editor.putString("sku", getProductDetailsModel.get(0).getSku());

                            Log.e("id_sku", getProductDetailsModel.get(0).getId());
                            editor.apply();

                            // check product is available in sqlite or not
                            if (databaseHandler.checkProduct(getProductDetailsModel.get(0).getId(), getProductDetailsModel.get(0).getSku())) {

                                addTocart_ProductDetails.setBackgroundResource(R.color.colorGreen);
                                addTocart_ProductDetails.setText("Go To Cart");

                            } else {
                                addTocart_ProductDetails.setText("Add To Cart");
                            }


                            tvProductName.setText(getProductDetailsModel.get(0).getName());
                            tvProductPrice.setText("₹ " + getProductDetailsModel.get(0).getPrice());
                            String val = String.format("%.0f", Double.valueOf(getProductDetailsModel.get(0).getPrice()));
                            Log.e("tag_price", getProductDetailsModel.get(0).getPrice());
                            Log.e("tag_price_afterdecimal", val);


                            String space = "";
                            String value1 = "\\r\\n";
                            String value2 = "&nbsp;";
                            String value3 = "&rsquo;";


                            String shortdescription = getProductDetailsModel.get(0).getShortdescription();

                            shortdescription = shortdescription.replace(value1, space).replace(value2, space).replace(value3, space);

                            tvDescription.setText(shortdescription);
                            tvVenderName.setText("  Indian Smart Hub");
                            wishlist_status = Integer.parseInt(getProductDetailsModel.get(0).getWishlist());
                            product_Id = getProductDetailsModel.get(0).getId();
                            Picasso.with(DetailsActivity.this)
                                    .load(getProductDetailsModel.get(0).getImage())
                                    .error(R.drawable.logo)
                                    .into(ivProductImage);
                            if (prefManager.getString("cust_id", "").isEmpty()) {
                                ivWishListRemovedetails.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        startActivity(new Intent(DetailsActivity.this, LoginActivity.class));
                                    }
                                });
                            }
                            if (wishlist_status == 0) {
                                ivWishListRemovedetails.setVisibility(View.VISIBLE);
                                ivWishListAdddetails.setVisibility(View.GONE);
                                ivWishListRemovedetails.setOnClickListener(new View.OnClickListener()
                                {
                                    @Override
                                    public void onClick (View view){
                                    Add_Remove_WishList.getAddWishList(DetailsActivity.this, getProductDetailsModel.get(0).getId(), getProductDetailsModel.get(0).getSku(), prefManager.getString("cust_id", ""));
                                    ivWishListAdddetails.setVisibility(View.VISIBLE);
                                    ivWishListRemovedetails.setVisibility(View.GONE);
                                }
                                });

                            } else if (wishlist_status == 1) {
                                ivWishListAdddetails.setVisibility(View.VISIBLE);
                                ivWishListRemovedetails.setVisibility(View.GONE);
                                ivWishListAdddetails.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Add_Remove_WishList.getRemoveWishList(DetailsActivity.this, getProductDetailsModel.get(0).getId(), prefManager.getString("cust_id", ""));
                                        ivWishListRemovedetails.setVisibility(View.VISIBLE);
                                        ivWishListAdddetails.setVisibility(View.GONE);
                                    }
                                });
                            }


                            final String qty = getProductDetailsModel.get(0).getCart();
                           /* if (qty.equals("0")) {
                              //  qty_addtocart_bestProduct.setText("1");
                                addTocart_ProductDetails.setText("ADD TO CART");
                                addTocart_ProductDetails.setVisibility(View.VISIBLE);

                            } else {

                             //   qty_addtocart_bestProduct.setText(qty + "");
                                addTocart_ProductDetails.setText("ALLREADY IN CART");

                            }*/

                            addTocart_ProductDetails.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    if (!prefManager.getString("cust_id", "").isEmpty()) {
                                        if (addTocart_ProductDetails.getText().toString().equals("Add To Cart")) {
                                            generateArray();
                                        } else if (addTocart_ProductDetails.getText().toString().equals("Go To Cart")) {
                                            Intent intent = new Intent(DetailsActivity.this, CartActivity.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);
                                            finish();

                                        }

                                    } else {

                                        if (databaseHandler.checkProduct(getProductDetailsModel.get(0).getId(), getProductDetailsModel.get(0).getSku())) {

                                            addTocart_ProductDetails.setBackgroundResource(R.color.colorGreen);
                                            addTocart_ProductDetails.setText("Go To Cart");
                                            if (addTocart_ProductDetails.getText().toString().equals("Go To Cart")) {
                                                Intent intent = new Intent(DetailsActivity.this, CartActivity.class);
                                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                startActivity(intent);
                                                finish();
                                            }


                                        } else {
                                            Bitmap bitmap = GeneralCode.drawableToBitmap(ivProductImage.getDrawable());
                                            String encoded = "";
                                            if (bitmap != null) {
                                                encoded = encodeToBase64(bitmap, Bitmap.CompressFormat.WEBP, 100);
                                            }
                                            long ans = databaseHandler.addToCart(getProductDetailsModel.get(0).getId(), "1", getProductDetailsModel.get(0).getSku(), getProductDetailsModel.get(0).getPrice(), getProductDetailsModel.get(0).getName(), encoded);
                                            if (ans != -1) {
                                                getCount();

                                                addTocart_ProductDetails.setText("ADDING...");
                                                //addTocart_ProductDetails.setVisibility(View.GONE);
                                                //    layoutAddTocartBest.setVisibility(View.VISIBLE);
                                                getDetailsProduct();
                                            } else {
                                                addTocart_ProductDetails.setBackgroundResource(R.color.colorGreen);
                                                addTocart_ProductDetails.setText("Go To Cart");
                                                Toast.makeText(DetailsActivity.this, "Something went wrong,please try again...!!", Toast.LENGTH_SHORT).show();

                                                if (addTocart_ProductDetails.getText().toString().equals("Go To Cart")) {
                                                    Intent intent = new Intent(DetailsActivity.this, CartActivity.class);
                                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                    startActivity(intent);
                                                    finish();
                                                }

                                            }
                                        }
                                        // startActivity(new Intent(DetailsActivity.this, LoginActivity.class));
                                    }

                                }

                            });


                            progressDialog.dismiss();

                        }
                    } else {
                        progressDialog.dismiss();
                        //   Toast.makeText(DetailsActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    }
                } else {
                    progressDialog.dismiss();
                    //  Toast.makeText(DetailsActivity.this, "Something went wrong...!!", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ModelResponseProductDetails> call, Throwable t) {
                progressDialog.dismiss();
                //  Toast.makeText(DetailsActivity.this, "Something went wrong...!!", Toast.LENGTH_SHORT).show();

            }
        });

    }

    public void setDetail() {
        try {
            String imageurl;
            tvProductName.setText(jsonObject.getString("name"));
            tvProductPrice.setText("₹ " + jsonObject.getString("price"));
            try
            {
             double amt=Double.parseDouble(jsonObject.getString("price"))  ;
             if(amt<1)
             {
                 tvProductPrice.setText("it");
             }
            }catch (Exception e)
            {
                e.printStackTrace();
            }
            tvDescription.setText(Html.fromHtml(jsonObject.getString("description")));
            try {
                JSONArray imgarr = jsonObject.getJSONArray("product_image");

                imageurl = "http://52.66.136.244/images/product_images/" + imgarr.getJSONObject(0).getString("url");
            }catch (Exception e)
            {
                imageurl=jsonObject.getString("image_url");
            }
            Picasso.with(DetailsActivity.this)
                    .load(imageurl)
                    .error(R.drawable.logo)
                    .into(ivProductImage);
            String type = jsonObject.getString("type");
            if (type.equalsIgnoreCase("enquiry")) {
                addTocart_ProductDetails.setBackgroundResource(R.color.colororangeLight);
                addTocart_ProductDetails.setText("SEND ENQUIRY");
            } else {

            }
        } catch (Exception e) {
            try {
                JSONObject jsonObject1 = jsonObject.getJSONObject("product_imagefirst");
                String url = "http://52.66.136.244/images/product_images/" + jsonObject1.getString("url");
                Picasso.with(DetailsActivity.this)
                        .load(url)
                        .error(R.drawable.logo)
                        .into(ivProductImage);
                String type = jsonObject.getString("type");
                if (!type.equalsIgnoreCase("sell")) {
                    addTocart_ProductDetails.setBackgroundResource(R.color.colororangeLight);
                    addTocart_ProductDetails.setText("SEND ENQUIRY");
                } else {

                }


            } catch (Exception e1) {
                e.printStackTrace();
            }

            e.printStackTrace();
        }


    }

    private void generateArray() {
        //   ArrayList<ModelLocalCart> localCarts = databaseHandler.getCart();
        String allinone = "";

        String datastring = "";

        String prodqty = "", prodid = "", configurable = "";
        allinone = "{\"custid\":\"" + prefManager.getString("cust_id", "") + "\",\"cartProduct\":[{";
                /*if (i != 0) {
                    datastring = datastring + ",{";
                }*/
        prodqty = prodqty + "\"prodqty\":" + "\"" + "1" + "\"";
        prodid = prodid + "\"prodid\":" + "\"" + product_Id + "\"";
        datastring = datastring + prodqty + "," + prodid;


        datastring = datastring + "}";

        allinone = allinone + datastring + "]}";
        Log.d("tag_allinone", allinone);


        placeOrder(allinone);

    }

    private void placeOrder(String json) {
        progressDialog = new ProgressDialog(DetailsActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        NetworkService networkService = NetworkClient.getClient().create(NetworkService.class);
        Call<GeneralModel> responseCall = networkService.addCart("NXqXAgFZA0jiWp5t6+=lGpgWJXEkbo", json);

        responseCall.enqueue(new Callback<GeneralModel>() {
            @Override
            public void onResponse(Call<GeneralModel> call, Response<GeneralModel> response) {

                if (response.body() != null) {

                    if (response.body().getSuccess().equals("1")) {
                        Toast.makeText(DetailsActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        String msg = response.body().getMessage();
                        if (msg.equals("successfully add to cart")) {
                            addTocart_ProductDetails.setBackgroundResource(R.color.colorGreen);
                            addTocart_ProductDetails.setText("Go To Cart");


                        }
                        Log.e("tag_re", msg);
                        progressDialog.dismiss();
                       /* if (prefManager.getBoolean("isMembership", false)) {


                            progressDialog.dismiss();

                        }*/
                    } else {
                        Toast.makeText(DetailsActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                } else {
                    Toast.makeText(DetailsActivity.this, "Something went wrong...!!", Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
                progressDialog.dismiss();
            }


            @Override
            public void onFailure(Call<GeneralModel> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(DetailsActivity.this, "Something went wrong...!!", Toast.LENGTH_LONG).show();
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }


    private void getCount() {
        int count = databaseHandler.cartCount();
        if (count > 0) {
            //   cartCount.setVisibility(View.VISIBLE);
            BottomMenuHelper.showBadge(DetailsActivity.this, mBottomNavigationView, R.id.navigation_cart, count + "");
        } else {
        }
    }

    public void addWishList() {
        final String token = prefManager.getString("cust_id", "");
        final ProgressDialog dialog = ProgressDialog.show(DetailsActivity.this, "", "Proccessing....Please wait");

        final String url = "http://52.66.136.244/api/v1/wishlist?qty=1&product_id=" + product_id;
        RequestQueue requestQueue = Volley.newRequestQueue(DetailsActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject object = null;
                try {
                    object = new JSONObject(response);
                    String status = object.getString("success");
                    String wishlistid=object.getString("wishlistid");
                    //JSONObject statusjson=new JSONObject(status);
                    // JSONArray jsonArray=object.getJSONArray("details");
                    if (!wishlistid.equals("0")) {
                        ivWishListRemovedetails.setBackgroundResource(R.drawable.wishlist_red);
                    } else {
                        ivWishListRemovedetails.setBackgroundResource(R.drawable.wishlist_outline);

                        Toast.makeText(DetailsActivity.this, "alreay in wishlist", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                    dialog.dismiss();
                    ///dialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                    dialog.dismiss();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Login", "" + error.getCause());
                dialog.dismiss();
                //dialog.dismiss();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", token);
                headers.put("Content-Type", "application/json");
                return headers;
            }

        };
        requestQueue.add(stringRequest);
    }

    //token=eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6ImJiYzUwOWY4ZmJlNDQ0ZDFkY2ZjY2Q1MWQ2NTZhMTI1NTM4ZGNhMGQ5NTY4ZGFhNTM2ZjY3NmIwNDIzOWU1YmM1MGVlZDRhMmQ0YWZkYzM4In0.eyJhdWQiOiIxIiwianRpIjoiYmJjNTA5ZjhmYmU0NDRkMWRjZmNjZDUxZDY1NmExMjU1MzhkY2EwZDk1NjhkYWE1MzZmNjc2YjA0MjM5ZTViYzUwZWVkNGEyZDRhZmRjMzgiLCJpYXQiOjE1NjgwMjcyMzcsIm5iZiI6MTU2ODAyNzIzNywiZXhwIjoxNTk5NjQ5NjM3LCJzdWIiOiI4Iiwic2NvcGVzIjpbXX0.Uw7MF6Vm1y-sDm1wKBf7A5K2oJya6u968BxoJS25q37hTzs57PT9WqDJ3r7PK4-hg0bFaGaN9so9YYPxktC8rvSSzo4Nd4fdf63GaGMExf69WDb1GGxWB1vhOKVRq13mrOsI6i0d6Q7GIApYLHz4SUFkUvCpx90Jh4DjWlay8qDdypC_xPOIWmlLk-oHzljYanpuNaD9xBagqquIwJdg6phUG7vN-SsUbHGOtZ-o29ZnjtyalBDl6MvoIKSXyd0nijQZPZdDzXbU4-2ewO9WD6we0uKCKSQXgpoudKV8wxB7BEYydUzYvsOTq8KDClJTo3PRgE0w5u3n8mu4ckUeHubalBedNdqjoDU7aqdmFMIcy77ps67i5SEQlgJmD1mkqlVs7O-4O4FcpHppDbE6NyNtACbRFONsFb_7riwzqKckETsaxJq7sHmTMx6VvmWAO7glduop5FkLvTcTQdMMcL82_3nCOlaLsAgW9oKNR-W2cxq_vPdZmkkQCLCu8Qi9cKY7HLMDSFjpg6QstTDcmWIjFYq6lufTiiRo2khM2YbXcuTA--iYzCOjLueugtUbHRdHMnGaSJaqgVlRZVoTHSDvB-gwRGh4aScm87fl5Uwwl0NdBbMOkxe8VulWecdHg-_NtL6sa5KmzXvRS7JDEczLtOVfxOIu8-vMFYiRNsU
    public void addTocart(String cusid) {
        final String token = prefManager.getString("cust_id", "");
        final ProgressDialog dialog = ProgressDialog.show(DetailsActivity.this, "", "Proccessing....Please wait");

        final String url = "http://52.66.136.244/api/v1/cart?qty=1&product_id=" + cusid;
        RequestQueue requestQueue = Volley.newRequestQueue(DetailsActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject object = null;
                try {

                    object = new JSONObject(response);
                    String status = object.getString("success");
                    //JSONObject statusjson=new JSONObject(status);
                    // JSONArray jsonArray=object.getJSONArray("details");
                    if (status.equals("1")) {
                        addTocart_ProductDetails.setBackgroundResource(R.color.colorGreen);
                        addTocart_ProductDetails.setText("Go To Cart");

                    } else {

                        Toast.makeText(DetailsActivity.this, "alreay in wishlist", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                    dialog.dismiss();
                    ///dialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                    dialog.dismiss();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Login", "" + error.getCause());
                dialog.dismiss();
                //dialog.dismiss();
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", token);
                headers.put("Content-Type", "application/json");
                return headers;
            }


        };
        requestQueue.add(stringRequest);
    }

    public void getWishList() {
        final String token = prefManager.getString("cust_id", "");
        // final ProgressDialog dialog = ProgressDialog.show(getContext(), "", "Proccessing....Please wait");

        final String url = "http://52.66.136.244/api/v1/wishlist";
        RequestQueue requestQueue = Volley.newRequestQueue(DetailsActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject object = null;
                try {
                    object = new JSONObject(response);
                    String status = object.getString("success");
                    wishlist = new ArrayList<>();
                    if (status.equals("1")) {
                        JSONArray jsonArray = object.getJSONArray("whislist");
                        for (int a = 0; a < jsonArray.length(); a++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(a);
                            wishlist.add(jsonObject.getString("product_id"));
                        }

                        if (wishlist.contains(product_id)) {
                            ivWishListRemovedetails.setBackgroundResource(R.drawable.wishlist_red);

                        } else {
                            ivWishListRemovedetails.setBackgroundResource(R.drawable.wishlist_outline);
                        }

                    } else {
                    }
                    // dialog.dismiss();
                    ///dialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                    // dialog.dismiss();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Login", "" + error.getCause());
                // dialog.dismiss();
                //dialog.dismiss();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", token);
                headers.put("Content-Type", "application/json");
                return headers;
            }

        };
        requestQueue.add(stringRequest);
    }

    public void getcarts() {
        final String token = prefManager.getString("cust_id", "");
        //final ProgressDialog dialog = ProgressDialog.show(getActivity(), "", "Proccessing....Please wait");

        final String url = "http://52.66.136.244/api/v1/cart";
        RequestQueue requestQueue = Volley.newRequestQueue(DetailsActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject object = null;
                try {

                    object = new JSONObject(response);
                    String status = object.getString("success");
                    JSONArray getCarts = object.getJSONArray("cart");
                    // JSONArray jsonArray=object.getJSONArray("details");
                    if (!status.equals("")) {
                        cartlist = new ArrayList<>();
                        for (int a = 0; a < getCarts.length(); a++) {
                            JSONObject jsonObject = getCarts.getJSONObject(a);
                            cartlist.add(jsonObject.getString("product_id"));
                        }
                    } else {

                        Toast.makeText(DetailsActivity.this, "alreay in wishlist", Toast.LENGTH_SHORT).show();
                        //   dialog.dismiss();
                    }
                    if (cartlist.contains(product_id)) {
                        addTocart_ProductDetails.setBackgroundResource(R.color.colorGreen);

                        addTocart_ProductDetails.setText("Go To Cart");
                    }
                    // dialog.dismiss();
                    ///dialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                    // dialog.dismiss();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Login", "" + error.getCause());
                //dialog.dismiss();
                //dialog.dismiss();
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", token);
                headers.put("Content-Type", "application/json");
                return headers;
            }


        };
        requestQueue.add(stringRequest);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == PICK_FILE_REQUEST) {
                if (data == null) {
                    //no data present
                    return;
                }
                try {
                    Uri selectedFileUri = data.getData();
                    documentbit = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedFileUri);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void showEnquiry() {
        String comment = "";

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View layout = inflater.inflate(R.layout.enqirelayout, (ViewGroup) findViewById(R.id.mainlay));
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final EditText nameed = (EditText) layout.findViewById(R.id.name);
        final EditText pred = (EditText) layout.findViewById(R.id.pname);
        final EditText mobileed = (EditText) layout.findViewById(R.id.mobile);
        final EditText cped = (EditText) layout.findViewById(R.id.customer);
        final EditText emailed = (EditText) layout.findViewById(R.id.email);
        final EditText commented = (EditText) layout.findViewById(R.id.comment);
        final Button post = (Button) layout.findViewById(R.id.send);
        final Button upload = (Button) layout.findViewById(R.id.file);
        final ImageView crossimg = (ImageView) layout.findViewById(R.id.close);
        crossimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passwordDialog.dismiss();
            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String cmed = commented.getText().toString();
                if (nameed.getText().toString().equalsIgnoreCase("")) {
                    nameed.setError("Require field");
                    return;
                }
                if (pred.getText().toString().equalsIgnoreCase("")) {
                    pred.setError("Require field");
                    return;
                }

                if (mobileed.getText().toString().equalsIgnoreCase("") && mobileed.getText().toString().length() != 10) {
                    nameed.setError("Enter valid mobile no");
                    return;
                }
                if (cped.getText().toString().equalsIgnoreCase("")) {
                    cped.setError("Require field");
                    return;
                }
                if (emailed.getText().toString().equalsIgnoreCase("")) {
                    emailed.setError("Require field");
                    return;
                } else {
                    if (documentbit == null) {
                        sendEnquiry(nameed.getText().toString(), pred.getText().toString(), mobileed.getText().toString(),
                                cped.getText().toString(), emailed.getText().toString(), commented.getText().toString());

                    } else {
                        sendEnquiryifdoc(nameed.getText().toString(), pred.getText().toString(), mobileed.getText().toString(),
                                cped.getText().toString(), emailed.getText().toString(), commented.getText().toString());
                    }
                }


            }
        });
        builder.setView(layout);
        passwordDialog = builder.create();
        passwordDialog.show();
    }

    private void showFileChooser() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, PICK_FILE_REQUEST);

    }

    public void sendEnquiryifdoc(final String name, final String product, final String mobile, final
    String custumer_product, final String email, final String comment) {
        final String cusid = prefManager.getString("cust_id", "");
        final String url = "http://52.66.136.244/api/v1/sendenquiry?name=" + name + "&product=" + product + "&" +
                "mobile=" + mobile + "&email=" + email + "&custom_product=" + custumer_product + "&comment=" + comment;

        //our custom volley request
        Volleymultipart volleyMultipartRequest = new Volleymultipart(Request.Method.POST, url,
                new com.android.volley.Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {
                            JSONObject object = new JSONObject(new String(response.data));
                            String status = object.getString("success");
                            if (status.equalsIgnoreCase("1")) {
                                Toast.makeText(DetailsActivity.this, "You enquiry successfully send", Toast.LENGTH_SHORT).show();
                                passwordDialog.dismiss();
                            } else {
                                Toast.makeText(DetailsActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                            }
                            Toast.makeText(getApplicationContext(), object.getString("message"), Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {

            /*
             * If you want to add more parameters with the image
             * you can do it here
             * here we have only one parameter with the image
             * which is tags
             * */
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("product", product);
                params.put("mobile", mobile);
                params.put("email", email);
                params.put("custom_product", custumer_product);
                params.put("shipmentid", "143");

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", cusid);
                headers.put("Content-Type", "application/json");
                return headers;
            }

            /*
             * Here we are passing image by renaming it with a unique name
             * */
            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                params.put("uploadedfile", new DataPart(imagename + ".png", getFileDataFromDrawable(documentbit)));

                return params;
            }
        };

        //adding the request to volley
        Volley.newRequestQueue(this).add(volleyMultipartRequest);
    }

    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public void sendEnquiry(final String name, final String product, final String mobile, final
    String custumer_product, final String email, final String comment) {
        final String token = prefManager.getString("cust_id", "");
        final String url = "http://52.66.136.244/api/v1/sendenquiry?name=" + name + "&product=" + product + "&" +
                "mobile=" + mobile + "&email=" + email + "&custom_product=" + custumer_product + "&comment=" + comment;
        RequestQueue requestQueue = Volley.newRequestQueue(DetailsActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject object = null;
                try {

                    object = new JSONObject(response);
                    String status = object.getString("success");
                    if (status.equalsIgnoreCase("1")) {
                        Toast.makeText(DetailsActivity.this, "You enquiry successfully send", Toast.LENGTH_SHORT).show();
                        passwordDialog.dismiss();
                    } else {
                        Toast.makeText(DetailsActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    // dialog.dismiss();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Login", "" + error.getCause());
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", token);
                headers.put("Content-Type", "application/json");
                return headers;
            }


        };
        requestQueue.add(stringRequest);
    }

    public void getDetail(String id) {
        final String token = prefManager.getString("cust_id", "");
        //final ProgressDialog dialog = ProgressDialog.show(getActivity(), "", "Proccessing....Please wait");

        final String url = "http://52.66.136.244/api/v1/product/" + id;
        RequestQueue requestQueue = Volley.newRequestQueue(DetailsActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject object = null;
                try {

                    object = new JSONObject(response);
                    String status = object.getString("success");
                    jsonObject = object.getJSONObject("products");
                    // JSONArray jsonArray=object.getJSONArray("details");
                    if (!status.equals("")) {
                        tvProductName.setText(jsonObject.getString("name"));
                        tvProductPrice.setText("₹ " + jsonObject.getString("price"));
                        JSONArray imgarr = jsonObject.getJSONArray("product_image");
                        String url = "http://52.66.136.244/images/product_images/" + imgarr.getJSONObject(0).getString("url");
                        Picasso.with(DetailsActivity.this)
                                .load(url)
                                .error(R.drawable.logo)
                                .into(ivProductImage);
                        tvDescription.setText(jsonObject.getString("description"));

                    } else {

                        Toast.makeText(DetailsActivity.this, "alreay in wishlist", Toast.LENGTH_SHORT).show();
                        //   dialog.dismiss();
                    }
                    if (cartlist.contains(product_id)) {
                        addTocart_ProductDetails.setBackgroundResource(R.color.colorGreen);

                        addTocart_ProductDetails.setText("Go To Cart");
                    }
                    try {
                        String type = jsonObject.getString("type");
                        if (!type.equalsIgnoreCase("sell")) {
                            addTocart_ProductDetails.setBackgroundResource(R.color.colororangeLight);
                            addTocart_ProductDetails.setText("SEND ENQUIRY");
                        } else {

                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    // dialog.dismiss();
                    ///dialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                    // dialog.dismiss();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Login", "" + error.getCause());
                //dialog.dismiss();
                //dialog.dismiss();
            }
        }) {


        };
        requestQueue.add(stringRequest);

    }
    @Override
    public void onBackPressed()
    {
      Intent intent=new Intent(DetailsActivity.this,MainActivity.class);
      finish();
      startActivity(intent);
       }

}


