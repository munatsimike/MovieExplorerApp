package com.example.movieexplorerapp.data.local.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingSource
import com.example.movieexplorerapp.data.local.dao.MovieDao
import com.example.movieexplorerapp.domain.model.BaseMovieApiResponse
import com.example.movieexplorerapp.domain.model.DiscoverMovieAPIResponseImp
import com.example.movieexplorerapp.domain.model.Movie
import com.example.movieexplorerapp.domain.model.MovieDateRange
import com.example.movieexplorerapp.domain.model.NowPlayingMovieAPIResponseImp
import com.example.movieexplorerapp.domain.model.PopularMovieAPIResponseImp
import com.example.movieexplorerapp.domain.model.TopRatedMovieAPIResponseImp
import com.example.movieexplorerapp.domain.model.UpcomingMovieAPIResponseImp
import com.example.movieexplorerapp.utils.test.TestUtils.listOfMovies
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@HiltAndroidTest
class MovieDaoTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    // Rule for immediate execution of LiveData operations on the main thread in tests
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named(TEST_DB_NAME)
    lateinit var movieLocalDatabase: LocalMovieDatabase

    @Inject
    lateinit var movieDao: MovieDao

    private lateinit var movieList: List<Movie>

    @Before
    fun setup() {
        hiltRule.inject()
        movieList = listOfMovies()
    }

    @After
    fun tearDown() {
        movieLocalDatabase.close()
    }

    @Test
    fun should_insert_and_fetch_discover_movie_object_from_database() = runTest {
        val sampleData = DiscoverMovieAPIResponseImp(
            primaryKey = 1, page = 1, results = movieList, totalPages = 2, totalResults = 4
        )

        movieDao.insertDiscover(sampleData)

        // Create a PagingSource
        val pagingSource: PagingSource<Int, DiscoverMovieAPIResponseImp> = movieDao.fetchDiscover()

        val loadResult = loadPagingSourceData(pagingSource)
        validateLoadResult(loadResult, sampleData)
    }


    @Test
    fun should_insert_and_fetch_now_playing_movie_object_from_database() = runTest {
        val sampleData = NowPlayingMovieAPIResponseImp(
            primaryKey = 1, page = 1, results = movieList, totalPages = 2, totalResults = 4, dataRange = MovieDateRange("","")
        )

        movieDao.insertNowPlaying(sampleData)

        // Create a PagingSource
        val pagingSource: PagingSource<Int, NowPlayingMovieAPIResponseImp> = movieDao.fetchNowPlaying()

        val loadResult = loadPagingSourceData(pagingSource)
        validateLoadResult(loadResult, sampleData)
    }

    @Test
    fun should_insert_and_fetch_popular_movie_object_from_database() = runTest {
        val sampleData = PopularMovieAPIResponseImp(
            primaryKey = 1, page = 1, results = movieList, totalPages = 2, totalResults = 4
        )

        movieDao.insertPopular(sampleData)

        // Create a PagingSource
        val pagingSource: PagingSource<Int, PopularMovieAPIResponseImp> = movieDao.fetchPopular()

        val loadResult = loadPagingSourceData(pagingSource)
        validateLoadResult(loadResult, sampleData)
    }

    @Test
    fun should_insert_and_fetch_top_rated_movie_object_from_database() = runTest {
        val sampleData = TopRatedMovieAPIResponseImp(
            primaryKey = 1, page = 1, results = movieList, totalPages = 2, totalResults = 4)

        movieDao.insertTopRated(sampleData)

        // Create a PagingSource
        val pagingSource: PagingSource<Int, TopRatedMovieAPIResponseImp> = movieDao.fetchTopRated()

        val loadResult = loadPagingSourceData(pagingSource)
        validateLoadResult(loadResult, sampleData)
    }

    @Test
    fun should_insert_and_fetch_upcoming_movie_object_from_database() = runTest {
        val sampleData = UpcomingMovieAPIResponseImp(
            primaryKey = 1, page = 1, results = movieList, totalPages = 2, totalResults = 4, dataRange = MovieDateRange("","")
        )

        movieDao.insertUpcoming(sampleData)

        // Create a PagingSource
        val pagingSource: PagingSource<Int, UpcomingMovieAPIResponseImp> = movieDao.fetchUpcoming()

        val loadResult = loadPagingSourceData(pagingSource)
        validateLoadResult(loadResult, sampleData)
    }
    // Assert that the loaded data matches the expected data
    private fun <T : BaseMovieApiResponse> validateLoadResult(
        loadResult: PagingSource.LoadResult<Int, T>, sampleData: T
    ) {

        assertThat(loadResult is PagingSource.LoadResult.Page).isTrue()
        val pageData = (loadResult as PagingSource.LoadResult.Page).data
        assertThat(pageData[0].results.size == sampleData.results.size).isTrue()
        assertThat(pageData[0] == sampleData).isTrue()
    }

    // Load the data from the PagingSource
    private suspend fun <T : BaseMovieApiResponse> loadPagingSourceData(
        pagingSource: PagingSource<Int, T>, loadSize: Int = 10
    ): PagingSource.LoadResult<Int, T> {
        return pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null, loadSize = loadSize, placeholdersEnabled = false
            )
        )
    }
}