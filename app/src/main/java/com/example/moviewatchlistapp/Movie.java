package com.example.moviewatchlistapp;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Movie {
    @SerializedName("id")
    private int id;

    @SerializedName("title")
    private String title;

    @SerializedName("release_date")
    private String releaseDate;

    @SerializedName("vote_average")
    private double rating;

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("overview")
    private String summary;

    @SerializedName("genre_ids")
    private List<Integer> genreIds;

    // Getters
    public int getId() { return id; }
    public String getTitle() { return title; }

    public String getYear() {
        if (releaseDate != null && releaseDate.length() >= 4) {
            return releaseDate.substring(0, 4);
        }
        return "N/A";
    }

    public double getRating() { return rating; }

    public String getImageUrl() {
        return "https://image.tmdb.org/t/p/w500" + posterPath;
    }

    public String getSummary() { return summary; }

    public String getGenres() {
        if (genreIds == null || genreIds.isEmpty()) return "N/A";
        // Simple mapping for demonstration, since we don't have the full genre list from TMDB
        StringBuilder genres = new StringBuilder();
        for (int i = 0; i < Math.min(genreIds.size(), 3); i++) {
            genres.append(getGenreName(genreIds.get(i)));
            if (i < Math.min(genreIds.size(), 3) - 1) genres.append(", ");
        }
        return genres.toString();
    }

    private String getGenreName(int id) {
        switch (id) {
            case 28: return "Action";
            case 12: return "Adventure";
            case 16: return "Animation";
            case 35: return "Comedy";
            case 80: return "Crime";
            case 99: return "Documentary";
            case 18: return "Drama";
            case 10751: return "Family";
            case 14: return "Fantasy";
            case 36: return "History";
            case 27: return "Horror";
            case 10402: return "Music";
            case 9648: return "Mystery";
            case 10749: return "Romance";
            case 878: return "Sci-Fi";
            case 10770: return "TV Movie";
            case 53: return "Thriller";
            case 10752: return "War";
            case 37: return "Western";
            default: return "Other";
        }
    }
}