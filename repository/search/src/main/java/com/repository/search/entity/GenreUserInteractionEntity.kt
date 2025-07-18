package com.repository.search.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "genres_user_interaction",
    foreignKeys = [
        ForeignKey(
            entity = GenreEntity::class,
            parentColumns = ["id"],
            childColumns = ["genreId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class GenreUserInteractionEntity(
    @PrimaryKey
    val genreId: Int,
    val interactionCount: Int
)

