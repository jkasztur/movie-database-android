package com.jkasztur.android.moviedatabase.viewmodel;

import android.arch.lifecycle.ViewModel;

import com.jkasztur.android.moviedatabase.model.Movie;

import lombok.Getter;
import lombok.Setter;

public class DetailsViewModel extends ViewModel {
    @Getter @Setter
    private Movie movie;


}
