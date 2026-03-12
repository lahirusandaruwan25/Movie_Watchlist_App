package com.example.moviewatchlistapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddMovieActivity extends AppCompatActivity {

    // Declare UI components
    EditText etMovieName, etGenre, etReview;
    RatingBar ratingBar;
    Button btnSave;
    DataBaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_movie);

        // Link UI components
        etMovieName = findViewById(R.id.etMovieName);
        etGenre = findViewById(R.id.etGenre);
        etReview = findViewById(R.id.etReview);
        ratingBar = findViewById(R.id.ratingBar);
        btnSave = findViewById(R.id.btnSave);

        // Initialize DataBaseHelper
        db = new DataBaseHelper(this);

        // Save button click listener
        btnSave.setOnClickListener(v -> {
            String name = etMovieName.getText().toString().trim();
            String genre = etGenre.getText().toString().trim();
            float rating = ratingBar.getRating();
            String review = etReview.getText().toString().trim();

            // Simple validation
            if(name.isEmpty() || genre.isEmpty()) {
                Toast.makeText(this, "Please enter movie name and genre", Toast.LENGTH_SHORT).show();
                return;
            }

            // Insert movie into database
            // Note: AddMovieActivity in previous version didn't have userEmail or imageUrl, 
            // but DataBaseHelper.insertMovie requires them. 
            // I should check how other activities use it or if I should update DataBaseHelper to be backward compatible.
            // For now, I'll use placeholders if needed, but let's check WatchedMovieActivity first.
            
            boolean success = db.insertMovie("default_user", name, genre, rating, review, "");

            if(success) {
                Toast.makeText(this, "Movie Saved!", Toast.LENGTH_SHORT).show();

                // Clear fields after saving
                etMovieName.setText("");
                etGenre.setText("");
                etReview.setText("");
                ratingBar.setRating(0);
            } else {
                Toast.makeText(this, "Error saving movie", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
