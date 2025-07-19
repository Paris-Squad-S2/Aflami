package com.repository.movie.models.local

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.repository.movie.util.getCurrentDate
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime

@Entity(
    tableName = "Reviews_Table",
    foreignKeys = [ForeignKey(
        entity = MovieEntity::class,
        parentColumns = ["id"],
        childColumns = ["movieId"],
        onDelete = ForeignKey.CASCADE
    )
    ]
)
data class ReviewEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val movieId: Int,
    val name: String,
    val createdAt: LocalDate,
    val avatarUrl: String,
    val username: String,
    val rating: Double,
    val reviewCacheDate: LocalDateTime = getCurrentDate(),
    val description: String
    )