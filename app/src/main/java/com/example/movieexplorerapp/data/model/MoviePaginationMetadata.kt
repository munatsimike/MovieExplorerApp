package com.example.movieexplorerapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.movieexplorerapp.data.PAGINATION_META_DATA


@Entity(tableName = PAGINATION_META_DATA)
class MoviePaginationMetadata(
    @PrimaryKey val id: MovieCategory, val page: Int, val totalPages: Int
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as MoviePaginationMetadata
        return id == other.id
    }

}