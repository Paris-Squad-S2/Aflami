package com.repository.model.local

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.repository.util.getCurrentDate
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime

@Entity(
    tableName = "reviews_Table",
    foreignKeys = [ForeignKey(
        entity = TvShowEntity::class,
        parentColumns = ["id"],
        childColumns = ["tvShowId"],
        onDelete = ForeignKey.CASCADE
    )
    ]
)
data class ReviewEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val tvShowId: Int,
    val name: String,
    val createdAt: LocalDate,
    val avatarUrl: String,
    val username: String,
    val rating: Double,
    val reviewCacheDate: LocalDateTime = getCurrentDate(),
    val description: String,
    val language: String
    )