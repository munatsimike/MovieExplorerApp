package com.example.movieexplorerapp.data.service

import com.example.movieexplorerapp.data.local.dao.MovieDao
import com.example.movieexplorerapp.data.local.database.LocalMovieDatabase
import com.example.movieexplorerapp.data.local.repository.LocalMovieRepoImp
import com.example.movieexplorerapp.data.model.LastFetchTime
import javax.inject.Inject

/**
 * Manages data fetching intervals and determines when to fetch movies from a remote server.
 */

class DataRefreshController @Inject constructor(val movieDao: MovieDao) {
    // assigns the default fetch interval
    private var fetchInterval = DEFAULT_FETCH_INTERVAL
    private var userTriggeredRefresh = false

    companion object {
        /**
         * Sets the time interval for fetching data in milliseconds.
         * @param timeMilliSecond Interval between data fetches in milliseconds.
         */
        private const val DEFAULT_FETCH_INTERVAL: Long = 3600000
    }

    // assigns time interval in milliseconds
    fun setFetchInterval(timeMilliSecond: Long) {
        fetchInterval = timeMilliSecond
    }

    /**
     * Determines whether it's time to fetch new movies based on the last fetch time.
     * If `lastFetchTime.value` is null, no fetch has been made yet, and it returns true.
     * The last fetch time should be updated after successfully fetching movies.
     * @param lastFetchTime Custom object storing the last fetch time as a Long value.
     * @return True if it's time to fetch movies from the remote server, false otherwise.
     */
    suspend fun shouldFetchMovies(lastFetchTime: LastFetchTime): Boolean {
        val totalMovieCount = movieDao.countMovieEntries()
        if (lastFetchTime.value == null || userTriggeredRefresh || totalMovieCount == 0) {
            return true
        }
        val currentTime = System.currentTimeMillis()
        val timeSinceLastFetch = lastFetchTime.value.let { currentTime.minus(it) }
        return timeSinceLastFetch >= fetchInterval
    }

    // Assigns a boolean value to userTriggeredRefresh. This value will be used to refresh data as per users request
    fun refreshData(refreshData: Boolean) {
        userTriggeredRefresh = refreshData
    }
}