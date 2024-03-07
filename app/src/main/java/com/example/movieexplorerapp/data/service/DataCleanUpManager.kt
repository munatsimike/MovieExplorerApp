package com.example.movieexplorerapp.data.service

import com.example.movieexplorerapp.data.local.dao.MovieDao
import javax.inject.Inject

/**
 * Manages cleaning up excess movies in the local database to maintain a specified maximum number of movies.
 * Injects a MovieDao instance to interact with the database.
 */
class DataCleanUpManager @Inject constructor(private val movieDao: MovieDao) {
    // The maximum number of movies to keep in the database.
    private var movieCountThreshold = 150

    //Updates the threshold of movies to keep in the database.
    fun setMoviesToKeep(moviesToKeep: Int) {
        this.movieCountThreshold = moviesToKeep
    }

    //Cleans up excess movies from the database if the total count exceeds the defined threshold.
    suspend fun cleanExcessMovies() {
        val totalMovieCount = movieDao.countMovieEntries()
        if (totalMovieCount > movieCountThreshold) {
            val excessMovieCount = totalMovieCount - movieCountThreshold
            movieDao.cleanExcessMovies(excessMovieCount)
        }
    }
}