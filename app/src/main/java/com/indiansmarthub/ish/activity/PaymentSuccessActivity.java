package com.indiansmarthub.ish.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.indiansmarthub.ish.R;

public class PaymentSuccessActivity  extends AppCompatActivity {
    TextView tvText;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_success);
        tvText=findViewById(R.id.tvAbc);
        getBundle();
    }

    private void getBundle() {
        Bundle bundle=getIntent().getExtras();
        if (bundle!=null){
            String text=bundle.getString("order");
            tvText.setText("Your order number is:"+text);
        }
    }
}
