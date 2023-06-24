package com.aman.apps.aman.Adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.aman.apps.aman.R;
import com.aman.apps.aman.UploadProductData2;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class Myadapter_plant extends ArrayAdapter<String>  {

    Activity activity;

    ArrayList<UploadProductData2> arrayList;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v=activity.getLayoutInflater().inflate(R.layout.fragment_plant_list_item,null);

        TextView textView =v.findViewById(R.id.textView);
        ImageView imageView =v.findViewById(R.id.imageView);
        TextView textView1=v.findViewById(R.id.textView2);

        UploadProductData2 data=arrayList.get(position);

        String name=data.getName_product();
        String price=data.getPrice_product();
        String url=data.getUrl1();
        textView.setText(name);

        Glide.with(activity).load(url).into(imageView);

        textView1.setText("₹ "+price);
        return v;
    }

    public Myadapter_plant(Activity activity, ArrayList<UploadProductData2> arrayList)
    {
        super(activity,R.layout.fragment_plant_list_item);
        this.activity=activity;
        this.arrayList=arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

}
