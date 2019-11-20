package com.indiansmarthub.ish.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.indiansmarthub.ish.R;
import com.indiansmarthub.ish.fragments.Account;
import com.indiansmarthub.ish.pojo.AccountPojo;

import java.util.ArrayList;

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.MyViewHolder> {
    Context context;
    ArrayList<AccountPojo> arrayList;

    public AccountAdapter(Context context, ArrayList<AccountPojo> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_account_layout, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int pos) {
        AccountPojo accountPojo = arrayList.get(pos);

        holder.imageView.setImageDrawable(accountPojo.getImage());
        holder.name.setText(accountPojo.getName());

        Log.e("tag_size", String.valueOf(arrayList.size()));
        Log.e("tag_name", arrayList.get(pos).getName());



    }

    @Override
    public int getItemCount() {
        return arrayList.size();

    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView name;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.name);

        }
    }
}
