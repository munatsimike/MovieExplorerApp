package com.example.movieexplorerapp.data.remote.repo

import com.example.movieexplorerapp.data.remote.api.MovieService
import com.example.movieexplorerapp.data.remote.dto.BaseMovieApiResponse
import com.example.movieexplorerapp.data.remote.dto.DiscoverMovieAPIResponseImp
import com.example.movieexplorerapp.data.remote.dto.NowPlayingMovieAPIResponseImp
import com.example.movieexplorerapp.data.remote.dto.PopularMovieAPIResponseImp
import com.example.movieexplorerapp.data.remote.dto.TopRatedMovieAPIResponseImp
import com.example.movieexplorerapp.data.remote.dto.UpcomingMovieAPIResponseImp
import com.example.movieexplorerapp.utils.MyException
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

/**
 * Implements the RemoteMovieRepository interface. This class takes two arguments: MovieService and LocalMovieRepository,
 * which are injected by Hilt. Each function in this class is a suspending function that fetches a different type of movie data object.
 * Additionally, the executeAPICall function is used internally to handle exceptions specific to API calls, ensuring the app does not crash.
 */
class RemoteMovieRepoImp @Inject constructor(
    private val movieService: MovieService
) : RemoteMovieRepository {

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

    suspend fun <T : BaseMovieApiResponse> executeAPICall(apiCall: suspend () -> Response<T>): T {
        val unknownError = "An unknown error has occurred"
        return try {
            val response = apiCall.invoke()
            if (response.isSuccessful) {
                response.body()
                    ?: throw MyException.EmptyBodyException("Response body is null or empty")
            } else {
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
