package com.aman.apps.aman.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.aman.apps.aman.R;



public class ProductFragment extends Fragment {
    TextView text;
    Button item1,item2,item3;




    public ProductFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_product, container, false);
        item1=v.findViewById(R.id.no1);
        item2=v.findViewById(R.id.no2);
        item3=v.findViewById(R.id.no3);
        text=v.findViewById(R.id.des);



/*if (item1.isSelected())
{
    text.setText("hi");

}
else if (item2.isSelected())
{

    text.setText("Hi there");
}
else
{
    text.setText("lol");
}*/






        return v;
    }


}
