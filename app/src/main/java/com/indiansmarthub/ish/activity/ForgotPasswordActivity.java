package com.indiansmarthub.ish.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.indiansmarthub.ish.R;
import com.indiansmarthub.ish.custom.GeneralCode;
import com.indiansmarthub.ish.model.ModelGenralResponse;
import com.indiansmarthub.ish.retrofit.NetworkClient;
import com.indiansmarthub.ish.retrofit.NetworkService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar mToolbar;
    private EditText etForgotPass;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        /*mToolbar = findViewById(R.id.toolBar);
        mToolbar.setTitle("Forgot Password");
        mToolbar.setNavigationIcon(R.drawable.left_arrow);
        setSupportActionBar(mToolbar);*/

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        init();
    }

    private void init() {
        findViewById(R.id.btnForgotPass).setOnClickListener(this);
        etForgotPass = findViewById(R.id.etForgotPass);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnForgotPass:
                vadidationForgot();
        }

    }

    private void vadidationForgot() {
        if (GeneralCode.isConnectingToInternet(ForgotPasswordActivity.this)) {
            if (GeneralCode.isEmptyString(etForgotPass.getText().toString())) {
                Toast.makeText(this, "Enter email...!!", Toast.LENGTH_SHORT).show();

            } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(etForgotPass.getText().toString()).matches()) {
                Toast.makeText(this, "Enter valid email...!!", Toast.LENGTH_SHORT).show();

            } else {
                setForgotPass();
            }
        } else {
            GeneralCode.showDialog(ForgotPasswordActivity.this);
        }
    }

    private void setForgotPass() {
        progressDialog = new ProgressDialog(ForgotPasswordActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        NetworkService networkService = NetworkClient.getClient().create(NetworkService.class);
        Call<ModelGenralResponse> responseCall = networkService.sendForgotPass("NXqXAgFZA0jiWp5t6+=lGpgWJXEkbo", etForgotPass.getText().toString());
        responseCall.enqueue(new Callback<ModelGenralResponse>() {
            @Override
            public void onResponse(Call<ModelGenralResponse> call, Response<ModelGenralResponse> response) {
                if (response.body() != null) {
                    if (response.body().getSuccess().equals("1")) {
                        Toast.makeText(ForgotPasswordActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ForgotPasswordActivity.this,LoginActivity.class));
                    } else {

                        progressDialog.dismiss();
                        Toast.makeText(ForgotPasswordActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    }
                    progressDialog.dismiss();
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(ForgotPasswordActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ModelGenralResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(ForgotPasswordActivity.this, "", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

}
