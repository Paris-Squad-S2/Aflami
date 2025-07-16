package com.repository.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

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
    @PrimaryKey
    val id: String,
    val movieId: Int,
    val name: String,
    val createdAt: LocalDate,
    val avatarUrl: String,
    val username: String,
    val rating: Double,
)