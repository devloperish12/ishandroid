package com.indiansmarthub.ish.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;
import com.indiansmarthub.ish.R;
import com.indiansmarthub.ish.activity.AllServices;
import com.indiansmarthub.ish.activity.DetailsActivity;
import com.indiansmarthub.ish.activity.ServicesProducts;
import com.indiansmarthub.ish.activity.ViewMoreProductActivity;
import com.indiansmarthub.ish.activity.WebViewLoadUrl;
import com.indiansmarthub.ish.adapter.BrandAdapter;
import com.indiansmarthub.ish.adapter.CategoryAdapter;
import com.indiansmarthub.ish.adapter.CreativeGalleryAdapter;
import com.indiansmarthub.ish.adapter.EduBasketProductAdapter;
import com.indiansmarthub.ish.adapter.FinancialFluxAdapter;
import com.indiansmarthub.ish.adapter.OurServicesAdapter;
import com.indiansmarthub.ish.adapter.SlidingImageAdapter;
import com.indiansmarthub.ish.adapter.SlidingImageAdapter1;
import com.indiansmarthub.ish.adapter.SlidingImageAdapter2;
import com.indiansmarthub.ish.adapter.SubServicesAdapter;
import com.indiansmarthub.ish.custom.GeneralCode;
import com.indiansmarthub.ish.javaclass.BottomMenuHelper;
import com.indiansmarthub.ish.javaclass.RecyclerItemClickListener;
import com.indiansmarthub.ish.model.AcHomeProducts;
import com.indiansmarthub.ish.model.BannerOffer;
import com.indiansmarthub.ish.model.BannerOfferModel;
import com.indiansmarthub.ish.model.CategoryProduct1;
import com.indiansmarthub.ish.model.CategoryServices;
import com.indiansmarthub.ish.model.Count;
import com.indiansmarthub.ish.model.HologramHomeProducts;
import com.indiansmarthub.ish.model.LedHomeProducts;
import com.indiansmarthub.ish.model.ModelBanner;
import com.indiansmarthub.ish.model.ModelCategory;
import com.indiansmarthub.ish.model.ModelFeaturedProduct;
import com.indiansmarthub.ish.model.ModelNewArrival;
import com.indiansmarthub.ish.model.ModelResponseBanner;
import com.indiansmarthub.ish.model.ModelResponseCategory;
import com.indiansmarthub.ish.model.ModelResponseFeaturedProduct;
import com.indiansmarthub.ish.model.ModelResponseNewArrival;
import com.indiansmarthub.ish.model.ModelResponseSlider;
import com.indiansmarthub.ish.model.ModelResponseTodayDeal;
import com.indiansmarthub.ish.model.ModelSlider;
import com.indiansmarthub.ish.model.ModelToday;
import com.indiansmarthub.ish.model.SecurityShutterHomeProducts;
import com.indiansmarthub.ish.model.ServicesModel;
import com.indiansmarthub.ish.retrofit.NetworkClient;
import com.indiansmarthub.ish.retrofit.NetworkService;
import com.indiansmarthub.ish.sqlite.DatabaseHandler;
import com.squareup.picasso.Picasso;
import com.viewpagerindicator.CirclePageIndicator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.ACTIVITY_SERVICE;
import static com.indiansmarthub.ish.MainActivity.mBottomNavigationView;

/**
 * A simple {@link Fragment} subclass.
 */

public class Home extends Fragment {

    View view;
    Button btnFeaturedViewMore, btnNewArrivalViewMore, btnBlogMore;
    private ViewPager imageSliderViewPager, imagebanaer, vpslider;
    private LinearLayout llOurServices, llEduBasket, llFinancialFlux, llCreativeGallery, llSmartServe, llClientsPartner;
    private RecyclerView rvservices, rvfeaturedproduct, rvNewArrivals, rvFromTheBlog, rvClientsPartner, mrv_headerServices,rv_subcategory;
    LinearLayoutManager managerEduBasket;
    CirclePageIndicator  indicator1, indicator3;
    private static int currentPage = 0;
    private static int NUM_PAGES = 10;
    SharedPreferences prefManager;
    DatabaseHandler databaseHandler;

    CategoryAdapter catrgoryAdapter;
    EduBasketProductAdapter eduBasketProductAdapter;
    FinancialFluxAdapter financialFluxAdapter;
    CreativeGalleryAdapter creativeGalleryAdapter;
    BrandAdapter brandAdapter;

