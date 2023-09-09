package com.example.movieexplorerapp.domain.model

import com.squareup.moshi.Json

open class ApiResponse(
    open val page: Int,
    open val results: List<Movie>,
    @Json(name ="total_pages")
    open val totalPages: Int,
    @Json(name = "total_results")
    open val totalResults: Int
)