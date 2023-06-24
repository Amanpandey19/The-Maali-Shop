package com.aman.apps.aman.Adapters;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.aman.apps.aman.CartUpload;
import com.aman.apps.aman.R;
import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

import java.util.ArrayList;

public class Myadapter extends ArrayAdapter<String> {
    Activity activity;
    Integer sum;
    String username,phone;
    TextView Q;
    DatabaseReference dbreference;
    ArrayList<CartUpload> arrayList;

    public Myadapter(Activity activity, ArrayList<CartUpload> arrayList,String u,String p)
    {
        super(activity,R.layout.customlayout);
        this.activity=activity;
        this.arrayList=arrayList;
        username=u;
        phone=p;
    }

    public Integer getArrayList() {
        sum=0;
        Integer si=0;
        for(int i=0;i<arrayList.size();i++)
        {
            try{
                si=Integer.parseInt(arrayList.get(i).getPrice_product())*arrayList.get(i).getQty();
                sum=sum+si.intValue();
            }catch (Exception e)
            {

            }
        }
        return sum;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @NotNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater= (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v=inflater.inflate(R.layout.customlayout,null);
        TextView textView=v.findViewById(R.id.namecart);
        ImageView imageView=v.findViewById(R.id.imageviewcart);
        final TextView textView1=v.findViewById(R.id.sizecart);
        TextView textView2=v.findViewById(R.id.pricecart);
        TextView textView4=v.findViewById(R.id.deliverycart);

        Q=v.findViewById(R.id.productqty);
        CardView c1=v.findViewById(R.id.removeitem);
        final CartUpload cartdata =arrayList.get(position);

        String name=cartdata.getName_product();
        String url=cartdata.getUrl1();
        String Size=cartdata.getSize();
        Integer qty=cartdata.getQty();
        Integer price=Integer.parseInt(cartdata.getPrice_product());

        textView.setText(name);
        textView1.setText("Size: "+Size);
        textView2.setText("₹ "+price*qty);
        textView4.setText("Delivery: " +cartdata.getDelivery());
        Q.setText("Qty : "+cartdata.getQty());
        Glide.with(activity).load(url).into(imageView);



        c1.setOnClickListener(
                new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String ch=arrayList.get(position).getName_product();
                final String ch1=arrayList.get(position).getSize();
                final Integer ch3=arrayList.get(position).getQty();
                arrayList.remove(position);
                ProgressDialog pd=new ProgressDialog(activity);
                pd.setMessage("Wait");
                pd.show();

                dbreference= FirebaseDatabase.getInstance().getReference("CartDetails").child(username+phone);
                int i=0;
                dbreference.addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NotNull DataSnapshot dataSnapshot) {

                        for(DataSnapshot data:dataSnapshot.getChildren())
                        {
                            CartUpload uploadData=data.getValue(CartUpload.class);
                            if(uploadData.getName_product().equals(ch) &&
                                uploadData.getSize().equals(ch1) &&
                                 uploadData.getQty().equals(ch3))
                            {
                                String key =data.getKey();
                                dbreference.child(key).removeValue();
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NotNull DatabaseError databaseError) {

                    }
                });
                arrayList.clear();
                Myadapter.this.notifyDataSetChanged();
                pd.dismiss();
            }
        });

        /*
        button.setOnClickListener(new ElegantNumberButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                String q=button.getNumber();
                Log.e("NUM",q);
                Integer qty=Integer.parseInt(q);
                Toast.makeText(getContext(), ""+qty, Toast.LENGTH_SHORT).show();
            }
        });
*/
        return v;
    }
}
