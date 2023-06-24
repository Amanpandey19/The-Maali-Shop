package com.aman.apps.aman;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aman.apps.aman.Fragments.No_Internet;

import static android.content.Context.CONNECTIVITY_SERVICE;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContactUs extends Fragment {

    TextView t1,t2,t3;

    public ContactUs() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_contact_us, container, false);

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

        t1=v.findViewById(R.id.mail_feedback);
        t2=v.findViewById(R.id.owner_call);

        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(Intent.ACTION_SEND);
                String recipientlist="themaalishop@gmail.com";
                String recipient[]=recipientlist.split(",");

                intent.putExtra(Intent.EXTRA_EMAIL,recipient);
                intent.putExtra(Intent.EXTRA_SUBJECT," Contacting The Maali Shop ");

                intent.setType("message/rfc822");
                startActivity(Intent.createChooser(intent,"Choose an application to proceed"));
            }
        });

        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    Activity#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for Activity#requestPermissions for more details.

                    ActivityCompat.requestPermissions
                            (getActivity(),new String[]{Manifest.permission.CALL_PHONE},0);
                    return ;
                }
                Intent I=new Intent(Intent.ACTION_DIAL);
                I.setData(Uri.parse("tel:8860730410"));
                startActivity(I);

            }
        });
        return v;
    }

}
