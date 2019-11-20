package com.indiansmarthub.ish.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.indiansmarthub.ish.MainActivity;
import com.indiansmarthub.ish.R;
import com.indiansmarthub.ish.custom.GeneralCode;
import com.indiansmarthub.ish.model.ModelResponseRegister;
import com.indiansmarthub.ish.retrofit.NetworkClient;
import com.indiansmarthub.ish.retrofit.NetworkService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar mToolbar;
    private EditText etRFirstName, etRLastName, etREmail, etRNumber, etRPassword, etRConfirmPassword;
    private ProgressDialog progressDialog;
    String log = "Registeractivity";
    private SharedPreferences prefManager = null;
    SharedPreferences.Editor editor;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mToolbar = findViewById(R.id.toolBar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        prefManager = this.getSharedPreferences("ISH", RegisterActivity.MODE_PRIVATE);
        //prefManager = this.getSharedPreferences("ISH", Splash.MODE_PRIVATE);

        init();
    }

    private void init() {
        findViewById(R.id.btnRegisiter).setOnClickListener(this);
        etRFirstName = findViewById(R.id.etRFirstName);
        etRLastName = findViewById(R.id.etRLastName);
        etREmail = findViewById(R.id.etREmail);
        etRNumber = findViewById(R.id.etRNumber);
        etRPassword = findViewById(R.id.etRPassword);
        etRConfirmPassword = findViewById(R.id.etRConfirmPassword);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnRegisiter:
                ValidationRegister();
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    private void ValidationRegister() {
        if (GeneralCode.isConnectingToInternet(RegisterActivity.this)) {
            if (GeneralCode.isEmptyString(etRFirstName.getText().toString())) {
                Toast.makeText(this, "Enter Firstname...!!", Toast.LENGTH_SHORT).show();

            } else if (GeneralCode.isEmptyString(etRLastName.getText().toString())) {
                Toast.makeText(this, "Enter Lastname...!!", Toast.LENGTH_SHORT).show();

            } else if (GeneralCode.isEmptyString(etREmail.getText().toString())) {
                Toast.makeText(this, "Enter email...!!", Toast.LENGTH_SHORT).show();

            } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(etREmail.getText().toString()).matches()) {
                Toast.makeText(this, "Enter valid email...!!", Toast.LENGTH_SHORT).show();

            } else if (GeneralCode.isEmptyString(etRNumber.getText().toString())) {
                Toast.makeText(this, "Enter phone number...!!", Toast.LENGTH_SHORT).show();

            } else if ((etRNumber.getText().toString().length() < 10)) {
                Toast.makeText(this, "Please enter valid 10 digit phone number...!!", Toast.LENGTH_SHORT).show();

            } else if (GeneralCode.isEmptyString(etRPassword.getText().toString().trim())) {
                Toast.makeText(this, "Enter password...!!", Toast.LENGTH_SHORT).show();


            } else if (GeneralCode.isPasswordValid(etRPassword.getText().toString().trim())) {
                Toast.makeText(this, "Please enter 6 or more characters in password...!!", Toast.LENGTH_SHORT).show();


            } else if (GeneralCode.isEmptyString(etRConfirmPassword.getText().toString().trim())) {
                Toast.makeText(this, "Enter Confirm password...!!", Toast.LENGTH_SHORT).show();

            } else if (!etRPassword.getText().toString().trim().equals(etRConfirmPassword.getText().toString())) {
                Toast.makeText(this, "Confirm password is not the same as password...!!", Toast.LENGTH_SHORT).show();

            } else {
                sendRegister();
            }

        } else {
            GeneralCode.showDialog(RegisterActivity.this);
        }
    }


