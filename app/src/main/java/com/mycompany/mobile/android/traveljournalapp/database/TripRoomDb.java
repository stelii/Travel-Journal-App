package com.mycompany.mobile.android.traveljournalapp.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Insert;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Trip.class}, exportSchema = false, version = 1)
public abstract class TripRoomDb extends RoomDatabase {

    private static TripRoomDb instance ;

    public abstract TripDao tripDao();

    public static synchronized TripRoomDb getInstance(final Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),TripRoomDb.class,
                    "trip_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(callback)
                    .build();
        }

        return instance ;
    }

    private static RoomDatabase.Callback callback = new RoomDatabase.Callback(){

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            Trip trip = new Trip("trip1","destination1","399");
            new InsertAsyncTask(instance.tripDao()).execute(trip);
        }
    };

    private static class InsertAsyncTask extends AsyncTask<Trip,Void,Void>{
        private TripDao tripDao ;

        public InsertAsyncTask(TripDao tripDao){
            this.tripDao = tripDao;
        }

        @Override
        protected Void doInBackground(Trip... trips) {
            tripDao.insert(trips[0]);
            return null ;
        }
    }


}
