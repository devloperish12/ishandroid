package com.indiansmarthub.ish.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
    Button btnSendMessage;
    ProgressBar mProgressbar;

    public Support() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_support, container, false);
       btnSendMessage= view.findViewById(R.id.btnsendMessage);
        init();
        return view;
    }

    private void init() {
        btnSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validation()){
                    /*getSupport();*/
                }
            }
        });
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
                if(validation()){
                    /*getSupport();*/
                }

                break;
        }

    }

    private boolean validation() {

            if (GeneralCode.isEmptyString(etCFirstName.getText().toString())) {
                etCFirstName.setError("Please enter your name.");
                return false;

            }  else if (GeneralCode.isEmptyString(etCEmail.getText().toString())) {
                etCEmail.setError("Please enter email id.");
                return false;


            } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(etCEmail.getText().toString()).matches()) {
                etCEmail.setError("Please enter valid email id.");
                return false;

            } else if (GeneralCode.isEmptyString(etCMobileNumber.getText().toString())) {
                etCMobileNumber.setError("Please enter mobile number.");
                return false;

            }  else if (GeneralCode.isEmptyString(etComment.getText().toString())) {
                etComment.setError("Please enter comment.");
                return false;

            }
        return true;
    }

/*    private void getSupport() {
        if(GeneralCode.isConnectingToInternet(getContext())) {


            mProgressbar.setVisibility(View.VISIBLE);

            NetworkService networkService = NetworkClient.getClient().create(NetworkService.class);
            Call<GeneralModel> responseCall = networkService.setContacts( etCFirstName.getText().toString(), etCEmail.getText().toString(), etCMobileNumber.getText().toString(), etComment.getText().toString());
            responseCall.enqueue(new Callback<GeneralModel>() {
                @Override
                public void onResponse(Call<GeneralModel> call, Response<GeneralModel> response) {
                    if (response.body() != null) {
                        if (response.body().getSuccess().equals("1")) {

                            Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_LONG).show();
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
        }else {
            Toast.makeText(getContext(), "Please check your internet.", Toast.LENGTH_SHORT).show();
        }
    }*/

}
