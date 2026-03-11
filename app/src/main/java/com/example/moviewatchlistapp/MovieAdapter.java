package com.example.movie_watchlist_app;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private List<Movie> movieList;
    private com.example.movie_watchlist_app.DatabaseHelper dbHelper;
    private String userEmail;

    public MovieAdapter(List<Movie> movieList) {
        this.movieList = movieList;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);
        dbHelper = new com.example.movie_watchlist_app.DatabaseHelper(parent.getContext());

        // Get user email from SharedPreferences
        SharedPreferences pref = parent.getContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        userEmail = pref.getString("email", "");

        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = movieList.get(position);
        holder.tvTitle.setText(movie.getTitle());
        holder.tvYear.setText("Year: " + movie.getYear());
        holder.tvRating.setText("Avg Rating: " + movie.getRating() + "/10");

        if (holder.tvGenres != null) {
            holder.tvGenres.setText("Genres: " + movie.getGenres());
            holder.tvGenres.setVisibility(View.VISIBLE);
        }

        Glide.with(holder.itemView.getContext())
                .load(movie.getImageUrl())
                .placeholder(android.R.drawable.ic_menu_gallery)
                .error(android.R.drawable.ic_menu_report_image)
                .into(holder.ivPoster);

        // ✅ Handle "Add to Watchlist" button click
        holder.btnAddToWatchlist.setOnClickListener(v -> {
            String title = movie.getTitle();
            String year = movie.getYear();
            float rating = (float) movie.getRating();
            String imageUrl = movie.getImageUrl();
            String genres = movie.getGenres();

            // Insert into DB with userEmail
            boolean isInserted = dbHelper.insertMovie(userEmail, title, genres, rating, "", imageUrl);
            if (isInserted) {
                Toast.makeText(holder.itemView.getContext(), title + " added to Watchlist!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(holder.itemView.getContext(), title + " is already in your list!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPoster;
        TextView tvTitle, tvYear, tvRating, tvGenres;
        Button btnAddToWatchlist;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPoster = itemView.findViewById(R.id.moviePoster);
            tvTitle = itemView.findViewById(R.id.movieTitle);
            tvYear = itemView.findViewById(R.id.movieYear);
            tvRating = itemView.findViewById(R.id.movieRating);
            tvGenres = itemView.findViewById(R.id.movieGenres);
            btnAddToWatchlist = itemView.findViewById(R.id.btnAddToWatchlist);
        }
    }
}
