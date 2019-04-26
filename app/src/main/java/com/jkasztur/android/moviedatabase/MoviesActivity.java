package com.jkasztur.android.moviedatabase;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.jkasztur.android.moviedatabase.databinding.ActivityMoviesBinding;
import com.jkasztur.android.moviedatabase.model.Movie;
import com.jkasztur.android.moviedatabase.viewmodel.MoviesViewModel;

import java.util.List;

public class MoviesActivity extends AppCompatActivity {
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
        setupRefreshClick();
    }

    private void setupRefreshClick() {
        viewModel.refreshClicked.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if(aBoolean != null && aBoolean) {
                    setupList();
                }
            }
        });
    }

    private void setupList() {
        viewModel.loading.set(View.VISIBLE);
        viewModel.fetchList(Integer.parseInt(editLastDays.getText().toString()));
        viewModel.getMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                viewModel.loading.set(View.GONE);
                viewModel.setMoviesInAdapter(movies);
                viewModel.setDataLoaded(true);
                viewModel.refreshClicked.setValue(false);
                viewModel.getEditButtonRes().set(R.drawable.baseline_refresh_black_24dp);
            }
        });
    }
}
