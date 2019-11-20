package com.indiansmarthub.ish.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.indiansmarthub.ish.R;
import com.indiansmarthub.ish.adapter.AllServicesAdapter;
import com.indiansmarthub.ish.adapter.AllServicesAdapterCreativeGallery;
import com.indiansmarthub.ish.adapter.AllServicesAdapterFinancialFlux;
import com.indiansmarthub.ish.adapter.AllServicesAdapterMall;
import com.indiansmarthub.ish.adapter.AllServicesAdapterSmartServe;
import com.indiansmarthub.ish.adapter.OurServicesAdapter;
import com.indiansmarthub.ish.adapter.SubServicesAdapter;
import com.indiansmarthub.ish.javaclass.RecyclerItemClickListener;
import com.indiansmarthub.ish.model.Category;
import com.indiansmarthub.ish.model.SubCategoryProduct;
import com.indiansmarthub.ish.retrofit.NetworkClient;
import com.indiansmarthub.ish.retrofit.NetworkService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllServices extends AppCompatActivity {
    RecyclerView rv_allservices, rvfinancialflux, rvcreativegallery, rvsmartserve, rvmall;
    List<Category> finalProductSubServicesModel;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_services);

        getsubCategory();
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        rv_allservices = findViewById(R.id.rv_allservicesEB);
        rvfinancialflux = findViewById(R.id.rv_allservicesFF);
        rvcreativegallery = findViewById(R.id.rv_allservicesCG);
        rvsmartserve = findViewById(R.id.rv_allservicesSS);
        rvmall = findViewById(R.id.rv_allservicesMALL);

    }

private void getsubCategory()
{
    final ProgressDialog dialog = ProgressDialog.show(AllServices.this, "", "Proccessing....Please wait");
        //  final String url= "http://52.66.136.244/api/v1/register";
        final String url="http://52.66.136.244/api/v1/categories";
        RequestQueue requestQueue = Volley.newRequestQueue(AllServices.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject object= null;
                try {
                    object=new JSONObject(response);
                    JSONObject jsonObject=object.getJSONObject("categories");
                    JSONArray edubasketarray=jsonObject.getJSONArray("data");
                    JSONObject edujson=edubasketarray.getJSONObject(0);
                    JSONObject financialobj=edubasketarray.getJSONObject(1);
                    JSONObject creativeobject=edubasketarray.getJSONObject(2);
                    JSONObject smartsurve=edubasketarray.getJSONObject(3);
                    JSONObject mall=edubasketarray.getJSONObject(4);

                    JSONArray edubasket=edujson.getJSONArray("subcategories");
                    final JSONArray edubasketarr=edujson.getJSONArray("subcategories");
                  final  JSONArray creativegallery=creativeobject.getJSONArray("subcategories");
                    final  JSONArray smartsurv=smartsurve.getJSONArray("subcategories");
                   final JSONArray finenceflexarr=financialobj.getJSONArray("subcategories");
                    final JSONArray mallarr=mall.getJSONArray("subcategories");

                    String imgurl=jsonObject.getString("url");

                    GridLayoutManager gridLayoutManager1 = new GridLayoutManager(AllServices.this,3);
                    AllServicesAdapterFinancialFlux financialfluxAdapter = new AllServicesAdapterFinancialFlux(AllServices.this, finenceflexarr,imgurl);
                    rvfinancialflux.setLayoutManager(gridLayoutManager1);
                    rvfinancialflux.setAdapter(financialfluxAdapter);

                    rvfinancialflux.addOnItemTouchListener(new RecyclerItemClickListener(AllServices.this, rvfinancialflux, new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            try {
                                final JSONObject categoryjson = finenceflexarr.getJSONObject(position);
                                final String id = categoryjson.getString("id");
                                Intent intent = new Intent(AllServices.this, ServicesProducts.class);
                                intent.putExtra("cate_id", id);
                                startActivity(intent);
                            }catch (Exception e)
                            {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onLongItemClick(View view, int position) {

                        }
                    }));
//******

                    // creative gallery


                    GridLayoutManager gridLayoutManager2 = new GridLayoutManager(AllServices.this,3);
                    AllServicesAdapterCreativeGallery creativeGalleryAdapter = new AllServicesAdapterCreativeGallery(AllServices.this, creativegallery,imgurl);
                    rvcreativegallery.setLayoutManager(gridLayoutManager2);
                    rvcreativegallery.setAdapter(creativeGalleryAdapter);

                    rvcreativegallery.addOnItemTouchListener(new RecyclerItemClickListener(AllServices.this, rvcreativegallery, new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            try {
                                final JSONObject categoryjson = creativegallery.getJSONObject(position);
                                final String id = categoryjson.getString("id");
                                Intent intent = new Intent(AllServices.this, ServicesProducts.class);
                                intent.putExtra("cate_id", id);
                                startActivity(intent);
                            }catch (Exception e)
                            {
                                e.printStackTrace();
                            }


                        }

                        @Override
                        public void onLongItemClick(View view, int position) {

                        }
                    }));
//*******

                    // smart serve


                    GridLayoutManager gridLayoutManager3 = new GridLayoutManager(AllServices.this,3);
                    AllServicesAdapterSmartServe smartserveAdapter = new AllServicesAdapterSmartServe(AllServices.this, smartsurv,imgurl);
                    rvsmartserve.setLayoutManager(gridLayoutManager3);
                    rvsmartserve.setAdapter(smartserveAdapter);

                    rvsmartserve.addOnItemTouchListener(new RecyclerItemClickListener(AllServices.this, rvsmartserve, new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {

                            try {
                                final JSONObject categoryjson = smartsurv.getJSONObject(position);
                                final String id = categoryjson.getString("id");
                                Intent intent = new Intent(AllServices.this, ServicesProducts.class);
                                intent.putExtra("cate_id", id);
                                startActivity(intent);
                            }catch (Exception e)
                            {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onLongItemClick(View view, int position) {

                        }
                    }));


                    // mall


                    GridLayoutManager gridLayoutManager4 = new GridLayoutManager(AllServices.this,3);
                    AllServicesAdapterMall mallAdapter = new AllServicesAdapterMall(AllServices.this, mallarr,imgurl);
                    rvmall.setLayoutManager(gridLayoutManager4);
                    rvmall.setAdapter(mallAdapter);

                    rvmall.addOnItemTouchListener(new RecyclerItemClickListener(AllServices.this, rvmall, new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            try {
                                final JSONObject categoryjson = mallarr.getJSONObject(position);
                                final String id = categoryjson.getString("id");
                                Intent intent = new Intent(AllServices.this, ServicesProducts.class);
                                intent.putExtra("cate_id", id);
                                startActivity(intent);
                            }catch (Exception e)
                            {
                                e.printStackTrace();
                            }


                        }

                        @Override
                        public void onLongItemClick(View view, int position) {

                        }
                    }));
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(AllServices.this,3);
                            AllServicesAdapter subServicesAdapter = new AllServicesAdapter(AllServices.this,edubasketarr,imgurl);
                            rv_allservices.setLayoutManager(gridLayoutManager);
                            rv_allservices.setAdapter(subServicesAdapter);
                    rv_allservices.addOnItemTouchListener(new RecyclerItemClickListener(AllServices.this, rv_allservices, new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            try {
                                final JSONObject categoryjson = edubasketarr.getJSONObject(position);
                                final String id = categoryjson.getString("id");
                                Intent intent = new Intent(AllServices.this, ServicesProducts.class);
                                intent.putExtra("cate_id", id);
                                startActivity(intent);
                            }catch (Exception e)
                            {
                                e.printStackTrace();
                            }


                        }

                        @Override
                        public void onLongItemClick(View view, int position) {

                        }
                    }));

