package com.indiansmarthub.ish.adapter;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

import com.indiansmarthub.ish.activity.CartActivity;
import com.indiansmarthub.ish.activity.DetailsActivity;
import com.indiansmarthub.ish.activity.LoginActivity;
import com.indiansmarthub.ish.custom.GeneralCode;
import com.indiansmarthub.ish.fragments.Cart;
import com.indiansmarthub.ish.javaclass.BottomMenuHelper;
import com.indiansmarthub.ish.model.Addtocart;
import com.indiansmarthub.ish.model.GeneralModel;
import com.indiansmarthub.ish.retrofit.NetworkClient;
import com.indiansmarthub.ish.retrofit.NetworkService;
import com.indiansmarthub.ish.sqlite.DatabaseHandler;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.jaredrummler.materialspinner.MaterialSpinnerAdapter;
import com.squareup.picasso.Picasso;

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


public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartHolder> {

    Context context;
    public static List<Addtocart> viewCarts;
    LinearLayout nocartFound;
    LinearLayout viewcart_main_layout;

    private double cartAmount = 0;
    private ProgressDialog progressDialog;
    private SharedPreferences prefManager;
    double subTotal = 0;

    String cust_id;

    private String qtyValue;
    public static int pos_item = 0;
    String payment_cart;
    public static Addtocart viewCart;
    DatabaseHandler databaseHandler;
    Integer price;
    private int pricelocal;
    float finalprice;
    Cart cart;
    JSONArray jsonArray;
    int oldQty = 0;
    String cartct = "", wishlistcount = "";
    ArrayList<Integer> qtylist = new ArrayList<>();

    public CartAdapter(Context context, JSONArray viewCarts, String cust_id, String payment_cart, Cart fragment) {
        this.context = context;
        this.jsonArray = viewCarts;
        this.cust_id = cust_id;
        this.payment_cart = payment_cart;
        this.cart = fragment;
        cartct = "" + jsonArray.length();

    }

    @NonNull
    @Override
    public CartAdapter.CartHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_cart_layout, parent, false);
        return new CartAdapter.CartHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull final CartHolder holder, int pos) {

        databaseHandler = new DatabaseHandler(context);
        prefManager = context.getSharedPreferences("ISH", context.MODE_PRIVATE);
        pos_item = pos;
        try {
            JSONObject jsonObject = jsonArray.getJSONObject(pos);
            final String cart_id = jsonObject.getString("id");
            Log.e("tag_p", String.valueOf(pos_item));
            holder.tvProductCartListName.setText(jsonObject.getString("product_name"));
            //   price = Integer.parseInt(String.valueOf(viewCarts.get(pos).getPrice()));
            holder.tvProductListCartPrice.setText("\u20b9 " + jsonObject.getString("price"));
            holder.tvCartQty.setText(String.valueOf("Cart Qty : " + jsonObject.getString("qty")));
            holder.tvCart.setText(String.valueOf("" + jsonObject.getString("qty")));
            oldQty = jsonObject.getInt("qty");
            qtylist.add(oldQty);
            final String id = jsonObject.getString("product_id");
            final double price = jsonObject.getDouble("price");
            String imgurl = jsonObject.getString("image_url");
            if (payment_cart.equals("payment")) {
                holder.mlineatqty.setVisibility(View.GONE);
            } else {
                holder.mlineatqty.setVisibility(View.VISIBLE);
            }
            holder.materialSpinner_qty.setTag(pos);
            holder.ivAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        int x=Integer.parseInt(jsonObject.getString("qty"));
                        x++;
                        holder.tvCartQty.setText(String.valueOf("Cart Qty : " +x ));
                        holder.tvCart.setText(String.valueOf("" + x));
                        updatecart(cart_id,x);

//
//

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
            holder.ivRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        int x=Integer.parseInt(jsonObject.getString("qty"));
                        x--;
                        holder.tvCartQty.setText(String.valueOf("Cart Qty : " +x ));
                        holder.tvCart.setText(String.valueOf("" + x));
                        if (x==0){

                            if (!prefManager.getString("cust_id", "").isEmpty()) {
                                addTocart(cart_id);
                            } else {

                                Intent intent = new Intent(context, MainActivity.class);
                                intent.putExtra("iscart", true);
                                context.startActivity(intent);
                                //notifyDataSetChanged();
                            }

                        }else {

                            updatecart(cart_id,x);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
            final TextView textView = holder.tvCartQty;
            spinnerCall(holder, price, pos, cart_id);

            // image get offline or online
            if (!prefManager.getString("cust_id", "").isEmpty()) {
                try {
                    Picasso.with(context)
                            .load(imgurl)
                            .error(R.drawable.logo)
                            .into(holder.iv_cart_product_image);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {
                if (viewCarts.get(pos).getImage() != null) {
                    Bitmap bitmap = GeneralCode.decodeBase64(viewCarts.get(pos).getImage());
                    if (bitmap != null) {
                        holder.iv_cart_product_image.setImageBitmap(bitmap);
                    }
                }
            }


            final int finalPos = pos;


            holder.delete_cart.setVisibility(View.VISIBLE);
            holder.delete_cart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (!prefManager.getString("cust_id", "").isEmpty()) {
                        addTocart(cart_id);
                    } else {
                        databaseHandler.deleteProductFromCart(viewCarts.get(finalPos).getAutoId());
                        viewCarts.remove(finalPos);
                        Intent intent = new Intent(context, MainActivity.class);
                        intent.putExtra("iscart", true);
                        context.startActivity(intent);
                        //notifyDataSetChanged();
                    }


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void generateArray(String qty) {
        //   ArrayList<ModelLocalCart> localCarts = databaseHandler.getCart();
        String allinone = "";

        String datastring = "";

        String prodqty = "", prodid = "", configurable = "";
        allinone = "{\"custid\":\"" + cust_id + "\",\"cartProduct\":[{";

        prodqty = prodqty + "\"prodqty\":" + "\"" + qty + "\"";
        prodid = prodid + "\"prodid\":" + "\"" + viewCarts.get(pos_item).getId() + "\"";
        datastring = datastring + prodqty + "," + prodid;

        datastring = datastring + "}";

        allinone = allinone + datastring + "]}";
        Log.e("tag_allinone", allinone);
        try {
            /*placeOrder(allinone, CartAdapter.pos_item);*/
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

   /* private void placeOrder(String json, final int position) {
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        NetworkService networkService = NetworkClient.getClient().create(NetworkService.class);
        Call<GeneralModel> responseCall = networkService.addCart("NXqXAgFZA0jiWp5t6+=lGpgWJXEkbo", json);

        responseCall.enqueue(new Callback<GeneralModel>() {
            @Override
            public void onResponse(Call<GeneralModel> call, Response<GeneralModel> response) {

                if (response.body() != null) {

                    if (response.body().getSuccess().equals("1")) {
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        String msg = response.body().getMessage();
                        if (msg.equals("successfully add to cart")) {
                            //  addTocart_ProductDetails.setText("GO TO CART");

                            Intent intent = new Intent(context, CartActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            context.startActivity(intent);


                            progressDialog.dismiss();
                        }

                        Log.e("tag_re", msg);
                        progressDialog.dismiss();
                       *//* if (prefManager.getBoolean("isMembership", false)) {


                            progressDialog.dismiss();

                        }*//*
                    } else {
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                } else {
                    Toast.makeText(context, "Something went wrong...!!", Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
                progressDialog.dismiss();
            }


            @Override
            public void onFailure(Call<GeneralModel> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(context, "Something went wrong...!!", Toast.LENGTH_LONG).show();
            }
        });
    }


    private void callCartDeleteItemApi(final int i) {

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        NetworkService networkService = NetworkClient.getClient().create(NetworkService.class);
        Call<GeneralModel> responseCall = networkService.getCartDeleteItems("NXqXAgFZA0jiWp5t6+=lGpgWJXEkbo", cust_id, viewCarts.get(i).getItem_id());

//        Log.e("tag_deleteditem",viewCarts.get(i).getItem_id());
        responseCall.enqueue(new Callback<GeneralModel>() {
            @Override
            public void onResponse(Call<GeneralModel> call, Response<GeneralModel> response) {

                if (response.body() != null) {

                    if (response.body().getSuccess().equals("1")) {
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        String msg = response.body().getMessage();
                        if (msg.equals("deleted from cart")) {
                            //   addTocart_ProductDetails.setText("GO TO CART");

                            Log.e("tag_", "tag_");

                            viewCarts.remove(i);
                            notifyDataSetChanged();
                            Intent intent = new Intent(context, CartActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            context.startActivity(intent);


                        }
                        Log.e("tag_re", msg);
                        progressDialog.dismiss();
                       *//* if (prefManager.getBoolean("isMembership", false)) {


                            progressDialog.dismiss();

                        }*//*
                    } else {
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                } else {
                    Toast.makeText(context, "Something went wrong...!!", Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
                progressDialog.dismiss();
            }


            @Override
            public void onFailure(Call<GeneralModel> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(context, "Something went wrong...!!", Toast.LENGTH_LONG).show();
            }
        });


    }*/

    @SuppressLint("ResourceAsColor")
    private void spinnerCall(final CartHolder holder, final double price, final int pos, final String itid) {

        try {
            ArrayList<String> qty = new ArrayList<>();
            qty.add("Please Select");
            qty.add("1");
            qty.add("2");
            qty.add("3");
            qty.add("4");
            qty.add("5");
            qty.add("6");
            qty.add("7");
            qty.add("8");
            qty.add("9");
            qty.add("10");
            qty.add("More");
//            holder.materialSpinner_qty.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    //spinnerView.performClick();
//
//                    holder.materialSpinner_qty.showDropDown();
//                }
//            });
////            //leave categry spinner
            MaterialSpinnerAdapter materialSpinnerAdapter = new MaterialSpinnerAdapter(context, qty);
            materialSpinnerAdapter.setTextColor(R.color.colorGreyDark);
            holder.materialSpinner_qty.setAdapter(materialSpinnerAdapter);
            holder.materialSpinner_qty.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
                @Override
                public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                    oldQty = qtylist.get(pos);
                    if (position <= 10) {

                        updatecart(itid, position);
                        if (view.getId() == R.id.spinnerqty) {
                            //   if (position>0) {
                            if (position <= oldQty) {
                                try {
                                    holder.btUpdateQty.setText(position);
                                    // textView.setText(position);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                int calprice = oldQty - position;
                                double total = price * calprice;
                                oldQty = position;
                                qtylist.set(pos, position);
                                cart.docomthing("1", total, false);
                            } else {
                                try {
                                    // textView.setText(position);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                int calprice = position - oldQty;
                                double total = price * calprice;
                                oldQty = position;
                                qtylist.set(pos, position);

                                    cart.docomthing("1", total, true);
                                }
                            }
                        }
//                        if (x == 0){
//                            oldQty
//                            //   qtyValue = "Please Select";
//                        } else if (x == 1) {
//                            qtyValue = "1";
//                        } else if (x == 2) {
//                            qtyValue = "2";
//                        } else if (x == 3) {
//                            qtyValue = "3";
//                        } else if (x == 4) {
//                            qtyValue = "4";
//                        } else if (x == 5) {
//                            qtyValue = "5";
//                        } else if (x == 6) {
//                            qtyValue = "6";
//                        } else if (x == 7) {
//                            qtyValue = "7";
//                        } else if (x == 8) {
//                            qtyValue = "8";
//                        } else if (x == 9) {
//                            qtyValue = "9";
//                        }
//                        else if (x == 10) {
//                            qtyValue = "10";
//                        }
                    if (position == 11) {

                        holder.mLinearSpinner.setVisibility(View.GONE);
                        holder.mLinearMoreQty.setVisibility(View.VISIBLE);
                        holder.mLineatupdateqty.setVisibility(View.VISIBLE);

                        holder.btUpdateQty.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                updatecart(itid, position);

                                if (holder.mEnterQty.getText().toString().isEmpty()) {
                                    Toast.makeText(context, "Please Enter Qty", Toast.LENGTH_SHORT).show();
                                } else {

                                    qtyValue = holder.mEnterQty.getText().toString();
                                    if (prefManager.getString("cust_id", "").isEmpty()) {
                                        addInDatabase(qtyValue);
                                    } else {
                                        updatecart(itid, Integer.parseInt(qtyValue));

                                        //generateArray(qtyValue);
                                    }

                                }

                            }
                        });
                        holder.btUpdateQty.addTextChangedListener(new TextWatcher() {
                            private String previousDigits, num;
                            private boolean textChanged = false;

                            @Override
                            public void onTextChanged(CharSequence currentDigits, int start,
                                                      int before, int count) {
                                if (count > 0) {
                                    try {
                                        //   updatecart(itid,position);

                                        String value = holder.btUpdateQty.getText().toString();
                                        int intval = Integer.parseInt(value);
                                        if (intval < oldQty) {
                                            int calprice = oldQty - intval;
                                            double total = price * calprice;
                                            cart.docomthing("1", total, false);
                                        } else {
                                            int calprice = intval - oldQty;
                                            double total = price * calprice;
                                            cart.docomthing("1", total, true);
                                        }
                                        // previousDigits = currentDigits.toString();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }

                                @Override
                                public void beforeTextChanged(CharSequence s, int start, int count,
                                                              int after) {

                                }

                                @Override
                                public void afterTextChanged(Editable s) {

                                }
                            });


                        }

//                        pos_item = (int) view.getTag();
//                        Log.e("tag_itemp", String.valueOf(pos_item));
//
//                        //  try {
//
//                        if (position<=10){
//                            if (prefManager.getString("cust_id", "").isEmpty()) {
//                                addInDatabase(qtyValue);
//                            }
//                            else {
//                                generateArray(qtyValue);
//                            }
//                        }
//                        else {
//                          //  Toast.makeText(context, "more then 10", Toast.LENGTH_SHORT).show();
//                        }
//


               /* }catch (Exception e){
                    Log.e("tag_ee", String.valueOf(e));
                    e.printStackTrace();
                }*/

                    //  }


                }
            });


        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return jsonArray.length();
    }


    public class CartHolder extends RecyclerView.ViewHolder {
        ImageView iv_cart_product_image, ivAdd, ivRemove, delete_cart;
        TextView tvProductCartListName, tvProductListCartPrice, tvCart, tvCartQty;
        MaterialSpinner materialSpinner_qty;
        LinearLayout mlineatqty, mLinearSpinner, mLinearMoreQty, mLineatupdateqty;
        TextView btUpdateQty;
        EditText mEnterQty;


        public CartHolder(@NonNull View itemView) {
            super(itemView);
            iv_cart_product_image = itemView.findViewById(R.id.iv_cart_product_image);
            tvProductCartListName = itemView.findViewById(R.id.tvProductCartListName);
            tvProductListCartPrice = itemView.findViewById(R.id.tvProductListCartPrice);
            ivAdd = itemView.findViewById(R.id.ivAdd);
            ivRemove = itemView.findViewById(R.id.ivRemove);
            tvCartQty = itemView.findViewById(R.id.tvCartQty);
            tvCart=itemView.findViewById(R.id.tvCart);
            mlineatqty = itemView.findViewById(R.id.lineatqty);
            delete_cart = itemView.findViewById(R.id.delete_cart);
            materialSpinner_qty = itemView.findViewById(R.id.spinnerqty);
            mLinearSpinner = itemView.findViewById(R.id.linearSpinner);
            mLinearMoreQty = itemView.findViewById(R.id.qtymore);
            mLineatupdateqty = itemView.findViewById(R.id.lineatupdateqty);
            btUpdateQty = itemView.findViewById(R.id.btqtyupdate);
            mEnterQty = itemView.findViewById(R.id.etEnterQty);
        }
    }

    private void addInDatabase(String qtyValue) {
        pricelocal = Integer.parseInt(String.valueOf(viewCarts.get(pos_item).getPrice())) * Integer.parseInt(qtyValue);
        Log.e("tag_pl", String.valueOf(pricelocal));
        //   holder.tvProductListCartPrice.setText("\u20b9 " + String.format("%.2f", price));

        databaseHandler.updateCart(viewCarts.get(pos_item).getId(), qtyValue, viewCarts.get(pos_item).getSku(), String.valueOf(pricelocal), viewCarts.get(pos_item).getName());
        //   cartDataChange(qtyValue);
    }


    private void cartDataChange(String qtyValue) {
        viewCarts = databaseHandler.getCart();

        if (viewCarts.size() > 0) {

            for (int i = 0; i < viewCarts.size(); i++) {
                if (i == 0) {
                    cartAmount = Double.parseDouble(String.valueOf(viewCarts.get(i).getPrice())) * Double.parseDouble(qtyValue);
                    subTotal = Double.parseDouble(String.valueOf(viewCarts.get(i).getPrice())) * Double.parseDouble(qtyValue);
                } else {
                    double tempTotal = Double.parseDouble(String.valueOf(viewCarts.get(i).getPrice())) * Double.parseDouble(qtyValue);
                    cartAmount = cartAmount + tempTotal;
                    subTotal = subTotal + tempTotal;

                    Log.e("tag_price", String.valueOf(subTotal));
                }
            }
            // tvSubTotal.setText("\u20b9 " + String.format("%.2f", subTotal));
            notifyDataSetChanged();


        } else {

        }
    }

    public void addTocart(String cust_id) {
        final String token = prefManager.getString("cust_id", "");
        // final ProgressDialog dialog = ProgressDialog.show(context, "", "Proccessing....Please wait");

        final String url = "http://52.66.136.244/api/v1/cart/" + cust_id;
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject object = null;
                try {
                    object = new JSONObject(response);
                    String status = object.getString("success");
                    //JSONObject statusjson=new JSONObject(status);
                    // JSONArray jsonArray=object.getJSONArray("details");
                    if (status.equals("1")) {
                        getWishList();

//                        addTocart_ProductDetails.setBackgroundResource(R.color.colorGreen);
//                        addTocart_ProductDetails.setText("Go To Cart");

                    } else {
                        Intent intent = new Intent(context, MainActivity.class);
                        intent.putExtra("iscart", true);

                        intent.putExtra("cart", "" + cartct);
                        intent.putExtra("wish", "" + wishlistcount);
                        context.startActivity(intent);

                        context.startActivity(intent);

                        //  Toast.makeText(DetailsActivity.this, "alreay in wishlist", Toast.LENGTH_SHORT).show();
                        // dialog.dismiss();
                    }
                    //  dialog.dismiss();
                    ///dialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                    //  dialog.dismiss();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Login", "" + error.getCause());
                //  dialog.dismiss();
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

    public void updatecart(final String cartid, final int posission) {
        final String token = prefManager.getString("cust_id", "");
        // final ProgressDialog dialog = ProgressDialog.show(getContext(), "", "Proccessing....Please wait");
        final String url = "http://52.66.136.244/api/v1/updatecart?id=" + cartid + "&qty=" + posission;
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject object = null;
                try {
                    object = new JSONObject(response);
                    String status = object.getString("success");
                    //JSONObject statusjson=new JSONObject(status);
                    // JSONArray jsonArray=object.getJSONArray("details");
                    if (status.equals("1")) {
//                        JSONArray jsonArray = object.getJSONArray("whislist");
//                        String wishlist=""+jsonArray.length();
                        Intent intent = new Intent(context, MainActivity.class);
                        intent.putExtra("iscart", true);
                        intent.putExtra("cart", "" + cartct);
                        // intent.putExtra("wish",""+wishlist);
                        context.startActivity(intent);


                    } else {

                        // Toast.makeText(getActivity(), "alreay in wishlist", Toast.LENGTH_SHORT).show();
                        // dialog.dismiss();
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
                Log.d("Login", "" + error.getCause());
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
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//               Map<String, String> map = new HashMap<String, String>();
//                map.put("id",cartid);
//                map.put("qty",""+posission);
//                return map;
//            }


        };
        requestQueue.add(stringRequest);
    }

    public void getWishList() {
        final String token = prefManager.getString("cust_id", "");
        // final ProgressDialog dialog = ProgressDialog.show(getContext(), "", "Proccessing....Please wait");
        final String url = "http://52.66.136.244/api/v1/wishlist";
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject object = null;
                try {
                    object = new JSONObject(response);
                    String status = object.getString("success");
                    //JSONObject statusjson=new JSONObject(status);
                    // JSONArray jsonArray=object.getJSONArray("details");
                    if (status.equals("1")) {
                        JSONArray jsonArray = object.getJSONArray("whislist");
                        String wishlist = "" + jsonArray.length();
                        Intent intent = new Intent(context, MainActivity.class);
                        intent.putExtra("iscart", true);
                        intent.putExtra("cart", "" + cartct);
                        intent.putExtra("wish", "" + wishlist);
                        context.startActivity(intent);


                    } else {

                        // Toast.makeText(getActivity(), "alreay in wishlist", Toast.LENGTH_SHORT).show();
                        // dialog.dismiss();
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
                Log.d("Login", "" + error.getCause());
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

}
