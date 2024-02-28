package com.example.movieexplorerapp.data.local.repository

import com.example.movieexplorerapp.data.local.database.DatabaseTable
import com.example.movieexplorerapp.domain.model.DiscoverMovieAPIResponseImp
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class LocalMovieRepoImpTest {

    private lateinit var localMovieRepoImp: LocalMovieRepoImp

    @Before
    fun setup() {
      // localMovieRepoImp = LocalMovieRepoImp(movieDao = FakeMovieDao())
        runBlocking {
            localMovieRepoImp.clearTable(DatabaseTable.DISCOVER)
        }
    }

    @Test
    fun insertDiscover() = runBlocking {
        localMovieRepoImp.insertDiscover(DiscoverMovieAPIResponseImp(primaryKey = 1, page = 2, results = emptyList(), totalResults = 23, totalPages = 3))
      val result =  localMovieRepoImp.fetchDiscover()
        assertThat(result).isNull()
    }

    @Test
    fun insertNowPlaying() {
    }

    @Test
    fun insertPopular() {
    }

    @Test
    fun insertTopRated() {
    }

    @Test
    fun insertUpcoming() {
    }

    @Test
    fun fetchDiscover() {
        localMovieRepoImp.fetchDiscover()
    }

    @Test
    fun fetchNowPlaying() {
    }

    @Test
    fun fetchPopular() {
    }

    @Test
    fun fetchTopRated() {
    }

    @Test
    fun fetchUpcoming() {
    }

    @Test
    fun clearTable() {
    }
}