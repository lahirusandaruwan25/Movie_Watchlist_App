package com.example.movie_watchlist_app;

public class WatchlistMovie {
    private int id;
    private String name;
    private String genre;
    private float rating;
    private String review;
    private String imageUrl; // New Field

    public WatchlistMovie(int id, String name, String genre, float rating, String review, String imageUrl) {
        this.id = id;
        this.name = name;
        this.genre = genre;
        this.rating = rating;
        this.review = review;
        this.imageUrl = imageUrl;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getGenre() { return genre; }
    public float getRating() { return rating; }
    public String getReview() { return review; }
    public String getImageUrl() { return imageUrl; }
}