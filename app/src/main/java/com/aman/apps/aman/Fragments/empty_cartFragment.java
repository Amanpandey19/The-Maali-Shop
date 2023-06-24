package com.aman.apps.aman.Fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aman.apps.aman.R;

import static android.content.Context.CONNECTIVITY_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 */
public class empty_cartFragment extends Fragment {

    TextView carthead;
    CardView exploreshop;
    SharedPreferences sharedPreferences;


    public empty_cartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.fragment_empty_cart, container, false);

        carthead=v.findViewById(R.id.emptycartwelcome);
        exploreshop=v.findViewById(R.id.explorshopnow);

        sharedPreferences=getActivity().getPreferences(Context.MODE_PRIVATE);

       final String username=sharedPreferences.getString("username","");
        carthead.setText(username+", Your Cart is Empty ");


        exploreshop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try
                {
                    ConnectivityManager manager= (ConnectivityManager) getActivity().getSystemService(CONNECTIVITY_SERVICE);

                    boolean is3g=manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
                    boolean iswifi=manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();

                    if(!is3g && !iswifi)
                    {
                        getFragmentManager().
                                beginTransaction().addToBackStack("").
                                replace(R.id.main_fragment_container,new No_Internet()).commit();

                    }else {
                        getFragmentManager().beginTransaction()
                                .replace(R.id.main_fragment_container, new HomeFragment(), "HomeFragment")
                                .commit();
                    }
                }
                catch (Exception e)
                {

                }

            }
        });

        return v;
    }

}
