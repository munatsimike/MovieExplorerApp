package com.example.movieexplorerapp.data.remote.repo

import com.example.movieexplorerapp.data.common.MovieRepository
import com.example.movieexplorerapp.domain.model.DiscoverMovieAPIResponseImp
import com.example.movieexplorerapp.domain.model.NowPlayingMovieAPIResponseImp
import com.example.movieexplorerapp.domain.model.PopularMovieAPIResponseImp
import com.example.movieexplorerapp.domain.model.TopRatedMovieAPIResponseImp
import com.example.movieexplorerapp.domain.model.UpcomingMovieAPIResponseImp

/**
 * The remote repository defines asynchronous methods for fetching movie data from the server
 * using Retrofit service. Each method corresponds to a specific type of movie data response.
 */
interface RemoteMovieRepository : MovieRepository {
    suspend fun getAllMoviesFromAPI(): DiscoverMovieAPIResponseImp
    suspend fun getNowPlayingMoviesFromAPI(): NowPlayingMovieAPIResponseImp
    suspend fun getUpComingMoviesFromAPI(): UpcomingMovieAPIResponseImp
    suspend fun getPopularMoviesFromAPI(): PopularMovieAPIResponseImp
    suspend fun getTopRatedMoviesFromAPI(): TopRatedMovieAPIResponseImp
}