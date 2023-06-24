package com.aman.apps.aman.Fragments;

import android.app.ProgressDialog;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aman.apps.aman.MainActivity;
import com.aman.apps.aman.Models.User;
import com.aman.apps.aman.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

import static android.content.Context.CONNECTIVITY_SERVICE;


public class LoginFragment extends Fragment {
    EditText email, password;
    FirebaseAuth mAuth;
    ImageButton btn;
    DatabaseReference reference;
    private RelativeLayout relativeLayout;
    private Animation animation;
    TextView logout,login;
    public String usernameprofile,phone,emailuser,address;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.other_login, container, false);
        getActivity().setTitle("Login");

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

        email = rootview.findViewById(R.id.user_emailid);
        password = rootview.findViewById(R.id.user_pass);
        logout=rootview.findViewById(R.id.menu_logout);
        login=rootview.findViewById(R.id.menu_username);
        btn=rootview.findViewById(R.id.no_account2);
        mAuth=FirebaseAuth.getInstance();


        SharedPreferences sharedPreferences=getActivity().getPreferences(Context.MODE_PRIVATE);

        final String username=sharedPreferences.getString("username","");

        if(!username.equals(""))
        {
             getFragmentManager().beginTransaction()
                .replace(R.id.main_fragment_container, new ProfileFragment(), "ProfileFragment")
                .commit();
        }

        rootview.findViewById(R.id.login_btn).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final String emailid=email.getText().toString();
                String passw=password.getText().toString();

                final ProgressDialog pd=new ProgressDialog(getActivity());
                pd.setMessage("Please wait..");
                pd.setCanceledOnTouchOutside(false);
                pd.show();
                if (emailid.isEmpty() || passw.isEmpty()) {

                    View toastview=getLayoutInflater().inflate(R.layout.fillallboxexcorrect,null );
                    Toast toast=new Toast(getActivity().getApplicationContext());
                    toast.setView(toastview);
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.show();

                    pd.dismiss();
                }
                else
                    { mAuth.signInWithEmailAndPassword(emailid,passw).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {

                            View toastview=getLayoutInflater().inflate(R.layout.loginsuccess,null );
                            Toast toast=new Toast(getActivity().getApplicationContext());
                            toast.setView(toastview);
                            toast.setDuration(Toast.LENGTH_SHORT);
                            toast.show();

                            reference=FirebaseDatabase.getInstance().
                                    getReference("user");

                            reference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NotNull DataSnapshot dataSnapshot) {

                                    for(DataSnapshot data:dataSnapshot.getChildren())
                                    {
                                        User userdata=data.getValue(User.class);
                                        if(userdata.getEmail().equals(emailid))
                                        {
                                            phone=userdata.getPhone();
                                            address=userdata.getAddress();
                                            usernameprofile=userdata.getName();
                                            emailuser=userdata.getEmail();
                                            try {
                                                SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
                                                SharedPreferences.Editor editor = sharedPref.edit();
                                                editor.clear();
                                                editor.putString("userID", emailuser);
                                                editor.putString("username",usernameprofile);
                                                editor.putString("phone",phone);
                                                editor.putString("address",address);
                                                editor.apply();
                                                pd.dismiss();

                                            }
                                            catch (NullPointerException e)
                                            {

                                            }

                                            UpdateUI();
                                        }
                                    }
                                }
                                @Override
                                public void onCancelled(@NotNull DatabaseError databaseError) {

                                    Toast.makeText(getActivity(), ""+databaseError, Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(Exception e) {
                            Toast.makeText(getContext(), "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                            emailuser="";
                            pd.dismiss();
                        }
                    });
                }
            }
        });

        rootview.findViewById(R.id.no_account).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    getFragmentManager()
                            .beginTransaction().setCustomAnimations(R.anim.animate_slide_left_enter, R.anim.animate_slide_left_exit).addToBackStack("")
                            .replace(R.id.main_fragment_container, new RegisterFragment(), "RegisterFragment")
                            .commit();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        rootview.findViewById(R.id.no_account2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    getFragmentManager()
                            .beginTransaction().addToBackStack("")
                            .setCustomAnimations(R.anim.animate_slide_left_enter, R.anim.animate_slide_left_exit).addToBackStack("")
                            .replace(R.id.main_fragment_container, new RegisterFragment(), "RegisterFragment")
                            .commit();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return rootview;
    }

    private void UpdateUI() {
        try {

            Intent activityinbackstack=new Intent(getActivity(),MainActivity.class);
            Intent activity=new Intent(getActivity(),MainActivity.class);
            TaskStackBuilder builder=TaskStackBuilder.create(getActivity());
            builder.addNextIntent(activityinbackstack);
            builder.addNextIntent(activity);
            builder.startActivities();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void login(String email, String pass) {
    }
}