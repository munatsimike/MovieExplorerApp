package com.example.movieexplorerapp.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RawQuery
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQuery
import com.example.movieexplorerapp.data.local.database.DISCOVER_MOVIE_TABLE_NAME
import com.example.movieexplorerapp.data.local.database.DatabaseTable
import com.example.movieexplorerapp.data.local.database.NOW_PLAYING_MOVIE_TABLE_NAME
import com.example.movieexplorerapp.data.local.database.POPULAR_MOVIE_TABLE_NAME
import com.example.movieexplorerapp.data.local.database.TOP_RATED_MOVIE_TABLE_NAME
import com.example.movieexplorerapp.data.local.database.UPCOMING_MOVIE_TABLE_NAME
import com.example.movieexplorerapp.domain.model.DiscoverMovieAPIResponseImp
import com.example.movieexplorerapp.domain.model.NowPlayingMovieAPIResponseImp
import com.example.movieexplorerapp.domain.model.PopularMovieAPIResponseImp
import com.example.movieexplorerapp.domain.model.TopRatedMovieAPIResponseImp
import com.example.movieexplorerapp.domain.model.UpcomingMovieAPIResponseImp

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
    override suspend fun insertDiscover(discoverApiResponse: DiscoverMovieAPIResponseImp)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override suspend fun insertNowPlaying(nowPlaying: NowPlayingMovieAPIResponseImp)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override suspend fun insertPopular(popular: PopularMovieAPIResponseImp)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override suspend fun insertTopRated(topRated: TopRatedMovieAPIResponseImp)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override suspend fun insertUpcoming(upcoming: UpcomingMovieAPIResponseImp)

    // the following functions fetches the responses from the database
    @Query("SELECT * FROM $DISCOVER_MOVIE_TABLE_NAME")
    override fun fetchDiscover(): PagingSource<Int, DiscoverMovieAPIResponseImp>

    @Query("SELECT * FROM $NOW_PLAYING_MOVIE_TABLE_NAME")
    override fun fetchNowPlaying(): PagingSource<Int, NowPlayingMovieAPIResponseImp>

    @Query("SELECT * FROM $POPULAR_MOVIE_TABLE_NAME")
    override fun fetchPopular(): PagingSource<Int, PopularMovieAPIResponseImp>

    @Query("SELECT * FROM $TOP_RATED_MOVIE_TABLE_NAME")
    override fun fetchTopRated(): PagingSource<Int, TopRatedMovieAPIResponseImp>

    @Query("SELECT * FROM $UPCOMING_MOVIE_TABLE_NAME")
    override fun fetchUpcoming(): PagingSource<Int, UpcomingMovieAPIResponseImp>

    /**
     *     The following method clears all the tables. DatabaseTable  is an enum with all database table names
     *    Clears all data from the specified table in the database.
     */
    @RawQuery
    fun queryExecutor(query: SupportSQLiteQuery): Int

    override suspend fun clearTable(tableName: DatabaseTable) {
        val query = "DELETE FROM ${tableName.value}"
        queryExecutor(SimpleSQLiteQuery(query))
    }
}