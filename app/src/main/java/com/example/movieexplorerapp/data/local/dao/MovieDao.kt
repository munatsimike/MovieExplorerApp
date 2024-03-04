package com.example.movieexplorerapp.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movieexplorerapp.data.MOVIE_ENTITY
import com.example.movieexplorerapp.data.model.MovieCategory
import com.example.movieexplorerapp.data.model.MovieEntity

/**
 * MovieDao contains suspending functions to interact with the local Room database.
 * It handles different API responses containing various movie data, which are inserted and fetched from the database.
 * Data fetched from the database is returned using PagingSource to handle pagination.
 */
@Dao
interface MovieDao : BaseMovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override suspend fun insertMovies(movies: List<MovieEntity>)
    @Query("SELECT * FROM $MOVIE_ENTITY WHERE category = :category")
    override fun fetchMovies(category: MovieCategory): PagingSource<Int, MovieEntity>
    @Query("DELETE FROM $MOVIE_ENTITY WHERE category =:category")
    override suspend fun deleteMovies(category: MovieCategory)
}