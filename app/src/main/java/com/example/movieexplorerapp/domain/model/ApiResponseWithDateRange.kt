package com.example.movieexplorerapp.domain.model

/**
 *
 * This interface extends BaseMovieAPiResponse and adds an extra field date range.
 * The date range filed will be used by movie objects with an extra date field like nowplaying, upcoming
 */
interface ApiResponseWithDateRange : BaseMovieApiResponse{
    override val page: Int
    override val results: List<Movie>
    override val totalPages: Int
    override val totalResults: Int
    val dataRange: MovieDateRange
}