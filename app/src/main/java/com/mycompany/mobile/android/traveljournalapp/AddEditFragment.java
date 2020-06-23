package com.mycompany.mobile.android.traveljournalapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mycompany.mobile.android.traveljournalapp.database.Trip;
import com.mycompany.mobile.android.traveljournalapp.database.TripViewModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddEditFragment extends Fragment {
    public static final int PERMISSION_REQUEST = 100;
    public static final int PICK_IMAGE_REQUEST = 200;

    public static final String TAG = "add_edit_fragment";

    private EditText nameInput;
    private EditText destinationInput;
    private RadioGroup tripTypeGroup;
    private RadioButton tripType;
    private SeekBar priceInput;
    private RatingBar tripRatingInput;
    private Button pickImageButton;
    private Button saveTripButton;
    private ImageView imagePreview;
    private TextView priceDisplay;

    private String imageUri;

    private TripViewModel viewModel;

    public AddEditFragment() {
    }

    public static AddEditFragment newInstance(Trip trip) {
        AddEditFragment fragment = new AddEditFragment();
        Bundle args = trip.createBundle();
        fragment.setArguments(args);

        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_edit, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        viewModel =
                new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
                        .getInstance(getActivity().getApplication())).get(TripViewModel.class);

        nameInput = view.findViewById(R.id.add_edit_fragment_name_input);
        destinationInput = view.findViewById(R.id.add_edit_fragment_destination_input);
        tripTypeGroup = view.findViewById(R.id.add_edit_fragment_radio_group);
        priceInput = view.findViewById(R.id.add_edit_fragment_price_input);
        tripRatingInput = view.findViewById(R.id.add_edit_fragment_rating_bar);
        pickImageButton = view.findViewById(R.id.add_edit_fragment_gallery_image_button);
        imagePreview = view.findViewById(R.id.add_edit_fragment_image_preview);
        priceDisplay = view.findViewById(R.id.add_edit_fragment_price_display);

        Bundle args = getArguments();
        if (args != null) {
            Log.d(TAG, "onViewCreated: " + "args aici ???");
            fillPage(args);
        }


        priceInput.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                priceDisplay.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        pickImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermission();
            }
        });


        saveTripButton = view.findViewById(R.id.add_edit_fragment_save_button);
        saveTripButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (saveTrip(view)) launchHomeFragment();
            }
        });
    }

    private void fillPage(Bundle args) {
        nameInput.setText(args.getString(Trip.EXTRA_TRIP_NAME));
        destinationInput.setText(args.getString(Trip.EXTRA_TRIP_DESTINATION));

        if (args.getString(Trip.EXTRA_TRIP_PRICE) != null)
            priceInput.setProgress(args.getInt(Trip.EXTRA_TRIP_NAME));

        priceDisplay.setText(args.getString(Trip.EXTRA_TRIP_PRICE));
        tripRatingInput.setRating(args.getFloat(Trip.EXTRA_TRIP_RATING));
        Log.d(TAG, "fillPage: " + "THE RATING IS: " + args.getInt(Trip.EXTRA_TRIP_RATING));

        this.imageUri = args.getString(Trip.EXTRA_TRIP_IMAGEURI);

        priceInput.setProgress(args.getInt(Trip.EXTRA_TRIP_PRICE));
    }

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST);
        } else {
            getImage();
        }
    }

    private void getImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }


    private boolean saveTrip(View view) {
        String tripName = nameInput.getText().toString();
        String tripDestination = destinationInput.getText().toString();
        String tripPrice = String.valueOf(priceInput.getProgress());
        float tripRating = tripRatingInput.getRating();
        Log.d(TAG, "saveTrip: num star on saveTrip " + tripRating);
        String imageUri = this.imageUri;

        Trip trip = new Trip(tripName, tripDestination, tripPrice);

        int buttonId = tripTypeGroup.getCheckedRadioButtonId();
        tripType = view.findViewById(buttonId);

        if (tripType != null) {
            String type = tripType.getText().toString();
            trip.setType(type);
        }

        trip.setRating(tripRating);
        trip.setImageURI(imageUri);

        Bundle args = getArguments();
        if (args != null) {
            int tripId = args.getInt(Trip.EXTRA_TRIP_ID);
            trip.setId(tripId);

            viewModel.update(trip);
        } else {
            viewModel.insert(trip);
        }

        launchHomeFragment();
        return true;
    }

    private void launchHomeFragment() {
        getFragmentManager().beginTransaction().replace(R.id.main_activity_frame_layout,
                new HomeFragment()).commit();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_REQUEST) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getImage();
            }
        } else {
            Toast.makeText(getContext(), "Gallery permission denied", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == getActivity().RESULT_OK) {
            Uri imageUri = data.getData();
            if (imageUri != null) {
                getContext().getContentResolver()
                        .takePersistableUriPermission(imageUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);

                this.imageUri = imageUri.toString();
                imagePreview.setImageURI(imageUri);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


}
