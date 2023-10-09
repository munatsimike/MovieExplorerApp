package com.example.movieexplorerapp.data.local.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RawQuery
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQuery
import com.example.movieexplorerapp.domain.model.DiscoverMovieAPIResponseImp
import com.example.movieexplorerapp.domain.model.NowPlayingMovieAPIResponseImp
import com.example.movieexplorerapp.domain.model.PopularMovieAPIResponseImp
import com.example.movieexplorerapp.domain.model.TopRatedMovieAPIResponseImp
import com.example.movieexplorerapp.domain.model.UpcomingMovieAPIResponseImp

@Dao
interface MovieDao {
    // the following methods inserts api response into room the response contains a list of movies and
    // additional info like page numbers and total pages
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDiscover(discoverApiResponse: DiscoverMovieAPIResponseImp)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNowPlaying(nowPlaying: NowPlayingMovieAPIResponseImp)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPopular(popular: PopularMovieAPIResponseImp)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTopRated(topRated: TopRatedMovieAPIResponseImp)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUpcoming(upcoming: UpcomingMovieAPIResponseImp)

    // the following functions fetches the responses from the database
    @Query("SELECT * FROM $DISCOVER_MOVIE_TABLE_NAME")
    fun fetchDiscover(): PagingSource<Int, DiscoverMovieAPIResponseImp>
    @Query("SELECT * FROM $NOW_PLAYING_MOVIE_TABLE_NAME")
    fun fetchNowPlaying(): PagingSource<Int, NowPlayingMovieAPIResponseImp>
    @Query("SELECT * FROM $POPULAR_MOVIE_TABLE_NAME")
    fun fetchPopular(): PagingSource<Int, PopularMovieAPIResponseImp>
    @Query("SELECT * FROM $TOP_RATED_MOVIE_TABLE_NAME")
    fun fetchTopRated():PagingSource<Int, TopRatedMovieAPIResponseImp>
    @Query("SELECT * FROM $UPCOMING_MOVIE_TABLE_NAME")
    fun fetchUpcoming():PagingSource<Int, UpcomingMovieAPIResponseImp>

    // the following method clears all the tables. Database table is an enum with all database table names
    @RawQuery
    fun queryExecutor(query: SupportSQLiteQuery): Int

    suspend fun clearTable(tableName: DatabaseTable) {
        val query = queryBuilder("DELETE FROM ${tableName.value}")
        queryExecutor(query)
    }

    private fun queryBuilder(query: String): SupportSQLiteQuery {
        return SimpleSQLiteQuery(query)
    }
}