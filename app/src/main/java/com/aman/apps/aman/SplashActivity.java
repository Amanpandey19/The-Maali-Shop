package com.aman.apps.aman;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.alexzh.circleimageview.CircleImageView;

public class SplashActivity extends AppCompatActivity {

    Animation animation1,animation2,animation3;
    CardView cardView;
    LinearLayout linearLayout;
    CircleImageView circleImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        animation1= AnimationUtils.loadAnimation(SplashActivity.this,R.anim.animate_spin_enter);
        animation2= AnimationUtils.loadAnimation(SplashActivity.this,R.anim.splash_up_to_down);

        cardView=findViewById(R.id.splashtext);
        circleImageView=findViewById(R.id.splashimage);
        linearLayout=findViewById(R.id.splashlinear);

        cardView.setAnimation(animation1);
        circleImageView.setAnimation(animation2);

        ConnectivityManager manager= (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);

        boolean is3g=manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
        boolean iswifi=manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();

        if(!is3g && !iswifi)
        {
            View toastview=getLayoutInflater().inflate(R.layout.toastv,null );
            Toast toast=new Toast(getApplicationContext());
            toast.setView(toastview);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.show();
            ex();
        }else
        {
            init();
        }

    }
    public void init() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, com.aman.apps.aman.MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 4000);
    }

    public void ex() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try{
                    Intent intent = new Intent(SplashActivity.this, com.aman.apps.aman.Main2Activity.class);
                    startActivity(intent);
                    finish();
                }catch (Exception e)
                {

                }

            }
        }, 6000);
    }
}
