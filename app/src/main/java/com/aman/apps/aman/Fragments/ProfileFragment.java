package com.aman.apps.aman.Fragments;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.CONNECTIVITY_SERVICE;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.TextView;
import android.widget.Toast;

import com.alexzh.circleimageview.CircleImageView;
import com.aman.apps.aman.R;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


public class ProfileFragment extends Fragment implements View.OnClickListener {
    FirebaseAuth mAuth;
    SharedPreferences sharedPreferences;
    TextView tv1,tv2,tv3,tv4;
    TextView Selectbutton;
    CardView cardView;
    CircleImageView imageView;
    Uri filepath;
    CircleImageView imageView2;
    DatabaseReference dbreference;
    StorageReference firebaseStorage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_profile, container, false);
        getActivity().setTitle("Profile");

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

        tv1=rootview.findViewById(R.id.username_profile);
        tv2=rootview.findViewById(R.id.email_profile);
        tv4=rootview.findViewById(R.id.address_profile);
        tv3=rootview.findViewById(R.id.phone_no_profile);
        cardView=rootview.findViewById(R.id.explorshop);


        View v = inflater.inflate(R.layout.nav_layout, container, false);

        Selectbutton=rootview.findViewById(R.id.changeuserpicture);
        imageView=rootview.findViewById(R.id.userimage_profile);
        imageView2=v.findViewById(R.id.user_img);
        Selectbutton.setOnClickListener(this);

        sharedPreferences=getActivity().getPreferences(Context.MODE_PRIVATE);

        String emailID=sharedPreferences.getString("userID","");
        final String username=sharedPreferences.getString("username","");
        final String phone=sharedPreferences.getString("phone","");
        String address=sharedPreferences.getString("address","");


        dbreference= FirebaseDatabase.getInstance().getReference("UserImages").child(username+phone);
        firebaseStorage= FirebaseStorage.getInstance().getReference();

        if(emailID.equals(""))
        {
            getFragmentManager().beginTransaction().setCustomAnimations(R.anim.animate_slide_in_left,
                    R.anim.animate_slide_out_right)
                .replace(R.id.main_fragment_container, new LoginFragment(), "LoginFragment")
                .commit();

           /* View toastview=getLayoutInflater().inflate(R.layout.loginfirst,null );
            Toast toast=new Toast(getActivity().getApplicationContext());
            toast.setView(toastview);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.show();*/
        }
        else
            {

                final ProgressDialog pd=new ProgressDialog(getActivity());
                pd.setMessage("Loading Profile");
                pd.setCanceledOnTouchOutside(false);
                pd.show();

                dbreference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists())
                        {
                            String url= (String) dataSnapshot.getValue();
                            if(!url.equals("")) {
                                Glide.with(getActivity()).load(url).into(imageView);
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NotNull DatabaseError databaseError) {
                    }
                });


                tv2.setText("  "+emailID);
                tv1.setText("  "+username);
                tv3.setText("  "+phone);
                tv4.setText("  "+address);

                pd.dismiss();
            }
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_fragment_container, new HomeFragment(), "HomeFragment")
                        .commit();
            }
        });


        return rootview;
    }

   @Override
    public void onClick(View view) {

        if(view==Selectbutton)
        {
            selectpicture();
        }
    }

    public void selectpicture()
    {

        Intent I=new Intent();
        I.setType("image/*");
        I.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(I,101);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(resultCode==RESULT_OK && data!=null)
        {

            final ProgressDialog pd=new ProgressDialog(getActivity());
            pd.setMessage("Changing Profile Picture");
            pd.show();

            try{

                filepath=data.getData();
                imageView.setImageURI(filepath);
                imageView2.setImageURI(filepath);
                final StorageReference str = firebaseStorage.child
                        ("UserProfileImages/" + System.currentTimeMillis() + "." + getFileExt(filepath));

                str.putFile(filepath).addOnSuccessListener
                        (new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                Task<Uri> urltask = taskSnapshot.getStorage().getDownloadUrl();

                                while (!urltask.isSuccessful()) ;
                                Uri uri = urltask.getResult();

                                String url1 = uri.toString();

                                dbreference.setValue(url1);

                                Toast.makeText(getActivity(), "Profile Picture Uploaded", Toast.LENGTH_SHORT).show();

                                pd.dismiss();
                            }
                        }).addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NotNull Exception e) {
                                Toast.makeText(getActivity(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                pd.dismiss();
                            }
                        });
            }
            catch (Exception e)
            {
                Toast.makeText(getActivity(), "Error "+e, Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            Toast.makeText(getActivity(), "Try again", Toast.LENGTH_SHORT).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    public String getFileExt(Uri filepath)
    {
        ContentResolver cr=getContext().getContentResolver();
        MimeTypeMap map=MimeTypeMap.getSingleton();
        return map.getExtensionFromMimeType(cr.getType(filepath));
    }
}