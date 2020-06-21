package com.mycompany.mobile.android.traveljournalapp.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Main {

    @SerializedName("temp")
    @Expose
    private double temp ;

    public double getTemp() {
        return temp;
    }
}
