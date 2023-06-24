package com.aman.apps.aman.Fragments;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.aman.apps.aman.R;

import static android.content.Context.CONNECTIVITY_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 */
public class No_Internet extends Fragment {

    Button button;
    public No_Internet() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.slider_home_prodcut_layout, container, false);

        button =v.findViewById(R.id.angry_btn);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int countstack=getFragmentManager().getBackStackEntryCount();

                if(countstack==0)
                {

                    ConnectivityManager manager= (ConnectivityManager) getActivity().getSystemService(CONNECTIVITY_SERVICE);

                    boolean is3g=manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
                    boolean iswifi=manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();

                    if(!is3g && !iswifi)
                    {


                    }
                    else
                    {
                        Intent intent = new Intent(getActivity(), com.aman.apps.aman.SplashActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }


                }
                else
                {
                    try{
                        getFragmentManager().popBackStack();
                    }catch (Exception e)
                    {

                    }

                }

            }
        });

        return v;

    }
}
