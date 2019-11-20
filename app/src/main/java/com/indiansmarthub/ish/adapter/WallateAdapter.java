package com.indiansmarthub.ish.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.indiansmarthub.ish.R;
import com.indiansmarthub.ish.model.ModelRewardHistory;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class WallateAdapter extends RecyclerView.Adapter<WallateAdapter.WalletListHolder> {
    Context context;
    List<ModelRewardHistory> getwallateHistory;
    JSONArray jsonArray;
    public WallateAdapter(Context context, List<ModelRewardHistory> getwallateHistory,JSONArray jsonArray) {
        this.context = context;
        this.getwallateHistory = getwallateHistory;
        this.jsonArray=jsonArray;
    }

    @NonNull
    @Override
    public WalletListHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_wallet_history_layout, parent, false);
        return new WallateAdapter.WalletListHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull WalletListHolder holder, int postion) {
        try {
            String type="";

            JSONObject jsonObject=jsonArray.getJSONObject(postion);
            if(jsonObject.getString("type").equalsIgnoreCase("0"))
            {
                type="Debit";
            }
            else
            {
                type="Credit";
            }
            holder.pointCashHistory.setText(jsonObject.getString("balance"));
            holder.commentCashHistory.setText(type);
            holder.dateCashHistory.setText(jsonObject.getString("created_at"));
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return jsonArray.length();
    }

    public class WalletListHolder extends RecyclerView.ViewHolder {
        TextView pointCashHistory, commentCashHistory, dateCashHistory;

        public WalletListHolder(@NonNull View itemView) {
            super(itemView);
            pointCashHistory = itemView.findViewById(R.id.pointCashHistory);
            commentCashHistory = itemView.findViewById(R.id.commentCashHistory);
            dateCashHistory = itemView.findViewById(R.id.dateCashHistory);
        }
    }
}
