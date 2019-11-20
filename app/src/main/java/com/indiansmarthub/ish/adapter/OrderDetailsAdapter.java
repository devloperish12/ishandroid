package com.indiansmarthub.ish.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.indiansmarthub.ish.R;
import com.indiansmarthub.ish.model.ModelOrderItem;

import java.util.List;

public class OrderDetailsAdapter extends RecyclerView.Adapter<OrderDetailsAdapter.OrderDetailsHolder> {
    Context context;
    List<ModelOrderItem> productsItemsList;
    public OrderDetailsAdapter(Context context, List<ModelOrderItem> productsItemsList) {
        this.context = context;
        this.productsItemsList = productsItemsList;
    }

    @NonNull
    @Override
    public OrderDetailsHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_order_details_product, parent, false);
        return new OrderDetailsAdapter.OrderDetailsHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderDetailsHolder holder, int i) {
        holder.orderProductName.setText(productsItemsList.get(holder.getAdapterPosition()).getName());
        holder.orderProductSKU.setText(productsItemsList.get(holder.getAdapterPosition()).getSku());
        holder.orderProductQty.setText("\u20b9 " + String.format("%.2f", Double.parseDouble(productsItemsList.get(holder.getAdapterPosition()).getPrice())) + "X" + productsItemsList.get(holder.getAdapterPosition()).getQty());
        holder.orderProductPrice.setText("\u20b9 " + String.format("%.2f", Double.parseDouble(productsItemsList.get(holder.getAdapterPosition()).getPrice())));
    }

    @Override
    public int getItemCount() {
        return productsItemsList.size();
    }

    public class OrderDetailsHolder extends RecyclerView.ViewHolder {
        TextView orderProductName, orderProductSKU, orderProductQty, orderProductPrice;
        public OrderDetailsHolder(@NonNull View itemView) {
            super(itemView);
            orderProductName =  itemView.findViewById(R.id.orderProductName);
            orderProductSKU =  itemView.findViewById(R.id.orderProductSKU);
            orderProductQty =  itemView.findViewById(R.id.orderProductQty);
            orderProductPrice =  itemView.findViewById(R.id.orderProductPrice);
        }
    }


    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }*/


}
