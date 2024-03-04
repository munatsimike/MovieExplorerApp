package com.example.movieexplorerapp.data.model.dto

import com.squareup.moshi.Json

/**
 * This defines a movie response without the extra date range field
 * This will be used by discover, popular and top rated movie objects
 */

data class MovieAPIResponseImp(
    override val page: Int,
    override val results: List<Movie>,
    @Json(name = "total_pages") override val totalPages: Int,
    @Json(name = "total_results") override val totalResults: Int
) : BaseMovieApiResponse