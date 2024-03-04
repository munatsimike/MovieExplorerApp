package com.example.movieexplorerapp.data.service.api

import com.example.movieexplorerapp.data.model.APIKey

interface APIKeyProvider {
    fun getKey(): APIKey
    fun updateKey(apIkey: APIKey)
}