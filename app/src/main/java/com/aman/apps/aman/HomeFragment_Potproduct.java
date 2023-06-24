package com.aman.apps.aman;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.aman.apps.aman.Adapters.Myclick;
import com.aman.apps.aman.Adapters.RvAdapter;
import com.aman.apps.aman.Adapters.RvAdapter2;
import com.aman.apps.aman.Fragments.Add_A_Review_Fragment;
import com.aman.apps.aman.Fragments.BuyNow;
import com.aman.apps.aman.Fragments.CartFragment;
import com.aman.apps.aman.Fragments.Favourites_Fragment;
import com.aman.apps.aman.Fragments.LoginFragment;
import com.aman.apps.aman.Fragments.No_Internet;
import com.aman.apps.aman.Fragments.PotsFragment;
import com.aman.apps.aman.Fragments.Recently_Liked_Fragment;
import com.aman.apps.aman.Fragments.Recently_Viewed_Fragment;
import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;
import com.like.LikeButton;
import com.like.OnLikeListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;

import static android.content.Context.CONNECTIVITY_SERVICE;


public class HomeFragment_Potproduct extends Fragment {

    int page_position = 0;
    Timer timer;
    String quantity[]={"1","2","3","4","5"};
    Spinner spinner;
    private ViewPager images_slider;
    private LinearLayout pages_dots;
    private TextView[] dots;
    Animation animation;
    LikeButton heart,likeButton;
    UploadProductData2 viewedproduct;
    ArrayList<UploadProductData2> similarproducts;
    DatabaseReference prreference,recreference;
    TextView text;
    Button item1,item2,item3;
    private static RecyclerView recyclerView,recyclerView2,recyclerView3,recyclerView4;


    DatabaseReference deliveryreference,cartreference,viewedref,viewedref2,likeref,likeref2,heartref,heartref2;
    UploadPincode uploadPincode;
    Button buy,addtocart;
    RadioButton r1,r2,r3;
    EditText e1;
    private LinearLayout layout,layout2,layout3;
    String pricei;
    String descriptioncart,deliverycart;
    SharedPreferences sharedPreferences;String urlcart;
    ArrayList<String> arraylist;

    ArrayList<BuyNowList> buyNowLists;
    ImageView i1,i2,i3,i4,i5,i6;
    TextView n1,n2,n3,n4,n5,n6,reviewproduct;
    TextView p1,p2,p3,p4,p5,p6;
    CardView c1,c2,c3,c4,c5,c6,c7,c8,c9,c10,c11;
    ImageView des1,des2,des3;

