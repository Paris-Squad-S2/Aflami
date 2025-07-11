package com.repository.search.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Genres_table")
data class GenreEntity(
    @PrimaryKey
    val id: Int,
    val name: String
)