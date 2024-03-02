package com.example.movieexplorerapp.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.movieexplorerapp.data.local.database.PAGINATION_META_DATA


@Entity(tableName = PAGINATION_META_DATA)
class MoviePaginationMetadata(
    @PrimaryKey val id: String,
    val page: Int, val totalPages: Int
)