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
import com.indiansmarthub.ish.model.ModelBlog;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BlogViweAdapter  extends RecyclerView.Adapter<BlogViweAdapter.CreativeGalleryHolder>{
    Context context;
    List<ModelBlog> finalCreativeGallery;
    public BlogViweAdapter(Context context, List<ModelBlog> finalCreativeGallery) {
        this.context = context;
        this.finalCreativeGallery = finalCreativeGallery;
    }

    @NonNull
    @Override
    public CreativeGalleryHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_blog_view_all, parent, false);
        return new BlogViweAdapter.CreativeGalleryHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CreativeGalleryHolder holder, final int postion) {
        Picasso.with(context)
                .load(finalCreativeGallery.get(postion).getImg())
                .error(R.drawable.logo)
                .into(holder.ivBlogImageViewMore);

        holder.tvTitleViewMore.setText(finalCreativeGallery.get(postion).getName());
        holder.tvDateViewMore.setText(finalCreativeGallery.get(postion).getDate());
     //   holder.tvContainViewMore.setText(finalCreativeGallery.get(postion).getContent());

       /* holder.ivBlogImageViewMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, BlogDetailsActivity.class);
                i.putExtra("image", finalCreativeGallery.get(postion).getImg());
                i.putExtra("title", finalCreativeGallery.get(postion).getName());
                i.putExtra("date", finalCreativeGallery.get(postion).getDate());
                i.putExtra("contain", finalCreativeGallery.get(postion).getContent());
                context.startActivity(i);
            }
        });
        holder.tvTitleViewMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, BlogDetailsActivity.class);
                i.putExtra("image", finalCreativeGallery.get(postion).getImg());
                i.putExtra("title", finalCreativeGallery.get(postion).getName());
                i.putExtra("date", finalCreativeGallery.get(postion).getDate());
                i.putExtra("contain", finalCreativeGallery.get(postion).getContent());
                context.startActivity(i);
            }
        });*/

    }

    @Override
    public int getItemCount() {
        return finalCreativeGallery.size();
    }

    public class CreativeGalleryHolder extends RecyclerView.ViewHolder {
        ImageView ivBlogImageViewMore;
        TextView tvTitleViewMore,tvDateViewMore,tvContainViewMore;
        public CreativeGalleryHolder(@NonNull View itemView) {
            super(itemView);
            ivBlogImageViewMore = itemView.findViewById(R.id.ivBlogImageViewMore);
            tvTitleViewMore = itemView.findViewById(R.id.tvTitleViewMore);
            tvDateViewMore = itemView.findViewById(R.id.tvDateViewMore);
         //   tvContainViewMore = itemView.findViewById(R.id.tvContainViewMore);
        }
    }
}