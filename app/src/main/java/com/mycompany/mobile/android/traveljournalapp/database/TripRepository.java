package com.mycompany.mobile.android.traveljournalapp.database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class TripRepository {
    private TripDao tripDao ;
    private LiveData<List<Trip>> allTrips ;

    public TripRepository(Application application){
        TripRoomDb database = TripRoomDb.getInstance(application.getApplicationContext());
        tripDao = database.tripDao();
        allTrips = tripDao.getTrips();
    }

    public void insert(final Trip trip){
        new InsertAsyncTask(tripDao).execute(trip);
    }

    public void delete(final Trip trip){
        new DeleteAsyncTask(tripDao).execute(trip);
    }

    public LiveData<List<Trip>> getTrips(){
        return allTrips;
    }

    public void deleteAll(){
        new DeleteAllAsyncTask(tripDao).execute();
    }

    public void update(Trip trip){
        new UpdateAsyncTask(tripDao).execute(trip);
    }


    private class InsertAsyncTask extends AsyncTask<Trip,Void,Void>{
        private TripDao tripDao ;

        public InsertAsyncTask(TripDao tripDao){
            this.tripDao = tripDao;
        }

        @Override
        protected Void doInBackground(Trip... trips) {
            tripDao.insert(trips[0]);
            return null;
        }
    }

    private class DeleteAsyncTask extends AsyncTask<Trip,Void,Void>{
        private TripDao tripDao ;

        public DeleteAsyncTask(TripDao tripDao){
            this.tripDao = tripDao;
        }

        @Override
        protected Void doInBackground(Trip... trips) {
            tripDao.delete(trips[0]);
            return null;
        }
    }

    private class DeleteAllAsyncTask extends AsyncTask<Void,Void,Void>{
        private TripDao tripDao ;

        public DeleteAllAsyncTask(TripDao tripDao){
            this.tripDao = tripDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            tripDao.deleteAll();
            return null;
        }
    }

    private class UpdateAsyncTask extends AsyncTask<Trip,Void,Void>{
        private TripDao tripDao ;

        public UpdateAsyncTask(TripDao tripDao){
            this.tripDao = tripDao;
        }

        @Override
        protected Void doInBackground(Trip... trips) {
            tripDao.update(trips[0]);
            return null;
        }
    }

}
