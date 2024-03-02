package com.example.movieexplorerapp.data.local.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.movieexplorerapp.data.local.dao.MoviePaginationMetadataDao
import com.example.movieexplorerapp.data.local.database.LocalMovieDatabase
import com.example.movieexplorerapp.data.local.model.MovieCategory
import com.example.movieexplorerapp.data.local.model.MovieEntity
import com.example.movieexplorerapp.data.local.model.MovieMapper
import com.example.movieexplorerapp.data.local.model.MoviePaginationMetadata
import com.example.movieexplorerapp.data.local.repository.LocalMovieRepository
import com.example.movieexplorerapp.data.remote.dto.BaseMovieApiResponse
import com.example.movieexplorerapp.data.remote.dto.Movie
import com.example.movieexplorerapp.data.remote.repo.RemoteMovieRepository
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class MyRemoteMediator @Inject constructor(
    private val localMovieRepo: LocalMovieRepository,
    private val database: LocalMovieDatabase,
    private val remoteRepo: RemoteMovieRepository,
    private val movieCategory: MovieCategory,
    private val paginationMetadataDao: MoviePaginationMetadataDao
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

            val response = getMovies(movieCategory)
            saveMovies(response, loadType)

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

    private suspend fun getMovies(category: MovieCategory): BaseMovieApiResponse {
        return when (category) {
            MovieCategory.Discover -> {
                remoteRepo.getAllMoviesFromAPI()
            }

            MovieCategory.Popular -> {
                remoteRepo.getPopularMoviesFromAPI()
            }

            MovieCategory.NowPlaying -> {
                remoteRepo.getNowPlayingMoviesFromAPI()
            }

            MovieCategory.UpComing -> {
                remoteRepo.getUpComingMoviesFromAPI()
            }

            MovieCategory.TopRated -> {
                remoteRepo.getTopRatedMoviesFromAPI()
            }
        }
    }

    private suspend fun saveMovies(apiResponse: BaseMovieApiResponse, loadType: LoadType) {
        database.withTransaction {
            if (loadType == LoadType.REFRESH) {
                localMovieRepo.deleteMovies(movieCategory)
            }

            apiResponse.let { data ->
                val mapResult = mapMovie(data.results, movieCategory)
                val paginationMetadata =
                    MoviePaginationMetadata(movieCategory.name, data.page, data.totalPages)
                localMovieRepo.insertMovies(mapResult)
                paginationMetadataDao.insertPagingMetaData(paginationMetadata)
            }
        }
    }
}




