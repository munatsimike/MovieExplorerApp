package com.example.movieexplorerapp.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.movieexplorerapp.data.local.dao.MovieDao
import com.example.movieexplorerapp.data.local.database.converter.ListOfIntegersConverter
import com.example.movieexplorerapp.data.local.database.converter.ListOfMoviesConverter
import com.example.movieexplorerapp.data.local.database.converter.MovieDateRangeConverter
import com.example.movieexplorerapp.data.local.model.MovieEntity

@Database(
    entities = [
        MovieEntity::class
    ],
    version = 1
)
@TypeConverters(
    ListOfMoviesConverter::class,
    MovieDateRangeConverter::class,
    ListOfIntegersConverter::class
)
abstract class LocalMovieDatabase : RoomDatabase() {
    abstract val movieDao: MovieDao
}