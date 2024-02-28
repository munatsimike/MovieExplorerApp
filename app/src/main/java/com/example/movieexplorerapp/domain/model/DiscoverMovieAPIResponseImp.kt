package com.example.movieexplorerapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.movieexplorerapp.data.local.database.DISCOVER_MOVIE_TABLE_NAME
import com.squareup.moshi.Json
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = DISCOVER_MOVIE_TABLE_NAME)
data class DiscoverMovieAPIResponseImp(
    @PrimaryKey(autoGenerate = true)
    val primaryKey: Int? = null,
    override val page: Int = 0,
    override val results: List<Movie> = emptyList(),
    @Json(name = "total_pages") override val totalPages: Int = 0,
    @Json(name = "total_results") override val totalResults: Int = 0
) : BaseMovieApiResponse