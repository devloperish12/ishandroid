package com.indiansmarthub.ish.firebase;

import android.util.Log;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
     //   super.onTokenRefresh();
        String refreshToken = FirebaseInstanceId.getInstance().getToken();

        Log.e("tag_newtoken", refreshToken);

    }
}
