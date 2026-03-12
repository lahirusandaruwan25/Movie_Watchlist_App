package com.example.moviewatchlistapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BrowseMovieActivity extends AppCompatActivity {

    private RecyclerView rvMovies;
    private MovieAdapter adapter;
    private List<Movie> movieList = new ArrayList<>();
    private ProgressBar progressBar;

    // Using TMDB API (The Movie Database) as it's the standard alternative to IMDb/YTS
    // You should replace this with your own API key from https://www.themoviedb.org/settings/api
    private static final String TMDB_API_KEY = "896ee8e5a8f8e5e4ba2e9730985146a7";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_movies);

        rvMovies = findViewById(R.id.rvMovies);
        progressBar = findViewById(R.id.progressBar);

        rvMovies.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MovieAdapter(movieList);
        rvMovies.setAdapter(adapter);

        fetchMovies();
    }

    private void fetchMovies() {
        progressBar.setVisibility(View.VISIBLE);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MovieApiService apiService = retrofit.create(MovieApiService.class);
        Call<MovieResponse> call = apiService.getPopularMovies(TMDB_API_KEY);

        call.enqueue(new Callback<MovieResponse>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null) {
                    movieList.clear();
                    movieList.addAll(response.body().getMovies());
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(BrowseMovieActivity.this, "Failed to load movies. Check API Key.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(BrowseMovieActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
