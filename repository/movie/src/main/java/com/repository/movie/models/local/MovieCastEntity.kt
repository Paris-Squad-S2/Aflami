package com.repository.movie.models.local

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.repository.movie.util.getCurrentDate
import kotlinx.datetime.LocalDateTime

@Entity(
    tableName = "movie_cast_table", foreignKeys = [ForeignKey(
        entity = MovieEntity::class,
        parentColumns = ["id"],
        childColumns = ["movieId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class MovieCastEntity(
    @PrimaryKey
    val id: Int,
    val movieId: Int,
    val name: String,
    val imageUri: String,
    val castCacheDate: LocalDateTime = getCurrentDate(),
    val language: String
)
