package com.example.movieexplorerapp.data.model.dto

import com.squareup.moshi.Json

/**
 * This defines a movie response with the extra date range field.
 * This will be used by upcoming and now playing movie objects
 */

data class MovieAPIResponseWithDateImp(
    override val page: Int,
    override val results: List<Movie>,
    @Json(name = "total_pages")
    override val totalPages: Int,
    @Json(name = "total_results")
    override val totalResults: Int,
    @Json(name = "dates")
    val dataRange: MovieDateRange
) : BaseMovieApiResponse