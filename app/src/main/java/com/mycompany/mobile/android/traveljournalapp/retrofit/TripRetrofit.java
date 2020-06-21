package com.mycompany.mobile.android.traveljournalapp.retrofit;

import com.google.gson.Gson;
import com.mycompany.mobile.android.traveljournalapp.database.TripRepository;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TripRetrofit {
    public static final String BASE_URL = "https://openweathermap.org/data/2.5/";
    public static final String APP_ID = "439d4b804bc8187953eb36d2a8c26a02";

    public static Retrofit instance ;

    public static Retrofit getInstance(){
        if(instance == null){
            instance = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return instance ;
    }


}
