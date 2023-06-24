package com.aman.apps.aman.Fragments;


import android.app.ActivityOptions;
import android.app.Dialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;

import com.aman.apps.aman.Adapters.ImageAdapter;
import com.aman.apps.aman.ContactUs;
import com.aman.apps.aman.R;
import com.aman.apps.aman.UploadPosterData;
import com.bumptech.glide.Glide;
import com.github.clans.fab.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

import java.util.ArrayList;

import static android.content.Context.CONNECTIVITY_SERVICE;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainGalleryFragment extends android.support.v4.app.Fragment {

    ArrayList<UploadPosterData> arrayList;
    DatabaseReference reference;
    GridView gridView;
    ImageAdapter myadapter;
    FloatingActionButton f1,f2;
    ProgressDialog pd;

    public MainGalleryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_main_gallery, container, false);

        getActivity().setTitle("Explore");

        pd=new ProgressDialog(getActivity());

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

        f1=v.findViewById(R.id.fab_1);
         f2=v.findViewById(R.id.fab_2);

         gridView=v.findViewById(R.id.picturesgrid);

         arrayList=new ArrayList<UploadPosterData>();

        String ref=getArguments().getString("ref");

        reference= FirebaseDatabase.getInstance().getReference(ref);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {

                for(DataSnapshot data:dataSnapshot.getChildren())
                {
                    UploadPosterData uploadData=data.getValue(UploadPosterData.class);
                    arrayList.add(uploadData);
                }

                myadapter=new ImageAdapter(arrayList,getActivity());
                gridView.setAdapter(myadapter);
                pd.dismiss();

            }

            @Override
            public void onCancelled(@NotNull DatabaseError databaseError) {

            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String url=arrayList.get(i).getPoster_url();
                ShowDialogBox(url);

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

        f2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getFragmentManager().
                        beginTransaction().setCustomAnimations(R.anim.animate_swipe_right_enter, R.anim.animate_swipe_right_exit).addToBackStack("").
                        replace(R.id.main_fragment_container,new HowItWorks()).commit();
            }
        });

        return v;
    }

    public void ShowDialogBox(final String url)
    {
        final Dialog dialog=new Dialog(getActivity());
        dialog.setContentView(R.layout.gallerydialog);

        dialog.setCanceledOnTouchOutside(false);


        final ImageView im=dialog.findViewById(R.id.gallery_item);
        Button prev=dialog.findViewById(R.id.preview_item);
        Button closeprev=dialog.findViewById(R.id.close_dialog);

        Glide.with(getActivity()).load(url).into(im);
        im.setScaleType(ImageView.ScaleType.CENTER_CROP);

       dialog.show();

        prev.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {

                PreviewFragment previewFragment=new PreviewFragment();

                Bundle bundle=new Bundle();
                bundle.putString("key",url);

                previewFragment.setArguments(bundle);

                Pair[] pair=new Pair[1];
                pair[0]=new Pair(im ,"imagetransition");

                ActivityOptions activityOptions=ActivityOptions.makeSceneTransitionAnimation(getActivity(),pair);

                getFragmentManager().
                        beginTransaction().addToBackStack("").
                        replace(R.id.main_fragment_container,previewFragment).commit();
                dialog.dismiss();
            }
        });


        closeprev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();
            }
        });

    }
}
