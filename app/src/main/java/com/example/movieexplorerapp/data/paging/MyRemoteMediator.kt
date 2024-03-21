package com.example.movieexplorerapp.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.movieexplorerapp.data.local.dao.MoviePaginationMetadataDao
import com.example.movieexplorerapp.data.local.database.LocalMovieDatabase
import com.example.movieexplorerapp.data.model.LastFetchTime
import com.example.movieexplorerapp.data.model.MovieCategory
import com.example.movieexplorerapp.data.model.MovieEntity
import com.example.movieexplorerapp.data.model.MovieMapper
import com.example.movieexplorerapp.data.model.MoviePaginationMetadata
import com.example.movieexplorerapp.data.model.dto.BaseMovieApiResponse
import com.example.movieexplorerapp.data.model.dto.Movie
import com.example.movieexplorerapp.data.remote.repo.RemoteMovieRepository
import com.example.movieexplorerapp.data.service.DataCleanUpManager
import com.example.movieexplorerapp.data.service.DataRefreshController
import com.example.movieexplorerapp.data.service.LastFetchTimeProvider
import kotlinx.coroutines.flow.first
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class MyRemoteMediator @Inject constructor(
    private val database: LocalMovieDatabase,
    private val remoteRepo: RemoteMovieRepository,
    private val movieCategory: MovieCategory,
    private val paginationMetadataDao: MoviePaginationMetadataDao,
    private val dataRefreshController: DataRefreshController,
    private val lastFetchTimeProvider: LastFetchTimeProvider,
    private val dataCleanUpManager: DataCleanUpManager,
) : RemoteMediator<Int, MovieEntity>() {

    override suspend fun load(
        loadType: LoadType, state: PagingState<Int, MovieEntity>
    ): MediatorResult {

        try {
            val page = when (loadType) {
                LoadType.REFRESH -> {
                    val lastFetchTime = lastFetchTimeProvider.getLastFetchTime().first()
                    if (!dataRefreshController.shouldFetchMovies(lastFetchTime)) {
                        return MediatorResult.Success(endOfPaginationReached = true)
                    }
                    // Start from the first page when refreshing
                    1
                }

                LoadType.PREPEND -> {
                    // Handle loading previous items if needed
                    return MediatorResult.Success(endOfPaginationReached = true)
                }

                LoadType.APPEND -> {
                    val pagingMataData =
                        paginationMetadataDao.fetchPagingMetaData(movieCategory.name)
                    if (pagingMataData.size == 1) {
                        val metaData = pagingMataData[0]
                        if (metaData.page <= metaData.totalPages) {
                            metaData.page + 1
                        } else {
                            return MediatorResult.Success(endOfPaginationReached = true)
                        }

                    } else {
                        // Unexpected metadata size, handle accordingly
                        return MediatorResult.Error(Exception("Unexpected metadata size"))
                    }
                }
            }
            //fetch movies from API
            val response = getMovies(movieCategory, page)
            // Save movies to room
            saveMovies(response, loadType)
            //save fetch time. it will be used to determine the for next to fetch data to avoid fetching data everytime the app is opened
            lastFetchTimeProvider.saveLastFetchTime(LastFetchTime(System.currentTimeMillis()))
            //clean up excess movies from db if threshold has been exceed
            dataCleanUpManager.cleanExcessMovies()

            return MediatorResult.Success(endOfPaginationReached = response.page == response.totalPages)
        } catch (exception: Exception) {
            return MediatorResult.Error(exception)
        }
    }

    private fun mapMovie(list: List<Movie>, category: MovieCategory): List<MovieEntity> {
        val mapResult = MovieMapper.mapList(list, category)
        val result = mapResult.getOrElse {
            throw it
        }
        return result
    }

    private suspend fun getMovies(category: MovieCategory, page: Int): BaseMovieApiResponse {
        return when (category) {
            MovieCategory.Discover -> {
                remoteRepo.getAllMoviesFromAPI(page)
            }

            MovieCategory.Popular -> {
                remoteRepo.getPopularMoviesFromAPI(page)
            }

            MovieCategory.NowPlaying -> {
                remoteRepo.getNowPlayingMoviesFromAPI(page)
            }

            MovieCategory.UpComing -> {
                remoteRepo.getUpComingMoviesFromAPI(page)
            }

            MovieCategory.TopRated -> {
                remoteRepo.getTopRatedMoviesFromAPI(page)
            }
        }
    }

    private suspend fun saveMovies(apiResponse: BaseMovieApiResponse, loadType: LoadType) {
        database.withTransaction {
            if (loadType == LoadType.REFRESH) {
                database.movieDao.deleteMoviesByCategory(movieCategory)
                paginationMetadataDao.deleteMovies(movieCategory.name)
            }

            apiResponse.let { data ->
                val mapResult = mapMovie(data.results, movieCategory)
                val paginationMetadata =
                    MoviePaginationMetadata(movieCategory, data.page, data.totalPages)
                database.movieDao.insertMovies(mapResult)
                paginationMetadataDao.insertPagingMetaData(paginationMetadata)
            }
        }
    }
}




