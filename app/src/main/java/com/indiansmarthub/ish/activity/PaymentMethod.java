package com.indiansmarthub.ish.activity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.indiansmarthub.ish.MainActivity;
import com.indiansmarthub.ish.PaymentPackage.AppEnvironment;
import com.indiansmarthub.ish.PaymentPackage.AppPreference;
import com.indiansmarthub.ish.R;
import com.indiansmarthub.ish.adapter.CartAdapter;
import com.indiansmarthub.ish.custom.FilePath;
import com.indiansmarthub.ish.custom.GeneralCode;
import com.indiansmarthub.ish.javaclass.BottomMenuHelper;
import com.indiansmarthub.ish.model.Addtocart;
import com.indiansmarthub.ish.model.CartItems;
import com.indiansmarthub.ish.model.Count;
import com.indiansmarthub.ish.model.GeneralModel;
import com.indiansmarthub.ish.model.ModelGetPayment;
import com.indiansmarthub.ish.model.ModelResponseWalletBalance;
import com.indiansmarthub.ish.model.ModelWalletBalance;
import com.indiansmarthub.ish.retrofit.NetworkClient;
import com.indiansmarthub.ish.retrofit.NetworkService;
import com.indiansmarthub.ish.sqlite.DatabaseHandler;
import com.payumoney.core.PayUmoneyConfig;
import com.payumoney.core.PayUmoneyConstants;
import com.payumoney.core.PayUmoneySdkInitializer;
import com.payumoney.core.entity.TransactionResponse;
import com.payumoney.sdkui.ui.utils.PayUmoneyFlowManager;
import com.payumoney.sdkui.ui.utils.ResultModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.indiansmarthub.ish.MainActivity.mBottomNavigationView;

public class PaymentMethod extends AppCompatActivity implements View.OnClickListener{

    DatabaseHandler databaseHandler;

    String method = "", subTotal = "", totalamount;
    TextView tvGrandTotalPayment, continuePayment, tvSubTotalPayment, tvSkyBalance, tvUsedCash, file;
    TextView mShipping, mCgst, mSgst, mTotalAmount, mGrandTotal;
    LinearLayout layoutPaymentCash, layoutAddCash, layoutUsedCashTotal;
    RadioButton paymentCOD;
    RadioGroup radioGroupPayment;
    List<ModelGetPayment> paymentList;
    List<ModelWalletBalance> WalletCashes;
    SharedPreferences prefManager;
    CheckBox checkboxwalletCash;
    EditText etCashEntered;
    private String selectedFilePath;
    double grandTotal = 0;
    private Toolbar mToolbar;
    double subtotalval=0.0,gstval=0,grandtotalval=0,transactionval=0;
    private static final String TAG = PaymentMethod.class.getSimpleName();
    private static final int PICK_FILE_REQUEST = 1;
    int bytesRead, bytesAvailable, bufferSize;
    DataOutputStream dataOutputStream;
    File selectedFile;
    Uri selectedFileUri;
    private ProgressDialog progressDialog;
    RecyclerView mRecyclerView;
    private List<Addtocart> getcartarray;
    private int strGrandTotal;
    int pointsToBeUsed;
    private int wallet;
    String shipment_id="";
    RadioButton walletcheck,cardcheck,deliverycheck;
    String address_id="";
    LinearLayout transaction;
      double withtransactionTotal=0;
      Bitmap documentbit=null;
      String type="",salt,key;
    double amountdb;
    double amount;
    String txnId;

    String phone,productName,firstName,email;
    private boolean isDisableExitConfirmation = false;
    private AppPreference mAppPreference;


