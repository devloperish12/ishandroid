package com.indiansmarthub.ish.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
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
import com.indiansmarthub.ish.adapter.SearchProductAdapter;
import com.indiansmarthub.ish.custom.GeneralCode;
import com.indiansmarthub.ish.javaclass.BottomMenuHelper;
import com.indiansmarthub.ish.model.ModelResponseSearchProduct;
import com.indiansmarthub.ish.model.ModelSearchProduct;
import com.indiansmarthub.ish.model.searchresponse.SearchResponseModel;
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
public class SearchFragment extends Fragment {


    private EditText edittext_search_pro_store;
    public Fragment fragment;
    RecyclerView recyclerview_productListingSearch;
    private TextView noProdlistFoundSearch;
    static String name = "";
    List<ModelSearchProduct> getProductsSerach;
    SearchProductAdapter searchProductAdapter;

    private SharedPreferences prefManager;
    ProgressBar progressBar;
    private DatabaseHandler databaseHandler;


    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;
        fragment=this;
        view = inflater.inflate(R.layout.fragment_search, container, false);
        databaseHandler = new DatabaseHandler(getActivity());
        edittext_search_pro_store = view.findViewById(R.id.edittext_search_pro_store);
        recyclerview_productListingSearch = view.findViewById(R.id.recyclerview_productListingSearch);
        noProdlistFoundSearch = view.findViewById(R.id.noProdlistFoundSearch);
        progressBar = view.findViewById(R.id.progress_bar);
        edittext_search_pro_store.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().equalsIgnoreCase("")){
                    recyclerview_productListingSearch.setVisibility(View.GONE);
                    noProdlistFoundSearch.setVisibility(View.VISIBLE);
                } else {

                   // getProducts(s.toString());
                    noProdlistFoundSearch.setVisibility(View.GONE);

                    name = edittext_search_pro_store.getText().toString();
                    searchapi(name);
                   // getProducts(edittext_search_pro_store.getText().toString());
                }

            }
        });

     /*   edittext_search_pro_store.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (GeneralCode.isConnectingToInternet(getActivity())) {
                        if (!edittext_search_pro_store.getText().toString().isEmpty()) {
                            recyclerview_productListingSearch.setVisibility(View.VISIBLE);
                            noProdlistFoundSearch.setVisibility(View.GONE);
                            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(edittext_search_pro_store.getWindowToken(), 0);
                            name = edittext_search_pro_store.getText().toString();
                            getProducts(edittext_search_pro_store.getText().toString());
                        } else {
                            recyclerview_productListingSearch.setVisibility(View.GONE);
                            noProdlistFoundSearch.setVisibility(View.VISIBLE);
                        }
                    } else {
                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(edittext_search_pro_store.getWindowToken(), 0);
                    //    internetLayout.setVisibility(View.VISIBLE);
                        recyclerview_productListingSearch.setVisibility(View.GONE);
                    }
                    return true;
                }
                return false;
            }
        });*/

        return view;
    }

    private void getProducts(String name)
    {
        if(GeneralCode.isConnectingToInternet(fragment.getContext())) {

            progressBar.setVisibility(View.VISIBLE);

            NetworkService networkService = NetworkClient.getClient().create(NetworkService.class);
            //  Call<ModelResponseSearchProduct> categoriesCall = networkService.getSearchProduct("NXqXAgFZA0jiWp5t6+=lGpgWJXEkbo", name,prefManager.getString("cust_id",""));
            Call<SearchResponseModel> categoriesCall = networkService.searchApi(name);


            categoriesCall.enqueue(new Callback<SearchResponseModel>() {
                @Override
                public void onResponse(Call<SearchResponseModel> call, Response<SearchResponseModel> response) {
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(edittext_search_pro_store.getWindowToken(), 0);
                    if (response.body() != null) {
                        if (response.body().getProducts() != null) {

                            progressBar.setVisibility(View.GONE);
                            noProdlistFoundSearch.setVisibility(View.GONE);
                            recyclerview_productListingSearch.setVisibility(View.VISIBLE);
                            //  getProductsSerach.clear();
                            //   finalProductSearch.clear();
                            //    getProductsSerach = response.body().getSearch();
                            ArrayList<ModelSearchProduct> finalProductSearch = new ArrayList<>();
                            /*finalProductSearch.addAll(response.body().getSearch());*/

                            // call adapter
                            GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
                            recyclerview_productListingSearch.setLayoutManager(gridLayoutManager);
//                            SearchProductAdapter adapter = new SearchProductAdapter(getActivity(), response.body().getProducts());
//                            recyclerview_productListingSearch.setAdapter(adapter);
                            //

                         /*else {
                            noProdlistFoundSearch.setVisibility(View.VISIBLE);
                            recyclerview_productListingSearch.setVisibility(View.GONE);
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

                            progressBar.setVisibility(View.GONE);
                        }*/
                        } else {
                            noProdlistFoundSearch.setVisibility(View.VISIBLE);
                            recyclerview_productListingSearch.setVisibility(View.GONE);


                            progressBar.setVisibility(View.GONE);
                        }
                    } else {
                        noProdlistFoundSearch.setVisibility(View.VISIBLE);
                        recyclerview_productListingSearch.setVisibility(View.GONE);

                        Toast.makeText(getActivity(), "Something went wrong...!!", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailure(Call<SearchResponseModel> call, Throwable t) {

                    noProdlistFoundSearch.setVisibility(View.VISIBLE);
                    recyclerview_productListingSearch.setVisibility(View.GONE);

                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "Something went wrong...!!", Toast.LENGTH_SHORT).show();

                }
            });
        }else {
            Toast.makeText(fragment.getContext(), "Please check your internet...", Toast.LENGTH_SHORT).show();
        }
    }

    public void searchapi(String seval) {
      //  final String token = prefManager.getString("cust_id", "");
        // final ProgressDialog dialog = ProgressDialog.show(getContext(), "", "Proccessing....Please wait");

        final String url = "http://52.66.136.244/api/v1/search?search="+seval;
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject object = null;
                try {
                    object = new JSONObject(response);
                    //String status = object.getString("success");
                    //JSONObject statusjson=new JSONObject(status);
                     JSONArray jsonArray=object.getJSONArray("products");
                    //int lenght=0;
                    //if (status.equals("1")) {
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
                        recyclerview_productListingSearch.setLayoutManager(gridLayoutManager);
                        SearchProductAdapter adapter = new SearchProductAdapter(getActivity(), jsonArray);
                        recyclerview_productListingSearch.setAdapter(adapter);

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
               // headers.put("Authorization", token);
                headers.put("Content-Type", "application/json");
                return headers;
            }

        };
        requestQueue.add(stringRequest);
    }



}
