package com.example.movieexplorerapp.data.local.database.converter

import androidx.room.TypeConverter
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class ListOfIntegersConverter {
    @TypeConverter
    fun fromListToString(list: List<Int>): String = Json.encodeToString(list)

    @TypeConverter
    fun fromStringToList(string: String): List<Int> = Json.decodeFromString(string)
}