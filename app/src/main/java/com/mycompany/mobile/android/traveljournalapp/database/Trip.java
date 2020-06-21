package com.mycompany.mobile.android.traveljournalapp.database;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "trip_table")
public class Trip {
    public static final String EXTRA_TRIP_ID = "id";
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
    private String imageURI;
    private String type ;

    @ColumnInfo(defaultValue = "false")
    private boolean favorite ;
    private float rating ;

    public Trip(String name,String destination,String price){
        this.name = name ;
        this.destination = destination;
        this.price = price ;
    }

    public Bundle createBundle(){
        Bundle args = new Bundle();

        args.putInt(EXTRA_TRIP_ID,id);
        args.putString(EXTRA_TRIP_NAME,name);
        args.putString(EXTRA_TRIP_DESTINATION,destination);
        args.putString(EXTRA_TRIP_PRICE,price);
        args.putString(EXTRA_TRIP_TYPE,type);
        args.putFloat(EXTRA_TRIP_RATING,rating);
        args.putString(EXTRA_TRIP_IMAGEURI, imageURI);

        return args;
    }

//    public Trip createTrip(Bundle args){
//        int id = args.getInt(EXTRA_TRIP_ID,-1);
//        String name = args.getString(EXTRA_TRIP_NAME);
//        String destination = args.getString(EXTRA_TRIP_DESTINATION);
//        String price = args.getString(EXTRA_TRIP_PRICE,"-1");
//
//
//    }

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

    public String getImageURI() {
        return imageURI;
    }

    public void setImageURI(String imageURI) {
        this.imageURI = imageURI;
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

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }


    @Override
    public boolean equals(@Nullable Object obj) {
        if(obj instanceof Trip){
            Trip trip = (Trip) obj ;

            if (false
                    && trip.type != null) {
                return trip.name.equals(this.name)
                        && trip.destination.equals(this.destination)
                        && trip.imageURI != null ? trip.imageURI.equals(this.imageURI) : trip.type.equals(this.type);
            } else {
                return trip.name.equals(this.name)
                        && trip.destination.equals(this.destination)
                        && trip.imageURI != null ? trip.imageURI.equals(this.imageURI) : false ? trip.rating == this.rating : false;
            }
        }

        return false ;
    }
}
