package com.example.movieexplorerapp.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.example.movieexplorerapp.data.local.repository.LocalMovieRepoImp
import com.example.movieexplorerapp.data.model.MovieCategory
import com.example.movieexplorerapp.data.model.MovieEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class MovieViewModel @Inject constructor(
    private val localRepo: LocalMovieRepoImp,
) : ViewModel() {
    val discover = localRepo.fetchMovies(MovieCategory.Discover)
    val popular: Flow<PagingData<MovieEntity>> =  localRepo.fetchMovies(MovieCategory.Popular)
    val topRated: Flow<PagingData<MovieEntity>> =  localRepo.fetchMovies(MovieCategory.TopRated)
    val upComing: Flow<PagingData<MovieEntity>> =  localRepo.fetchMovies(MovieCategory.UpComing)
    val nowPlaying: Flow<PagingData<MovieEntity>> =  localRepo.fetchMovies(MovieCategory.NowPlaying)
}




