package com.aman.apps.aman;


import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;


public class RecyclerViewHolder extends RecyclerView.ViewHolder  {
    // View holder for gridview recycler view as we used in listview
    public TextView User_name;
    public TextView Comment;
    public TextView date;
    public TextView namestart;
    public ImageView imageview;
    public RatingBar ratingBar;
    //add anim to card view
    public CardView cardView;




    public RecyclerViewHolder(View view) {
        super(view);
        // Find all views ids

        this.Comment=view.findViewById(R.id.comment_by_person);
        this.date=view.findViewById(R.id.date_of_person);
        this.namestart=view.findViewById(R.id.name_start);
        this.User_name = (TextView) view
                .findViewById(R.id.Comment_title);
        this.ratingBar=view.findViewById(R.id.rate_comment);
        this.imageview = (ImageView) view
                .findViewById(R.id.comment_image);
        this.cardView= (CardView) view.findViewById(R.id.card_view_review);


    }

    public void clearAnimation()
    {
        cardView.clearAnimation();
    }

}