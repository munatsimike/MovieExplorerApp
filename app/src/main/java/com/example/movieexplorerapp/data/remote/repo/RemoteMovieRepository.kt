package com.example.movieexplorerapp.data.remote.repo

import com.example.movieexplorerapp.data.common.MovieRepository
import com.example.movieexplorerapp.data.model.dto.MovieAPIResponseImp
import com.example.movieexplorerapp.data.model.dto.MovieAPIResponseWithDateImp
/**
 * The remote repository defines asynchronous methods for fetching movie data from the server
 * using Retrofit service. Each method corresponds to a specific type of movie data response.
 */
interface RemoteMovieRepository : MovieRepository {
    suspend fun getAllMoviesFromAPI(page: Int): MovieAPIResponseImp
    suspend fun getNowPlayingMoviesFromAPI(page: Int): MovieAPIResponseWithDateImp
    suspend fun getUpComingMoviesFromAPI(page: Int): MovieAPIResponseWithDateImp
    suspend fun getPopularMoviesFromAPI(page: Int): MovieAPIResponseImp
    suspend fun getTopRatedMoviesFromAPI(page: Int): MovieAPIResponseImp
}