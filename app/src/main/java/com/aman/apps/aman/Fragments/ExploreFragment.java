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


public class ExploreFragment extends Fragment {
    ImageView imageView1,imageView2,imageView3,imageView4,
    imageView5,imageView6,imageView7,imageView8,imageView9;
    DatabaseReference homedecorreference,plantgiftingrrefrence,terracegradenreference,
                      verticalreference,corporatereference,wallgardenreference,
                      plantsatworkrefrence,airrefiningrefrence,balconyreference;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_explore, container, false);
        getActivity().setTitle("Explore");


        final ProgressDialog pd=new ProgressDialog(getActivity());
        pd.setMessage("Please wait..");
        pd.setCanceledOnTouchOutside(false);
        pd.show();


        ConnectivityManager manager= (ConnectivityManager) getActivity().getSystemService(CONNECTIVITY_SERVICE);

        boolean is3g=manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
        boolean iswifi=manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();

        if(!is3g && !iswifi)
        {
            getFragmentManager().
                    beginTransaction().addToBackStack("").
                    replace(R.id.main_fragment_container,new No_Internet()).commit();

            pd.dismiss();
        }else {

        }

        imageView1=rootview.findViewById(R.id.homedecor);
        imageView2=rootview.findViewById(R.id.plantgifting);
        imageView3=rootview.findViewById(R.id.terracegarden);
        imageView4=rootview.findViewById(R.id.corporategifting);
        imageView5=rootview.findViewById(R.id.wallgarden);
        imageView6=rootview.findViewById(R.id.plantsatwork);
        imageView7=rootview.findViewById(R.id.airrefining);
        imageView8=rootview.findViewById(R.id.balconygarden);
        imageView9=rootview.findViewById(R.id.verticalgarden);



        homedecorreference= FirebaseDatabase.getInstance().getReference("HomePagePoster1PosterPicture");

        homedecorreference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {

                UploadPosterData uploadPosterData;
                uploadPosterData=dataSnapshot.getValue(UploadPosterData.class);
                Glide.with(getActivity()).load(uploadPosterData.getPoster_url()).into(imageView1);
            }

            @Override
            public void onCancelled(@NotNull DatabaseError databaseError) {

            }
        });

        plantgiftingrrefrence=FirebaseDatabase.getInstance().getReference("HomePagePoster3PosterPicture");

        plantgiftingrrefrence.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {

                UploadPosterData uploadPosterData;


                uploadPosterData=dataSnapshot.getValue(UploadPosterData.class);


                Glide.with(getActivity()).load(uploadPosterData.getPoster_url()).into(imageView2);

            }

            @Override
            public void onCancelled(@NotNull DatabaseError databaseError) {

            }
        });

        terracegradenreference=FirebaseDatabase.getInstance().getReference("HomePagePoster2PosterPicture");

        terracegradenreference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {

                UploadPosterData uploadPosterData;


                uploadPosterData=dataSnapshot.
                        getValue(UploadPosterData.class);


                Glide.with(getActivity()).load(uploadPosterData.getPoster_url()).into(imageView3);

            }

            @Override
            public void onCancelled(@NotNull DatabaseError databaseError) {

            }
        });


        corporatereference= FirebaseDatabase.getInstance().getReference("HomePageCorporatePosterPicture");

        corporatereference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {

                UploadPosterData uploadPosterData;


                uploadPosterData=dataSnapshot.getValue(UploadPosterData.class);


                Glide.with(getActivity()).load(uploadPosterData.getPoster_url()).into(imageView4);

            }

            @Override
            public void onCancelled(@NotNull DatabaseError databaseError) {

            }
        });

        wallgardenreference= FirebaseDatabase.getInstance().getReference("HomePageWallGardenPosterPicture");

        wallgardenreference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {

                UploadPosterData uploadPosterData;


                uploadPosterData=dataSnapshot.getValue(UploadPosterData.class);


                Glide.with(getActivity()).load(uploadPosterData.getPoster_url()).into(imageView5);

            }

            @Override
            public void onCancelled(@NotNull DatabaseError databaseError) {

            }
        });

        plantsatworkrefrence= FirebaseDatabase.getInstance().getReference("HomePageAtWorkPosterPicture");

        plantsatworkrefrence.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {

                UploadPosterData uploadPosterData;


                uploadPosterData=dataSnapshot.getValue(UploadPosterData.class);


                Glide.with(getActivity()).load(uploadPosterData.getPoster_url()).into(imageView6);

            }

            @Override
            public void onCancelled(@NotNull DatabaseError databaseError) {

            }
        });

        verticalreference= FirebaseDatabase.getInstance().getReference("HomePageVerticalGardenPosterPicture");

        verticalreference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {

                UploadPosterData uploadPosterData;


                uploadPosterData=dataSnapshot.getValue(UploadPosterData.class);


                Glide.with(getActivity()).load(uploadPosterData.getPoster_url()).into(imageView9);

            }

            @Override
            public void onCancelled(@NotNull DatabaseError databaseError) {

            }
        });

        balconyreference= FirebaseDatabase.getInstance().getReference("HomePageBalconyGardenPosterPicture");

        balconyreference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {

                UploadPosterData uploadPosterData;


                uploadPosterData=dataSnapshot.getValue(UploadPosterData.class);


                Glide.with(getActivity()).load(uploadPosterData.getPoster_url()).into(imageView8);

            }

            @Override
            public void onCancelled(@NotNull DatabaseError databaseError) {

            }
        });

        airrefiningrefrence= FirebaseDatabase.getInstance().getReference("HomePageAirRefiningPosterPicture");

        airrefiningrefrence.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {

                UploadPosterData uploadPosterData;


                uploadPosterData=dataSnapshot.getValue(UploadPosterData.class);


                Glide.with(getActivity()).load(uploadPosterData.getPoster_url()).into(imageView7);

            }

            @Override
            public void onCancelled(@NotNull DatabaseError databaseError) {

            }
        });


        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainGalleryFragment mainGalleryFragment=new MainGalleryFragment();
                Bundle bundle=new Bundle();
                bundle.putString("ref","HomeDecorPictures");
                mainGalleryFragment.setArguments(bundle);

                getFragmentManager()
                        .beginTransaction().setCustomAnimations(R.anim.animate_card_enter, R.anim.animate_card_exit).addToBackStack("")
                        .replace(R.id.main_fragment_container,mainGalleryFragment).commit();
            }
        });

        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainGalleryFragment mainGalleryFragment=new MainGalleryFragment();
                Bundle bundle=new Bundle();
                bundle.putString("ref","PlantGiftingPictures");
                mainGalleryFragment.setArguments(bundle);

                getFragmentManager()
                        .beginTransaction().setCustomAnimations(R.anim.animate_card_enter, R.anim.animate_card_exit).addToBackStack("")
                        .replace(R.id.main_fragment_container,mainGalleryFragment).commit();
            }
        });

        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainGalleryFragment mainGalleryFragment=new MainGalleryFragment();
                Bundle bundle=new Bundle();
                bundle.putString("ref","TerraceGardenPictures");
                mainGalleryFragment.setArguments(bundle);

                getFragmentManager()
                        .beginTransaction().setCustomAnimations(R.anim.animate_card_enter, R.anim.animate_card_exit).addToBackStack("")
                        .replace(R.id.main_fragment_container,mainGalleryFragment).commit();
            }
        });

        imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainGalleryFragment mainGalleryFragment=new MainGalleryFragment();
                Bundle bundle=new Bundle();
                bundle.putString("ref","CorporateGiftingPictures");
                mainGalleryFragment.setArguments(bundle);

                getFragmentManager()
                        .beginTransaction().setCustomAnimations(R.anim.animate_card_enter, R.anim.animate_card_exit).addToBackStack("")
                        .replace(R.id.main_fragment_container,mainGalleryFragment).commit();
            }
        });

        imageView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainGalleryFragment mainGalleryFragment=new MainGalleryFragment();
                Bundle bundle=new Bundle();
                bundle.putString("ref","WallGardenPictures");
                mainGalleryFragment.setArguments(bundle);

                getFragmentManager()
                        .beginTransaction().setCustomAnimations(R.anim.animate_card_enter, R.anim.animate_card_exit).addToBackStack("")
                        .replace(R.id.main_fragment_container,mainGalleryFragment).commit();
            }
        });

        imageView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainGalleryFragment mainGalleryFragment=new MainGalleryFragment();
                Bundle bundle=new Bundle();
                bundle.putString("ref","AtWorkGardenPictures");
                mainGalleryFragment.setArguments(bundle);

                getFragmentManager()
                        .beginTransaction().setCustomAnimations(R.anim.animate_card_enter, R.anim.animate_card_exit).addToBackStack("")
                        .replace(R.id.main_fragment_container,mainGalleryFragment).commit();
            }
        });

        imageView7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager()
                        .beginTransaction().setCustomAnimations(R.anim.animate_card_enter, R.anim.animate_card_exit).addToBackStack("")
                        .replace(R.id.main_fragment_container,new AirPurifyingPlantsFragment()).commit();
            }
        });

        imageView8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainGalleryFragment mainGalleryFragment=new MainGalleryFragment();
                Bundle bundle=new Bundle();
                bundle.putString("ref","BalconyGardenPictures");
                mainGalleryFragment.setArguments(bundle);

                getFragmentManager()
                        .beginTransaction().setCustomAnimations(R.anim.animate_card_enter, R.anim.animate_card_exit).addToBackStack("")
                        .replace(R.id.main_fragment_container,mainGalleryFragment).commit();
            }
        });

        imageView9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainGalleryFragment mainGalleryFragment=new MainGalleryFragment();
                Bundle bundle=new Bundle();
                bundle.putString("ref","VerticalGardenPictures");
                mainGalleryFragment.setArguments(bundle);

                getFragmentManager()
                        .beginTransaction().setCustomAnimations(R.anim.animate_card_enter, R.anim.animate_card_exit).addToBackStack("")
                        .replace(R.id.main_fragment_container,mainGalleryFragment).commit();
            }
        });

        pd.dismiss();
        return rootview;
    }
}