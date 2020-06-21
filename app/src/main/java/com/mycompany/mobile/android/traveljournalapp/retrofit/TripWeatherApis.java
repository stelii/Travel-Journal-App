package com.mycompany.mobile.android.traveljournalapp.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TripWeatherApis {

    @GET("weather")
    Call<TripWeather> getWeather(@Query("q") String cityName, @Query("appid") String appId);
}
