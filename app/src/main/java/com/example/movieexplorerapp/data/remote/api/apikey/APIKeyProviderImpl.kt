package com.example.movieexplorerapp.data.remote.api.apikey

import com.example.movieexplorerapp.data.local.preferences.EncryptedPreferenceManager
import javax.inject.Inject

/**
 * This class provides functionality to securely store and retrieve an API key using encrypted SharedPreferences.
 */

class APIKeyProviderImpl @Inject constructor(private val encryptedPreferenceManager: EncryptedPreferenceManager) : APIKeyProvider {
    private val storageKey = "api_key"

    override fun getKey(): APIKey {
       val stringKey = encryptedPreferenceManager.getData(storageKey)
        return APIKey(value = stringKey)
    }

    /**
     * Updates the stored API key in SharedPreferences.
     * To delete an API key, pass an empty APIKey object.
     */
    override fun updateKey(apiKey: APIKey) {
        encryptedPreferenceManager.saveUpdate(storageKey, apiKey.value)
    }
}
