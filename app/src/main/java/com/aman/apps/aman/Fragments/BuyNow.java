package com.aman.apps.aman.Fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.aman.apps.aman.Adapters.Adapter_For_Buylist;
import com.aman.apps.aman.BuyNowList;
import com.aman.apps.aman.R;

import java.util.ArrayList;

import static android.content.Context.CONNECTIVITY_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 */
public class BuyNow extends Fragment {

    SharedPreferences sharedPreferences;
    TextView final_name,final_address,final_phone;
    TextView final_amount;
    ListView lv;
    Button changedetails;
    Adapter_For_Buylist adapter;

    public BuyNow() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_buy_now, container, false);

        getActivity().setTitle("Items to Buy");

        Bundle b=getArguments();
        ArrayList<BuyNowList> buyNowLists=b.getParcelableArrayList("arraylist");
        int total =b.getInt("total");

        lv=v.findViewById(R.id.items_to_buy);
        final_name=v.findViewById(R.id.final_name);
        final_phone=v.findViewById(R.id.final_number);
        final_address=v.findViewById(R.id.final_address);
        final_amount=v.findViewById(R.id.final_amount_payble);
        changedetails=v.findViewById(R.id.changedeliverydetails);

        sharedPreferences=getActivity().getPreferences(Context.MODE_PRIVATE);

        String emailID=sharedPreferences.getString("userID","");
        final String username=sharedPreferences.getString("username","");
        final String phone=sharedPreferences.getString("phone","");
        String address=sharedPreferences.getString("address","");


        adapter=new Adapter_For_Buylist(getActivity(),buyNowLists);
        lv.setAdapter(adapter);
        final_name.setText(username);
        final_address.setText(address);
        final_phone.setText(phone);
        final_amount.setText(" ₹ "+total);

        changedetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{

                    ConnectivityManager manager= (ConnectivityManager) getActivity().getSystemService(CONNECTIVITY_SERVICE);

                    boolean is3g=manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
                    boolean iswifi=manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();

                    if(!is3g && !iswifi)
                    {
                        getFragmentManager().
                                beginTransaction().
                                replace(R.id.main_fragment_container,new No_Internet()).commit();

                    }else
                    {

                    }

                }
                catch (Exception e)
                {

                }
                getFragmentManager().beginTransaction()
                        .replace(R.id.main_fragment_container, new Change_Details())
                        .commit();
            }
        });
        return v;
    }

}
