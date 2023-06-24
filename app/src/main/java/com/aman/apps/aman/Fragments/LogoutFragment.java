package com.aman.apps.aman.Fragments;


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
import android.widget.TextView;
import android.widget.Toast;

import com.alexzh.circleimageview.CircleImageView;
import com.aman.apps.aman.MainActivity;
import com.aman.apps.aman.R;
import com.google.firebase.auth.FirebaseAuth;

import static android.content.Context.CONNECTIVITY_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 */
public class LogoutFragment extends Fragment {


    FirebaseAuth mAuth;
    SharedPreferences sharedPreferences;
    TextView name;
    CircleImageView imageView;

    public LogoutFragment() {
        // Required empty public constructor
    }


   @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v=inflater.inflate(R.layout.fragment_logout, container, false);

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

       sharedPreferences=getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        final String username=sharedPreferences.getString("username","");

        View view=inflater.inflate(R.layout.nav_layout, container, false);

        imageView=view.findViewById(R.id.user_img);
        name=view.findViewById(R.id.menu_username);


        if(username.equals(""))
        {
            assert getFragmentManager() != null;
            getFragmentManager().beginTransaction()
                    .replace(R.id.main_fragment_container, new LoginFragment(), "LoginFragment")
                    .commit();
            View toastview=getLayoutInflater().inflate(R.layout.loginfirst,null );
            Toast toast=new Toast(getActivity().getApplicationContext());
            toast.setView(toastview);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.show();
        }
        else
            {
                mAuth=FirebaseAuth.getInstance();
                mAuth.signOut();
                editor.clear();
                editor.apply();
                Intent activityinbackstack=new Intent(getActivity(),MainActivity.class);
                Intent activity=new Intent(getActivity(),MainActivity.class);
                TaskStackBuilder builder=TaskStackBuilder.create(getActivity());
                builder.addNextIntent(activityinbackstack);
                builder.addNextIntent(activity);
                builder.startActivities();
                View toastview=getLayoutInflater().inflate(R.layout.logoutsuccess,null );
                Toast toast=new Toast(getActivity().getApplicationContext());
                toast.setView(toastview);
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.show();
        }
        return v;
    }
}
