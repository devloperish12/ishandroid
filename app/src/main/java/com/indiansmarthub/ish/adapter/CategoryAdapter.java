package com.indiansmarthub.ish.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.indiansmarthub.ish.R;
import com.indiansmarthub.ish.model.ModelCategory;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryHolder>{

    Context context;
    List<ModelCategory> finalcategoryArrayModel;
    public CategoryAdapter(Context context, List<ModelCategory> finalcategoryArrayModel) {
        this.context = context;
        this.finalcategoryArrayModel = finalcategoryArrayModel;
    }

    @NonNull
    @Override
    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_main_category_layout, parent, false);
        return new CategoryAdapter.CategoryHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryHolder holder, int postion) {
        final ModelCategory modelCategory = finalcategoryArrayModel.get(holder.getAdapterPosition());
        holder.tvCategoryName.setText(modelCategory.getName());
        Picasso.with(context)
                .load(modelCategory.getImg())
                .error(R.drawable.logo)
                .into(holder.ivCategoryImage);

/*
        holder.ivCategoryImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SubCategoriesActivity.class);
                intent.putExtra("sub_cat_id", modelCategory.getId());
                intent.putExtra("product_name", modelCategory.getName());
                context.startActivity(intent);

            }
        });
*/
    }

    @Override
    public int getItemCount() {
        return finalcategoryArrayModel.size();
    }

    public class CategoryHolder extends RecyclerView.ViewHolder {
        ImageView ivCategoryImage;
        TextView tvCategoryName;

        public CategoryHolder(@NonNull View itemView) {
            super(itemView);
            ivCategoryImage = itemView.findViewById(R.id.ivCategoryImage);
            tvCategoryName = itemView.findViewById(R.id.tvCategoryName);
        }
    }

}