//

//****rv_allservices


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
            //            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("Content-Type", "application/json");
//                return params;
//            }
            //
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
//                Map<String, String> map = new HashMap<String, String>();
//                map.put("name",etRFirstName.getText().toString());
//                //map.put("middle_name","");
//                // map.put("last_name", etRLastName.getText().toString());
//                map.put("email",etREmail.getText().toString());
//                map.put("password",etRPassword.getText().toString());
//                map.put("user_type","normal");
//                // map.put("role","1");
//                //map.put("id","A55C88D-321D-4E13-BBF3-F11A8FE56A6D");
//                map.put("confirm_password",etRConfirmPassword.getText().toString());
//                return map;
//            }

        };
        requestQueue.add(stringRequest);
    }

//    private void getsubCategory() {
//        progressDialog = new ProgressDialog(AllServices.this);
//        progressDialog.setMessage("Loading...");
//        progressDialog.show();
//        NetworkService networkService = NetworkClient.getClient().create(NetworkService.class);
//        Call<SubCategoryProduct> categoriesCall = networkService.getSubCategoryProduct("NXqXAgFZA0jiWp5t6+=lGpgWJXEkbo");
//
//        categoriesCall.enqueue(new Callback<SubCategoryProduct>() {
//            @Override
//            public void onResponse(Call<SubCategoryProduct> call, Response<SubCategoryProduct> response) {
//                if (response.body() != null) {
//                    if (response.body().getSuccess().equals("1")) {
//                        if (response.body().getCategory() != null) {
//                            //    managerEduBasket = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
//
//
//                            progressDialog.dismiss();
//                            finalProductSubServicesModel = new ArrayList<>();
//                            finalProductSubServicesModel.addAll(response.body().getCategory());
//
//                            // edu
//                            final ArrayList<Category> serviceedu = new ArrayList<>();
//
//                            for (int i=0; i<finalProductSubServicesModel.size(); i++){
//                                if (finalProductSubServicesModel.get(i).getCategoryid()==78){
//                                    serviceedu.add(finalProductSubServicesModel.get(i));
//                                }
//                            }
//
//                            GridLayoutManager gridLayoutManager = new GridLayoutManager(AllServices.this,3);
//                            AllServicesAdapter subServicesAdapter = new AllServicesAdapter(AllServices.this, serviceedu);
//                            rv_allservices.setLayoutManager(gridLayoutManager);
//                            rv_allservices.setAdapter(subServicesAdapter);
//
//                            rv_allservices.addOnItemTouchListener(new RecyclerItemClickListener(AllServices.this, rv_allservices, new RecyclerItemClickListener.OnItemClickListener() {
//                                @Override
//                                public void onItemClick(View view, int position) {
//
//
//                                    if (serviceedu.get(position).getUrl()==""){
//                                        Intent intent0 = new Intent(AllServices.this, ServicesProducts.class);
//
//                                        intent0.putExtra("cate_id", serviceedu.get(position).getId());
//                                        startActivity(intent0);
//                                    }
//                                    else {
//                                        Intent intent0 = new Intent(AllServices.this, WebViewLoadUrl.class);
//
//                                        intent0.putExtra("url", serviceedu.get(position).getUrl());
//                                        startActivity(intent0);
//                                    }
//
//
//                                }
//
//                                @Override
//                                public void onLongItemClick(View view, int position) {
//
//                                }
//                            }));
//                            //*********
//
//                            // financial flux
//
//                            final ArrayList<Category> financialflux = new ArrayList<>();
//
//                            for (int i=0; i<finalProductSubServicesModel.size(); i++){
//                                if (finalProductSubServicesModel.get(i).getCategoryid()==49){
//                                    financialflux.add(finalProductSubServicesModel.get(i));
//                                }
//                            }
//
//                            GridLayoutManager gridLayoutManager1 = new GridLayoutManager(AllServices.this,3);
//                            AllServicesAdapterFinancialFlux financialfluxAdapter = new AllServicesAdapterFinancialFlux(AllServices.this, financialflux);
//                            rvfinancialflux.setLayoutManager(gridLayoutManager1);
//                            rvfinancialflux.setAdapter(financialfluxAdapter);
//
//                            rvfinancialflux.addOnItemTouchListener(new RecyclerItemClickListener(AllServices.this, rvfinancialflux, new RecyclerItemClickListener.OnItemClickListener() {
//                                @Override
//                                public void onItemClick(View view, int position) {
//
//
//
//                                    if (financialflux.get(position).getUrl()==""){
//                                        Intent intent1 = new Intent(AllServices.this, ServicesProducts.class);
//
//                                        intent1.putExtra("cate_id", financialflux.get(position).getId());
//                                        startActivity(intent1);
//                                    }
//                                    else {
//                                        Intent intent1 = new Intent(AllServices.this, WebViewLoadUrl.class);
//
//                                        intent1.putExtra("url", financialflux.get(position).getUrl());
//                                        startActivity(intent1);
//                                    }
//
//
//                                }
//
//                                @Override
//                                public void onLongItemClick(View view, int position) {
//
//                                }
//                            }));
////******
//
//                            // creative gallery
//
//                            final ArrayList<Category> creativegallery = new ArrayList<>();
//
//                            for (int i=0; i<finalProductSubServicesModel.size(); i++){
//                                if (finalProductSubServicesModel.get(i).getCategoryid()==50){
//                                    creativegallery.add(finalProductSubServicesModel.get(i));
//                                }
//                            }
//
//                            GridLayoutManager gridLayoutManager2 = new GridLayoutManager(AllServices.this,3);
//                            AllServicesAdapterCreativeGallery creativeGalleryAdapter = new AllServicesAdapterCreativeGallery(AllServices.this, creativegallery);
//                            rvcreativegallery.setLayoutManager(gridLayoutManager2);
//                            rvcreativegallery.setAdapter(creativeGalleryAdapter);
//
//                            rvcreativegallery.addOnItemTouchListener(new RecyclerItemClickListener(AllServices.this, rvcreativegallery, new RecyclerItemClickListener.OnItemClickListener() {
//                                @Override
//                                public void onItemClick(View view, int position) {
//
//                                    if (creativegallery.get(position).getUrl()==""){
//                                        Intent intent1 = new Intent(AllServices.this, ServicesProducts.class);
//
//                                        intent1.putExtra("cate_id", creativegallery.get(position).getId());
//                                        startActivity(intent1);
//                                    }
//                                    else {
//                                        Intent intent1 = new Intent(AllServices.this, WebViewLoadUrl.class);
//
//                                        intent1.putExtra("url", creativegallery.get(position).getUrl());
//                                        startActivity(intent1);
//                                    }
//
//
//                                }
//
//                                @Override
//                                public void onLongItemClick(View view, int position) {
//
//                                }
//                            }));
////*******
//
//                            // smart serve
//
//                            final ArrayList<Category> smartserve = new ArrayList<>();
//
//                            for (int i=0; i<finalProductSubServicesModel.size(); i++){
//                                if (finalProductSubServicesModel.get(i).getCategoryid()==79){
//                                    smartserve.add(finalProductSubServicesModel.get(i));
//                                }
//                            }
//
//                            GridLayoutManager gridLayoutManager3 = new GridLayoutManager(AllServices.this,3);
//                            AllServicesAdapterSmartServe smartserveAdapter = new AllServicesAdapterSmartServe(AllServices.this, smartserve);
//                            rvsmartserve.setLayoutManager(gridLayoutManager3);
//                            rvsmartserve.setAdapter(smartserveAdapter);
//
//                            rvsmartserve.addOnItemTouchListener(new RecyclerItemClickListener(AllServices.this, rvsmartserve, new RecyclerItemClickListener.OnItemClickListener() {
//                                @Override
//                                public void onItemClick(View view, int position) {
//
//                                    if (smartserve.get(position).getUrl()==""){
//                                        Intent intent1 = new Intent(AllServices.this, ServicesProducts.class);
//
//                                        intent1.putExtra("cate_id", smartserve.get(position).getId());
//                                        startActivity(intent1);
//                                    }
//                                    else {
//                                        Intent intent1 = new Intent(AllServices.this, WebViewLoadUrl.class);
//
//                                        intent1.putExtra("url", smartserve.get(position).getUrl());
//                                        startActivity(intent1);
//                                    }
//
//
//                                }
//
//                                @Override
//                                public void onLongItemClick(View view, int position) {
//
//                                }
//                            }));
//
//
//                            // mall
//
//
//                            GridLayoutManager gridLayoutManager4 = new GridLayoutManager(AllServices.this,3);
//                            AllServicesAdapterMall mallAdapter = new AllServicesAdapterMall(AllServices.this, );
//                            rvmall.setLayoutManager(gridLayoutManager4);
//                            rvmall.setAdapter(mallAdapter);
//
//                            rvmall.addOnItemTouchListener(new RecyclerItemClickListener(AllServices.this, rvmall, new RecyclerItemClickListener.OnItemClickListener() {
//                                @Override
//                                public void onItemClick(View view, int position) {
//
//
//                                }
//
//                                @Override
//                                public void onLongItemClick(View view, int position) {
//
//                                }
//                            }));
//
////****
//
//
///*
//                            rv_allservices.addOnItemTouchListener(new RecyclerItemClickListener(AllServices.this, rv_allservices, new RecyclerItemClickListener.OnItemClickListener() {
//                                @Override
//                                public void onItemClick(View view, int position) {
//
//                                    Intent intent = new Intent(AllServices.this, ServicesProducts.class);
//
//                                    intent.putExtra("cate_id", finalProductSubServicesModel.get(position).getId());
//                                    startActivity(intent);
//
//
//
//                                }
//
//                                @Override
//                                public void onLongItemClick(View view, int position) {
//
//                                }
//                            }));
//*/
//
//
//
//                        }
//                    }
//                    else {
//                        progressDialog.dismiss();
//                        Toast.makeText(AllServices.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
//
//                    }
//                } else {
//                    progressDialog.dismiss();
//                    Toast.makeText(AllServices.this, "Something went wrong...!!", Toast.LENGTH_SHORT).show();
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<SubCategoryProduct> call, Throwable t) {
//                progressDialog.dismiss();
//                Toast.makeText(AllServices.this, "Something went wrong...!!", Toast.LENGTH_SHORT).show();
//
//            }
//        });
//    }

    private ArrayList<Category> removeDuplicates(ArrayList<Category> finalProductSubServicesModel) {
        // Create a new ArrayList
        ArrayList<Category> newList = new ArrayList<Category>();

        // Traverse through the first list
        for (Category element : finalProductSubServicesModel) {

            // If this element is not present in newList
            // then add it
            if (!newList.contains(element)) {

                newList.add(element);
            }
        }

        // return the new list
        return newList;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

   /* // Function to remove duplicates from an ArrayList
    public static <T> ArrayList<T> removeDuplicates(ArrayList<T> list)
    {

        // Create a new ArrayList
        ArrayList<T> newList = new ArrayList<T>();

        // Traverse through the first list
        for (T element : list) {

            // If this element is not present in newList
            // then add it
            if (!newList.contains(element)) {

                newList.add(element);
            }
        }

        // return the new list
        return newList;
    }*/
}
