package com.example.movie_watchlist_app;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("list_movies.json")
    Call<com.example.movie_watchlist_app.APIResponse> getMovies(@Query("limit") int limit, @Query("sort_by") String sortBy);
}
