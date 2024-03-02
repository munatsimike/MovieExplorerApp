package com.example.movieexplorerapp.data.remote.api.apikey

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import androidx.security.crypto.MasterKeys.AES256_GCM_SPEC

/**
 * This class provides functionality to securely store and retrieve an API key using encrypted SharedPreferences.
 * The AIKey object is used to store the api key
 *
 */

class APIKeyProviderImpl(context: Context) : APIKeyProvider {

    // Using AndroidX Security library to generate or retrieve a master key alias
    private val masterKeyAlias = MasterKeys.getOrCreate(AES256_GCM_SPEC)

    // Creating EncryptedSharedPreferences instance with AES encryption
    private val sharedPreferences = EncryptedSharedPreferences.create(
        "secret_shared_preferences",
        masterKeyAlias, // Master key alias generated or retrieved
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    private val editor = sharedPreferences.edit()
    private val apiKey = "api_key"

    override fun getKey(): APIKey {
        val apiKey = sharedPreferences.getString(apiKey, null)
        return APIKey(key = apiKey ?: "")
    }

    /**
     * Updates the stored API key in SharedPreferences.
     * To delete an API key, pass an empty APIKey object.
     */
    override fun updateKey(apIkey: APIKey) {
        editor.putString(apiKey, apIkey.key).apply()
    }
}
