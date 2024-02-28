package com.example.movieexplorerapp.data.remote.api

import com.example.movieexplorerapp.data.remote.dto.DiscoverMovieAPIResponseImp
import com.example.movieexplorerapp.data.remote.dto.NowPlayingMovieAPIResponseImp
import com.example.movieexplorerapp.data.remote.dto.PopularMovieAPIResponseImp
import com.example.movieexplorerapp.data.remote.dto.TopRatedMovieAPIResponseImp
import com.example.movieexplorerapp.data.remote.dto.UpcomingMovieAPIResponseImp
import retrofit2.Response
import retrofit2.http.GET

/**
 * The service is responsible for making HTTP requests to the remote server.
 *The server response is a JSON object, which will be converted by retrofit to a Kotlin object
 * The response object contains a list of movies  of various movies and other attributes
 */
interface MovieService {
    @GET("discover/movie")
    suspend fun exploreAllMovies(): Response<DiscoverMovieAPIResponseImp>

    // api response with date range
    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(): Response<NowPlayingMovieAPIResponseImp>

    @GET("movie/popular")
    suspend fun getPopularMovies(): Response<PopularMovieAPIResponseImp>

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(): Response<TopRatedMovieAPIResponseImp>

    // api response with date range
    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(): Response<UpcomingMovieAPIResponseImp>
}