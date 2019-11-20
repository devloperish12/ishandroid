package com.indiansmarthub.ish.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.indiansmarthub.ish.R;
import com.indiansmarthub.ish.adapter.SearchProductAdapter;
import com.indiansmarthub.ish.adapter.ViewMoreProductListAdapter;
import com.indiansmarthub.ish.custom.GeneralCode;
import com.indiansmarthub.ish.model.ModelFeaturedProduct;
import com.indiansmarthub.ish.model.ModelResponseFeaturedProduct;
import com.indiansmarthub.ish.model.ViewCategoryProducts;
import com.indiansmarthub.ish.retrofit.NetworkClient;
import com.indiansmarthub.ish.retrofit.NetworkService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewMoreProductActivity extends AppCompatActivity {
    RecyclerView rvProductListViewMore;
    private TextView noProdlistFound;
    private int flag;
    String cate_id;
    private ProgressDialog progressDialog;
    List<ModelFeaturedProduct> finalProductModel;
    SharedPreferences prefManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_more_product);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        prefManager = ViewMoreProductActivity.this.getSharedPreferences("ISH", this.MODE_PRIVATE);
        rvProductListViewMore = findViewById(R.id.rvProductListViewMore);
        noProdlistFound = findViewById(R.id.noProdlistFound);


        if (getIntent().hasExtra("flag")) {
            flag = Integer.parseInt(getIntent().getStringExtra("flag"));
        }
        if (GeneralCode.isConnectingToInternet(ViewMoreProductActivity.this)){
            if (flag == 1){
                getViewMoreProductList();
            }else if (flag == 2){
                getnewArrivalProductList();
            }


        }
        else {
            GeneralCode.showDialog(ViewMoreProductActivity.this);
        }

    }



    private void getViewMoreProductList() {
        progressDialog = new ProgressDialog(ViewMoreProductActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        NetworkService networkService = NetworkClient.getClient().create(NetworkService.class);
        Call<ModelResponseFeaturedProduct> categoriesCall = networkService.getfeaturedproduct("NXqXAgFZA0jiWp5t6+=lGpgWJXEkbo",prefManager.getString("cust_id",""));
        categoriesCall.enqueue(new Callback<ModelResponseFeaturedProduct>() {
            @Override
            public void onResponse(Call<ModelResponseFeaturedProduct> call, Response<ModelResponseFeaturedProduct> response) {

                if (response.body() != null) {
                    if (response.body().getSuccess().equals("1")) {
                        if (response.body().getFeaturedProduct() != null) {
                            progressDialog.dismiss();
                            //linearLayoutManagerPrroductList = new LinearLayoutManager(ProductListActivity.this, LinearLayoutManager.VERTICAL, false);
                            
                            finalProductModel = new ArrayList<>();
                            rvProductListViewMore.setVisibility(View.VISIBLE);
                            noProdlistFound.setVisibility(View.GONE);

                            finalProductModel.clear();

                            finalProductModel.addAll(response.body().getFeaturedProduct());

                            // call adapter
                            GridLayoutManager gridLayoutManager = new GridLayoutManager(ViewMoreProductActivity.this,2);
                            rvProductListViewMore.setLayoutManager(gridLayoutManager);
                            ViewMoreProductListAdapter adapter = new ViewMoreProductListAdapter(ViewMoreProductActivity.this, finalProductModel);
                            rvProductListViewMore.setAdapter(adapter);
                            //

                           /* viewMoreProductListAdapter = new ViewMoreProductListAdapter();
                            rvProductListViewMore.setLayoutManager(gridLayoutManager);
                            rvProductListViewMore.setAdapter(viewMoreProductListAdapter);
                            viewMoreProductListAdapter.notifyDataSetChanged();*/
                            progressDialog.dismiss();


                        }
                    } else {
                        rvProductListViewMore.setVisibility(View.GONE);
                        noProdlistFound.setVisibility(View.VISIBLE);
                        progressDialog.dismiss();
                        Toast.makeText(ViewMoreProductActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    }
                } else {
                    rvProductListViewMore.setVisibility(View.GONE);
                    noProdlistFound.setVisibility(View.VISIBLE);
                    progressDialog.dismiss();
                    Toast.makeText(ViewMoreProductActivity.this, "Something went wrong...!!", Toast.LENGTH_SHORT).show();


                }
            }

            @Override
            public void onFailure(Call<ModelResponseFeaturedProduct> call, Throwable t) {
                progressDialog.dismiss();
                rvProductListViewMore.setVisibility(View.GONE);
                noProdlistFound.setVisibility(View.VISIBLE);
                Toast.makeText(ViewMoreProductActivity.this, "Something went wrong...!!", Toast.LENGTH_SHORT).show();

            }
        });

    }


    private void getnewArrivalProductList() {
        progressDialog = new ProgressDialog(ViewMoreProductActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        NetworkService networkService = NetworkClient.getClient().create(NetworkService.class);
        Call<ModelResponseFeaturedProduct> categoriesCall = networkService.getnewArrivaleproduct("NXqXAgFZA0jiWp5t6+=lGpgWJXEkbo",prefManager.getString("cust_id",""));

        categoriesCall.enqueue(new Callback<ModelResponseFeaturedProduct>() {
            @Override
            public void onResponse(Call<ModelResponseFeaturedProduct> call, Response<ModelResponseFeaturedProduct> response) {
                if (response.body() != null) {
                    if (response.body().getSuccess().equals("1")) {
                        if (response.body().getFeaturedProduct() != null) {
                            //linearLayoutManagerPrroductList = new LinearLayoutManager(ProductListActivity.this, LinearLayoutManager.VERTICAL, false);

                            finalProductModel = new ArrayList<>();
                            rvProductListViewMore.setVisibility(View.VISIBLE);
                            noProdlistFound.setVisibility(View.GONE);

                            finalProductModel.clear();

                            finalProductModel.addAll(response.body().getFeaturedProduct());

                            // call adapter
                            GridLayoutManager gridLayoutManager = new GridLayoutManager(ViewMoreProductActivity.this,2);
                            rvProductListViewMore.setLayoutManager(gridLayoutManager);
                            ViewMoreProductListAdapter adapter = new ViewMoreProductListAdapter(ViewMoreProductActivity.this, finalProductModel);
                            rvProductListViewMore.setAdapter(adapter);
                            //

                            /*viewMoreProductListAdapter = new ViewMoreProductListAdapter();
                            rvProductListViewMore.setLayoutManager(gridLayoutManager);
                            rvProductListViewMore.setAdapter(viewMoreProductListAdapter);
                            viewMoreProductListAdapter.notifyDataSetChanged();*/
                            progressDialog.dismiss();

                        }
                    } else {
                        rvProductListViewMore.setVisibility(View.GONE);
                        noProdlistFound.setVisibility(View.VISIBLE);
                        progressDialog.dismiss();
                        Toast.makeText(ViewMoreProductActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();


                    }
                } else {
                    rvProductListViewMore.setVisibility(View.GONE);
                    noProdlistFound.setVisibility(View.VISIBLE);
                    progressDialog.dismiss();
                    Toast.makeText(ViewMoreProductActivity.this, "Something went wrong...!!", Toast.LENGTH_SHORT).show();


                }
            }

            @Override
            public void onFailure(Call<ModelResponseFeaturedProduct> call, Throwable t) {
                progressDialog.dismiss();
                rvProductListViewMore.setVisibility(View.GONE);
                noProdlistFound.setVisibility(View.VISIBLE);
                Toast.makeText(ViewMoreProductActivity.this, "Something went wrong...!!", Toast.LENGTH_SHORT).show();


            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }
}
