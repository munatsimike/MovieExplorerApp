package com.example.movieexplorerapp.data.remote.api.apikey

interface APIkeyProvider {
    fun getKey(): APIkey
    fun updateKey(apIkey: APIkey)
}