    private TextView name,price,desc,del,care,offerprice;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.product, container, false);
        getActivity().setTitle("Pots");
        final ProgressDialog pd=new ProgressDialog(getActivity());
        pd.setMessage("Please wait..");
        pd.setCanceledOnTouchOutside(false);
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

        sharedPreferences=getActivity().getPreferences(Context.MODE_PRIVATE);

        final String emailID=sharedPreferences.getString("userID","");
        final String username=sharedPreferences.getString("username","");
        final String phone=sharedPreferences.getString("phone","");


        layout=rootview.findViewById(R.id.viewlayout);
        layout.setVisibility(View.INVISIBLE);
        layout2=rootview.findViewById(R.id.viewlayout2);
        layout2.setVisibility(View.INVISIBLE);
        layout3=rootview.findViewById(R.id.viewlayout3);
        layout3.setVisibility(View.INVISIBLE);

        animation= AnimationUtils.loadAnimation(getActivity(),R.anim.slide_down);

         offerprice=rootview.findViewById(R.id.offerprice);
        reviewproduct=rootview.findViewById(R.id.product_review);

        heart=rootview.findViewById(R.id.heart_button);
        likeButton=rootview.findViewById(R.id.thumb_button);

        des1=rootview.findViewById(R.id.cashproduct);
        des2=rootview.findViewById(R.id.organicproduct);
        des3=rootview.findViewById(R.id.freshproduct);

        buyNowLists=new ArrayList<>();
        spinner=rootview.findViewById(R.id.spinnerQuantity);


        c7=rootview.findViewById(R.id.view_allproduct);
        c8=rootview.findViewById(R.id.add_a_review);
        c9=rootview.findViewById(R.id.view_allviewed);
        c11=rootview.findViewById(R.id.view_allliked);
        c10=rootview.findViewById(R.id.view_allfav);

        name=rootview.findViewById(R.id.product_name);
        price=rootview.findViewById(R.id.price);

        r1=rootview.findViewById(R.id.radio);
        r2=rootview.findViewById(R.id.radio1);
        r3=rootview.findViewById(R.id.radio2);

        buy=rootview.findViewById(R.id.buynow);
        addtocart=rootview.findViewById(R.id.addtocart);
        e1=rootview.findViewById(R.id.deliverypincode);
        uploadPincode=new UploadPincode();

        images_slider = rootview.findViewById(R.id.image_product_page_slider);
        pages_dots = rootview.findViewById(R.id.image_product_page_dots);


        name=rootview.findViewById(R.id.product_name);
        price=rootview.findViewById(R.id.price);

        similarproducts=new ArrayList<UploadProductData2>();
        c1=rootview.findViewById(R.id.similar_product_item_1);
        c2=rootview.findViewById(R.id.similar_product_item_2);
        c3=rootview.findViewById(R.id.similar_product_item_3);
        c4=rootview.findViewById(R.id.similar_product_item_4);
        c5=rootview.findViewById(R.id.similar_product_item_5);
        c6=rootview.findViewById(R.id.similar_product_item_6);

        i1=rootview.findViewById(R.id.similar_product_item_1_image);
        n1=rootview.findViewById(R.id.similar_product_item_1_name);
        p1=rootview.findViewById(R.id.similar_product_item_1_price);

        i2=rootview.findViewById(R.id.similar_product_item_2_image);
        n2=rootview.findViewById(R.id.similar_product_item_2_name);
        p2=rootview.findViewById(R.id.similar_product_item_2_price);

        i3=rootview.findViewById(R.id.similar_product_item_3_image);
        n3=rootview.findViewById(R.id.similar_product_item_3_name);
        p3=rootview.findViewById(R.id.similar_product_item_3_price);

        i4=rootview.findViewById(R.id.similar_product_item_4_image);
        n4=rootview.findViewById(R.id.similar_product_item_4_name);
        p4=rootview.findViewById(R.id.similar_product_item_4_price);

        i5=rootview.findViewById(R.id.similar_product_item_5_image);
        n5=rootview.findViewById(R.id.similar_product_item_5_name);
        p5=rootview.findViewById(R.id.similar_product_item_5_price);

        i6=rootview.findViewById(R.id.similar_product_item_6_image);
        n6=rootview.findViewById(R.id.similar_product_item_6_name);
        p6=rootview.findViewById(R.id.similar_product_item_6_price);



        Bundle b=getArguments();
        final String product_name=b.getString("name_key");

        final ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,quantity);
        spinner.setAdapter(adapter);

        prreference= FirebaseDatabase.getInstance().getReference("PotsProductPictures");

        prreference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data:dataSnapshot.getChildren())
                {
                    final UploadProductData2 uploadData=data.getValue(UploadProductData2.class);
                    if(uploadData.getName_product().equals(product_name))
                    {
                        viewedproduct=uploadData;
                        name.setText(uploadData.getName_product());
                      pricei=uploadData.getPrice_product();
                        text.setText(uploadData.getDescription());
                        deliverycart=uploadData.getDelivery();
                        descriptioncart=uploadData.getDescription();
                        urlcart=uploadData.getUrl1();
                        item1.setTypeface(null, Typeface.BOLD);
                        item1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                item1.setTypeface(null, Typeface.BOLD);
                                item2.setTypeface(null, Typeface.NORMAL);
                                item3.setTypeface(null, Typeface.NORMAL);
                                text.setText(uploadData.getDescription());
                            }
                        });
                        item2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                item2.setTypeface(null, Typeface.BOLD);
                                item1.setTypeface(null, Typeface.NORMAL);
                                item3.setTypeface(null, Typeface.NORMAL);
                                text.setText(uploadData.getDelivery());

                            }
                        });
                        item3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                item3.setTypeface(null, Typeface.BOLD);
                                item1.setTypeface(null, Typeface.NORMAL);
                                item2.setTypeface(null, Typeface.NORMAL);
                                text.setText(uploadData.getCare());

                            }
                        });

                        String[] images={uploadData.getUrl1(),uploadData.getUrl2(),
                                uploadData.getUrl3(),uploadData.getUrl4()};
                        Slider_Pager_Adapter_product2 sliderPagerAdapterProduct;

                        sliderPagerAdapterProduct = new Slider_Pager_Adapter_product2(getActivity(), images);

                        images_slider.setAdapter(sliderPagerAdapterProduct);

                        images_slider.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                            @Override
                            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                            }
                            @Override
                            public void onPageSelected(int position) {
                                addBottomDots(position);
                            }
                            @Override
                            public void onPageScrollStateChanged(int state) {
                            }
                        });

                        Integer p=Integer.parseInt(uploadData.getPrice_product());
                        p=p-(p/10);

                        String st=" ₹ "+uploadData.getPrice_product().toString()+"/-";

                        price.setText("₹ "+p.toString()+"/-");

                        SpannableString spannableString=new SpannableString(st);

                        StrikethroughSpan strikethroughSpan=new StrikethroughSpan();


                            if(spannableString.length()==3)
                            {
                                spannableString.setSpan(strikethroughSpan,0,3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                                offerprice.setText(spannableString);

                            }
                            else

                            if(spannableString.length()==4)
                            {
                                spannableString.setSpan(strikethroughSpan,0,4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                                offerprice.setText(spannableString);

                            }
                            else
                            if(spannableString.length()==5)
                            {
                                spannableString.setSpan(strikethroughSpan,0,5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                                offerprice.setText(spannableString);

                            }
                            else
                            if(spannableString.length()==6)
                            {
                                spannableString.setSpan(strikethroughSpan,0,6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                                offerprice.setText(spannableString);

                            }
                            else
                            if(spannableString.length()==7)
                            {
                                spannableString.setSpan(strikethroughSpan,0,7, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                                offerprice.setText(spannableString);

                            }
                            else
                            if(spannableString.length()==8)
                            {
                                spannableString.setSpan(strikethroughSpan,0,8, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                                offerprice.setText(spannableString);

                            }
                            else
                            if(spannableString.length()==9)
                            {
                                spannableString.setSpan(strikethroughSpan,0,9, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                                offerprice.setText(spannableString);

                            }
                            else
                            if(spannableString.length()==10)
                            {
                                spannableString.setSpan(strikethroughSpan,0,10, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                                offerprice.setText(spannableString);

                            }


                        if(!emailID.equals(""))
                        {
                            viewedref= FirebaseDatabase.getInstance().getReference("Viewed").child(username+phone).child(product_name);
                            viewedref.setValue(viewedproduct);
                        }

                        addBottomDots(0);

                    }
                    if(!(uploadData.getName_product().equals(product_name)))
                        similarproducts.add(uploadData);
                }
                Collections.shuffle(similarproducts);
                UploadProductData2 uploadProductData;
                uploadProductData=similarproducts.get(1);

                Glide.with(getActivity()).load(uploadProductData.getUrl1()).into(i1);
                n1.setText(uploadProductData.getName_product());
                p1.setText("₹ "+uploadProductData.getPrice_product());

                uploadProductData=similarproducts.get(3);

                Glide.with(getActivity()).load(uploadProductData.getUrl1()).into(i2);
                n2.setText(uploadProductData.getName_product());
                p2.setText("₹ "+uploadProductData.getPrice_product());

                uploadProductData=similarproducts.get(2);

                Glide.with(getActivity()).load(uploadProductData.getUrl1()).into(i3);
                n3.setText(uploadProductData.getName_product());
                p3.setText("₹ "+uploadProductData.getPrice_product());

                uploadProductData=similarproducts.get(4);

                Glide.with(getActivity()).load(uploadProductData.getUrl1()).into(i4);
                n4.setText(uploadProductData.getName_product());
                p4.setText("₹ "+uploadProductData.getPrice_product());

                uploadProductData=similarproducts.get(3);

                Glide.with(getActivity()).load(uploadProductData.getUrl1()).into(i5);
                n5.setText(uploadProductData.getName_product());
                p5.setText("₹ "+uploadProductData.getPrice_product());

                uploadProductData=similarproducts.get(0);

                Glide.with(getActivity()).load(uploadProductData.getUrl1()).into(i6);
                n6.setText(uploadProductData.getName_product());
                p6.setText("₹ "+uploadProductData.getPrice_product());

                pd.dismiss();

            }


            @Override
            public void onCancelled(@NotNull DatabaseError databaseError) {

            }
        });


        heartref= FirebaseDatabase.getInstance().getReference("Favourites").child(username+phone)
                .child(product_name);
        heartref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    heart.setLiked(true);
                }
            }

            @Override
            public void onCancelled(@NotNull DatabaseError databaseError) {

            }
        });

        likeref= FirebaseDatabase.getInstance().getReference("Liked").child(username+phone)
                .child(product_name);
        likeref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                 likeButton.setLiked(true);
                }
            }

            @Override
            public void onCancelled(@NotNull DatabaseError databaseError) {

            }
        });

        Glide.with(getActivity())
                .load("https://firebasestorage.googleapis.com/v0/b/themaalishop-69ac4.appspot.com/o/freshproducts.jpg?alt=media&token=c31bc25d-a86e-4fdc-a75e-6cd4519f9387").
                into(des3);
        Glide.with(getActivity()).
                load("https://firebasestorage.googleapis.com/v0/b/themaalishop-69ac4.appspot.com/o/organic3.jpg?alt=media&token=17e4cf39-ce50-4e65-8a41-e10cc0b6708d").
                into(des2);
        Glide.with(getActivity()).load("https://firebasestorage.googleapis.com/v0/b/themaalishop-69ac4.appspot.com/o/cashondelivery.png?alt=media&token=be624230-dc3d-41f1-a8f9-9de9dd42b24c")
                .into(des1);

        recyclerView = (RecyclerView)
                rootview.findViewById(R.id.recycler_view);
        //Set RecyclerView type according to intent value
        recyclerView
                .setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));



            //database refrence se data leke arraylist me dalna hai
            recreference= FirebaseDatabase.getInstance().getReference("Reviews").child(product_name);

            recreference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                    ArrayList<ItemModel> arrayList=new ArrayList<>();
                    if(dataSnapshot.exists())
                    {
                        for(DataSnapshot data:dataSnapshot.getChildren())
                        {
                            ItemModel uploadData=data.getValue(ItemModel.class);
                            arrayList.add(uploadData);
                        }
                        RvAdapter rvAdapter = new RvAdapter(getActivity(), arrayList);
                        recyclerView.setAdapter(rvAdapter);// set adapter on recyclerview
                        rvAdapter.notifyDataSetChanged();// Notify the adapter

                        reviewproduct.setText(rvAdapter.getreviewsum());
                    }

                }

                @Override
                public void onCancelled(@NotNull DatabaseError databaseError) {

                }
            });

        if(emailID.equals(""))
        {
            LinearLayout linearLayout4;
            linearLayout4=(LinearLayout) rootview.findViewById(R.id.imageslinear);
            LinearLayout.LayoutParams layoutParams2= (LinearLayout.LayoutParams) linearLayout4.getLayoutParams();
            layoutParams2.setMargins(0,-780,0,10);
            linearLayout4.setLayoutParams(layoutParams2);

        }
            heart.setOnLikeListener(new OnLikeListener() {
                @Override
                public void liked(LikeButton likeButton) {

                    if(!emailID.equals(""))
                    {
                        heartref= FirebaseDatabase.getInstance().getReference("Favourites").child(username+phone)
                                .child(product_name);
                        heartref.setValue(viewedproduct);

                        View toastview=getLayoutInflater().inflate(R.layout.addedtofav,null );
                        Toast toast=new Toast(getActivity().getApplicationContext());
                        toast.setView(toastview);
                        toast.setDuration(Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    else
                    {
                        getFragmentManager().beginTransaction().setCustomAnimations(R.anim.animate_slide_in_left,
                                R.anim.animate_slide_out_right)
                                .replace(R.id.main_fragment_container, new LoginFragment(), "LoginFragment")
                                .commit();
                        View toastview=getLayoutInflater().inflate(R.layout.loginfirst,null );
                        Toast toast=new Toast(getActivity().getApplicationContext());
                        toast.setView(toastview);
                        toast.setDuration(Toast.LENGTH_SHORT);
                        toast.show();
                    }

                }

                @Override
                public void unLiked(LikeButton likeButton) {

                    if(!emailID.equals(""))
                    {
                        heartref= FirebaseDatabase.getInstance().getReference("Favourites").child(username+phone)
                                .child(product_name);
                        heartref.removeValue();

                        View toastview=getLayoutInflater().inflate(R.layout.removefromfav,null );
                        Toast toast=new Toast(getActivity().getApplicationContext());
                        toast.setView(toastview);
                        toast.setDuration(Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    else
                    {
                        getFragmentManager().beginTransaction().setCustomAnimations(R.anim.animate_slide_in_left,
                                R.anim.animate_slide_out_right)
                                .replace(R.id.main_fragment_container, new LoginFragment(), "LoginFragment")
                                .commit();
                        View toastview=getLayoutInflater().inflate(R.layout.loginfirst,null );
                        Toast toast=new Toast(getActivity().getApplicationContext());
                        toast.setView(toastview);
                        toast.setDuration(Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
            });

            likeButton.setOnLikeListener(new OnLikeListener() {
                @Override
                public void liked(LikeButton likeButton) {


                    if(!emailID.equals(""))
                    {
                        likeref= FirebaseDatabase.getInstance().getReference("Liked").
                                child(username+phone).child(product_name);
                        likeref.setValue(viewedproduct);

                        View toastview=getLayoutInflater().inflate(R.layout.addedtoliked,null );
                        Toast toast=new Toast(getActivity().getApplicationContext());
                        toast.setView(toastview);
                        toast.setDuration(Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    else
                    {
                        getFragmentManager().beginTransaction().setCustomAnimations(R.anim.animate_slide_in_left,
                                R.anim.animate_slide_out_right)
                                .replace(R.id.main_fragment_container, new LoginFragment(), "LoginFragment")
                                .commit();
                        View toastview=getLayoutInflater().inflate(R.layout.loginfirst,null );
                        Toast toast=new Toast(getActivity().getApplicationContext());
                        toast.setView(toastview);
                        toast.setDuration(Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
                @Override
                public void unLiked(LikeButton likeButton) {
                    if(!emailID.equals(""))
                    {
                        likeref= FirebaseDatabase.getInstance().getReference("Liked").
                                child(username+phone).child(product_name);
                        likeref.removeValue();

                        View toastview=getLayoutInflater().inflate(R.layout.removefromliked,null );
                        Toast toast=new Toast(getActivity().getApplicationContext());
                        toast.setView(toastview);
                        toast.setDuration(Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    else
                    {
                        getFragmentManager().beginTransaction().setCustomAnimations(R.anim.animate_slide_in_left,
                                R.anim.animate_slide_out_right)
                                .replace(R.id.main_fragment_container, new LoginFragment(), "LoginFragment")
                                .commit();
                        View toastview=getLayoutInflater().inflate(R.layout.loginfirst,null );
                        Toast toast=new Toast(getActivity().getApplicationContext());
                        toast.setView(toastview);
                        toast.setDuration(Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
            });


        if(!username.equals(""))
            {


                layout.setVisibility(View.VISIBLE);
                layout2.setVisibility(View.VISIBLE);
                layout3.setVisibility(View.VISIBLE);

                recyclerView2 = (RecyclerView)
                        rootview.findViewById(R.id.recycler_view2);
                //Set RecyclerView type according to intent value
                recyclerView2
                        .setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));


                //database refrence se data leke arraylist me dalna hai
                viewedref2= FirebaseDatabase.getInstance().getReference("Viewed").child(username+phone);

                viewedref2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                        final ArrayList<UploadProductData2> arrayList2=new ArrayList<>();
                        if(dataSnapshot.exists())
                        {
                            for(DataSnapshot data:dataSnapshot.getChildren())
                            {
                                UploadProductData2 uploadData=data.getValue(UploadProductData2.class);
                                arrayList2.add(uploadData);
                            }
                            RvAdapter2 rvAdapter = new RvAdapter2(new Myclick() {
                                @Override
                                public void Onmyclcik(View view, int position) {

                                    HomeFragment_recently_viewed_product homeFragment=new HomeFragment_recently_viewed_product();
                                    String name=arrayList2.get(position).getName_product();

                                    Bundle b=new Bundle();
                                    b.putString("name_key",name);
                                    homeFragment.setArguments(b);

                                    getFragmentManager().
                                            beginTransaction().addToBackStack("").
                                            replace(R.id.main_fragment_container,homeFragment).commit();

                                }
                            },getActivity(),arrayList2);

                            recyclerView2.setAdapter(rvAdapter);// set adapter on recyclerview
                            rvAdapter.notifyDataSetChanged();// Notify the adapter
                        }
                    }

                    @Override
                    public void onCancelled(@NotNull DatabaseError databaseError) {
                    }
                });


                recyclerView4 = (RecyclerView)
                        rootview.findViewById(R.id.recycler_view4);
                //Set RecyclerView type according to intent value
                recyclerView4
                        .setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));


                //database refrence se data leke arraylist me dalna hai
                likeref2= FirebaseDatabase.getInstance().getReference("Liked").child(username+phone);

                likeref2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                        final ArrayList<UploadProductData2> arrayList2=new ArrayList<>();
                        if(dataSnapshot.exists())
                        {
                            for(DataSnapshot data:dataSnapshot.getChildren())
                            {
                                UploadProductData2 uploadData=data.getValue(UploadProductData2.class);
                                arrayList2.add(uploadData);
                            }
                            Collections.shuffle(arrayList2);
                            RvAdapter2 rvAdapter = new RvAdapter2(new Myclick() {
                                @Override
                                public void Onmyclcik(View view, int position) {

                                    HomeFragment_recentlylikedproduct homeFragment=new HomeFragment_recentlylikedproduct();
                                    String name=arrayList2.get(position).getName_product();

                                    Bundle b=new Bundle();
                                    b.putString("name_key",name);
                                    homeFragment.setArguments(b);

                                    getFragmentManager().
                                            beginTransaction().addToBackStack("").
                                            replace(R.id.main_fragment_container,homeFragment).commit();

                                }
                            },getActivity(),arrayList2);

                            recyclerView4.setAdapter(rvAdapter);// set adapter on recyclerview
                            rvAdapter.notifyDataSetChanged();// Notify the adapter
                        }
                    }

                    @Override
                    public void onCancelled(@NotNull DatabaseError databaseError) {
                    }
                });




                recyclerView3 = (RecyclerView)
                        rootview.findViewById(R.id.recycler_view3);
                //Set RecyclerView type according to intent value
                recyclerView3
                        .setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

                //database refrence se data leke arraylist me dalna hai
                heartref2= FirebaseDatabase.getInstance().getReference("Favourites").child(username+phone);

                heartref2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                        final ArrayList<UploadProductData2> arrayList2=new ArrayList<>();
                        if(dataSnapshot.exists())
                        {
                            for(DataSnapshot data:dataSnapshot.getChildren())
                            {
                                UploadProductData2 uploadData=data.getValue(UploadProductData2.class);
                                arrayList2.add(uploadData);
                            }
                            Collections.shuffle(arrayList2);
                            RvAdapter2 rvAdapter = new RvAdapter2(new Myclick() {
                                @Override
                                public void Onmyclcik(View view, int position) {

                                    HomeFragment_Favourites homeFragment=new HomeFragment_Favourites();
                                    String name=arrayList2.get(position).getName_product();

                                    Bundle b=new Bundle();
                                    b.putString("name_key",name);
                                    homeFragment.setArguments(b);

                                    getFragmentManager().
                                            beginTransaction().addToBackStack("").
                                            replace(R.id.main_fragment_container,homeFragment).commit();

                                }
                            },getActivity(),arrayList2);

                            recyclerView3.setAdapter(rvAdapter);// set adapter on recyclerview
                            rvAdapter.notifyDataSetChanged();// Notify the adapter
                        }
                    }

                    @Override
                    public void onCancelled(@NotNull DatabaseError databaseError) {
                    }
                });
            }


        c7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().addToBackStack("")
                        .replace(R.id.main_fragment_container,new PotsFragment()).commit();

            }
        });


        c8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!emailID.equals("") )
                {
                    Add_A_Review_Fragment add_a_review_fragment=new Add_A_Review_Fragment();
                    String ref=name.getText().toString();
                    Bundle bundle=new Bundle();
                    bundle.putString("ref5_key",ref);
                    add_a_review_fragment.setArguments(bundle);
                    getFragmentManager().beginTransaction().addToBackStack("")
                            .replace(R.id.main_fragment_container,add_a_review_fragment).commit();

                }
                else
                {
                    getFragmentManager().beginTransaction().setCustomAnimations(R.anim.animate_slide_in_left,
                            R.anim.animate_slide_out_right)
                            .replace(R.id.main_fragment_container, new LoginFragment(), "LoginFragment")
                            .commit();
                    View toastview=getLayoutInflater().inflate(R.layout.loginfirst,null );
                    Toast toast=new Toast(getActivity().getApplicationContext());
                    toast.setView(toastview);
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        c9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().addToBackStack("")
                        .replace(R.id.main_fragment_container,new Recently_Viewed_Fragment()).commit();
            }
        });

        c10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().addToBackStack("")
                        .replace(R.id.main_fragment_container,new Favourites_Fragment()).commit();
            }
        });

        c11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().addToBackStack("")
                        .replace(R.id.main_fragment_container,new Recently_Liked_Fragment()).commit();
            }
        });

        addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deliveryreference= FirebaseDatabase.getInstance().getReference("DeliveryPincodes");
                deliveryreference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NotNull DataSnapshot dataSnapshot) {

                        if(!emailID.equals(""))
                        {
                            String e=e1.getText().toString();
                            uploadPincode=dataSnapshot.getValue(UploadPincode.class);

                            arraylist=uploadPincode.getStringArrayList();

                            if(arraylist.contains(e)) {

                                String size;
                                cartreference= FirebaseDatabase.getInstance().getReference("CartDetails");

                                CartUpload uploadData=new CartUpload();
                                uploadData.setName_product(name.getText().toString());
                                uploadData.setUrl1(urlcart);
                                uploadData.setQty(Integer.parseInt(spinner.getSelectedItem().toString()));
                                if(r1.isChecked())
                                {
                                    size=r1.getText().toString();
                                }else {
                                    if (r2.isChecked()) {
                                        size = r2.getText().toString();
                                    } else {
                                        size = r3.getText().toString();
                                    }
                                }
                                uploadData.setDelivery(deliverycart);
                                uploadData.setDescription(descriptioncart);
                                uploadData.setSize(size);
                                uploadData.setPrice_product(pricei);
                                String childID=cartreference.push().getKey();
                                cartreference.child(username+phone).child(childID).setValue(uploadData);
                                getFragmentManager().
                                        beginTransaction().addToBackStack("").
                                        replace(R.id.main_fragment_container,new CartFragment()).commit();

                                View toastview=getLayoutInflater().inflate(R.layout.customtoast,null );
                                Toast toast=new Toast(getActivity().getApplicationContext());
                                toast.setView(toastview);
                                toast.setDuration(Toast.LENGTH_SHORT);
                                toast.show();
                            }
                            else {

                                View toastview=getLayoutInflater().inflate(R.layout.notavailable,null );
                                Toast toast=new Toast(getActivity().getApplicationContext());
                                toast.setView(toastview);
                                toast.setDuration(Toast.LENGTH_SHORT);
                                toast.show();
                            }

                        }

                        else
                            {
                                getFragmentManager().beginTransaction().setCustomAnimations(R.anim.animate_slide_in_left,
                                        R.anim.animate_slide_out_right)
                                        .replace(R.id.main_fragment_container, new LoginFragment(), "LoginFragment")
                                        .commit();
                                View toastview=getLayoutInflater().inflate(R.layout.loginfirst,null );
                                Toast toast=new Toast(getActivity().getApplicationContext());
                                toast.setView(toastview);
                                toast.setDuration(Toast.LENGTH_SHORT);
                                toast.show();
                            }

                    }

                    @Override
                    public void onCancelled(@NotNull DatabaseError databaseError) {

                    }
                });
            }
        });

        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ConnectivityManager manager= (ConnectivityManager) getActivity().getSystemService(CONNECTIVITY_SERVICE);

                boolean is3g=manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
                boolean iswifi=manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();

                if(!is3g && !iswifi)
                {
                    getFragmentManager().
                            beginTransaction().addToBackStack("").
                            replace(R.id.main_fragment_container,new No_Internet()).commit();

                }

                deliveryreference= FirebaseDatabase.getInstance().getReference("DeliveryPincodes");
                deliveryreference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NotNull DataSnapshot dataSnapshot) {

                        if(!emailID.equals(""))
                        {
                            String e=e1.getText().toString();
                            uploadPincode=dataSnapshot.getValue(UploadPincode.class);

                            arraylist=uploadPincode.getStringArrayList();

                            if(arraylist.contains(e)) {

                                String size;

                                BuyNowList uploadData=new BuyNowList();
                                uploadData.setName_product(name.getText().toString());
                                uploadData.setUrl1(urlcart);
                                uploadData.setQty(Integer.parseInt(spinner.getSelectedItem().toString()));
                                if(r1.isChecked())
                                {
                                    size=r1.getText().toString();
                                }else {
                                    if (r2.isChecked()) {
                                        size = r2.getText().toString();
                                    } else {
                                        size = r3.getText().toString();
                                    }
                                }
                                uploadData.setDelivery(deliverycart);
                                uploadData.setSize(size);
                                uploadData.setPrice_product(pricei);

                                buyNowLists.add(uploadData);
                                Bundle b=new Bundle();
                                b.putInt("total",uploadData.getQty()*Integer.parseInt(uploadData.getPrice_product())+80);
                                b.putParcelableArrayList("arraylist",buyNowLists);
                                BuyNow buyNow=new BuyNow();
                                buyNow.setArguments(b);

                                getFragmentManager().beginTransaction()
                                        .replace(R.id.main_fragment_container, buyNow).addToBackStack("")
                                        .commit();

                                View toastview=getLayoutInflater().inflate(R.layout.customtoast,null );
                                Toast toast=new Toast(getActivity().getApplicationContext());
                                toast.setView(toastview);
                                toast.setDuration(Toast.LENGTH_SHORT);
                                toast.show();
                            }
                            else {

                                View toastview=getLayoutInflater().inflate(R.layout.notavailable,null );
                                Toast toast=new Toast(getActivity().getApplicationContext());
                                toast.setView(toastview);
                                toast.setDuration(Toast.LENGTH_SHORT);
                                toast.show();
                            }

                        }

                        else
                        {
                            getFragmentManager().beginTransaction().setCustomAnimations(R.anim.animate_slide_in_left,
                                    R.anim.animate_slide_out_right)
                                    .replace(R.id.main_fragment_container, new LoginFragment(), "LoginFragment")
                                    .commit();
                            View toastview=getLayoutInflater().inflate(R.layout.loginfirst,null );
                            Toast toast=new Toast(getActivity().getApplicationContext());
                            toast.setView(toastview);
                            toast.setDuration(Toast.LENGTH_SHORT);
                            toast.show();
                        }

                    }

                    @Override
                    public void onCancelled(@NotNull DatabaseError databaseError) {

                    }
                });
            }
        });

        item1=rootview.findViewById(R.id.no1);
        item2=rootview.findViewById(R.id.no2);
        item3=rootview.findViewById(R.id.no3);
        text=rootview.findViewById(R.id.des);


        e1.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                e1.setBackgroundTintList(getResources().getColorStateList(R.color.green));
            }
        });

        c1.setOnClickListener(new View.OnClickListener() {
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

        c1.setAnimation(animation);



        c2.setOnClickListener(new View.OnClickListener() {
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

        c2.setAnimation(animation);


        c3.setOnClickListener(new View.OnClickListener() {
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

        c3.setAnimation(animation);



        c4.setOnClickListener(new View.OnClickListener() {
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

        c4.setAnimation(animation);


        c5.setOnClickListener(new View.OnClickListener() {
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

        c5.setAnimation(animation);




        c6.setOnClickListener(new View.OnClickListener() {
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

        c6.setAnimation(animation);



        timer = new Timer();
        scheduleSlider();
        return rootview;
    }



    public void scheduleSlider() {

        final Handler handler = new Handler();

        final Runnable update = new Runnable() {
            public void run() {
                if (page_position == 3) {
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



    public void addBottomDots(int currentPage) {
        dots = new TextView[4];

        pages_dots.removeAllViews();
        pages_dots.setPadding(0, 0, 0, 20);
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(getContext());
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(Color.parseColor("#9f9f9f")); // un selected
            pages_dots.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(Color.parseColor("#2f383a")); // selected
    }

    @Override
    public void onPause() {
        timer.cancel();
        super.onPause();
    }


}
