package com.mycompany.mobile.android.traveljournalapp.database;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "trip_table")
public class Trip {
    public static final String EXTRA_TRIP_NAME = "name";
    public static final String EXTRA_TRIP_DESTINATION = "destination";
    public static final String EXTRA_TRIP_PRICE = "price";
    public static final String EXTRA_TRIP_IMAGEURI = "image";
    public static final String EXTRA_TRIP_RATING = "rating";
    public static final String EXTRA_TRIP_TYPE = "type";

    @PrimaryKey(autoGenerate = true)
    private int id  ;

    private String name ;
    private String destination ;
    private String price ;
    private String imageURL ;
    private String type ;

    @ColumnInfo(defaultValue = "false")
    private boolean favorite ;
    private int rating ;

    public Trip(String name,String destination,String price){
        this.name = name ;
        this.destination = destination;
        this.price = price ;
    }

    public Bundle createBundle(){
        Bundle args = new Bundle();

        args.putString(EXTRA_TRIP_NAME,name);
        args.putString(EXTRA_TRIP_DESTINATION,destination);
        args.putString(EXTRA_TRIP_PRICE,price);
        args.putString(EXTRA_TRIP_TYPE,type);
        args.putInt(EXTRA_TRIP_RATING,rating);
        args.putString(EXTRA_TRIP_IMAGEURI,imageURL);

        return args;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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


    @Override
    public boolean equals(@Nullable Object obj) {
        if(obj instanceof Trip){
            Trip trip = (Trip) obj ;

            return trip.name.equals(this.name)
                    && trip.destination.equals(this.destination);
//                    && trip.price.equals(this.price) && trip.imageURL.equals(this.imageURL)
//                    && trip.type.equals(this.type) && trip.favorite == this.favorite
//                    && trip.rating == this.rating ;
        }

        return false ;
    }
}
