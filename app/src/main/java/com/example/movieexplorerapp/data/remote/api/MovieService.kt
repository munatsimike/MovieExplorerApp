package com.example.movieexplorerapp.data.remote.api

import com.example.movieexplorerapp.domain.model.ApiResponse
import com.example.movieexplorerapp.domain.model.ApiResponseWithDateRange
import retrofit2.Response
import retrofit2.http.GET

interface MovieService {
    @GET("discover/movie")
    suspend fun exploreAllMovies(): Response<ApiResponse>

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(): Response<ApiResponseWithDateRange>

    @GET("movie/popular")
    suspend fun getPopularMovies(): Response<ApiResponse>

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(): Response<ApiResponse>

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(): Response<ApiResponseWithDateRange>
}