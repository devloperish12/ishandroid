package com.indiansmarthub.ish.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.indiansmarthub.ish.R;
import com.indiansmarthub.ish.activity.BlogsDetailActivity;
import com.indiansmarthub.ish.adapter.BlogViweAdapter;
import com.indiansmarthub.ish.custom.GeneralCode;
import com.indiansmarthub.ish.javaclass.RecyclerItemClickListener;
import com.indiansmarthub.ish.model.ModelBlog;
import com.indiansmarthub.ish.model.ModelResponseBlog;
import com.indiansmarthub.ish.retrofit.NetworkClient;
import com.indiansmarthub.ish.retrofit.NetworkService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */

public class Blogs extends Fragment {
View view;
    List<ModelBlog> finalCreativeGallery;
    BlogViweAdapter blogViweAdapterl;
    LinearLayout internetLayout;
    RecyclerView rvProductListViewMoreBlog;
    private TextView noProdlistFound;
    ProgressBar mProgressbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_blogs, container, false);

        init();

        if (GeneralCode.isConnectingToInternet(getActivity())) {

           /* getViewMoreBlogProductList();*/

        } else {
            GeneralCode.showDialog(getActivity());
        }

        return view;
    }

    private void init() {
        finalCreativeGallery = new ArrayList<>();
        rvProductListViewMoreBlog = view.findViewById(R.id.rvProductListViewMoreBlog);

        noProdlistFound = view.findViewById(R.id.noProdlistFound);
        mProgressbar = view.findViewById(R.id.progress_bar);
    }

  /*  private void getViewMoreBlogProductList() {
        mProgressbar.setVisibility(View.VISIBLE);
        NetworkService networkService = NetworkClient.getClient().create(NetworkService.class);
        Call<ModelResponseBlog> categoriesCall = networkService.getBlogVies("NXqXAgFZA0jiWp5t6+=lGpgWJXEkbo");
        categoriesCall.enqueue(new Callback<ModelResponseBlog>() {
            @Override
            public void onResponse(Call<ModelResponseBlog> call, Response<ModelResponseBlog> response) {
                if (response.body() != null) {
                    if (response.body().getSuccess().equals("1")) {
                        if (response.body().getCategory() != null) {
                            GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2);

                            finalCreativeGallery = new ArrayList<>();
                            rvProductListViewMoreBlog.setVisibility(View.VISIBLE);
                            noProdlistFound.setVisibility(View.GONE);

                            finalCreativeGallery.addAll(response.body().getCategory());

                            calllAdapter();

                            mProgressbar.setVisibility(View.GONE);


                        }
                    } else {
                        rvProductListViewMoreBlog.setVisibility(View.GONE);
                        noProdlistFound.setVisibility(View.VISIBLE);
                        mProgressbar.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();


                    }
                } else {
                    rvProductListViewMoreBlog.setVisibility(View.GONE);
                    noProdlistFound.setVisibility(View.VISIBLE);
                    mProgressbar.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "Something went wrong...!!", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ModelResponseBlog> call, Throwable t) {
                mProgressbar.setVisibility(View.GONE);
                rvProductListViewMoreBlog.setVisibility(View.GONE);
                noProdlistFound.setVisibility(View.VISIBLE);
                Toast.makeText(getActivity(), "Something went wrong...!!", Toast.LENGTH_SHORT).show();


            }
        });
    }*/

    private void calllAdapter() {
        blogViweAdapterl = new BlogViweAdapter(getActivity(), finalCreativeGallery);
        rvProductListViewMoreBlog.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        //   rvProductListViewMoreBlog.setLayoutManager(gridLayoutManager);
        rvProductListViewMoreBlog.setAdapter(blogViweAdapterl);
        blogViweAdapterl.notifyDataSetChanged();

        rvProductListViewMoreBlog.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), rvProductListViewMoreBlog, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent i = new Intent(getActivity(), BlogsDetailActivity.class);
                i.putExtra("image", finalCreativeGallery.get(position).getImg());
                i.putExtra("title", finalCreativeGallery.get(position).getName());
                i.putExtra("date", finalCreativeGallery.get(position).getDate());
                i.putExtra("contain", finalCreativeGallery.get(position).getContent());
                startActivity(i);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
    }

}
