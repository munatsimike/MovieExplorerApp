package com.example.movieexplorerapp.data.local.database


// This enum contains all the table names that stores various movies
enum class DatabaseTable(val value: String)  {
    DISCOVER(DISCOVER_MOVIE_TABLE_NAME),
    NOW_PLAYING(NOW_PLAYING_MOVIE_TABLE_NAME),
    POPULAR(POPULAR_MOVIE_TABLE_NAME),
    TOP_RATED(TOP_RATED_MOVIE_TABLE_NAME),
    UPCOMING(UPCOMING_MOVIE_TABLE_NAME)
}