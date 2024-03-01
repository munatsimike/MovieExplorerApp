package com.example.movieexplorerapp.ui.viewModel

import androidx.lifecycle.ViewModel
import com.example.movieexplorerapp.data.local.model.MovieCategory
import com.example.movieexplorerapp.data.local.repository.LocalMovieRepoImp
import com.example.movieexplorerapp.data.remote.api.apikey.APIKey
import com.example.movieexplorerapp.data.remote.api.apikey.APIKeyProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

private const val apiKey =
    "2b36d6fc58fa055e7f5ca4dc10684209"// this will be removed its for development only

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val localRepo: LocalMovieRepoImp,
    apiKeyProvider: APIKeyProvider
) : ViewModel() {
    val movies = localRepo.fetchMovies(MovieCategory.Discover)

    init {
        val key = apiKeyProvider.getKey()
        if (key.key == "")
            apiKeyProvider.updateKey(APIKey(key = apiKey))
    }
}




