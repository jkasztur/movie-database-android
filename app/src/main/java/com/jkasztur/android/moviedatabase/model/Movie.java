package com.jkasztur.android.moviedatabase.model;

import android.databinding.BaseObservable;
import android.os.Parcel;
import android.os.Parcelable;

import com.jkasztur.android.moviedatabase.api.TheMovieDbClient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import retrofit2.Callback;

@Setter
@Getter
@NoArgsConstructor
public class Movie extends BaseObservable implements Parcelable {
    private final String SMALL_POSTER_BASE = "http://image.tmdb.org/t/p/w185/";
    private final String BIG_POSTER_BASE = "http://image.tmdb.org/t/p/w342/";

    private String id;

    private String title;
    private String language;
    private List<String> genres;
    private String overview;
    private String releaseDate;
    private String posterPath;

    private boolean detailsSet;

    protected Movie(Parcel in) {
        id = in.readString();
        title = in.readString();
        language = in.readString();
        genres = new ArrayList<>();
        in.readList(genres, String.class.getClassLoader());
        overview = in.readString();
        releaseDate = in.readString();
        posterPath = in.readString();
        detailsSet = in.readByte() != 0;
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public String getSmallPosterUrl() {
        if (posterPath != null) {
            return SMALL_POSTER_BASE + posterPath;
        }
        return null;
    }

    public String getBigPosterUrl() {
        if (posterPath != null) {
            return BIG_POSTER_BASE + posterPath;
        }
        return null;
    }

    public void fetchDetails(Callback<Movie> callback) {
        TheMovieDbClient.getApi().getMovieDetails(id, "7b6d38875404b0b7a8c7b8dbbe72e16c").enqueue(callback);
    }

    public void setDetails(Movie other) {
        setId(other.getId());
        setTitle(other.getTitle());
        setPosterPath(other.getPosterPath());
        setGenres(other.getGenres());
        if(genres == null || genres.isEmpty()) {
            setGenres(Collections.singletonList("No genres have been added"));
        }
        setLanguage(other.getLanguage());
        if(language == null || language.isEmpty()) {
            setLanguage("No language has been added");
        }
        setOverview(other.getOverview());
        if(overview == null || overview.isEmpty()) {
            setOverview("No overview has been added");
        }
        setReleaseDate(other.getReleaseDate());
        if(releaseDate == null || releaseDate.isEmpty()) {
            setReleaseDate("No release date has been added");
        }

        setDetailsSet(true);
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(title);
        parcel.writeString(language);
        parcel.writeList(genres);
        parcel.writeString(overview);
        parcel.writeString(releaseDate);
        parcel.writeString(posterPath);
        parcel.writeByte((byte) (detailsSet ? 1 : 0));
    }
}
