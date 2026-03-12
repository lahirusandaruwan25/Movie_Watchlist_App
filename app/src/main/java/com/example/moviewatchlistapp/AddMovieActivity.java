package com.example.moviewatchlistapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddMovieActivity extends AppCompatActivity {

    // Declare UI components
    EditText etMovieName, etReview;
    Spinner spGenre;
    RatingBar ratingBar;
    Button btnSave;
    DatabaseHelper db;
    String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_movie);

        // Get logged in user email
        SharedPreferences pref = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        userEmail = pref.getString("email", "");

        // Link UI components
        etMovieName = findViewById(R.id.etMovieName);
        spGenre = findViewById(R.id.spGenre);
        etReview = findViewById(R.id.etReview);
        ratingBar = findViewById(R.id.ratingBar);
        btnSave = findViewById(R.id.btnSave);

        // Initialize DatabaseHelper
        db = new DatabaseHelper(this);

        // Save button click listener
        btnSave.setOnClickListener(v -> {
            String name = etMovieName.getText().toString().trim();
            String genre = spGenre.getSelectedItem().toString();
            float rating = ratingBar.getRating();
            String review = etReview.getText().toString().trim();

            // Simple validation
            if(name.isEmpty()) {
                Toast.makeText(this, "Please enter movie name", Toast.LENGTH_SHORT).show();
                return;
            }

            if(genre.equals("Select Genre")) {
                Toast.makeText(this, "Please select a genre", Toast.LENGTH_SHORT).show();
                return;
            }

            // Insert movie into database with userEmail
            boolean success = db.insertMovie(userEmail, name, genre, rating, review, "");

            if(success) {
                Toast.makeText(this, "Movie Saved!", Toast.LENGTH_SHORT).show();

                // Clear fields after saving
                etMovieName.setText("");
                spGenre.setSelection(0);
                etReview.setText("");
                ratingBar.setRating(0);
            } else {
                Toast.makeText(this, "Error saving movie (Movie might already exist)", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
