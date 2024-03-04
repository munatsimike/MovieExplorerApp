package com.example.movieexplorerapp.data.remote.api

import com.example.movieexplorerapp.data.model.dto.MovieAPIResponseImp
import com.example.movieexplorerapp.data.model.dto.MovieAPIResponseWithDateImp
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * The service is responsible for making HTTP requests to the remote server.
 *The server response is a JSON object, which will be converted by retrofit to a Kotlin object
 * The response object contains a list of movies  of various movies and other attributes
 */
interface MovieService {
    @GET("discover/movie")
    suspend fun exploreAllMovies(@Query("page") page: Int): Response<MovieAPIResponseImp>

    // api response with date range
    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(@Query("page") page: Int): Response<MovieAPIResponseWithDateImp>

    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("page") page: Int): Response<MovieAPIResponseImp>

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(@Query("page") page: Int): Response<MovieAPIResponseImp>

    // api response with date range
    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(@Query("page") page: Int): Response<MovieAPIResponseWithDateImp>
}