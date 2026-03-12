package com.example.moviewatchlistapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIService {
    @GET("list_movies.json")
    Call<APIResponse> getMovies(@Query("limit") int limit, @Query("sort_by") String sortBy);
}
