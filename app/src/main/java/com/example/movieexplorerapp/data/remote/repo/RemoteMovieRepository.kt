package com.example.movieexplorerapp.data.remote.repo

import com.example.movieexplorerapp.data.common.MovieRepository
import com.example.movieexplorerapp.data.remote.dto.MovieAPIResponseImp
import com.example.movieexplorerapp.data.remote.dto.MovieAPIResponseWithDateImp

/**
 * The remote repository defines asynchronous methods for fetching movie data from the server
 * using Retrofit service. Each method corresponds to a specific type of movie data response.
 */
interface RemoteMovieRepository : MovieRepository {
    suspend fun getAllMoviesFromAPI(): MovieAPIResponseImp
    suspend fun getNowPlayingMoviesFromAPI(): MovieAPIResponseWithDateImp
    suspend fun getUpComingMoviesFromAPI(): MovieAPIResponseWithDateImp
    suspend fun getPopularMoviesFromAPI(): MovieAPIResponseImp
    suspend fun getTopRatedMoviesFromAPI(): MovieAPIResponseImp
}