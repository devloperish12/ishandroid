package com.indiansmarthub.ish.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.indiansmarthub.ish.R;
import com.squareup.picasso.Picasso;

public class BlogsDetailActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    ImageView ivBlogImageDetails;
    TextView tvTitleDetails,tvDateDetails,tvContainDetails;
    String image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blogs_detail);

        image = getIntent().getStringExtra("image");

        init();
    }
    private void init() {
        ivBlogImageDetails = findViewById(R.id.ivBlogImageDetails);
        tvTitleDetails = findViewById(R.id.tvTitleDetails);
        tvDateDetails = findViewById(R.id.tvDateDetails);
        tvContainDetails = findViewById(R.id.tvContainDetails);

        tvTitleDetails.setText(getIntent().getStringExtra("title"));
        tvDateDetails.setText(getIntent().getStringExtra("date"));
        tvContainDetails.setText(getIntent().getStringExtra("contain"));
        Picasso.with(BlogsDetailActivity.this)
                .load(image)
                .error(R.drawable.logo)
                .into(ivBlogImageDetails);

    }

}