    private PayUmoneySdkInitializer.PaymentParam mPaymentParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_method);
        mAppPreference=new AppPreference();
        transaction=findViewById(R.id.transaction);
        mToolbar = findViewById(R.id.toolBar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        prefManager = PaymentMethod.this.getSharedPreferences("ISH", MODE_PRIVATE);
try
{
    Intent intent=getIntent();
    address_id=intent.getStringExtra("id");
}
catch (Exception e)
{
    e.printStackTrace();
}
        init();

        if (GeneralCode.isConnectingToInternet(PaymentMethod.this)) {
            walleDetail();
        } else {
            GeneralCode.showDialog(PaymentMethod.this);
        }

        //callCartItemsApi(prefManager.getString("cust_id", ""));

        paymentCOD.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    method = paymentList.get(0).getMethod();
                }
            }
        });
    }


    private void init() {
        databaseHandler = new DatabaseHandler(PaymentMethod.this);
        paymentList = new ArrayList<>();
        WalletCashes = new ArrayList<>();
      //  tvFileName = findViewById(R.id.tvFileName);
        radioGroupPayment = findViewById(R.id.radioGroupPayment);
        paymentCOD = findViewById(R.id.paymentCOD);
        tvGrandTotalPayment = findViewById(R.id.tvGrandTotalPayment);
        continuePayment = findViewById(R.id.continuePayment);
        tvSubTotalPayment = findViewById(R.id.tvSubTotalPayment);
     //   checkboxwalletCash = findViewById(R.id.checkboxwalletCash);
        layoutPaymentCash = findViewById(R.id.layoutPaymentCash);
      //  etCashEntered = findViewById(R.id.etCashEntered);
        tvSkyBalance = findViewById(R.id.tvSkyBalance);
        tvUsedCash = findViewById(R.id.tvUsedCash);
        layoutAddCash = findViewById(R.id.layoutAddCash);
        layoutUsedCashTotal = findViewById(R.id.layoutUsedCashTotal);

        mRecyclerView = findViewById(R.id.rvcartpayment);
        findViewById(R.id.btnUplodaFile).setOnClickListener(this);
       file=findViewById(R.id.file);
       // findViewById(R.id.btnAtech).setOnClickListener(this);
      //  subTotal = getIntent().getStringExtra("subTotal");
      //  tvSubTotalPayment.setText("\u20b9 " + String.format("%.2f", Double.parseDouble(subTotal)));


        //
        mShipping = findViewById(R.id.tvShipping);
        mCgst = findViewById(R.id.tvCgst);
        mSgst = findViewById(R.id.tvSgst);
        mTotalAmount = findViewById(R.id.tvSubTotalPayment);
        mGrandTotal = findViewById(R.id.tvgrandtotal);
        walletcheck=findViewById(R.id.rd1);
        cardcheck=findViewById(R.id.rd2);
        deliverycheck=findViewById(R.id.rd3);
        deliverycheck.setVisibility(View.GONE);
        mShipping.setText("Excluding");
        walletcheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardcheck.setChecked(false);
                transaction.setVisibility(View.GONE);
                deliverycheck.setChecked(false);
                grandtotalval=subtotalval+gstval;
                mGrandTotal.setText(""+grandtotalval);
                mSgst.setVisibility(View.GONE);


            }
        });
        cardcheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                walletcheck.setChecked(false);
                deliverycheck.setChecked(false);
                transaction.setVisibility(View.VISIBLE);
                double transaction=(grandtotalval/100)*2;
                mSgst.setVisibility(View.VISIBLE);
                mSgst.setText(""+transaction);
                withtransactionTotal=grandtotalval+transaction;
                double totalval=subtotalval+gstval;
                mGrandTotal.setText(""+withtransactionTotal);


            }
        });

        deliverycheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardcheck.setChecked(false);
                walletcheck.setChecked(false);

            }
        });
        getDetail();

        if (prefManager.getBoolean("isMembership", false)) {
        }

        if (prefManager.getBoolean("isMembership", false)) {
        }
    }
    public void walleDetail()
    {
        final String token=prefManager.getString("cust_id","");
        final String url="http://52.66.136.244/api/v1/wallet";
        RequestQueue requestQueue = Volley.newRequestQueue(PaymentMethod.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject object= null;
                try {

                    object = new JSONObject(response);
                    String status=object.getString("success");
                    String amount=object.getString("amount");
                    amountdb=Double.parseDouble(amount);
                    if(!status.equals("")) {
                        continuePayment.setVisibility(View.VISIBLE);
                        continuePayment.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                AlertDialog.Builder builder;
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                    builder = new AlertDialog.Builder(PaymentMethod.this);
                                } else {
                                    builder = new AlertDialog.Builder(PaymentMethod.this);
                                }

                                builder.setTitle("Place Order")
                                        .setMessage("Are you sure you want to Place Order?")
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (walletcheck.isChecked()) {
                                                    type = "Wallet";
                                                    double amt = 0;
                                                    try {
                                                        amt = Double.parseDouble(mGrandTotal.getText().toString());
                                                    } catch (Exception e) {
                                                        e.printStackTrace();
                                                    }
                                                    if (amt > amountdb) {
                                                Toast.makeText(PaymentMethod.this,"you dont have sufficiant balance",Toast.LENGTH_LONG).show();
                                                    } else {
                                                        if (documentbit == null) {
                                                            sendDetail();

                                                        } else {
                                                            uploadBitmap(documentbit);
                                                        }
                                                    }
                                                }
                                                else
                                                {
                                                    type="Payu";
                                                    launchPayUMoneyFlow();
                                                }
                                                //Toast.makeText(getActivity(),"Data has been Post Successfully",Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                // do nothing
                                            }
                                        })
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .show();


                            }
                        });
                        //  }


                        tvSkyBalance.setText("Your wallet balance is       \u20b9 " + amount);

