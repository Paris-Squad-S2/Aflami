package com.repository.media.models.local.moive

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "movie_similar_table",
    foreignKeys = [ForeignKey(
        entity = MovieEntity::class,
        parentColumns = ["id"],
        childColumns = ["movieId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class MovieSimilarEntity(
    @PrimaryKey()
    val id: Int,
    val movieId: Int,
    val title: String,
    val voteAverage: Double?,
    val posterPath: String,
    val releaseDate: String,
    val language: String,
    val page: Int
)