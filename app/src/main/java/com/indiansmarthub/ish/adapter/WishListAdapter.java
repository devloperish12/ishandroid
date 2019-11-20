package com.indiansmarthub.ish.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.indiansmarthub.ish.activity.DetailsActivity;
import com.indiansmarthub.ish.activity.Splash;
import com.indiansmarthub.ish.custom.GeneralCode;
import com.indiansmarthub.ish.javaclass.Add_Remove_WishList;
import com.indiansmarthub.ish.javaclass.BottomMenuHelper;
import com.indiansmarthub.ish.model.ModelWishList;
import com.indiansmarthub.ish.sqlite.DatabaseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import static com.indiansmarthub.ish.MainActivity.mBottomNavigationView;
import static com.indiansmarthub.ish.custom.GeneralCode.encodeToBase64;

public class WishListAdapter extends RecyclerView.Adapter<WishListAdapter.WishListHolder> {
    Context context;
    ArrayList<ModelWishList> getWishList;
    private DatabaseHandler databaseHandler;
    private SharedPreferences prefManager;
    JSONArray jsonArray;
    String wishlistcount, cartct;

    public WishListAdapter(Context context, JSONArray jsonArray) {
        this.context = context;
        this.jsonArray = jsonArray;
    }


    @NonNull
    @Override
    public WishListHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_product_list_mutiple_layout, parent, false);
        return new WishListAdapter.WishListHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final WishListHolder holder, final int postion) {
        prefManager = context.getSharedPreferences("ISH", context.MODE_PRIVATE);
        try {
            final JSONObject jsonObject = jsonArray.getJSONObject(postion);
            // final ModelWishList modelProductList = getWishList.get(holder.getAdapterPosition());
            final String url = jsonObject.getString("image_url");

            holder.tvProductListName.setText(jsonObject.getString("product_name"));
            holder.tvProductListPrice.setText("₹ " + jsonObject.getString("price"));
            Picasso.with(context)
                    .load(url)
                    .error(R.drawable.logo)
                    .into(holder.iv_product_list_image);

            holder.iv_product_list_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                     //   getcarts( jsonObject.getString("id"));
                        Intent intent = new Intent(context, DetailsActivity.class);
                        intent.putExtra("product_id", jsonObject.getString("product_id"));
                        intent.putExtra("detail", jsonObject.toString());

                        // {"success":"1","message":"product view","whislist":[{"id":204,"product_name":"SURAKSHA SECURITY HOME NON GSM","name":"SURAKSHA SECURITY HOME NON GSM","description":"Technical DetailsColorBlackItem Weight1.25 KgProduct Dimensions20 x 15 x 10 cmShipping Weight1.50 KilogramsItem Part NumberSSSGM101Primary materialironCapacityStandardNumber of Pieces1Item ShapeRectangular","type":"sell","product_id":116,"image_url":"http:\/\/52.66.136.244\/images\/product_images\/Suraksha-Security-Home-GSM.jpg","product_url":"http:\/\/52.66.136.244\/product\/116","sku":"SURAKSHA SECURITY HOME NON GSM","price":5999,"qty":1},{"id":205,"product_name":"JS015 \u2013 JAGSUN ID CARD WITH GPS TRACKING","name":"JS015 \u2013 JAGSUN ID CARD WITH GPS TRACKING","description":" GPS+AGPS+LBS POSITIONINGAllow the location to be pinpointed accurately and rapidly.IP65 DUST &amp; WATER PROOFPrevent device breakdown from unexpectedly splashing by water.GEO-FENCE ALERTSCreate geofences in circle or rectangle for key locations.1200mAh INTERNAL BATTERYHigh capacity Li-ion battery allowing sufficient power supply.AUTO-MUTE MODE ON CLASSESNever disturb the classes via auto-setting.VOICE MONITORActivate listen-in mode via text.SOS CALL &amp; SPEED DIALThumb sized button allowing urgent call during an emergency case.TRACKED BY: SMS, APP, WEBReports location In real time through different interfaces.","type":"sell","product_id":130,"image_url":"http:\/\/52.66.136.244\/images\/product_images\/JAGSUN-ID-CARD-WITH-GPS-TRACKING.jpg","product_url":"http:\/\/52.66.136.244\/product\/130","sku":"JS015 \u2013 JAGSUN ID CARD WITH GPS TRACKING","price":5499,"qty":1},{"id":206,"product_name":"JAGSUN SMART LED TV 50 INCH 4K SMART","name":"JAGSUN SMART LED TV 50 INCH 4K SMART","description":"Technical DetailsBrandJAGSUNModel Year2019Product Dimensions110.9 x 25.7 x 71.1 cmRam Memory Installed Size1Operating SystemAndroid BasedGraphics CoprocessorMALI 450Resolution4KIncluded ComponentsRemote, Base Stand, Instruction ManualNumber Of Items1Display TechnologyLEDScreen Size50 InchesImage Aspect Ratio16:09Image Contrast Ratio4000000:1Supported Image TypeJPEGDisplay Resolution Maximum3840 * 2160 PixelsSupported Audio Formataac, aac-lc, mp3,cookerPower SourceACBatteries IncludedNoBatteries RequiredNoRefresh Rate60 hertzTotal USB Ports2Connector TypeBuilt-in WifiDigital Media FormatDVD-VideoSupports Bluetooth TechnologyNo","type":"sell","product_id":90,"image_url":"http:\/\/52.66.136.244\/images\/product_images\/Jagusn-Smart-LED-TV-50-Inch-4K-01.jpg","product_url":"http:\/\/52.66.136.244\/product\/90","sku":"8528","price":26999,"qty":1},{"id":217,"product_name":"JAGSUN HOLOGRAM 65 CM. 720 LED HD FAN","name":"JAGSUN HOLOGRAM 65 CM. 720 LED HD FAN","description":"1. Diameter:65cm&nbsp;2. Definition:720px *720px&nbsp;3. Power:70W&nbsp;4. Output voltage:AC 100-240V&nbsp;5. Input: 12v&nbsp;6. Lifetime: More than 50000 hrs7. Pixel LED :180*4=720pcs 0605 LED8. Remote Control: Available&nbsp;9. Accessories\uff1a 1x AC Adapter 100-240v&nbsp;10. 2x setscrews11. Software E-Manual&nbsp;12. Warranty: 1 year","type":"sell","product_id":124,"image_url":"http:\/\/52.66.136.244\/images\/product_images\/2.jpg","product_url":"http:\/\/52.66.136.244\/product\/124","sku":"JAGSUN HOLOGRAM 65 CM. 720 LED HD FAN","price":70799,"qty":1},{"id":220,"product_name":"JAGSUN HOLOGRAM 100 CM. FAN","name":"JAGSUN HOLOGRAM 100 CM. FAN","description":"1. Diameter:100cm&nbsp;2. Definition:680px *680px&nbsp;3. Max. Display Dimension:90.5x90.5 cm4. Power:50W&nbsp;5. Lifetime: More than 60000 hrs6. Input: 24v 3A&nbsp;7. Output voltage: AC 100-240v .50\/60hz&nbsp;8. Remote Control: Available&nbsp;9. Accessories\uff1a 1x Remote Control&nbsp;1x AC Adapter 100-240v&nbsp;4x setscrews&nbsp;1x Acrylic protector (optional)&nbsp;1x Basement (optional)10. Software&nbsp;11. E-manual&nbsp;12. Warranty: 1 year","type":"sell","product_id":129,"image_url":"http:\/\/52.66.136.244\/images\/product_images\/2.jpg","product_url":"http:\/\/52.66.136.244\/product\/129","sku":"JAGSUN HOLOGRAM 100 CM","price":188799,"qty":1},{"id":222,"product_name":"SURAKSHA SECURITY SHUTTER GSM","name":"SURAKSHA SECURITY SHUTTER GSM","description":"Technical DetailsColorBlackItem Weight1.25 KgProduct Dimensions20 x 15 x 10 cmShipping Weight1.50 KilogramsItem Part NumberSSSGM101Primary materialironCapacityStandardNumber of Pieces1Item ShapeRectangular","type":"sell","product_id":114,"image_url":"http:\/\/52.66.136.244\/images\/product_images\/Suraksha-Security-Shutter-GSM.jpg","product_url":"http:\/\/52.66.136.244\/product\/114","sku":"SURAKSHA SECURITY SHUTTER GSM","price":12999,"qty":1}]}
                        intent.putExtra("iswish", "yes");
                        context.startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
//        holder.wishlist_status = Integer.parseInt(modelProductList.getWishlist());
//        if (holder.wishlist_status == 0) {
          //  holder.ivWishListRemove.setVisibility(View.VISIBLE);
           // holder.ivWishListAdd.setVisibility(View.GONE);
            holder.ivWishListRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //getAddWishList(modelProductList.getIds(), modelProductList.getSku());
                    holder.ivWishListAdd.setVisibility(View.VISIBLE);
                    holder.ivWishListRemove.setVisibility(View.GONE);
                    try {
                        addWishList(jsonObject.getString("product_id"));
                    }catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            });

