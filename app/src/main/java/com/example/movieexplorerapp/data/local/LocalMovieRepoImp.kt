package com.example.movieexplorerapp.data.local

import com.example.movieexplorerapp.domain.model.DiscoverMovieAPIResponseImp
import com.example.movieexplorerapp.domain.model.NowPlayingMovieAPIResponseImp
import com.example.movieexplorerapp.domain.model.PopularMovieAPIResponseImp
import com.example.movieexplorerapp.domain.model.TopRatedMovieAPIResponseImp
import com.example.movieexplorerapp.domain.model.UpcomingMovieAPIResponseImp

class LocalMovieRepoImp : LocalMovieRepository {
    override suspend fun saveAllMoviesToLocalDB(movies: DiscoverMovieAPIResponseImp) {
        TODO("Not yet implemented")
    }

    override suspend fun saveNowPlayingMoviesToLocalDB(nowPlayingImp: NowPlayingMovieAPIResponseImp) {
        TODO("Not yet implemented")
    }

    override suspend fun saveUpComingMoviesToLocalDB(upcomingImp: UpcomingMovieAPIResponseImp) {
        TODO("Not yet implemented")
    }

    override suspend fun savePopularMoviesToLocalDB(popularImp: PopularMovieAPIResponseImp) {
        TODO("Not yet implemented")
    }

    override suspend fun saveTopRatedMoviesToLocalDB(topRatedImp: TopRatedMovieAPIResponseImp) {
        TODO("Not yet implemented")
    }


}