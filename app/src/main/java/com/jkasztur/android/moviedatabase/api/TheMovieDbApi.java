package com.jkasztur.android.moviedatabase.api;

import com.jkasztur.android.moviedatabase.model.Movie;
import com.jkasztur.android.moviedatabase.model.Movies;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TheMovieDbApi {

    @GET("movie/changes")
    Call<Movies> getMovies(@Query("api_key") String apiKey,
                           @Query("start_date") String startDate,
                           @Query("end_date") String endDate);


    @GET("movie/{id}")
    Call<Movie> getMovieDetails(@Path("id") String movieId,
                                @Query("api_key") String apiKey);
}
