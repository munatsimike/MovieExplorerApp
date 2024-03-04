package com.example.movieexplorerapp.data.local.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

/**
* This preference manager is responsible for saving and retrieving key-value pairs as strings using Jetpack DataStore.
* It currently saves the last fetch time, but it's capable of saving any string value.
*/
@Singleton
class PreferenceManager @Inject constructor(@ApplicationContext val context: Context) {
    // Creates a DataStore instance with the file name "appPreferences".
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("appPreferences")
    /**
     * Saves the provided data using the specified key.
     * @param key The key under which the value will be saved.
     * @param value The value to be saved.
     */
    suspend fun saveData(key: Preferences.Key<String>, value: String) {
        context.dataStore.edit { pref ->
            pref[key] = value
        }
    }

    /**
     * Retrieves the value associated with the specified key as a Flow.
     * If the key is not found, an empty string is returned.
     * @param key The key whose value is to be retrieved.
     * @return A Flow emitting the retrieved value.
     */
    fun fetchData(key: Preferences.Key<String>): Flow<String> =
        context.dataStore.data
            .map { pref ->
                pref[key] ?: ""
            }
}
