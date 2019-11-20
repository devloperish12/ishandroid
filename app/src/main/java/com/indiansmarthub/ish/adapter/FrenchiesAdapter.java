package com.indiansmarthub.ish.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.indiansmarthub.ish.R;
import com.indiansmarthub.ish.model.ModelTree;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class FrenchiesAdapter extends RecyclerView.Adapter<FrenchiesAdapter.MyViewHolder>{
    Context context;
    List<ModelTree> finaltreeModel;
    JSONArray jsonArray;
    String type;
    public FrenchiesAdapter(Context context, List<ModelTree> finaltreeModel, JSONArray jsonArray,String type) {
        this.context = context;
        this.finaltreeModel = finaltreeModel;
        this.jsonArray=jsonArray;
        this.type=type;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_tree_layout, parent, false);
        return new FrenchiesAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int postion) {
        try {
            JSONObject jsonObject=jsonArray.getJSONObject(postion);

            holder.name.setText(jsonObject.getString("name"));
            holder.type.setText(type);
if(type.equalsIgnoreCase("dp"))
{
    holder.type.setText("District Partner");

}
else {
    holder.type.setText("Channnel Partner");

}

        }catch (Exception e)
        {
            e.printStackTrace();
        }
//        if (postion == 0){
//            holder.itemView.setVisibility(View.GONE);
//        }
    }

    @Override
    public int getItemCount() {
        return jsonArray.length();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvLevel2,name,type;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            //tvLevel2 = itemView.findViewById(R.id.tvLevel2);
            name=itemView.findViewById(R.id.name);
            type=itemView.findViewById(R.id.type);

        }
    }
}
