package com.indiansmarthub.ish.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
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
import com.indiansmarthub.ish.activity.CustomerAddressActivity;
import com.indiansmarthub.ish.activity.DetailsActivity;
import com.indiansmarthub.ish.activity.LoginActivity;
import com.indiansmarthub.ish.adapter.CartAdapter;

import com.indiansmarthub.ish.javaclass.BottomMenuHelper;
import com.indiansmarthub.ish.model.Addtocart;
import com.indiansmarthub.ish.model.CartItems;
import com.indiansmarthub.ish.model.GeneralModel;
import com.indiansmarthub.ish.retrofit.NetworkClient;
import com.indiansmarthub.ish.retrofit.NetworkService;
import com.indiansmarthub.ish.sqlite.DatabaseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.indiansmarthub.ish.MainActivity.mBottomNavigationView;

/**
 * A simple {@link Fragment} subclass.
 */
public class Cart extends Fragment {
    View view;
    private SharedPreferences prefManager;
    private DatabaseHandler databaseHandler;
    private TextView tvSubTotal, tvChakeout;
    private RecyclerView rvCartView;
    private Button startShopping;
    ArrayList<Addtocart> viewCarts;
    double cartAmount = 0;
    private List<Addtocart> getcartarray;
    public static Boolean cartflag = false;
    private String strcartflag;
    String strpassingvalue;
    private   CartAdapter cartAdapter;
    LinearLayout nocartFound, viewcart_main_layout;
    private ProgressDialog progressDialog;
    ProgressBar mProgressBar;
double totalprice=0.0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_cart, container, false);

        prefManager = getActivity().getSharedPreferences("ISH", getActivity().MODE_PRIVATE);
        rvCartView = view.findViewById(R.id.rvCartView);
        init();


        return view;
    }
    public void docomthing(String id,double total,boolean isadd)
    {
        if(isadd)
        {
            totalprice=totalprice+total;
            tvSubTotal.setText(""+totalprice);
        }
        else
        {
            totalprice=totalprice-total;
            tvSubTotal.setText(""+totalprice);
        }

        int a=10;
    }


    private void init() {
        databaseHandler = new DatabaseHandler(getActivity());
       // mProgressBar = view.findViewById(R.id.progressbar);
        tvSubTotal = view.findViewById(R.id.tvSubTotal);
        tvChakeout = view.findViewById(R.id.tvChakeout);
        startShopping = view.findViewById(R.id.startShopping);
        nocartFound = view.findViewById(R.id.nocartFound);
        viewcart_main_layout = view.findViewById(R.id.viewcart_main_layout);

        viewCarts = new ArrayList<>();

        startShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), MainActivity.class));
            }
        });

        if (getArguments() != null) {
            strpassingvalue = getArguments().getString("cartflag");

        }


        // cart list with api or with sqlite database
       // if (prefManager.getBoolean("isMembership", true)) {
            if (!prefManager.getString("cust_id", "").isEmpty()) {
                addTocart();
               // callCartItemsApi(prefManager.getString("cust_id", ""));

            } else {
                addTocart();
            }
       // }


       tvChakeout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    if (!prefManager.getString("cust_id", "").isEmpty()) {
                        Intent intent = new Intent(getActivity(), CustomerAddressActivity.class);
                        intent.putExtra("subTotal", totalprice + "");
                        startActivity(intent);
                       // generateArray();
                    } else {
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        intent.putExtra("cartflag", "cartflag");
                        startActivity(intent);

                    }
                }

        });


