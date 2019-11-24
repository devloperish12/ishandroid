package com.indiansmarthub.ish.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.indiansmarthub.ish.R;
import com.indiansmarthub.ish.adapter.TrackingOrderAdapter;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderTracking extends Fragment implements View.OnClickListener {
    EditText etOrderNumber,etPhoneorEmail;
    RecyclerView rvOrderProduct;
    LinearLayout llOrderList;
    TextView tvOrderId,tvFrenchishName,tvName,tvEmail,tvFristnameLastName,tvstreet,tvCity,tvcontry,tvMobile,tvOrderStatus;
    List<ModelOrderItem> productsItemsList;
    TrackingOrderAdapter trackingOrderAdapter;
    View view;
    ProgressBar mProgressBar;

    public OrderTracking() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_order_tracking, container, false);

        init();
        return view;
    }

    private void init() {
        productsItemsList = new ArrayList<>();
        mProgressBar = view.findViewById(R.id.progress_bar);
        tvOrderId = view.findViewById(R.id.tvOrderId);
        tvFrenchishName = view.findViewById(R.id.tvFrenchishName);
        tvName = view.findViewById(R.id.tvName);
        tvEmail = view.findViewById(R.id.tvEmail);
        tvFristnameLastName = view.findViewById(R.id.tvFristnameLastName);
        tvstreet = view.findViewById(R.id.tvstreet);
        tvCity = view.findViewById(R.id.tvCity);
        tvcontry = view.findViewById(R.id.tvcontry);
        tvMobile = view.findViewById(R.id.tvMobile);
        tvOrderStatus = view.findViewById(R.id.tvOrderStatus);
        view.findViewById(R.id.btnTrackingOrder).setOnClickListener(this);
        llOrderList = view.findViewById(R.id.llOrderList);
        etOrderNumber = view.findViewById(R.id.etOrderNumber);
        etPhoneorEmail = view.findViewById(R.id.etPhoneorEmail);
        rvOrderProduct = view.findViewById(R.id.rvOrderProduct);
    }

    private void validation() {
        if (GeneralCode.isConnectingToInternet(getActivity())){
            if (GeneralCode.isEmptyString(etOrderNumber.getText().toString())){
                Toast.makeText(getActivity(), "Enter Order Number...!!", Toast.LENGTH_SHORT).show();

            }else if (GeneralCode.isEmptyString(etPhoneorEmail.getText().toString())){
                Toast.makeText(getActivity(), "Enter Phone Number Or Email...!!", Toast.LENGTH_SHORT).show();

            }else {
                /*getOrderTracking();*/
            }
        }else {
            GeneralCode.showDialog(getActivity());
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnTrackingOrder:
                validation();
                break;
        }
    }

   /* private void getOrderTracking() {
        mProgressBar.setVisibility(View.VISIBLE);

        NetworkService networkService = NetworkClient.getClient().create(NetworkService.class);
        Call<ModelResponseOrderDetails> responseCall = networkService.getTrakingorder("NXqXAgFZA0jiWp5t6+=lGpgWJXEkbo",etOrderNumber.getText().toString(),etPhoneorEmail.getText().toString());
        responseCall.enqueue(new Callback<ModelResponseOrderDetails>() {
            @Override
            public void onResponse(Call<ModelResponseOrderDetails> call, Response<ModelResponseOrderDetails> response) {
                if (response.body() != null) {
                    if (response.body().getSuccess().equals("1")) {
                        llOrderList.setVisibility(View.VISIBLE);
                        etOrderNumber.setText("");
                        etPhoneorEmail.setText("");
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
                        trackingOrderAdapter = new TrackingOrderAdapter(getActivity(), productsItemsList);
                        rvOrderProduct.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                        rvOrderProduct.setAdapter(trackingOrderAdapter);
                      //  trackingOrderAdapter.notifyDataSetChanged();


                        mProgressBar.setVisibility(View.GONE);
                    } else {

                        mProgressBar.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    }

                    mProgressBar.setVisibility(View.GONE);
                } else {

                    mProgressBar.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ModelResponseOrderDetails> call, Throwable t) {

                mProgressBar.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();

            }
        });
    }*/

}
