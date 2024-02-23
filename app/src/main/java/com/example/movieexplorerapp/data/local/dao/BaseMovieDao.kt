package com.example.movieexplorerapp.data.local.dao

import androidx.paging.PagingSource
import com.example.movieexplorerapp.data.local.database.DatabaseTable
import com.example.movieexplorerapp.domain.model.DiscoverMovieAPIResponseImp
import com.example.movieexplorerapp.domain.model.NowPlayingMovieAPIResponseImp
import com.example.movieexplorerapp.domain.model.PopularMovieAPIResponseImp
import com.example.movieexplorerapp.domain.model.TopRatedMovieAPIResponseImp
import com.example.movieexplorerapp.domain.model.UpcomingMovieAPIResponseImp

interface BaseMovieDao {
    suspend fun insertDiscover(discoverApiResponse: DiscoverMovieAPIResponseImp)
    suspend fun insertNowPlaying(nowPlaying: NowPlayingMovieAPIResponseImp)
    suspend fun insertPopular(popular: PopularMovieAPIResponseImp)
    suspend fun insertTopRated(topRated: TopRatedMovieAPIResponseImp)
    suspend fun insertUpcoming(upcoming: UpcomingMovieAPIResponseImp)

    // the following functions fetches the responses from the database
    fun fetchDiscover(): PagingSource<Int, DiscoverMovieAPIResponseImp>
    fun fetchNowPlaying(): PagingSource<Int, NowPlayingMovieAPIResponseImp>
    fun fetchPopular(): PagingSource<Int, PopularMovieAPIResponseImp>
    fun fetchTopRated(): PagingSource<Int, TopRatedMovieAPIResponseImp>
    fun fetchUpcoming(): PagingSource<Int, UpcomingMovieAPIResponseImp>

    suspend fun clearTable(tableName: DatabaseTable)
}