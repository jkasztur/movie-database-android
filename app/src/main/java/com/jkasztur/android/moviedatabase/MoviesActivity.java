package com.jkasztur.android.moviedatabase;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.jkasztur.android.moviedatabase.databinding.ActivityMoviesBinding;
import com.jkasztur.android.moviedatabase.model.Movie;
import com.jkasztur.android.moviedatabase.viewmodel.MoviesViewModel;

import java.util.List;

public class MoviesActivity extends AppCompatActivity {
    private MoviesViewModel viewModel;

    private Spinner daysSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        daysSpinner = findViewById(R.id.changedDaysSpinner);
        // todo: use array from resources
        final Integer[] items = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14};
        ArrayAdapter<Integer> spinnerAdapter =
                new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, items);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        daysSpinner.setAdapter(spinnerAdapter);

        setupBindings(savedInstanceState);

    }

    private void setupBindings(Bundle savedInstanceState) {
        ActivityMoviesBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_movies);
        viewModel = ViewModelProviders.of(this).get(MoviesViewModel.class);
        if (savedInstanceState == null) {
            viewModel.init();
        }
        binding.setViewModel(viewModel);
        setupList();
    }

    private void setupList() {
        viewModel.loading.set(View.VISIBLE);
        viewModel.fetchList();
        viewModel.getMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                viewModel.loading.set(View.GONE);
                viewModel.setMoviesInAdapter(movies);
            }
        });
    }
}
