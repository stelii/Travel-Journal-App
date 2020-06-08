package com.mycompany.mobile.android.traveljournalapp.database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class TripViewModel extends AndroidViewModel {
    private TripRepository tripRepository ;
    private LiveData<List<Trip>> allTrips ;

    public TripViewModel(@NonNull Application application) {
        super(application);

        tripRepository = new TripRepository(application);
        allTrips = tripRepository.getTrips();
    }

    public void insert(Trip trip){
        tripRepository.insert(trip);
    }

    public void delete(Trip trip){
        tripRepository.delete(trip);
    }

    public LiveData<List<Trip>> getTrips(){
        return allTrips;
    }

    public void deleteAll(){
        tripRepository.deleteAll();
    }

    public void update(Trip trip){
        tripRepository.update(trip);
    }

}
