package com.example.movie_watchlist_app;

import android.os.Bundle;
import android.widget.Button;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button btnAddMovie, btnWatchlist, btnLogout;
    DatabaseHelper dbHelper;   // optional but good practice

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize DB
        dbHelper = new DatabaseHelper(this);

        btnAddMovie = findViewById(R.id.btnAddMovie);
        btnWatchlist = findViewById(R.id.btnWatchlist);
        btnLogout = findViewById(R.id.btnLogout);

        // Go Add Movie Screen
        btnAddMovie.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, AddMovieActivity.class));
        });

        // Go Watchlist Screen
        btnWatchlist.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, WatchlistActivity.class));
        });

        // Logout → Back to Login
        btnLogout.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        });
    }
}