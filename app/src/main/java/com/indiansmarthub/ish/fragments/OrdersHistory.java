package com.indiansmarthub.ish.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.indiansmarthub.ish.R;
import com.indiansmarthub.ish.activity.DetailsActivity;
import com.indiansmarthub.ish.adapter.OrderHistoryAdapter;
import com.indiansmarthub.ish.adapter.WallateAdapter;
import com.indiansmarthub.ish.custom.GeneralCode;
import com.indiansmarthub.ish.model.ModelOrderHistory;
import com.indiansmarthub.ish.model.ModelResponseOrderHistory;
import com.indiansmarthub.ish.retrofit.NetworkClient;
import com.indiansmarthub.ish.retrofit.NetworkService;
import com.squareup.picasso.Picasso;

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

/**
 * A simple {@link Fragment} subclass.
 */
public class OrdersHistory extends Fragment {
View view;
    private TextView noProdlistFound;
    private RecyclerView rvorders;
    List<ModelOrderHistory> finalOrderHistoryModel;
    private LinearLayoutManager linearLayoutManager;
    private OrderHistoryAdapter orderHistoryAdapter;
    ProgressBar mProgressBar;
    SharedPreferences prefManager;

    public OrdersHistory() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_orders_history, container, false);
        prefManager = getActivity().getSharedPreferences("ISH", getActivity().MODE_PRIVATE);
        init();
        if (GeneralCode.isConnectingToInternet(getActivity())) {
            getorders();
        } else {
            GeneralCode.showDialog(getActivity());
        }

        return view;
    }

    private void init() {
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvorders = view.findViewById(R.id.rvWallateHistory);

//        finalOrderHistoryModel = new ArrayList<>();
//        noProdlistFound = view.findViewById(R.id.noProdlistFound);
//        rvorders = view.findViewById(R.id.rvorders);
//        mProgressBar = view.findViewById(R.id.progress_bar);
    }

   /* private void getOrderHistoryData() {
        mProgressBar.setVisibility(View.VISIBLE);
        NetworkService networkService = NetworkClient.getClient().create(NetworkService.class);
        Call<ModelResponseOrderHistory> categoriesCall = networkService.getordetHistory("NXqXAgFZA0jiWp5t6+=lGpgWJXEkbo", prefManager.getString("cust_id", ""));

        categoriesCall.enqueue(new Callback<ModelResponseOrderHistory>() {
            @Override
            public void onResponse(Call<ModelResponseOrderHistory> call, Response<ModelResponseOrderHistory> response) {
                if (response.body() != null) {
                    if (response.body().getSuccess().equals("1")) {
                        if (response.body().getCustomerOrder() != null) {

                            linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                            rvorders.setVisibility(View.VISIBLE);
                            noProdlistFound.setVisibility(View.GONE);

                            finalOrderHistoryModel.addAll(response.body().getCustomerOrder());
//                            orderHistoryAdapter = new OrderHistoryAdapter(getActivity(), finalOrderHistoryModel);
//                            rvorders.setLayoutManager(linearLayoutManager);
//                            rvorders.setAdapter(orderHistoryAdapter);
//
//                            mProgressBar.setVisibility(View.GONE);
                        }
                    } else {
                        rvorders.setVisibility(View.GONE);
                        noProdlistFound.setVisibility(View.VISIBLE);
                        mProgressBar.setVisibility(View.GONE);
                      //  Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();


                    }
                } else {
                    rvorders.setVisibility(View.GONE);
                    noProdlistFound.setVisibility(View.VISIBLE);
                    mProgressBar.setVisibility(View.GONE);
                 //   Toast.makeText(getActivity(), "Something went wrong...!!", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ModelResponseOrderHistory> call, Throwable t) {
                mProgressBar.setVisibility(View.GONE);
                rvorders.setVisibility(View.GONE);
                noProdlistFound.setVisibility(View.VISIBLE);
             //   Toast.makeText(getActivity(), "Something went wrong...!!", Toast.LENGTH_SHORT).show();

            }
        });
    }*/
    public void getorders()
    {
            final String token = prefManager.getString("cust_id", "");
            //final ProgressDialog dialog = ProgressDialog.show(getActivity(), "", "Proccessing....Please wait");

            final String url = "http://52.66.136.244/api/v1/myorders";
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new com.android.volley.Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    JSONObject object = null;
                    try {

                        object = new JSONObject(response);
                        String status = object.getString("success");
                        JSONArray jsonArray=object.getJSONArray("orders");
//                        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                        rvorders.setVisibility(View.VISIBLE);
                       // orderHistoryAdapter = new OrderHistoryAdapter(getActivity(), finalOrderHistoryModel,jsonArray);
                        //rvorders.setLayoutManager(linearLayoutManager);
                        rvorders.setAdapter(orderHistoryAdapter);
                        rvorders.setLayoutManager(linearLayoutManager);
                        orderHistoryAdapter = new OrderHistoryAdapter(getActivity(), finalOrderHistoryModel,jsonArray);
                        rvorders.setAdapter(orderHistoryAdapter);

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
            })

                {
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
