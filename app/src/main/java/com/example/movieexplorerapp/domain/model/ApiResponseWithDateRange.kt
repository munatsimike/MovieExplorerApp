package com.example.movieexplorerapp.domain.model

interface ApiResponseWithDateRange : BaseMovieApiResponse{
    override val page: Int
    override val results: List<Movie>
    override val totalPages: Int
    override val totalResults: Int
    val dataRange: MovieDateRange
}