package com.jkasztur.android.moviedatabase.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.jkasztur.android.moviedatabase.BR;
import com.jkasztur.android.moviedatabase.R;
import com.jkasztur.android.moviedatabase.model.Movie;
import com.jkasztur.android.moviedatabase.viewmodel.MoviesViewModel;

import java.util.List;

public class MoviesRvAdapter extends RecyclerView.Adapter<MoviesRvAdapter.ViewHolder> {
    private int movieLayoutId = R.layout.recyclerview_item;
    private int moreLayoutId = R.layout.recyclerview_item_more;
    private List<Movie> movies;
    private MoviesViewModel viewModel;

    public MoviesRvAdapter(MoviesViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public int getItemCount() {
        return movies == null ? 0 : movies.size() + 1;
    }

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(layoutInflater, viewType, parent, false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(viewModel, position);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return moreLayoutId;
        } else {
            return movieLayoutId;
        }
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final ViewDataBinding binding;

        ViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(MoviesViewModel viewModel, Integer position) {
            viewModel.fetchMovieDetailsAt(position);
            binding.setVariable(BR.viewModel, viewModel);
            binding.setVariable(BR.position, position);
            binding.executePendingBindings();
        }
    }

}
