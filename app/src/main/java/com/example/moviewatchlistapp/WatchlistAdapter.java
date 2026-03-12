package com.example.movie_watchlist_app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.List;

public class WatchlistAdapter extends RecyclerView.Adapter<WatchlistAdapter.WatchlistViewHolder> {

    private List<WatchlistMovie> movieList;
    private DatabaseHelper dbHelper;

    public WatchlistAdapter(List<WatchlistMovie> movieList) {
        this.movieList = movieList;
    }

    @NonNull
    @Override
    public WatchlistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_watchlist, parent, false);
        dbHelper = new DatabaseHelper(parent.getContext());
        return new WatchlistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WatchlistViewHolder holder, int position) {
        WatchlistMovie movie = movieList.get(position);
        holder.tvTitle.setText(movie.getName());
        holder.tvGenre.setText(movie.getGenre());

        // Assuming rating in DB is out of 10 if from Browse, or out of 5 if manual
        // The item_watchlist.xml uses a small RatingBar (5 stars)
        float rating = movie.getRating();
        if (rating > 5) {
            holder.ratingBar.setRating(rating / 2);
        } else {
            holder.ratingBar.setRating(rating);
        }

        holder.tvReview.setText(movie.getReview());

        Glide.with(holder.itemView.getContext())
                .load(movie.getImageUrl())
                .placeholder(android.R.drawable.ic_menu_gallery)
                .error(android.R.drawable.ic_menu_report_image)
                .into(holder.ivPoster);

        holder.btnRemove.setOnClickListener(v -> {
            boolean isDeleted = dbHelper.deleteMovie(movie.getId());
            if (isDeleted) {
                int currentPosition = holder.getAdapterPosition();
                if (currentPosition != RecyclerView.NO_POSITION) {
                    movieList.remove(currentPosition);
                    notifyItemRemoved(currentPosition);
                    notifyItemRangeChanged(currentPosition, movieList.size());
                    Toast.makeText(holder.itemView.getContext(), movie.getName() + " removed from Collection", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public static class WatchlistViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPoster;
        TextView tvTitle, tvGenre, tvReview;
        RatingBar ratingBar;
        ImageButton btnRemove;

        public WatchlistViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPoster = itemView.findViewById(R.id.ivWatchlistPoster);
            tvTitle = itemView.findViewById(R.id.tvWatchlistTitle);
            tvGenre = itemView.findViewById(R.id.tvWatchlistGenre);
            tvReview = itemView.findViewById(R.id.tvWatchlistReview);
            ratingBar = itemView.findViewById(R.id.rbWatchlistRating);
            btnRemove = itemView.findViewById(R.id.btnRemoveFromWatchlist);
        }
    }
}