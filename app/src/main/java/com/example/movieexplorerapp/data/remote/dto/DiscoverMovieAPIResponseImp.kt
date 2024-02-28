package com.example.movieexplorerapp.data.remote.dto

import com.squareup.moshi.Json


data class DiscoverMovieAPIResponseImp(
    override val page: Int,
    override val results: List<Movie>,
    @Json(name = "total_pages") override val totalPages: Int,
    @Json(name = "total_results") override val totalResults: Int
) : BaseMovieApiResponse