package com.indiansmarthub.ish.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.indiansmarthub.ish.R;
import com.indiansmarthub.ish.model.ModelOrderItem;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TrackingOrderAdapter extends RecyclerView.Adapter<TrackingOrderAdapter.TraHolder> {
    Context context;
    List<ModelOrderItem> productsItemsList;
    public TrackingOrderAdapter(Context context, List<ModelOrderItem> productsItemsList) {
        this.context =context;
        this.productsItemsList =productsItemsList;
    }


    @NonNull
    @Override
    public TraHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_tracking_order_layout, parent, false);
        return new TrackingOrderAdapter.TraHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TraHolder holder, int i) {
        Picasso.with(context)
                .load(productsItemsList.get(i).getImg())
                .error(R.drawable.logo)
                .into(holder.ivTrakingImage);

        holder.tvProductTName.setText(productsItemsList.get(i).getName());
        holder.tvProductSku.setText("SKU: " +productsItemsList.get(i).getSku());
        holder.tvQtu.setText("Qty: "+productsItemsList.get(i).getQty());
    }

    @Override
    public int getItemCount() {
        return productsItemsList.size();
    }

    public class TraHolder extends RecyclerView.ViewHolder {
        TextView tvProductTName,tvProductSku,tvQtu;
        ImageView ivTrakingImage;
        public TraHolder(@NonNull View itemView) {
            super(itemView);
            tvProductTName = itemView.findViewById(R.id.tvProductTName);
            tvProductSku = itemView.findViewById(R.id.tvProductSku);
            tvQtu = itemView.findViewById(R.id.tvQtu);
            ivTrakingImage = itemView.findViewById(R.id.ivTrakingImage);
        }
    }
}
