package com.indiansmarthub.ish.adapter;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.indiansmarthub.ish.R;
import com.indiansmarthub.ish.model.BannerOffer;
import com.indiansmarthub.ish.model.ModelToday;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SlidingImageAdapter1 extends PagerAdapter {

    private ArrayList<BannerOffer> imageModelArrayList;
    private LayoutInflater inflater;
    private Context context;
ArrayList<String> arrayList=new ArrayList<>();

    public SlidingImageAdapter1(Context context, ArrayList<BannerOffer> imageModelArrayList,ArrayList<String> arrayList) {
        this.context = context;
        this.imageModelArrayList = imageModelArrayList;
        inflater = LayoutInflater.from(context);
        this.arrayList=arrayList;

    }



    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        String path=arrayList.get(position);
        View imageLayout = inflater.inflate(R.layout.raw_today_deal_slider_layout, view, false);
        ImageView imageView = imageLayout.findViewById(R.id.ivtodaySlider1);
        TextView textView = imageLayout.findViewById(R.id.tvCatogory);
      //  textView.setText(imageModelArrayList.get(position).getName());
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        Picasso.with(context)
                .load(path)
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
