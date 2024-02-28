package com.example.movieexplorerapp.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.movieexplorerapp.data.local.repository.LocalMovieRepoImp
import com.example.movieexplorerapp.domain.model.DiscoverMovieAPIResponseImp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(private val localRepo: LocalMovieRepoImp) : ViewModel() {
    private val movies_: MutableStateFlow<PagingData<DiscoverMovieAPIResponseImp>> =
        MutableStateFlow(
            PagingData.empty()
        )

    val movies: StateFlow<PagingData<DiscoverMovieAPIResponseImp>> = movies_

    init {
        getMovies()
    }

    private fun getMovies() {
        viewModelScope.launch {
            localRepo.fetchDiscover().collectLatest {
                movies_.value = it
            }
        }
    }
}


