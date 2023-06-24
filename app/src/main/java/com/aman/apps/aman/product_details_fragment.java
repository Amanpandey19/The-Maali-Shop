package com.aman.apps.aman;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;


/**
 * A simple {@link Fragment} subclass.
 */
public class product_details_fragment extends Fragment {


    RadioButton r1,r2,r3;
    public product_details_fragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_details_product, container, false);

        r1=v.findViewById(R.id.radio);
        r2=v.findViewById(R.id.radio1);
        r3=v.findViewById(R.id.radio2);

        return v;}

}
