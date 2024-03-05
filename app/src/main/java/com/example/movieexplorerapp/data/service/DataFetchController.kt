package com.example.movieexplorerapp.data.service

import com.example.movieexplorerapp.data.model.LastFetchTime

/**
 * Manages data fetching intervals and determines when to fetch movies from a remote server.
 */

class DataRefreshController {
    // assigns the default fetch interval
    private var fetchInterval = DEFAULT_FETCH_INTERVAL

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
    fun shouldFetchMovies(lastFetchTime: LastFetchTime): Boolean {
        if (lastFetchTime.value == null){
            return true
        }

        val currentTime = System.currentTimeMillis()
        val timeSinceLastFetch = lastFetchTime.value.let { currentTime.minus(it) }
        return timeSinceLastFetch >= currentTime
    }
}