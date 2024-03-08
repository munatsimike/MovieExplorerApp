package com.example.movieexplorerapp.data.model

import com.example.movieexplorerapp.data.model.dto.Movie


/**
 * This class is responsible for converting a Movie object into a MovieEntity object.
 * A Movie object serves as a data transfer object (DTO) representing data fetched from an external source or API.
 * A MovieEntity object is designed for database storage, extending the Movie data model with additional database-related functionalities, including a category field to differentiate movies based on predefined categories.
 */
class MovieMapper {

    companion object {
        /**
         * Converts a [Movie] object to a [MovieEntity], enriching it with a specific category.
         * This method allows for the inclusion of an additional field, 'category', in the MovieEntity,
         * which is not present in the original Movie object. This extra field is crucial for categorizing
         * movies in the database, for example, as 'ACTION', 'DRAMA', etc.
         *
         * @param movie The Movie object to be converted.
         * @param category The category to be assigned to the resulting MovieEntity.
         * @return A MovieEntity object that includes all fields from the Movie object, along with the specified category.
         */
        private fun map(movie: Movie, category: MovieCategory) = with(movie) {
            MovieEntity(
                id = id,
                adult = adult,
                backdropPath = backdropPath,
                genreIds = genreIds,
                originalLanguage = originalLanguage,
                originalTitle = originalTitle,
                overview = overview,
                popularity = popularity,
                posterPath = posterPath,
                releaseDate = releaseDate,
                title = title,
                video = video,
                voteAverage = voteAverage,
                voteCount = voteCount,
                category = category
            )
        }

        /**
         * Converts a list of Movie objects into a list of MovieEntity objects, each enriched with a given category.
         * This function facilitates batch conversion of Movie objects for database insertion or update,
         * ensuring that all entities are consistently categorized.
         *
         * @param list The list of Movie objects to be converted.
         * @param category The category to assign to all resulting MovieEntities.
         * @return A Result<List<MovieEntity>> object encapsulating the successful conversion of the list or an exception if the operation fails.
         */
        fun mapList(list: List<Movie>, category: MovieCategory) = runCatching {
            list.map { movie ->
                map(movie, category)
            }
        }
    }
}