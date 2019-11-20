package com.indiansmarthub.ish.fragments;


import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.indiansmarthub.ish.R;
import com.indiansmarthub.ish.activity.OrderHistoryDetails;
import com.indiansmarthub.ish.adapter.FrenchiesAdapter;
import com.indiansmarthub.ish.adapter.WallateAdapter;
import com.indiansmarthub.ish.custom.GeneralCode;
import com.indiansmarthub.ish.model.ModelResponseTree;
import com.indiansmarthub.ish.model.ModelTree;
import com.indiansmarthub.ish.retrofit.NetworkClient;
import com.indiansmarthub.ish.retrofit.NetworkService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.BreakIterator;
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
public class Frenchies extends Fragment {
    View view;
RecyclerView mRecyclerView,rvlevel2;
    List<ModelTree> finaltreeModel;
    TextView tvTreeLevel1,mypos;
    SharedPreferences prefManager;
    private ProgressDialog progressDialog;

    public Frenchies() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_frenchies, container, false);
        prefManager = getActivity().getSharedPreferences("ISH", getActivity().MODE_PRIVATE);
        init();

        if (GeneralCode.isConnectingToInternet(getActivity())) {
            getlevel();
        } else {
            GeneralCode.showDialog(getActivity());
        }

        return view;
    }

    private void init() {
//        finaltreeModel = new ArrayList<>();
//        mRecyclerView = view.findViewById(R.id.rvFrenchies);
//        tvTreeLevel1 = view.findViewById(R.id.tvTreeLevel1);
        mypos=view.findViewById(R.id.mypos);
//        rvlevel2=view.findViewById(R.id.level3);
        //linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
       // tvWallateBalance = view.findViewById(R.id.tvWallateBalance);
        mRecyclerView = view.findViewById(R.id.rvWallateHistory);
        rvlevel2 = view.findViewById(R.id.rvWallateHistory2);

        //mProgressbar = view.findViewById(R.id.progress_bar);

    }


    private void callfrenchiesApi() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.show();


        NetworkService networkService = NetworkClient.getClient().create(NetworkService.class);
        Call<ModelResponseTree> categoriesCall = networkService.getTree("NXqXAgFZA0jiWp5t6+=lGpgWJXEkbo", prefManager.getString("cust_id", ""));

        categoriesCall.enqueue(new Callback<ModelResponseTree>() {
            @Override
            public void onResponse(Call<ModelResponseTree> call, Response<ModelResponseTree> response) {
                if (response.body() != null) {
                    if (response.body().getSuccess().equals("1")) {
                        if (response.body().getCustomerHistory() != null) {
                            LinearLayoutManager  managerEduBasket = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);

                            finaltreeModel.clear();

                            finaltreeModel.addAll( response.body().getCustomerHistory());
                            tvTreeLevel1.setText(response.body().getCustomerHistory().get(0).getLevel1());
                            mRecyclerView.setLayoutManager(managerEduBasket);

//                           FrenchiesAdapter frenchiesAdapter = new FrenchiesAdapter(getActivity(), finaltreeModel);
//                            mRecyclerView.setAdapter(frenchiesAdapter);

                            progressDialog.dismiss();

                        } else {
                            progressDialog.dismiss();
                       //     Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    } else {
                        progressDialog.dismiss();
                      //  Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    }
                } else {
                    progressDialog.dismiss();
                //    Toast.makeText(getActivity(), "Something went wrong...!!", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ModelResponseTree> call, Throwable t) {
                progressDialog.dismiss();
            //    Toast.makeText(getActivity(), "Something went wrong...!!", Toast.LENGTH_SHORT).show();

            }
        });
    }
public void getlevel()
{
         final String token = prefManager.getString("cust_id", "");
        // final ProgressDialog dialog = ProgressDialog.show(getContext(), "", "Proccessing....Please wait");

        final String url = "http://52.66.136.244/api/v1/frenchies";
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject object = null;
                try {
                    JSONArray chennalarr=new JSONArray();
                    object = new JSONObject(response);
                    String status = object.getString("success");
                    //JSONObject statusjson=new JSONObject(status);
                    // JSONArray jsonArray=
                    if (status.equals("1")) {
                        LinearLayoutManager  layout = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                        String pos=object.getString("user_type");
                        mypos.setText("You are "+pos);
                        if(pos.equalsIgnoreCase("SP"))
                        {
                            mypos.setText("You are "+"State partner");

                            JSONObject jsonObject=object.getJSONObject("sp_user");
                           // tvTreeLevel1.setText(jsonObject.getString("name"));
                            JSONArray sparr=object.getJSONArray("users");
                            mRecyclerView.setLayoutManager(layout);
                            FrenchiesAdapter frenchiesAdapter = new FrenchiesAdapter(getActivity(), finaltreeModel,sparr,"DP");
                            mRecyclerView.setAdapter(frenchiesAdapter);
                            try {
                                for (int a = 0; a < sparr.length(); a++) {
                                    JSONObject obj = sparr.getJSONObject(a);
                                    String id = obj.getString("id");
                                    String channel = obj.getString("channel");
                                    chennalarr = new JSONArray(channel);
                                    LinearLayoutManager  layout1 = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                                    rvlevel2.setLayoutManager(layout1);
                                    FrenchiesAdapter frenchiesAdapter1 = new FrenchiesAdapter(getActivity(), finaltreeModel,chennalarr,"CP");
                                    rvlevel2.setAdapter(frenchiesAdapter1);

                                }
                            }catch (Exception e)
                            {
                                e.printStackTrace();
                            }
                        }
                        else if(pos.equalsIgnoreCase("dp"))
                        {

                        }

//                        try {
//                            LinearLayoutManager  layout1 = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
//
//                            rvlevel2.setLayoutManager(layout1);
//                            FrenchiesAdapter frenchiesAdapter = new FrenchiesAdapter(getActivity(), finaltreeModel, chennalarr);
//                            rvlevel2.setAdapter(frenchiesAdapter);
//                        }catch (Exception e1)
//                        {
//                            e1.printStackTrace();
//                        }

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


