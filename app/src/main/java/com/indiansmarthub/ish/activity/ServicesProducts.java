package com.indiansmarthub.ish.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
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
import com.indiansmarthub.ish.MainActivity;
import com.indiansmarthub.ish.R;
import com.indiansmarthub.ish.adapter.ServicesProductListAdapter;
import com.indiansmarthub.ish.adapter.ViewMoreProductListAdapter;
import com.indiansmarthub.ish.intrface.onItemCLickListner;
import com.indiansmarthub.ish.javaclass.Add_Remove_WishList;
import com.indiansmarthub.ish.model.CategoryProduct;
import com.indiansmarthub.ish.model.ModelFeaturedProduct;
import com.indiansmarthub.ish.model.ViewCategoryProducts;
import com.indiansmarthub.ish.retrofit.NetworkClient;
import com.indiansmarthub.ish.retrofit.NetworkService;
import com.indiansmarthub.ish.sqlite.DatabaseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServicesProducts extends AppCompatActivity {

    private String cate_id;
    private ProgressDialog progressDialog;
    private List<CategoryProduct> finalProductModel;

    RecyclerView rvProductList;
    private TextView noProdlistFound;

    SharedPreferences prefManager;
    private int product_id=0;
    private String cusid="";
    private int wishlist=0;
    private String cart="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services_products);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        prefManager = ServicesProducts.this.getSharedPreferences("ISH", MODE_PRIVATE);
        rvProductList = findViewById(R.id.rvProductList);
        noProdlistFound = findViewById(R.id.noProdlistFound);

        Intent intent = getIntent();
        if (intent != null){
            cate_id = intent.getStringExtra("cate_id");
        }

       /* if (prefManager.getString("cust_id", "").isEmpty()) {
            startActivity(new Intent(ServicesProducts.this, LoginActivity.class));
        } else {
*/
           // getview_category_products(cate_id);
        getCategoryDetail(cate_id);
      //  }

        Log.e("tag_cat", cate_id);

    }

  /*  private void getview_category_products(String cate_id) {
        Log.e("tag_", "tag_");
        progressDialog = new ProgressDialog(ServicesProducts.this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        NetworkService networkService = NetworkClient.getClient().create(NetworkService.class);
        Call<ViewCategoryProducts> categoriesCall = networkService.getservicesproduct("NXqXAgFZA0jiWp5t6+=lGpgWJXEkbo",cate_id, prefManager.getString("cust_id", ""));
        categoriesCall.enqueue(new Callback<ViewCategoryProducts>() {
            @Override
            public void onResponse(Call<ViewCategoryProducts> call, Response<ViewCategoryProducts> response) {
                Log.e("tag_", "tag_1");
                if (response.body() != null) {
                    if (response.body().getSuccess().equals("1")) {
                        if (response.body().getCategoryProduct() != null) {
                            progressDialog.dismiss();
                            //linearLayoutManagerPrroductList = new LinearLayoutManager(ProductListActivity.this, LinearLayoutManager.VERTICAL, false);

                            finalProductModel = new ArrayList<>();
                            rvProductList.setVisibility(View.VISIBLE);
                            noProdlistFound.setVisibility(View.GONE);

                            Log.e("tag_res", response.message());

                            finalProductModel.clear();

                            finalProductModel.addAll(response.body().getCategoryProduct());

                            // call adapter
                            GridLayoutManager gridLayoutManager = new GridLayoutManager(ServicesProducts.this,2);
                            rvProductList.setLayoutManager(gridLayoutManager);
//                            ServicesProductListAdapter adapter = new ServicesProductListAdapter(ServicesProducts.this, finalProductModel);
//                            rvProductList.setAdapter(adapter);
                            //


                            progressDialog.dismiss();


                        }
                    } else {
                        rvProductList.setVisibility(View.GONE);
                        noProdlistFound.setVisibility(View.VISIBLE);
                        progressDialog.dismiss();
                        Toast.makeText(ServicesProducts.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    }
                } else {
                    rvProductList.setVisibility(View.GONE);
                    noProdlistFound.setVisibility(View.VISIBLE);
                    progressDialog.dismiss();
                    Toast.makeText(ServicesProducts.this, "Something went wrong...!!", Toast.LENGTH_SHORT).show();


                }
            }

            @Override
            public void onFailure(Call<ViewCategoryProducts> call, Throwable t) {
                progressDialog.dismiss();
                rvProductList.setVisibility(View.GONE);
                noProdlistFound.setVisibility(View.VISIBLE);
                Toast.makeText(ServicesProducts.this, "Something went wrong...!!", Toast.LENGTH_SHORT).show();

            }
        });

    }*/



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }
    private void getCategoryDetail(String id) {
        final String url= "http://52.66.136.244/api/v1/category_product/"+id;
        RequestQueue requestQueue = Volley.newRequestQueue(ServicesProducts.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject object= null;
                try {
                    ArrayList<String> detail=new ArrayList<>();
                    ArrayList iconslist=new ArrayList();
                    object = new JSONObject(response);
                    String status=object.getString("success");
                    JSONArray jsonArray=object.getJSONArray("products");
                    if(status.equals("1")) {
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(ServicesProducts.this,2);
                        rvProductList.setLayoutManager(gridLayoutManager);
                        ServicesProductListAdapter adapter = new ServicesProductListAdapter(ServicesProducts.this, finalProductModel,jsonArray,new onItemCLickListner() {
                            @Override
                            public void onItemCLick(int position, View view, String entity) {
                                if(entity.equals("add")){
                                    try {
                                        product_id=jsonArray.getJSONObject(position).getInt("id");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    addWishList();

                                }else {
                                    try {
                                        product_id=jsonArray.getJSONObject(position).getInt("id");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    addWishList();

                                }
                            }
                        });
                        rvProductList.setAdapter(adapter);
                    }
                    else
                    {
                        Toast.makeText(ServicesProducts.this, "Network Issue", Toast.LENGTH_SHORT).show();
                    }

                    } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
//                Map<String, String> map = new HashMap<String, String>();
//                SessionManager sessionManager = new SessionManager(user_profile.this);
//                HashMap<Stringet("id");
//                map.put("login_id",login_id);g, String> user = sessionManager.getUserDetails();
//                String smobile_no = user.get("phone");
//                String login_id = user.
//
//                return map;
//            }

        };
        requestQueue.add(stringRequest);
    }
    public void addWishList() {
        final String token = prefManager.getString("cust_id", "");
        final ProgressDialog dialog = ProgressDialog.show(ServicesProducts.this, "", "Proccessing....Please wait");
        cusid = prefManager.getString("cust_id", "");

        final String url = "http://52.66.136.244/api/v1/wishlist?qty=1&product_id=" + product_id;
        RequestQueue requestQueue = Volley.newRequestQueue(ServicesProducts.this);
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
                    DatabaseHandler databaseHandler=new DatabaseHandler(ServicesProducts.this);
                   /* int count=databaseHandler.get*/
                    if (!wishlistid.equals("0")) {

                        Toast.makeText(ServicesProducts.this, "Product added to wishlist.", Toast.LENGTH_SHORT).show();
                        getWishList();
                    } else {
                        getWishList();

                        Toast.makeText(ServicesProducts.this, "Removed From Wishist.", Toast.LENGTH_SHORT).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(ServicesProducts.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject object = null;
                try {
                    object = new JSONObject(response);
                    String status = object.getString("success");
                    //JSONObject statusjson=new JSONObject(status);
                    // JSONArray jsonArray=object.getJSONArray("details");
                    if (status.equals("1")) {
                        JSONArray jsonArray = object.getJSONArray("whislist");
                        wishlist =  jsonArray.length();
                        Intent intent = new Intent(ServicesProducts.this, MainActivity.class);
                        intent.putExtra("cart", "" + cart);
                        intent.putExtra("wish", "" + wishlist);
                        startActivity(intent);
                        finish();


                    } else {

                        // Toast.makeText(getActivity(), "alreay in wishlist", Toast.LENGTH_SHORT).show();
                        // dialog.dismiss();
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
                headers.put("Authorization", cusid);
                headers.put("Content-Type", "application/json");
                return headers;
            }

        };
        requestQueue.add(stringRequest);
    }

}
