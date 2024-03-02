package com.example.movieexplorerapp.data.remote.repo

import com.example.movieexplorerapp.data.remote.api.MovieService
import com.example.movieexplorerapp.data.remote.dto.BaseMovieApiResponse
import com.example.movieexplorerapp.utils.MyException
import com.example.movieexplorerapp.utils.extensions.NetworkUtility.jsonToMovieObject
import com.example.movieexplorerapp.utils.test.MockMovieRepoImp
import com.example.movieexplorerapp.utils.test.TestUtils.sampleMovieJsonApiResponse
import com.example.movieexplorerapp.utils.test.TestUtils.sampleMovieJsonResponseWithDateRange
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.fail
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody.Companion.toResponseBody
import okio.IOException
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class RemoteMovieRepoImpTest {

    private lateinit var movieService: MovieService
    private lateinit var localMovieRepoImp: MockMovieRepoImp
    private lateinit var remoteMovieRepo: RemoteMovieRepoImp

    @Before
    fun setUp() {
        movieService = mockk()
        localMovieRepoImp = MockMovieRepoImp()
        remoteMovieRepo = RemoteMovieRepoImp(movieService)
    }

    @Test
    fun should_fetch_all_movies_from_api() {
        val data = fetchData(sampleMovieJsonApiResponse().jsonToMovieObject(),
            { remoteMovieRepo.getAllMoviesFromAPI(1) },
            { movieService.exploreAllMovies(1) })

        validateResult(data, 4) { movieService.exploreAllMovies(1) }
    }

    @Test
    fun should_fetch_now_playing_movies_from_api() {

        val data = fetchData(sampleMovieJsonResponseWithDateRange().jsonToMovieObject(),

            { remoteMovieRepo.getNowPlayingMoviesFromAPI(1) },
            { movieService.getNowPlayingMovies(1) })

        validateResult(data, 5) { movieService.getNowPlayingMovies(1) }
    }

    @Test
    fun should_fetch_upcoming_movies_from_api() {

        val data = fetchData(sampleMovieJsonResponseWithDateRange().jsonToMovieObject(),

            { remoteMovieRepo.getUpComingMoviesFromAPI(1) },
            { movieService.getUpcomingMovies(1) })

        validateResult(data, 5) { movieService.getUpcomingMovies(1) }
    }

    @Test
    fun should_fetch_popular_movie_from_api() {
        val data = fetchData(sampleMovieJsonApiResponse().jsonToMovieObject(),
            { remoteMovieRepo.getPopularMoviesFromAPI(1) },
            { movieService.getPopularMovies(1) })

        validateResult(data, 4) { movieService.getPopularMovies(1) }
    }

    @Test
    fun should_fetch_top_rated_movies_from_api() {
        val data = fetchData(sampleMovieJsonApiResponse().jsonToMovieObject(),

            { remoteMovieRepo.getTopRatedMoviesFromAPI(1) },
            { movieService.getTopRatedMovies(1) })

        validateResult(data, 4) { movieService.getTopRatedMovies(1) }
    }

    @Test
    fun api_error_should_throw_http_error_exception() {
        val expectedMsg = "Resource not found"
        // Create a mock for the apiCall function
        val apiCall = mockk<suspend () -> Response<BaseMovieApiResponse>>()

        // Create a non-empty error response with the specified status code and error message
        coEvery { apiCall.invoke() } returns Response.error(
            404, expectedMsg.toResponseBody()
        )

        // Call the executeAPiCall function with the mocked apiCall
        try {
            val result = runBlocking { remoteMovieRepo.executeAPICall(apiCall) }
            assertThat(result).isNull() // Ensure that the result is null since an exception should be thrown
        } catch (e: MyException.HttpException) {
            assertThat(e.errorMessage).isEqualTo(expectedMsg)
            // Ensure that the exception is caught
            assertThat(e.code).isEqualTo(404)
        } catch (e: Exception) {
            fail("Unexpected exception thrown: ${e.javaClass.simpleName}")
        }
    }

    @Test
    fun should_catch_ioexception_and_rethrow_as_networkException() {
        val expectedMsg = "Network error: IO error occurred"
        val apiCall = mockk<suspend () -> Response<BaseMovieApiResponse>>()

        coEvery { apiCall.invoke() } throws IOException(expectedMsg)
        try {
            runBlocking {
                remoteMovieRepo.executeAPICall(apiCall)

            }
            coVerify { remoteMovieRepo.executeAPICall(apiCall) }

        } catch (e: MyException.NetworkException) {
            assertThat(e).isInstanceOf(MyException.NetworkException::class.java)
            assertThat(e.errorMessage).isEqualTo(expectedMsg)
        } catch (e: Exception) {
            fail("Unexpected exception thrown: ${e.javaClass.simpleName} while expecting NetworkException")
        }
    }
}

private fun <T : BaseMovieApiResponse> fetchData(
    sampleDate: T, fetchData: suspend () -> T, apiCall: suspend () -> Response<T>
): T {
    coEvery { apiCall.invoke() } returns Response.success(
        sampleDate
    )
    return runBlocking { fetchData.invoke() }

}

private fun <T : BaseMovieApiResponse> validateResult(
    apiResponse: T, listSize: Int, apiCall: suspend () -> Response<T>
) {
    coVerify { apiCall.invoke() }
    assertThat(apiResponse).isNotNull()
    assertThat(apiResponse.results).isNotEmpty()
    assertThat(apiResponse.results.size).isEqualTo(listSize)
}
