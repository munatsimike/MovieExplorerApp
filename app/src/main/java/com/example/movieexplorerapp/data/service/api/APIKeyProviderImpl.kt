package com.example.movieexplorerapp.data.service.api

import com.example.movieexplorerapp.BuildConfig
import com.example.movieexplorerapp.data.local.preferences.EncryptedPreferenceManager
import com.example.movieexplorerapp.data.model.APIKey
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject

/**
 * This class provides functionality to securely store and retrieve an API key using encrypted SharedPreferences.
 */

class APIKeyProviderImpl @Inject constructor(private val encryptedPreferenceManager: EncryptedPreferenceManager) : APIKeyProvider {
    private val storageKey = "api_key"
    @Volatile private var cachedKey: APIKey? = null
    private val mutex = Mutex()

    override suspend fun getKey(): APIKey {
        // Return the cached key if it exists
        cachedKey?.let { return it }

        // Lock to ensure thread-safe access to the cached key
        return mutex.withLock {
            // Double-check if the key was set while waiting for the lock
            cachedKey?.let { return@withLock it }

            // Fetch the key from encrypted storage safely
            val stringKey = try {
                encryptedPreferenceManager.getData(storageKey)
            } catch (e: Exception) {

                ""
            }

            // If the key doesn't exist in the encrypted storage, initialize it
            val finalKey = stringKey.ifEmpty {
                val newKey = BuildConfig.API_KEY
                // Save the initialized key in the encrypted storage for future use
                try {
                    encryptedPreferenceManager.saveUpdate(storageKey, newKey)
                } catch (e: Exception) {
                    // Handle or log error as needed
                }
                newKey
            }

            // Cache and return the key
            APIKey(value = finalKey).also { cachedKey = it }
        }
    }

    override suspend fun updateKey(apiKey: APIKey) {
        // Ensure thread-safe update of the cached key
        mutex.withLock {
            try {
                encryptedPreferenceManager.saveUpdate(storageKey, apiKey.value)
            } catch (e: Exception) {
                // Handle or log error as needed
            }
            cachedKey = apiKey
        }
    }
}
