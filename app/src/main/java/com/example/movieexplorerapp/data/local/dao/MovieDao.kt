package com.example.movieexplorerapp.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RawQuery
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQuery
import com.example.movieexplorerapp.data.local.database.MOVIE_ENTITY
import com.example.movieexplorerapp.data.local.model.MovieEntity

/**
 * MovieDao contains suspending functions to interact with the local Room database.
 * It handles different API responses containing various movie data, which are inserted and fetched from the database.
 * Data fetched from the database is returned using PagingSource to handle pagination.
 * The clearTable function uses raw queries to clear tables. It takes the table name as a parameter and executes the query.
 */
@Dao
interface MovieDao : BaseMovieDao {
    /**
     *  The following methods inserts api response into room the response contains a list of movies and
     *  additional info like page numbers and total pages
     */

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override suspend fun insertDiscover(discoverApiResponse: List<MovieEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override suspend fun insertNowPlaying(nowPlaying: List<MovieEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override suspend fun insertPopular(popular: List<MovieEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override suspend fun insertTopRated(topRated: List<MovieEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override suspend fun insertUpcoming(upcoming: List<MovieEntity>)

    // the following functions fetches the responses from the database
    @Query("SELECT * FROM $MOVIE_ENTITY")
    override fun fetchDiscover(): PagingSource<Int, MovieEntity>

    @Query("SELECT * FROM $MOVIE_ENTITY")
    override fun fetchNowPlaying(): PagingSource<Int, MovieEntity>

    @Query("SELECT * FROM $MOVIE_ENTITY")
    override fun fetchPopular(): PagingSource<Int, MovieEntity>

    @Query("SELECT * FROM $MOVIE_ENTITY")
    override fun fetchTopRated(): PagingSource<Int, MovieEntity>

    @Query("SELECT * FROM $MOVIE_ENTITY")
    override fun fetchUpcoming(): PagingSource<Int, MovieEntity>

    /**
     *     The following method clears all the tables. DatabaseTable  is an enum with all database table names
     *    Clears all data from the specified table in the database.
     */
    @RawQuery
    fun queryExecutor(query: SupportSQLiteQuery): Int

    override suspend fun clearTable(tableName: String) {
        val query = "DELETE FROM $tableName"
        queryExecutor(SimpleSQLiteQuery(query))
    }
}