package com.jkasztur.android.moviedatabase.model;

import android.databinding.BaseObservable;

import com.jkasztur.android.moviedatabase.api.TheMovieDbClient;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import retrofit2.Callback;

@Setter
@Getter
public class Movie extends BaseObservable {
    private final String SMALL_POSTER_BASE = "http://image.tmdb.org/t/p/w185/";
    private final String BIG_POSTER_BASE = "http://image.tmdb.org/t/p/w342/";

    private String id;

    private String title;
    private String language;
    private List<String> genres;
    private String overview;
    private String releaseDate;
    private String posterPath;

    private boolean detailsSet = false;

    public String getSmallPosterUrl() {
        if(posterPath != null) {
            return SMALL_POSTER_BASE + posterPath;
        }
        return null;
    }

    public String getBigPosterUrl() {
        if(posterPath != null) {
            return BIG_POSTER_BASE + posterPath;
        }
        return null;
    }

    public void fetchDetails(Callback<Movie> callback) {
        TheMovieDbClient.getApi().getMovieDetails(id, "7b6d38875404b0b7a8c7b8dbbe72e16c").enqueue(callback);
    }
}
