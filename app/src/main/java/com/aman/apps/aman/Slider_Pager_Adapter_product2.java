package com.aman.apps.aman;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;


public class Slider_Pager_Adapter_product2 extends PagerAdapter {
    Context context;
    private String[] image_urls;
    private LayoutInflater layoutInflater;

    public Slider_Pager_Adapter_product2(Context context , String[] image_urls ) {
        this.context = context;
        this.image_urls= image_urls;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        PhotoView imageView=new PhotoView(context);

        Glide.with(context).load(image_urls[position]).fitCenter().into(imageView);

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