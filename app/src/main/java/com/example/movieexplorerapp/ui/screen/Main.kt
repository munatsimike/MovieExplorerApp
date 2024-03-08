package com.example.movieexplorerapp.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.rememberAsyncImagePainter
import com.example.movieexplorerapp.R
import com.example.movieexplorerapp.data.model.MovieEntity
import com.example.movieexplorerapp.data.model.dto.Movie
import com.example.movieexplorerapp.ui.viewModel.MovieViewModel
import com.example.movieexplorerapp.utils.ImageUrlBuilder

object Main {
    @Composable
    fun Screen(movieViewModel: MovieViewModel) {
        val discover = movieViewModel.discover.collectAsLazyPagingItems()
        val nowPlaying = movieViewModel.nowPlaying.collectAsLazyPagingItems()
        val popular = movieViewModel.popular.collectAsLazyPagingItems()
        val topRated = movieViewModel.topRated.collectAsLazyPagingItems()
        val upComing = movieViewModel.upComing.collectAsLazyPagingItems()

        Column {
            DiscoverMovie(movies = discover)
            NowPlayingMovie(movies = nowPlaying)
        }
    }

    @Composable
    fun DiscoverMovie(movies: LazyPagingItems<MovieEntity>) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(0.5f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            items(movies.itemSnapshotList) { movie ->
                if (movie != null) {
                    Image(
                        modifier = Modifier.size(150.dp),
                        painter = rememberAsyncImagePainter(model = ImageUrlBuilder.getUrl(movie.posterPath)),
                        contentDescription = stringResource(id = R.string.poster_image_desc)
                    )
                }
            }
        }
    }

    @Composable
    fun NowPlayingMovie(movies: LazyPagingItems<MovieEntity>) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth(0.5f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            items(movies.itemSnapshotList) { movie ->
                if (movie != null) {
                    MovieItem(movie)
                }
            }
            movies.apply {
                when (loadState.append) {
                    is LoadState.Loading -> {
                        item { LoadingItem() }
                    }

                    is LoadState.Error -> {
                        item { RetryItem { retry() } }
                    }

                    is LoadState.NotLoading -> {

                    }
                }
            }
        }
    }

    @Composable
    fun MovieItem(movie: Movie) {
        Card(modifier = Modifier.padding(8.dp)) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = movie.title, style = MaterialTheme.typography.headlineLarge)
                Text(
                    text = "Rating: ${movie.popularity}", style = MaterialTheme.typography.bodySmall
                )
                // Add more movie details here
            }
        }
    }

    @Composable
    fun LoadingItem() {
        Text(text = "Loading.....")
    }

    @Composable
    fun RetryItem(retry: () -> Unit) {
        retry()
    }


}