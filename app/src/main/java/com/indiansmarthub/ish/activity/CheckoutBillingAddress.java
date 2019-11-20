package com.indiansmarthub.ish.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
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
import com.indiansmarthub.ish.custom.GeneralCode;
import com.indiansmarthub.ish.model.GeneralModel;
import com.indiansmarthub.ish.model.ModelGetBillingAddress;
import com.indiansmarthub.ish.model.ModelGetShippingMethod;
import com.indiansmarthub.ish.model.ModelResponseGetShippingMethodd;
import com.indiansmarthub.ish.retrofit.NetworkClient;
import com.indiansmarthub.ish.retrofit.NetworkService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckoutBillingAddress extends AppCompatActivity {
    private Toolbar mToolbar;
    SharedPreferences prefManager;

    private EditText etFirstNameAddNewUserAddress, alternatenoed,etLastNameAddNewUserAddress, etStreetAddNewUserAddress, etStreetTwoAddNewUserAddress, etCityAddNewUserAddress, etRegionAddNewUserAddress, etPostcodeAddNewUserAddress, etCountryAddNewUserAddress, etTelephoneAddNewUserAddress;
    List<ModelGetShippingMethod> methodList;
    List<ModelGetBillingAddress> addressList, defaultaddressList;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout_billing_address);

        prefManager = CheckoutBillingAddress.this.getSharedPreferences("ISH", getApplicationContext().MODE_PRIVATE);
        mToolbar = findViewById(R.id.toolBar);
