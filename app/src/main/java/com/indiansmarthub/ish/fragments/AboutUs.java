package com.indiansmarthub.ish.fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.indiansmarthub.ish.R;
import com.indiansmarthub.ish.adapter.AccountAdapter;
import com.indiansmarthub.ish.adapter.OurServicesAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutUs extends Fragment {

View view;
    private Toolbar mToolbar;
    EditText tvPName,etPEmail;
    private SharedPreferences prefManager ;


    public AboutUs() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_about_us, container, false);

        prefManager =getActivity().getSharedPreferences("ISH", getActivity().MODE_PRIVATE);
        init();

        return view;
    }

    private void init() {
        tvPName = view.findViewById(R.id.tvPName);
        etPEmail = view.findViewById(R.id.etPEmail);

        tvPName.setText(prefManager.getString("firs_name",""));
        etPEmail.setText(prefManager.getString("email",""));
    }


}
