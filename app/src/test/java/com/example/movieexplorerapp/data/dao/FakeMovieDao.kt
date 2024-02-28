package com.example.movieexplorerapp.data.dao

import androidx.paging.PagingSource
import com.example.movieexplorerapp.data.local.dao.BaseMovieDao
import com.example.movieexplorerapp.data.local.model.MovieEntity

class FakeMovieDao : BaseMovieDao {
    override suspend fun insertDiscover(discoverApiResponse: List<MovieEntity>) {
        TODO("Not yet implemented")
    }

    override suspend fun insertNowPlaying(nowPlaying: List<MovieEntity>) {
        TODO("Not yet implemented")
    }

    override suspend fun insertPopular(popular: List<MovieEntity>) {
        TODO("Not yet implemented")
    }

    override suspend fun insertTopRated(topRated: List<MovieEntity>) {
        TODO("Not yet implemented")
    }

    override suspend fun insertUpcoming(upcoming: List<MovieEntity>) {
        TODO("Not yet implemented")
    }

    override fun fetchDiscover(): PagingSource<Int, MovieEntity> {
        TODO("Not yet implemented")
    }

    override fun fetchNowPlaying(): PagingSource<Int, MovieEntity> {
        TODO("Not yet implemented")
    }

    override fun fetchPopular(): PagingSource<Int, MovieEntity> {
        TODO("Not yet implemented")
    }

    override fun fetchTopRated(): PagingSource<Int, MovieEntity> {
        TODO("Not yet implemented")
    }

    override fun fetchUpcoming(): PagingSource<Int, MovieEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun clearTable(tableName: String) {
        TODO("Not yet implemented")
    }

}