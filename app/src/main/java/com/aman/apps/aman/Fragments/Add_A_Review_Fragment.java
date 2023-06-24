package com.aman.apps.aman.Fragments;


import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.aman.apps.aman.ItemModel;
import com.aman.apps.aman.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;

import static android.content.Context.CONNECTIVITY_SERVICE;


/**
 * A simple {@link Fragment} subclass.
 */
public class Add_A_Review_Fragment extends android.support.v4.app.Fragment {

    EditText e2;
    RatingBar r1;
    Button b1;
    String url;
    SharedPreferences sharedPreferences;
    DatabaseReference reference,dbreference;

    public Add_A_Review_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_add__a__review_, container, false);

       e2=v.findViewById(R.id.comment_Person);
        b1=v.findViewById(R.id.submit);
        r1=v.findViewById(R.id.ratingBarproduct);

        sharedPreferences=getActivity().getPreferences(Context.MODE_PRIVATE);

        final String emailID=sharedPreferences.getString("userID","");
        final String username=sharedPreferences.getString("username","");
        final String phone=sharedPreferences.getString("phone","");



        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(e2.getText().toString().equals(""))
                {
                    Toast.makeText(getActivity(), "Please Fill Both columns", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    final ProgressDialog pd=new ProgressDialog(getActivity());
                    pd.setMessage("Please wait..");
                    pd.show();
                    pd.setCanceledOnTouchOutside(false);
                    ConnectivityManager manager= (ConnectivityManager) getActivity().getSystemService(CONNECTIVITY_SERVICE);

                    boolean is3g=manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
                    boolean iswifi=manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();

                    if(!is3g && !iswifi)
                    {
                        getFragmentManager().
                                beginTransaction().addToBackStack("").
                                replace(R.id.main_fragment_container,new No_Internet()).commit();

                        pd.dismiss();
                    }else {


                        dbreference = FirebaseDatabase.getInstance().getReference("UserImages").child(username + phone);

                        dbreference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    url = (String) dataSnapshot.getValue();
                                }

                                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                                Date date = new Date();
                                String d = formatter.format(date);
                                ItemModel uploadData = new ItemModel();
                                uploadData.setName(username);
                                uploadData.setComment(e2.getText().toString());
                                uploadData.setRating(r1.getRating());
                                uploadData.setUserimage(url);
                                uploadData.setDate(d);
                                Bundle b = getArguments();
                                final String ref = b.getString("ref5_key");

                                reference = FirebaseDatabase.getInstance().getReference("Reviews").child(ref);
                                String childID = reference.push().getKey();

                                reference.child(childID).setValue(uploadData);
                                e2.setText("");

                                Toast.makeText(getActivity(), "Review Updated", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCancelled(@NotNull DatabaseError databaseError) {
                            }
                        });

                        pd.dismiss();
                    }
                }
            }
        });

        return v;
    }

}
