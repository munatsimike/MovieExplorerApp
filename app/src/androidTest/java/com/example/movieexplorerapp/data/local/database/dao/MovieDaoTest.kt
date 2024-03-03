package com.example.movieexplorerapp.data.local.database.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingSource
import com.example.movieexplorerapp.data.local.dao.MovieDao
import com.example.movieexplorerapp.data.local.database.LocalMovieDatabase
import com.example.movieexplorerapp.data.local.database.TEST_DB
import com.example.movieexplorerapp.data.local.model.MovieCategory
import com.example.movieexplorerapp.data.local.model.MovieEntity
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

/**
 * This class contains test cases to test the movie dao.
 * The class uses an in memory database (injected by hilt) to insert and fetch various movies based on their category
 * The movie samples are from the api which does the filtering. looking at the movies samples they look the same, so it is not clear how the backend does the filtering.
 *
 */
@HiltAndroidTest
class MovieDaoTest {

    // Rule for Hilt setup
    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    // Rule for immediate execution of LiveData operations on the main thread in tests
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named(TEST_DB)
    lateinit var movieLocalDatabase: LocalMovieDatabase

    @Inject
    lateinit var movieDao: MovieDao

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @After
    fun tearDown() {
        movieLocalDatabase.close()
    }

    @Test
    fun should_insert_and_fetch_discover_movie_object_from_database() = runTest {
        val movie1 = MovieEntity(
            adult = false,
            backdropPath = "/ctMserH8g2SeOAnCw5gFjdQF8mo.jpg",
            genreIds = listOf(35, 12, 14),
            id = 346698,
            originalLanguage = "en",
            originalTitle = "Barbie",
            overview = "Barbie and Ken are having the time of their lives in the colorful and seemingly perfect world of Barbie Land. However, when they get a chance to go to the real world, they soon discover the joys and perils of living among humans.",
            popularity = 3176.933,
            posterPath = "/iuFNMS8U5cb6xfzi51Dbkovj7vM.jpg",
            releaseDate = "2023-07-19",
            title = "Barbie",
            video = false,
            voteAverage = 7.4,
            voteCount = 3954,
            category = MovieCategory.Discover // Placeholder for manual addition
        )

        val movie2 = MovieEntity(
            adult = false,
            backdropPath = "/8pjWz2lt29KyVGoq1mXYu6Br7dE.jpg",
            genreIds = listOf(28, 878, 27),
            id = 615656,
            originalLanguage = "en",
            originalTitle = "Meg 2: The Trench",
            overview = "An exploratory dive into the deepest depths of the ocean of a daring research team spirals into chaos when a malevolent mining operation threatens their mission and forces them into a high-stakes battle for survival.",
            popularity = 3784.306,
            posterPath = "/drCySAAAvegq1vQRGRqPKN9f00w.jpg",
            releaseDate = "2023-08-02",
            title = "Meg 2: The Trench",
            video = false,
            voteAverage = 7.0,
            voteCount = 1523,
            category = MovieCategory.Discover // Placeholder for manual addition
        )

        val sampleData = listOf(movie1, movie2)

        movieDao.insertMovies(sampleData)

        // Create a PagingSource
        val pagingSource: PagingSource<Int, MovieEntity> =
            movieDao.fetchMovies(MovieCategory.Discover)

        val loadResult = loadPagingSourceData(pagingSource)
        validateLoadData(loadResult, sampleData)
    }

    @Test
    fun should_insert_and_fetch_now_playing_movie_object_from_database() = runTest {

        val movie1 = MovieEntity(
            adult = false,
            backdropPath = "/4woSOUD0equAYzvwhWBHIJDCM88.jpg",
            genreIds = listOf(28, 27, 53),
            id = 1096197,
            originalLanguage = "en",
            originalTitle = "No Way Up",
            overview = "Characters from different backgrounds are thrown together when the plane they're travelling on crashes into the Pacific Ocean. A nightmare fight for survival ensues with the air supply running out and dangers creeping in from all sides.",
            popularity = 2853.409,
            posterPath = "/hu40Uxp9WtpL34jv3zyWLb5zEVY.jpg",
            releaseDate = "2024-01-18",
            title = "No Way Up",
            video = false,
            voteAverage = 5.7,
            voteCount = 65,
            category = MovieCategory.NowPlaying // Placeholder for manual addition
        )

        val movie2 = MovieEntity(
            adult = false,
            backdropPath = "/oBIQDKcqNxKckjugtmzpIIOgoc4.jpg",
            genreIds = listOf(28, 53, 10752),
            id = 969492,
            originalLanguage = "en",
            originalTitle = "Land of Bad",
            overview = "When a Delta Force special ops mission goes terribly wrong, Air Force drone pilot Reaper has 48 hours to remedy what has devolved into a wild rescue operation. With no weapons and no communication other than the drone above, the ground mission suddenly becomes a full-scale battle when the team is discovered by the enemy.",
            popularity = 2089.144,
            posterPath = "/h3jYanWMEJq6JJsCopy1h7cT2Hs.jpg",
            releaseDate = "2024-01-25",
            title = "Land of Bad",
            video = false,
            voteAverage = 7.01,
            voteCount = 200,
            category = MovieCategory.NowPlaying // Placeholder for manual addition
        )

        val sampleData = listOf(movie1, movie2)

        movieDao.insertMovies(sampleData)

        // Create a PagingSource
        val pagingSource = movieDao.fetchMovies(MovieCategory.NowPlaying)

        val loadResult = loadPagingSourceData(pagingSource)
        validateLoadData(loadResult, sampleData)
    }

