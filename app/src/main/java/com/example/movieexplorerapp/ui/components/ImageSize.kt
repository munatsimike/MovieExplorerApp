package com.example.movieexplorerapp.ui.components


/**
 * This enum contains image sizes for movie posters.
 * The sizes are defined by TMDB Api
 */
enum class ImageSize(val size: String) {
    ExtraSmall("w92"),
    Small("w154"),
    Medium("w185"),
    Large("w342"),
    ExtraLarge("w500"),
    Ultra("w780");
}