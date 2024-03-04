package com.example.movieexplorerapp.data.paging

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

            val response = getMovies(movieCategory, page)
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
                localMovieRepo.deleteMovies(movieCategory)
            }

            apiResponse.let { data ->
                val mapResult = mapMovie(data.results, movieCategory)
                val paginationMetadata =
                    MoviePaginationMetadata(movieCategory, data.page, data.totalPages)
                localMovieRepo.insertMovies(mapResult)
                paginationMetadataDao.insertPagingMetaData(paginationMetadata)
            }
        }
    }

    private suspend fun shouldFetch(): Boolean {
        return false
    }
}




