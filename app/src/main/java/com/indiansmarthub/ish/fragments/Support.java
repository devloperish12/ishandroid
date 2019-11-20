package com.indiansmarthub.ish.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.indiansmarthub.ish.MainActivity;
import com.indiansmarthub.ish.R;
import com.indiansmarthub.ish.custom.GeneralCode;
import com.indiansmarthub.ish.model.GeneralModel;
import com.indiansmarthub.ish.retrofit.NetworkClient;
import com.indiansmarthub.ish.retrofit.NetworkService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class Support extends Fragment implements View.OnClickListener {
    EditText etCFirstName, etCEmail, etCMobileNumber, etComment;
    View view;
    ProgressBar mProgressbar;

    public Support() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_support, container, false);
        view.findViewById(R.id.btnsendMessage).setOnClickListener(this);
        init();
        return view;
    }

    private void init() {
        etCFirstName = view.findViewById(R.id.etCFirstName);
        etCEmail = view.findViewById(R.id.etCEmail);
        etCMobileNumber = view.findViewById(R.id.etCMobileNumber);
        etComment = view.findViewById(R.id.etComment);
        mProgressbar = view.findViewById(R.id.progress_bar);
    }

    @Override
    public void onClick(View v) {
        switch (view.getId()){
            case R.id.btnsendMessage:
                validation();
                break;
        }

    }

    private void validation() {
        if (GeneralCode.isConnectingToInternet(getActivity())) {
            if (GeneralCode.isEmptyString(etCFirstName.getText().toString())) {
                Toast.makeText(getActivity(), "Enter Frist Name...!!", Toast.LENGTH_SHORT).show();

            }  else if (GeneralCode.isEmptyString(etCEmail.getText().toString())) {
                Toast.makeText(getActivity(), "Enter email...!!", Toast.LENGTH_SHORT).show();

            } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(etCEmail.getText().toString()).matches()) {
                Toast.makeText(getActivity(), "Enter valid email...!!", Toast.LENGTH_SHORT).show();

            } else if (GeneralCode.isEmptyString(etCMobileNumber.getText().toString())) {
                Toast.makeText(getActivity(), "Enter Mobile Number...!!", Toast.LENGTH_SHORT).show();

            }  else if (GeneralCode.isEmptyString(etComment.getText().toString())) {
                Toast.makeText(getActivity(), "Enter Comments...!!", Toast.LENGTH_SHORT).show();

            } else {
                getSupport();
            }
        } else {
            GeneralCode.showDialog(getActivity());
        }
    }

    private void getSupport() {

        mProgressbar.setVisibility(View.VISIBLE);

        NetworkService networkService = NetworkClient.getClient().create(NetworkService.class);
        Call<GeneralModel> responseCall = networkService.setContacts("NXqXAgFZA0jiWp5t6+=lGpgWJXEkbo",etCFirstName.getText().toString(),etCEmail.getText().toString(),etCMobileNumber.getText().toString(),etComment.getText().toString());
        responseCall.enqueue(new Callback<GeneralModel>() {
            @Override
            public void onResponse(Call<GeneralModel> call, Response<GeneralModel> response) {
                if (response.body() != null) {
                    if (response.body().getSuccess().equals("1")) {

                        Toast.makeText(getActivity(),response.body().getMessage(),Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);


                        mProgressbar.setVisibility(View.GONE);
                    } else {
                        mProgressbar.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    mProgressbar.setVisibility(View.GONE);
                } else {

                    mProgressbar.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<GeneralModel> call, Throwable t) {
                mProgressbar.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();

            }
        });
    }

}