    @Test
    fun should_insert_and_fetch_popular_movie_object_from_database() = runTest {
        val movie1 = MovieEntity(
            adult = false,
            backdropPath = "/4woSOUD0equAYzvwhWBHIJDCM88.jpg",
            genreIds = listOf(28, 27, 53),
            id = 1096197,
            originalLanguage = "en",
            originalTitle = "No Way Up",
            overview = "Characters from different backgrounds are thrown together when the plane they're travelling on crashes into the Pacific Ocean. A nightmare fight for survival ensues with the air supply running out and dangers creeping in from all sides.",
            popularity = 2853.409,
            posterPath = "/hu40Uxp9WtpL34jv3zyWLb5zEVY.jpg",
            releaseDate = "2024-01-18",
            title = "No Way Up",
            video = false,
            voteAverage = 5.7,
            voteCount = 65,
            category = MovieCategory.Popular // Placeholder for manual addition
        )

        val movie2 = MovieEntity(
            adult = false,
            backdropPath = "/oBIQDKcqNxKckjugtmzpIIOgoc4.jpg",
            genreIds = listOf(28, 53, 10752),
            id = 969492,
            originalLanguage = "en",
            originalTitle = "Land of Bad",
            overview = "When a Delta Force special ops mission goes terribly wrong, Air Force drone pilot Reaper has 48 hours to remedy what has devolved into a wild rescue operation. With no weapons and no communication other than the drone above, the ground mission suddenly becomes a full-scale battle when the team is discovered by the enemy.",
            popularity = 2089.144,
            posterPath = "/h3jYanWMEJq6JJsCopy1h7cT2Hs.jpg",
            releaseDate = "2024-01-25",
            title = "Land of Bad",
            video = false,
            voteAverage = 7.01,
            voteCount = 200,
            category = MovieCategory.Popular // Placeholder for manual addition
        )

        val sampleData = listOf(movie1, movie2)

        movieDao.insertMovies(sampleData)

        val pagingSource = movieDao.fetchMovies(MovieCategory.Popular)

        val loadResult = loadPagingSourceData(pagingSource)
        validateLoadData(loadResult, sampleData)
    }

    @Test
    fun should_insert_and_fetch_top_rated_movie_object_from_database() = runTest {
        val movie1 = MovieEntity(
            adult = false,
            backdropPath = "/kXfqcdQKsToO0OUXHcrrNCHDBzO.jpg",
            genreIds = listOf(18, 80),
            id = 278,
            originalLanguage = "en",
            originalTitle = "The Shawshank Redemption",
            overview = "Framed in the 1940s for the double murder of his wife and her lover, upstanding banker Andy Dufresne begins a new life at the Shawshank prison, where he puts his accounting skills to work for an amoral warden. During his long stretch in prison, Dufresne comes to be admired by the other inmates -- including an older prisoner named Red -- for his integrity and unquenchable sense of hope.",
            popularity = 151.435,
            posterPath = "/9cqNxx0GxF0bflZmeSMuL5tnGzr.jpg",
            releaseDate = "1994-09-23",
            title = "The Shawshank Redemption",
            video = false,
            voteAverage = 8.704,
            voteCount = 25684,
            category = MovieCategory.TopRated // Placeholder for manual addition
        )

        val movie2 = MovieEntity(
            adult = false,
            backdropPath = "/tmU7GeKVybMWFButWEGl2M4GeiP.jpg",
            genreIds = listOf(18, 80),
            id = 238,
            originalLanguage = "en",
            originalTitle = "The Godfather",
            overview = "Spanning the years 1945 to 1955, a chronicle of the fictional Italian-American Corleone crime family. When organized crime family patriarch, Vito Corleone barely survives an attempt on his life, his youngest son, Michael steps in to take care of the would-be killers, launching a campaign of bloody revenge.",
            popularity = 129.454,
            posterPath = "/3bhkrj58Vtu7enYsRolD1fZdja1.jpg",
            releaseDate = "1972-03-14",
            title = "The Godfather",
            video = false,
            voteAverage = 8.696,
            voteCount = 19515,
            category = MovieCategory.TopRated // Placeholder for manual addition
        )

        val sampleData = listOf(movie1, movie2)

        movieDao.insertMovies(sampleData)

        // Create a PagingSource
        val pagingSource: PagingSource<Int, MovieEntity> =
            movieDao.fetchMovies(MovieCategory.TopRated)

        val loadResult = loadPagingSourceData(pagingSource)
        validateLoadData(loadResult, sampleData)
    }