//                        cart=""+getCarts.length();
//                        getWishList();
                    }
                    else
                    {
                        //Toast.makeText(PaymentMethod.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    // dialog.dismiss();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Login",""+error.getCause());
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

    private void getCountApi() {

        NetworkService networkService = NetworkClient.getClient().create(NetworkService.class);
        //  Call<AcHomeProducts> categoriesCall = networkService.getAcProducts("NXqXAgFZA0jiWp5t6+=lGpgWJXEkbo", prefManager.getString("cust_id", ""));
        Call<Count> categoriesCall = networkService.getcount("NXqXAgFZA0jiWp5t6+=lGpgWJXEkbo",prefManager.getString("cust_id", ""));

        categoriesCall.enqueue(new Callback<Count>() {
            @Override
            public void onResponse(Call<Count> call, final Response<Count> response) {
                if (response.body() != null) {
                    if (response.body().getSuccess().equals("1")) {
                        if (response.body().getTotalQty() != null) {
                            //   llOurServices.setVisibility(View.VISIBLE);

                            try {

                                mShipping.setText("\u20b9 " +response.body().getShipping());
                                mCgst.setText("\u20b9 " +response.body().getCGST());
                                mSgst.setText("\u20b9 " +response.body().getSGST());
                                mTotalAmount.setText("\u20b9 " +response.body().getTotalamount());
                                mGrandTotal.setText("\u20b9 " +response.body().getGrandtotal());
                                strGrandTotal = Integer.parseInt(response.body().getGrandtotal());
                                if (strGrandTotal<=wallet){
                                    etCashEntered.setHint(String.valueOf(strGrandTotal));
                                }
                                totalamount = String.valueOf(response.body().getGrandtotal());
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    } else {

                        Log.e("tag_count", response.body().getMessage());
                        //  Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    }
                } else {

                   // Log.e("tag_count", "Something went wrong...!!");
                    //  Toast.makeText(getActivity(), "Something went wrong...!!", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<Count> call, Throwable t) {

               // Log.e("tag_count1", "Something went wrong...!!");
                //  Toast.makeText(getActivity(), "Something went wrong...!!", Toast.LENGTH_SHORT).show();

            }
        });

    }


    private void setPaymentMethod() {
        progressDialog = new ProgressDialog(PaymentMethod.this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        NetworkService networkService = NetworkClient.getClient().create(NetworkService.class);
        Call<GeneralModel> responseCall = networkService.setPaymentMethod("NXqXAgFZA0jiWp5t6+=lGpgWJXEkbo", prefManager.getString("cust_id", ""), "setpayment", "cashondelivery");
        Log.e("tag_params", "NXqXAgFZA0jiWp5t6+=lGpgWJXEkbo"+","+ prefManager.getString("cust_id", "")+","+ "setpayment"+","+ "cashondelivery");

        responseCall.enqueue(new Callback<GeneralModel>() {
            @Override
            public void onResponse(Call<GeneralModel> call, Response<GeneralModel> response) {

                if (response.body() != null) {
                    if (response.body().getSuccess().equals("1")) {

                        Log.e("tag_res", response.body().getMessage());
                        if (checkboxwalletCash.isChecked()) {
                            tvSkyBalance.setVisibility(View.VISIBLE);
                            if (wallet !=0) {

                                if (etCashEntered.getText().toString().isEmpty()) {

                                    if (strGrandTotal<=wallet){
                                        pointsToBeUsed = strGrandTotal;
                                        placeYourOrder(pointsToBeUsed);
                                    }
                                    else {
                                        pointsToBeUsed = wallet;
                                        placeYourOrder(pointsToBeUsed);
                                    }

                                }
                                else {
                                    int amountEnter = Integer.parseInt(etCashEntered.getText().toString());
                                    if (amountEnter<=wallet){
                                        if (amountEnter<=strGrandTotal){
                                            pointsToBeUsed=amountEnter;
                                            placeYourOrder(pointsToBeUsed);
                                        }
                                        else {
                                            Toast.makeText(PaymentMethod.this, "Please enter correct amount!", Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                    else {
                                        Toast.makeText(PaymentMethod.this, "You don't have sufficient balance.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            } else {
                                pointsToBeUsed =0;
                                placeYourOrder(pointsToBeUsed);
                            }

                        }
                        else {
                            pointsToBeUsed =0;
                            placeYourOrder(pointsToBeUsed);
                        }

                        progressDialog.dismiss();
                    } else {
                        Log.e("tag_pm", response.body().getMessage());
                        progressDialog.dismiss();
                        Toast.makeText(PaymentMethod.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                    }
                }      else {
                    progressDialog.dismiss();
                    Toast.makeText(PaymentMethod.this, response.body().getMessage(), Toast.LENGTH_LONG).show();

                }

            }

            @Override
            public void onFailure(Call<GeneralModel> call, Throwable t) {

                progressDialog.dismiss();
              //  Toast.makeText(PaymentMethod.this, "Something went wrong...!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void placeYourOrder(int skmused) {

        Log.e("tag_skmused", String.valueOf(skmused));

        progressDialog = new ProgressDialog(PaymentMethod.this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        NetworkService networkService = NetworkClient.getClient().create(NetworkService.class);
        Call<GeneralModel> responseCall = networkService.placeOrders("NXqXAgFZA0jiWp5t6+=lGpgWJXEkbo", prefManager.getString("cust_id", ""), String.valueOf(skmused));

        Log.e("tag_paramspo", "NXqXAgFZA0jiWp5t6+=lGpgWJXEkbo"+","+ prefManager.getString("cust_id", "")+","+ String.valueOf(skmused));

        responseCall.enqueue(new Callback<GeneralModel>() {
            @Override
            public void onResponse(Call<GeneralModel> call, Response<GeneralModel> response) {

                Log.e("tag_rop", response.body().getMessage());
                if (response.body() != null) {
                    if (response.body().getSuccess().equals("1")) {
                        if (!prefManager.getBoolean("isMembership", false)) {
                            SharedPreferences.Editor editor = prefManager.edit();
                            editor.putBoolean("isMembership", true);
                            editor.apply();
                        }
                        Toast.makeText(PaymentMethod.this,response.body().getMessage(),Toast.LENGTH_LONG).show();
                        databaseHandler.emptyCart();
                        Intent intent = new Intent(PaymentMethod.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                        progressDialog.dismiss();
                    }
                    else {
                        Log.e("tag_order", response.body().getMessage());

                        progressDialog.dismiss();
                        Toast.makeText(PaymentMethod.this, response.body().getMessage(), Toast.LENGTH_LONG).show();


                    }
                } else {
                    progressDialog.dismiss();
                    //Toast.makeText(PaymentMethod.this, "Something went wrong...!!", Toast.LENGTH_SHORT).show();

                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<GeneralModel> call, Throwable t) {

                progressDialog.dismiss();
               // Toast.makeText(PaymentMethod.this, "Something went wrong...!!", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnUplodaFile:
                showFileChooser();
                if (selectedFilePath != null) {

                    if(ActivityCompat.checkSelfPermission(PaymentMethod.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(PaymentMethod.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                        ActivityCompat.requestPermissions(PaymentMethod.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                        // this will request for permission when permission is not true
                    }else{
                        // Download code here
                       // uploadFile(selectedFilePath);
                    }
                } else {
                    Toast.makeText(PaymentMethod.this, "Please choose a File First", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void showFileChooser() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, PICK_FILE_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == PICK_FILE_REQUEST) {
                if (data == null) {
                    //no data present
                    return;
                }
                try {
                    selectedFileUri = data.getData();
                    documentbit = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedFileUri);
                    file.setText("Document attached");
                   // uploadBitmap(bitmap);
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
            if (requestCode == PayUmoneyFlowManager.REQUEST_CODE_PAYMENT && resultCode == RESULT_OK && data !=
                    null) {
                TransactionResponse transactionResponse = data.getParcelableExtra(PayUmoneyFlowManager
                        .INTENT_EXTRA_TRANSACTION_RESPONSE);

                ResultModel resultModel = data.getParcelableExtra(PayUmoneyFlowManager.ARG_RESULT);

                // Check which object is non-null
                if (transactionResponse != null && transactionResponse.getPayuResponse() != null) {
                    if (transactionResponse.getTransactionStatus().equals(TransactionResponse.TransactionStatus.SUCCESSFUL)) {
                        //Success Transaction
                    } else {
                        //Failure Transaction
                    }

                    // Response from Payumoney
                    String payuResponse = transactionResponse.getPayuResponse();
                    try {
                        JSONObject object=new JSONObject(payuResponse);
                        JSONObject res=object.getJSONObject("result");
                        String status=res.getString("status");
                        if(status.equalsIgnoreCase("success"))
                        {
                            if (documentbit == null) {
                                sendDetail();

                            } else {
                                uploadBitmap(documentbit);
                            }

                        }
                    }catch (Exception e)
                    {
                        e.printStackTrace();
                    }

                    // Response from SURl and FURL
//                    String merchantResponse = transactionResponse.getTransactionDetails();
//
//                    new android.app.AlertDialog.Builder(this)
//                            .setCancelable(false)
//                            .setMessage("Payu's Data : " + payuResponse + "\n\n\n Merchant's Data: " + merchantResponse)
//                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int whichButton) {
//                                    dialog.dismiss();
//                                }
//                            }).show();

                } else if (resultModel != null && resultModel.getError() != null) {
                    Log.d(TAG, "Error response : " + resultModel.getError().getTransactionResponse());
                } else {
                    Log.d(TAG, "Both objects are null!");
                }
            }


        }
    }
    private String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(this, contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private void getDetail()
    {
       // final ProgressDialog dialog = ProgressDialog.show(LoginActivity.this, "", "Proccessing....Please wait");
        final String token=prefManager.getString("cust_id","");

        final String url="http://52.66.136.244/api/v1/choosepayment";
        RequestQueue requestQueue = Volley.newRequestQueue(PaymentMethod.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject object= null;
                try {

                    object = new JSONObject(response);
                    String status=object.getString("success");
                   // JSONObject statusjson=object.getJSONObject("shipmentid");
                     String gst=object.getString("gst");
                     String shipping_id=object.getString("shipmentid");
                     String subtotal=object.getString("subtotal");
                     String grandtotal=object.getString("grandtotal");
                    subtotalval=Double.parseDouble(subtotal);
                    gstval=Double.parseDouble(gst);
                    mCgst.setText(gst);
                    double totalval=subtotalval+gstval;
                    grandtotalval=totalval;
                    tvGrandTotalPayment.setVisibility(View.VISIBLE);
                    tvGrandTotalPayment.setText(""+subtotalval);
                    mGrandTotal.setText(""+totalval);
                    tvSubTotalPayment.setVisibility(View.VISIBLE);
                    tvSubTotalPayment.setText(subtotal);
                    // JSONArray jsonArray=object.getJSONArray("details");
                    if(!status.equals("")) {
//                        String token="Bearer "+statusjson.getString("token");
//                        Toast.makeText(PaymentMethod.this, "update successfully", Toast.LENGTH_SHORT).show();

                    }

                    else
                    {

                        Toast.makeText(PaymentMethod.this, "faild", Toast.LENGTH_SHORT).show();
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
    private void uploadBitmap(final Bitmap bitmap) {

        //getting the tag from the edittext
        final String cusid = prefManager.getString("cust_id","");
        //our custom volley request
        Volleymultipart volleyMultipartRequest = new Volleymultipart(Request.Method.POST, "http://52.66.136.244/api/v1/paymentsuccess?shipped_to_address="+address_id+"&type="+type,
                new com.android.volley.Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {
                            JSONObject obj = new JSONObject(new String(response.data));
                            Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                                Toast.makeText(PaymentMethod.this, "order successfully place", Toast.LENGTH_SHORT).show();
                                String ordern=obj.getString("order");
                                payalert(ordern);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {

            /*
             * If you want to add more parameters with the image
             * you can do it here
             * here we have only one parameter with the image
             * which is tags
             * */
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("shipped_to_address", "143");

                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", cusid);
                headers.put("Content-Type", "application/json");
                return headers;
            }

            /*
             * Here we are passing image by renaming it with a unique name
             * */
            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                params.put("document", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));

                return params;
            }
        };

        //adding the request to volley
        Volley.newRequestQueue(this).add(volleyMultipartRequest);
    }

    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 50, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
public void sendDetail()
{
    final String token=prefManager.getString("cust_id","");

    final String url="http://52.66.136.244/api/v1/paymentsuccess?shipped_to_address="+address_id+"&type="+type;
    RequestQueue requestQueue = Volley.newRequestQueue(PaymentMethod.this);
    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            JSONObject object= null;
            try {

                object = new JSONObject(response);
                String status=object.getString("success");
                if(status.equals("1")) {
                        Toast.makeText(PaymentMethod.this, "order successfully place", Toast.LENGTH_SHORT).show();
                    String ordern=object.getString("order");
                    payalert(ordern);
                }

                else
                {

                    Toast.makeText(PaymentMethod.this, "faild", Toast.LENGTH_SHORT).show();
                   }
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
    private void launchPayUMoneyFlow() {

        PayUmoneyConfig payUmoneyConfig = PayUmoneyConfig.getInstance();

        //Use this to set your custom text on result screen button
        payUmoneyConfig.setDoneButtonText(("page status success"));

        //Use this to set your custom title for the activity
        payUmoneyConfig.setPayUmoneyActivityTitle("Indian Smart Hub");

        payUmoneyConfig.disableExitConfirmation(isDisableExitConfirmation);

        PayUmoneySdkInitializer.PaymentParam.Builder builder = new PayUmoneySdkInitializer.PaymentParam.Builder();

         amount =Double.parseDouble(mGrandTotal.getText().toString());
        try {
            //amount = Double.parseDouble(amount_et.getText().toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
        txnId = System.currentTimeMillis() + "";
        //String txnId = "TXNID720431525261327973";

         phone = "8607646841";
        productName = "indian smart hub products";
         firstName = "jagsun";
         email = "support@indiansmarthub.net";
        String udf1 = "";
        String udf2 = "";
        String udf3 = "";
        String udf4 = "";
        String udf5 = "";
        String udf6 = "";
        String udf7 = "";
        String udf8 = "";
        String udf9 = "";
        String udf10 = "";
        ((BaseApplication) getApplication()).setAppEnvironment(AppEnvironment.PRODUCTION);

        AppEnvironment appEnvironment = ((BaseApplication) getApplication()).getAppEnvironment();
        salt=appEnvironment.salt();
        key=appEnvironment.merchant_Key();
        builder.setAmount(String.valueOf(amount))
                .setTxnId(txnId)
                .setPhone(phone)
                .setProductName(productName)
                .setFirstName(firstName)
                .setEmail(email)
                .setsUrl(appEnvironment.surl())
                .setfUrl(appEnvironment.furl())
                .setUdf1(udf1)
                .setUdf2(udf2)
                .setUdf3(udf3)
                .setUdf4(udf4)
                .setUdf5(udf5)
                .setUdf6(udf6)
                .setUdf7(udf7)
                .setUdf8(udf8)
                .setUdf9(udf9)
                .setUdf10(udf10)
                .setIsDebug(appEnvironment.debug())
                .setKey(appEnvironment.merchant_Key())
                .setMerchantId(appEnvironment.merchant_ID());

        try {
            mPaymentParams = builder.build();

            /*
             * Hash should always be generated from your server side.
             * */
            //    generateHashFromServer(mPaymentParams);

            /*            *//**
             * Do not use below code when going live
             * Below code is provided to generate hash from sdk.
             * It is recommended to generate hash from server side only.
             * */
           // mPaymentParams = calculateServerSideHashAndInitiatePayment1(mPaymentParams);
            gethash();
               // PayUmoneyFlowManager.startPayUMoneyFlow(mPaymentParams,PaymentMethod.ththiis, R.style.AppTheme_default,false);

        } catch (Exception e) {
            // some exception occurred
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            //payNowButton.setEnabled(true);
        }
    }
    private PayUmoneySdkInitializer.PaymentParam calculateServerSideHashAndInitiatePayment1(final PayUmoneySdkInitializer.PaymentParam paymentParam) {

        StringBuilder stringBuilder = new StringBuilder();
        HashMap<String, String> params = paymentParam.getParams();
        stringBuilder.append(params.get(PayUmoneyConstants.KEY) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.TXNID) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.AMOUNT) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.PRODUCT_INFO) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.FIRSTNAME) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.EMAIL) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.UDF1) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.UDF2) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.UDF3) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.UDF4) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.UDF5) + "||||||");

        AppEnvironment appEnvironment = AppEnvironment.PRODUCTION;
        stringBuilder.append(appEnvironment.salt());

        String hash = hashCal(stringBuilder.toString());
        paymentParam.setMerchantHash(hash);

        return paymentParam;
    }
    public static String hashCal(String str) {
        byte[] hashseq = str.getBytes();
        StringBuilder hexString = new StringBuilder();
        try {
            MessageDigest algorithm = MessageDigest.getInstance("SHA-512");
            algorithm.reset();
            algorithm.update(hashseq);
            byte messageDigest[] = algorithm.digest();
            for (byte aMessageDigest : messageDigest) {
                String hex = Integer.toHexString(0xFF & aMessageDigest);
                if (hex.length() == 1) {
                    hexString.append("0");
                }
                hexString.append(hex);
            }
        } catch (NoSuchAlgorithmException ignored) {
        }
        return hexString.toString();
    }
    public void gethash()
    {
        //    final ProgressDialog dialog = ProgressDialog.show(MainActivity.this, "", "Proccessing....Please wait");

        final String url="http://52.66.136.244/api/v1/checkouthash";
        RequestQueue requestQueue = Volley.newRequestQueue(PaymentMethod.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject object= null;
                try {
                    String status=response;
                    if(!status.equals("")) {
                        mPaymentParams.setMerchantHash(status);
                        PayUmoneyFlowManager.startPayUMoneyFlow(mPaymentParams, PaymentMethod.this, AppPreference.selectedTheme, mAppPreference.isOverrideResultScreen());
                    }

                    else
                    {

                        Toast.makeText(PaymentMethod.this, "fail to update", Toast.LENGTH_SHORT).show();

                    }

                    ///dialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();

                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Login",""+error.getCause());

                //dialog.dismiss();
            }
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                String httpPostBody="email="+email+"&txnId="+txnId+"&mobile="+phone+"&name="+firstName+"&amount="+amount+"&productinfo="+productName+"&key="+ key+"&salt="+salt;
                return httpPostBody.getBytes();
            }


        };
        requestQueue.add(stringRequest);
    }
public  void payalert(String order)
{
    AlertDialog.Builder builder;
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        builder = new AlertDialog.Builder(PaymentMethod.this);
    } else {
        builder = new AlertDialog.Builder(PaymentMethod.this);
    }

    builder.setTitle("Payment status")
            .setMessage("Your order successfully place order no :"+order)
            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent=new Intent(PaymentMethod.this,MainActivity.class);
                    finish();
                     startActivity(intent);
                    //Toast.makeText(getActivity(),"Data has been Post Successfully",Toast.LENGTH_SHORT).show();
                }
            })
            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // do nothing
                }
            })
          //  .setIcon(android.R.drawable.ic_dialog_alert)
            .show();


}





}





