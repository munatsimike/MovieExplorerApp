package com.example.movieexplorerapp.data.model.dto

import kotlinx.serialization.Serializable

@Serializable
data class MovieDateRange(
    val maximum: String,
    val minimum: String
)