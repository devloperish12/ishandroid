/*
package com.indiansmarthub.ish.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.indiansmarthub.ish.R;
import com.indiansmarthub.ish.custom.GeneralCode;
import com.indiansmarthub.ish.model.ModelLocalCart;
import com.indiansmarthub.ish.sqlite.DatabaseHandler;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.jaredrummler.materialspinner.MaterialSpinnerAdapter;

import java.util.ArrayList;
import java.util.List;

public class CartAdapterLocal extends RecyclerView.Adapter<CartAdapterLocal.CartHolder> implements MaterialSpinner.OnItemSelectedListener{
    List<ModelLocalCart> viewCarts;
    Context context;
    DatabaseHandler databaseHandler;
    double cartAmount = 0;
    private String payment_cart;
    public static int pos_item = 0;
    private String qtyValue;
    SharedPreferences prefManager;
   public static ModelLocalCart viewCart;


    public CartAdapterLocal(Context context, ArrayList<ModelLocalCart> viewCarts) {
        this.context = context;
        this.viewCarts = viewCarts;

    }

    @NonNull
    @Override
    public CartHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_cart_layout, parent, false);
        return new CartAdapterLocal.CartHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final CartHolder holder, int i) {
        prefManager = context.getSharedPreferences("ISH", context.getApplicationContext().MODE_PRIVATE);
        pos_item = i;
        databaseHandler = new DatabaseHandler(context);
         viewCart = viewCarts.get(holder.getAdapterPosition());

        holder.tvProductCartListName.setText(viewCart.getName());
        final double priceQty = Double.parseDouble(viewCart.getPrice()) * Double.parseDouble(viewCart.getProductQty());
        holder.tvProductListCartPrice.setText("\u20b9 " + String.format("%.2f", priceQty));
        holder.qty_addtocart_bestProduct.setText("Cart Qty : "+viewCart.getProductQty());
        if (viewCart.getImage() != null) {
            Bitmap bitmap = GeneralCode.decodeBase64(viewCart.getImage());
            if (bitmap != null) {
                holder.iv_cart_product_image.setImageBitmap(bitmap);
            }
        }
        holder.i = Integer.parseInt(viewCart.getProductQty());


        holder.mlineatqty.setVisibility(View.VISIBLE);

        holder.materialSpinner_qty.setTag(i);

        spinnerCall(holder);


    }

    @SuppressLint("ResourceAsColor")
    private void spinnerCall(CartHolder holder) {

        try {
            ArrayList<String> qty = new ArrayList<>();
            qty.add("1");
            qty.add("2");
            qty.add("3");
            qty.add("4");
            qty.add("5");
            qty.add("6");
            qty.add("7");
            qty.add("8");
            qty.add("9");
            qty.add("10");

            //leave categry spinner
            MaterialSpinnerAdapter materialSpinnerAdapter = new MaterialSpinnerAdapter(context, qty);
            materialSpinnerAdapter.setTextColor(R.color.dark_gray);
            holder.materialSpinner_qty.setAdapter(materialSpinnerAdapter);
            holder.materialSpinner_qty.setOnItemSelectedListener(this);


        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {

        if (view.getId() == R.id.spinnerqty) {
            if (position>0) {
                if (position == 1) {
                    qtyValue = "2";
                } else if (position == 2) {
                    qtyValue = "3";
                } else if (position == 3) {
                    qtyValue = "4";
                } else if (position == 4) {
                    qtyValue = "5";
                } else if (position == 5) {
                    qtyValue = "6";
                } else if (position == 6) {
                    qtyValue = "7";
                } else if (position == 7) {
                    qtyValue = "8";
                } else if (position == 8) {
                    qtyValue = "9";
                } else if (position == 9) {
                    qtyValue = "10";
                }
                Log.e("tag_m", qtyValue);
                //    month_ = month.get(materialSpinner_month.getSelectedIndex());

                pos_item = (int) view.getTag();
                Log.e("tag_itemp", String.valueOf(pos_item));

                try {
                 //   generateArray(qtyValue);

                    addInDatabase(qtyValue);

                }catch (Exception e){
                    Log.e("tag_ee", String.valueOf(e));
                    e.printStackTrace();
                }

            }
        }

    }

    private void addInDatabase(String qtyValue) {
        double price = Double.parseDouble(viewCart.getPrice()) * Double.parseDouble(qtyValue);
    //    holder.tvProductListCartPrice.setText("\u20b9 " + String.format("%.2f", price));

        databaseHandler.updateCartConfigurable(viewCart.getProductId(), qtyValue, viewCart.getSku(), viewCart.getName());
        cartDataChange(qtyValue);
    }



    private void generateArray(String qtyValue) {
        Log.e("tag_qty", qtyValue);
        ArrayList<ModelLocalCart> localCarts = databaseHandler.getCart();
        String allinone = "";
        if (localCarts.size() > 0) {
            String datastring = "";
            for (int i = 0; i < localCarts.size(); i++) {
                String prodqty = "", prodid = "", configurable = "";
                allinone = "{\"custid\":\"" + prefManager.getString("cust_id", "") + "\",\"cartProduct\":[{";
                if (i != 0) {
                    datastring = datastring + ",{";
                }
                prodqty = prodqty + "\"prodqty\":" + "\"" + qtyValue + "\"";
                prodid = prodid + "\"prodid\":" + "\"" + localCarts.get(i).getProductId() + "\"";
                datastring = datastring + prodqty + "," + prodid;

                datastring = datastring + "}";
            }
            allinone = allinone + datastring + "]}";
            Log.d("tag_allinone", allinone);
        //    placeOrder(allinone);
        }
    }




    @Override
    public int getItemCount() {
        return viewCarts.size();
    }

    public class CartHolder extends RecyclerView.ViewHolder {
        ImageView iv_cart_product_image, minus_bestProduct, plus_bestProduct, delete_cart;
        TextView tvProductCartListName, tvProductListCartPrice, qty_addtocart_bestProduct;
        MaterialSpinner materialSpinner_qty;
        LinearLayout mlineatqty;
        int i = 1;

        public CartHolder(@NonNull View itemView) {
            super(itemView);
            iv_cart_product_image = itemView.findViewById(R.id.iv_cart_product_image);
            tvProductCartListName = itemView.findViewById(R.id.tvProductCartListName);
            tvProductListCartPrice = itemView.findViewById(R.id.tvProductListCartPrice);
            minus_bestProduct = itemView.findViewById(R.id.minus_bestProduct);
            plus_bestProduct = itemView.findViewById(R.id.plus_bestProduct);
            qty_addtocart_bestProduct = itemView.findViewById(R.id.tvCartQty);
            materialSpinner_qty = itemView.findViewById(R.id.spinnerqty);
            mlineatqty = itemView.findViewById(R.id.lineatqty);
            delete_cart = itemView.findViewById(R.id.delete_cart);
        }
    }

    private void cartDataChange(String qtyValue) {
        viewCarts = databaseHandler.getCart();
        double subTotal = 0;
        if (viewCarts.size() > 0) {

            for (int i = 0; i < viewCarts.size(); i++) {
                if (i == 0) {
                    cartAmount = Double.parseDouble(viewCarts.get(i).getPrice()) * Double.parseDouble(qtyValue);
                    subTotal = Double.parseDouble(viewCarts.get(i).getPrice()) * Double.parseDouble(qtyValue);
                } else {
                    double tempTotal = Double.parseDouble(viewCarts.get(i).getPrice()) * Double.parseDouble(qtyValue);
                    cartAmount = cartAmount + tempTotal;
                    subTotal = subTotal + tempTotal;

                    Log.e("tag_price", String.valueOf(subTotal));
                }
            }
           // tvSubTotal.setText("\u20b9 " + String.format("%.2f", subTotal));
                notifyDataSetChanged();


        } else {
         //   nocartFound.setVisibility(View.VISIBLE);
         //   viewcart_main_layout.setVisibility(View.GONE);
        }
    }



}
*/
