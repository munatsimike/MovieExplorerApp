package com.example.movieexplorerapp.data.local.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.movieexplorerapp.data.local.database.DatabaseTable
import com.example.movieexplorerapp.data.local.database.MovieLocalDatabase
import com.example.movieexplorerapp.data.local.repository.LocalMovieRepository
import com.example.movieexplorerapp.data.remote.repo.RemoteMovieRepository
import com.example.movieexplorerapp.domain.model.Movie
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class MyRemoteMediator @Inject constructor(
    private val remoteRepo: RemoteMovieRepository,
    private val localMovieRepo: LocalMovieRepository,
    private val  database: MovieLocalDatabase
) : RemoteMediator<Int, Movie>() {

    override suspend fun load(
        loadType: LoadType, state: PagingState<Int, Movie>
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
                    localMovieRepo.clearTable(DatabaseTable.DISCOVER) // Clear existing data when refreshing
                }

                response.let { data ->
                    localMovieRepo.insertDiscover(data)
                }
            }

            return MediatorResult.Success(endOfPaginationReached = response.page == response.totalPages )
        } catch (exception: Exception) {
            return MediatorResult.Error(exception)
        }
    }
}




