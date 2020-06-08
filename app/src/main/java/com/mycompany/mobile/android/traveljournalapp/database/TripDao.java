package com.mycompany.mobile.android.traveljournalapp.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TripDao {

    @Insert
    void insert(Trip trip);

    @Delete
    void delete(Trip trip);

    @Query("SELECT * FROM trip_table")
    LiveData<List<Trip>> getTrips();

    @Query("DELETE FROM trip_table")
    void deleteAll();
}
