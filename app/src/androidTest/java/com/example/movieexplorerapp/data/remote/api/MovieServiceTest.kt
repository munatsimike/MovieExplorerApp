package com.example.movieexplorerapp.data.remote.api

import com.example.movieexplorerapp.domain.model.ApiResponse
import com.example.movieexplorerapp.domain.model.ApiResponseWithDateRange
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
        val response: Response<ApiResponse> =
            httpResponse(movieResponse) { movieService.exploreAllMovies() }
        validateApiResponse(response, 4)
    }

    @Test
    fun success_call_should_return_upcoming_movies() {
        val movieResponse = sampleMovieJsonResponseWithDateRange()
        val response: Response<ApiResponseWithDateRange> =
            httpResponse(movieResponse) { movieService.getUpcomingMovies() }
        validateApiResponse(response, 5)
    }

    @Test
    fun success_call_should_return_popular_movies() {
        val movieResponse = sampleMovieJsonApiResponse()
        val response: Response<ApiResponse> =
            httpResponse(movieResponse) { movieService.getPopularMovies() }
        validateApiResponse(response, 4)
    }

    @Test
    fun success_call_should_return_now_playing_movies() {
        val movieResponse = sampleMovieJsonResponseWithDateRange()
        val response: Response<ApiResponseWithDateRange> =
            httpResponse(movieResponse) { movieService.getNowPlayingMovies() }
        validateApiResponse(response, 5)
    }

    @Test
    fun success_call_should_return_top_rated_movies() {
        val movieResponse = sampleMovieJsonApiResponse()
        val response: Response<ApiResponse> =
            httpResponse(movieResponse) { movieService.getTopRatedMovies() }
        validateApiResponse(response, 4)
    }


    private fun <T : ApiResponse> validateApiResponse(response: Response<T>, listSize: Int) {
        assertThat(response.isSuccessful).isTrue()
        assertThat(response.body()).isNotNull()

        assertThat(response.body()?.results).isNotEmpty()
        assertThat(response.body()?.results?.size).isEqualTo(listSize)
    }

    private fun <T : ApiResponse> httpResponse(
        jsonBody: String, httpCall: suspend () -> Response<T>
    ): Response<T> {
        mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(jsonBody))
        return runBlocking { httpCall.invoke() }
    }
}