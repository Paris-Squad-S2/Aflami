package com.repository.media.models.local.media

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "genres_user_interaction")
data class GenreUserInteractionEntity(
    @PrimaryKey
    val genreId: Int,
    val interactionCount: Int
)

