package com.mycompany.mobile.android.traveljournalapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mycompany.mobile.android.traveljournalapp.database.Trip;
import com.mycompany.mobile.android.traveljournalapp.database.TripViewModel;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements TripListAdapter.BookmarkListener, TripListAdapter.ClickListener {
    private  TripViewModel viewModel;
    public static final String TAG = "HOME FRAGMENT";

    public HomeFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final TripListAdapter tripAdapter = new TripListAdapter();
        tripAdapter.setBookmarkListener(this);
        tripAdapter.setClickListener(this);
        RecyclerView tripList = view.findViewById(R.id.home_fragment_recyclerview);
        tripList.setLayoutManager(new LinearLayoutManager(getContext()));
        tripList.setAdapter(tripAdapter);


        viewModel =
                new ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory
                .getInstance(getActivity().getApplication())).get(TripViewModel.class);
        viewModel.getTrips().observe(getViewLifecycleOwner(), new Observer<List<Trip>>() {
            @Override
            public void onChanged(List<Trip> trips) {
                tripAdapter.submitList(trips);
            }
        });

        FloatingActionButton fab = view.findViewById(R.id.home_fab);
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                launchAddEditFragment();
//                Trip trip = new Trip("Worst Trip","Las Vegas ",
//                        "399");
//                trip.setFavorite(true);
//                viewModel.insert(trip);
            }
        });
    }

    private void launchAddEditFragment(){
        AddEditFragment fragment = new AddEditFragment();
        FragmentManager manager = getFragmentManager();
        manager.beginTransaction()
                .replace(R.id.main_activity_frame_layout,fragment)
                .addToBackStack("add_edit_fragment")
                .commit();

    }

    @Override
    public boolean changeBookmarkStatus(Trip trip) {
       if(!trip.isFavorite()) trip.setFavorite(true);
       else trip.setFavorite(false);

       viewModel.update(trip);

       //returns true if trip is now on favorite list, false otherwise
       return trip.isFavorite();
    }

    @Override
    public void onLongClickItem(Trip trip) {
        AddEditFragment fragment = AddEditFragment.newInstance(trip);
        getFragmentManager().beginTransaction()
                .replace(R.id.main_activity_frame_layout,fragment)
                .addToBackStack("add_edit_fragment")
                .commit();
    }

    @Override
    public void onClickItem(Trip trip) {

    }
}