//    private void sendRegister() {
//        progressDialog = new ProgressDialog(RegisterActivity.this);
//        progressDialog.setMessage("Loading...");
//        progressDialog.show();
//
//        NetworkService networkService = NetworkClient.getClient().create(NetworkService.class);
//        Call<ModelResponseRegister> responseCall = networkService.sendRegistration("normal", etREmail.getText().toString(), etRFirstName.getText().toString(), etRLastName.getText().toString(), etRPassword.getText().toString(),"1","null",etRConfirmPassword.getText().toString());
//        responseCall.enqueue(new Callback<ModelResponseRegister>() {
//            @Override
//            public void onResponse(Call<ModelResponseRegister> call, Response<ModelResponseRegister> response) {
//                if (response.body() != null) {
//                    if (response.body().getSuccess().equals("1")) {
//
////                            userid = response.body().getData().getiD();
////                            username = response.body().getData().getDisplayName();
////                            email = response.body().getData().getUserEmail();
////                            fname = response.body().getData().getFirstName();
////                            lname = response.body().getData().getLastName();
////                            house = response.body().getData().getHouse();
////                            nickname = response.body().getData().getNickname();
////                            year = response.body().getData().getYearGroup();
////                            profession = response.body().getData().getProfession();
////                            telephone = response.body().getData().getTelephoneNumber();
////                            prefManager = getSharedPreferences("userDetail", android.content.Context.MODE_PRIVATE);
////                            android.content.SharedPreferences.Editor ed = prefManager.edit();
////                            ed.putBoolean("isMembership", true);
////                            ed.putString("username", username);
////                            ed.putString("email",email);
////                            ed.putString("userid",userid);
////                            ed.putString("lname",lname);
////                            ed.putString("fname",fname);
////                            ed.putString("house",house);
////                            ed.putString("nickname",nickname);
////                            ed.putString("year",year);
////                            ed.putString("profession",profession);
////                            ed.putString("telephone",telephone);
////                            ed.apply();
//                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
//                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                        startActivity(intent);
//                        finish();
//
//
//                    } else {
//                        progressDialog.dismiss();
//                        Toast.makeText(RegisterActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
//
//                    }
//                    progressDialog.dismiss();
//                } else {
//                    progressDialog.dismiss();
//                    Toast.makeText(RegisterActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ModelResponseRegister> call, Throwable t) {
//                progressDialog.dismiss();
//                Toast.makeText(RegisterActivity.this, "", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    private void sendRegister() {
        final ProgressDialog dialog = ProgressDialog.show(RegisterActivity.this, "", "Proccessing....Please wait");
        //final String url= "http://52.66.136.244/api/v1/register";
        final String url = "http://52.66.136.244/api/v1/register?name="+etRFirstName.getText().toString() +etRLastName.getText().toString()+"&email="+etREmail.getText().toString()+"&password="+etRPassword.getText().toString()+"&confirm_password="+etRConfirmPassword.getText().toString()+"&user_type=normal";
        RequestQueue requestQueue = Volley.newRequestQueue(RegisterActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject object = null;
                try {
                    Toast.makeText(RegisterActivity.this, "Registred Successfully...", Toast.LENGTH_SHORT).show();
                    object = new JSONObject(response);
                    String status=object.getString("success");
                    JSONObject statusjson=new JSONObject(status);

                    if (!status.equals("")) {
                        String token="Bearer "+statusjson.getString("token");
                        Toast.makeText(RegisterActivity.this, "update successfully", Toast.LENGTH_SHORT).show();
                                                   //prefManager = getSharedPreferences("userDetail", android.content.Context.MODE_PRIVATE);

                       editor = prefManager.edit();
////
                        editor = prefManager.edit();
                        editor.clear();
                        editor.putBoolean("isMembership", true);
                        editor.putString("cust_id", token);
                        Log.e("catid", token);
                        editor.apply();
                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    } else {

                        Toast.makeText(RegisterActivity.this, "fail to update", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                    dialog.dismiss();
                    ///dialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                    dialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Login", "" + error.getCause());
                dialog.dismiss();
                if(error.networkResponse.statusCode==401){
                    Toast.makeText(RegisterActivity.this, "The email has already been taken.", Toast.LENGTH_SHORT).show();
                }
            }
        }) {
        };
        requestQueue.add(stringRequest);
    }
}
