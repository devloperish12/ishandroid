package com.indiansmarthub.ish.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.indiansmarthub.ish.R;
import com.indiansmarthub.ish.adapter.CartAdapter;
import com.indiansmarthub.ish.adapter.CustomerAddressAdapter;
import com.indiansmarthub.ish.adapter.FinancialFluxAdapter;
import com.indiansmarthub.ish.fragments.Cart;
import com.indiansmarthub.ish.javaclass.RecyclerItemClickListener;
import com.indiansmarthub.ish.model.CustomerAddress;
import com.indiansmarthub.ish.model.CutomerAddressModel;
import com.indiansmarthub.ish.model.GeneralModel;
import com.indiansmarthub.ish.model.ModelResponseNewArrival;
import com.indiansmarthub.ish.retrofit.NetworkClient;
import com.indiansmarthub.ish.retrofit.NetworkService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerAddressActivity extends AppCompatActivity {
TextView mAddNewAddress;
RecyclerView mRecyclerView;
    SharedPreferences prefManager;
    private ArrayList<CustomerAddress> CustomerAddressArrayList;
    private ProgressDialog progressDialog;
    private String cartAmount;
    private CustomerAddressAdapter customerAddressAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_address);

        prefManager = getSharedPreferences("ISH", MODE_PRIVATE);
        mAddNewAddress = findViewById(R.id.addnewaddress);
        mRecyclerView = findViewById(R.id.rvcustomerAddress);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getAdresses();
        //callCustomerAddressApi();
        mAddNewAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerAddressActivity.this, CheckoutBillingAddress.class);
                startActivity(intent);
            }
        });
    }
    public void getAdresses()
    {
        final String token=prefManager.getString("cust_id","");
        //final ProgressDialog dialog = ProgressDialog.show(getActivity(), "", "Proccessing....Please wait");

        final String url="http://52.66.136.244/api/v1/address";
        RequestQueue requestQueue = Volley.newRequestQueue(CustomerAddressActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject object= null;
                try {

                    object = new JSONObject(response);
                    String status=object.getString("success");
                   final JSONArray getAddress=object.getJSONArray("address");
                    // JSONArray jsonArray=object.getJSONArray("details");
                    if(status.equals("1")) {
                        CustomerAddressArrayList = new ArrayList<>();
                        CustomerAddressArrayList.clear();
                        for(int a=0;a<getAddress.length();a++) {
                            JSONObject jsonObject=getAddress.getJSONObject(a);
                            CustomerAddress customerAddress=new CustomerAddress();
                            customerAddress.setFirstname(jsonObject.getString("name"));
                            customerAddress.setCity(jsonObject.getString("city"));
                            customerAddress.setCountryId(jsonObject.getString("country"));
                            customerAddress.setLastname(jsonObject.getString("lname"));
                            customerAddress.setTelephone(jsonObject.getString("contact_number"));
                            customerAddress.setStreet(jsonObject.getString("landmark"));
                            customerAddress.setPostcode(jsonObject.getString("pincode"));
                            CustomerAddressArrayList.add(customerAddress);
                        }
                        customerAddressAdapter = new CustomerAddressAdapter(CustomerAddressActivity.this, CustomerAddressArrayList);
                        mRecyclerView.setLayoutManager(new LinearLayoutManager(CustomerAddressActivity.this, LinearLayoutManager.VERTICAL, false));
                        mRecyclerView.setAdapter(customerAddressAdapter);

                        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(CustomerAddressActivity.this, mRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                //   Intent i = new Intent(CustomerAddressActivity.this, PaymentMethod.class);

                                String fName = CustomerAddressArrayList.get(position).getFirstname();
                                String lName = CustomerAddressArrayList.get(position).getLastname();
                                String street = CustomerAddressArrayList.get(position).getStreet();
                                String city = CustomerAddressArrayList.get(position).getCity();
                                String region = CustomerAddressArrayList.get(position).getRegion();
                                String postcode = CustomerAddressArrayList.get(position).getPostcode();
                                String country = CustomerAddressArrayList.get(position).getCountryId();
                                String telephone_ = CustomerAddressArrayList.get(position).getTelephone();
                                try {
                                    String id = getAddress.getJSONObject(position).getString("id");
                                    //deleteAddress(id);
                                    Intent intent = new Intent(CustomerAddressActivity.this, PaymentMethod.class);
                                   intent.putExtra("id",id);
                                    startActivity(intent);
                                }catch (Exception e)
                                {
                                    e.printStackTrace();
                                }

                               // setbillingAddress(fName, lName, street, city, region, postcode, country, telephone_);
                                Log.e("tag_e",fName+","+ lName+","+ street+","+ city+","+ region+","+ postcode+","+ country+","+ telephone_);
                                      /*  i.putExtra("image", finalCreativeGallery.get(position).getImg());
                                        i.putExtra("title", finalCreativeGallery.get(position).getName());
                                        i.putExtra("date", finalCreativeGallery.get(position).getDate());
                                        i.putExtra("contain", finalCreativeGallery.get(position).getContent());*/
                                //    startActivity(i);
                            }

                            @Override
                            public void onLongItemClick(View view, final int position) {
                                final String id = String.valueOf(CustomerAddressArrayList.get(position).getId());
                                Log.e("tag_id", id);

                                AlertDialog.Builder builder;
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                    builder = new AlertDialog.Builder(CustomerAddressActivity.this);
                                } else {
                                    builder = new AlertDialog.Builder(CustomerAddressActivity.this);
                                }

                                builder.setTitle("Address Delete")
                                        .setMessage("Are you sure you want to Delete the Address?")
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                /*callcustomerAddressDeleteApi(CustomerAddressArrayList,position, id);*/
                                                //Toast.makeText(getActivity(),"Data has been Post Successfully",Toast.LENGTH_SHORT).show();
                                               try {
                                                   String id = getAddress.getJSONObject(position).getString("id");
                                                   deleteAddress(id);
                                               }catch (Exception e)
                                               {
                                                   e.printStackTrace();
                                               }

                                            }
                                        })
                                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                // do nothing
                                            }
                                        })
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .show();



                            }
                        }));

