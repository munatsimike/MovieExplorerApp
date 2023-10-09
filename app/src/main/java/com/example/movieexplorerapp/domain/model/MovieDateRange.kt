package com.example.movieexplorerapp.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class MovieDateRange(
    val maximum: String,
    val minimum: String
)