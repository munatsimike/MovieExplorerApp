package com.example.movieexplorerapp.domain.model

interface BaseMovieApiResponse {
    val page: Int
    val results: List<Movie>
    val totalPages: Int
    val totalResults: Int
}