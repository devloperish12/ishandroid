package com.indiansmarthub.ish.fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.api.Api;
import com.indiansmarthub.ish.R;
import com.indiansmarthub.ish.activity.PaymentMethod;
import com.indiansmarthub.ish.activity.ViewMoreProductActivity;
import com.indiansmarthub.ish.model.ServicesModel;
import com.indiansmarthub.ish.pojo.UserUpdateResponse;
import com.indiansmarthub.ish.retrofit.NetworkClient;
import com.indiansmarthub.ish.retrofit.NetworkService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountInfo extends Fragment implements View.OnClickListener {
    View view;
    private SharedPreferences prefManager;
    EditText mNmae, mEmail, email, password, city, state, pincode, address, address1, taxnumber;
    Button btnUpdate;

    public AccountInfo() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_account_info, container, false);

        mNmae = view.findViewById(R.id.tvPName);
        mEmail = view.findViewById(R.id.etPEmail);
        btnUpdate = view.findViewById(R.id.btnUpdate);
        password = view.findViewById(R.id.etPhoneNumber);
        city = view.findViewById(R.id.etCity);
        address = view.findViewById(R.id.etAddress);
        address1 = view.findViewById(R.id.etAddress1);
        taxnumber = view.findViewById(R.id.etTAxNumber);
        state = view.findViewById(R.id.etState);
        pincode = view.findViewById(R.id.etPinCode);
        btnUpdate.setOnClickListener(this);

        prefManager = getActivity().getSharedPreferences("ISH", getActivity().MODE_PRIVATE);
        showDetail();
//        String name = prefManager.getString("firs_name", "");
//        String email = prefManager.getString("email", "");
//
//        mNmae.setText(name);
//        mEmail.setText(email);

        return view;
    }

    public void showDetail() {
        final String token = prefManager.getString("cust_id", "");

        final String url = "http://52.66.136.244/api/v1/getdetails";
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject object = null;
                try {

                    object = new JSONObject(response);
                    String status = object.getString("success");
                    // JSONObject statusjson=object.getJSONObject("shipmentid");
                    // String gst=object.getString("gst");
                    if (status.equals("1")) {
                        JSONObject jsonObject = object.getJSONObject("user");

                        mNmae.setText(jsonObject.getString("name"));
                        mEmail.setText(jsonObject.getString("email"));
                        password.setText(jsonObject.getString("mobile"));
                        address.setText(jsonObject.getString("address"));
                        address1.setText(jsonObject.getString("address1"));
                        city.setText(jsonObject.getString("city"));
                        state.setText(jsonObject.getString("state"));
                        pincode.setText(jsonObject.getString("zip"));
                        taxnumber.setText(jsonObject.getString("tax_vat_number"));


                    } else {

                        Toast.makeText(getActivity(), "Internet connection problem", Toast.LENGTH_SHORT).show();
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
                headers.put("Authorization", token);
                headers.put("Content-Type", "application/json");
                return headers;
            }


        };
        requestQueue.add(stringRequest);

    }

    @Override
    public void onClick(View view) {
        updateApi();
    }

    private void updateApi() {
        NetworkService networkService= NetworkClient.createApi(getContext());
        networkService.updateUser(mNmae.getText().toString(),"",address.getText().toString(),address1.getText().toString(),password.getText().toString(),city.getText().toString(),state.getText().toString(),pincode.getText().toString(),taxnumber.getText().toString()).enqueue(new Callback<UserUpdateResponse>() {
            @Override
            public void onResponse(Call<UserUpdateResponse> call, Response<UserUpdateResponse> response) {
                Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
                if(response.isSuccessful()){


               mNmae.setText(response.body().getUser().getName());
               password.setText(response.body().getUser().getMobile());
               address1.setText(response.body().getUser().getAddress1());
               address.setText(response.body().getUser().getAddress());
               city.setText(response.body().getUser().getCity());
               state.setText(response.body().getUser().getState());
               pincode.setText(response.body().getUser().getZip());
               taxnumber.setText(response.body().getUser().getTaxVatNumber());
                }
            }

            @Override
            public void onFailure(Call<UserUpdateResponse> call, Throwable t) {

            }
        });

/*

        final String token = prefManager.getString("cust_id", "");

        final String url="http://52.66.136.244/api/v1/userupdate?name="+mNmae.getText().toString()+"&last_name="+""+
                "&address="+address.getText().toString()+"&address1="+address1.getText().toString()+"" +
                "&mobile="+password.getText().toString()+"&city="+city.getText().toString()+"" +
                "&state="+state.getText().toString()+"&" +
                "zip="+pincode.getText().toString()+"" +
                "tax_vat_number"+taxnumber.getText().toString();
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                JSONObject object = null;
                try {

                    object = new JSONObject(response);


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
                headers.put("Authorization","Bearer"+ token);
                headers.put("Content-Type", "application/json");
                return headers;
            }


        };
        requestQueue.add(stringRequest);

*/

    }


}
