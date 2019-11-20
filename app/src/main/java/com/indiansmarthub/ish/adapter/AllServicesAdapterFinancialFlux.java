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

import com.indiansmarthub.ish.R;
import com.indiansmarthub.ish.activity.ServicesProducts;
import com.indiansmarthub.ish.model.Category;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AllServicesAdapterFinancialFlux extends RecyclerView.Adapter<AllServicesAdapterFinancialFlux.MyViewHolder> {
    Context context;
    List<Category> serviceedu;
    JSONArray jsonArray;
    String url;

    public AllServicesAdapterFinancialFlux(Context context, JSONArray jsonArray, String url) {
        this.url=url;
        this.context = context;
        this.jsonArray=jsonArray;
    }

    @NonNull
    @Override
    public AllServicesAdapterFinancialFlux.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.sub_category_layout, parent, false);
        return new AllServicesAdapterFinancialFlux.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

try {

    JSONObject jsonObject=jsonArray.getJSONObject(position);
    //String id=jsonObject.getString("id");
    String name=jsonObject.getString("name");
    String imgurl=url+"/"+jsonObject.getString("img");

    holder.tvCategoryName.setText(name);
    Picasso.with(context)
            .load(imgurl)
            .error(R.drawable.logo)
            .into(holder.ivCategoryImage);

//    holder.ivCategoryImage.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//
//        }
//    });

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

