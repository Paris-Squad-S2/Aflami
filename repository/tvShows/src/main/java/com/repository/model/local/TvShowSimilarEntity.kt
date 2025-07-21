package com.repository.model.local

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "tv_shows_similar_table",
    foreignKeys = [ForeignKey(
        entity = TvShowEntity::class,
        parentColumns = ["id"],
        childColumns = ["tvShowId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class TvShowSimilarEntity(
    @PrimaryKey()
    val id: Int,
    val tvShowId: Int,
    val title: String,
    val voteAverage: Double,
    val posterPath: String,
    val releaseDate: String,
    val language: String,
    val page: Int
)
