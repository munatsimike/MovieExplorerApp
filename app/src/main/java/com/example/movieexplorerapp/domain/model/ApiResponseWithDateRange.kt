package com.example.movieexplorerapp.domain.model

import com.squareup.moshi.Json

data class ApiResponseWithDateRange(
    override val page: Int,
    override val results: List<Movie>,
    @Json(name = "total_pages")
    override val totalPages: Int,
    @Json(name = "total_results")
    override val totalResults: Int,
    @Json(name = "dates")
    val dataRange: MovieDateRange
) : ApiResponse(page, results, totalPages, totalResults) {
}