package com.mycompany.mobile.android.traveljournalapp;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
        if(trip.isFavorite()){
            holder.tripBookmark.setImageResource(R.drawable.ic_star_black_filled_24dp);
        }else{
            holder.tripBookmark.setImageResource(R.drawable.ic_star_empty_24dp);
        }

        holder.tripName.setText(trip.getName());
        holder.tripDestination.setText(trip.getDestination());
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
        private ImageView tripBookmark ;

        public TripViewHolder(@NonNull View itemView) {
            super(itemView);

            tripName = itemView.findViewById(R.id.item_name);
            tripDestination = itemView.findViewById(R.id.item_destination);
            tripBookmark = itemView.findViewById(R.id.item_bookmark);

            tripBookmark.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setBookmarkStatus();
                }
            });
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
