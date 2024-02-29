package com.example.movieexplorerapp.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
<<<<<<< HEAD
import com.example.movieexplorerapp.data.local.database.MOVIE_ENTITY
import com.example.movieexplorerapp.data.remote.dto.Movie

@Entity(tableName = MOVIE_ENTITY)
=======
import com.example.movieexplorerapp.data.local.database.MOVIE_ENTITY_TABLE
import com.example.movieexplorerapp.data.remote.dto.Movie

@Entity(tableName = MOVIE_ENTITY_TABLE)
>>>>>>> 96c59544107052fa26ba09abc39e1f6ab68886f9
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
    companion object {
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