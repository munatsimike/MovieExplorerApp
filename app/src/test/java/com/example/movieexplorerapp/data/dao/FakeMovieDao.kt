package com.example.movieexplorerapp.data.dao

import androidx.paging.PagingSource
import com.example.movieexplorerapp.data.local.dao.BaseMovieDao
import com.example.movieexplorerapp.data.local.model.MovieCategory
import com.example.movieexplorerapp.data.local.model.MovieEntity

class FakeMovieDao : BaseMovieDao {
    override suspend fun insertMovies(movies: List<MovieEntity>) {
        TODO("Not yet implemented")
    }

    override fun fetchMovies(category: MovieCategory): PagingSource<Int, MovieEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteMovies(category: MovieCategory) {
        TODO("Not yet implemented")
    }

}