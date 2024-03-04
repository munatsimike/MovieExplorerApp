package com.example.movieexplorerapp.data.local.dao

import androidx.paging.PagingSource
import com.example.movieexplorerapp.data.model.MovieCategory
import com.example.movieexplorerapp.data.model.MovieEntity

interface BaseMovieDao {
    suspend fun insertMovies(movies: List<MovieEntity>)
    // the following functions fetches the responses from the database
    fun fetchMovies(category: MovieCategory): PagingSource<Int, MovieEntity>
    suspend fun deleteMovies(category: MovieCategory)
}