package com.indiansmarthub.ish.fragments;


import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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
import com.indiansmarthub.ish.adapter.WishListAdapter;
import com.indiansmarthub.ish.custom.GeneralCode;
import com.indiansmarthub.ish.model.ModelResponseWishList;
import com.indiansmarthub.ish.model.ModelWishList;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class WishList extends Fragment {

    View view;
    private RecyclerView rvWishList;
    ProgressBar progressBar;
    private DatabaseHandler databaseHandler;
    SharedPreferences prefManager;
    LinearLayout viewwishlist_main_layout;


    public WishList() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_wish_list, container, false);
        databaseHandler = new DatabaseHandler(getActivity());
        rvWishList = view.findViewById(R.id.rvWishList);

        progressBar = view.findViewById(R.id.progress_bar);

        prefManager = getActivity().getSharedPreferences("ISH", getActivity().MODE_PRIVATE);

        init();
        getWishList();
        if (GeneralCode.isConnectingToInternet(getActivity())) {
            getWishList();
        } else {
            GeneralCode.showDialog(getActivity());
        }

        return view;
    }

    private void init() {
        databaseHandler = new DatabaseHandler(getActivity());
        //   cartCount = findViewById(R.id.cartCount);
        rvWishList = view.findViewById(R.id.rvWishList);
        viewwishlist_main_layout = view.findViewById(R.id.notFound);
        //  tryAgain = findViewById(R.id.tryAgain);
        //   internetLayout = findViewById(R.id.internetLayout);
    }

    //    private void getWishlist() {
//       progressBar.setVisibility(View.VISIBLE);
//
//        NetworkService networkService = NetworkClient.getClient().create(NetworkService.class);
//      //  Call<ModelResponseWishList> categoriesCall = networkService.getWishList("NXqXAgFZA0jiWp5t6+=lGpgWJXEkbo", prefManager.getString("cust_id", ""));
//        Call<ModelResponseWishList> categoriesCall = networkService.getWishList("NXqXAgFZA0jiWp5t6+=lGpgWJXEkbo", prefManager.getString("cust_id", ""));
//
//        Log.e("tag_id", prefManager.getString("cust_id", ""));
//        categoriesCall.enqueue(new Callback<ModelResponseWishList>() {
//            @Override
//            public void onResponse(Call<ModelResponseWishList> call, Response<ModelResponseWishList> response) {
//                if (response.body() != null) {
//                    if (response.body().getSuccess().equals("1")) {
//                        if (response.body().getAddtocart() != null) {
//                            ArrayList<ModelWishList> getWishList = new ArrayList<>();
//
//                            rvWishList.setVisibility(View.VISIBLE);
//                          //  noProdlistFound.setVisibility(View.GONE);
//                            viewwishlist_main_layout.setVisibility(view.GONE);
//                   //         getWishList.clear();
//                            getWishList.addAll(response.body().getAddtocart());
//
//                            GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
//                            WishListAdapter wishListAdapter = new WishListAdapter(getActivity(), getWishList);
//                            rvWishList.setLayoutManager(gridLayoutManager);
//                            rvWishList.setAdapter(wishListAdapter);
//
//                            progressBar.setVisibility(View.GONE);
//                        }
//                        else {
//                            rvWishList.setVisibility(View.GONE);
//                            viewwishlist_main_layout.setVisibility(view.VISIBLE);
//                            progressBar.setVisibility(View.GONE);
//
////                            Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
//
//                        }
//                    } else {
//                        rvWishList.setVisibility(View.GONE);
//                      //  noProdlistFound.setVisibility(View.VISIBLE);
//                        viewwishlist_main_layout.setVisibility(view.VISIBLE);
//                        progressBar.setVisibility(View.GONE);
//
////                        Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
//
//                    }
//                }
//                else {
//                    rvWishList.setVisibility(View.GONE);
//                    viewwishlist_main_layout.setVisibility(view.VISIBLE);
//                    progressBar.setVisibility(View.GONE);
//                   // Toast.makeText(getActivity(), "Something went wrong...!!", Toast.LENGTH_SHORT).show();
//
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ModelResponseWishList> call, Throwable t) {
//                progressBar.setVisibility(View.GONE);
//                rvWishList.setVisibility(View.GONE);
//                viewwishlist_main_layout.setVisibility(view.VISIBLE);
//              //  Toast.makeText(getActivity(), "Something went wrong...!!", Toast.LENGTH_SHORT).show();
//
//
//            }
//        });
//
//    }
    public void getWishList() {
        final String token = prefManager.getString("cust_id", "");
        final ProgressDialog dialog = ProgressDialog.show(getContext(), "", "Proccessing....Please wait");

        final String url = "http://52.66.136.244/api/v1/wishlist";
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
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
                        if (jsonArray.length() > 0) {
                            GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
                            WishListAdapter wishListAdapter = new WishListAdapter(getActivity(), jsonArray);
                            rvWishList.setLayoutManager(gridLayoutManager);
                            rvWishList.setAdapter(wishListAdapter);

                        } else {
                            rvWishList.setVisibility(View.GONE);
                            viewwishlist_main_layout.setVisibility(view.VISIBLE);
                            // dialog.dismiss();
                        }
                    } else {

                        Toast.makeText(getActivity(), "alreay in wishlist", Toast.LENGTH_SHORT).show();
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


}
