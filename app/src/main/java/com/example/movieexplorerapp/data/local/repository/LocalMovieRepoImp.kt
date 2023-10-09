package com.example.movieexplorerapp.data.local.repository

import androidx.paging.PagingSource
import com.example.movieexplorerapp.data.local.database.DatabaseTable
import com.example.movieexplorerapp.data.local.database.MovieDao
import com.example.movieexplorerapp.domain.model.DiscoverMovieAPIResponseImp
import com.example.movieexplorerapp.domain.model.NowPlayingMovieAPIResponseImp
import com.example.movieexplorerapp.domain.model.PopularMovieAPIResponseImp
import com.example.movieexplorerapp.domain.model.TopRatedMovieAPIResponseImp
import com.example.movieexplorerapp.domain.model.UpcomingMovieAPIResponseImp
import javax.inject.Inject

class LocalMovieRepoImp @Inject constructor(
    private val movieDao: MovieDao,
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

    override fun fetchDiscover(): PagingSource<Int, DiscoverMovieAPIResponseImp> {
        TODO("Not yet implemented")
    }

    override fun fetchNowPlaying(): PagingSource<Int, NowPlayingMovieAPIResponseImp> {
        TODO("Not yet implemented")
    }

    override fun fetchPopular(): PagingSource<Int, PopularMovieAPIResponseImp> {
        TODO("Not yet implemented")
    }

    override fun fetchTopRated(): PagingSource<Int, TopRatedMovieAPIResponseImp> {
        TODO("Not yet implemented")
    }

    override fun fetchUpcoming(): PagingSource<Int, UpcomingMovieAPIResponseImp> {
        TODO("Not yet implemented")
    }

    override suspend fun clearTable(tableName: DatabaseTable) {
        movieDao.clearTable(tableName)
    }


}