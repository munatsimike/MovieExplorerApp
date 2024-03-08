package com.example.movieexplorerapp.data.local.preferences

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EncryptedPreferenceManager @Inject constructor(@ApplicationContext val context: Context) {
    // Using AndroidX Security library to generate or retrieve a master key alias
    private val masterKeyAlias by lazy { MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC) }

    // Creating EncryptedSharedPreferences instance with AES encryption
    private val sharedPreferences = EncryptedSharedPreferences.create(
        "secret_shared_preferences",
        masterKeyAlias, // Master key alias generated or retrieved
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    private val editor = sharedPreferences.edit()
    private val cache: MutableMap<String, String> = mutableMapOf()
    suspend fun getData(key: String): String = withContext(Dispatchers.IO) {
        // First, attempt to get the value from the cache
        return@withContext cache[key] ?: sharedPreferences.getString(key, null)?.also {
            // If not in cache, read from SharedPreferences and cache it
            cache[key] = it
        } ?: ""
    }

    suspend fun saveUpdate(key: String, value: String) = withContext(Dispatchers.IO) {

        // Update the cache as well as the SharedPreferences
        cache[key] = value
        editor.putString(key, value).apply()
    }
}