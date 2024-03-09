package com.example.movieexplorerapp.data.service.api

import com.example.movieexplorerapp.data.model.APIKey

interface APIKeyProvider {
    suspend fun getKey(): APIKey
    suspend fun updateKey(apiKey: APIKey)
}