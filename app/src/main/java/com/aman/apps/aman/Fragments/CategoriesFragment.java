package com.aman.apps.aman.Fragments;

import android.app.ProgressDialog;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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


public class CategoriesFragment extends Fragment {

    ImageView seed,pot,ae,indoor,outdoor,semiindoor;
    DatabaseReference potreference,seedreference,indoorreference,
    outdoorreference,semiindoorrefrence,aerefrence;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_categroies, container, false);
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

        seed=rootview.findViewById(R.id.seed);
        pot=rootview.findViewById(R.id.pots);
        ae=rootview.findViewById(R.id.ae);
        indoor=rootview.findViewById(R.id.plant_indoor);
        outdoor=rootview.findViewById(R.id.plant_Outdoor);
        semiindoor=rootview.findViewById(R.id.plant_semiindoor);

        potreference = FirebaseDatabase.getInstance().getReference("PotsPosterPicture");

        potreference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data:dataSnapshot.getChildren())
                {
                    UploadPosterData uploadPosterData=data.getValue(UploadPosterData.class);
                    Glide.with(getActivity()).load(uploadPosterData.getPoster_url()).into(pot);
                }


            }

            @Override
            public void onCancelled(@NotNull DatabaseError databaseError) {

            }
        });

        indoorreference = FirebaseDatabase.getInstance().getReference("IndoorPlantPosterPicture");

        indoorreference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data:dataSnapshot.getChildren())
                {
                    UploadPosterData uploadPosterData=data.getValue(UploadPosterData.class);
                    Glide.with(getActivity()).load(uploadPosterData.getPoster_url()).into(indoor);
                }


            }

            @Override
            public void onCancelled(@NotNull DatabaseError databaseError) {

            }
        });

        seedreference = FirebaseDatabase.getInstance().getReference("SeedsPosterPicture");

        seedreference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data:dataSnapshot.getChildren())
                {
                    UploadPosterData uploadPosterData=data.getValue(UploadPosterData.class);
                    Glide.with(getActivity()).load(uploadPosterData.getPoster_url()).into(seed);
                }
            }

            @Override
            public void onCancelled(@NotNull DatabaseError databaseError) {

            }
        });

        aerefrence = FirebaseDatabase.getInstance().getReference("AePosterPicture");

        aerefrence.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data:dataSnapshot.getChildren())
                {
                    UploadPosterData uploadPosterData=data.getValue(UploadPosterData.class);
                    Glide.with(getActivity()).load(uploadPosterData.getPoster_url()).into(ae);
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
                    Glide.with(getActivity()).load(uploadPosterData.getPoster_url()).into(outdoor);
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
                    Glide.with(getActivity()).load(uploadPosterData.getPoster_url()).into(semiindoor);
                }
                pd.dismiss();
            }
            @Override
            public void onCancelled(@NotNull DatabaseError databaseError) {
            }
        });

        seed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().
                        beginTransaction().addToBackStack("").
                        replace(R.id.main_fragment_container,new SeedFragment()).commit();
            }
        });
        pot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().
                        beginTransaction().addToBackStack("").
                        replace(R.id.main_fragment_container,new PotsFragment()).commit();

            }
        });

        ae.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().
                        beginTransaction().addToBackStack("").
                        replace(R.id.main_fragment_container,new Equipment_Fragment()).commit();

            }
        });

        indoor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getFragmentManager().
                        beginTransaction().addToBackStack("").
                        replace(R.id.main_fragment_container,new IndoorFragment()).commit();

            }
        });

        outdoor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().
                        beginTransaction().addToBackStack("").
                        replace(R.id.main_fragment_container,new OutdoorFragment()).commit();
            }
        });

        semiindoor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().
                        beginTransaction().addToBackStack("").
                        replace(R.id.main_fragment_container,new SemiIndoorFragment()).commit();
            }
        });
        return rootview;
    }
}