//        if (cartflag==true){
//
//           Log.e("tag_cartflag", "true");
//            getCart();
//        }
//        else {
//            Log.e("tag_cartflag", "false");
//            callCartItemsApi(prefManager.getString("cust_id", ""));
//
//        }

    }


    public void callCartItemsApi(final String cust_id) {

         // mProgressBar.setVisibility(View.VISIBLE);
        NetworkService networkService = NetworkClient.getClient().create(NetworkService.class);
        Call<CartItems> cartItemsCall = networkService.getCartItems("NXqXAgFZA0jiWp5t6+=lGpgWJXEkbo", cust_id);

//        Log.e("tag_custid", prefManager.getString("cust_id", ""));

        cartItemsCall.enqueue(new Callback<CartItems>() {
            @Override
            public void onResponse(Call<CartItems> call, Response<CartItems> response) {
                if (response.body() != null) {
                    if (response.body().getSuccess().equals("1")) {
                        if (response.body().getAddtocart() != null) {
                            //mProgressBar.setVisibility(View.GONE);
                            viewcart_main_layout.setVisibility(View.VISIBLE);
                            nocartFound.setVisibility(View.GONE);
                            getcartarray = new ArrayList<>();
                            getcartarray.clear();
                            getcartarray = response.body().getAddtocart();

                            try {
                                BottomMenuHelper.showBadge(getActivity(), mBottomNavigationView, R.id.navigation_cart, response.body().getTotalqty() + "");
                            }catch (Exception e){
                                e.printStackTrace();
                            }

                            Log.e("tag_price", String.valueOf(response.body().getTotalamount()));
                            tvSubTotal.setText("\u20b9 " + String.valueOf(response.body().getTotalamount()));

//                            cartAdapter = new CartAdapter(getActivity(), getcartarray,cust_id, "cart");
//                            rvCartView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
//                            rvCartView.setAdapter(cartAdapter);


                            // data autometically update on login
                            if (strcartflag!= null && strcartflag.equalsIgnoreCase("cartflag_dbcheck")){
                                generateArray();
                            }

                            tvChakeout.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if (prefManager.getBoolean("isMembership", true)) {
                                        if (!prefManager.getString("cust_id", "").isEmpty()) {

                                           generateArray();
                                        } else {

                                           Intent intent = new Intent(getActivity(), LoginActivity.class);
                                            intent.putExtra("cartflag", "cartflag");
                                           startActivity(intent);


                                        }
                                    }

                                }
                            });


                        }
                        else {
                           // mProgressBar.setVisibility(View.GONE);
                            nocartFound.setVisibility(View.VISIBLE);
                            viewcart_main_layout.setVisibility(View.GONE);


                        }
                    } else {
                       // mProgressBar.setVisibility(View.GONE);
                       /* nocartFound.setVisibility(View.VISIBLE);
                        viewcart_main_layout.setVisibility(View.GONE);*/


                    }
                } else {
                    //mProgressBar.setVisibility(View.GONE);
                    Log.e("tag_er1", "tag_error1");
                   /* nocartFound.setVisibility(View.VISIBLE);
                    viewcart_main_layout.setVisibility(View.GONE);*/

                }
            }

            @Override
            public void onFailure(Call<CartItems> call, Throwable t) {
                //mProgressBar.setVisibility(View.GONE);
                Log.e("tag_er", "tag_error");
               /* nocartFound.setVisibility(View.VISIBLE);
                viewcart_main_layout.setVisibility(View.GONE);*/

            }
        });

    }


    private void generateArray() {
     //   ArrayList<ModelLocalCart> localCarts = databaseHandler.getCart();
        String allinone = "";
     //   if (localCarts.size() > 0) {
            String datastring = "";
            for (int i = 0; i < getcartarray.size(); i++) {
                String prodqty = "", prodid = "", configurable = "";
                allinone = "{\"custid\":\"" + prefManager.getString("cust_id", "") + "\",\"cartProduct\":[{";
                if (i != 0) {
                    datastring = datastring + ",{";
                }
                prodqty = prodqty + "\"prodqty\":" + "\"" + getcartarray.get(i).getQty() + "\"";
                prodid = prodid + "\"prodid\":" + "\"" + getcartarray.get(i).getId() + "\"";
                datastring = datastring + prodqty + "," + prodid;


                datastring = datastring + "}";
            }
            allinone = allinone + datastring + "]}";
            Log.d("tag_allinone", allinone);
            placeOrder(allinone);
     //   }
    }

    private void placeOrder(String json) {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        NetworkService networkService = NetworkClient.getClient().create(NetworkService.class);
        Call<GeneralModel> responseCall = networkService.addCart("NXqXAgFZA0jiWp5t6+=lGpgWJXEkbo", json);

        responseCall.enqueue(new Callback<GeneralModel>() {
            @Override
            public void onResponse(Call<GeneralModel> call, Response<GeneralModel> response) {

                if (response.body() != null) {

                    if (response.body().getSuccess().equals("1")) {
                        if (prefManager.getBoolean("isMembership", false)) {

                            if (cartflag==true){
                                databaseHandler.emptyCart();
                            }
                            else {


                            }

                           /* if (strpassingvalue!= null && strpassingvalue.equalsIgnoreCase("cartflag_dbcheck")){
                                Intent intent = new Intent(getActivity(), MainActivity.class);
                                startActivity(intent);
                                databaseHandler.emptyCart();
                                getActivity().finish();
                            }
                            else {*/
                           try {
                               Intent intent = new Intent(getActivity(), CustomerAddressActivity.class);
                               intent.putExtra("subTotal", cartAmount + "");
                               startActivity(intent);
                           }catch (Exception e){
                               e.printStackTrace();
                               Log.e("tag_error", String.valueOf(e));
                           }

                         //   }


                            progressDialog.dismiss();

                        }
                    } else {
                        Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                } else {
                    Toast.makeText(getActivity(), "Something went wrong...!!", Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
                progressDialog.dismiss();
            }


            @Override
            public void onFailure(Call<GeneralModel> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Something went wrong...!!", Toast.LENGTH_LONG).show();
            }
        });
    }


    private void getCart() {
        viewCarts = databaseHandler.getCart();
        double subTotal = 0;
        if (viewCarts.size() > 0) {

            viewcart_main_layout.setVisibility(View.VISIBLE);
            nocartFound.setVisibility(View.GONE);

            for (int i = 0; i < viewCarts.size(); i++) {
                if (i == 0) {
                    cartAmount = Double.parseDouble(String.valueOf(viewCarts.get(i).getPrice()));
                    subTotal = Double.parseDouble(String.valueOf(viewCarts.get(i).getPrice()));
                } else {
                    double tempTotal = Double.parseDouble(String.valueOf(viewCarts.get(i).getPrice()));
                    cartAmount = cartAmount + tempTotal;
                    subTotal = subTotal + tempTotal;
                }
            }
            //bottom_cart_layout.setVisibility(View.VISIBLE);
            tvSubTotal.setText("\u20b9 " + String.format("%.2f", subTotal));


//            CartAdapter cartAdapter = new CartAdapter(getActivity(), viewCarts,"", "cart");
//            rvCartView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
//            rvCartView.setAdapter(cartAdapter);


            if (cartflag==true){

                generateArrayLocal();
            }
            else {

            }

        } else {

            nocartFound.setVisibility(View.VISIBLE);
            viewcart_main_layout.setVisibility(View.GONE);
            //bottom_cart_layout.setVisibility(View.GONE);

        }
    }

    private void generateArrayLocal() {
        ArrayList<Addtocart> localCarts = databaseHandler.getCart();
        String allinone = "";
        if (localCarts.size() > 0) {
            String datastring = "";
            for (int i = 0; i < localCarts.size(); i++) {
                String prodqty = "", prodid = "", configurable = "";
                allinone = "{\"custid\":\"" + prefManager.getString("cust_id", "") + "\",\"cartProduct\":[{";
                if (i != 0) {
                    datastring = datastring + ",{";
                }
                prodqty = prodqty + "\"prodqty\":" + "\"" + localCarts.get(i).getQty() + "\"";
                prodid = prodid + "\"prodid\":" + "\"" + localCarts.get(i).getId() + "\"";
                datastring = datastring + prodqty + "," + prodid;

                datastring = datastring + "}";
            }
            allinone = allinone + datastring + "]}";
            Log.d("tag_allinone", allinone);
            placeOrder(allinone);
        }
    }
    public void addTocart()
    {
        final String token=prefManager.getString("cust_id","");
        //final ProgressDialog dialog = ProgressDialog.show(getActivity(), "", "Proccessing....Please wait");

        final String url="http://52.66.136.244/api/v1/cart";
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject object= null;
                try {

                    object = new JSONObject(response);
                    String status=object.getString("success");
                    JSONArray getCarts=object.getJSONArray("cart");
                    // JSONArray jsonArray=object.getJSONArray("details");
                        if (getCarts.length() < 1) {
                            nocartFound.setVisibility(View.VISIBLE);
                            viewcart_main_layout.setVisibility(View.GONE);

                        } else {
                            if(!status.equals("")) {

                                //    dialog.dismiss();
                            for (int a = 0; a < getCarts.length(); a++) {
                                JSONObject jsonObject = getCarts.getJSONObject(a);
                                double pr = jsonObject.getDouble("price") * jsonObject.getInt("qty");
                                totalprice = totalprice + pr;
                            }
                            tvSubTotal.setText("" + totalprice);
                            cartAdapter = new CartAdapter(getActivity(), getCarts, "4", "cart", Cart.this);
                            rvCartView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                            rvCartView.setAdapter(cartAdapter);
                            //    dialog.dismiss();
                        }
                    else
                        {

                            Toast.makeText(getActivity(), "alreay in wishlist", Toast.LENGTH_SHORT).show();
                            //   dialog.dismiss();
                        }
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


        };
        requestQueue.add(stringRequest);
    }

}
