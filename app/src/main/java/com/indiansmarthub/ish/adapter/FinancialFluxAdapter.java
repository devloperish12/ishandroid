package com.indiansmarthub.ish.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.indiansmarthub.ish.R;
import com.indiansmarthub.ish.activity.DetailsActivity;
import com.indiansmarthub.ish.activity.LoginActivity;
import com.indiansmarthub.ish.custom.GeneralCode;
import com.indiansmarthub.ish.javaclass.Add_Remove_WishList;
import com.indiansmarthub.ish.javaclass.BottomMenuHelper;
import com.indiansmarthub.ish.model.ModelNewArrival;
import com.indiansmarthub.ish.sqlite.DatabaseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import static com.indiansmarthub.ish.MainActivity.mBottomNavigationView;
import static com.indiansmarthub.ish.custom.GeneralCode.encodeToBase64;

public class FinancialFluxAdapter extends RecyclerView.Adapter<FinancialFluxAdapter.FinancialFluxHolder> {
        Context context;
        List<ModelNewArrival> finalFinancialFlux;
        private DatabaseHandler databaseHandler;
        private SharedPreferences prefManager;
JSONArray jsonArray=new JSONArray();
String url="";
        public FinancialFluxAdapter(Context context, JSONArray finalFinancialFlux,String url) {
                this.context = context;
                this.jsonArray = finalFinancialFlux;
                databaseHandler = new DatabaseHandler(context);
                this.url=url;
        }

        @NonNull
        @Override
        public FinancialFluxHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_product_list_layout, parent, false);
                return new FinancialFluxAdapter.FinancialFluxHolder(itemView);
        }


        @Override
        public void onBindViewHolder(@NonNull final FinancialFluxHolder holder, int i) {

                try
                {
                        JSONArray jsonArray1=new JSONArray();
int len=jsonArray1.length();
                      final  JSONObject jsonObject=jsonArray.getJSONObject(i);
                     final   String product_id=jsonObject.getString("id");
                        holder.tvProductName.setText(jsonObject.getString("name"));
                        holder.tvProductPrice.setText("₹ " +jsonObject.getString("price"));
try {
        jsonArray1 = jsonObject.getJSONArray("product_image");
    String url1=jsonArray1.getJSONObject(0).getString("url");
    String imgurl=url+"/"+url1;
    Picasso.with(context)
            .load(imgurl)
            .error(R.drawable.logo)
            .into(holder.ivProductImage);
    holder.ivProductImage.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, DetailsActivity.class);
            intent.putExtra("product_id",product_id );
            intent.putExtra("detail",jsonObject.toString() );
            context.startActivity(intent);
        }
    });
}catch (Exception e)
{
       // jsonArray1 = jsonObject.getJSONArray("product_imagefirst");
        JSONObject jsonObject1=jsonObject.getJSONObject("product_imagefirst");
    String url1=jsonObject1.getString("url");
    String imgurl=url+"/"+url1;
    Picasso.with(context)
            .load(imgurl)
            .error(R.drawable.logo)
            .into(holder.ivProductImage);
    holder.ivProductImage.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, DetailsActivity.class);
            intent.putExtra("product_id",product_id );
            intent.putExtra("detail",jsonObject.toString() );
            context.startActivity(intent);
        }
    });


}

                }
                catch (Exception e)
                {
                        e.printStackTrace();
                }






