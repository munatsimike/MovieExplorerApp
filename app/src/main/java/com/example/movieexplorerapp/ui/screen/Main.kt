package com.example.movieexplorerapp.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.movieexplorerapp.data.local.model.MovieEntity
import com.example.movieexplorerapp.ui.viewModel.MovieViewModel

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
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(movies.itemSnapshotList) { movie ->
                if (movie != null) {
                    Text(text = movie.title)
                }
            }
        }
    }

    @Composable
    fun NowPlayingMovie(movies: LazyPagingItems<MovieEntity>) {
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(movies.itemSnapshotList) { movie ->
                if (movie != null) {
                    Row {
                        Text(text = movie.title)
                    }
                }
            }
        }
    }
}