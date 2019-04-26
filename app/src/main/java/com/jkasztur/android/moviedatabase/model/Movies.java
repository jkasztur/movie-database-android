package com.jkasztur.android.moviedatabase.model;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.BaseObservable;

import java.text.DateFormat;

import android.util.Log;

import com.jkasztur.android.moviedatabase.api.TheMovieDbClient;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Movies extends BaseObservable {
    public static final int BLOCK_COUNT = 30;
    private List<Movie> movieList = new ArrayList<>();

    private MutableLiveData<List<Movie>> movies = new MutableLiveData<>();

    public void addMovie(Movie movie) {
        movieList.add(movie);
    }

    public MutableLiveData<List<Movie>> getMovies() {
        return movies;
    }

    public void addMoreToPresented() {
        List<Movie> temp = new ArrayList<>(movies.getValue());
        int currentlyShown = temp.size();
        for (int i = 0; i < BLOCK_COUNT; i++) {
            if (movieList.size() > currentlyShown + i) {
                temp.add(movieList.get(currentlyShown + i));
            } else {
                break;
            }
        }

        movies.setValue(temp);
    }

    public void fetchList(int inLastDays) {

        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        Calendar cal = Calendar.getInstance();
        String end = dateFormat.format(cal.getTime());
        cal.add(Calendar.DAY_OF_YEAR, -inLastDays);
        String start = dateFormat.format(cal.getTime());

        Callback<Movies> callback = new Callback<Movies>() {
            @Override
            public void onResponse(Call<Movies> call, Response<Movies> response) {
                Movies body = response.body();
                movieList = body.movieList;
                movies.setValue(body.movieList.subList(0, BLOCK_COUNT));
            }

            @Override
            public void onFailure(Call<Movies> call, Throwable t) {
                Log.e("Test", t.getMessage(), t);
            }
        };
        TheMovieDbClient.getApi().getMovies("7b6d38875404b0b7a8c7b8dbbe72e16c",
                start, end).enqueue(callback);
    }
}
