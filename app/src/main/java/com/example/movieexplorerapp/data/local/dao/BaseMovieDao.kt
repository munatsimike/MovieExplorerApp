package com.example.movieexplorerapp.data.local.dao

import androidx.paging.PagingSource
import com.example.movieexplorerapp.data.local.model.MovieEntity

interface BaseMovieDao {
    suspend fun insertDiscover(discoverApiResponse: List<MovieEntity>)
    suspend fun insertNowPlaying(nowPlaying: List<MovieEntity>)
    suspend fun insertPopular(popular: List<MovieEntity>)
    suspend fun insertTopRated(topRated: List<MovieEntity>)
    suspend fun insertUpcoming(upcoming: List<MovieEntity>)

    // the following functions fetches the responses from the database
    fun fetchDiscover(): PagingSource<Int, MovieEntity>
    fun fetchNowPlaying(): PagingSource<Int, MovieEntity>
    fun fetchPopular(): PagingSource<Int, MovieEntity>
    fun fetchTopRated(): PagingSource<Int, MovieEntity>
    fun fetchUpcoming(): PagingSource<Int, MovieEntity>

    suspend fun clearTable(tableName: String)
}