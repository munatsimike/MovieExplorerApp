package com.example.movieexplorerapp.data.remote.repo

import com.example.movieexplorerapp.data.common.MovieRepository
import com.example.movieexplorerapp.data.remote.api.MovieService
import com.example.movieexplorerapp.domain.model.BaseMovieApiResponse
import com.example.movieexplorerapp.domain.model.DiscoverMovieAPIResponseImp
import com.example.movieexplorerapp.domain.model.NowPlayingMovieAPIResponseImp
import com.example.movieexplorerapp.domain.model.PopularMovieAPIResponseImp
import com.example.movieexplorerapp.domain.model.TopRatedMovieAPIResponseImp
import com.example.movieexplorerapp.domain.model.UpcomingMovieAPIResponseImp
import com.example.movieexplorerapp.utils.MyException
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class RemoteMovieRepoImp @Inject constructor(
    private val movieService: MovieService, private val localMovieRepoImp: MovieRepository
) : RemoteMovieRepository {

    suspend fun refreshMoviesFromAPI() {
        saveAllMoviesToLocalDB(getAllMoviesFromAPI())
        saveNowPlayingMoviesToLocalDB(getNowPlayingMoviesFromAPI())
        saveUpComingMoviesToLocalDB(getUpComingMoviesFromAPI())
        savePopularMoviesToLocalDB(getPopularMoviesFromAPI())
        saveTopRatedMoviesToLocalDB(getTopRatedMoviesFromAPI())
    }

    override suspend fun getAllMoviesFromAPI(): DiscoverMovieAPIResponseImp {
        return executeAPICall { movieService.exploreAllMovies() }
    }

    override suspend fun getNowPlayingMoviesFromAPI(): NowPlayingMovieAPIResponseImp {
        return executeAPICall { movieService.getNowPlayingMovies() }
    }

    override suspend fun getUpComingMoviesFromAPI(): UpcomingMovieAPIResponseImp {
        return executeAPICall { movieService.getUpcomingMovies() }
    }

    override suspend fun getPopularMoviesFromAPI(): PopularMovieAPIResponseImp {
        return executeAPICall { movieService.getPopularMovies() }
    }

    override suspend fun getTopRatedMoviesFromAPI(): TopRatedMovieAPIResponseImp {
        return executeAPICall { movieService.getTopRatedMovies() }
    }

    override suspend fun saveAllMoviesToLocalDB(movies: DiscoverMovieAPIResponseImp) {
        localMovieRepoImp.saveAllMoviesToLocalDB(movies)
    }

    override suspend fun saveNowPlayingMoviesToLocalDB(nowPlayingImp: NowPlayingMovieAPIResponseImp) {
        localMovieRepoImp.saveNowPlayingMoviesToLocalDB(nowPlayingImp)
    }

    override suspend fun saveUpComingMoviesToLocalDB(upcomingImp: UpcomingMovieAPIResponseImp) {
        localMovieRepoImp.saveUpComingMoviesToLocalDB(upcomingImp)
    }

    override suspend fun savePopularMoviesToLocalDB(popularImp: PopularMovieAPIResponseImp) {
        localMovieRepoImp.savePopularMoviesToLocalDB(popularImp)
    }

    override suspend fun saveTopRatedMoviesToLocalDB(topRatedImp: TopRatedMovieAPIResponseImp) {
        localMovieRepoImp.saveTopRatedMoviesToLocalDB(topRatedImp)
    }

    suspend fun <T : BaseMovieApiResponse> executeAPICall(apiCall: suspend () -> Response<T>): T {
        val unknownError = "An unknown error has occurred"
        return try {
            val response = apiCall.invoke()
            if (response.isSuccessful) {
                response.body()
                    ?: throw MyException.EmptyAPIResponseBodyException("Response body is null or empty")
            }
            else {
                // handle http response exceptions
                val errorMsg = response.errorBody()?.string() ?: unknownError
                throw MyException.HttpException(response.code(), errorMsg)
            }
        } catch (e: IOException) {
            throw MyException.NetworkException(e.message ?: unknownError)
        } catch (e: Exception) {
            throw e
        }
    }
}
