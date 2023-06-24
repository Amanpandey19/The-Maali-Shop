package com.aman.apps.aman.Adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.aman.apps.aman.R;
import com.aman.apps.aman.UploadPosterData;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ImageAdapter extends BaseAdapter {
    private ArrayList<UploadPosterData> arrayList;
    Activity activity;
    private Animation animation;

    public ImageAdapter(ArrayList<UploadPosterData> arrayList, Activity activity) {
        this.arrayList = arrayList;
        this.activity=activity;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v=activity.getLayoutInflater().inflate(R.layout.fragment_gallery_list_item,null);

        ImageView imageView =v.findViewById(R.id.imageView_gallery);

        UploadPosterData data=arrayList.get(i);

        String url=data.getPoster_url();

        Glide.with(activity).load(url).into(imageView);

        animation= AnimationUtils.loadAnimation(activity,R.anim.slide_down);
        v.setAnimation(animation);
        return v;
    }
}
