package com.example.movieexplorerapp.data.local.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.movieexplorerapp.data.local.database.LocalMovieDatabase
import com.example.movieexplorerapp.data.local.model.MovieCategory
import com.example.movieexplorerapp.data.local.model.MovieEntity
import com.example.movieexplorerapp.data.local.repository.LocalMovieRepository
import com.example.movieexplorerapp.data.remote.dto.Movie
import com.example.movieexplorerapp.data.remote.repo.RemoteMovieRepository
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class MyRemoteMediator @Inject constructor(
    private val localMovieRepo: LocalMovieRepository,
    private val database: LocalMovieDatabase,
    private val remoteRepo: RemoteMovieRepository
) : RemoteMediator<Int, MovieEntity>() {

    @OptIn(ExperimentalPagingApi::class)
    override suspend fun load(
        loadType: LoadType, state: PagingState<Int, MovieEntity>
    ): MediatorResult {
        try {
            val page = when (loadType) {
                LoadType.REFRESH -> {
                    // Start from the first page when refreshing
                    1
                }

                LoadType.PREPEND -> {
                    // Handle loading previous items if needed
                    return MediatorResult.Success(endOfPaginationReached = true)
                }

                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if (lastItem == null) {
                        1
                    } else {
                        // Load the next page
                        lastItem.id + 1
                    }
                }
            }

            val response = remoteRepo.getAllMoviesFromAPI()

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                  localMovieRepo.deleteMovies(MovieCategory.Discover)
                }

                response.let { data ->
                    localMovieRepo.insertMovies(fromMovieToMovieEntityList(data.results))
                }
            }

            return MediatorResult.Success(endOfPaginationReached = response.page == response.totalPages)
        } catch (exception: Exception) {
            return MediatorResult.Error(exception)
        }
    }

    private fun fromMovieToMovieEntityList(movies: List<Movie>): List<MovieEntity> {
        val movieEntities = mutableListOf<MovieEntity>()
        for (movie in movies) {
            val movieEntity = MovieEntity.fromMovieToMovieEntity(movie, MovieCategory.Discover)
            movieEntities.add(movieEntity)
        }
        return movieEntities
    }
}




