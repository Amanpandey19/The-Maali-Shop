package com.aman.apps.aman.Fragments;


import android.app.ProgressDialog;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.aman.apps.aman.R;
import com.aman.apps.aman.UploadPosterData;
import com.bumptech.glide.Glide;
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
public class category extends Fragment {

    ImageView imageView1,imageView2,imageView3;
    DatabaseReference outdoorreference,semiindoorrefrence,indoorreference;

    public category() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_category2, container, false);
        final ProgressDialog pd=new ProgressDialog(getActivity());
        pd.setMessage("Please wait..");
        pd.show();

        ConnectivityManager manager= (ConnectivityManager) getActivity().getSystemService(CONNECTIVITY_SERVICE);

        boolean is3g=manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
        boolean iswifi=manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();

        if(!is3g && !iswifi)
        {
            getFragmentManager().
                    beginTransaction().addToBackStack("").
                    replace(R.id.main_fragment_container,new No_Internet()).commit();

        }

        getActivity().setTitle("Categories");
        imageView1=v.findViewById(R.id.plant_indoor);
        imageView2=v.findViewById(R.id.plant_semiindoor);
        imageView3=v.findViewById(R.id.plant_Outdoor);


        indoorreference = FirebaseDatabase.getInstance().getReference("IndoorPlantPosterPicture");

        indoorreference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data:dataSnapshot.getChildren())
                {
                    UploadPosterData uploadPosterData=data.getValue(UploadPosterData.class);
                    Glide.with(getActivity()).load(uploadPosterData.getPoster_url()).into(imageView1);
                }


            }

            @Override
            public void onCancelled(@NotNull DatabaseError databaseError) {

            }
        });

        semiindoorrefrence = FirebaseDatabase.getInstance().getReference("Semi-IndoorPlantPosterPicture");

        semiindoorrefrence.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data:dataSnapshot.getChildren())
                {
                    UploadPosterData uploadPosterData=data.getValue(UploadPosterData.class);
                    Glide.with(getActivity()).load(uploadPosterData.getPoster_url()).into(imageView2);
                }
            }
            @Override
            public void onCancelled(@NotNull DatabaseError databaseError) {
            }
        });

        outdoorreference = FirebaseDatabase.getInstance().getReference("OutdoorPlantPosterPicture");

        outdoorreference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data:dataSnapshot.getChildren())
                {
                    UploadPosterData uploadPosterData=data.getValue(UploadPosterData.class);
                    Glide.with(getActivity()).load(uploadPosterData.getPoster_url()).into(imageView3);
                }


                pd.dismiss();
            }

            @Override
            public void onCancelled(@NotNull DatabaseError databaseError) {

            }
        });


        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm=getFragmentManager();
                FragmentTransaction ft=fm.beginTransaction();
                ft.addToBackStack("");
                ft.replace(R.id.main_fragment_container,new IndoorFragment());
                ft.commit();
            }
        });

        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentManager fm=getFragmentManager();
                FragmentTransaction ft=fm.beginTransaction();
                ft.addToBackStack("");
                ft.replace(R.id.main_fragment_container,new SemiIndoorFragment());
                ft.commit();

            }
        });

        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm=getFragmentManager();
                FragmentTransaction ft=fm.beginTransaction();
                ft.addToBackStack("");
                ft.replace(R.id.main_fragment_container,new OutdoorFragment());
                ft.commit();

            }
        });

        return v;
    }

}
