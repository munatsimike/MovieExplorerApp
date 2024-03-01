package com.example.movieexplorerapp.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.movieexplorerapp.data.local.database.MOVIE_ENTITY
import com.example.movieexplorerapp.data.remote.dto.Movie

/**
 * This class defines objects that will be saved to the database and displayed to the user.
 * It inherits the movie object fetched from the API but includes an additional category field to differentiate movies.
 * The overridden methods help compare movie object contents in test cases.
 */
@Entity(tableName = MOVIE_ENTITY)
class MovieEntity(
    @PrimaryKey(autoGenerate = true)
    val primaryKey: Int = 0,
    override val adult: Boolean,
    override val backdropPath: String,
    override val genreIds: List<Int>,
    override val id: Int,
    override val originalLanguage: String,
    override val originalTitle: String,
    override val overview: String,
    override val popularity: Double?,
    override val posterPath: String,
    override val releaseDate: String,
    override val title: String,
    override val video: Boolean?,
    override val voteAverage: Double,
    override val voteCount: Int,
    val category: MovieCategory
) : Movie(
    adult,
    backdropPath,
    genreIds,
    id,
    originalLanguage,
    originalTitle,
    overview,
    popularity,
    posterPath,
    releaseDate,
    title,
    video,
    voteAverage,
    voteCount
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as MovieEntity
        return id == other.id
    }

    override fun hashCode(): Int {
        // Hash other relevant fields
        return id
    }

    companion object {

        // Converts a [Movie] object to a [MovieEntity] with a given category.
        fun fromMovieToMovieEntity(movie: Movie, category: MovieCategory): MovieEntity {
            return MovieEntity(
                id = movie.id,
                adult = movie.adult,
                backdropPath = movie.backdropPath,
                genreIds = movie.genreIds,
                originalLanguage = movie.originalLanguage,
                originalTitle = movie.originalTitle,
                overview = movie.overview,
                popularity = movie.popularity,
                posterPath = movie.posterPath,
                releaseDate = movie.releaseDate,
                title = movie.title,
                video = movie.video,
                voteAverage = movie.voteAverage,
                voteCount = movie.voteCount,
                category = category
            )
        }
    }
}