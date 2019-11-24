package com.indiansmarthub.ish.retrofit;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.Preference;

import com.indiansmarthub.ish.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkClient {

    private static final String BASE_URL = "http://52.66.136.244/api/v1/";
    public static SharedPreferences preference;

    private static Retrofit retrofit = null;


    public static NetworkService createApi(Context context) {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder client = new OkHttpClient.Builder()
                .readTimeout(100, TimeUnit.SECONDS)
                .connectTimeout(100, TimeUnit.SECONDS);
        preference=context.getSharedPreferences("ISH", context.MODE_PRIVATE);;



        client.addInterceptor(chain -> {
            Request.Builder builder = chain.request().newBuilder()
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Accept", "application/json");
            builder .addHeader("Authorization", preference.getString("cust_id", "") );


          /*  if (null != token) {
                builder.addHeader("Authorization", token);
            }*/
            return chain.proceed(builder.build());
        });


        if (BuildConfig.BUILD_TYPE.equals("debug")) {
            client.addInterceptor(interceptor);
        }

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(NetworkService.class);

    }
}
