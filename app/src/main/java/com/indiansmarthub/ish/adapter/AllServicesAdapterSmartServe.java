package com.indiansmarthub.ish.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.indiansmarthub.ish.R;
import com.indiansmarthub.ish.activity.ServicesProducts;
import com.indiansmarthub.ish.model.Category;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AllServicesAdapterSmartServe extends RecyclerView.Adapter<AllServicesAdapterSmartServe.MyViewHolder> {
    Context context;
    List<Category> serviceedu;
    JSONArray jsonArray;
    String url;

    public AllServicesAdapterSmartServe(Context context, JSONArray jsonElements,String url) {
        this.context = context;
        this.jsonArray = jsonElements;
        this.url=url;
    }

    @NonNull
    @Override
    public AllServicesAdapterSmartServe.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.sub_category_layout, parent, false);
        return new AllServicesAdapterSmartServe.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
try {
    JSONObject jsonObject=jsonArray.getJSONObject(position);
    String name=jsonObject.getString("name");
    String imgurl=url+"/"+jsonObject.getString("img");
    holder.tvCategoryName.setText(name);
    Picasso.with(context)
            .load(imgurl)
            .error(R.drawable.logo)
            .into(holder.ivCategoryImage);

}catch (Exception e)
{
    e.printStackTrace();
}


    }

    @Override
    public int getItemCount() {
        return jsonArray.length();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView ivCategoryImage;
        TextView tvCategoryName;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ivCategoryImage = itemView.findViewById(R.id.ivSubCategoryImage);
            tvCategoryName = itemView.findViewById(R.id.tvSubCategoryName);
        }
    }
}


