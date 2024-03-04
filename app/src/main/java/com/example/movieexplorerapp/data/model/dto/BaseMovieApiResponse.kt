package com.example.movieexplorerapp.data.model.dto

/**
 * This defines a basic a api response object
 * Common properties for this interface are found in all movie objects like popular, upcoming, discover and top rated movie object
 */
interface BaseMovieApiResponse {
    val page: Int
    val results: List<Movie>
    val totalPages: Int
    val totalResults: Int
}