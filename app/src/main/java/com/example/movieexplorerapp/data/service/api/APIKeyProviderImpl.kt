package com.example.movieexplorerapp.data.service.api

import com.example.movieexplorerapp.BuildConfig
import com.example.movieexplorerapp.data.local.preferences.EncryptedPreferenceManager
import com.example.movieexplorerapp.data.model.APIKey
import javax.inject.Inject

/**
 * This class provides functionality to securely store and retrieve an API key using encrypted SharedPreferences.
 */

class APIKeyProviderImpl @Inject constructor(private val encryptedPreferenceManager: EncryptedPreferenceManager) :
    APIKeyProvider {
    private val storageKey = "api_key"
    private var cachedKey: APIKey? = null

    override suspend fun getKey(): APIKey {
        // Return the cached key if it exists
        if (cachedKey != null) return cachedKey!!

        // Fetch the key from encrypted storage
        var stringKey = encryptedPreferenceManager.getData(storageKey)

        // If the key doesn't exist in the encrypted storage, initialize it from BuildConfig or another secure source
        if (stringKey.isEmpty()) {
            stringKey = BuildConfig.API_KEY
            // Save the initialized key in the encrypted storage for future use
            encryptedPreferenceManager.saveUpdate(storageKey, stringKey)
        }

        // Cache and return the key
        cachedKey = APIKey(value = stringKey)
        return cachedKey!!
    }

    /**
     * Updates the stored API key in SharedPreferences.
     * To delete an API key, pass an empty APIKey object.
     */
    override suspend fun updateKey(apiKey: APIKey) {
        encryptedPreferenceManager.saveUpdate(storageKey, apiKey.value)
        cachedKey = apiKey
    }
}
