package com.example.movieexplorerapp.data.remote.api.apikey

object APIkeyProviderImp: APIkeyProvider {
    override fun getKey(): APIkey {
        return APIkey(key = "2b36d6fc58fa055e7f5ca4dc10684209")
    }

    override fun updateKey(apIkey: APIkey) {
        TODO("Not yet implemented")
    }

}