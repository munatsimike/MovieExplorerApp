package com.example.movieexplorerapp.data.remote.api

import com.example.movieexplorerapp.data.remote.dto.BaseMovieApiResponse
import com.example.movieexplorerapp.data.remote.dto.MovieAPIResponseImp
import com.example.movieexplorerapp.data.remote.dto.MovieAPIResponseWithDateImp
import com.example.movieexplorerapp.utils.test.TestUtils.sampleMovieJsonApiResponse
import com.example.movieexplorerapp.utils.test.TestUtils.sampleMovieJsonResponseWithDateRange
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Named

/**
 * The class tests various methods in the movieService class. Hilt is used to inject dependencies.
 * To isolate the moviesService from accessing the actual server the class uses a mockWebserver.
 * The httpResponse function contains the mockWebserver. It mimics the actual server response, it takes a request and json movie data sample and provides are response
 * ValidateAPi response checks if the server response is what is expected
 */
@HiltAndroidTest
class MovieServiceTest {
    @get:Rule
    var hilt = HiltAndroidRule(this)

    @Inject
    @Named("testMovieService")
    lateinit var movieService: MovieService

    @Inject
    lateinit var mockWebServer: MockWebServer

    @Before
    fun setup() {
        hilt.inject()
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun success_call_should_return_a_list_of_movies() {
        val movieResponse = sampleMovieJsonApiResponse()
        val response: Response<MovieAPIResponseImp> =
            httpResponse(movieResponse) { movieService.exploreAllMovies() }
        validateApiResponse(response, 4)
    }

    @Test
    fun success_call_should_return_upcoming_movies() {
        val movieResponse = sampleMovieJsonResponseWithDateRange()
        val response: Response<MovieAPIResponseWithDateImp> =
            httpResponse(movieResponse) { movieService.getUpcomingMovies() }
        validateApiResponse(response, 5)
    }

    @Test
    fun success_call_should_return_popular_movies() {
        val movieResponse = sampleMovieJsonApiResponse()
        val response: Response<MovieAPIResponseImp> =
            httpResponse(movieResponse) { movieService.getPopularMovies() }
        validateApiResponse(response, 4)
    }

    @Test
    fun success_call_should_return_now_playing_movies() {
        val movieResponse = sampleMovieJsonResponseWithDateRange()
        val response: Response<MovieAPIResponseWithDateImp> =
            httpResponse(movieResponse) { movieService.getNowPlayingMovies() }
        validateApiResponse(response, 5)
    }

    @Test
    fun success_call_should_return_top_rated_movies() {
        val movieResponse = sampleMovieJsonApiResponse()
        val response: Response<MovieAPIResponseImp> =
            httpResponse(movieResponse) { movieService.getTopRatedMovies() }
        validateApiResponse(response, 4)
    }

    // checks is the server response is what is expected
    private fun <T : BaseMovieApiResponse> validateApiResponse(
        response: Response<T>,
        listSize: Int
    ) {
        assertThat(response.isSuccessful).isTrue()
        assertThat(response.body()).isNotNull()

        assertThat(response.body()?.results).isNotEmpty()
        assertThat(response.body()?.results?.size).isEqualTo(listSize)
    }

    /**
     * Contains the logic to mock server responses using the provided mockWebServer.
     * This function takes a JSON body representing the server's response and a suspend function representing an HTTP call.
     * It enqueues a mock response with the given JSON body into the mockWebServer and executes the HTTP call.
     * Returns the response received from the mock server.
     */
    private fun <T : BaseMovieApiResponse> httpResponse(
        jsonBody: String, httpCall: suspend () -> Response<T>
    ): Response<T> {
        mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(jsonBody))
        return runBlocking { httpCall.invoke() }
    }
}