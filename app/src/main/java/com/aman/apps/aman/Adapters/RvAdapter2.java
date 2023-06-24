package com.aman.apps.aman.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.aman.apps.aman.R;
import com.aman.apps.aman.RecyclerViewHolder2;
import com.aman.apps.aman.UploadProductData2;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class RvAdapter2 extends
        RecyclerView.Adapter<RecyclerViewHolder2> {// Recyclerview will extend to
    // recyclerview adapter
    private ArrayList<UploadProductData2> arrayList;
     private Context context;
     Myclick myclick;
    private int lastPosition=-1;

    public RvAdapter2(Myclick myclick,Context context,
                      ArrayList<UploadProductData2> arrayList) {

        this.myclick=myclick;
        this.context = context;
        this.arrayList = arrayList;

    }

    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size() : 0);

    }




    @Override
    public RecyclerViewHolder2 onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        // This method will inflate the custom layout and return as viewholder
        LayoutInflater mInflater = LayoutInflater.from(viewGroup.getContext());

        final ViewGroup mainGroup = (ViewGroup) mInflater.inflate(
                R.layout.card_item_home, viewGroup, false);
        final RecyclerViewHolder2 listHolder = new RecyclerViewHolder2(mainGroup);

        mainGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myclick.Onmyclcik(mainGroup,listHolder.getPosition());
            }
        });

        return listHolder;

    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder2 holder, int position) {
        final UploadProductData2 model = arrayList.get(position);

        RecyclerViewHolder2 mainHolder = (RecyclerViewHolder2) holder;// holder

        // bitmap

        // setting Username
        mainHolder.productname.setText(model.getName_product());
        mainHolder.productprice.setText("₹ "+model.getPrice_product());

        Glide.with(context).load(model.getUrl1()).into(mainHolder.imageview);
        //add anim to card view
        setAnimation(holder.cardView, position);



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
    public void onViewDetachedFromWindow(RecyclerViewHolder2 holder) {
        super.onViewDetachedFromWindow(holder);
        ((RecyclerViewHolder2)holder).clearAnimation();
    }


}