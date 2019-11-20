package com.indiansmarthub.ish.fragments;


import android.annotation.SuppressLint;
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
import com.indiansmarthub.ish.adapter.WallateAdapter;
import com.indiansmarthub.ish.custom.GeneralCode;
import com.indiansmarthub.ish.model.ModelResponseRewardHistory;
import com.indiansmarthub.ish.model.ModelRewardHistory;
import com.indiansmarthub.ish.retrofit.NetworkClient;
import com.indiansmarthub.ish.retrofit.NetworkService;

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
public class WalletList extends Fragment {
View view;
    private SharedPreferences prefManager;
    private LinearLayoutManager linearLayoutManager;
    TextView tvWallateBalance;
    RecyclerView rvWallateHistory;
    List<ModelRewardHistory> getwallateHistory;
    private WallateAdapter wallateAdapter;
    ProgressBar mProgressbar;


    public WalletList() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_wallet_list, container, false);

        prefManager = getActivity().getSharedPreferences("ISH", getActivity().MODE_PRIVATE);

        init();

        if (GeneralCode.isConnectingToInternet(getActivity())) {
            getWalletDetail();
        } else {
            GeneralCode.showDialog(getActivity());
        }

        return view;
    }

    private void init() {
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        tvWallateBalance = view.findViewById(R.id.tvWallateBalance);
        rvWallateHistory = view.findViewById(R.id.rvWallateHistory);
        mProgressbar = view.findViewById(R.id.progress_bar);
    }

//    private void getWalletHistory() {
//        mProgressbar.setVisibility(View.VISIBLE);
//
//        NetworkService networkService = NetworkClient.getClient().create(NetworkService.class);
//        Call<ModelResponseRewardHistory> cartCall = networkService.getRewardHistory("NXqXAgFZA0jiWp5t6+=lGpgWJXEkbo", prefManager.getString("cust_id", ""));
//
//        cartCall.enqueue(new Callback<ModelResponseRewardHistory>() {
//            @SuppressLint({"SetTextI18n", "DefaultLocale"})
//            @Override
//            public void onResponse(Call<ModelResponseRewardHistory> call, Response<ModelResponseRewardHistory> response) {
//
//                if (response.body() != null) {
//                    if (response.body().getSuccess().equals("1")) {
//                        if (response.body().getCustomerHistory() != null) {
//                            if (!response.body().getCustomerHistory().isEmpty()) {
//                                getwallateHistory = new ArrayList<>();
//
//                                getwallateHistory = response.body().getCustomerHistory();
//                                rvWallateHistory.setLayoutManager(linearLayoutManager);
//                                tvWallateBalance.setText(response.body().getBalance());
////                                wallateAdapter = new WallateAdapter(getActivity(), getwallateHistory);
////                                rvWallateHistory.setAdapter(wallateAdapter);
//
//                                mProgressbar.setVisibility(View.GONE);
//
//                            } else {
//
//                                mProgressbar.setVisibility(View.GONE);
//                             //   Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
//
//                            }
//                        } else {
//
//                            mProgressbar.setVisibility(View.GONE);
//                         //   Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
//
//                        }
//                    } else {
//                        mProgressbar.setVisibility(View.GONE);
//                    //    Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
//
//                    }
//                } else {
//                    mProgressbar.setVisibility(View.GONE);
//                 //   Toast.makeText(getActivity(), "Something went wrong...!!", Toast.LENGTH_SHORT).show();
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ModelResponseRewardHistory> call, Throwable t) {
//                mProgressbar.setVisibility(View.GONE);
//            //    Toast.makeText(getActivity(), "Something went wrong...!!", Toast.LENGTH_SHORT).show();
//
//            }
//        });
//    }
public void getWalletDetail()
{
    final String token = prefManager.getString("cust_id", "");
    // final ProgressDialog dialog = ProgressDialog.show(getContext(), "", "Proccessing....Please wait");

    final String url = "http://52.66.136.244/api/v1/wallet_detail";
    RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
    StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new com.android.volley.Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            JSONObject object = null;
            try {
                object = new JSONObject(response);
                String status = object.getString("success");
                //JSONObject statusjson=new JSONObject(status);
                // JSONArray jsonArray=
                if (status.equals("1")) {
                    String amt=object.getString("total_amount");
                    tvWallateBalance.setText(amt);
                    JSONArray jsonArray = object.getJSONArray("wallets");
                    rvWallateHistory.setLayoutManager(linearLayoutManager);
                    wallateAdapter = new WallateAdapter(getActivity(), getwallateHistory,jsonArray);
                    rvWallateHistory.setAdapter(wallateAdapter);
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
            headers.put("Authorization", token);
            headers.put("Content-Type", "application/json");
            return headers;
        }

    };
    requestQueue.add(stringRequest);
}

}
