package com.mycompany.mobile.android.traveljournalapp.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TripWeather {

    @SerializedName("name")
    @Expose
    private String cityName ;

    @SerializedName("main")
    @Expose
    private Main main;

    public String getCityName() {
        return cityName;
    }

    public Main getMain() {
        return main;
    }
}
