package com.indiansmarthub.ish.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.indiansmarthub.ish.R;
import com.indiansmarthub.ish.activity.DetailsActivity;
import com.indiansmarthub.ish.activity.LoginActivity;
import com.indiansmarthub.ish.custom.GeneralCode;
import com.indiansmarthub.ish.javaclass.Add_Remove_WishList;
import com.indiansmarthub.ish.javaclass.BottomMenuHelper;
import com.indiansmarthub.ish.model.ModelSearchProduct;
import com.indiansmarthub.ish.model.searchresponse.Product;
import com.indiansmarthub.ish.model.searchresponse.SearchResponseModel;
import com.indiansmarthub.ish.sqlite.DatabaseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.indiansmarthub.ish.MainActivity.mBottomNavigationView;
import static com.indiansmarthub.ish.custom.GeneralCode.encodeToBase64;


public class SearchProductAdapter extends RecyclerView.Adapter<SearchProductAdapter.SearchHolder> {
    Context context;
    List<Product> finalProductSearch;
    private DatabaseHandler databaseHandler;
    private SharedPreferences prefManager;
    JSONArray jsonArray;

    public SearchProductAdapter(Context context, JSONArray jsonArray){
        this.context = context;
        databaseHandler = new DatabaseHandler(context);
        this.jsonArray=jsonArray;
    }

    @NonNull
    @Override
    public SearchProductAdapter.SearchHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_product_list_mutiple_layout, parent, false);
        return new SearchProductAdapter.SearchHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final SearchProductAdapter.SearchHolder holder, int i) {
try {

JSONObject jsonObject=jsonArray.getJSONObject(i);
    prefManager = context.getSharedPreferences("ISH", context.MODE_PRIVATE);
//        notifyDataSetChanged();
   // final Product modelProductList = finalProductSearch.get(holder.getAdapterPosition());

    holder.tvProductListName.setText(jsonObject.getString("name"));
    holder.tvProductListPrice.setText("₹ " +jsonObject.getString("price"));
    try
    {
        int pr=Integer.parseInt(jsonObject.getString("price"));
        if(pr<1)
        {
            holder.tvProductListPrice.setText("");
        }
    }
    catch (Exception e)
    {
        e.printStackTrace();
    }
    Picasso.with(context)
            .load(jsonObject.getString("img"))
            .error(R.drawable.logo)
            .into(holder.iv_product_list_image);

    holder.iv_product_list_image.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            try {
                JSONArray arr=jsonObject.getJSONArray("product_image");
                JSONObject json=arr.getJSONObject(0);
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("product_id", jsonObject.getString("id"));
                intent.putExtra("detail", jsonObject.toString());

                context.startActivity(intent);
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    });
    holder.tvProductListName.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, DetailsActivity.class);
            //intent.putExtra("product_id", modelProductList.getId());
            context.startActivity(intent);
        }
    });
}catch (Exception e)
{
    e.printStackTrace();
}

    }

    @Override
    public int getItemCount() {
        return jsonArray.length();
    }

    public class SearchHolder extends RecyclerView.ViewHolder {
        ImageView iv_product_list_image, iv_minus, iv_plus,ivWishListRemovehome, ivWishListAddhome;
        TextView tvProductListName, tvProductListPrice, qty_addtocart, tv_addtocart;
        LinearLayout layoutAddTocartBest;
        int i, wishlist_status;
        public SearchHolder(@NonNull View itemView) {
            super(itemView);
            ivWishListRemovehome = itemView.findViewById(R.id.ivWishListRemove);
            ivWishListRemovehome.setVisibility(View.GONE);
            ivWishListAddhome = itemView.findViewById(R.id.ivWishListAdd);
            iv_product_list_image = itemView.findViewById(R.id.iv_product_list_image);
            iv_minus = itemView.findViewById(R.id.iv_minus);
            iv_plus = itemView.findViewById(R.id.iv_plus);
            tvProductListName = itemView.findViewById(R.id.tvProductListName);
            tvProductListPrice = itemView.findViewById(R.id.tvProductListPrice);
            tv_addtocart = itemView.findViewById(R.id.tv_addtocart);
            qty_addtocart = itemView.findViewById(R.id.qty_addtocart);
            layoutAddTocartBest = itemView.findViewById(R.id.layoutAddTocartBest);
        }
    }

    private void getCount() {
        int count = databaseHandler.cartCount();
        if (count > 0) {

            BottomMenuHelper.showBadge(context, mBottomNavigationView, R.id.navigation_cart, count + "");

        } else {

        }
    }

}
