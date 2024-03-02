package com.example.movieexplorerapp.data.local.repository

import androidx.paging.testing.asSnapshot
import com.example.movieexplorerapp.data.dao.FakeMovieDao
import com.example.movieexplorerapp.data.local.dao.BaseMovieDao
import com.example.movieexplorerapp.data.local.database.LocalMovieDatabase
import com.example.movieexplorerapp.data.local.model.MovieCategory
import com.example.movieexplorerapp.data.local.model.MovieEntity
import com.example.movieexplorerapp.data.remote.repo.RemoteMovieRepoImp
import com.google.common.truth.Truth.assertThat
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class LocalMovieRepoImpTest {

    private lateinit var localMovieRepoImp: LocalMovieRepoImp

    @Before
    fun setup() {
        val remoteMovieRepoImp: RemoteMovieRepoImp = mockk()
        val database: LocalMovieDatabase = mockk()
        val movieDao: BaseMovieDao = FakeMovieDao()
        localMovieRepoImp = LocalMovieRepoImp(movieDao,remoteMovieRepoImp,database)
    }

    @Test
    fun insertMovies() = runBlocking {
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
        val movie3 = MovieEntity(
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

        val movie = listOf(movie1, movie2, movie3)

        localMovieRepoImp.insertMovies(movie)

        val snapshotList = localMovieRepoImp.fetchMovies(MovieCategory.Discover).asSnapshot {
            scrollTo(index = 2)
        }
        val result = mutableListOf<MovieEntity>()
        assertThat(snapshotList.size).isEqualTo(2)
        assertThat(result).contains(movie1)

    }

    @Test
    fun deleteMovies() {
    }
}