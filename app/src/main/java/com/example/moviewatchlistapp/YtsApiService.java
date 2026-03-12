package com.example.moviewatchlistapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface YtsApiService {
    @GET("list_movies.json")
    Call<YtsResponse> getMovies(@Query("limit") int limit, @Query("sort_by") String sortBy);
}
