package com.indiansmarthub.ish.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.indiansmarthub.ish.MainActivity;
import com.indiansmarthub.ish.R;
import com.indiansmarthub.ish.adapter.CartAdapter;
import com.indiansmarthub.ish.adapter.WishListAdapter;
import com.indiansmarthub.ish.custom.InternetConnection;
import com.indiansmarthub.ish.fragments.Cart;
import com.indiansmarthub.ish.model.ModelResponseRegister;
import com.indiansmarthub.ish.retrofit.NetworkClient;
import com.indiansmarthub.ish.retrofit.NetworkService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Splash extends AppCompatActivity {

    private SharedPreferences prefManager;
    private Toolbar toolbar;
    private ProgressDialog progressDialog;
    String cart = "", wishlist = "";
    String cusid = "";

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            this.requestWindowFeature(Window.FEATURE_NO_TITLE);
            this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            setContentView(R.layout.activity_splash);
            prefManager = this.getSharedPreferences("ISH", Splash.MODE_PRIVATE);

            cusid = prefManager.getString("cust_id", "");


                    prefManager = getSharedPreferences("userDetail", android.content.Context.MODE_PRIVATE);
            this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (!cusid.equalsIgnoreCase("")) {
                        //   getDetail();
                        addTocart();
                    } else {
                        Intent intent = new Intent(Splash.this, MainActivity.class);
                        intent.putExtra("cart", "");
                        intent.putExtra("wish", "");
                        startActivity(intent);
                        finish();

                    }
//                if (!prefManager.getBoolean("isMembership", false)) {
//                    Intent intent = new Intent(SplaseScreenActivity.this, LoginActivity.class);
//                    startActivity(intent);
//                    finish();
//                }
                    //else {
                    // }
                }
            }, 2000);


      /*  if (readExternalStorege  + location!= PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.ACCESS_COARSE_LOCATION}, 555);
        } else {
            InternetConnection internetConnection = new InternetConnection();
            internetConnection. new callGoogleApi(){
                @Override
                protected void onPostExecute(Boolean isConnection) {
                    super.onPostExecute(isConnection);
                    isConnectedNetwork(isConnection);
                }
            }.execute("");
            //if (internetConnection.IsConnected(SplashScreen.this)) {
        }*/
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.d("LOGIN", "Login Dashboard");
        }
    }


    public void addTocart() {
        final String token = prefManager.getString("cust_id", "");
        //final ProgressDialog dialog = ProgressDialog.show(getActivity(), "", "Proccessing....Please wait");

        final String url = "http://52.66.136.244/api/v1/cart";
        RequestQueue requestQueue = Volley.newRequestQueue(Splash.this);
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
                        cart = "" + getCarts.length();
                        getWishList();
                    } else {

                        Toast.makeText(Splash.this, "alreay in wishlist", Toast.LENGTH_SHORT).show();
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
                Log.d("Login", "" + error.getCause());
                //dialog.dismiss();
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

    public void getWishList() {
        final String token = prefManager.getString("cust_id", "");
        // final ProgressDialog dialog = ProgressDialog.show(getContext(), "", "Proccessing....Please wait");

        final String url = "http://52.66.136.244/api/v1/wishlist";
        RequestQueue requestQueue = Volley.newRequestQueue(Splash.this);
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
                        wishlist = "" + jsonArray.length();
                        Intent intent = new Intent(Splash.this, MainActivity.class);
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

    private void getDetail() {
        // final ProgressDialog dialog = ProgressDialog.show(LoginActivity.this, "", "Proccessing....Please wait");

        final String url = "http://52.66.136.244/api/v1/choosepayment";
        RequestQueue requestQueue = Volley.newRequestQueue(Splash.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject object = null;
                try {

                    object = new JSONObject(response);
                    String status = object.getString("success");
                    JSONObject statusjson = object.getJSONObject("shipmentid");
                    String gst = object.getString("gst");
                    String shipping_id = object.getString("shipmentid");
                    String subtotal = object.getString("subtotal");
                    String grandtotal = object.getString("grandtotal");
//                    subtotalval=Double.parseDouble(subtotal);
//                    gstval=Double.parseDouble(gst);
//                    double totalval=subtotalval+gstval;
//                    grandtotalval=totalval;
//                    tvGrandTotalPayment.setText(""+totalval);
//                    tvSubTotalPayment.setText("subtotal");
//                    // JSONArray jsonArray=object.getJSONArray("details");
                    if (!status.equals("")) {
                        String token = "Bearer " + statusjson.getString("token");
                        //  Toast.makeText(Splash.this, "update successfully", Toast.LENGTH_SHORT).show();

                    } else {

                        // Toast.makeText(PaymentMethod.this, "faild", Toast.LENGTH_SHORT).show();
                        //dialog.dismiss();
                    }
                    // dialog.dismiss();
                    ///dialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                    //  dialog.dismiss();
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
