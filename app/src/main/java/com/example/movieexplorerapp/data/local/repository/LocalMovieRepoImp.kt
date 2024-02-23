package com.example.movieexplorerapp.data.local.repository

import androidx.paging.PagingData
import com.example.movieexplorerapp.data.local.dao.BaseMovieDao
import com.example.movieexplorerapp.data.local.database.DatabaseTable
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
) : LocalMovieRepository {
    override suspend fun insertDiscover(discoverApiResponse: DiscoverMovieAPIResponseImp) {
        TODO("Not yet implemented")
    }

    override suspend fun insertNowPlaying(nowPlaying: NowPlayingMovieAPIResponseImp) {
        TODO("Not yet implemented")
    }

    override suspend fun insertPopular(popular: PopularMovieAPIResponseImp) {
        TODO("Not yet implemented")
    }

    override suspend fun insertTopRated(topRated: TopRatedMovieAPIResponseImp) {
        TODO("Not yet implemented")
    }

    override suspend fun insertUpcoming(upcoming: UpcomingMovieAPIResponseImp) {
        TODO("Not yet implemented")
    }

    override fun fetchDiscover(): Flow<PagingData<DiscoverMovieAPIResponseImp>> {
        TODO("Not yet implemented")
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