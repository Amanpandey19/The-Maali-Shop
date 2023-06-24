package com.aman.apps.aman;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.alexzh.circleimageview.CircleImageView;
import com.aman.apps.aman.Fragments.HomeFragment;
import com.aman.apps.aman.Fragments.No_Internet;
import com.aman.apps.aman.Tools.SlideNavigation;
import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

public class MainActivity extends AppCompatActivity {
    ActionBarDrawerToggle actionBarDrawerToggle;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    TextView logout,login;
    CircleImageView imageView;
    DatabaseReference dbreference;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ConnectivityManager manager= (ConnectivityManager) MainActivity.this.getSystemService(CONNECTIVITY_SERVICE);

        boolean is3g=manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
        boolean iswifi=manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();

        if(!is3g && !iswifi)
        {
            getSupportFragmentManager().
                    beginTransaction().addToBackStack("").
                    replace(R.id.main_fragment_container,new No_Internet()).commit();

        }else
        {

        }

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        logout=findViewById(R.id.menu_logout);
        login=findViewById(R.id.menu_username);
        imageView=findViewById(R.id.user_img);
        sharedPreferences=this.getPreferences(Context.MODE_PRIVATE);
        String emailID=sharedPreferences.getString("userID","");
        final String username=sharedPreferences.getString("username","");
        final String phone=sharedPreferences.getString("phone","");
        dbreference= FirebaseDatabase.getInstance().getReference("UserImages").child(username+phone);

        if (emailID.equals(""))
        {
            login.setText(" Login ");
        }
        else
        {
            login.setText(username);
            dbreference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists())
                    {
                        String url= (String) dataSnapshot.getValue();
                        if(!url.equals(""))
                            Glide.with(MainActivity.this).load(url).into(imageView);
                    }
                }
                @Override
                public void onCancelled(@NotNull DatabaseError databaseError) {

                }
            });
        }
        drawerLayout = findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.main_fragment_container, new HomeFragment(), "HomeFragment")
                .commit();
        SlideNavigation slideNavigation = new SlideNavigation(R.id.main_fragment_container);
        slideNavigation.initSlideMenu(MainActivity.this, getSupportFragmentManager(), drawerLayout);
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.example_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NotNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.item2:
                Intent intent =new Intent(Intent.ACTION_SEND);
                String recipientlist="themaalishop@gmail.com";
                String recipient[]=recipientlist.split(",");

                intent.putExtra(Intent.EXTRA_EMAIL,recipient);
                intent.putExtra(Intent.EXTRA_SUBJECT,"Feedback on The Maali Shop ");

                intent.setType("message/rfc822");
                startActivity(Intent.createChooser(intent,"Choose an application to proceed"));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
