package com.jkasztur.android.moviedatabase;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.jkasztur.android.moviedatabase.databinding.ActivityMoviesBinding;
import com.jkasztur.android.moviedatabase.model.Movie;
import com.jkasztur.android.moviedatabase.viewmodel.MoviesViewModel;

import java.util.List;

public class MoviesActivity extends AppCompatActivity {
    public static final String APP_MOVIE_ID = "APP_MOVIE_ID";

    private MoviesViewModel viewModel;
    private EditText editLastDays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
        editLastDays = findViewById(R.id.edit_days);
        setupBindings();
    }

    private void setupBindings() {
        ActivityMoviesBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_movies);
        viewModel = ViewModelProviders.of(this).get(MoviesViewModel.class);
        if (!viewModel.isDataLoaded()) {
            viewModel.init();
        }
        binding.setViewModel(viewModel);
        setupClicks();
    }

    private void setupClicks() {
        viewModel.refreshClicked.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if(aBoolean != null && aBoolean) {
                    setupList();
                }
            }
        });

        viewModel.movieClicked.observe(this, new Observer<Movie>() {
            @Override
            public void onChanged(@Nullable Movie movie) {
                if(movie != null) {
                    makeMovieToast(movie);
                    startDetailsActivity(movie);
                }
            }
        });
    }

    private void makeMovieToast(Movie movie) {
        Toast.makeText(this, "You selected: " + movie.getTitle(), Toast.LENGTH_SHORT).show();
    }

    private void startDetailsActivity(Movie movie) {
        Intent detailsAct = new Intent(this, DetailsActivity.class);
        detailsAct.putExtra(APP_MOVIE_ID, movie);
        startActivity(detailsAct);
    }

    private void setupList() {
        viewModel.loading.set(View.VISIBLE);
        EditText editText = findViewById(R.id.edit_days);
        int days = Integer.parseInt(editText.getText().toString());
        Log.i("Activity", "days taken from edit: " + days);
        viewModel.fetchList(days);
        viewModel.getMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                viewModel.loading.set(View.GONE);
                viewModel.setMoviesInAdapter(movies);
                viewModel.setDataLoaded(true);
                viewModel.getEditButtonRes().set(R.drawable.baseline_refresh_black_24dp);
            }
        });
    }
}
