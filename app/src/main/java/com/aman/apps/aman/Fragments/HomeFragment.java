package com.aman.apps.aman.Fragments;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.agrawalsuneet.dotsloader.loaders.LazyLoader;
import com.aman.apps.aman.Adapters.Slider_Pager_Adapter2;
import com.aman.apps.aman.HomeFragment_Aeproduct;
import com.aman.apps.aman.HomeFragment_Potproduct;
import com.aman.apps.aman.HomeFragment_Seedproduct;
import com.aman.apps.aman.HomeFragment_product;
import com.aman.apps.aman.R;
import com.aman.apps.aman.UploadPosterData;
import com.aman.apps.aman.UploadProductData2;
import com.aman.apps.aman.UploadSlider;
import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;

import static android.content.Context.CONNECTIVITY_SERVICE;


public class HomeFragment extends Fragment {

    int page_position = 0;
    Timer timer;
    private  Handler handler1;
    private ViewPager images_slider;
    private LinearLayout pages_dots;
    private TextView[] dots;
    ArrayList<UploadProductData2> mainproductsplantHome;
    ArrayList<UploadProductData2> mainproductsseedHome;
    ArrayList<UploadProductData2> mainproductspotHome;
    ArrayList<UploadProductData2> mainproductsaeHome;
    ImageView im,im2,im3,im4,im5,im6,
            im7,im8,im9,im10,im11,im12,
            im13,im14,im15,im16,im17,im18
            ,im19,im20,im21,im22,im23,im24;
    TextView tv1,tv2,tv3,tv4,tv5,tv6,tv7,tv8,tv9,tv10,tv11,tv12,
            t1,t2,t3,t4,t5,t6,t7,t8,t9,t10,t11,t12,
                n1,n2,n3,n4,n5,n6,p1,p2,p3,p4,p5,p6
            ,tt1,tt2,tt3,tt4,tt5,tt6,tt7,tt8,tt9,tt10,tt11,tt12;
    private DatabaseReference dbreference,sdreference,pdreference,aereference,s4;
    private ImageView imageView1,imageView2,imageView3,imageView4,
                      imageView6,imageView7,imageView8,imageView5,imageView9;
    private DatabaseReference homedecorreference,plantgiftingrrefrence,terracegradenreference,
                               balconyreference,airrefiningrefrence,verticalreference,
                               wallgardenreference,plantsatworkrefrence,corporatereference;
    private CardView c1,c2,c3,c4;
    String slider1url,slider2url,slider3url,slider4url;

    public HomeFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View  rootview = inflater.inflate(R.layout.fragment_home, container, false);
        getActivity().setTitle("The Maali Shop");



        handler1=new Handler(Looper.getMainLooper());/*
        final AlertDialog pd=new SpotsDialog.Builder().setContext(getContext()).setTheme(R.style.Customprogress).build();
        pd.show();
*/
/*
        final ProgressDialog pd=new ProgressDialog(getContext());
        pd.setMessage("Loading");
        pd.setCanceledOnTouchOutside(false);
        pd.show();
*/
        final Dialog pd=new Dialog(getActivity());
        pd.setContentView(R.layout.homepageprogressdialog);

        pd.setCanceledOnTouchOutside(false);


        LazyLoader lazyLoader=pd.findViewById(R.id.progress_container);

        LazyLoader loader = new LazyLoader(getContext(), 30, 20,
                ContextCompat.getColor(getContext(), R.color.loader_selected),
                ContextCompat.getColor(getContext(), R.color.loader_selected),
                ContextCompat.getColor(getContext(), R.color.loader_selected));
        loader.setAnimDuration(500);
        loader.setFirstDelayDuration(100);
        loader.setSecondDelayDuration(200);
        loader.setInterpolator(new LinearInterpolator());

