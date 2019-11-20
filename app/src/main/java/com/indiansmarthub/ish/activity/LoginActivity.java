package com.indiansmarthub.ish.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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
import com.indiansmarthub.ish.custom.GeneralCode;
import com.indiansmarthub.ish.fragments.Cart;
import com.indiansmarthub.ish.model.ModelResponseRegister;
import com.indiansmarthub.ish.retrofit.NetworkClient;
import com.indiansmarthub.ish.retrofit.NetworkService;
import com.indiansmarthub.ish.sqlite.DatabaseHandler;

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
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etLUserName, etLPassword;
    private SharedPreferences prefManager = null;
    String first_name, last_name, customer_id;
    SharedPreferences.Editor editor;
    String strcartflag;
    TextView mSkipSignIn;
    DatabaseHandler databaseHandler;
    private String id_, sku_;
    private ProgressDialog progressDialog;
    TextView mForgetPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        prefManager = this.getSharedPreferences("ISH", this.MODE_PRIVATE);
        databaseHandler = new DatabaseHandler(LoginActivity.this);
        init();
    }

    private void init() {
        findViewById(R.id.tvLRegister).setOnClickListener(this);
        findViewById(R.id.btnLogin).setOnClickListener(this);
        findViewById(R.id.tvLForgotPass).setOnClickListener(this);
        etLUserName = findViewById(R.id.etLUserName);
        etLPassword = findViewById(R.id.etLPassword);
        mSkipSignIn = findViewById(R.id.skipsignin);
        mSkipSignIn.setOnClickListener(this);
        Intent intent = getIntent();
        if (intent!=null){
            strcartflag = intent.getStringExtra("cartflag");
        }
        if (!prefManager.getString("id", "").isEmpty()) {
            id_ = prefManager.getString("id", "");
            sku_ = prefManager.getString("sku", "");

            } else  {
                // Toast.makeText(DetailsActivity.this, "Go to cart", Toast.LENGTH_SHORT).show();
            }
        }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvLRegister:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;

            case R.id.btnLogin:
                validationLogin();
                break;

            case R.id.tvLForgotPass:
                startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
                break;

            case R.id.skipsignin:
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                break;
        }
    }

    private void validationLogin() {
        if (GeneralCode.isConnectingToInternet(LoginActivity.this)) {
            if (GeneralCode.isEmptyString(etLUserName.getText().toString())) {
                Toast.makeText(this, "Enter email...!!", Toast.LENGTH_SHORT).show();

            } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(etLUserName.getText().toString()).matches())
            {
                Toast.makeText(this, "Enter valid email...!!", Toast.LENGTH_SHORT).show();

            } else if (GeneralCode.isEmptyString(etLPassword.getText().toString().trim())) {
                Toast.makeText(this, "Enter password...!!", Toast.LENGTH_SHORT).show();
            }
            else
            {
                sendLogin();
            }
        } else {
            GeneralCode.showDialog(LoginActivity.this);
        }
    }

    private void sendLogin()
    {
        final ProgressDialog dialog = ProgressDialog.show(LoginActivity.this, "", "Proccessing....Please wait");

        final String url="http://52.66.136.244/api/v1/login?email="+""+etLUserName.getText().toString()+""+"&password="+etLPassword.getText().toString();
        RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject object= null;
                try {

                    object = new JSONObject(response);
                    String status=object.getString("success");
                    JSONObject statusjson=new JSONObject(status);
                   // JSONArray jsonArray=object.getJSONArray("details");
                    if(!status.equals("")) {
                        String token="Bearer "+statusjson.getString("token");
                        Toast.makeText(LoginActivity.this, "update successfully", Toast.LENGTH_SHORT).show();
                        editor = prefManager.edit();
                        editor.clear();
                        editor.putBoolean("isMembership", true);
                        editor.putString("cust_id", token);
                        Log.e("catid", token);
                        editor.apply();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }

                    else
                    {

                        Toast.makeText(LoginActivity.this, "fail to update", Toast.LENGTH_SHORT).show();
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
                Log.d("Login",""+error.getCause());
                dialog.dismiss();
                //dialog.dismiss();
            }
        }) {

        };
        requestQueue.add(stringRequest);
    }
}
