package com.example.movieexplorerapp.data.local.repository

import androidx.paging.PagingData
import com.example.movieexplorerapp.data.common.MovieRepository
import com.example.movieexplorerapp.data.local.database.DatabaseTable
import com.example.movieexplorerapp.domain.model.DiscoverMovieAPIResponseImp
import com.example.movieexplorerapp.domain.model.NowPlayingMovieAPIResponseImp
import com.example.movieexplorerapp.domain.model.PopularMovieAPIResponseImp
import com.example.movieexplorerapp.domain.model.TopRatedMovieAPIResponseImp
import com.example.movieexplorerapp.domain.model.UpcomingMovieAPIResponseImp
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
    suspend fun insertDiscover(discoverApiResponse: DiscoverMovieAPIResponseImp)
    suspend fun insertNowPlaying(nowPlaying: NowPlayingMovieAPIResponseImp)
    suspend fun insertPopular(popular: PopularMovieAPIResponseImp)
    suspend fun insertTopRated(topRated: TopRatedMovieAPIResponseImp)
    suspend fun insertUpcoming(upcoming: UpcomingMovieAPIResponseImp)

    // Fetches responses from the database as a Flow. Observing changes and emitting data continuously.
    fun fetchDiscover(): Flow<PagingData<DiscoverMovieAPIResponseImp>>
    fun fetchNowPlaying():Flow<PagingData<NowPlayingMovieAPIResponseImp>>
    fun fetchPopular(): Flow<PagingData<PopularMovieAPIResponseImp>>
    fun fetchTopRated(): Flow<PagingData<TopRatedMovieAPIResponseImp>>
    fun fetchUpcoming(): Flow<PagingData<UpcomingMovieAPIResponseImp>>

    // the following method clears all the tables.
    suspend fun clearTable(tableName: DatabaseTable)
}