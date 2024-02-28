package com.example.movieexplorerapp.data.local.database.converter

import androidx.room.TypeConverter
import com.example.movieexplorerapp.data.remote.dto.MovieDateRange
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class MovieDateRangeConverter {
    @TypeConverter
    fun fromMovieDateRangeToString(movieDateRange: MovieDateRange): String =
        Json.encodeToString(movieDateRange)

    @TypeConverter
    fun fromStringToMovieDateRange(string: String): MovieDateRange = Json.decodeFromString(string)
}