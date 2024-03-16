package com.example.movieexplorerapp.ui.components

/**
 * This class builds a URL to fetch movie posters from the internet.
 * The image sizes are custom sizes provided by the TMDB API.
 */
object ImageUrlBuilder {
    private const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/"

    /**
     * Builds and returns a complete URL string for fetching an image.
     *
     * @param imgPath The path of the image as provided by the TMDB API.
     * @param imageSize The desired size of the image. Default is ExtraLarge.
     * @return The full URL string to fetch the image.
     */
    fun getUrl(imgPath: String, imageSize: ImageSize): String =
        "$IMAGE_BASE_URL${imageSize.size}$imgPath"
}