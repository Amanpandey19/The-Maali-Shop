package com.aman.apps.aman;

public class CartUpload {
    String name_product,url1,url2,url3,url4;

    String price_product;
    String description;
    String delivery;
    String care;
    Integer qty;
    String Size_product;

    public CartUpload(String name_product, String url1, String url2,
                              String url3, String url4, String price_product,
                              String description, String delivery, String care,String Size_product) {
        this.name_product = name_product;
        this.url1 = url1;
        this.url2 = url2;
        this.url3 = url3;
        this.url4 = url4;
        this.price_product = price_product;
        this.description = description;
        this.delivery = delivery;
        this.care = care;
        this.Size_product=Size_product;
    }

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

    public CartUpload() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }

    public String getCare() {
        return care;
    }

    public void setCare(String care) {
        this.care = care;
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

    public String getUrl2() {
        return url2;
    }

    public void setUrl2(String url2) {
        this.url2 = url2;
    }

    public String getUrl3() {
        return url3;
    }

    public void setUrl3(String url3) {
        this.url3 = url3;
    }

    public String getUrl4() {
        return url4;
    }

    public void setUrl4(String url4) {
        this.url4 = url4;
    }

    public String getPrice_product() {
        return price_product;
    }

    public void setPrice_product(String price_product) {
        this.price_product = price_product;
    }

}
