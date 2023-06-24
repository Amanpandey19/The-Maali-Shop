package com.aman.apps.aman.Fragments;


import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aman.apps.aman.ContactUs;
import com.aman.apps.aman.R;
import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.github.clans.fab.FloatingActionButton;

import static android.content.Context.CONNECTIVITY_SERVICE;


/**
 * A simple {@link Fragment} subclass.
 */
public class PreviewFragment extends Fragment {

    Bundle bundle;
    PhotoView imageView;
    FloatingActionButton f1,f2;

    public PreviewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v=inflater.inflate(R.layout.fragment_preview, container, false);

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

        bundle=new Bundle();

        imageView=v.findViewById(R.id.image_preview);

        f1=v.findViewById(R.id.fab1);
        f2=v.findViewById(R.id.fab2);

        String url=getArguments().getString("key");

        Glide.with(getActivity()).load(url).into(imageView);

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
                        beginTransaction().
                        setCustomAnimations(R.anim.animate_swipe_right_enter, R.anim.animate_swipe_right_exit).
                        addToBackStack("").
                        replace(R.id.main_fragment_container,new HowItWorks()).commit();
            }
        });
        return v;
    }

}
