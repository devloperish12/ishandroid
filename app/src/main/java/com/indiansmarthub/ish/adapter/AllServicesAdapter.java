package com.indiansmarthub.ish.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.indiansmarthub.ish.R;
import com.indiansmarthub.ish.fragments.Cart;
import com.indiansmarthub.ish.model.Category;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AllServicesAdapter extends RecyclerView.Adapter<AllServicesAdapter.MyViewHolder> {
    Context context;
    List<Category> serviceedu;
    JSONArray jsonArray;
    String url;
    public AllServicesAdapter(Context context ,JSONArray jsonArray,String url) {
        this.context = context;

        this.jsonArray=jsonArray;
        this.url=url;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.sub_category_layout, parent, false);
        return new AllServicesAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {


//        holder.tvCategoryName.setText(serviceedu.get(position).getName());
//        Picasso.with(context)
//                .load(serviceedu.get(position).getImg())
//                .error(R.drawable.logo)
//                .into(holder.ivCategoryImage);
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




        /*GridLayoutManager gridLayoutManager = new GridLayoutManager(context,4);
        SubServicesHorizontalAdapter subServicesHorizontalAdapter = new SubServicesHorizontalAdapter(context, finalProductSubServicesModel);
        holder.mRecyclerView.setLayoutManager(gridLayoutManager);
        holder.mRecyclerView.setAdapter(subServicesHorizontalAdapter);*/


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