    ArrayList<ModelSlider> getslider, getsliderarray;
    List<ModelFeaturedProduct> finalProductModel;

    List<CategoryServices> categoryServices;
    ProgressBar mProgressBar;
    List<ModelCategory> finalcategoryArrayModel;
    List<BannerOffer> getsliderToday, getsliderarrayToday;
    List<ModelBanner> getsliderBaner, getsliderarrayBanner;
    List<ModelNewArrival> finalFinancialFlux;
    ImageView hologram1, hologram2, hologram3, hologram4, led1, led2, led3, securityshutter1, securityshutter2, securityshutter3, securityshutter4, ac1, ac2, ac3, ac4;
    private ArrayList<CategoryProduct1> hologramArrayList;
    private ArrayList<CategoryProduct1> ledArrayList;
    private ArrayList<CategoryProduct1> securityshutterArrayList;
    private ArrayList<CategoryProduct1> acArrayList;
    Button mHologramViewAll,mLedViewAll, mSecuritySuttlerViewAll,mAcViewAll;
    private ProgressDialog progressDialog;
    JSONArray servicearray=new JSONArray();
    JSONArray categoryarray=new JSONArray();
    JSONArray bannerarray=new JSONArray();
    JSONArray mallarray=new JSONArray();
    JSONArray featurearray=new JSONArray();
LinearLayout servicelayout;
Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_home, container, false);
       // mProgressBar = view.findViewById(R.id.progress_bar);
        servicelayout= view.findViewById(R.id.featureproduct);
        imageSliderViewPager = view.findViewById(R.id.imageSliderViewPager);

        llOurServices = view.findViewById(R.id.llOurServices);
        llOurServices.setVisibility(View.GONE);
        llEduBasket = view.findViewById(R.id.llEduBasket);
        llEduBasket.setVisibility(View.GONE);
        rvservices = view.findViewById(R.id.rvservices);
        rvservices.setVisibility(View.GONE);
        rvfeaturedproduct = view.findViewById(R.id.rvfeaturedproduct);
        rvfeaturedproduct.setVisibility(View.GONE);
        rv_subcategory = view.findViewById(R.id.rv_subcategory);
        intiId();

