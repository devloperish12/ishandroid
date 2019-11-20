package com.indiansmarthub.ish.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.indiansmarthub.ish.R;
import com.indiansmarthub.ish.model.CategoryServices;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class SubServicesAdapter extends RecyclerView.Adapter<SubServicesAdapter.MyViewHolder> {
    Context context;
    String url;
   // List<CategoryServices> categoryServices;
    JSONArray categoryServices=new JSONArray();
   // public SubServicesAdapter(Context context, List<CategoryServices> categoryServices) {
         public SubServicesAdapter(Context context, JSONArray categoryServices,String url) {
        this.context = context;
        this.categoryServices = categoryServices;
        this.url=url;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.sub_category_layout, parent, false);
        return new SubServicesAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {

             try
             {
                 int len=categoryServices.length();
                 JSONObject jsonObject= categoryServices.getJSONObject(i);
                 holder.tvCategoryName.setText(jsonObject.getString("name"));
                 String imgurl=url+"/"+jsonObject.getString("img");

                 Picasso.with(context)
                         .load(imgurl)
                         .error(R.drawable.logo)
                         .into(holder.ivCategoryImage);
             }catch (Exception e)
             {
                 e.printStackTrace();
             }
//        final CategoryServices modelCategory = categoryServices.get(holder.getAdapterPosition());
//        holder.tvCategoryName.setText(modelCategory.getName());
//        Picasso.with(context)
//                .load(modelCategory.getImg())
//                .error(R.drawable.logo)
//                .into(holder.ivCategoryImage);
    }

    @Override
    public int getItemCount() {
       /* return finalProductSubServicesModel.size();*/
        return categoryServices.length();
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