    @Test
    fun should_insert_and_fetch_upcoming_movie_object_from_database() = runTest {
        val movie1 = MovieEntity(
            adult = false,
            backdropPath = "/oBIQDKcqNxKckjugtmzpIIOgoc4.jpg",
            genreIds = listOf(28, 53, 10752),
            id = 969492,
            originalLanguage = "en",
            originalTitle = "Land of Bad",
            overview = "When a Delta Force special ops mission goes terribly wrong, Air Force drone pilot Reaper has 48 hours to remedy what has devolved into a wild rescue operation. With no weapons and no communication other than the drone above, the ground mission suddenly becomes a full-scale battle when the team is discovered by the enemy.",
            popularity = 2089.144,
            posterPath = "/h3jYanWMEJq6JJsCopy1h7cT2Hs.jpg",
            releaseDate = "2024-01-25",
            title = "Land of Bad",
            video = false,
            voteAverage = 7.023,
            voteCount = 199,
            category = MovieCategory.UpComing // Placeholder for manual addition
        )

        val movie2 = MovieEntity(
            adult = false,
            backdropPath = "/nTPFkLUARmo1bYHfkfdNpRKgEOs.jpg",
            genreIds = listOf(35, 10749),
            id = 1072790,
            originalLanguage = "en",
            originalTitle = "Anyone But You",
            overview = "After an amazing first date, Bea and Ben’s fiery attraction turns ice cold — until they find themselves unexpectedly reunited at a destination wedding in Australia. So they do what any two mature adults would do: pretend to be a couple.",
            popularity = 1454.065,
            posterPath = "/yRt7MGBElkLQOYRvLTT1b3B1rcp.jpg",
            releaseDate = "2023-12-21",
            title = "Anyone But You",
            video = false,
            voteAverage = 6.896,
            voteCount = 684,
            category = MovieCategory.UpComing // Placeholder for manual addition
        )

        val sampleData = listOf(movie1, movie2)

        movieDao.insertMovies(sampleData)

        // Create a PagingSource
        val pagingSource: PagingSource<Int, MovieEntity> =
            movieDao.fetchMovies(MovieCategory.UpComing)

        val loadResult = loadPagingSourceData(pagingSource)
        validateLoadData(loadResult, sampleData)
    }


    @Test
    fun should_insert_and_fetch_empty_list ()= runTest {
        val sampleData = listOf<MovieEntity>()

        movieDao.insertMovies(sampleData)

        // Create a PagingSource
        val pagingSource: PagingSource<Int, MovieEntity> =
            movieDao.fetchMovies(MovieCategory.UpComing)

        val loadResult = loadPagingSourceData(pagingSource)
        validateLoadData(loadResult, sampleData)
    }

    // Assert that the loaded data matches the expected data
    private fun validateLoadData(
        loadResult: PagingSource.LoadResult<Int, MovieEntity>, sampleData: List<MovieEntity>
    ) {
        assertThat(loadResult is PagingSource.LoadResult.Page).isTrue()
        val pageData = (loadResult as PagingSource.LoadResult.Page).data
        assertThat(pageData.size).isEqualTo(sampleData.size)
        assertThat(pageData).isEqualTo(sampleData)
    }

    // Load the data from the PagingSource
    private suspend fun loadPagingSourceData(
        pagingSource: PagingSource<Int, MovieEntity>, loadSize: Int = 2
    ): PagingSource.LoadResult<Int, MovieEntity> {
        return pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null, loadSize = loadSize, placeholdersEnabled = false
            )
        )
    }
}