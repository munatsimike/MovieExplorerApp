package com.example.movieexplorerapp.data.local.repository

import androidx.paging.PagingData
import com.example.movieexplorerapp.data.common.MovieRepository
import com.example.movieexplorerapp.data.local.model.MovieEntity
import kotlinx.coroutines.flow.Flow

/**
 * This interface contains suspending functions to insert database entities into the local Room database.
 * Database entities represent API response objects converted to Room entities containing movie data.
 * Each function inserts or fetches a different type of movie data object.
 * Functions to fetch data return Flow to observe changes and emit data continuously.
 * The interface extends the MovieRepository, which serves as the base repository.
 */
interface LocalMovieRepository : MovieRepository {
    // Inserts API responses into the Room database. Each response contains a list of movies and additional metadata.
    suspend fun insertDiscover(discoverApiResponse: List<MovieEntity>)
    suspend fun insertNowPlaying(nowPlaying: List<MovieEntity>)
    suspend fun insertPopular(popular: List<MovieEntity>)
    suspend fun insertTopRated(topRated: List<MovieEntity>)
    suspend fun insertUpcoming(upcoming: List<MovieEntity>)

    // Fetches responses from the database as a Flow. Observing changes and emitting data continuously.
    fun fetchDiscover(): Flow<PagingData<MovieEntity>>
    fun fetchNowPlaying():Flow<PagingData<MovieEntity>>
    fun fetchPopular(): Flow<PagingData<MovieEntity>>
    fun fetchTopRated(): Flow<PagingData<MovieEntity>>
    fun fetchUpcoming(): Flow<PagingData<MovieEntity>>

    // the following method clears all the tables.
    suspend fun clearTable(tableName: String)
}