package com.indiansmarthub.ish.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.indiansmarthub.ish.R;
import com.indiansmarthub.ish.model.Category;
import com.indiansmarthub.ish.model.CategoryServices;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SubServicesHorizontalAdapter extends RecyclerView.Adapter<SubServicesHorizontalAdapter.MyViewHolder> {
    Context context;
    List<Category> finalProductSubServicesModel;
    public SubServicesHorizontalAdapter(Context context, List<Category> finalProductSubServicesModel) {
        this.context = context;
        this.finalProductSubServicesModel = finalProductSubServicesModel;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.sub_category_layout, parent, false);
        return new SubServicesHorizontalAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {

        final Category modelCategory = finalProductSubServicesModel.get(holder.getAdapterPosition());
        holder.tvCategoryName.setText(modelCategory.getName());
        Picasso.with(context)
                .load(modelCategory.getImg())
                .error(R.drawable.logo)
                .into(holder.ivCategoryImage);
    }

    @Override
    public int getItemCount() {
        return finalProductSubServicesModel.size();
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
