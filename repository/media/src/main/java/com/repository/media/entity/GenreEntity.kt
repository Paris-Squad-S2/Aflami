package com.repository.media.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Genres_table")
data class GenreEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val language: String
)