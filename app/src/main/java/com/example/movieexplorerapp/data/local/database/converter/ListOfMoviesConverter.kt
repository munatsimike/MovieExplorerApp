package com.example.movieexplorerapp.data.local.database.converter

import androidx.room.TypeConverter
import com.example.movieexplorerapp.data.model.dto.Movie
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class ListOfMoviesConverter {
    @TypeConverter
    fun fromListToString(strList: List<Movie>): String {
        return strList.let {  Json.encodeToString(strList)}
    }

    @TypeConverter
    fun fromStringToList(str: String): List<Movie> {
        return str.let {  Json.decodeFromString(str)} ?: emptyList()
    }
}