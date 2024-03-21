package com.example.movieexplorerapp.ui.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.example.movieexplorerapp.data.model.MovieEntity
import com.example.movieexplorerapp.data.model.dto.Movie
import com.example.movieexplorerapp.ui.components.NetworkImageLoader
import com.example.movieexplorerapp.ui.viewModel.MovieViewModel

/**
 * This screen is the main screen that displays paginated movies from the local database.
 * The movies are collected from the view model and displayed on the lazyRow.
 * The lazyRow ensures efficient display and also different movie categories(now playing, popular, toprated, upcoming) can fit on the screen
 */
object Main {
    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun Screen(movieViewModel: MovieViewModel) {
        val discover = movieViewModel.discover.collectAsLazyPagingItems()
        val nowPlaying = movieViewModel.nowPlaying.collectAsLazyPagingItems()
        val popular = movieViewModel.popular.collectAsLazyPagingItems()
        val topRated = movieViewModel.topRated.collectAsLazyPagingItems()
        val upComing = movieViewModel.upComing.collectAsLazyPagingItems()

        val pagerItems = nowPlaying.itemSnapshotList.items.take(10)
        val horizontalPagingState = rememberPagerState {
            pagerItems.size
        }

        Column(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            MyHorizontalPager(
                pagerItems,
                horizontalPagingState
            )

            LazyRowMovie(movies = discover)
            LazyRowMovie(movies = topRated)
            LazyRowMovie(movies = popular)
            LazyRowMovie(movies = upComing)
        }
    }

    //display now playing movie in a horizontal pager that covers 50% of the screen, so as to make it more visible.
    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun MyHorizontalPager(
        pagingItems: List<MovieEntity>,
        pagerState: PagerState,
    ) {
        var title = ""
        Box(
            Modifier
                .fillMaxHeight(0.45f)
                .clickable { }) {
            HorizontalPager(state = pagerState) { index ->
                val movie = pagingItems[index]
                title = movie.category.name
                movie.posterPath?.let { posterUrl ->
                    NetworkImageLoader(
                        posterUrl,
                        Modifier
                            .fillMaxSize()
                            .clip(
                                RoundedCornerShape(15.dp)
                            )
                    )
                }
            }
            Title(title = title)
        }

    }

    /**
     * The lazy Row takes movies and for each movie it displays the poster.
     * Each moive is clickable and to track the status of each movie as separate Map that contains movieid and the associeate bolean status.
     * if a movie is clicked the status is set to true and a detail page is displayed
     */
    @Composable
    private fun LazyRowMovie(movies: LazyPagingItems<MovieEntity>) {
        val configuration = LocalConfiguration.current
        val itemWidth = configuration.screenWidthDp.dp / 5
        val itemHeight = configuration.screenHeightDp.dp / 5

        val moviesList = movies.itemSnapshotList.items

        // Initialize visibility states for each item
        val itemsClickedStates = remember { mutableStateMapOf<Int, Boolean>() }

        moviesList.forEach { movie ->
            if (!itemsClickedStates.containsKey(movie.id)) {
                itemsClickedStates[movie.id] = false
            }
        }

        if (moviesList.isNotEmpty()) {
            Title(moviesList.first().category.name)
        }

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .height(itemHeight),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            items(count = movies.itemCount,
                key = movies.itemKey { movie -> movie.id },
                contentType = movies.itemContentType { "movies" }

            ) { index ->
                val movie = movies[index]
                if (movie != null) {
                    LazyRowMovieItem(movie, itemWidth) {
                        itemsClickedStates[movie.id] = true
                    }

                    if (itemsClickedStates[movie.id] == true) {
                        Detail.Screen(movie) { itemsClickedStates[movie.id] = false }
                    }
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

    // Title is the category for each movie group this is obtained from the movie object
    @Composable
    private fun Title(title: String) {
        Row(modifier = Modifier.height(20.dp), verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }

    /**
     * movie item to be displayed in the lazy row.
     */
    @Composable
    private fun LazyRowMovieItem(
        movie: Movie, size: Dp, modifier: Modifier = Modifier, onClick: () -> Unit
    ) {
        movie.posterPath?.let {
            Card(
                modifier
                    .width(size)
                    .fillMaxHeight()
            ) {
                Box(
                    modifier = modifier.fillMaxSize()
                ) {

                    NetworkImageLoader(imageUrl = it,
                        modifier = modifier
                            .matchParentSize()
                            .clickable { onClick() })
                }
            }
        }
    }


    @Composable
    private fun LoadingItem() {
        Text(text = "Loading.....")
    }

    @Composable
    private fun RetryItem(retry: () -> Unit) {
        retry()
    }
}

@Composable
@Preview
fun MoviePreview() {
}