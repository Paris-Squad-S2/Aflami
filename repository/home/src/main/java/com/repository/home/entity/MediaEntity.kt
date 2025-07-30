package com.repository.home.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "media_table")
data class MediaEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val voteAverage: Double?,
    val posterPath: String,
    val releaseDate: String,
    val genreIds: List<Int>,
    val type: MediaTypeEntity
)

enum class MediaTypeEntity {
    TV_SHOW,
    MOVIE
}