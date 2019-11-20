package com.indiansmarthub.ish.adapter;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.indiansmarthub.ish.R;
import com.indiansmarthub.ish.model.ModelSlider;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SlidingImageAdapter extends PagerAdapter {

    private ArrayList<ModelSlider> imageModelArrayList;
    private LayoutInflater inflater;
    private Context context;


    public SlidingImageAdapter(Context context, ArrayList<ModelSlider> imageModelArrayList) {
        this.context = context;
        this.imageModelArrayList = imageModelArrayList;
        try {
            inflater = LayoutInflater.from(context);
        }
        catch (Exception e){
            e.printStackTrace();
        }


    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return imageModelArrayList.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View imageLayout = inflater.inflate(R.layout.image_slider_layout, view, false);
        ImageView imageView = imageLayout.findViewById(R.id.iv_slider_image);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        Picasso.with(context)
                .load(imageModelArrayList.get(position).getImage())
                .error(R.drawable.logo)
                .into(imageView);

        view.addView(imageLayout);
        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }


}
