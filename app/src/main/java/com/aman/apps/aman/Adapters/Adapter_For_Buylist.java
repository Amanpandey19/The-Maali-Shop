package com.aman.apps.aman.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.aman.apps.aman.BuyNowList;
import com.aman.apps.aman.R;
import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.annotations.NotNull;

import java.util.ArrayList;

public class Adapter_For_Buylist extends ArrayAdapter<String> {
    Activity activity;
    TextView Q;
    DatabaseReference dbreference;
    ArrayList<BuyNowList> arrayList;
    public Adapter_For_Buylist(Activity activity, ArrayList<BuyNowList> arrayList)
    {
        super(activity, R.layout.customlayout);
        this.activity=activity;
        this.arrayList=arrayList;

    }

    public Adapter_For_Buylist(@NotNull Context context, int resource) {
        super(context, resource);
    }


    @Override
    public int getCount() {
        return arrayList.size();
    }

    @NotNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater= (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v=inflater.inflate(R.layout.custom_buy,null);
        TextView textView=v.findViewById(R.id.final_namecart);
        ImageView imageView=v.findViewById(R.id.final_imageviewcart);
        final TextView textView1=v.findViewById(R.id.final_sizecart);
        TextView textView2=v.findViewById(R.id.final_pricecart);
        TextView textView4=v.findViewById(R.id.final_deliverycart);

        Q=v.findViewById(R.id.final_productqty);

        final BuyNowList cartdata =arrayList.get(position);

        String name=cartdata.getName_product();
        String url=cartdata.getUrl1();
        String Size=cartdata.getSize();
        Integer qty=cartdata.getQty();
        Integer price=Integer.parseInt(cartdata.getPrice_product());

        textView.setText(name);
        textView1.setText("Size: "+Size);
        textView2.setText("₹ "+price*qty);
        textView4.setText("Delivery: " +cartdata.getDelivery());
        Q.setText("Qty : "+cartdata.getQty());
        Glide.with(activity).load(url).into(imageView);

        return v;
    }
}