        lazyLoader.addView(loader);
        pd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
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
        }else
        {

        }
        Thread t=new Thread(){
            @Override
            public void run() {
                try{
                    while (!isInterrupted()){
                        Thread.sleep(1000);
                        handler1.post(new Runnable() {
                            @Override
                            public void run() {
                                TextView tv=rootview.findViewById(R.id.date);
                                TextView tv1=rootview.findViewById(R.id.date2);
                                TextView tv2=rootview.findViewById(R.id.date4);
                                Calendar calendar=Calendar.getInstance();
                                int hour=calendar.get(Calendar.HOUR_OF_DAY);
                                if(hour>12 && hour<21)
                                {
                                    hour=8-calendar.get(Calendar.HOUR);
                                }
                                else
                                if(hour<=12)
                                {
                                    hour=8-hour+12;
                                }
                                else
                                if(hour>=21)
                                {
                                    hour=23-hour+21;
                                }
                                int min=calendar.get(Calendar.MINUTE);
                                int Sec=calendar.get(Calendar.SECOND);
                                min=59-min;
                                Sec=59-Sec;
                                String h=" "+hour;
                                String m=" "+min;
                                String s=" "+Sec;
                                tv.setText(h);
                                tv1.setText(m);
                                tv2.setText(s);
                            }
                        });
                    }
                }catch (InterruptedException e){

                }
            }
        };
        t.start();



        images_slider = rootview.findViewById(R.id.image_page_slider);
        pages_dots = rootview.findViewById(R.id.image_page_dots);

        imageView1=rootview.findViewById(R.id.homedecor);
        imageView2=rootview.findViewById(R.id.plantgifting);
        imageView3=rootview.findViewById(R.id.terracegarden);
        imageView4=rootview.findViewById(R.id.corporategifting);
        imageView5=rootview.findViewById(R.id.wallgarden);
        imageView6=rootview.findViewById(R.id.plantsatwork);
        imageView7=rootview.findViewById(R.id.airrefining);
        imageView8=rootview.findViewById(R.id.balconygarden);
        imageView9=rootview.findViewById(R.id.verticalgarden);

        s4=FirebaseDatabase.getInstance().getReference("HomePageSliderImages");
        s4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {

                UploadSlider uploadSlider=dataSnapshot.getValue(UploadSlider.class);

                slider1url=uploadSlider.getPoster_url();
                slider2url=uploadSlider.getPoster2_url();
                slider3url=uploadSlider.getPoster3_url();
                slider4url=uploadSlider.getPoster4_url();
                timer = new Timer();
                initSlider();
                scheduleSlider();
            }
            @Override
            public void onCancelled(@NotNull DatabaseError databaseError) {

            }
        });

        homedecorreference=FirebaseDatabase.getInstance().getReference("HomePagePoster1PosterPicture");

        homedecorreference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {

                UploadPosterData uploadPosterData;

                uploadPosterData=dataSnapshot.getValue(UploadPosterData.class);

                Glide.with(getActivity()).load(uploadPosterData.getPoster_url()).into(imageView1);

            }

            @Override
            public void onCancelled(@NotNull DatabaseError databaseError) {

            }
        });

        plantgiftingrrefrence=FirebaseDatabase.getInstance().getReference("HomePagePoster3PosterPicture");

        plantgiftingrrefrence.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {

                UploadPosterData uploadPosterData;


                uploadPosterData=dataSnapshot.getValue(UploadPosterData.class);


                Glide.with(getActivity()).load(uploadPosterData.getPoster_url()).into(imageView2);

            }

            @Override
            public void onCancelled(@NotNull DatabaseError databaseError) {

            }
        });

        terracegradenreference=FirebaseDatabase.getInstance().getReference("HomePagePoster2PosterPicture");

        terracegradenreference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {

                UploadPosterData uploadPosterData;


                uploadPosterData=dataSnapshot.getValue(UploadPosterData.class);


                Glide.with(getActivity()).load(uploadPosterData.getPoster_url()).into(imageView3);

            }

            @Override
            public void onCancelled(@NotNull DatabaseError databaseError) {

            }
        });

        balconyreference= FirebaseDatabase.getInstance().getReference("HomePageBalconyGardenPosterPicture");

        balconyreference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {

                UploadPosterData uploadPosterData;


                uploadPosterData=dataSnapshot.getValue(UploadPosterData.class);


                Glide.with(getActivity()).load(uploadPosterData.getPoster_url()).into(imageView8);

            }

            @Override
            public void onCancelled(@NotNull DatabaseError databaseError) {

            }
        });

        airrefiningrefrence= FirebaseDatabase.getInstance().getReference("HomePageAirRefiningPosterPicture");

        airrefiningrefrence.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {

                UploadPosterData uploadPosterData;


                uploadPosterData=dataSnapshot.getValue(UploadPosterData.class);


                Glide.with(getActivity()).load(uploadPosterData.getPoster_url()).into(imageView7);

            }

            @Override
            public void onCancelled(@NotNull DatabaseError databaseError) {

            }
        });

        plantsatworkrefrence= FirebaseDatabase.getInstance().getReference("HomePageAtWorkPosterPicture");

        plantsatworkrefrence.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {

                UploadPosterData uploadPosterData;


                uploadPosterData=dataSnapshot.getValue(UploadPosterData.class);


                Glide.with(getActivity()).load(uploadPosterData.getPoster_url()).into(imageView6);

            }

            @Override
            public void onCancelled(@NotNull DatabaseError databaseError) {

            }
        });

        wallgardenreference= FirebaseDatabase.getInstance().getReference("HomePageWallGardenPosterPicture");

        wallgardenreference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {

                UploadPosterData uploadPosterData;


                uploadPosterData=dataSnapshot.getValue(UploadPosterData.class);


                Glide.with(getActivity()).load(uploadPosterData.getPoster_url()).into(imageView5);

            }

            @Override
            public void onCancelled(@NotNull DatabaseError databaseError) {

            }
        });

        corporatereference= FirebaseDatabase.getInstance().getReference("HomePageCorporatePosterPicture");

        corporatereference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {

                UploadPosterData uploadPosterData;


                uploadPosterData=dataSnapshot.getValue(UploadPosterData.class);


                Glide.with(getActivity()).load(uploadPosterData.getPoster_url()).into(imageView4);

            }

            @Override
            public void onCancelled(@NotNull DatabaseError databaseError) {

            }
        });

        verticalreference= FirebaseDatabase.getInstance().getReference("HomePageVerticalGardenPosterPicture");

        verticalreference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {

                UploadPosterData uploadPosterData;


                uploadPosterData=dataSnapshot.getValue(UploadPosterData.class);


                Glide.with(getActivity()).load(uploadPosterData.getPoster_url()).into(imageView9);

            }

            @Override
            public void onCancelled(@NotNull DatabaseError databaseError) {

            }
        });

        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainGalleryFragment mainGalleryFragment=new MainGalleryFragment();
                Bundle bundle=new Bundle();
                bundle.putString("ref","HomeDecorPictures");
                mainGalleryFragment.setArguments(bundle);

                getFragmentManager()
                        .beginTransaction().setCustomAnimations(R.anim.animate_card_enter, R.anim.animate_card_exit).addToBackStack("")
                        .replace(R.id.main_fragment_container,mainGalleryFragment).commit();
            }
        });

        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainGalleryFragment mainGalleryFragment=new MainGalleryFragment();
                Bundle bundle=new Bundle();
                bundle.putString("ref","PlantGiftingPictures");
                mainGalleryFragment.setArguments(bundle);

                getFragmentManager()
                        .beginTransaction().setCustomAnimations(R.anim.animate_card_enter, R.anim.animate_card_exit).addToBackStack("")
                        .replace(R.id.main_fragment_container,mainGalleryFragment).commit();
            }
        });

        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainGalleryFragment mainGalleryFragment=new MainGalleryFragment();
                Bundle bundle=new Bundle();
                bundle.putString("ref","TerraceGardenPictures");
                mainGalleryFragment.setArguments(bundle);

                getFragmentManager()
                        .beginTransaction().setCustomAnimations(R.anim.animate_card_enter, R.anim.animate_card_exit).addToBackStack("")
                        .replace(R.id.main_fragment_container,mainGalleryFragment).commit();
            }
        });

        imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainGalleryFragment mainGalleryFragment=new MainGalleryFragment();
                Bundle bundle=new Bundle();
                bundle.putString("ref","CorporateGiftingPictures");
                mainGalleryFragment.setArguments(bundle);

                getFragmentManager()
                        .beginTransaction().setCustomAnimations(R.anim.animate_card_enter, R.anim.animate_card_exit).addToBackStack("")
                        .replace(R.id.main_fragment_container,mainGalleryFragment).commit();
            }
        });

        imageView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainGalleryFragment mainGalleryFragment=new MainGalleryFragment();
                Bundle bundle=new Bundle();
                bundle.putString("ref","WallGardenPictures");
                mainGalleryFragment.setArguments(bundle);

                getFragmentManager()
                        .beginTransaction().setCustomAnimations(R.anim.animate_card_enter, R.anim.animate_card_exit)
                        .addToBackStack("")
                        .replace(R.id.main_fragment_container,mainGalleryFragment).commit();
            }
        });

        imageView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainGalleryFragment mainGalleryFragment=new MainGalleryFragment();
                Bundle bundle=new Bundle();
                bundle.putString("ref","AtWorkGardenPictures");
                mainGalleryFragment.setArguments(bundle);

                getFragmentManager()
                        .beginTransaction().setCustomAnimations(R.anim.animate_card_enter, R.anim.animate_card_exit)
                        .addToBackStack("")
                        .replace(R.id.main_fragment_container,mainGalleryFragment).commit();
            }
        });

        imageView7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager()
                        .beginTransaction().setCustomAnimations(R.anim.animate_card_enter, R.anim.animate_card_exit)
                        .addToBackStack("")
                        .replace(R.id.main_fragment_container,new AirPurifyingPlantsFragment()).commit();
            }
        });

        imageView8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainGalleryFragment mainGalleryFragment=new MainGalleryFragment();
                Bundle bundle=new Bundle();
                bundle.putString("ref","BalconyGardenPictures");
                mainGalleryFragment.setArguments(bundle);

                getFragmentManager()
                        .beginTransaction().setCustomAnimations(R.anim.animate_card_enter, R.anim.animate_card_exit)
                        .addToBackStack("")
                        .replace(R.id.main_fragment_container,mainGalleryFragment).commit();
            }
        });

        imageView9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainGalleryFragment mainGalleryFragment=new MainGalleryFragment();
                Bundle bundle=new Bundle();
                bundle.putString("ref","VerticalGardenPictures");
                mainGalleryFragment.setArguments(bundle);

                getFragmentManager()
                        .beginTransaction().setCustomAnimations(R.anim.animate_card_enter, R.anim.animate_card_exit)
                        .addToBackStack("")
                        .replace(R.id.main_fragment_container,mainGalleryFragment).commit();
            }
        });


        c1=rootview.findViewById(R.id.view_all1);
        c2=rootview.findViewById(R.id.view_all2);
        c3=rootview.findViewById(R.id.view_all3);
        c4=rootview.findViewById(R.id.view_all4);


        c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getFragmentManager()
                        .beginTransaction().addToBackStack("")
                        .replace(R.id.main_fragment_container,new category()).commit();
               }
        });

        c2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getFragmentManager().beginTransaction().addToBackStack("")
                        .replace(R.id.main_fragment_container,new SeedFragment()).commit();
            }
        });

        c3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getFragmentManager().beginTransaction().addToBackStack("")
                        .replace(R.id.main_fragment_container,new PotsFragment()).commit();

            }
        });

        c4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().addToBackStack("")
                        .replace(R.id.main_fragment_container,new Equipment_Fragment()).commit();

            }
        });



        im=rootview.findViewById(R.id.plant_item_1_image);
        tv1=rootview.findViewById(R.id.plant_item_1_name);
        tv2=rootview.findViewById(R.id.plant_item_1_price);

        im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeFragment_product homeFragment=new HomeFragment_product();
                String name= tv1.getText().toString();

                Bundle b=new Bundle();
                b.putString("name_key",name);
                homeFragment.setArguments(b);

                getFragmentManager().
                        beginTransaction().addToBackStack("").
                        replace(R.id.main_fragment_container,homeFragment).commit();

            }
        });

        im2=rootview.findViewById(R.id.plant_item_2_image);
        tv3=rootview.findViewById(R.id.plant_item_2_name);
        tv4=rootview.findViewById(R.id.plant_item_2_price);

        im2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeFragment_product homeFragment=new HomeFragment_product();
                String name=tv3.getText().toString();

                Bundle b=new Bundle();
                b.putString("name_key",name);
                homeFragment.setArguments(b);

                getFragmentManager().
                        beginTransaction().addToBackStack("").
                        replace(R.id.main_fragment_container,homeFragment).commit();

            }
        });

        im3=rootview.findViewById(R.id.plant_item_3_image);
        tv5=rootview.findViewById(R.id.plant_item_3_name);
        tv6=rootview.findViewById(R.id.plant_item_3_price);

        im3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeFragment_product homeFragment=new HomeFragment_product();
                String name=tv5.getText().toString();

                Bundle b=new Bundle();
                b.putString("name_key",name);
                homeFragment.setArguments(b);

                getFragmentManager().
                        beginTransaction().addToBackStack("").
                        replace(R.id.main_fragment_container,homeFragment).commit();

            }
        });

        im4=rootview.findViewById(R.id.plant_item_4_image);
        tv7=rootview.findViewById(R.id.plant_item_4_name);
        tv8=rootview.findViewById(R.id.plant_item_4_price);


        im4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeFragment_product homeFragment=new HomeFragment_product();
                String name=tv7.getText().toString();

                Bundle b=new Bundle();
                b.putString("name_key",name);
                homeFragment.setArguments(b);

                getFragmentManager().
                        beginTransaction().addToBackStack("").
                        replace(R.id.main_fragment_container,homeFragment).commit();

            }
        });

        im5=rootview.findViewById(R.id.plant_item_5_image);
        tv9=rootview.findViewById(R.id.plant_item_5_name);
        tv10=rootview.findViewById(R.id.plant_item_5_price);

        im5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeFragment_product homeFragment=new HomeFragment_product();
                String name=tv9.getText().toString();

                Bundle b=new Bundle();
                b.putString("name_key",name);
                homeFragment.setArguments(b);

                getFragmentManager().
                        beginTransaction().addToBackStack("").
                        replace(R.id.main_fragment_container,homeFragment).commit();

            }
        });


        im6=rootview.findViewById(R.id.plant_item_6_image);
        tv11=rootview.findViewById(R.id.plant_item_6_name);
        tv12=rootview.findViewById(R.id.plant_item_6_price);

        im6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                HomeFragment_product homeFragment=new HomeFragment_product();
                String name=tv11.getText().toString();

                Bundle b=new Bundle();
                b.putString("name_key",name);
                homeFragment.setArguments(b);

                getFragmentManager().
                        beginTransaction().addToBackStack("").
                        replace(R.id.main_fragment_container,homeFragment).commit();

            }
        });

        dbreference=FirebaseDatabase.getInstance().getReference("IndoorPlantProductPictures");

        mainproductsplantHome=new ArrayList<UploadProductData2>();

        dbreference.addValueEventListener(new ValueEventListener() {

            @Override

            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {


                for(DataSnapshot data:dataSnapshot.getChildren())
                    {
                        UploadProductData2 uploadData=data.getValue(UploadProductData2.class);

                        mainproductsplantHome.add(uploadData);
                    }
                    Collections.shuffle(mainproductsplantHome);
                    UploadProductData2 uploadProductData;

                    uploadProductData= mainproductsplantHome.get(1);
                    Glide.with(getActivity()).load(uploadProductData.getUrl1()).into(im);
                    tv1.setText(uploadProductData.getName_product());
                    tv2.setText("₹ "+uploadProductData.getPrice_product());

                    uploadProductData= mainproductsplantHome.get(3);
                    Glide.with(getActivity()).load(uploadProductData.getUrl1()).into(im2);
                    tv3.setText(uploadProductData.getName_product());
                    tv4.setText("₹ "+uploadProductData.getPrice_product());

                    uploadProductData= mainproductsplantHome.get(2);
                    Glide.with(getActivity()).load(uploadProductData.getUrl1()).into(im3);
                    tv5.setText(uploadProductData.getName_product());
                    tv6.setText("₹ "+uploadProductData.getPrice_product());

                    uploadProductData= mainproductsplantHome.get(4);
                    Glide.with(getActivity()).load(uploadProductData.getUrl1()).into(im4);
                    tv7.setText(uploadProductData.getName_product());
                    tv8.setText("₹ "+uploadProductData.getPrice_product());

                    uploadProductData= mainproductsplantHome.get(5);
                    Glide.with(getActivity()).load(uploadProductData.getUrl1()).into(im5);
                    tv9.setText(uploadProductData.getName_product());
                    tv10.setText("₹ "+uploadProductData.getPrice_product());

                    uploadProductData= mainproductsplantHome.get(0);
                    Glide.with(getActivity()).load(uploadProductData.getUrl1()).into(im6);
                    tv11.setText(uploadProductData.getName_product());
                    tv12.setText("₹ "+uploadProductData.getPrice_product());
            }


            @Override
            public void onCancelled(@NotNull DatabaseError databaseError) {

            }
        });

        im.setScaleType(ImageView.ScaleType.CENTER_CROP);
        im2.setScaleType(ImageView.ScaleType.CENTER_CROP);
        im3.setScaleType(ImageView.ScaleType.CENTER_CROP);
        im4.setScaleType(ImageView.ScaleType.CENTER_CROP);
        im5.setScaleType(ImageView.ScaleType.CENTER_CROP);
        im6.setScaleType(ImageView.ScaleType.CENTER_CROP);


        im7=rootview.findViewById(R.id.seed_item_1_image);
        t1=rootview.findViewById(R.id.seed_item_1_name);
        t2=rootview.findViewById(R.id.seed_item_1_price);
        im7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeFragment_Seedproduct homeFragment=new HomeFragment_Seedproduct();
                String name=t1.getText().toString();

                Bundle b=new Bundle();
                b.putString("name_key",name);
                homeFragment.setArguments(b);

                getFragmentManager().
                        beginTransaction().addToBackStack("").
                        replace(R.id.main_fragment_container,homeFragment).commit();

            }
        });

        im8=rootview.findViewById(R.id.seed_item_2_image);
        t3=rootview.findViewById(R.id.seed_item_2_name);
        t4=rootview.findViewById(R.id.seed_item_2_price);

        im8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeFragment_Seedproduct homeFragment=new HomeFragment_Seedproduct();
                String name=t3.getText().toString();

                Bundle b=new Bundle();
                b.putString("name_key",name);
                homeFragment.setArguments(b);

                getFragmentManager().
                        beginTransaction().addToBackStack("").
                        replace(R.id.main_fragment_container,homeFragment).commit();

            }
        });


        im9=rootview.findViewById(R.id.seed_item_3_image);
        t5=rootview.findViewById(R.id.seed_item_3_name);
        t6=rootview.findViewById(R.id.seed_item_3_price);

        im9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                HomeFragment_Seedproduct homeFragment=new HomeFragment_Seedproduct();
                String name=t5.getText().toString();

                Bundle b=new Bundle();
                b.putString("name_key",name);
                homeFragment.setArguments(b);

                getFragmentManager().
                        beginTransaction().addToBackStack("").
                        replace(R.id.main_fragment_container,homeFragment).commit();

            }
        });

        im10=rootview.findViewById(R.id.seed_item_4_image);
        t7=rootview.findViewById(R.id.seed_item_4_name);
        t8=rootview.findViewById(R.id.seed_item_4_price);

        im10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                HomeFragment_Seedproduct homeFragment=new HomeFragment_Seedproduct();
                String name=t7.getText().toString();

                Bundle b=new Bundle();
                b.putString("name_key",name);
                homeFragment.setArguments(b);

                getFragmentManager().
                        beginTransaction().addToBackStack("").
                        replace(R.id.main_fragment_container,homeFragment).commit();

            }
        });

        im11=rootview.findViewById(R.id.seed_item_5_image);
        t9=rootview.findViewById(R.id.seed_item_5_name);
        t10=rootview.findViewById(R.id.seed_item_5_price);
        im11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                HomeFragment_Seedproduct homeFragment=new HomeFragment_Seedproduct();
                String name=t9.getText().toString();

                Bundle b=new Bundle();
                b.putString("name_key",name);
                homeFragment.setArguments(b);

                getFragmentManager().
                        beginTransaction().addToBackStack("").
                        replace(R.id.main_fragment_container,homeFragment).commit();

            }
        });

        im12=rootview.findViewById(R.id.seed_item_6_image);
        t11=rootview.findViewById(R.id.seed_item_6_name);
        t12=rootview.findViewById(R.id.seed_item_6_price);
        im12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                HomeFragment_Seedproduct homeFragment=new HomeFragment_Seedproduct();
                String name=t11.getText().toString();

                Bundle b=new Bundle();
                b.putString("name_key",name);
                homeFragment.setArguments(b);

                getFragmentManager().
                        beginTransaction().addToBackStack("").
                        replace(R.id.main_fragment_container,homeFragment).commit();

            }
        });


        sdreference=FirebaseDatabase.getInstance().getReference("SeedProductPictures");

        mainproductsseedHome=new ArrayList<UploadProductData2>();

        sdreference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {

                for(DataSnapshot data:dataSnapshot.getChildren())
                {
                    UploadProductData2 uploadData=data.getValue(UploadProductData2.class);

                    mainproductsseedHome.add(uploadData);
                }
                Collections.shuffle(mainproductsseedHome);
                UploadProductData2 uploadProductData;

                uploadProductData= mainproductsseedHome.get(1);
                Glide.with(getActivity()).load(uploadProductData.getUrl1()).into(im7);
                t1.setText(uploadProductData.getName_product());
                t2.setText("₹ "+uploadProductData.getPrice_product());

                uploadProductData= mainproductsseedHome.get(3);
                Glide.with(getActivity()).load(uploadProductData.getUrl1()).into(im8);
                t3.setText(uploadProductData.getName_product());
                t4.setText("₹ "+uploadProductData.getPrice_product());

                uploadProductData= mainproductsseedHome.get(2);
                Glide.with(getActivity()).load(uploadProductData.getUrl1()).into(im9);
                t5.setText(uploadProductData.getName_product());
                t6.setText("₹ "+uploadProductData.getPrice_product());

                uploadProductData= mainproductsseedHome.get(4);
                Glide.with(getActivity()).load(uploadProductData.getUrl1()).into(im10);
                t7.setText(uploadProductData.getName_product());
                t8.setText("₹ "+uploadProductData.getPrice_product());

                uploadProductData= mainproductsseedHome.get(5);
                Glide.with(getActivity()).load(uploadProductData.getUrl1()).into(im11);
                t9.setText(uploadProductData.getName_product());
                t10.setText("₹ "+uploadProductData.getPrice_product());

                uploadProductData= mainproductsseedHome.get(0);
                Glide.with(getActivity()).load(uploadProductData.getUrl1()).into(im12);
                t11.setText(uploadProductData.getName_product());
                t12.setText("₹ "+uploadProductData.getPrice_product());
            }

            @Override
            public void onCancelled(@NotNull DatabaseError databaseError) {

            }
        });
        im7.setScaleType(ImageView.ScaleType.CENTER_CROP);
        im8.setScaleType(ImageView.ScaleType.CENTER_CROP);
        im9.setScaleType(ImageView.ScaleType.CENTER_CROP);
        im10.setScaleType(ImageView.ScaleType.CENTER_CROP);
        im11.setScaleType(ImageView.ScaleType.CENTER_CROP);
        im12.setScaleType(ImageView.ScaleType.CENTER_CROP);



        im13=rootview.findViewById(R.id.pot_item_1_image);
        n1=rootview.findViewById(R.id.pot_item_1_name);
        p1=rootview.findViewById(R.id.pot_item_1_price);
        im13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                HomeFragment_Potproduct homeFragment=new HomeFragment_Potproduct();
                String name=n1.getText().toString();

                Bundle b=new Bundle();
                b.putString("name_key",name);
                homeFragment.setArguments(b);

                getFragmentManager().
                        beginTransaction().addToBackStack("").
                        replace(R.id.main_fragment_container,homeFragment).commit();

            }
        });

        im14=rootview.findViewById(R.id.pot_item_2_image);
        n2=rootview.findViewById(R.id.pot_item_2_name);
        p2=rootview.findViewById(R.id.pot_item_2_price);
        im14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                HomeFragment_Potproduct homeFragment=new HomeFragment_Potproduct();
                String name=n2.getText().toString();

                Bundle b=new Bundle();
                b.putString("name_key",name);
                homeFragment.setArguments(b);

                getFragmentManager().
                        beginTransaction().addToBackStack("").
                        replace(R.id.main_fragment_container,homeFragment).commit();

            }
        });


        im15=rootview.findViewById(R.id.pot_item_3_image);
        n3=rootview.findViewById(R.id.pot_item_3_name);
        p3=rootview.findViewById(R.id.pot_item_3_price);

        im15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                HomeFragment_Potproduct homeFragment=new HomeFragment_Potproduct();
                String name=n3.getText().toString();

                Bundle b=new Bundle();
                b.putString("name_key",name);
                homeFragment.setArguments(b);

                getFragmentManager().
                        beginTransaction().addToBackStack("").
                        replace(R.id.main_fragment_container,homeFragment).commit();

            }
        });


        im16=rootview.findViewById(R.id.pot_item_4_image);
        n4=rootview.findViewById(R.id.pot_item_4_name);
        p4=rootview.findViewById(R.id.pot_item_4_price);

        im16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                HomeFragment_Potproduct homeFragment=new HomeFragment_Potproduct();
                String name=n4.getText().toString();

                Bundle b=new Bundle();
                b.putString("name_key",name);
                homeFragment.setArguments(b);

                getFragmentManager().
                        beginTransaction().addToBackStack("").
                        replace(R.id.main_fragment_container,homeFragment).commit();

            }
        });




        im17=rootview.findViewById(R.id.pot_item_5_image);
        n5=rootview.findViewById(R.id.pot_item_5_name);
        p5=rootview.findViewById(R.id.pot_item_5_price);
        im17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                HomeFragment_Potproduct homeFragment=new HomeFragment_Potproduct();
                String name=n5.getText().toString();

                Bundle b=new Bundle();
                b.putString("name_key",name);
                homeFragment.setArguments(b);

                getFragmentManager().
                        beginTransaction().addToBackStack("").
                        replace(R.id.main_fragment_container,homeFragment).commit();

            }
        });



        im18=rootview.findViewById(R.id.pot_item_6_image);
        n6=rootview.findViewById(R.id.pot_item_6_name);
        p6=rootview.findViewById(R.id.pot_item_6_price);

        im18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                HomeFragment_Potproduct homeFragment=new HomeFragment_Potproduct();
                String name=n6.getText().toString();

                Bundle b=new Bundle();
                b.putString("name_key",name);
                homeFragment.setArguments(b);

                getFragmentManager().
                        beginTransaction().addToBackStack("").
                        replace(R.id.main_fragment_container,homeFragment).commit();

            }
        });


        pdreference=FirebaseDatabase.getInstance().getReference("PotsProductPictures");

        mainproductspotHome=new ArrayList<UploadProductData2>();

        pdreference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {


                for(DataSnapshot data:dataSnapshot.getChildren())
                {
                    UploadProductData2 uploadData=data.getValue(UploadProductData2.class);

                    mainproductspotHome.add(uploadData);
                }
                Collections.shuffle(mainproductspotHome);
                UploadProductData2 uploadProductData;

                uploadProductData= mainproductspotHome.get(1);
                Glide.with(getActivity()).load(uploadProductData.getUrl1()).into(im13);
                n1.setText(uploadProductData.getName_product());
                p1.setText("₹ "+uploadProductData.getPrice_product());

                uploadProductData= mainproductspotHome.get(3);
                Glide.with(getActivity()).load(uploadProductData.getUrl1()).into(im14);
                n2.setText(uploadProductData.getName_product());
                p2.setText("₹ "+uploadProductData.getPrice_product());

                uploadProductData= mainproductspotHome.get(2);
                Glide.with(getActivity()).load(uploadProductData.getUrl1()).into(im15);
                n3.setText(uploadProductData.getName_product());
                p3.setText("₹ "+uploadProductData.getPrice_product());

                uploadProductData= mainproductspotHome.get(4);
                Glide.with(getActivity()).load(uploadProductData.getUrl1()).into(im16);
                n4.setText(uploadProductData.getName_product());
                p4.setText("₹ "+uploadProductData.getPrice_product());

                uploadProductData= mainproductspotHome.get(5);
                Glide.with(getActivity()).load(uploadProductData.getUrl1()).into(im17);
                n5.setText(uploadProductData.getName_product());
                p5.setText("₹ "+uploadProductData.getPrice_product());

                uploadProductData= mainproductspotHome.get(0);
                Glide.with(getActivity()).load(uploadProductData.getUrl1()).into(im18);
                n6.setText(uploadProductData.getName_product());
                p6.setText("₹ "+uploadProductData.getPrice_product());
            }

            @Override
            public void onCancelled(@NotNull DatabaseError databaseError) {

            }
        });

        im13.setScaleType(ImageView.ScaleType.CENTER_CROP);
        im14.setScaleType(ImageView.ScaleType.CENTER_CROP);
        im15.setScaleType(ImageView.ScaleType.CENTER_CROP);
        im16.setScaleType(ImageView.ScaleType.CENTER_CROP);
        im17.setScaleType(ImageView.ScaleType.CENTER_CROP);
        im18.setScaleType(ImageView.ScaleType.CENTER_CROP);

        im19=rootview.findViewById(R.id.ae_item_1_image);
        tt1=rootview.findViewById(R.id.ae_item_1_name);
        tt2=rootview.findViewById(R.id.ae_item_1_price);

        im19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeFragment_Aeproduct homeFragment=new HomeFragment_Aeproduct();
                String name=tt1.getText().toString();

                Bundle b=new Bundle();
                b.putString("name_key",name);
                homeFragment.setArguments(b);

                getFragmentManager().
                        beginTransaction().addToBackStack("").
                        replace(R.id.main_fragment_container,homeFragment).commit();

            }
        });
        im20=rootview.findViewById(R.id.ae_item_2_image);
        tt3=rootview.findViewById(R.id.ae_item_2_name);
        tt4=rootview.findViewById(R.id.ae_item_2_price);
        im20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeFragment_Aeproduct homeFragment=new HomeFragment_Aeproduct();
                String name=tt3.getText().toString();

                Bundle b=new Bundle();
                b.putString("name_key",name);
                homeFragment.setArguments(b);

                getFragmentManager().
                        beginTransaction().addToBackStack("").
                        replace(R.id.main_fragment_container,homeFragment).commit();

            }
        });


        im21=rootview.findViewById(R.id.ae_item_3_image);
        tt5=rootview.findViewById(R.id.ae_item_3_name);
        tt6=rootview.findViewById(R.id.ae_item_3_price);
        im21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeFragment_Aeproduct homeFragment=new HomeFragment_Aeproduct();
                String name=tt5.getText().toString();

                Bundle b=new Bundle();
                b.putString("name_key",name);
                homeFragment.setArguments(b);

                getFragmentManager().
                        beginTransaction().addToBackStack("").
                        replace(R.id.main_fragment_container,homeFragment).commit();

            }
        });


        im22=rootview.findViewById(R.id.ae_item_4_image);
        tt7=rootview.findViewById(R.id.ae_item_4_name);
        tt8=rootview.findViewById(R.id.ae_item_4_price);

        im22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeFragment_Aeproduct homeFragment=new HomeFragment_Aeproduct();
                String name=tt7.getText().toString();

                Bundle b=new Bundle();
                b.putString("name_key",name);
                homeFragment.setArguments(b);

                getFragmentManager().
                        beginTransaction().addToBackStack("").
                        replace(R.id.main_fragment_container,homeFragment).commit();

            }
        });


        im23=rootview.findViewById(R.id.ae_item_5_image);
        tt9=rootview.findViewById(R.id.ae_item_5_name);
        tt10=rootview.findViewById(R.id.ae_item_5_price);

        im23.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeFragment_Aeproduct homeFragment=new HomeFragment_Aeproduct();
                String name=tt9.getText().toString();

                Bundle b=new Bundle();
                b.putString("name_key",name);
                homeFragment.setArguments(b);

                getFragmentManager().
                        beginTransaction().addToBackStack("").
                        replace(R.id.main_fragment_container,homeFragment).commit();

            }
        });


        im24=rootview.findViewById(R.id.ae_item_6_image);
        tt11=rootview.findViewById(R.id.ae_item_6_name);
        tt12=rootview.findViewById(R.id.ae_item_6_price);
        im24.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeFragment_Aeproduct homeFragment=new HomeFragment_Aeproduct();
                String name=tt11.getText().toString();

                Bundle b=new Bundle();
                b.putString("name_key",name);
                homeFragment.setArguments(b);

                getFragmentManager().
                        beginTransaction().addToBackStack("").
                        replace(R.id.main_fragment_container,homeFragment).commit();

            }
        });


        aereference=FirebaseDatabase.getInstance().getReference("AccessoriesAndEqiupmentsProductPictures");


        mainproductsaeHome=new ArrayList<UploadProductData2>();
        aereference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {

                for(DataSnapshot data:dataSnapshot.getChildren())
                {
                    UploadProductData2 uploadData=data.getValue(UploadProductData2.class);

                    mainproductsaeHome.add(uploadData);
                }
                Collections.shuffle(mainproductsaeHome);
                UploadProductData2 uploadProductData;

                uploadProductData= mainproductsaeHome.get(1);
                Glide.with(getActivity()).load(uploadProductData.getUrl1()).into(im19);
                tt1.setText(uploadProductData.getName_product());
                tt2.setText("₹ "+uploadProductData.getPrice_product());

                uploadProductData= mainproductsaeHome.get(3);
                Glide.with(getActivity()).load(uploadProductData.getUrl1()).into(im20);
                tt3.setText(uploadProductData.getName_product());
                tt4.setText("₹ "+uploadProductData.getPrice_product());

                uploadProductData= mainproductsaeHome.get(2);
                Glide.with(getActivity()).load(uploadProductData.getUrl1()).into(im21);
                tt5.setText(uploadProductData.getName_product());
                tt6.setText("₹ "+uploadProductData.getPrice_product());

                uploadProductData= mainproductsaeHome.get(4);
                Glide.with(getActivity()).load(uploadProductData.getUrl1()).into(im22);
                tt7.setText(uploadProductData.getName_product());
                tt8.setText("₹ "+uploadProductData.getPrice_product());

                uploadProductData= mainproductsaeHome.get(5);
                Glide.with(getActivity()).load(uploadProductData.getUrl1()).into(im23);
                tt9.setText(uploadProductData.getName_product());
                tt10.setText("₹ "+uploadProductData.getPrice_product());

                uploadProductData= mainproductsaeHome.get(0);
                Glide.with(getActivity()).load(uploadProductData.getUrl1()).into(im24);
                tt11.setText(uploadProductData.getName_product());
                tt12.setText("₹ "+uploadProductData.getPrice_product());

            }

            @Override
            public void onCancelled(@NotNull DatabaseError databaseError) {

            }
        });

        im19.setScaleType(ImageView.ScaleType.CENTER_CROP);
        im20.setScaleType(ImageView.ScaleType.CENTER_CROP);
        im21.setScaleType(ImageView.ScaleType.CENTER_CROP);
        im22.setScaleType(ImageView.ScaleType.CENTER_CROP);
        im23.setScaleType(ImageView.ScaleType.CENTER_CROP);
        im24.setScaleType(ImageView.ScaleType.CENTER_CROP);


        Handler handler4 =new Handler();
        handler4.postDelayed(new Runnable() {
            @Override
            public void run() {
              pd.dismiss();
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
            }
        },3000);
        return rootview;
    }


    public void initSlider() {
        addBottomDots(0);

        String[] images={slider1url,slider2url,
                slider3url,slider4url};
        Slider_Pager_Adapter2 sliderPagerAdapter;

        sliderPagerAdapter = new Slider_Pager_Adapter2(getActivity(), images);
        images_slider.setAdapter(sliderPagerAdapter);
        images_slider.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onPageSelected(int position) {
                try
                {
                    addBottomDots(position);
                }
                catch (Exception e)
                {

                    e.getSuppressed();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void scheduleSlider() {

        final Handler handler = new Handler();

        try
        {
            final Runnable update = new Runnable() {
                public void run() {
                    if (page_position ==3) {
                        page_position = 0;
                    } else {
                        page_position = page_position + 1;
                    }
                    images_slider.setCurrentItem(page_position, true);
                }
            };

            timer.schedule(new TimerTask() {

                @Override
                public void run() {
                    handler.post(update);
                }
            }, 8000, 8000);

        }
        catch (Exception e)
        {}
    }

    public void addBottomDots(int currentPage) {
        try
        {

            dots = new TextView[4];

            pages_dots.removeAllViews();
            pages_dots.setPadding(0, -15, 0, 0);
            for (int i = 0; i < dots.length; i++) {
                dots[i] = new TextView(getContext());
                dots[i].setText(Html.fromHtml("&#8226;"));
                dots[i].setTextSize(35);
                dots[i].setTextColor(Color.parseColor("#B4F3B4")); // un selected
                pages_dots.addView(dots[i]);
            }

            if (dots.length > 0)
                dots[currentPage].setTextColor(Color.parseColor("#00e500")); // selected

        }
        catch (Exception e)
        {}


    }

    @Override
    public void onPause() {
        try{
            timer.cancel();
        }
        catch (Exception e)
        {}
        try{

            super.onPause();
        }
        catch (Exception e)
        {

        }
    }
}
