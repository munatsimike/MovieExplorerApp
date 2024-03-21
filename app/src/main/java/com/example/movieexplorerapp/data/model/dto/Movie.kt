package com.example.movieexplorerapp.data.model.dto

import com.squareup.moshi.Json
import kotlinx.serialization.Serializable


@Serializable
open class Movie(
    open val adult: Boolean,
    @Json(name = "backdrop_path") open val backdropPath: String?,
    @Json(name = "genre_ids") open val genreIds: List<Int>,
    open val id: Int,
    @Json(name = "original_language") open val originalLanguage: String,
    @Json(name = "original_title") open val originalTitle: String,
    open val overview: String,
    open val popularity: Double?,
    @Json(name = "poster_path") open val posterPath: String?,
    @Json(name = "release_date") open val releaseDate: String,
    open val title: String,
    open val video: Boolean?,
    @Json(name = "vote_average") open val voteAverage: Double,
    @Json(name = "vote_count") open val voteCount: Int
)