//                prefManager = context.getSharedPreferences("ISH", context.MODE_PRIVATE);
//                final ModelNewArrival modelfinalFinancialFlux = finalFinancialFlux.get(holder.getAdapterPosition());
//                holder.tvProductName.setText(modelfinalFinancialFlux.getName());
//                holder.tvProductPrice.setText("₹ " + modelfinalFinancialFlux.getPrice());
//                Picasso.with(context)
//                        .load(modelfinalFinancialFlux.getImg())
//                        .error(R.drawable.logo)
//                        .into(holder.ivProductImage);
//
//                int qty = databaseHandler.getQty(finalFinancialFlux.get(holder.getAdapterPosition()).getId(), finalFinancialFlux.get(holder.getAdapterPosition()).getSku());
//                if (qty == 0) {
//                        holder.tv_qty_addtocart_EduBasket.setText("1");
//                        holder.tv_addTocart_EduBasket.setVisibility(View.VISIBLE);
//                        holder.layoutAddTocartEduBasket.setVisibility(View.GONE);
//                } else {
//                        holder.i = qty;
//                        holder.tv_qty_addtocart_EduBasket.setText(qty + "");
//                        holder.tv_addTocart_EduBasket.setVisibility(View.GONE);
//                        holder.layoutAddTocartEduBasket.setVisibility(View.VISIBLE);
//                }
//                holder.tv_addTocart_EduBasket.setOnClickListener(new View.OnClickListener() {
//
//                        @Override
//
//                        public void onClick(View view) {
//                                if (databaseHandler.checkProduct(finalFinancialFlux.get(holder.getAdapterPosition()).getId(), finalFinancialFlux.get(holder.getAdapterPosition()).getSku())) {
//                                        long ans = databaseHandler.updateCart(finalFinancialFlux.get(holder.getAdapterPosition()).getId(), holder.tv_qty_addtocart_EduBasket.getText().toString(), finalFinancialFlux.get(holder.getAdapterPosition()).getSku(),finalFinancialFlux.get(holder.getAdapterPosition()).getPrice(), finalFinancialFlux.get(holder.getAdapterPosition()).getName());
//                                        if (ans != -1) {
//                                                getCount();
//                                        } else {
//                                                Toast.makeText(context, "Something went wrong,please try again...!!", Toast.LENGTH_SHORT).show();
//
//                                        }
//                                } else {
//                                        Bitmap bitmap = GeneralCode.drawableToBitmap(holder.ivProductImage.getDrawable());
//                                        String encoded = "";
//                                        if (bitmap != null) {
//                                                encoded = encodeToBase64(bitmap, Bitmap.CompressFormat.WEBP, 100);
//                                        }
//                                        long ans = databaseHandler.addToCart(finalFinancialFlux.get(holder.getAdapterPosition()).getId(), holder.tv_qty_addtocart_EduBasket.getText().toString(), finalFinancialFlux.get(holder.getAdapterPosition()).getSku(), finalFinancialFlux.get(holder.getAdapterPosition()).getPrice(), finalFinancialFlux.get(holder.getAdapterPosition()).getName(), encoded);
//                                        if (ans != -1) {
//                                                getCount();
//                                                holder.tv_addTocart_EduBasket.setVisibility(View.GONE);
//                                                holder.layoutAddTocartEduBasket.setVisibility(View.VISIBLE);
//                                        } else {
//                                                Toast.makeText(context, "Something went wrong,please try again...!!", Toast.LENGTH_SHORT).show();
//                                        }
//                                }
//                        }
//                });
//                holder.iv_plus_EduBasket.setOnClickListener(new View.OnClickListener() {
//
//                        @Override
//                        public void onClick(View view) {
//                                holder.i = holder.i + 1;
//                                holder.tv_qty_addtocart_EduBasket.setText(holder.i + "");
//
//                                if (databaseHandler.checkProduct(finalFinancialFlux.get(holder.getAdapterPosition()).getId(), finalFinancialFlux.get(holder.getAdapterPosition()).getSku())) {
//                                        long ans = databaseHandler.updateCart(finalFinancialFlux.get(holder.getAdapterPosition()).getId(), holder.tv_qty_addtocart_EduBasket.getText().toString(), finalFinancialFlux.get(holder.getAdapterPosition()).getSku(),finalFinancialFlux.get(holder.getAdapterPosition()).getPrice(), finalFinancialFlux.get(holder.getAdapterPosition()).getName());
//                                        if (ans != -1) {
//                                                getCount();
//                                        } else {
//                                                Toast.makeText(context, "Something went wrong,please try again...!!", Toast.LENGTH_SHORT).show();
//
//                                        }
//                                } else {
//                                        Bitmap bitmap = GeneralCode.drawableToBitmap(holder.ivProductImage.getDrawable());
//                                        String encoded = "";
//                                        if (bitmap != null) {
//                                                encoded = encodeToBase64(bitmap, Bitmap.CompressFormat.WEBP, 100);
//                                        }
//                                        long ans = databaseHandler.addToCart(finalFinancialFlux.get(holder.getAdapterPosition()).getId(), holder.tv_qty_addtocart_EduBasket.getText().toString(), finalFinancialFlux.get(holder.getAdapterPosition()).getSku(), finalFinancialFlux.get(holder.getAdapterPosition()).getPrice(), finalFinancialFlux.get(holder.getAdapterPosition()).getName(), encoded);
//                                        if (ans != -1) {
//                                                getCount();
//                                                holder.tv_addTocart_EduBasket.setVisibility(View.GONE);
//                                                holder.layoutAddTocartEduBasket.setVisibility(View.VISIBLE);
//                                        } else {
//                                                Toast.makeText(context, "Something went wrong,please try again...!!", Toast.LENGTH_SHORT).show();
//                                        }
//                                }
//                        }
//                });
//                holder.iv_minus_EduBasket.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                                if (holder.i > 1) {
//                                        holder.i = holder.i - 1;
//                                        holder.tv_qty_addtocart_EduBasket.setText(holder.i + "");
//
//                                        if (databaseHandler.checkProduct(finalFinancialFlux.get(holder.getAdapterPosition()).getId(), finalFinancialFlux.get(holder.getAdapterPosition()).getSku())) {
//                                                long ans = databaseHandler.updateCart(finalFinancialFlux.get(holder.getAdapterPosition()).getId(), holder.tv_qty_addtocart_EduBasket.getText().toString(), finalFinancialFlux.get(holder.getAdapterPosition()).getSku(),finalFinancialFlux.get(holder.getAdapterPosition()).getPrice(), finalFinancialFlux.get(holder.getAdapterPosition()).getName());
//                                                if (ans != -1) {
//                                                        getCount();
//                                                } else {
//                                                        Toast.makeText(context, "Something went wrong,please try again...!!", Toast.LENGTH_SHORT).show();
//
//                                                }
//                                        } else {
//                                                Bitmap bitmap = GeneralCode.drawableToBitmap(holder.ivProductImage.getDrawable());
//                                                String encoded = "";
//                                                if (bitmap != null) {
//                                                        encoded = encodeToBase64(bitmap, Bitmap.CompressFormat.WEBP, 100);
//                                                }
//                                                long ans = databaseHandler.addToCart(finalFinancialFlux.get(holder.getAdapterPosition()).getId(), holder.tv_qty_addtocart_EduBasket.getText().toString(), finalFinancialFlux.get(holder.getAdapterPosition()).getSku(), finalFinancialFlux.get(holder.getAdapterPosition()).getPrice(), finalFinancialFlux.get(holder.getAdapterPosition()).getName(), encoded);
//                                                if (ans != -1) {
//                                                        getCount();
//                                                        holder.tv_addTocart_EduBasket.setVisibility(View.GONE);
//                                                        holder.layoutAddTocartEduBasket.setVisibility(View.VISIBLE);
//                                                } else {
//                                                        Toast.makeText(context, "Something went wrong,please try again...!!", Toast.LENGTH_SHORT).show();
//                                                }
//                                        }
//                                } else {
//                                        databaseHandler.getProductIdFromAutoIDSimple(modelfinalFinancialFlux.getId());
//                                        holder.tv_addTocart_EduBasket.setVisibility(View.GONE);
//                                        holder.layoutAddTocartEduBasket.setVisibility(View.VISIBLE);
//                                        getCount();
//                                        //financialFluxAdapter.notifyDataSetChanged();
//                                }
//                        }
//                });
//                holder.ivProductImage.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                                Intent intent = new Intent(context, DetailsActivity.class);
//                                intent.putExtra("product_id", modelfinalFinancialFlux.getId());
//                                context.startActivity(intent);
//                        }
//                });
//                holder.tvProductName.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                                Intent intent = new Intent(context, DetailsActivity.class);
//                                intent.putExtra("product_id", modelfinalFinancialFlux.getId());
//                                context.startActivity(intent);
//                        }
//                });

        }
        @Override
        public int getItemCount() {
                return jsonArray.length();
        }

        public class FinancialFluxHolder extends RecyclerView.ViewHolder {
                ImageView ivProductImage, iv_minus_EduBasket, iv_plus_EduBasket;
                TextView tvProductName, tvProductPrice, tv_qty_addtocart_EduBasket, tv_addTocart_EduBasket;
                LinearLayout layoutAddTocartEduBasket;
                int i, wishlist_status;

                public FinancialFluxHolder(@NonNull View itemView) {
                        super(itemView);

                        ivProductImage = itemView.findViewById(R.id.ivProductImage);
                        iv_minus_EduBasket = itemView.findViewById(R.id.iv_minus_EduBasket);
                        iv_plus_EduBasket = itemView.findViewById(R.id.iv_plus_EduBasket);
                        tvProductName = itemView.findViewById(R.id.tvProductName);
                        tvProductPrice = itemView.findViewById(R.id.tvProductPrice);
                        tv_qty_addtocart_EduBasket = itemView.findViewById(R.id.tv_qty_addtocart_EduBasket);
                        tv_addTocart_EduBasket = itemView.findViewById(R.id.tv_addTocart_EduBasket);
                        layoutAddTocartEduBasket = itemView.findViewById(R.id.layoutAddTocartEduBasket);

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
