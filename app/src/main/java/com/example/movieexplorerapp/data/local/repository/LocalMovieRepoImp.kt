package com.example.movieexplorerapp.data.local.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.movieexplorerapp.data.local.dao.MoviePaginationMetadataDao
import com.example.movieexplorerapp.data.local.database.LocalMovieDatabase
import com.example.movieexplorerapp.data.model.MovieCategory
import com.example.movieexplorerapp.data.model.MovieEntity
import com.example.movieexplorerapp.data.paging.MyRemoteMediator
import com.example.movieexplorerapp.data.remote.repo.RemoteMovieRepoImp
import com.example.movieexplorerapp.data.service.DataCleanUpManager
import com.example.movieexplorerapp.data.service.DataRefreshController
import com.example.movieexplorerapp.data.service.LastFetchTimeProvider
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * This class implements LocalMovieRepository, overriding suspending methods to interact with the database through the movieDao.
 * The methods insert and fetch movie data and clear database tables.
 *  The class takes moviesDao as a parameter, which is injected by Hilt.
 */
class LocalMovieRepoImp @Inject constructor(
    private val remoteMovieRepoImp: RemoteMovieRepoImp,
    private val database: LocalMovieDatabase,
    private val paginationMetadataDao: MoviePaginationMetadataDao,
    private val dataRefreshController: DataRefreshController,
    private val lastFetchTimeProvider: LastFetchTimeProvider,
    private val dataCleanUpManager: DataCleanUpManager,
) : LocalMovieRepository {
    override suspend fun insertMovies(movies: List<MovieEntity>) {
        database.movieDao.insertMovies(movies)
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun fetchMovies(category: MovieCategory): Flow<PagingData<MovieEntity>> {
        return Pager(
            config = PagingConfig(pageSize = 20, initialLoadSize = 40, prefetchDistance = 5),
            remoteMediator = MyRemoteMediator(
                database,
                remoteMovieRepoImp,
                category,
                paginationMetadataDao,
                dataRefreshController,
                lastFetchTimeProvider,
                dataCleanUpManager,
            ),
            pagingSourceFactory = { database.movieDao.fetchMovies(category) }
        ).flow
    }

    override suspend fun deleteMovies(category: MovieCategory) {
        database.movieDao.deleteMoviesByCategory(category)
    }
}