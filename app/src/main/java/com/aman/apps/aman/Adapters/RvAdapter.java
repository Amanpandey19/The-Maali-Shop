package com.aman.apps.aman.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.aman.apps.aman.ItemModel;
import com.aman.apps.aman.R;
import com.aman.apps.aman.RecyclerViewHolder;
import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class RvAdapter extends
        RecyclerView.Adapter<RecyclerViewHolder> {// Recyclerview will extend to
    // recyclerview adapter
    private ArrayList<ItemModel> arrayList;
    private Context context;
    private int lastPosition=-1;

    public RvAdapter(Context context,
                     ArrayList<ItemModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;

    }

    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size() : 0);

    }


    public String getreviewsum()
    {
        DecimalFormat decimalFormat=new DecimalFormat("0.0");
        float sum= (float) 27.5;
        for(int i=0;i<arrayList.size();i++)
        {
            sum=sum+arrayList.get(i).getRating();
        }
        return decimalFormat.format(sum/(arrayList.size()+6));
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        final ItemModel model = arrayList.get(position);

        RecyclerViewHolder mainHolder = (RecyclerViewHolder) holder;// holder

        // bitmap

        // setting Username
        mainHolder.User_name.setText(model.getName());
        mainHolder.namestart.setText(model.getName().substring(0,1));
        mainHolder.ratingBar.setRating(model.getRating());
        mainHolder.date.setText(model.getDate());
        mainHolder.Comment.setText(model.getComment());

        Glide.with(context).load(model.getUserimage()).into(mainHolder.imageview);
       //add anim to card view


        setAnimation(holder.cardView, position);



    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        // This method will inflate the custom layout and return as viewholder
        LayoutInflater mInflater = LayoutInflater.from(viewGroup.getContext());

        ViewGroup mainGroup = (ViewGroup) mInflater.inflate(
                R.layout.item, viewGroup, false);
        RecyclerViewHolder listHolder = new RecyclerViewHolder(mainGroup);
        return listHolder;

    }


    //
    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated

        if (position > lastPosition)
        {

            Animation animation = AnimationUtils.loadAnimation(context, (position > lastPosition) ? R.anim.slide_down : R.anim.slide_up);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }



    }
    //to solve the problem of fast scroll
    @Override
    public void onViewDetachedFromWindow(RecyclerViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        ((RecyclerViewHolder)holder).clearAnimation();
    }

}