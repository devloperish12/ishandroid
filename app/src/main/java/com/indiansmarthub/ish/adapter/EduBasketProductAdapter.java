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
import com.indiansmarthub.ish.model.ModelFeaturedProduct;
import com.indiansmarthub.ish.sqlite.DatabaseHandler;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.indiansmarthub.ish.MainActivity.mBottomNavigationView;
import static com.indiansmarthub.ish.custom.GeneralCode.encodeToBase64;

public class EduBasketProductAdapter extends RecyclerView.Adapter<EduBasketProductAdapter.EdubasketHolder> {
    Context context;
    List<ModelFeaturedProduct> finalProductModel;
    DatabaseHandler databaseHandler;
    SharedPreferences prefManager;

    public EduBasketProductAdapter(Context context, List<ModelFeaturedProduct> finalProductModel) {
        this.context = context;
        this.finalProductModel = finalProductModel;
    }

    @NonNull
    @Override
    public EdubasketHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_product_list_layout, parent, false);
        return new EduBasketProductAdapter.EdubasketHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final EdubasketHolder holder, int i) {
        prefManager = context.getSharedPreferences("ISH", context.MODE_PRIVATE);
        databaseHandler = new DatabaseHandler(context);
        final ModelFeaturedProduct modelProductList = finalProductModel.get(holder.getAdapterPosition());
        holder.tvProductName.setText(modelProductList.getName());
        holder.tvProductPrice.setText("₹ " + modelProductList.getPrice());

        try {
            Picasso.with(context)
                    .load(modelProductList.getImg())
                    .error(R.drawable.logo)
                    .into(holder.ivProductImage);
        }catch (Exception e){
            e.printStackTrace();
        }


        int qty = databaseHandler.getQty(finalProductModel.get(holder.getAdapterPosition()).getId(), finalProductModel.get(holder.getAdapterPosition()).getSku());
        if (qty == 0) {
            holder.tv_qty_addtocart_EduBasket.setText("1");
            holder.tv_addTocart_EduBasket.setVisibility(View.VISIBLE);
            holder.layoutAddTocartEduBasket.setVisibility(View.GONE);
        } else {
            holder.i = qty;

            holder.tv_qty_addtocart_EduBasket.setText(qty + "");
            holder.tv_addTocart_EduBasket.setVisibility(View.GONE);
            holder.layoutAddTocartEduBasket.setVisibility(View.VISIBLE);
        }
        holder.tv_addTocart_EduBasket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (databaseHandler.checkProduct(finalProductModel.get(holder.getAdapterPosition()).getId(), finalProductModel.get(holder.getAdapterPosition()).getSku())) {
                    long ans = databaseHandler.updateCart(finalProductModel.get(holder.getAdapterPosition()).getId(), holder.tv_qty_addtocart_EduBasket.getText().toString(), finalProductModel.get(holder.getAdapterPosition()).getSku(),finalProductModel.get(holder.getAdapterPosition()).getPrice(), finalProductModel.get(holder.getAdapterPosition()).getName());
                    if (ans != -1) {
                        getCount();
                    } else {
                        Toast.makeText(context, "Something went wrong,please try again...!!", Toast.LENGTH_SHORT).show();

                    }
                } else {
                    Bitmap bitmap = GeneralCode.drawableToBitmap(holder.ivProductImage.getDrawable());
                    String encoded = "";
                    if (bitmap != null) {
                        encoded = encodeToBase64(bitmap, Bitmap.CompressFormat.WEBP, 100);
                    }
                    long ans = databaseHandler.addToCart(finalProductModel.get(holder.getAdapterPosition()).getId(), holder.tv_qty_addtocart_EduBasket.getText().toString(), finalProductModel.get(holder.getAdapterPosition()).getSku(), finalProductModel.get(holder.getAdapterPosition()).getPrice(), finalProductModel.get(holder.getAdapterPosition()).getName(), encoded);
                    if (ans != -1) {
                        getCount();
                        holder.tv_addTocart_EduBasket.setVisibility(View.GONE);
                        holder.layoutAddTocartEduBasket.setVisibility(View.VISIBLE);
                    } else {
                        Toast.makeText(context, "Something went wrong,please try again...!!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        holder.iv_plus_EduBasket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.i = holder.i + 1;
                holder.tv_qty_addtocart_EduBasket.setText(holder.i + "");

                if (databaseHandler.checkProduct(finalProductModel.get(holder.getAdapterPosition()).getId(), finalProductModel.get(holder.getAdapterPosition()).getSku())) {
                    long ans = databaseHandler.updateCart(finalProductModel.get(holder.getAdapterPosition()).getId(), holder.tv_qty_addtocart_EduBasket.getText().toString(), finalProductModel.get(holder.getAdapterPosition()).getSku(),finalProductModel.get(holder.getAdapterPosition()).getSku(), finalProductModel.get(holder.getAdapterPosition()).getName());
                    if (ans != -1) {
                        getCount();
                    } else {
                        Toast.makeText(context, "Something went wrong,please try again...!!", Toast.LENGTH_SHORT).show();

                    }
                } else {
                    Bitmap bitmap = GeneralCode.drawableToBitmap(holder.ivProductImage.getDrawable());
                    String encoded = "";
                    if (bitmap != null) {
                        encoded = encodeToBase64(bitmap, Bitmap.CompressFormat.WEBP, 100);
                    }
                    long ans = databaseHandler.addToCart(finalProductModel.get(holder.getAdapterPosition()).getId(), holder.tv_qty_addtocart_EduBasket.getText().toString(), finalProductModel.get(holder.getAdapterPosition()).getSku(), finalProductModel.get(holder.getAdapterPosition()).getPrice(), finalProductModel.get(holder.getAdapterPosition()).getName(), encoded);
                    if (ans != -1) {
                        getCount();
                        holder.tv_addTocart_EduBasket.setVisibility(View.GONE);
                        holder.layoutAddTocartEduBasket.setVisibility(View.VISIBLE);
                    } else {
                        Toast.makeText(context, "Something went wrong,please try again...!!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        holder.iv_minus_EduBasket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.i > 1) {
                    holder.i = holder.i - 1;
                    holder.tv_qty_addtocart_EduBasket.setText(holder.i + "");

                    if (databaseHandler.checkProduct(finalProductModel.get(holder.getAdapterPosition()).getId(), finalProductModel.get(holder.getAdapterPosition()).getSku())) {
                        long ans = databaseHandler.updateCart(finalProductModel.get(holder.getAdapterPosition()).getId(), holder.tv_qty_addtocart_EduBasket.getText().toString(), finalProductModel.get(holder.getAdapterPosition()).getSku(),finalProductModel.get(holder.getAdapterPosition()).getSku(), finalProductModel.get(holder.getAdapterPosition()).getName());
                        if (ans != -1) {
                            getCount();
                        } else {
                            Toast.makeText(context, "Something went wrong,please try again...!!", Toast.LENGTH_SHORT).show();

                        }
                    } else {
                        Bitmap bitmap = GeneralCode.drawableToBitmap(holder.ivProductImage.getDrawable());
                        String encoded = "";
                        if (bitmap != null) {
                            encoded = encodeToBase64(bitmap, Bitmap.CompressFormat.WEBP, 100);
                        }
                        long ans = databaseHandler.addToCart(finalProductModel.get(holder.getAdapterPosition()).getId(), holder.tv_qty_addtocart_EduBasket.getText().toString(), finalProductModel.get(holder.getAdapterPosition()).getSku(), finalProductModel.get(holder.getAdapterPosition()).getPrice(), finalProductModel.get(holder.getAdapterPosition()).getName(), encoded);
                        if (ans != -1) {
                            getCount();
                            holder.tv_addTocart_EduBasket.setVisibility(View.GONE);
                            holder.layoutAddTocartEduBasket.setVisibility(View.VISIBLE);
                        } else {
                            Toast.makeText(context, "Something went wrong,please try again...!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    databaseHandler.getProductIdFromAutoIDSimple(modelProductList.getId());
                    holder.tv_addTocart_EduBasket.setVisibility(View.GONE);
                    holder.layoutAddTocartEduBasket.setVisibility(View.VISIBLE);
                    getCount();
                    notifyDataSetChanged();
                }

            }
        });



        holder.ivProductImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("product_id", modelProductList.getId());
                context.startActivity(intent);
            }
        });
        holder.tvProductName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("product_id", modelProductList.getId());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        //  return finalProductModel.size();

            if (finalProductModel.size() <= 7) {
                return finalProductModel.size();
            } else {
                return 8;
            }

    }


    public class EdubasketHolder extends RecyclerView.ViewHolder {
        ImageView ivProductImage, iv_minus_EduBasket, iv_plus_EduBasket;
        TextView tvProductName, tvProductPrice, tv_qty_addtocart_EduBasket, tv_addTocart_EduBasket;
        LinearLayout layoutAddTocartEduBasket;
        int i, wishlist_status;

        public EdubasketHolder(@NonNull View itemView) {
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
            //   cartCount.setVisibility(View.VISIBLE);
            BottomMenuHelper.showBadge(context, mBottomNavigationView, R.id.navigation_cart, count + "");
          /*  cartCount.setText(count + "");
            cartCount.setSolidColor("#E70F18");
            cartCount.setStrokeColor("#E70F18");*/
        } else {
            // cartCount.setVisibility(View.INVISIBLE);
        }
    }

}
