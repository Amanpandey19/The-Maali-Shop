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
import android.widget.EditText;
import android.widget.Toast;

import com.aman.apps.aman.Models.User;
import com.aman.apps.aman.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

import static android.content.Context.CONNECTIVITY_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 */
public class Change_Details extends Fragment {

    EditText t3;
    CardView c1;
    SharedPreferences sharedPreferences;
    DatabaseReference reference,r1,r2,r3,r4;

    public Change_Details() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_change__details, container, false);

        getActivity().setTitle("Your Details");

        t3=v.findViewById(R.id.changeaddress);
        c1=v.findViewById(R.id.update_details);

        sharedPreferences=getActivity().getPreferences(Context.MODE_PRIVATE);

        final String emailID=sharedPreferences.getString("userID","");
        final String username=sharedPreferences.getString("username","");
        final String phone=sharedPreferences.getString("phone","");
        String address=sharedPreferences.getString("address","");

        t3.setText(address);

        c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(t3.getText().toString().equals(""))
                {
                    View toastview=getLayoutInflater().inflate(R.layout.addresscorrect,null );
                    Toast toast=new Toast(getActivity().getApplicationContext());
                    toast.setView(toastview);
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.show();
                }
                else
                {

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
                        pd.dismiss();

                    }

                    reference= FirebaseDatabase.getInstance().
                            getReference("user");

/*
                    r1=FirebaseDatabase.getInstance().getReference("UserImages").child(username+phone);
                    r3=FirebaseDatabase.getInstance().getReference("UserImages");

                    r2=FirebaseDatabase.getInstance().getReference("CartDetails").child(username+phone);
                    r4=FirebaseDatabase.getInstance().getReference("CartDetails").child(
                            t1.getText().toString()+t2.getText().toString()
                    );

*/
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NotNull DataSnapshot dataSnapshot) {

                            for(DataSnapshot data:dataSnapshot.getChildren()) {
                                User userdata = (User) data.getValue(User.class);
                                if (userdata.getEmail().equals(emailID)) {

                                    userdata.setAddress(t3.getText().toString());
// userimages or cart details me reference key update karni hai
                                    String key= data.getKey();
                                    reference.child(key).setValue(userdata);
                                }
                            }
                        }
                        @Override
                        public void onCancelled(@NotNull DatabaseError databaseError) {

                        }
                    });

                /*    r1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NotNull DataSnapshot dataSnapshot) {

                            String img = dataSnapshot.getValue(String.class);

                            r3.child(t1.getText().toString()+t2.getText().toString())
                                    .setValue(img);
                        }

                        @Override
                        public void onCancelled(@NotNull DatabaseError databaseError) {

                        }
                    });


                    r2.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NotNull DataSnapshot dataSnapshot) {

                            for(DataSnapshot data:dataSnapshot.getChildren())
                            {
                                CartUpload cartUpload=data.getValue(CartUpload.class);
                                String childID=r4.push().getKey();
                                r4.child(childID).setValue(cartUpload);
                            }
                        }

                        @Override
                        public void onCancelled(@NotNull DatabaseError databaseError) {

                        }
                    });
*/
                  /*  if(!(t1.getText().toString()+t2.getText().toString()).equals(username+phone))
                    {
                        r1.removeValue();
                        r2.removeValue();
                    }
                  */  SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                  /*  editor.putString("username",t1.getText().toString());
                    editor.putString("phone",t2.getText().toString());
                  */  editor.putString("address",t3.getText().toString());
                    editor.commit();

                    getFragmentManager().beginTransaction()
                            .replace(R.id.main_fragment_container, new CartFragment())
                            .commit();
                    pd.dismiss();

                    View toastview=getLayoutInflater().inflate(R.layout.detailsupdated,null );
                    Toast toast=new Toast(getActivity().getApplicationContext());
                    toast.setView(toastview);
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.show();

                }
            }
        });

        return v;
    }

}
