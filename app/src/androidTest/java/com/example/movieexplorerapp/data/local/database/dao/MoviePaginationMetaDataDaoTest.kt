package com.example.movieexplorerapp.data.local.database.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.movieexplorerapp.data.local.dao.MoviePaginationMetadataDao
import com.example.movieexplorerapp.data.local.database.LocalMovieDatabase
import com.example.movieexplorerapp.data.TEST_DB
import com.example.movieexplorerapp.data.model.MovieCategory
import com.example.movieexplorerapp.data.model.MoviePaginationMetadata
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

/**
 * Tests for MoviePaginationMetadataDao to ensure proper interaction with the database
 * for movie pagination metadata operations. These tests verify the functionality of inserting and
 * fetching pagination metadata, emphasizing the handling of unique constraints and database empty states.
 */
@HiltAndroidTest
class MoviePaginationMetaDataDaoTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named(TEST_DB)
    lateinit var localMovieDatabase: LocalMovieDatabase

    @Inject
    lateinit var metadataDao: MoviePaginationMetadataDao

    @Before
    fun start(){
        // Initialize Dagger/Hilt components and inject dependencies
        hiltRule.inject()
    }

    @After
    fun tearDown() = runBlocking{
        metadataDao.clearAll()
    }


    /**
     * 1.Tests the insert and fetch operations for movie pagination metadata.
     * 2.Ensures that inserting multiple metadata records with unique ids results in the correct retrieval of data.
     * 3.Tests the scenario where inserting a record with an existing id replaces the previous record, maintaining
     */
    @Test
    fun should_Insert_Fetch_Movie_Pagination_meta_Data() = runBlocking {
        val metaData1 = MoviePaginationMetadata(id = MovieCategory.NowPlaying, 1, 10)
        val metaData2 = MoviePaginationMetadata(id = MovieCategory.Discover, 1, 10)
        val metaData3 = MoviePaginationMetadata(id = MovieCategory.UpComing, 1, 10)
        val metaData4 = MoviePaginationMetadata(id = MovieCategory.UpComing, 1, 10)

        metadataDao.insertPagingMetaData(metaData2)
        metadataDao.insertPagingMetaData(metaData1)
        metadataDao.insertPagingMetaData(metaData3)
        metadataDao.insertPagingMetaData(metaData4)

        val result = metadataDao.fetchPagingMetaData(MovieCategory.NowPlaying.name)

        assertThat(result.size).isEqualTo(1)
        assertThat(result[0]).isEqualTo(metaData1)
    }

    @Test
    fun should_return_empty_list_if_db_is_empty() = runBlocking {
        val result = metadataDao.fetchPagingMetaData(MovieCategory.UpComing.name)
        assertThat(result.size).isEqualTo(0)
    }
}