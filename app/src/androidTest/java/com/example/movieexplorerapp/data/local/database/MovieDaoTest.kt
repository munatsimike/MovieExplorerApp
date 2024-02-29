package com.example.movieexplorerapp.data.local.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingSource
import com.example.movieexplorerapp.data.local.dao.MovieDao
import com.example.movieexplorerapp.data.local.model.MovieEntity
import com.example.movieexplorerapp.data.remote.dto.Movie
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
        val sampleData = emptyList<MovieEntity>()

        movieDao.insertDiscover(sampleData)

        // Create a PagingSource
        val pagingSource: PagingSource<Int, MovieEntity> = movieDao.fetchDiscover()

        val loadResult = loadPagingSourceData(pagingSource)
        validateLoadResult(loadResult, sampleData)
    }


    @Test
    fun should_insert_and_fetch_now_playing_movie_object_from_database() = runTest {
        val sampleData = emptyList<MovieEntity>()

        movieDao.insertNowPlaying(sampleData)

        // Create a PagingSource
        val pagingSource: PagingSource<Int, MovieEntity> = movieDao.fetchNowPlaying()

        val loadResult = loadPagingSourceData(pagingSource)
        validateLoadResult(loadResult, sampleData)
    }

    @Test
    fun should_insert_and_fetch_popular_movie_object_from_database() = runTest {
        val sampleData = emptyList<MovieEntity>()

        movieDao.insertPopular(sampleData)

        // Create a PagingSource
        val pagingSource: PagingSource<Int, MovieEntity> = movieDao.fetchPopular()

        val loadResult = loadPagingSourceData(pagingSource)
        validateLoadResult(loadResult, sampleData)
    }

    @Test
    fun should_insert_and_fetch_top_rated_movie_object_from_database() = runTest {
        val sampleData = emptyList<MovieEntity>()

        movieDao.insertTopRated(sampleData)

        // Create a PagingSource
        val pagingSource: PagingSource<Int, MovieEntity> = movieDao.fetchTopRated()

        val loadResult = loadPagingSourceData(pagingSource)
        validateLoadResult(loadResult, sampleData)
    }

    @Test
    fun should_insert_and_fetch_upcoming_movie_object_from_database() = runTest {
        val sampleData = emptyList<MovieEntity>()

        movieDao.insertUpcoming(sampleData)

        // Create a PagingSource
        val pagingSource: PagingSource<Int, MovieEntity> = movieDao.fetchUpcoming()

        val loadResult = loadPagingSourceData(pagingSource)
        validateLoadResult(loadResult, sampleData)
    }

    // Assert that the loaded data matches the expected data
    private fun validateLoadResult(
        loadResult: PagingSource.LoadResult<Int, MovieEntity>, sampleData: List<MovieEntity>
    ) {

        assertThat(loadResult is PagingSource.LoadResult.Page).isTrue()
        val pageData = (loadResult as PagingSource.LoadResult.Page).data
        assertThat(pageData.size == sampleData.size).isTrue()
        assertThat(pageData == sampleData).isTrue()
    }

    // Load the data from the PagingSource
    private suspend fun loadPagingSourceData(
        pagingSource: PagingSource<Int, MovieEntity>, loadSize: Int = 10
    ): PagingSource.LoadResult<Int, MovieEntity> {
        return pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null, loadSize = loadSize, placeholdersEnabled = false
            )
        )
    }
}