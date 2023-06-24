package com.aman.apps.aman.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.aman.apps.aman.Adapters.Myadapter;
import com.aman.apps.aman.BuyNowList;
import com.aman.apps.aman.CartUpload;
import com.aman.apps.aman.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

import java.util.ArrayList;

import static android.content.Context.CONNECTIVITY_SERVICE;


public class CartFragment extends Fragment {

    SharedPreferences sharedPreferences;
    ListView lv;
    TextView carthead,shipping,delivery,total,priceitem;
    DatabaseReference dbreference;
    ArrayList<CartUpload> arrayList;
    CardView removeitem;
    Button proceed;
    Myadapter myadapter;
    ArrayList<BuyNowList> buyNowLists;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_cart, container, false);

        final ProgressDialog pd=new ProgressDialog(getActivity());
        pd.setMessage("Please wait..");
        pd.show();

        ConnectivityManager manager= (ConnectivityManager) getActivity().getSystemService(CONNECTIVITY_SERVICE);

        boolean is3g=manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
        boolean iswifi=manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();

        if(!is3g && !iswifi)
        {
            getFragmentManager().
                    beginTransaction().addToBackStack("").
                    replace(R.id.main_fragment_container,new No_Internet()).commit();

        }
        getActivity().setTitle("Cart");

        removeitem=rootview.findViewById(R.id.removeitem);
        lv=rootview.findViewById(R.id.listview);

        proceed=rootview.findViewById(R.id.Proceed);
        priceitem=rootview.findViewById(R.id.priceitem);
        shipping=rootview.findViewById(R.id.shippingitem);
        delivery=rootview.findViewById(R.id.deliveryitem);
        total=rootview.findViewById(R.id.Totalitemprice);

        carthead=rootview.findViewById(R.id.cartwelcome);
        arrayList=new ArrayList<CartUpload>();
        buyNowLists=new ArrayList<>();

        sharedPreferences=getActivity().getPreferences(Context.MODE_PRIVATE);

        String emailID=sharedPreferences.getString("userID","");
        final String username=sharedPreferences.getString("username","");
        final String phone=sharedPreferences.getString("phone","");
        String address=sharedPreferences.getString("address","");


        if(username.equals(""))
        {
            getFragmentManager().beginTransaction().setCustomAnimations(R.anim.animate_slide_in_left,
                    R.anim.animate_slide_out_right)
                    .replace(R.id.main_fragment_container, new LoginFragment(), "LoginFragment")
                    .commit();
            View toastview=getLayoutInflater().inflate(R.layout.loginfirst,null );
            Toast toast=new Toast(getActivity().getApplicationContext());
            toast.setView(toastview);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.show();
        }


        dbreference= FirebaseDatabase.getInstance().getReference("CartDetails").child(username+phone);

        dbreference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {

                for(DataSnapshot data:dataSnapshot.getChildren())
                {
                    CartUpload uploadData=data.getValue(CartUpload.class);
                    arrayList.add(uploadData);
                }
                if(arrayList.size()==0)
                {
                    getFragmentManager().beginTransaction()
                            .replace(R.id.main_fragment_container, new empty_cartFragment(), "EmptyCartFragment")
                            .commit();
                }
                else if(getActivity()!=null)
                {

                    carthead.setText(username+", Welcome to your Cart ");
                    myadapter=new Myadapter(getActivity(),arrayList,username,phone);
                    lv.setAdapter(myadapter);
                    Integer integer =myadapter.getArrayList();
                    final int tt=integer.intValue()+30+50;
                    shipping.setText("₹ 30");
                    delivery.setText("₹ 50");
                    priceitem.setText("₹ "+myadapter.getArrayList().toString());
                    total.setText("₹ "+tt);
                    proceed.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            for(int i=0;i<arrayList.size();i++)
                            {
                                BuyNowList buylist=new BuyNowList();
                                buylist.setName_product(arrayList.get(i).getName_product());
                                buylist.setPrice_product(arrayList.get(i).getPrice_product());
                                buylist.setQty(arrayList.get(i).getQty());
                                buylist.setDelivery(arrayList.get(i).getDelivery());
                                buylist.setUrl1(arrayList.get(i).getUrl1());
                                buylist.setSize(arrayList.get(i).getSize());

                                buyNowLists.add(buylist);
                            }
                            Bundle b=new Bundle();
                            b.putInt("total",tt);
                            b.putParcelableArrayList("arraylist",buyNowLists);
                            BuyNow buyNow=new BuyNow();
                            buyNow.setArguments(b);

                            getFragmentManager().beginTransaction()
                                    .replace(R.id.main_fragment_container, buyNow).addToBackStack("")
                                    .commit();
                        }
                    });
                }



               }
            @Override
            public void onCancelled(@NotNull DatabaseError databaseError) {

            }
        });

        pd.dismiss();


        return rootview;
    }
}