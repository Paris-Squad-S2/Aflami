package com.repository.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.datetime.LocalDate

@Entity(
    tableName = "Reviews_Table",
    foreignKeys = [ForeignKey(
        entity = TvShowEntity::class,
        parentColumns = ["id"],
        childColumns = ["tvShowId"],
        onDelete = ForeignKey.CASCADE
    )
    ]
)
data class ReviewEntity(
    @PrimaryKey
    val id: String,
    val tvShowId: Int,
    val name: String,
    val createdAt: LocalDate,
    val avatarUrl: String,
    val username: String,
    val rating: Double,
)