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
import android.widget.TextView;

import com.mycompany.mobile.android.traveljournalapp.database.Trip;


public class ReadOnlyFragment extends Fragment {
    private TextView tripName, tripDest, tripWeather;
    private ImageView tripImage;


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

    }
}
