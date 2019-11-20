package com.indiansmarthub.ish.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.indiansmarthub.ish.R;
import com.indiansmarthub.ish.activity.OrderHistoryDetails;
import com.indiansmarthub.ish.activity.OrderTrackingActivity;
import com.indiansmarthub.ish.custom.GeneralCode;
import com.indiansmarthub.ish.fragments.OrderTracking;
import com.indiansmarthub.ish.model.ModelOrderHistory;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class OrderHistoryAdapter extends RecyclerView.Adapter <OrderHistoryAdapter.OrderHolder>{
    Context context;
    List<ModelOrderHistory> finalOrderHistoryModel;
    JSONArray jsonArray;

    public OrderHistoryAdapter(Context context, List<ModelOrderHistory> finalOrderHistoryModel,JSONArray jsonArray) {
        this.context = context;
        this.finalOrderHistoryModel = finalOrderHistoryModel;
        this.jsonArray=jsonArray;
    }

    @NonNull
    @Override
    public OrderHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_orderhistory_layout, parent, false);
        return new OrderHistoryAdapter.OrderHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderHolder holder, final int postion) {
        try
        {
            JSONObject jsonObject=jsonArray.getJSONObject(postion);
            holder.tvOrderId.setText("Order #" +jsonObject.getString("order_number"));
            holder.tvOrderTotal.setText("₹ "+jsonObject.getString("grand_total"));
            //holder.tvOrderDate.setText(jsonObject.getString("ship_to_name"));
            holder.tvViewDetails.setText(jsonObject.getString("order_approval_status"));
        }

        catch (Exception e)
        {
            e.printStackTrace();
        }
//        holder.tvOrderId.setText("Order #"+finalOrderHistoryModel.get(postion).getOrderid());
//        holder.tvOrderTotal.setText("₹ "+finalOrderHistoryModel.get(postion).getOrdertotal());
//        holder.tvOrderDate.setText(finalOrderHistoryModel.get(postion).getOrderdate());
//        holder.tvexpectedtime.setText(finalOrderHistoryModel.get(postion).getExpectedtime());
//
//        holder.tvViewDetails.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (GeneralCode.isConnectingToInternet(context)) {
//                    Intent intent = new Intent(context, OrderHistoryDetails.class);
//                    intent.putExtra("order_id",finalOrderHistoryModel.get(postion).getOrderid());
//                    context.startActivity(intent);
//                } else {
//                    Toast.makeText(context, "No internet Connection", Toast.LENGTH_SHORT).show();
//                }
//
//            }
//        });
//
//        holder.mOrderTracking.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (GeneralCode.isConnectingToInternet(context)) {
//                    Intent intent = new Intent(context, OrderTrackingActivity.class);
//                    intent.putExtra("order_id",finalOrderHistoryModel.get(postion).getOrderid());
//                    context.startActivity(intent);
//                } else {
//                    Toast.makeText(context, "No internet Connection", Toast.LENGTH_SHORT).show();
//                }
//
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return jsonArray.length();
    }

    public class OrderHolder extends RecyclerView.ViewHolder {
        TextView tvOrderId, tvOrderDate, tvOrderTotal,tvViewDetails, tvexpectedtime;
        TextView mOrderTracking;

        public OrderHolder(@NonNull View itemView) {
            super(itemView);
            tvOrderId = itemView.findViewById(R.id.orderid);
          //  tvOrderDate = itemView.findViewById(R.id.orderid);
            tvOrderTotal = itemView.findViewById(R.id.amount);
            tvViewDetails = itemView.findViewById(R.id.status);
//            tvexpectedtime = itemView.findViewById(R.id.tvexpectedtime);
//
//            mOrderTracking = itemView.findViewById(R.id.tvOrderTracking);
        }
    }
}
