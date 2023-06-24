package com.aman.apps.aman;


import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class RecyclerViewHolder2 extends RecyclerView.ViewHolder {
    // View holder for gridview recycler view as we used in listview
    public TextView productname;
    public TextView productprice;
    public ImageView imageview;
    //add anim to card view
    public CardView cardView;




    public RecyclerViewHolder2(View view) {
        super(view);
        // Find all views ids

        this.productname=view.findViewById(R.id.plant_item_1_name);
        this.productprice = (TextView) view
                .findViewById(R.id.plant_item_1_price);
        this.imageview = (ImageView) view
                .findViewById(R.id.plant_item_1_image);
        this.cardView= (CardView) view.findViewById(R.id.id);


    }

    public void clearAnimation()
    {
        cardView.clearAnimation();
    }


}