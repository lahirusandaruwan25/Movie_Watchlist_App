package com.example.moviewatchlistapp;


import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movie_watchlist_app.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class WatchedMoviesActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseHelper db;
    List<WatchlistMovie> movieList;
    WatchedMoviesAdapter adapter;
    String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watched_list);

        // Get logged in user email
        SharedPreferences pref = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        userEmail = pref.getString("email", "");

        recyclerView = findViewById(R.id.rvWatchedMovies);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = new DatabaseHelper(this);
        movieList = new ArrayList<>();

        loadWatchedMovies();
    }

    private void loadWatchedMovies() {
        movieList.clear();
        Cursor c = db.getMoviesByUser(userEmail);

        if (c.getCount() == 0) {
            Toast.makeText(this, "No watched movies yet!", Toast.LENGTH_SHORT).show();
        } else {
            while (c.moveToNext()) {
                // Column indices: 0:ID, 1:UserEmail, 2:Name, 3:Genre, 4:Rating, 5:Review, 6:ImageUrl
                String imageUrl = c.getString(6);

                // Show ONLY movies added manually (no ImageUrl)
                if (imageUrl == null || imageUrl.isEmpty()) {
                    int id = c.getInt(0);
                    String name = c.getString(2);
                    String genre = c.getString(3);
                    float rating = c.getFloat(4);
                    String review = c.getString(5);

                    movieList.add(new WatchlistMovie(id, name, genre, rating, review, imageUrl));
                }
            }
        }
        c.close();

        adapter = new WatchedMoviesAdapter(movieList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadWatchedMovies();
    }
}