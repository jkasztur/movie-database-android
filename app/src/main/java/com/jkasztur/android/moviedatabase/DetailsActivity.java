package com.jkasztur.android.moviedatabase;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.jkasztur.android.moviedatabase.databinding.ActivityDetailsBinding;
import com.jkasztur.android.moviedatabase.model.Movie;
import com.jkasztur.android.moviedatabase.viewmodel.DetailsViewModel;

public class DetailsActivity extends AppCompatActivity {

    private DetailsViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        setupBindings();
    }

    private void setupBindings() {
        ActivityDetailsBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_details);
        viewModel = ViewModelProviders.of(this).get(DetailsViewModel.class);
        binding.setViewModel(viewModel);
        Intent intent = getIntent();
        Movie movie = intent.getParcelableExtra(MoviesActivity.APP_MOVIE_ID);
        viewModel.setMovie(movie);
        Log.i("DetailsActivity", movie.getTitle());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return false;
    }
}
