package com.example.movieexplorerapp.data.local.repository

import androidx.paging.PagingData
import com.example.movieexplorerapp.data.common.MovieRepository
import com.example.movieexplorerapp.data.model.MovieCategory
import com.example.movieexplorerapp.data.model.MovieEntity
import kotlinx.coroutines.flow.Flow

/**
 * This interface contains suspending functions to insert database entities into the local Room database.
 * Each function inserts or fetches a different type of movie data object.
 * Functions to fetch data return Flow to observe changes and emit data continuously.
 * The interface extends the MovieRepository, which serves as the base repository.
 */
interface LocalMovieRepository : MovieRepository {
    // Inserts API responses into the Room database. Each response contains a list of movies and additional metadata.
    suspend fun insertMovies(movies: List<MovieEntity>)

    // Fetches responses from the database as a Flow. Observing changes and emitting data continuously.
    fun fetchMovies(category: MovieCategory): Flow<PagingData<MovieEntity>>
    suspend fun deleteMovies(category: MovieCategory)

}