package com.example.movieexplorerapp.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.movieexplorerapp.BuildConfig
import com.example.movieexplorerapp.data.local.repository.LocalMovieRepoImp
import com.example.movieexplorerapp.data.model.APIKey
import com.example.movieexplorerapp.data.model.MovieCategory
import com.example.movieexplorerapp.data.model.MovieEntity
import com.example.movieexplorerapp.data.service.api.APIKeyProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class MovieViewModel @Inject constructor(
    private val localRepo: LocalMovieRepoImp,
    private val apiKeyProvider: APIKeyProvider
) : ViewModel() {
    private val apiKeyReady = MutableStateFlow(false)
    val discover: Flow<PagingData<MovieEntity>> = setupMovieFlow(MovieCategory.Discover)
    val popular: Flow<PagingData<MovieEntity>> = setupMovieFlow(MovieCategory.Popular)
    val  topRated: Flow<PagingData<MovieEntity>> = setupMovieFlow(MovieCategory.TopRated)
    val  upComing: Flow<PagingData<MovieEntity>> = setupMovieFlow(MovieCategory.UpComing)
    val  nowPlaying: Flow<PagingData<MovieEntity>> = setupMovieFlow(MovieCategory.NowPlaying)

    init {
        viewModelScope.launch {
            initializeApiKey()
            apiKeyReady.value = true
        }
    }

    private fun setupMovieFlow(category: MovieCategory): Flow<PagingData<MovieEntity>> {
        return apiKeyReady.filter { it }.flatMapLatest {
            localRepo.fetchMovies(category)
        }
    }

    private fun initializeApiKey() {
        val key = apiKeyProvider.getKey()
        if (key.value.isEmpty()) {
            // Use a secure method to obtain the API key for development/production
            val secureApiKey = getSecureApiKey()
            apiKeyProvider.updateKey(APIKey(value = secureApiKey))
        }
    }

    private fun getSecureApiKey(): String {
        //retrieve the API key from Gradle properties
        return BuildConfig.API_KEY
    }

}




