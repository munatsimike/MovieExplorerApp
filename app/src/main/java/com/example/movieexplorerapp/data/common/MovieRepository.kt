package com.example.movieexplorerapp.data.common

import com.example.movieexplorerapp.domain.model.DiscoverMovieAPIResponseImp
import com.example.movieexplorerapp.domain.model.NowPlayingMovieAPIResponseImp
import com.example.movieexplorerapp.domain.model.PopularMovieAPIResponseImp
import com.example.movieexplorerapp.domain.model.TopRatedMovieAPIResponseImp
import com.example.movieexplorerapp.domain.model.UpcomingMovieAPIResponseImp

interface MovieRepository {
    suspend fun saveAllMoviesToLocalDB(movies: DiscoverMovieAPIResponseImp)
    suspend fun saveNowPlayingMoviesToLocalDB(nowPlayingImp: NowPlayingMovieAPIResponseImp)
    suspend fun saveUpComingMoviesToLocalDB(upcomingImp: UpcomingMovieAPIResponseImp)
    suspend fun savePopularMoviesToLocalDB(popularImp: PopularMovieAPIResponseImp)
    suspend fun saveTopRatedMoviesToLocalDB(topRatedImp: TopRatedMovieAPIResponseImp)
}