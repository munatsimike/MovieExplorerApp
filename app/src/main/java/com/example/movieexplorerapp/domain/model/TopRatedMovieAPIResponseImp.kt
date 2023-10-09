package com.example.movieexplorerapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.movieexplorerapp.data.local.database.TOP_RATED_MOVIE_TABLE_NAME
import com.squareup.moshi.Json

@Entity(tableName = TOP_RATED_MOVIE_TABLE_NAME)
data class TopRatedMovieAPIResponseImp(
    @PrimaryKey(autoGenerate = true)
    val primaryKey: Int = 0,
    override val page: Int,
    override val results: List<Movie>,
    @Json(name = "total_pages")
    override val totalPages: Int,
    @Json(name = "total_results")
    override val totalResults: Int
) : BaseMovieApiResponse