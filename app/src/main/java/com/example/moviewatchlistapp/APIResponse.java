package com.example.movie_watchlist_app;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class APIResponse {
    @SerializedName("status")
    private String status;
    @SerializedName("data")
    private Data data;

    public Data getData() { return data; }

    public static class Data {
        @SerializedName("movies")
        private List<Movie> movies;

        public List<Movie> getMovies() { return movies; }
    }
}