//        mToolbar.setTitle("Billing Address");
//        mToolbar.setNavigationIcon(R.drawable.left_arrow);
//        setSupportActionBar(mToolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        init();
        if (GeneralCode.isConnectingToInternet(CheckoutBillingAddress.this)) {

        } else {
            GeneralCode.showDialog(CheckoutBillingAddress.this);
        }
    }

    private void init() {
        etFirstNameAddNewUserAddress = findViewById(R.id.etFirstNameAddNewUserAddress);
        etLastNameAddNewUserAddress = findViewById(R.id.etLastNameAddNewUserAddress);
        etStreetAddNewUserAddress = findViewById(R.id.etStreetAddNewUserAddress);
        etStreetTwoAddNewUserAddress = findViewById(R.id.etStreetTwoAddNewUserAddress);
        etCityAddNewUserAddress = findViewById(R.id.etCityAddNewUserAddress);
        etRegionAddNewUserAddress = findViewById(R.id.etRegionAddNewUserAddress);
        etPostcodeAddNewUserAddress = findViewById(R.id.etPostcodeAddNewUserAddress);
        etCountryAddNewUserAddress = findViewById(R.id.etCountryAddNewUserAddress);
        etTelephoneAddNewUserAddress = findViewById(R.id.etTelephoneAddNewUserAddress);
        alternatenoed=findViewById(R.id.alternateed);
    }

    private void getCustomerAddressCreate() {
        progressDialog = new ProgressDialog(CheckoutBillingAddress.this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        NetworkService networkService = NetworkClient.getClient().create(NetworkService.class);
        Call<GeneralModel> responseCall = networkService.cutomerAddressCreate("NXqXAgFZA0jiWp5t6+=lGpgWJXEkbo", prefManager.getString("cust_id", ""), etFirstNameAddNewUserAddress.getText().toString(), etLastNameAddNewUserAddress.getText().toString(), etStreetAddNewUserAddress.getText().toString(), etStreetAddNewUserAddress.getText().toString(), etCityAddNewUserAddress.getText().toString(), etRegionAddNewUserAddress.getText().toString(), etPostcodeAddNewUserAddress.getText().toString(), etCountryAddNewUserAddress.getText().toString(), etTelephoneAddNewUserAddress.getText().toString(), "true", "true");
        responseCall.enqueue(new Callback<GeneralModel>() {
            @Override
            public void onResponse(Call<GeneralModel> call, Response<GeneralModel> response) {
                if (response.body() != null) {
                    //setBothBillingAddress(strretAddress);
                   if (response.body().getMessage().equals("successfully Create")){
                       startActivity(new Intent(CheckoutBillingAddress.this, CustomerAddressActivity.class));
                   }
                    progressDialog.dismiss();
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(CheckoutBillingAddress.this, "Something went wrong...!!", Toast.LENGTH_SHORT).show();


                }
            }
            @Override
            public void onFailure(Call<GeneralModel> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(CheckoutBillingAddress.this, "Something went wrong...!!", Toast.LENGTH_SHORT).show();

            }
        });

    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_newuser_billing, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }
        if (id == R.id.billingAdd) {
            validation();
        }
        return super.onOptionsItemSelected(item);
    }

    private void validation() {
        if (GeneralCode.isEmptyString(etFirstNameAddNewUserAddress.getText().toString())) {
            Toast.makeText(this, "Enter first name...!!", Toast.LENGTH_SHORT).show();

        } else if (GeneralCode.isEmptyString(etLastNameAddNewUserAddress.getText().toString())) {
            Toast.makeText(this, "Enter last name...!!", Toast.LENGTH_SHORT).show();

        } else if (GeneralCode.isEmptyString(etStreetAddNewUserAddress.getText().toString())) {
            Toast.makeText(this, "Enter street name...!!", Toast.LENGTH_SHORT).show();

        } else if (GeneralCode.isEmptyString(etCityAddNewUserAddress.getText().toString())) {
            Toast.makeText(this, "Enter city...!!", Toast.LENGTH_SHORT).show();

        } else if (GeneralCode.isEmptyString(etRegionAddNewUserAddress.getText().toString())) {
            Toast.makeText(this, "Enter region...!!", Toast.LENGTH_SHORT).show();

        } else if (GeneralCode.isEmptyString(etPostcodeAddNewUserAddress.getText().toString())) {
            Toast.makeText(this, "Enter postcode...!!", Toast.LENGTH_SHORT).show();

        } else if (GeneralCode.isEmptyString(etTelephoneAddNewUserAddress.getText().toString())) {
            Toast.makeText(this, "Enter telephone...!!", Toast.LENGTH_SHORT).show();

        } else if (etTelephoneAddNewUserAddress.getText().toString().length() < 10) {
            Toast.makeText(this, "Enter correct telephone...!!", Toast.LENGTH_SHORT).show();

        }else{

            sendAddress();

        }
    }
    public void sendAddress()
    {
        final String token=prefManager.getString("cust_id","");
        //final ProgressDialog dialog = ProgressDialog.show(getActivity(), "", "Proccessing....Please wait");

        final String url="http://52.66.136.244/api/v1/address?name="+etFirstNameAddNewUserAddress.getText().toString()+"&lname="+etLastNameAddNewUserAddress.getText().toString()+
                "&pincode="+etPostcodeAddNewUserAddress.getText().toString()+"&landmark="+etStreetAddNewUserAddress.getText().toString()+"" +
                "&locallity="+etStreetAddNewUserAddress.getText().toString()+"&address="+etStreetAddNewUserAddress.getText().toString()+"" +
                "&address1=123456&city="+etCityAddNewUserAddress.getText().toString()+"&" +
                "state="+etRegionAddNewUserAddress.getText().toString()+"" +
                "&zip=123456&contact_number="+etTelephoneAddNewUserAddress.getText().toString()+"&country=india&contact_number_attrenate=" +
                ""+alternatenoed.getText().toString();
        RequestQueue requestQueue = Volley.newRequestQueue(CheckoutBillingAddress.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject object= null;
                try {

                     object = new JSONObject(response);
                    String status=object.getString("success");
                  //  JSONArray getCarts=object.getJSONArray("cart");
                    // JSONArray jsonArray=object.getJSONArray("details");
                    if(!status.equals("")) {
                        try {
                            String id = object.getString("address");
                            //deleteAddress(id);
                            Intent intent = new Intent(CheckoutBillingAddress.this, PaymentMethod.class);
                            intent.putExtra("id",id);
                            startActivity(intent);
                        }catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                    else
                    {

                        Toast.makeText(CheckoutBillingAddress.this, "alreay in wishlist", Toast.LENGTH_SHORT).show();
                        //   dialog.dismiss();
                    }
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
                Log.d("Login",""+error.getCause());
                //dialog.dismiss();
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
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//              //  String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
//               Map<String, String> map = new HashMap<String, String>();
//                map.put("name",etFirstNameAddNewUserAddress.getText().toString());
//                map.put("lname",etLastNameAddNewUserAddress.getText().toString());
//               // map.put("last_name", etRLastName.getText().toString());
//                map.put("pincode",etPostcodeAddNewUserAddress.getText().toString());
//                map.put("landmark",etStreetAddNewUserAddress.getText().toString());
//                map.put("locallity","normal");
//                map.put("address'","normal");
//                map.put("address1","normal");
//                map.put("city",etCityAddNewUserAddress.getText().toString());
//                map.put("state'",etRegionAddNewUserAddress.getText().toString());
//                map.put("zip","normal");
//                map.put("contact_number",etTelephoneAddNewUserAddress.getText().toString());
//                map.put("country","india");
//                map.put("contact_number_attrenate","");
//
//                return map;
//
//            }
//            @Override
//            public byte[] getBody() throws AuthFailureError {
//                String httpPostBody="name="+etFirstNameAddNewUserAddress.getText().toString()+"&lname="+etLastNameAddNewUserAddress.getText().toString()+"" +
//                        "pincode="+etPostcodeAddNewUserAddress.getText().toString()+""+
//                "&landmark="+etStreetAddNewUserAddress.getText().toString()+""+
//                "&locallity="+etPostcodeAddNewUserAddress.getText().toString()+""+
//                "&address="+etPostcodeAddNewUserAddress.getText().toString()+""+
//                "&address1="+etPostcodeAddNewUserAddress.getText().toString()+""+
//                "&city="+etCityAddNewUserAddress.getText().toString()+""+
//                        "&state="+etRegionAddNewUserAddress.getText().toString()+""+
//                        "&pincode="+etPostcodeAddNewUserAddress.getText().toString()+""+
//                        "&contact_number="+etTelephoneAddNewUserAddress.getText().toString()+""+
//                        "&country=india"+
//
//                        "&contact_number_attrenate="+etTelephoneAddNewUserAddress.getText().toString();
//
//                // usually you'd have a field with some values you'd want to escape, you need to do it yourself if overriding getBody. here's how you do it
//                try {
//                    httpPostBody=httpPostBody+"&randomFieldFilledWithAwkwardCharacters="+ URLEncoder.encode("{{%stuffToBe Escaped/","UTF-8");
//                } catch (UnsupportedEncodingException exception) {
//                    Log.e("ERROR", "exception", exception);
//                    // return null and don't pass any POST string if you encounter encoding error
//                    return null;
//                }
//                return httpPostBody.getBytes();
//            }



        };
        requestQueue.add(stringRequest);
    }



}
