package com.aman.apps.aman.Adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;


public class Slider_Pager_Adapter2 extends PagerAdapter {
    Context context;
    private String[] image_urls;

    public Slider_Pager_Adapter2(Context context, String[] image_urls) {
        this.context = context;
        this.image_urls= image_urls;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        ImageView imageView=new ImageView(context);

        Picasso.with(context).load(image_urls[position]).into(imageView);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        container.addView(imageView);
        return imageView;
    }

    @Override
    public int getCount() {
        return image_urls.length;
    }


    @Override
    public boolean isViewFromObject(View view, Object obj) {
        return view == obj;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }
}