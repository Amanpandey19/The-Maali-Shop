package com.aman.apps.aman.Fragments;

import android.app.ProgressDialog;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.aman.apps.aman.Models.User;
import com.aman.apps.aman.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.NotNull;

import static android.content.Context.CONNECTIVITY_SERVICE;


public class RegisterFragment extends Fragment {
    EditText email, username, password, address,phone_no;

    User userdata;
    private RelativeLayout rlayout;
    private FirebaseAuth mAuth;
    private Animation animation;

    private DatabaseReference profileuserRef;
    public String currentuserid;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.other_register, container, false);
        getActivity().setTitle("Sign Up");


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


        email = rootview.findViewById(R.id.user_email);
        password = rootview.findViewById(R.id.user_password);
        username=rootview.findViewById(R.id.user_name);
        address=rootview.findViewById(R.id.user_address);
        phone_no=rootview.findViewById(R.id.phone_no);
        mAuth = FirebaseAuth.getInstance();

        rootview.findViewById(R.id.register_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String emailid=email.getText().toString();
                String pass=password.getText().toString();
                final String user_name=username.getText().toString();
                final String addrss=address.getText().toString();
                final String phoneno1=phone_no.getText().toString();
                if (!email.getText().toString().equals("") && !password.getText().toString().equals("") &&
                !username.getText().toString().equals("") && !address.getText().toString().equals("")
                && !phone_no.getText().toString().equals("")) {

                    mAuth.createUserWithEmailAndPassword(emailid,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NotNull Task<AuthResult> task) {
                            if (task.isSuccessful())
                            {
                                userdata=new User();
                                final ProgressDialog pd=new ProgressDialog(getActivity());
                                pd.setMessage("Please wait..");
                                pd.setCanceledOnTouchOutside(false);
                                pd.show();
                                profileuserRef=FirebaseDatabase.getInstance().
                                        getReference("user");
                                userdata.setName(user_name);
                                userdata.setEmail(emailid);
                                userdata.setAddress(addrss);
                                userdata.setPhone(phoneno1);

                                String childID=profileuserRef.push().getKey();
                                profileuserRef.child(childID).setValue(userdata);

                                View toastview=getLayoutInflater().inflate(R.layout.registrationsuccess,null );
                                Toast toast=new Toast(getActivity().getApplicationContext());
                                toast.setView(toastview);
                                toast.setDuration(Toast.LENGTH_LONG);
                                toast.show();

                                currentuserid = mAuth.getCurrentUser().getUid();

                                pd.dismiss();
                                UpdateUI();
                            }
                            else
                            {
                                Toast.makeText(getContext(), ""+task.getException(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


                } else {
                    View toastview=getLayoutInflater().inflate(R.layout.fillallboxexcorrect,null );
                    Toast toast=new Toast(getActivity().getApplicationContext());
                    toast.setView(toastview);
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });

        rootview.findViewById(R.id.have_account).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (getFragmentManager() != null) {
                        getFragmentManager().beginTransaction().
                                setCustomAnimations(R.anim.animate_slide_in_left,
                                        R.anim.animate_slide_out_right).replace(R.id.main_fragment_container, new LoginFragment()
                                , "LoginFragment")
                                .commit();;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        return rootview;
    }

    private void UpdateUI() {
        getFragmentManager().popBackStack();
    }

    private void register(String email, String pass, String address, String username) {
    }
}
