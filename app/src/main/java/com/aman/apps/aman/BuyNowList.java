package com.aman.apps.aman;

import android.os.Parcel;
import android.os.Parcelable;

public class BuyNowList implements Parcelable {
    String name_product,url1;

    String price_product;
    String delivery;

    Integer qty;
    String Size_product;

    public BuyNowList(String name_product, String url1, String price_product,
                       String delivery,Integer qty, String Size_product) {
        this.name_product = name_product;
        this.url1 = url1;
        this.qty=qty;
        this.price_product = price_product;
        this.delivery = delivery;
        this.Size_product=Size_product;
    }

    protected BuyNowList(Parcel in) {
        name_product = in.readString();
        url1 = in.readString();
        price_product = in.readString();
        delivery = in.readString();
        if (in.readByte() == 0) {
            qty = null;
        } else {
            qty = in.readInt();
        }
        Size_product = in.readString();
    }

    public static final Creator<BuyNowList> CREATOR = new Creator<BuyNowList>() {
        @Override
        public BuyNowList createFromParcel(Parcel in) {
            return new BuyNowList(in);
        }

        @Override
        public BuyNowList[] newArray(int size) {
            return new BuyNowList[size];
        }
    };

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public String getSize() {
        return Size_product;
    }

    public void setSize(String size_product) {
        Size_product = size_product;
    }

    public BuyNowList() {
    }

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }

    public String getName_product() {
        return name_product;
    }

    public void setName_product(String name_product) {
        this.name_product = name_product;
    }

    public String getUrl1() {
        return url1;
    }

    public void setUrl1(String url1) {
        this.url1 = url1;
    }

    public String getPrice_product() {
        return price_product;
    }

    public void setPrice_product(String price_product) {
        this.price_product = price_product;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name_product);
        dest.writeString(url1);
        dest.writeString(price_product);
        dest.writeString(delivery);
        if (qty == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(qty);
        }
        dest.writeString(Size_product);
    }
}
