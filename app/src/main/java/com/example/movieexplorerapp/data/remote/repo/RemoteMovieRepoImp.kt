package com.example.movieexplorerapp.data.remote.repo

import com.example.movieexplorerapp.data.remote.api.MovieService
import com.example.movieexplorerapp.data.remote.dto.BaseMovieApiResponse
import com.example.movieexplorerapp.data.remote.dto.MovieAPIResponseImp
import com.example.movieexplorerapp.data.remote.dto.MovieAPIResponseWithDateImp
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

    override suspend fun getAllMoviesFromAPI(): MovieAPIResponseImp {
        return executeAPICall { movieService.exploreAllMovies() }
    }

    override suspend fun getNowPlayingMoviesFromAPI(): MovieAPIResponseWithDateImp {
        return executeAPICall { movieService.getNowPlayingMovies() }
    }

    override suspend fun getUpComingMoviesFromAPI(): MovieAPIResponseWithDateImp {
        return executeAPICall { movieService.getUpcomingMovies() }
    }

    override suspend fun getPopularMoviesFromAPI(): MovieAPIResponseImp {
        return executeAPICall { movieService.getPopularMovies() }
    }

    override suspend fun getTopRatedMoviesFromAPI(): MovieAPIResponseImp {
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
