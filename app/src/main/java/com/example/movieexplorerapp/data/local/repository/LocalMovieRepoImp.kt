package com.example.movieexplorerapp.data.local.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.movieexplorerapp.data.local.dao.BaseMovieDao
import com.example.movieexplorerapp.data.local.database.LocalMovieDatabase
import com.example.movieexplorerapp.data.local.model.MovieEntity
import com.example.movieexplorerapp.data.local.paging.MyRemoteMediator
import com.example.movieexplorerapp.data.remote.repo.RemoteMovieRepoImp
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * This class implements LocalMovieRepository, overriding suspending methods to interact with the database through the movieDao.
 * The methods insert and fetch movie data and clear database tables.
 *  The class takes moviesDao as a parameter, which is injected by Hilt.
 */
class LocalMovieRepoImp @Inject constructor(
    private val movieDao: BaseMovieDao,
    private val remoteMovieRepoImp: RemoteMovieRepoImp,
    private val database: LocalMovieDatabase
) : LocalMovieRepository {
    override suspend fun insertDiscover(discoverApiResponse: List<MovieEntity>) {
        movieDao.insertDiscover(discoverApiResponse)
    }

    override suspend fun insertNowPlaying(nowPlaying: List<MovieEntity>) {
        movieDao.insertNowPlaying(nowPlaying)
    }

    override suspend fun insertPopular(popular: List<MovieEntity>) {
        movieDao.insertPopular(popular)
    }

    override suspend fun insertTopRated(topRated: List<MovieEntity>) {
        movieDao.insertTopRated(topRated)
    }

    override suspend fun insertUpcoming(upcoming: List<MovieEntity>) {
        movieDao.insertUpcoming(upcoming)
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun fetchDiscover(): Flow<PagingData<MovieEntity>> {
        return Pager(
            config = PagingConfig(pageSize = 5),
            remoteMediator = MyRemoteMediator(this, database, remoteMovieRepoImp),
            pagingSourceFactory = { movieDao.fetchDiscover() }
        ).flow

    }

    override fun fetchNowPlaying(): Flow<PagingData<MovieEntity>> {
        TODO("Not yet implemented")
    }

    override fun fetchPopular(): Flow<PagingData<MovieEntity>> {
        TODO("Not yet implemented")
    }

    override fun fetchTopRated(): Flow<PagingData<MovieEntity>> {
        TODO("Not yet implemented")
    }

    override fun fetchUpcoming(): Flow<PagingData<MovieEntity>> {
        TODO("Not yet implemented")
    }

    override suspend fun clearTable(tableName: String) {
        movieDao.clearTable(tableName)
    }
}