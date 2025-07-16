package com.example.movie.models.local

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.movie.util.getCurrentDate
import kotlinx.datetime.LocalDateTime

@Entity(
    tableName = "cast_table", foreignKeys = [ForeignKey(
        entity = MovieEntity::class,
        parentColumns = ["id"],
        childColumns = ["movieId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class CastEntity(
    @PrimaryKey
    val id: Int,
    val movieId: Int,
    val name: String,
    val imageUri: String,
    val castCacheDate: LocalDateTime = getCurrentDate(),
    )
