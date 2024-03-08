package com.example.movieexplorerapp.data.service

import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.movieexplorerapp.data.local.preferences.PreferenceManager
import com.example.movieexplorerapp.data.model.LastFetchTime
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * This class abstracts the preference manager, handling the saving and fetching of the last time movies were fetched from the API.
 * The last fetched time is crucial for determining whether to fetch movies again.
 */
class LastFetchTimeProvider @Inject constructor(private val manager: PreferenceManager) {
    companion object {
        // Key for accessing last fetch time in preferences
        private val LAST_FETCH_TIME = stringPreferencesKey("last_fetch_time")
    }

    /**
     * Retrieves the last fetched time as a Flow, allowing for asynchronous data retrieval.
     * This time is converted to a LastFetchTime object.
     */
    fun getLastFetchTime(): Flow<LastFetchTime> {
        return manager.fetchData(LAST_FETCH_TIME).map {
            LastFetchTime(it.toLongOrNull())
        }
    }

    /**
     * Saves the provided last fetch time.
     * @param lastFetchTime The LastFetchTime object containing the time to be saved.
     */
    suspend fun saveLastFetchTime(lastFetchTime: LastFetchTime) {
        manager.saveData(LAST_FETCH_TIME, lastFetchTime.value.toString())
    }
}