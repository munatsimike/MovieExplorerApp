package com.example.movieexplorerapp.data.local.preferences

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EncryptedPreferenceManager @Inject constructor(@ApplicationContext val context: Context) {
    // Using AndroidX Security library to generate or retrieve a master key alias
    private val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

    // Creating EncryptedSharedPreferences instance with AES encryption
    private val sharedPreferences = EncryptedSharedPreferences.create(
        "secret_shared_preferences",
        masterKeyAlias, // Master key alias generated or retrieved
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    private val editor = sharedPreferences.edit()

    fun getData(key: String): String {
        return sharedPreferences.getString(key, null) ?: ""
    }

    /**
     * Updates the stored API key in SharedPreferences.
     * To delete an API key, pass an empty APIKey object.
     */
    fun saveUpdate(key: String, value: String) {
        editor.putString(key, value).apply()
    }
}