package com.example.movieexplorerapp.data.remote.api.apikey

interface APIKeyProvider {
    fun getKey(): APIKey
    fun updateKey(apIkey: APIKey)
}