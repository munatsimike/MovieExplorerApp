package com.example.movieexplorerapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movieexplorerapp.data.local.database.PAGINATION_META_DATA
import com.example.movieexplorerapp.data.local.model.MoviePaginationMetadata

@Dao
interface MoviePaginationMetadataDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPagingMetaData(metadata: MoviePaginationMetadata)

    @Query("SELECT * FROM $PAGINATION_META_DATA WHERE id = :id")
    suspend fun fetchPagingMetaData(id: String): List<MoviePaginationMetadata>

    @Query("DELETE FROM $PAGINATION_META_DATA WHERE id =:id")
    suspend fun deleteMovies(id: String)
}

