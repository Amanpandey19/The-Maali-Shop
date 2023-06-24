package com.aman.apps.aman;


 public class ItemModel {

     // Getter and Setter model for recycler view items
     private String name;
     private String userimage;
     private String date;
     private String comment;
     private float rating;

     public ItemModel(String name, String userimage, String date, String comment,float rating) {
         this.name = name;
         this.userimage = userimage;
         this.date = date;
         this.comment = comment;
         this.rating = rating;
     }

     public ItemModel() {
     }

     public String getDate() {
         return date;
     }

     public void setDate(String date) {
         this.date = date;
     }

     public float getRating() {
         return rating;
     }

     public void setRating(float rating) {
         this.rating = rating;
     }

     public String getName() {
         return name;
     }

     public void setName(String name) {
         this.name = name;
     }

     public String getComment() {
         return comment;
     }

     public void setComment(String comment) {
         this.comment = comment;
     }

     public String getUserimage() {
         return userimage;
     }

     public void setUserimage(String userimage) {
         this.userimage = userimage;
     }
 }