//                    cart=""+getCarts.length();
//                    getWishList();
                    }
                    else
                    {

                        Toast.makeText(CustomerAddressActivity.this, "no address found", Toast.LENGTH_SHORT).show();
                        //   dialog.dismiss();
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
                Log.d("Login",""+error.getCause());
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

/*

    private void callCustomerAddressApi() {
        progressDialog = new ProgressDialog(CustomerAddressActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

            NetworkService networkService = NetworkClient.getClient().create(NetworkService.class);
            Call<CutomerAddressModel> categoriesCall = networkService.getcustomerAddress("NXqXAgFZA0jiWp5t6+=lGpgWJXEkbo", prefManager.getString("cust_id", ""));

            categoriesCall.enqueue(new Callback<CutomerAddressModel>() {
                @Override
                public void onResponse(final Call<CutomerAddressModel> call, Response<CutomerAddressModel> response) {
                    if (response.body() != null) {
                        Log.e("tag_st", String.valueOf(response.body().getSuccess()));
                        if (String.valueOf(response.body().getSuccess()).equals("1")) {
                            if (response.body().getCustomerAddress() != null) {
                                progressDialog.dismiss();
                                CustomerAddressArrayList = new ArrayList<>();

                                CustomerAddressArrayList.clear();

                                CustomerAddressArrayList.addAll(response.body().getCustomerAddress());

                                customerAddressAdapter = new CustomerAddressAdapter(CustomerAddressActivity.this, CustomerAddressArrayList);
                                mRecyclerView.setLayoutManager(new LinearLayoutManager(CustomerAddressActivity.this, LinearLayoutManager.VERTICAL, false));
                                mRecyclerView.setAdapter(customerAddressAdapter);

                                mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(CustomerAddressActivity.this, mRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(View view, int position) {
                                     //   Intent i = new Intent(CustomerAddressActivity.this, PaymentMethod.class);

                                        String fName = CustomerAddressArrayList.get(position).getFirstname();
                                        String lName = CustomerAddressArrayList.get(position).getLastname();
                                        String street = CustomerAddressArrayList.get(position).getStreet();
                                        String city = CustomerAddressArrayList.get(position).getCity();
                                        String region = CustomerAddressArrayList.get(position).getRegion();
                                        String postcode = CustomerAddressArrayList.get(position).getPostcode();
                                        String country = CustomerAddressArrayList.get(position).getCountryId();
                                        String telephone_ = CustomerAddressArrayList.get(position).getTelephone();
                                        Intent intent = new Intent(CustomerAddressActivity.this, PaymentMethod.class);
                                        startActivity(intent);
                                       // getBillingAddress(fName, lName, street, city, region, postcode, country, telephone_);
                                      //  Log.e("tag_e",fName+","+ lName+","+ street+","+ city+","+ region+","+ postcode+","+ country+","+ telephone_);
                                      */
/*  i.putExtra("image", finalCreativeGallery.get(position).getImg());
                                        i.putExtra("title", finalCreativeGallery.get(position).getName());
                                        i.putExtra("date", finalCreativeGallery.get(position).getDate());
                                        i.putExtra("contain", finalCreativeGallery.get(position).getContent());*//*

                                    //    startActivity(i);
                                    }

                                    @Override
                                    public void onLongItemClick(View view, final int position) {
                                        final String id = String.valueOf(CustomerAddressArrayList.get(position).getId());
                                        Log.e("tag_id", id);

                                        AlertDialog.Builder builder;
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                            builder = new AlertDialog.Builder(CustomerAddressActivity.this);
                                        } else {
                                            builder = new AlertDialog.Builder(CustomerAddressActivity.this);
                                        }

                                        builder.setTitle("Address Delete")
                                                .setMessage("Are you sure you want to Delete the Address?")
                                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        callcustomerAddressDeleteApi(CustomerAddressArrayList,position, id);
                                                        //Toast.makeText(getActivity(),"Data has been Post Successfully",Toast.LENGTH_SHORT).show();
                                                    }
                                                })
                                                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        // do nothing
                                                    }
                                                })
                                                .setIcon(android.R.drawable.ic_dialog_alert)
                                                .show();



                                    }
                                }));


                            }
                        } else {

                            progressDialog.dismiss();
                            Log.e("tag_el", response.body().getMessage());
                           // Toast.makeText(CustomerAddressActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            Toast.makeText(CustomerAddressActivity.this, "Please Add New Address", Toast.LENGTH_SHORT).show();

                        }
                    } else {
                        progressDialog.dismiss();
                      //  Toast.makeText(CustomerAddressActivity.this, "Something went wrong...!!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<CutomerAddressModel> call, Throwable t) {
                    progressDialog.dismiss();
                  //  Toast.makeText(CustomerAddressActivity.this, "Something went wrong...!!", Toast.LENGTH_SHORT).show();


                }
            });

    }

    private void getBillingAddress(String fName, String lName, String street, String city, String region, String postcode, String country, String telephone_) {
        progressDialog = new ProgressDialog(CustomerAddressActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        NetworkService networkService = NetworkClient.getClient().create(NetworkService.class);
        Call<GeneralModel> responseCall = networkService.getBillingAddress("NXqXAgFZA0jiWp5t6+=lGpgWJXEkbo", prefManager.getString("cust_id", ""), fName, lName, street, city, region, postcode, country, telephone_, "setaddress");
        responseCall.enqueue(new Callback<GeneralModel>() {
            @Override
            public void onResponse(Call<GeneralModel> call, Response<GeneralModel> response) {
                if (response.body() != null) {
                    //setBothBillingAddress(strretAddress);

                    if (response.body().getMessage().equals("successfully checkout")){
                        setShippingMethod();
                    }

                    progressDialog.dismiss();
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(CustomerAddressActivity.this, "Something went wrong...!!", Toast.LENGTH_SHORT).show();


                }
            }
            @Override
            public void onFailure(Call<GeneralModel> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(CustomerAddressActivity.this, "Something went wrong...!!", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void setShippingMethod() {
        progressDialog = new ProgressDialog(CustomerAddressActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        NetworkService networkService = NetworkClient.getClient().create(NetworkService.class);
        Call<GeneralModel> responseCall = networkService.setShippingMethod("NXqXAgFZA0jiWp5t6+=lGpgWJXEkbo", prefManager.getString("cust_id", ""), "setshiping", "freeshipping_freeshipping");

        responseCall.enqueue(new Callback<GeneralModel>() {
            @Override
            public void onResponse(Call<GeneralModel> call, Response<GeneralModel> response) {

                if (response.body() != null) {
                    if (response.body().getSuccess().equals("1")) {
                        Intent intent = new Intent(CustomerAddressActivity.this, PaymentMethod.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(CustomerAddressActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                } else {
                    progressDialog.dismiss();
                  //  Toast.makeText(CustomerAddressActivity.this, "Something went wrong...!!", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<GeneralModel> call, Throwable t) {

                progressDialog.dismiss();
                Toast.makeText(CustomerAddressActivity.this, "Something went wrong...!!", Toast.LENGTH_SHORT).show();

            }
        });
    }


    // delete address
    private void callcustomerAddressDeleteApi(ArrayList<CustomerAddress> customerAddressArrayList, final int position, String id) {

        NetworkService networkService = NetworkClient.getClient().create(NetworkService.class);
        Call<GeneralModel> responseCall = networkService.cutomerAddressDelete("NXqXAgFZA0jiWp5t6+=lGpgWJXEkbo", id);

        responseCall.enqueue(new Callback<GeneralModel>() {
            @Override
            public void onResponse(Call<GeneralModel> call, Response<GeneralModel> response) {
                if (response.body() != null) {
                    if (response.body().getSuccess().equals("1")) {

                        if (response.body().getMessage().equals("successfully delete")){
                            CustomerAddressArrayList.remove(position);
                            customerAddressAdapter.notifyDataSetChanged();
                        }


                        // Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_LONG).show();
                    } else {
                        //  customLoadingDialog.dismiss();
                     //   Toast.makeText(CustomerAddressActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    }
                } else {
                    //  customLoadingDialog.dismiss();
                 //   Toast.makeText(context, "Something went wrong...!!", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<GeneralModel> call, Throwable t) {

                //  customLoadingDialog.dismiss();
            //    Toast.makeText(context, "Something went wrong...!!", Toast.LENGTH_SHORT).show();

            }
        });

    }
*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }
       
        return super.onOptionsItemSelected(item);
    }

public void deleteAddress(String id)
{
    final String token=prefManager.getString("cust_id","");
    //final ProgressDialog dialog = ProgressDialog.show(getActivity(), "", "Proccessing....Please wait");

    final String url="http://52.66.136.244/api/v1/address/"+id;
    RequestQueue requestQueue = Volley.newRequestQueue(CustomerAddressActivity.this);
    StringRequest stringRequest = new StringRequest(Request.Method.DELETE, url, new com.android.volley.Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            JSONObject object= null;
            try {

                object = new JSONObject(response);
                String status=object.getString("success");
               // JSONArray getCarts=object.getJSONArray("cart");
                // JSONArray jsonArray=object.getJSONArray("details");
                if(!status.equals("")) {
                    Intent intent = new Intent(CustomerAddressActivity.this, CustomerAddressActivity.class);
                    startActivity(intent);                }
                else
                {

                    Toast.makeText(CustomerAddressActivity.this, "alreay in wishlist", Toast.LENGTH_SHORT).show();
                    //   dialog.dismiss();
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
            Log.d("Login",""+error.getCause());
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

    public void setbillingAddress(String fName, String lName,String street,String city,String region,String postcode,String country,String telephone_)
    {
        final String token=prefManager.getString("cust_id","");
        //final ProgressDialog dialog = ProgressDialog.show(getActivity(), "", "Proccessing....Please wait");

        final String url="http://52.66.136.244/api/v1/address?name="+fName+"&lname="+lName+
                "&pincode="+postcode+"&landmark="+street+"" +
                "&locallity="+street+"&address="+street+"" + "&address1=123456&city="+city+"&" +
                "state=india&zip=123456&contact_number="+telephone_+"&country="+country+"&contact_number_attrenate="+telephone_;
        RequestQueue requestQueue = Volley.newRequestQueue(CustomerAddressActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject object= null;
                try {

                    object = new JSONObject(response);
                    String status=object.getString("success");
                    // JSONArray getCarts=object.getJSONArray("cart");
                    // JSONArray jsonArray=object.getJSONArray("details");
                    if(!status.equals("")) {
                        Intent intent = new Intent(CustomerAddressActivity.this, PaymentMethod.class);
                        startActivity(intent);                }
                    else
                    {

                        Toast.makeText(CustomerAddressActivity.this, "alreay in wishlist", Toast.LENGTH_SHORT).show();
                        //   dialog.dismiss();
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
                Log.d("Login",""+error.getCause());
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




}


