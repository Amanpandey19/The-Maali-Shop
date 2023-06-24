package com.aman.apps.aman.Fragments;


import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.aman.apps.aman.ContactUs;
import com.aman.apps.aman.R;
import com.aman.apps.aman.UploadSlider;
import com.bumptech.glide.Glide;
import com.github.clans.fab.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

import static android.content.Context.CONNECTIVITY_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 */
public class HowItWorks extends Fragment {

    FloatingActionButton f1;
    DatabaseReference reference;
     ImageView im1,im2,im3,im4;


    public HowItWorks() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment

        View v=inflater.inflate(R.layout.how_it_works, container, false);
        getActivity().setTitle("How it Works");

        f1=v.findViewById(R.id.fabb_1);

        im1=v.findViewById(R.id.image1);
        im2=v.findViewById(R.id.image2);
        im3=v.findViewById(R.id.image3);
        im4=v.findViewById(R.id.image4);

        ConnectivityManager manager= (ConnectivityManager) getActivity().getSystemService(CONNECTIVITY_SERVICE);

        boolean is3g=manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
        boolean iswifi=manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();

        if(!is3g && !iswifi)
        {
            getFragmentManager().
                    beginTransaction().addToBackStack("").
                    replace(R.id.main_fragment_container,new No_Internet()).commit();

        }else
        {

        }


        reference= FirebaseDatabase.getInstance().getReference("how");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull  DataSnapshot dataSnapshot) {

                UploadSlider data=dataSnapshot.getValue(UploadSlider.class);

                Glide.with(getActivity()).load(data.getPoster_url()).into(im1);
                Glide.with(getActivity()).load(data.getPoster2_url()).into(im2);
                Glide.with(getActivity()).load(data.getPoster3_url()).into(im3);
                Glide.with(getActivity()).load(data.getPoster4_url()).into(im4);

            }

            @Override
            public void onCancelled(@NotNull DatabaseError databaseError) {

            }
        });

        f1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getFragmentManager().
                        beginTransaction().setCustomAnimations(R.anim.animate_swipe_left_enter, R.anim.animate_swipe_left_exit).addToBackStack("").
                        replace(R.id.main_fragment_container,new ContactUs()).commit();
            }
        });



        return v;
    }

}
