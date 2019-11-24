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

import com.indiansmarthub.ish.R;
import com.indiansmarthub.ish.activity.DetailsActivity;
import com.indiansmarthub.ish.activity.LoginActivity;
import com.indiansmarthub.ish.activity.ViewMoreProductActivity;
import com.indiansmarthub.ish.custom.GeneralCode;
import com.indiansmarthub.ish.javaclass.Add_Remove_WishList;
import com.indiansmarthub.ish.javaclass.BottomMenuHelper;
import com.indiansmarthub.ish.model.ModelFeaturedProduct;
import com.indiansmarthub.ish.sqlite.DatabaseHandler;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.indiansmarthub.ish.MainActivity.mBottomNavigationView;
import static com.indiansmarthub.ish.custom.GeneralCode.encodeToBase64;

public class ViewMoreProductListAdapter extends RecyclerView.Adapter<ViewMoreProductListAdapter.MyViewHolder> {
    Context context;
    List<ModelFeaturedProduct> finalProductModel;
    private DatabaseHandler databaseHandler;
    private SharedPreferences prefManager;
    public ViewMoreProductListAdapter(Context context, List<ModelFeaturedProduct> finalProductModel) {
        this.context = context;
        this.finalProductModel = finalProductModel;
        databaseHandler = new DatabaseHandler(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_product_list_mutiple_layout, parent, false);
        return new ViewMoreProductListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int postion) {
        prefManager = context.getSharedPreferences("ISH", context.MODE_PRIVATE);
        final ModelFeaturedProduct modelProductList = finalProductModel.get(holder.getAdapterPosition());
        int qty = databaseHandler.getQty(finalProductModel.get(holder.getAdapterPosition()).getId(), finalProductModel.get(holder.getAdapterPosition()).getSku());
        if (qty == 0) {
            holder.qty_addtocart.setText("1");
            holder.tv_addtocart.setVisibility(View.VISIBLE);
            holder.layoutAddTocartBest.setVisibility(View.GONE);
        } else {
            holder.i = qty;
            holder.qty_addtocart.setText(qty + "");
            holder.tv_addtocart.setVisibility(View.GONE);
            holder.layoutAddTocartBest.setVisibility(View.VISIBLE);
        }
        holder.tvProductListName.setText(modelProductList.getName());
        holder.tvProductListPrice.setText("₹ " + modelProductList.getPrice());
        Picasso.with(context)
                .load(modelProductList.getImg())
                .error(R.drawable.logo)
                .into(holder.iv_product_list_image);

        holder.tv_addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (databaseHandler.checkProduct(finalProductModel.get(holder.getAdapterPosition()).getId(), finalProductModel.get(holder.getAdapterPosition()).getSku())) {
                    long ans = databaseHandler.updateCart(finalProductModel.get(holder.getAdapterPosition()).getId(), holder.qty_addtocart.getText().toString(), finalProductModel.get(holder.getAdapterPosition()).getSku(),finalProductModel.get(holder.getAdapterPosition()).getPrice(), finalProductModel.get(holder.getAdapterPosition()).getName());
                    if (ans != -1) {
                        getCount();
                    } else {
                        Toast.makeText(context, "Something went wrong,please try again...!!", Toast.LENGTH_SHORT).show();

                    }
                } else {
                    Bitmap bitmap = GeneralCode.drawableToBitmap(holder.iv_product_list_image.getDrawable());
                    String encoded = "";
                    if (bitmap != null) {
                        encoded = encodeToBase64(bitmap, Bitmap.CompressFormat.WEBP, 100);
                    }
                    long ans = databaseHandler.addToCart(finalProductModel.get(holder.getAdapterPosition()).getId(), holder.qty_addtocart.getText().toString(), finalProductModel.get(holder.getAdapterPosition()).getSku(), finalProductModel.get(holder.getAdapterPosition()).getPrice(), finalProductModel.get(holder.getAdapterPosition()).getName(), encoded);
                    if (ans != -1) {
                        getCount();
                        holder.tv_addtocart.setVisibility(View.GONE);
                        holder.layoutAddTocartBest.setVisibility(View.VISIBLE);
                    } else {
                        Toast.makeText(context, "Something went wrong,please try again...!!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        holder.iv_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.i = holder.i + 1;
                holder.qty_addtocart.setText(holder.i + "");

                if (databaseHandler.checkProduct(finalProductModel.get(holder.getAdapterPosition()).getId(), finalProductModel.get(holder.getAdapterPosition()).getSku())) {
                    long ans = databaseHandler.updateCart(finalProductModel.get(holder.getAdapterPosition()).getId(), holder.qty_addtocart.getText().toString(), finalProductModel.get(holder.getAdapterPosition()).getSku(),finalProductModel.get(holder.getAdapterPosition()).getPrice(), finalProductModel.get(holder.getAdapterPosition()).getName());
                    if (ans != -1) {
                        getCount();
                    } else {
                        Toast.makeText(context, "Something went wrong,please try again...!!", Toast.LENGTH_SHORT).show();

                    }
                } else {
                    Bitmap bitmap = GeneralCode.drawableToBitmap(holder.iv_product_list_image.getDrawable());
                    String encoded = "";
                    if (bitmap != null) {
                        encoded = encodeToBase64(bitmap, Bitmap.CompressFormat.WEBP, 100);
                    }
                    long ans = databaseHandler.addToCart(finalProductModel.get(holder.getAdapterPosition()).getId(), holder.qty_addtocart.getText().toString(), finalProductModel.get(holder.getAdapterPosition()).getSku(), finalProductModel.get(holder.getAdapterPosition()).getPrice(), finalProductModel.get(holder.getAdapterPosition()).getName(), encoded);
                    if (ans != -1) {
                        getCount();
                        holder.tv_addtocart.setVisibility(View.GONE);
                        holder.layoutAddTocartBest.setVisibility(View.VISIBLE);
                    } else {
                        Toast.makeText(context, "Something went wrong,please try again...!!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        holder.iv_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.i > 1) {
                    holder.i = holder.i - 1;
                    holder.qty_addtocart.setText(holder.i + "");

                    if (databaseHandler.checkProduct(finalProductModel.get(holder.getAdapterPosition()).getId(), finalProductModel.get(holder.getAdapterPosition()).getSku())) {
                        long ans = databaseHandler.updateCart(finalProductModel.get(holder.getAdapterPosition()).getId(), holder.qty_addtocart.getText().toString(), finalProductModel.get(holder.getAdapterPosition()).getSku(),finalProductModel.get(holder.getAdapterPosition()).getPrice(), finalProductModel.get(holder.getAdapterPosition()).getName());
                        if (ans != -1) {
                            getCount();
                        } else {
                            Toast.makeText(context, "Something went wrong,please try again...!!", Toast.LENGTH_SHORT).show();

                        }
                    } else {
                        Bitmap bitmap = GeneralCode.drawableToBitmap(holder.iv_product_list_image.getDrawable());
                        String encoded = "";
                        if (bitmap != null) {
                            encoded = encodeToBase64(bitmap, Bitmap.CompressFormat.WEBP, 100);
                        }
                        long ans = databaseHandler.addToCart(finalProductModel.get(holder.getAdapterPosition()).getId(), holder.qty_addtocart.getText().toString(), finalProductModel.get(holder.getAdapterPosition()).getSku(), finalProductModel.get(holder.getAdapterPosition()).getPrice(), finalProductModel.get(holder.getAdapterPosition()).getName(), encoded);
                        if (ans != -1) {
                            getCount();
                            holder.tv_addtocart.setVisibility(View.GONE);
                            holder.layoutAddTocartBest.setVisibility(View.VISIBLE);
                        } else {
                            Toast.makeText(context, "Something went wrong,please try again...!!", Toast.LENGTH_SHORT).show();
                        }

                    }
                } else {
                    databaseHandler.getProductIdFromAutoIDSimple(modelProductList.getId());
                    holder.tv_addtocart.setVisibility(View.GONE);
                    holder.layoutAddTocartBest.setVisibility(View.VISIBLE);
                    getCount();
                    notifyDataSetChanged();
                }
            }
        });
        holder.iv_product_list_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("product_id", modelProductList.getId());
                context.startActivity(intent);
            }
        });
        if (prefManager.getString("cust_id", "").isEmpty()) {
            holder.ivWishListRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    context.startActivity(new Intent(context, LoginActivity.class));
                }
            });
        }
        holder.wishlist_status = Integer.parseInt(modelProductList.getWishlist());
        if (holder.wishlist_status == 0) {
            holder.ivWishListRemove.setVisibility(View.VISIBLE);
            holder.ivWishListAdd.setVisibility(View.GONE);
            holder.ivWishListRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
/*
                    Add_Remove_WishList.getAddWishList(context,modelProductList.getId(), modelProductList.getSku(), prefManager.getString("cust_id", ""));
*/
                    holder.ivWishListAdd.setVisibility(View.VISIBLE);
                    holder.ivWishListRemove.setVisibility(View.GONE);
                }
            });

        } else if (holder.wishlist_status == 1) {
            holder.ivWishListAdd.setVisibility(View.VISIBLE);
            holder.ivWishListRemove.setVisibility(View.GONE);

            holder.ivWishListAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
/*
                    Add_Remove_WishList.getRemoveWishList(context,modelProductList.getId(), prefManager.getString("cust_id", ""));
*/
                    holder.ivWishListRemove.setVisibility(View.VISIBLE);
                    holder.ivWishListAdd.setVisibility(View.GONE);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return finalProductModel.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_product_list_image, iv_minus, iv_plus,ivWishListRemove, ivWishListAdd;
        TextView tvProductListName, tvProductListPrice, qty_addtocart, tv_addtocart;
        LinearLayout layoutAddTocartBest;
        int i,wishlist_status;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            iv_product_list_image = itemView.findViewById(R.id.iv_product_list_image);
            iv_minus = itemView.findViewById(R.id.iv_minus);
            iv_plus = itemView.findViewById(R.id.iv_plus);
            tvProductListName = itemView.findViewById(R.id.tvProductListName);
            tvProductListPrice = itemView.findViewById(R.id.tvProductListPrice);
            tv_addtocart = itemView.findViewById(R.id.tv_addtocart);
            qty_addtocart = itemView.findViewById(R.id.qty_addtocart);
            layoutAddTocartBest = itemView.findViewById(R.id.layoutAddTocartBest);
            ivWishListRemove = itemView.findViewById(R.id.ivWishListRemove);
            ivWishListAdd = itemView.findViewById(R.id.ivWishListAdd);
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
