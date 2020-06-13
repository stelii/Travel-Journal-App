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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mycompany.mobile.android.traveljournalapp.database.Trip;
import com.mycompany.mobile.android.traveljournalapp.database.TripViewModel;

import javax.xml.transform.Result;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddEditFragment extends Fragment {
    private EditText nameInput;
    private EditText destinationInput;
    private RadioGroup tripTypeGroup;
    private SeekBar priceInput;
    private RatingBar tripRatingInput;
    private Button pickImageButton;
    private Button saveTripButton;
    private ImageView imagePreview;
    private TextView priceDisplay ;

    private String imageUri ;

    private TripViewModel viewModel;

    public AddEditFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_edit, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
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
                if(saveTrip()) launchHomeFragment();
            }
        });
    }

    private void checkPermission(){
        if(ContextCompat.checkSelfPermission(getContext(),
                        Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},100);
        }else{
            getImage();
        }
    }

    private void getImage(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        startActivityForResult(intent,200);
    }


    private boolean saveTrip(){
        String tripName = nameInput.getText().toString();
        String tripDestination = destinationInput.getText().toString();
        String tripPrice = String.valueOf(priceInput.getProgress());
        int tripRating = tripRatingInput.getNumStars();
        String imageUri = this.imageUri;

        Trip trip = new Trip(tripName,tripDestination,tripPrice);
        trip.setRating(tripRating);
        trip.setImageURL(imageUri);
        viewModel.insert(trip);
        return true ;
    }

    private void launchHomeFragment(){
        getFragmentManager().beginTransaction().replace(R.id.main_activity_frame_layout
                ,new HomeFragment()).commit();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == 100){
            if(grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getImage();
            }
        }else{
            Toast.makeText(getContext(), "Gallery permission denied", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
       if(requestCode == 200 && resultCode == getActivity().RESULT_OK){
           Uri imageUri = data.getData();
           if(imageUri != null){
                getContext().getContentResolver()
                        .takePersistableUriPermission(imageUri,Intent.FLAG_GRANT_READ_URI_PERMISSION);

            this.imageUri = imageUri.toString();
            imagePreview.setImageURI(imageUri);
           }
       }else{
           super.onActivityResult(requestCode,resultCode,data);
       }
    }


}
