package com.jkasztur.android.moviedatabase.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableInt;
import android.util.Log;
import android.view.View;

import com.jkasztur.android.moviedatabase.R;
import com.jkasztur.android.moviedatabase.adapter.MoviesRvAdapter;
import com.jkasztur.android.moviedatabase.adapter.RangeNumFilter;
import com.jkasztur.android.moviedatabase.model.Movie;
import com.jkasztur.android.moviedatabase.model.Movies;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesViewModel extends ViewModel {

    @Getter
    private MoviesRvAdapter adapter;
    @Getter
    private RangeNumFilter editFilter;
    private Movies movies;
    public ObservableInt loading;
    public MutableLiveData<Boolean> refreshClicked;
    public MutableLiveData<Movie> movieClicked;
    @Getter @Setter
    private boolean dataLoaded = false;
    @Getter @Setter
    private ObservableInt editButtonRes;


    public void init() {
        movies = new Movies();
        adapter = new MoviesRvAdapter(this);
        editFilter = new RangeNumFilter(1,14);
        loading = new ObservableInt(View.GONE);
        refreshClicked = new MutableLiveData<>();
        editButtonRes = new ObservableInt(R.drawable.baseline_arrow_forward);
        movieClicked = new MutableLiveData<>();
    }

    public void fetchList(int lastDays) {
        movies.fetchList(lastDays);
    }

    public MutableLiveData<List<Movie>> getMovies() {
        return movies.getMovies();
    }

    public void setMoviesInAdapter(List<Movie> mv) {
        adapter.setMovies(mv);
        adapter.notifyDataSetChanged();
    }

    public Movie getMovieAt(Integer position) {
        if (movies.getMovies().getValue() != null
                && position != null
                && movies.getMovies().getValue().size() > position) {
            return movies.getMovies().getValue().get(position);
        }
        return null;
    }

    public void onRefreshClicked() {
        refreshClicked.setValue(true);
    }

    public void onClickMoreItem() {
        Log.i("CLICK", "more button clicked");
        int beforeAdd = movies.getMovies().getValue().size();
        movies.addMoreToPresented();
        int afterAdd = movies.getMovies().getValue().size();
        adapter.notifyItemRangeInserted(beforeAdd, afterAdd);
    }

    public void onClickMovieItem(int position) {
        Log.i("CLICK", "movie clicked");
        movieClicked.setValue(getMovieAt(position));
    }

    public void fetchMovieDetailsAt(final Integer index) {
        final Movie movie = getMovieAt(index);
        if (movie != null && !movie.isDetailsSet()) {

            movie.fetchDetails(new Callback<Movie>() {
                @Override
                public void onResponse(Call<Movie> call, Response<Movie> response) {
                    if (response.code() == 200) {
                        Movie body = response.body();
                        movie.setDetails(body);
                        adapter.notifyItemChanged(index);
                    }
                }

                @Override
                public void onFailure(Call<Movie> call, Throwable t) {
                    Log.e("Test", t.getMessage(), t);

                }
            });
        }
    }
}
