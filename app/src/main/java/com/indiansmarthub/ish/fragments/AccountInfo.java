package com.indiansmarthub.ish.fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.indiansmarthub.ish.R;
import com.indiansmarthub.ish.activity.PaymentMethod;
import com.indiansmarthub.ish.activity.ViewMoreProductActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountInfo extends Fragment {
View view;
    private SharedPreferences prefManager;
    EditText mNmae, mEmail;

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

        prefManager = getActivity().getSharedPreferences("ISH", getActivity().MODE_PRIVATE);
        showDetail();
//        String name = prefManager.getString("firs_name", "");
//        String email = prefManager.getString("email", "");
//
//        mNmae.setText(name);
//        mEmail.setText(email);

        return view;
    }
    public void showDetail()
    {
        final String token=prefManager.getString("cust_id","");

        final String url="http://52.66.136.244/api/v1/getdetails";
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject object= null;
                try {

                    object = new JSONObject(response);
                    String status=object.getString("success");
                    // JSONObject statusjson=object.getJSONObject("shipmentid");
                    // String gst=object.getString("gst");
                    if(status.equals("1")) {
                  JSONObject jsonObject=object.getJSONObject("user");

                        mNmae.setText(jsonObject.getString("name"));
                        mEmail.setText(jsonObject.getString("email"));

                    }

                    else
                    {

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
                Log.d("Login",""+error.getCause());
                // dialog.dismiss();
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
