package com.indiansmarthub.ish.activity;

import android.app.ProgressDialog;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.indiansmarthub.ish.R;
import com.indiansmarthub.ish.adapter.OrderDetailsAdapter;
import com.indiansmarthub.ish.custom.GeneralCode;
import com.indiansmarthub.ish.model.ModelOrderItem;
import com.indiansmarthub.ish.model.ModelResponseOrderDetails;
import com.indiansmarthub.ish.retrofit.NetworkClient;
import com.indiansmarthub.ish.retrofit.NetworkService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderHistoryDetails extends AppCompatActivity {
    private Toolbar mToolbar;
    TextView orderPlaceDate,orderStatus,orderSubtotal,orderItemCounts,orderItemAmounts;
    RecyclerView recycleProductOrder;
    NestedScrollView nestedscrollviewOrder;
    LinearLayout subTotalLayout,itemCountLayout;
    OrderDetailsAdapter orderDetailsAdapter;
    private String orderid;
    List<ModelOrderItem> productsItemsList;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history_details);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        orderid = getIntent().getStringExtra("order_id");
        init();

    }

    private void init() {
        productsItemsList = new ArrayList<>();
        nestedscrollviewOrder =  findViewById(R.id.nestedscrollviewOrder);

        recycleProductOrder = findViewById(R.id.recycleProductOrder);
        orderPlaceDate = findViewById(R.id.orderPlaceDate);
        orderStatus = findViewById(R.id.orderStatus);
        subTotalLayout = findViewById(R.id.subTotalLayout);
        orderSubtotal = findViewById(R.id.orderSubtotal);
        itemCountLayout = findViewById(R.id.itemCountLayout);
        orderItemCounts = findViewById(R.id.orderItemCounts);
        orderItemAmounts = findViewById(R.id.orderItemAmounts);
        if (GeneralCode.isConnectingToInternet(OrderHistoryDetails.this)) {
            /*getProducts();*/
        } else {

            nestedscrollviewOrder.setVisibility(View.GONE);
        }
    }
/*
    private void getProducts() {
        progressDialog = new ProgressDialog(OrderHistoryDetails.this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();


        NetworkService networkService = NetworkClient.getClient().create(NetworkService.class);
        Call<ModelResponseOrderDetails> categoriesCall = networkService.getordetDetails("NXqXAgFZA0jiWp5t6+=lGpgWJXEkbo", orderid);

        categoriesCall.enqueue(new Callback<ModelResponseOrderDetails>() {
            @Override
            public void onResponse(Call<ModelResponseOrderDetails> call, Response<ModelResponseOrderDetails> response) {
                if (response.body() != null) {
                    if (response.body().getSuccess().equals("1")) {
                        nestedscrollviewOrder.setVisibility(View.VISIBLE);
                        if (response.body().getOrder() != null) {
                            orderPlaceDate.setVisibility(View.VISIBLE);
                            orderStatus.setVisibility(View.VISIBLE);
                            subTotalLayout.setVisibility(View.VISIBLE);
                            orderPlaceDate.setText("Placed On " + response.body().getOrder().get(0).getDate());
                            orderStatus.setText(response.body().getOrder().get(0).getStatus());
                            orderSubtotal.setText("\u20b9" + String.format("%.2f", Double.parseDouble(response.body().getOrder().get(0).getSubtotal())));
                        }
                        if (response.body().getItems() != null) {
                            if (response.body().getOrder() != null) {
                                itemCountLayout.setVisibility(View.VISIBLE);
                                orderItemCounts.setText("ITEMS " + response.body().getItems().size());
                                orderItemAmounts.setText(" AMOUNT:\u20b9 " + String.format("%.2f", Double.parseDouble(response.body().getOrder().get(0).getSubtotal())));
                            }
                            recycleProductOrder.setVisibility(View.VISIBLE);
                            productsItemsList = response.body().getItems();
                            orderDetailsAdapter = new OrderDetailsAdapter(OrderHistoryDetails.this, productsItemsList);
                            recycleProductOrder.setLayoutManager(new LinearLayoutManager(OrderHistoryDetails.this, LinearLayoutManager.VERTICAL, false));
                            recycleProductOrder.setAdapter(orderDetailsAdapter);
                            orderDetailsAdapter.notifyDataSetChanged();
                        }
                    } else {
                        Toast.makeText(OrderHistoryDetails.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    }
                } else {
                    Toast.makeText(OrderHistoryDetails.this, "Something went wrong...!!", Toast.LENGTH_SHORT).show();

                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ModelResponseOrderDetails> call, Throwable t) {

                progressDialog.dismiss();
                Toast.makeText(OrderHistoryDetails.this, "Something went wrong...!!", Toast.LENGTH_SHORT).show();

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
public void getOrders()
{

}
}
