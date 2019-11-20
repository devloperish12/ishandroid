package com.indiansmarthub.ish.custom;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;

import java.net.HttpURLConnection;
import java.net.URL;

public class InternetConnection {

    Context context;
    public boolean IsConnected(Context context) {
        try {
            this.context = context;
            ConnectivityManager connec = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            android.net.NetworkInfo wifi = connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            return wifi.isConnected() || mobile.isConnected();
        } catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }

    public class callGoogleApi extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... strings) {
            boolean success = false;
            try {
                URL url = new URL("https://www.google.com/");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setConnectTimeout(10000);
                connection.connect();
                success = connection.getResponseCode() == 200;
            }catch (Exception ex){
                //Crashlytics.logException(ex);
            }
            return success;
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onPostExecute(Boolean aVoid) {
        }
    }
}
