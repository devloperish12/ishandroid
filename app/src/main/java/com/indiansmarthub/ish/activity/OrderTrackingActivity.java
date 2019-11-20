package com.indiansmarthub.ish.activity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.indiansmarthub.ish.R;
import com.indiansmarthub.ish.adapter.TrackingOrderAdapter;
import com.indiansmarthub.ish.model.ModelOrderItem;
import com.indiansmarthub.ish.model.ModelResponseOrderDetails;
import com.indiansmarthub.ish.retrofit.NetworkClient;
import com.indiansmarthub.ish.retrofit.NetworkService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderTrackingActivity extends AppCompatActivity {
    RecyclerView rvOrderProduct;
    LinearLayout llOrderList;
    TextView tvOrderId,tvFrenchishName,tvName,tvEmail,tvFristnameLastName,tvstreet,tvCity,tvcontry,tvMobile,tvOrderStatus;
    List<ModelOrderItem> productsItemsList;
    TrackingOrderAdapter trackingOrderAdapter;
    View view;
    private ProgressDialog progressDialog;
    private String orderid;
    private SharedPreferences prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_tracking);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        prefManager = getSharedPreferences("ISH", MODE_PRIVATE);

        init();
        try {
            orderid = getIntent().getStringExtra("order_id");
            String email = prefManager.getString("email", "");
            getOrderTracking(orderid, email);

            Log.e("tag_order", orderid + "," + email);
        }
        catch (Exception e){
            e.printStackTrace();
        }



    }



    private void init() {
        productsItemsList = new ArrayList<>();
        tvOrderId = findViewById(R.id.tvOrderId);
        tvFrenchishName = findViewById(R.id.tvFrenchishName);
        tvName = findViewById(R.id.tvName);
        tvEmail = findViewById(R.id.tvEmail);
        tvFristnameLastName = findViewById(R.id.tvFristnameLastName);
        tvstreet = findViewById(R.id.tvstreet);
        tvCity = findViewById(R.id.tvCity);
        tvcontry = findViewById(R.id.tvcontry);
        tvMobile = findViewById(R.id.tvMobile);
        tvOrderStatus = findViewById(R.id.tvOrderStatus);

        llOrderList = findViewById(R.id.llOrderList);

        rvOrderProduct = findViewById(R.id.rvOrderProduct);


    }




    private void getOrderTracking(String orderid, String email) {
        progressDialog = new ProgressDialog(OrderTrackingActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        NetworkService networkService = NetworkClient.getClient().create(NetworkService.class);
        Call<ModelResponseOrderDetails> responseCall = networkService.getTrakingorder("NXqXAgFZA0jiWp5t6+=lGpgWJXEkbo",orderid,email);
        responseCall.enqueue(new Callback<ModelResponseOrderDetails>() {
            @Override
            public void onResponse(Call<ModelResponseOrderDetails> call, Response<ModelResponseOrderDetails> response) {
                if (response.body() != null) {
                    if (response.body().getSuccess().equals("1")) {
                        llOrderList.setVisibility(View.VISIBLE);

                        productsItemsList = response.body().getItems();
                        tvOrderId.setText(response.body().getOrder().get(0).getOrder_id());
                        tvFrenchishName.setText(response.body().getOrder().get(0).getFrenchies());
                        tvName.setText(response.body().getOrder().get(0).getName());
                        tvEmail.setText(response.body().getOrder().get(0).getEmail());
                        tvFristnameLastName.setText(response.body().getShippingAddress().get(0).getFirstname()+ " " + response.body().getShippingAddress().get(0).getLastname());
                        tvstreet.setText(response.body().getShippingAddress().get(0).getStreet());
                        tvCity.setText(response.body().getShippingAddress().get(0).getRegion()+ "," + response.body().getShippingAddress().get(0).getCity() + ","+response.body().getShippingAddress().get(0).getPostcode());
                        tvcontry.setText(response.body().getShippingAddress().get(0).getCountryId());
                        tvMobile.setText(response.body().getShippingAddress().get(0).getTelephone());
                        tvOrderStatus.setText(response.body().getOrder().get(0).getStatus());
                        trackingOrderAdapter = new TrackingOrderAdapter(OrderTrackingActivity.this, productsItemsList);
                        rvOrderProduct.setLayoutManager(new LinearLayoutManager(OrderTrackingActivity.this, LinearLayoutManager.VERTICAL, false));
                        rvOrderProduct.setAdapter(trackingOrderAdapter);
                        //  trackingOrderAdapter.notifyDataSetChanged();


                        progressDialog.dismiss();
                    } else {

                        progressDialog.dismiss();

                    }

                    progressDialog.dismiss();
                } else {

                    progressDialog.dismiss();

                }
            }

            @Override
            public void onFailure(Call<ModelResponseOrderDetails> call, Throwable t) {

                progressDialog.dismiss();

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

}
