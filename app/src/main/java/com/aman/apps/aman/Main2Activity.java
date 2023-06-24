package com.aman.apps.aman;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.aman.apps.aman.Fragments.No_Internet;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tryagain);
        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.splashlinear2,new No_Internet()).commit();


    }
}
