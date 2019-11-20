package com.indiansmarthub.ish.javaclass;

import android.content.Context;
import android.widget.Toast;


import com.indiansmarthub.ish.model.GeneralModel;
import com.indiansmarthub.ish.retrofit.NetworkClient;
import com.indiansmarthub.ish.retrofit.NetworkService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Add_Remove_WishList {

    public static final void getRemoveWishList(final Context context, String id, String cust_id) {
        /*final CustomLoadingDialog customLoadingDialog = new CustomLoadingDialog(DetailsActivity.this);
        customLoadingDialog.show();*/
        NetworkService networkService = NetworkClient.getClient().create(NetworkService.class);
        Call<GeneralModel> responseCall = networkService.removeWishList("NXqXAgFZA0jiWp5t6+=lGpgWJXEkbo", cust_id, id);

        responseCall.enqueue(new Callback<GeneralModel>() {
            @Override
            public void onResponse(Call<GeneralModel> call, Response<GeneralModel> response) {
                if (response.body() != null) {
                    if (response.body().getSuccess().equals("1")) {

                       // Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_LONG).show();
                    } else {
                      //  customLoadingDialog.dismiss();
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    }
                } else {
                  //  customLoadingDialog.dismiss();
                    Toast.makeText(context, "Something went wrong...!!", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<GeneralModel> call, Throwable t) {

              //  customLoadingDialog.dismiss();
                Toast.makeText(context, "Something went wrong...!!", Toast.LENGTH_SHORT).show();

            }
        });
    }


    public static final void getAddWishList(final Context context, String id, String sku, String cust_id) {
      /*  final CustomLoadingDialog customLoadingDialog = new CustomLoadingDialog(DetailsActivity.this);
        customLoadingDialog.show();*/
        NetworkService networkService = NetworkClient.getClient().create(NetworkService.class);
        Call<GeneralModel> responseCall = networkService.addWishList("NXqXAgFZA0jiWp5t6+=lGpgWJXEkbo", cust_id, id, sku);

        responseCall.enqueue(new Callback<GeneralModel>() {
            @Override
            public void onResponse(Call<GeneralModel> call, Response<GeneralModel> response) {
                if (response.body() != null) {
                    if (response.body().getSuccess().equals("1")) {
                     //   Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_LONG).show();
                    } else {

                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                 //   customLoadingDialog.dismiss();
                    Toast.makeText(context, "Something went wrong...!!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GeneralModel> call, Throwable t) {

             //   customLoadingDialog.dismiss();
                Toast.makeText(context, "Something went wrong...!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
