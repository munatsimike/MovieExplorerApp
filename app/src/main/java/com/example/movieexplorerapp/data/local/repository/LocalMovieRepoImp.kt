package com.example.movieexplorerapp.data.local.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.movieexplorerapp.data.local.dao.BaseMovieDao
import com.example.movieexplorerapp.data.local.database.DatabaseTable
import com.example.movieexplorerapp.data.local.database.LocalMovieDatabase
import com.example.movieexplorerapp.data.local.paging.MyRemoteMediator
import com.example.movieexplorerapp.data.remote.repo.RemoteMovieRepoImp
import com.example.movieexplorerapp.domain.model.DiscoverMovieAPIResponseImp
import com.example.movieexplorerapp.domain.model.NowPlayingMovieAPIResponseImp
import com.example.movieexplorerapp.domain.model.PopularMovieAPIResponseImp
import com.example.movieexplorerapp.domain.model.TopRatedMovieAPIResponseImp
import com.example.movieexplorerapp.domain.model.UpcomingMovieAPIResponseImp
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
    override suspend fun insertDiscover(discoverApiResponse: DiscoverMovieAPIResponseImp) {
        movieDao.insertDiscover(discoverApiResponse)
    }

    override suspend fun insertNowPlaying(nowPlaying: NowPlayingMovieAPIResponseImp) {
        movieDao.insertNowPlaying(nowPlaying)
    }

    override suspend fun insertPopular(popular: PopularMovieAPIResponseImp) {
        movieDao.insertPopular(popular)
    }

    override suspend fun insertTopRated(topRated: TopRatedMovieAPIResponseImp) {
        movieDao.insertTopRated(topRated)
    }

    override suspend fun insertUpcoming(upcoming: UpcomingMovieAPIResponseImp) {
        movieDao.insertUpcoming(upcoming)
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun fetchDiscover(): Flow<PagingData<DiscoverMovieAPIResponseImp>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            remoteMediator = MyRemoteMediator(this, database, remoteMovieRepoImp),
            pagingSourceFactory = { movieDao.fetchDiscover() }
        ).flow

    }

    override fun fetchNowPlaying(): Flow<PagingData<NowPlayingMovieAPIResponseImp>> {
        TODO("Not yet implemented")
    }

    override fun fetchPopular(): Flow<PagingData<PopularMovieAPIResponseImp>> {
        TODO("Not yet implemented")
    }

    override fun fetchTopRated(): Flow<PagingData<TopRatedMovieAPIResponseImp>> {
        TODO("Not yet implemented")
    }

    override fun fetchUpcoming(): Flow<PagingData<UpcomingMovieAPIResponseImp>> {
        TODO("Not yet implemented")
    }

    override suspend fun clearTable(tableName: DatabaseTable) {
        movieDao.clearTable(tableName)
    }
}