package com.mycompany.mobile.android.traveljournalapp;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mycompany.mobile.android.traveljournalapp.database.Trip;
import com.mycompany.mobile.android.traveljournalapp.database.TripRepository;
import com.mycompany.mobile.android.traveljournalapp.retrofit.TripRetrofit;
import com.mycompany.mobile.android.traveljournalapp.retrofit.TripWeather;
import com.mycompany.mobile.android.traveljournalapp.retrofit.TripWeatherApis;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class ReadOnlyFragment extends Fragment {
    private TextView tripName, tripDest,
            tripWeather, tripPrice,tripType;
    private ImageView tripImage;
    private RatingBar tripRating ;



    public ReadOnlyFragment() {
        // Required empty public constructor
    }

    public static ReadOnlyFragment newInstance(Trip trip){
        Bundle args = trip.createBundle();

        ReadOnlyFragment fragment = new ReadOnlyFragment();
        fragment.setArguments(args);

        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_read_only, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tripName = view.findViewById(R.id.read_only_fragment_trip_name);
        tripDest = view.findViewById(R.id.read_only_fragment_trip_destination);
        tripWeather = view.findViewById(R.id.read_only_fragment_trip_weather);
        tripImage = view.findViewById(R.id.read_only_fragment_trip_image);
        tripPrice = view.findViewById(R.id.read_only_fragment_trip_price_value);
        tripType = view.findViewById(R.id.read_only_fragment_trip_type_value);
        tripRating = view.findViewById(R.id.read_only_fragment_trip_rating_value);
        displayInfo();


    }

    private void displayInfo(){
        Bundle args = getArguments();
        assert args != null ;

        tripName.setText(args.getString(Trip.EXTRA_TRIP_NAME));
        tripDest.setText(args.getString(Trip.EXTRA_TRIP_DESTINATION));

        String tempUri = args.getString(Trip.EXTRA_TRIP_IMAGEURI);
        Uri uri = Uri.parse(tempUri);
        tripImage.setImageURI(uri);

        tripPrice.setText(args.getString(Trip.EXTRA_TRIP_PRICE));
        tripType.setText(args.getString(Trip.EXTRA_TRIP_TYPE));
        tripRating.setRating(args.getFloat(Trip.EXTRA_TRIP_RATING));


        Retrofit retrofitInstance = TripRetrofit.getInstance();
        TripWeatherApis apis = retrofitInstance.create(TripWeatherApis.class);

        String tripDestination = args.getString(Trip.EXTRA_TRIP_DESTINATION);

        Call<TripWeather> call = apis.getWeather(tripDestination,TripRetrofit.APP_ID);
        call.enqueue(new Callback<TripWeather>() {
            @Override
            public void onResponse(Call<TripWeather> call, Response<TripWeather> response) {
                if(response.isSuccessful()){
                    TripWeather weather = response.body();
                    if (tripWeather != null) {
                        String cityName = weather.getCityName();
                        double cityTemp = weather.getMain().getTemp();

                        String weatherContent = "Weather : " + cityTemp + " °C";
                        tripWeather.setText(weatherContent);
                    }

                }
            }

            @Override
            public void onFailure(Call<TripWeather> call, Throwable t) {
                Toast.makeText(getContext(), "Failed!", Toast.LENGTH_SHORT).show();
            }
        });




    }
}
