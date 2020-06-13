package com.mycompany.mobile.android.traveljournalapp;

import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.mycompany.mobile.android.traveljournalapp.database.Trip;

import java.util.List;

public class TripListAdapter extends RecyclerView.Adapter<TripListAdapter.TripViewHolder> {
    private BookmarkListener bookmarkListener;


    private static final String TAG = "TRIPLISTADAPTER";
    private final AsyncListDiffer<Trip> mDiffer = new AsyncListDiffer<Trip>(this,DIFF_CALLBACK);

    private static final DiffUtil.ItemCallback<Trip> DIFF_CALLBACK = new DiffUtil.ItemCallback<Trip>() {
        @Override
        public boolean areItemsTheSame(@NonNull Trip oldItem, @NonNull Trip newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Trip oldItem, @NonNull Trip newItem) {
            return oldItem.equals(newItem);
        }
    };

    @NonNull
    @Override
    public TripViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.trip_item,parent,false);

        return new TripViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TripViewHolder holder, int position) {
        Trip trip = mDiffer.getCurrentList().get(position);
        holder.updateTrip(trip);
    }

    @Override
    public int getItemCount() {
        return mDiffer.getCurrentList().size();
    }

    public void submitList(List<Trip> submitedList){
        mDiffer.submitList(submitedList);
    }

    public void setBookmarkListener(BookmarkListener bookmarkListener){
        this.bookmarkListener = bookmarkListener;
    }



    public class TripViewHolder extends RecyclerView.ViewHolder {
        private TextView tripName ;
        private TextView tripDestination ;
        private TextView tripPrice ;
        private ImageView tripBookmark ;
        private RatingBar tripRating ;
        private ImageView tripImage ;

        public TripViewHolder(@NonNull View itemView) {
            super(itemView);

            tripName = itemView.findViewById(R.id.item_name);
            tripDestination = itemView.findViewById(R.id.item_destination);
            tripPrice = itemView.findViewById(R.id.item_price);
            tripBookmark = itemView.findViewById(R.id.item_bookmark);
            tripRating = itemView.findViewById(R.id.item_ratingBar);
            tripImage = itemView.findViewById(R.id.item_image);

            tripBookmark.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setBookmarkStatus();
                }
            });
        }

    private void updateTrip(Trip trip){
        if(trip.isFavorite()){
            tripBookmark.setImageResource(R.drawable.ic_star_black_filled_24dp);
        }else{
           tripBookmark.setImageResource(R.drawable.ic_star_empty_24dp);
        }
            tripName.setText(trip.getName());
            tripDestination.setText(trip.getDestination());
            tripPrice.setText(trip.getPrice());
            tripRating.setRating(trip.getRating());

        String uri = trip.getImageURL();
        if(uri != null){
            Uri imageUri = Uri.parse(uri);
            tripImage.setImageURI(imageUri);
        }
    }


        private void setBookmarkStatus(){
            int position = getAdapterPosition();
            Trip selectedTrip = mDiffer.getCurrentList().get(position);
            boolean result = bookmarkListener.changeBookmarkStatus(selectedTrip);

            if(!result)  tripBookmark.setImageResource(R.drawable.ic_star_empty_24dp);
            else tripBookmark.setImageResource(R.drawable.ic_star_black_filled_24dp);
        }

    }


    public interface BookmarkListener{
        boolean changeBookmarkStatus(Trip trip);
    }
}