context=getActivity();
        btnFeaturedViewMore = view.findViewById(R.id.btnFeaturedViewMore);


        prefManager = getActivity().getSharedPreferences("ISH", getActivity().MODE_PRIVATE);
        String cusid = prefManager.getString("cust_id", "");
        Log.e("castid", cusid);

        init();

        return view;
    }

    private void intiId() {

        led1 = view.findViewById(R.id.led1);
        led2 = view.findViewById(R.id.led2);
        led3 = view.findViewById(R.id.led3);

        mLedViewAll = view.findViewById(R.id.btnLedViewAll);

    }

    private void init() {
        databaseHandler = new DatabaseHandler(getActivity());
        if (GeneralCode.isConnectingToInternet(getActivity())) {
            getallData();
        }
        else {
            GeneralCode.showDialog(getActivity());
        }

    }
    private void callHeaderServicesAdapter(final List<ModelCategory> finalcategoryArrayModel) {
       LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
       OurServicesAdapter ourServicesAdapter = new OurServicesAdapter(getActivity(), finalcategoryArrayModel);
        mrv_headerServices.setLayoutManager(linearLayoutManager);
        mrv_headerServices.setAdapter(ourServicesAdapter);


        mrv_headerServices.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), mrv_headerServices, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Intent intent = new Intent(getActivity(), ServicesProducts.class);

                intent.putExtra("cate_id", finalcategoryArrayModel.get(position).getId());
                startActivity(intent);


            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));


    }

    public void getallData()
    {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        String url="http://52.66.136.244/api/v1/home_page";
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject object= null;
                try {
                    ArrayList<String> slider=new ArrayList<>();
                    object = new JSONObject(response);
                    JSONArray jsonArray=object.getJSONArray("data");
                    JSONObject jsonObject=jsonArray.getJSONObject(0);
                    String  categriesurl=jsonObject.getString("url");
                   categoryarray=jsonObject.getJSONArray("subdata");
                    for(int a=1;a<=jsonArray.length()-1;a++)
                    {
                        JSONObject jsonObject1=jsonArray.getJSONObject(a);
                        String  url=jsonObject1.getString("url");
                        String name=jsonObject1.getString("name");
                        if(!name.equalsIgnoreCase("banner"))
                        {
                            try {
                                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams((int)( LinearLayout.LayoutParams.MATCH_PARENT),
                                        LinearLayout.LayoutParams.WRAP_CONTENT);
                                lp.setMargins(10, 10, 10, 10);
                                LinearLayout.LayoutParams btnlp = new LinearLayout.LayoutParams((int)( LinearLayout.LayoutParams.WRAP_CONTENT),
                                        LinearLayout.LayoutParams.WRAP_CONTENT);
                                LinearLayout linearLayout = new LinearLayout(context);
                                linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                                linearLayout.setLayoutParams(lp);
                                RelativeLayout relativeLayout = new RelativeLayout(context);
                                relativeLayout.setGravity(Gravity.RIGHT);
                             final    JSONArray jsonArray1 = jsonObject1.getJSONArray("subdata");

                                TextView tv = new TextView(context);
                                tv.setAllCaps(true);
                                tv.setTextSize(14);
                                tv.setTextColor(Color.parseColor("#000000"));
                                tv.setTypeface(tv.getTypeface(), Typeface.BOLD);
                             //   button.setTextColor(Color.parseColor("#FFFFFF"));
                                tv.setText(name);
                              //  button.setText(" all");
                                // button.setTextColor(R.color.splashorange);
                                RecyclerView recyclerView = new RecyclerView(context);
                                servicelayout.setVisibility(View.VISIBLE);
                                linearLayout.addView(tv);
                                linearLayout.addView(relativeLayout);
                                servicelayout.addView(linearLayout);
                                servicelayout.addView(recyclerView);
                                GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 2);

                                financialFluxAdapter = new FinancialFluxAdapter(context, jsonArray1, url);
                                recyclerView.setLayoutManager(gridLayoutManager);
                                recyclerView.setAdapter(financialFluxAdapter);
                            }catch (Exception e)
                            {
                                e.printStackTrace();
                            }
                        }
                        else {
                            JSONArray jsonArray1 = jsonObject1.getJSONArray("subdata");
                            for(int b=0;b<jsonArray1.length()-1;b++) {
                                String imgurl=jsonArray1.getJSONObject(b).getString("url");
                                slider.add(url +"/"+ imgurl);
                            }

                              //  }
                            try {
                                imageSliderViewPager.setAdapter(new SlidingImageAdapter1(context, (ArrayList<BannerOffer>) getsliderToday,slider));

                            }catch (Exception e){
                                e.printStackTrace();
                            }

                            /*indicator1.setViewPager(imagebanaer);
                            indicator1.setRadius(5 * getResources().getDisplayMetrics().density);*/
                            final Handler handler = new Handler();
                            final Runnable Update = new Runnable() {
                                public void run() {
                                    if (currentPage == NUM_PAGES) {
                                        currentPage = 0;
                                    }
                                    imageSliderViewPager.setCurrentItem(currentPage++, true);
                                }
                            };
                            Timer swipeTimer = new Timer();
                            swipeTimer.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    handler.post(Update);
                                }
                            }, 2000, 2000);

                        }
                    }
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),4);
                    SubServicesAdapter subServicesAdapter = new SubServicesAdapter(getActivity(), categoryarray,categriesurl);
                    rv_subcategory.setLayoutManager(gridLayoutManager);
                    rv_subcategory.setAdapter(subServicesAdapter);
                    rv_subcategory.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), rv_subcategory, new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            try {
                                JSONObject categorydetail = categoryarray.getJSONObject(position);
                                String id =categorydetail.getString("id");
                                String name=categorydetail.getString("name");
                                if(!name.equalsIgnoreCase("view more")) {
                                    Intent intent = new Intent(context, ServicesProducts.class);
                                    intent.putExtra("cate_id", id);
                                    context.startActivity(intent);
                                }
                                else
                                {
                                    Intent intent = new Intent(context, AllServices.class);
                                    intent.putExtra("cate_id", id);
                                    context.startActivity(intent);

                                }
                            }catch (Exception e)
                            {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onLongItemClick(View view, int position) {

                        }
                    }));
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    progressDialog.dismiss();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();

                return map;
            }

        };
        requestQueue.add(stringRequest);
    }

}
