package com.example.moviewatchlistapp;

import android.os.Bundle;
import android.widget.Button;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button btnBrowseMovies, btnAddMovie, btnWatchlist, btnWatchedMovies, btnLogout;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize DB
        dbHelper = new DatabaseHelper(this);

        btnBrowseMovies = findViewById(R.id.btnBrowseMovies);
        btnAddMovie = findViewById(R.id.btnAddMovie);
        btnWatchlist = findViewById(R.id.btnWatchlist);
        btnWatchedMovies = findViewById(R.id.btnWatchedMovies);
        btnLogout = findViewById(R.id.btnLogout);

        // Go Browse Movies Screen (TMDB API)
        btnBrowseMovies.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, BrowseMoviesActivity.class));
        });

        // Go Add Movie Screen
        btnAddMovie.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, AddMovieActivity.class));
        });

        // Go Watchlist Screen
        btnWatchlist.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, WatchlistActivity.class));
        });

        // Go Watched Movies Screen
        btnWatchedMovies.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, WatchedMoviesActivity.class));
        });

        // Logout → Back to Login
        btnLogout.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        });
    }
}
