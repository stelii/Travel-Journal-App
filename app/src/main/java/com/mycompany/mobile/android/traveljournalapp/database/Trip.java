package com.mycompany.mobile.android.traveljournalapp.database;

import androidx.room.Entity;

@Entity(tableName = "trip_table")
public class Trip {

    private String name ;
    private String destination ;
    private String price ;
    private String imageURL ;

}
