package com.example.movieexplorerapp.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.movieexplorerapp.data.local.dao.MovieDao
import com.example.movieexplorerapp.data.local.database.converter.ListOfIntegersConverter
import com.example.movieexplorerapp.data.local.database.converter.ListOfMoviesConverter
import com.example.movieexplorerapp.data.local.database.converter.MovieDateRangeConverter
import com.example.movieexplorerapp.domain.model.DiscoverMovieAPIResponseImp
import com.example.movieexplorerapp.domain.model.NowPlayingMovieAPIResponseImp
import com.example.movieexplorerapp.domain.model.PopularMovieAPIResponseImp
import com.example.movieexplorerapp.domain.model.TopRatedMovieAPIResponseImp
import com.example.movieexplorerapp.domain.model.UpcomingMovieAPIResponseImp

@Database(
    entities = [
        DiscoverMovieAPIResponseImp::class,
        NowPlayingMovieAPIResponseImp::class,
        PopularMovieAPIResponseImp::class,
        TopRatedMovieAPIResponseImp::class,
        UpcomingMovieAPIResponseImp::class
    ],
    version = 1
)
@TypeConverters(
    ListOfMoviesConverter::class,
    MovieDateRangeConverter::class,
    ListOfIntegersConverter::class
)
abstract class MovieLocalDatabase : RoomDatabase() {
    abstract val movieDao: MovieDao
}