package com.aman.apps.aman.Adapters;

import android.app.Activity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.aman.apps.aman.R;
import com.aman.apps.aman.UploadProductData2;
import com.bumptech.glide.Glide;
import com.google.firebase.database.annotations.NotNull;

import java.util.ArrayList;

public class Myadapter_plant2 extends BaseAdapter implements Filterable {

    Activity activity;

    ArrayList<UploadProductData2> arrayList;
    ArrayList<UploadProductData2> filterList;
    CustomFilter filter;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v=activity.getLayoutInflater().inflate(R.layout.fragment_plant_list_item,null);

        TextView textView =v.findViewById(R.id.textView);
        TextView textView2 =v.findViewById(R.id.textView3);
        ImageView imageView =v.findViewById(R.id.imageView);
        TextView textView1=v.findViewById(R.id.textView2);

        UploadProductData2 data=arrayList.get(position);

        String name=data.getName_product();
        String price=data.getPrice_product();
        String url=data.getUrl1();
        textView.setText(name);

        Glide.with(activity).load(url).into(imageView);


        Integer p=Integer.parseInt(price);
        p=p-(p/10);

        String st=" ₹ "+price;

        textView1.setText(" ₹ "+p.toString());

        SpannableString spannableString=new SpannableString(st);

        StrikethroughSpan strikethroughSpan=new StrikethroughSpan();

        try
        {
            if(spannableString.length()==3)
            {
                spannableString.setSpan(strikethroughSpan,0,3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                textView2.setText(spannableString);

            }
            else

            if(spannableString.length()==4)
            {
                spannableString.setSpan(strikethroughSpan,0,4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                textView2.setText(spannableString);

            }
            else
            if(spannableString.length()==5)
            {
                spannableString.setSpan(strikethroughSpan,0,5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                textView2.setText(spannableString);

            }
            else
            if(spannableString.length()==6)
            {
                spannableString.setSpan(strikethroughSpan,0,6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                textView2.setText(spannableString);

            }
            else
            if(spannableString.length()==7)
            {
                spannableString.setSpan(strikethroughSpan,0,7, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                textView2.setText(spannableString);

            }
            else
            if(spannableString.length()==8)
            {
                spannableString.setSpan(strikethroughSpan,0,8, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                textView2.setText(spannableString);

            }
            else
            if(spannableString.length()==9)
            {
                spannableString.setSpan(strikethroughSpan,0,9, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                textView2.setText(spannableString);

            }
            else
            if(spannableString.length()==10)
            {
                spannableString.setSpan(strikethroughSpan,0,10, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                textView2.setText(spannableString);

            }


        }
        catch (Exception e)
        {

        }
        return v;
    }

    @Override
    public long getItemId(int position) {
        return arrayList.indexOf(getItem(position));
    }


    @NotNull
    @Override
    public Filter getFilter() {

        if(filter==null)
        {
            filter=new CustomFilter();
        }
        return filter;
    }

    public Myadapter_plant2(Activity activity, ArrayList<UploadProductData2> arrayList)
    {
        this.activity=activity;
        this.arrayList=arrayList;
        this.filterList=arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayList.get(i);
    }

    public ArrayList<UploadProductData2> getArrayList()
    {
        return arrayList;
    }

    class CustomFilter extends Filter
    {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            FilterResults results=new FilterResults();

            if(constraint!=null && constraint.length()>0)
            {
                constraint=constraint.toString().toUpperCase();

                ArrayList<UploadProductData2> filters=new ArrayList<UploadProductData2>();

                for(int i=0;i<filterList.size();i++)
                {
                    if(filterList.get(i).getName_product().toUpperCase().contains(constraint))
                    {
                        UploadProductData2 p=new UploadProductData2(filterList.get(i).getName_product(),
                                filterList.get(i).getUrl1(),filterList.get(i).getUrl2(),
                                filterList.get(i).getUrl3(),filterList.get(i).getUrl4(),
                                filterList.get(i).getPrice_product(),
                                filterList.get(i).getDescription(),filterList.get(i).getDelivery(),
                                filterList.get(i).getCare());
                        filters.add(p);
                    }
                }
                results.count=filters.size();
                results.values=filters;

            }else
            {
                results.count=filterList.size();
                results.values=filterList;
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

            arrayList= (ArrayList<UploadProductData2>) filterResults.values;
            notifyDataSetChanged();
        }
    }
}