//
//        }
//        else if (holder.wishlist_status == 1) {
//            holder.ivWishListAdd.setVisibility(View.VISIBLE);
//            holder.ivWishListRemove.setVisibility(View.GONE);
//
//            holder.ivWishListAdd.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Add_Remove_WishList.getRemoveWishList(context,modelProductList.getId(),prefManager.getString("cust_id", ""));
//
//
//                    holder.ivWishListRemove.setVisibility(View.VISIBLE);
//                    holder.ivWishListAdd.setVisibility(View.GONE);
//                    getWishList.remove(postion);
//                        notifyDataSetChanged();
//
//                }
//            });
//        }
//        holder.wishlist_status = Integer.parseInt(modelProductList.getWishlist());
//        if (holder.wishlist_status == 0){
//            holder.ivWishListRemove.setVisibility(View.VISIBLE);
//            holder.ivWishListAdd.setVisibility(View.GONE);
//        }else if (holder.wishlist_status == 1){
//            holder.ivWishListAdd.setVisibility(View.VISIBLE);
//            holder.ivWishListRemove.setVisibility(View.GONE);
//        }
        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    @Override
    public int getItemCount() {
        return jsonArray.length();
    }

    public class WishListHolder extends RecyclerView.ViewHolder {
        ImageView iv_product_list_image, iv_minus, iv_plus, ivWishListRemove, ivWishListAdd;
        TextView tvProductListName, tvProductListPrice, qty_addtocart, tv_addtocart;
        LinearLayout layoutAddTocartBest;
        int i, wishlist_status;

        public WishListHolder(@NonNull View itemView) {
            super(itemView);
            iv_product_list_image = itemView.findViewById(R.id.iv_product_list_image);
            iv_minus = itemView.findViewById(R.id.iv_minus);
            iv_plus = itemView.findViewById(R.id.iv_plus);
            tvProductListName = itemView.findViewById(R.id.tvProductListName);
            tvProductListPrice = itemView.findViewById(R.id.tvProductListPrice);
            tv_addtocart = itemView.findViewById(R.id.tv_addtocart);
            qty_addtocart = itemView.findViewById(R.id.qty_addtocart);
            layoutAddTocartBest = itemView.findViewById(R.id.layoutAddTocartBest);
            ivWishListRemove = itemView.findViewById(R.id.ivWishListRemove);
            //ivWishListRemove.setVisibility(View.GONE);
            ivWishListAdd = itemView.findViewById(R.id.ivWishListAdd);
        }
    }

    private void getCount() {
        int count = databaseHandler.cartCount();
        if (count > 0) {
            //   cartCount.setVisibility(View.VISIBLE);
            BottomMenuHelper.showBadge(context, mBottomNavigationView, R.id.navigation_cart, count + "");
          /*  cartCount.setText(count + "");
            cartCount.setSolidColor("#E70F18");
            cartCount.setStrokeColor("#E70F18");*/
        } else {
            // cartCount.setVisibility(View.INVISIBLE);
        }
    }
    public void getcarts(String id) {
        final String token = prefManager.getString("cust_id", "");
        //final ProgressDialog dialog = ProgressDialog.show(getActivity(), "", "Proccessing....Please wait");

        final String url = "http://52.66.136.244/api/v1/cart";
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject object = null;
                try {
                    ArrayList<String>  cartlist = new ArrayList<>();
                    object = new JSONObject(response);
                    String status = object.getString("success");
                    JSONArray getCarts = object.getJSONArray("cart");
                    // JSONArray jsonArray=object.getJSONArray("details");
                    if (!status.equals("")) {
                        for (int a = 0; a < getCarts.length(); a++) {
                            JSONObject jsonObject = getCarts.getJSONObject(a);
                            cartlist.add(jsonObject.getString("product_id"));

                        }
                    } else {
                    }
                    if (cartlist.contains(id)) {
                      int a=10;
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
    public void addWishList(String product_id ) {
        final String token = prefManager.getString("cust_id", "");
        //final ProgressDialog dialog = ProgressDialog.show(DetailsActivity.this, "", "Proccessing....Please wait");

        final String url = "http://52.66.136.244/api/v1/wishlist?qty=1&product_id=" + product_id;
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject object = null;
                try {
                    object = new JSONObject(response);
                    String status = object.getString("success");
                    String wishlistid=object.getString("wishlistid");
                    //JSONObject statusjson=new JSONObject(status);
                    // JSONArray jsonArray=object.getJSONArray("details");
                    if (!wishlistid.equals("0")) {
                        //ivWishListRemovedetails.setBackgroundResource(R.drawable.wishlist_red);
                    } else {
                       // ivWishListRemovedetails.setBackgroundResource(R.drawable.wishlist_outline);

                        Toast.makeText(context, "Successfully deleted", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(context,MainActivity.class);
                        intent.putExtra("isWish",true);
                        context.startActivity(intent);
                      //  dialog.dismiss();
                    }
                  //  dialog.dismiss();
                    ///dialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                    //dialog.dismiss();
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
