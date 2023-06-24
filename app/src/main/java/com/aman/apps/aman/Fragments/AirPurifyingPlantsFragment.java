package com.aman.apps.aman.Fragments;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.RadioButton;
import android.widget.SearchView;

import com.aman.apps.aman.Adapters.Myadapter_plant2;
import com.aman.apps.aman.HomeFragment_Airpurifyingproduct;
import com.aman.apps.aman.R;
import com.aman.apps.aman.UploadProductData2;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static android.content.Context.CONNECTIVITY_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 */
public class AirPurifyingPlantsFragment extends Fragment {

    SearchView searchView;
    Myadapter_plant2 myadapter;

    Button b1,b2;
    RadioButton r1,r2;

    GridView gridView;
    DatabaseReference reference;
    ArrayList<UploadProductData2> arrayList;

    public AirPurifyingPlantsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View v=inflater.inflate(R.layout.fragment_airpurifying, container, false);
        getActivity().setTitle("Air Purifying Plants");
        gridView=v.findViewById(R.id.airgridview);
        searchView=v.findViewById(R.id.aman_editText);

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
        }else
        {

        }

        b1=v.findViewById(R.id.sort);
        arrayList=new ArrayList<UploadProductData2>();

        reference= FirebaseDatabase.getInstance().getReference("AirpurifyingPlantProductPictures");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {

                for(DataSnapshot data:dataSnapshot.getChildren())
                {
                    UploadProductData2 uploadData=data.getValue(UploadProductData2.class);
                    arrayList.add(uploadData);
                }

                myadapter=new Myadapter_plant2(getActivity(),arrayList);
                gridView.setAdapter(myadapter);
                pd.dismiss();
            }

            @Override
            public void onCancelled(@NotNull DatabaseError databaseError) {

            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog d=new Dialog(getActivity());
                d.setContentView(R.layout.sort_filter_custom_dialog);
                d.setCanceledOnTouchOutside(false);


                r1=d.findViewById(R.id.sort_low_to_high);
                r2=d.findViewById(R.id.sort_high_to_low);

                d.show();
                r1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        d.dismiss();
                        arrayList=myadapter.getArrayList();
                        Collections.sort(arrayList, new lowtohigh());
                        myadapter.notifyDataSetChanged();
                    }
                });

                r2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        d.dismiss();
                        arrayList=myadapter.getArrayList();
                        Collections.sort(arrayList, new hightolow());
                        myadapter.notifyDataSetChanged();
                    }
                });
            }
        });

        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setIconified(false);
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                myadapter.getFilter().filter(s);
                return false;
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                try {
                    ConnectivityManager manager= (ConnectivityManager) getActivity().getSystemService(CONNECTIVITY_SERVICE);

                    boolean is3g=manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
                    boolean iswifi=manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();

                    if(!is3g && !iswifi)
                    {
                        getFragmentManager().
                                beginTransaction().addToBackStack("").
                                replace(R.id.main_fragment_container,new No_Internet()).commit();

                    }
                }
                catch (Exception e)
                {

                }

                HomeFragment_Airpurifyingproduct homeFragment=new HomeFragment_Airpurifyingproduct();
                UploadProductData2 productData2= (UploadProductData2) myadapter.getItem(i);
                String name=productData2.getName_product();

                Bundle b=new Bundle();
                b.putString("name_key",name);
                homeFragment.setArguments(b);

                getFragmentManager().
                        beginTransaction().addToBackStack("").
                        replace(R.id.main_fragment_container,homeFragment).commit();
            }
        });


        return v;

    }
    private class lowtohigh implements Comparator<UploadProductData2>
    {
        @Override
        public int compare(UploadProductData2 o1, UploadProductData2 o2) {
            return Integer.parseInt(o1.getPrice_product())-Integer.parseInt(o2.getPrice_product());
        }
    }

    public class hightolow implements Comparator<UploadProductData2>
    {
        @Override
        public int compare(UploadProductData2 o1, UploadProductData2 o2) {
            return Integer.parseInt(o2.getPrice_product())-Integer.parseInt(o1.getPrice_product());
        }
    }

}
