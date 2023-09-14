package com.example.movieexplorerapp.data.remote.api

import com.example.movieexplorerapp.domain.model.DiscoverMovieAPIResponseImp
import com.example.movieexplorerapp.domain.model.NowPlayingMovieAPIResponseImp
import com.example.movieexplorerapp.domain.model.PopularMovieAPIResponseImp
import com.example.movieexplorerapp.domain.model.TopRatedMovieAPIResponseImp
import com.example.movieexplorerapp.domain.model.UpcomingMovieAPIResponseImp
import retrofit2.Response
import retrofit2.http.GET

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