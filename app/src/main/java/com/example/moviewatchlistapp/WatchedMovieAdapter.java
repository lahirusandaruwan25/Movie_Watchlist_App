package com.example.moviewatchlistapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class WatchedMovieAdapter extends RecyclerView.Adapter<WatchedMovieAdapter.WatchedViewHolder> {

    private List<WatchlistMovie> movieList;
    private DataBaseHelper dbHelper;

    public WatchedMovieAdapter(List<WatchlistMovie> movieList) {
        this.movieList = movieList;
    }

    @NonNull
    @Override
    public WatchedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movies_watched, parent, false);
        dbHelper = new DataBaseHelper(parent.getContext());
        return new WatchedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WatchedViewHolder holder, int position) {
        WatchlistMovie movie = movieList.get(position);
        holder.tvTitle.setText(movie.getName());
        holder.tvGenre.setText(movie.getGenre());
        holder.rbRating.setRating(movie.getRating());
        holder.tvReview.setText(movie.getReview());

        holder.btnDelete.setOnClickListener(v -> {
            boolean isDeleted = dbHelper.deleteMovie(movie.getId());
            if (isDeleted) {
                int currentPosition = holder.getAdapterPosition();
                if (currentPosition != RecyclerView.NO_POSITION) {
                    movieList.remove(currentPosition);
                    notifyItemRemoved(currentPosition);
                    notifyItemRangeChanged(currentPosition, movieList.size());
                    Toast.makeText(holder.itemView.getContext(), movie.getName() + " deleted", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public static class WatchedViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvGenre, tvReview;
        RatingBar rbRating;
        Button btnDelete;

        public WatchedViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvWatchedTitle);
            tvGenre = itemView.findViewById(R.id.tvWatchedGenre);
            tvReview = itemView.findViewById(R.id.tvWatchedReview);
            rbRating = itemView.findViewById(R.id.rbWatchedRating);
            btnDelete = itemView.findViewById(R.id.btnDeleteWatched);
        }
    }
}
