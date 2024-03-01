package com.example.movieexplorerapp.ui.viewModel

import androidx.lifecycle.ViewModel
import com.example.movieexplorerapp.data.local.model.MovieCategory
import com.example.movieexplorerapp.data.local.repository.LocalMovieRepoImp
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(private val localRepo: LocalMovieRepoImp) : ViewModel() {
    val movies = localRepo.fetchMovies(MovieCategory.Discover)
}


