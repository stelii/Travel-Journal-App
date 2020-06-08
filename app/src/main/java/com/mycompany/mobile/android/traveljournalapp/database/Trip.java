package com.mycompany.mobile.android.traveljournalapp.database;

import androidx.room.Entity;

@Entity(tableName = "trip_table")
public class Trip {

    private String name ;
    private String destination ;
    private String price ;
    private String imageURL ;
    private String type ;

    private boolean favorite ;
    private int rating ;

    public Trip(String name,String destination,String price){
        this.name = name ;
        this.destination = destination;
        this.price = price ;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
